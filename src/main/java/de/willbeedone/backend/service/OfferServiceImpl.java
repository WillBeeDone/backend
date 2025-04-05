package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.offer_dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.domain.entity.Category;
import de.willbeedone.backend.domain.entity.ImageGallery;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.custom_exceptions.OfferNotFoundException;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.OfferValidationException;
import de.willbeedone.backend.repository.CategoryRepository;
import de.willbeedone.backend.repository.OfferRepository;
import de.willbeedone.backend.service.interfaces.CategoryService;
import de.willbeedone.backend.service.interfaces.ImageService;
import de.willbeedone.backend.service.interfaces.OfferService;
import de.willbeedone.backend.service.interfaces.UserService;
import de.willbeedone.backend.service.mapping.OfferMappingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private final OfferRepository offerRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final OfferMappingService offerMappingService;
    private final CategoryRepository categoryRepository;

    public OfferServiceImpl(OfferRepository offerRepository, UserService userService, CategoryService categoryService, ImageService imageService, OfferMappingService offerMappingService, CategoryRepository categoryRepository) {
        this.offerRepository = offerRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.imageService = imageService;
        this.offerMappingService = offerMappingService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Offer addNewOffer(OfferRequestDto offerDto, String email) {
        Offer offer = offerMappingService.mapRequestDtoToEntity(offerDto);

        Category category = categoryRepository.findCategoryByName(offerDto.getCategoryName());
        offer.setCategory(category);

        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        offer.setUser(user);

        if (offerDto.getImages() != null && !offerDto.getImages().isEmpty()) {
            Set<ImageGallery> images = offerDto.getImages().stream()
                    .map(imageGallery -> imageService.mapFileToImageGalleryDto(imageGallery, offer))
                    .collect(Collectors.toSet());

            offer.setImages(images);
        }

        return offerRepository.save(offer);
    }

    @Override
    public List<OfferFilterResponseDto> getAllActiveOffers() {
        return offerRepository
                .findAll()
                .stream()
                .filter(Offer::isActive)
                .sorted(Comparator.comparing(Offer::getPricePerHour))
                .map(offerMappingService::mapEntityToFilterResponseDto)
                .toList();
    }

    @Override
    public Page<OfferFilterResponseDto> getAllActiveOffers(Pageable pageable) {
        return offerRepository.findActiveOffers(pageable)
                .map(offerMappingService::mapEntityToFilterResponseDto);
    }

    @Override
    public Page<OfferFilterResponseDto> getFilteredOffers(
            String cityName, String category, String keyPhrase,
            BigDecimal minPrice, BigDecimal maxPrice, PageRequest pageRequest) {


        Specification<Offer> spec = getBaseSpecification();

        if (!"all".equals(cityName)) {
            spec = spec.and(filterByCity(cityName));
        }
        if (!"all".equals(category)) {
            if (!categoryService.existsByName(category)) {
                throw new OfferValidationException("Invalid category: " + category);
            }
            spec = spec.and(filterByCategory(category));
        }
        if (!"all".equals(keyPhrase)) {
            spec = spec.and(filterByKeyPhrase(keyPhrase));
        }
        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("pricePerHour"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("pricePerHour"), maxPrice));
        }

        Page<Offer> pagedData = offerRepository.findAll(spec, pageRequest);

        List<OfferFilterResponseDto> offerDtos = pagedData.getContent().stream()
                .map(offerMappingService::mapEntityToFilterResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(offerDtos, pageRequest, pagedData.getTotalElements());
    }

    /**
     * Базовая спецификация, фильтрующая только активные записи
     */
    private Specification<Offer> getBaseSpecification() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    private Specification<Offer> filterByCity(String cityName) {
        return "all".equals(cityName) ? null :
                (root, query, cb) -> cb.equal(root.join("user").join("location").get("cityName"), cityName);
    }

    private Specification<Offer> filterByCategory(String category) {
        return "all".equals(category) ? null :
                (root, query, cb) -> cb.equal(root.join("category").get("name"), category);
    }

    private Specification<Offer> filterByKeyPhrase(String keyPhrase) {
        return "all".equals(keyPhrase) ? null :
                (root, query, cb) -> {
                    String pattern = "%" + keyPhrase + "%";
                    return cb.or(
                            cb.like(root.get("title"), pattern),
                            cb.like(root.get("description"), pattern),
                            cb.like(root.join("user").get("firstName"), pattern),
                            cb.like(root.join("user").get("lastName"), pattern)
                    );
                };
    }


    @Override
    public Optional<List<OfferFilterResponseDto>> getOfferByTitle(String title) {
        return Optional.of(offerRepository.findOfferByTitleAndActiveIsTrue(title)
                .stream()
                .map(offerMappingService::mapEntityToFilterResponseDto)
                .toList());
    }

    @Override
    public Optional<OfferProfileGuestResponseDto> getActiveOfferById(Long id) {
        return Optional.ofNullable(offerMappingService.mapEntityToProfileGuestResponseDto(getActiveOfferEntityById(id)));

    }

    @Override
    public Page<OfferFilterResponseDto> getActiveOffersByCity(String cityName, Pageable pageable) {
        Page<Offer> offerPage = offerRepository.findByCity(cityName, pageable);

        if (offerPage.isEmpty()) {
            throw new OfferNotFoundException("No active offers found in city: " + cityName);
        }
        return offerPage.map(offerMappingService::mapEntityToFilterResponseDto);
    }

    @Override
    public Offer getActiveOfferEntityById(Long id) {
        return offerRepository.findById(id)
                .filter(Offer::isActive)
                .orElseThrow(
                        () -> new OfferNotFoundException(id));
    }

    @Override
    public Offer updateOffer(OfferRequestDto dto, Long id) {
        Category category = categoryService.getCategoryByName(dto.getCategoryName());

        return offerRepository.findById(id)
                .map(existingOffer -> {
                    if (dto.getCategoryName() != null) existingOffer.setCategory(category);
                    if (dto.getDescription() != null) existingOffer.setDescription(dto.getDescription());
                    if (dto.getTitle() != null) existingOffer.setTitle(dto.getTitle());
                    return existingOffer;
                })
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @Override
    public void deleteOfferById(Long id) {
        if (!offerRepository.existsById(id)) {
            throw new OfferNotFoundException(id);
        }
        offerRepository.deleteById(id);
    }

}

