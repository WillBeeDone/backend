package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.offer_dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.domain.entity.Category;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.exceptions.custom_exceptions.OfferNotFoundException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.OfferValidationException;
import de.willbeedone.backend.repository.CategoryRepository;
import de.willbeedone.backend.repository.OfferRepository;
import de.willbeedone.backend.service.interfaces.CategoryService;
import de.willbeedone.backend.service.interfaces.OfferService;
import de.willbeedone.backend.service.mapping.OfferMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private final OfferRepository repository;
    private final CategoryService categoryService;
    private final OfferMappingService mappingService;
    private final CategoryRepository categoryRepository;

    public OfferServiceImpl(OfferRepository repository, CategoryService categoryService, OfferMappingService mappingService, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.mappingService = mappingService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Offer addNewOffer(OfferRequestDto request) {
        try {
            Category category = categoryService.getCategoryByName(request.getCategoryDto().getName());
            Offer newOffer = mappingService.mapRequestDtoToEntity(request);
            newOffer.setCategory(category);
            return repository.save(newOffer);
        } catch (DataIntegrityViolationException e) {
            throw new OfferValidationException(e);
        }
    }

    @Override
    public List<OfferFilterResponseDto> getAllActiveOffers() {
        return repository
                .findAll()
                .stream()
                .filter(Offer::isActive)
                .sorted(Comparator.comparing(Offer::getPricePerHour))
                .map(mappingService::mapEntityToFilterResponseDto)
                .toList();
    }

    @Override
    public Page<OfferFilterResponseDto> getAllActiveOffers(Pageable pageable) {
        return repository.findActiveOffers(pageable)
                .map(mappingService::mapEntityToFilterResponseDto);
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

        Page<Offer> pagedData = repository.findAll(spec, pageRequest);

        List<OfferFilterResponseDto> offerDtos = pagedData.getContent().stream()
                .map(mappingService::mapEntityToFilterResponseDto)
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
        return Optional.of(repository.findOfferByTitleAndActiveIsTrue(title)
                .stream()
                .map(mappingService::mapEntityToFilterResponseDto)
                .toList());
    }

    @Override
    public  Optional<OfferProfileGuestResponseDto> getActiveOfferById(Long id) {
        return Optional.ofNullable(mappingService.mapEntityToProfileGuestResponseDto(getActiveOfferEntityById(id)));

    }

    @Override
    public Page<OfferFilterResponseDto> getActiveOffersByCity(String cityName, Pageable pageable) {
        Page<Offer> offerPage = repository.findByCity(cityName, pageable);

        if (offerPage.isEmpty()) {
            throw new OfferNotFoundException("No active offers found in city: " + cityName);
        }
        return offerPage.map(mappingService::mapEntityToFilterResponseDto);
    }

    @Override
    public Offer getActiveOfferEntityById(Long id) {
        return repository.findById(id)
                .filter(Offer::isActive)
                .orElseThrow(
                        () -> new OfferNotFoundException(id));
    }

    @Override
    public Offer updateOffer(OfferRequestDto dto, Long id) {
        Category category = categoryService.getCategoryByName(dto.getCategoryDto().getName());

        return repository.findById(id)
                .map(existingOffer -> {
                    if (dto.getCategoryDto().getName() != null) existingOffer.setCategory(category);
                    if (dto.getDescription() != null) existingOffer.setDescription(dto.getDescription());
                    if (dto.getTitle() != null) existingOffer.setTitle(dto.getTitle());
                    return existingOffer;
                })
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @Override
    public void deleteOfferById(Long id) {
        if (!repository.existsById(id)) {
            throw new OfferNotFoundException(id);
        }
        repository.deleteById(id);
    }

}

