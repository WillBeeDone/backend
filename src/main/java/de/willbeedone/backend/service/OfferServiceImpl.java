package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.offer_dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.exceptions.custom_exceptions.OfferNotFoundException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.OfferValidationException;
import de.willbeedone.backend.repository.OfferRepository;
import de.willbeedone.backend.service.interfaces.OfferService;
import de.willbeedone.backend.service.mapping.OfferMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private final OfferRepository repository;
    private final OfferMappingService mappingService;

    public OfferServiceImpl(OfferRepository repository, OfferMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public Offer addNewOffer(OfferRequestDto request) {
        try {
            Offer newOffer = mappingService.mapRequestDtoToEntity(request);
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
    public List<OfferFilterResponseDto> getFilteredOffers(String cityName, String category, String keyPhrase) {
        return repository
                .findAll()
                .stream()
                .filter(Offer::isActive)
                .filter(offer -> offer == null || "all".equals(cityName) || offer.getUser().getLocation().getCityName().equals(cityName))
                .filter(offer -> offer == null || "all".equals(category) || offer.getCategory().getName().equals(category))
                .filter(offer -> offer == null || "all".equals(keyPhrase) || offer.getUser().getFirstName().contains(keyPhrase) || offer.getUser().getLastName().contains(keyPhrase) || offer.getTitle().contains(keyPhrase) || offer.getDescription().contains(keyPhrase))
                .map(mappingService::mapEntityToFilterResponseDto)
                .toList();
    }

    @Override
    public Optional<List<OfferFilterResponseDto>> getOfferByTitle(String title) {
        return Optional.of(repository.findOfferByTitleAndActiveIsTrue(title)
                .stream()
                .map(mappingService::mapEntityToFilterResponseDto)
                .toList());
    }

    @Override
    public OfferProfileGuestResponseDto getActiveOfferById(Long id) {
        return mappingService.mapEntityToProfileGuestResponseDto(getActiveOfferEntityById(id));

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
        return repository.findById(id)
                .map(existingOffer -> {
                    if (dto.getCategory() != null) existingOffer.setCategory(dto.getCategory());
                    if (dto.getDescription() != null) existingOffer.setDescription(dto.getDescription());
                    if (dto.getTitle() != null) existingOffer.setTitle(dto.getTitle());
                    return repository.save(existingOffer);
                })
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @Override
    public void deleteOfferById(Long id) {
        if (!repository.existsById(id)) {
            throw new OfferNotFoundException(id);}
        repository.deleteById(id);
    }
}

