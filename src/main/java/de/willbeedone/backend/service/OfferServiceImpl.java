package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.dto.offer_dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.exceptions.custom_exceptions.OfferNotFoundException;
import de.willbeedone.backend.repository.OfferRepository;
import de.willbeedone.backend.service.interfaces.OfferService;
import de.willbeedone.backend.service.mapping.OfferMappingService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {


    private final OfferRepository repository;
    private final OfferMappingService mappingService;

    public OfferServiceImpl(OfferRepository repository, OfferMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    // Доработать обработчик, спросить у Артема про случай с Уже существующим оффером
    @Override
    public Offer addNewOffer(OfferRequestDto request) {
        Offer newOffer = mappingService.mapRequestDtoToEntity(request);
        return repository.save(newOffer);
    }

//        Optional<Offer> foundOffer = repository.findOfferByTitle(title);
//        if (foundOffer.isEmpty()) {
//            throw new OfferNotFoundException("Offer not found with title: " + title);
//        }

    @Override
    public List<OfferFilterResponseDto> getAllOffers() {
        List<OfferFilterResponseDto> offers = repository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Offer::getPricePerHour))
                .map(mappingService::mapEntityToFilterResponseDto)
                .toList();
        if (offers.isEmpty()) {
            throw new OfferNotFoundException();
        }
        return offers;
    }

    @Override
    public List<OfferFilterResponseDto> getFilteredOffers(String cityName, String category, String keyPhrase) {
        return repository
                .findAll()
                .stream()
                .filter(Offer::isActive)
                .filter(offer -> offer == null || "all".equals(cityName) || offer.getUser().getLocation().getCityName().equals(cityName))
                .filter(offer -> offer == null || "all".equals(category) || offer.getCategory().equals(category))
                .filter(offer -> offer == null || "all".equals(keyPhrase) || offer.getUser().getFirstName().contains(keyPhrase) || offer.getUser().getLastName().contains(keyPhrase) || offer.getTitle().contains(keyPhrase) || offer.getDescription().contains(keyPhrase))
                .map(mappingService::mapEntityToFilterResponseDto)
                .toList();
    }

    @Override
    public Optional<List<OfferFilterResponseDto>> getOfferByTitle(String title) {
        List<OfferFilterResponseDto> offers = repository.findOfferByTitleAndActiveIsTrue(title)
                .stream()
                .map(mappingService::mapEntityToFilterResponseDto)
                .toList();
        if (offers.isEmpty()) {
            throw new OfferNotFoundException(title);
        }

        return Optional.of(offers);
    }

    @Override
    public Optional<OfferFilterResponseDto> getOfferById(Long id) {
        return Optional.ofNullable(repository.findById(id)
                .map(mappingService::mapEntityToFilterResponseDto)
                .orElseThrow(
                        () -> new OfferNotFoundException(id)));
    }

    @Override
    public Offer updateOffer(OfferRequestDto dto, Long id) {
        return null;
    }

//    @Override
//    public Offer updateOffer(OfferRequestDto dto, Long id) {
//        if (dto == null) {
//            throw new IllegalArgumentException("OfferRequestDto cannot be null");
//        }
//
//        return repository.findById(id)
//                .map(existingOffer -> {
//                    if (dto.getCategory() != null) {
//                        existingOffer.setCategory(dto.getCategory());
//                    }
//                    if (dto.getDescription() != null) {
//                        existingOffer.setDescription(dto.getDescription());
//                    }
//                    if (dto.getTitle() != null) {
//                        existingOffer.setTitle(dto.getTitle());
//                    }
//                    return repository.save(existingOffer);
//                })
//                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id: " + id));
//    }

    @Override
    public void deleteOfferById(Long id) {
        if (!repository.existsById(id)) {
            throw new OfferNotFoundException(id);
        } else {
            repository.deleteById(id);
        }
    }

}
