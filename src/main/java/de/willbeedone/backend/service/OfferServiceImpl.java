package de.willbeedone.backend.service;


import de.willbeedone.backend.domain.dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.response_dto.OfferResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.exceptions.custom_exceptions.OfferNotFoundException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.OfferValidationException;
import de.willbeedone.backend.repository.OfferRepository;
import de.willbeedone.backend.service.interfaces.OfferService;
import de.willbeedone.backend.service.mapping.OfferMappingService;
import org.springframework.stereotype.Service;

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

    @Override
    public Offer addNewOffer(OfferRequestDto request) {
        try {
            Offer newOffer = mappingService.mapRequestDtoToEntity(request);
            return repository.save(newOffer);
        } catch (Exception e) {
            throw new OfferValidationException(e);
        }
    }

//        Optional<Offer> foundOffer = repository.findOfferByTitle(title);
//        if (foundOffer.isEmpty()) {
//            throw new OfferNotFoundException("Offer not found with title: " + title);
//        }

    @Override
    public Optional<List<OfferResponseDto>> getOfferByTitle(String title) {
        try {
            List<OfferResponseDto> offers = repository.findOfferByTitleAndActiveIsTrue(title)
                    .stream()
                    .map(mappingService::mapEntityToResponseDto)
                    .toList();
            if (offers.isEmpty()) {
                throw new OfferNotFoundException(title);
            }

            return Optional.of(offers);
        } catch (Exception e) {
            throw new OfferValidationException(e);
        }
    }

    @Override
    public Optional<OfferResponseDto> getOfferById(Long id) {
        try {
            return Optional.ofNullable(repository.findById(id)
                    .map(mappingService::mapEntityToResponseDto)
                    .orElseThrow(
                            () -> new OfferNotFoundException(id)));
        } catch (Exception e) {
            throw new OfferValidationException(e);
        }
    }

    @Override
    public Offer updateOffer(OfferRequestDto dto, Long id) {
        try {
            return null;
        } catch (Exception e) {
            throw new OfferValidationException(e);
        }
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
        try {
            if (!repository.existsById(id)) {
                throw new OfferNotFoundException(id);
            } else {
                repository.deleteById(id);
            }
        } catch (Exception e) {
            throw new OfferValidationException(e);
        }
    }

    @Override
    public List<Offer> findAllOffers() {
        try {
            List<Offer> offers = repository.findAll();
            if (offers.isEmpty()) {
                throw new OfferNotFoundException();
            }
            return offers;
        } catch (Exception e) {
            throw new OfferValidationException(e);
        }
    }
}
