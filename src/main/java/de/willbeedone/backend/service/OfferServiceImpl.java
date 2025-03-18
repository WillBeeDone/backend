package de.willbeedone.backend.service;


import de.willbeedone.backend.domain.dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.response_dto.OfferResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.exceptions.OfferNotFoundException;
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

            Offer newOffer = mappingService.mapRequestDtoToEntity(request);
            return repository.save(newOffer);
    }

//        Optional<Offer> foundOffer = repository.findOfferByTitle(title);
//        if (foundOffer.isEmpty()) {
//            throw new OfferNotFoundException("Offer not found with title: " + title);
//        }

    @Override
    public List<OfferResponseDto> getOfferByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
        return repository.findOfferByTitleAndActiveIsTrue(title).stream()
                .map(mappingService::mapEntityToResponseDto)
                .toList();

    }

    @Override
    public Optional<OfferResponseDto> getOfferById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be empty or null");
        }
        return repository.findById(id)
                .map(mappingService::mapEntityToResponseDto)
                .or(
                        () -> {
                            throw new OfferNotFoundException("Offer not found with id: " + id);
                        }
                );
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
            throw new OfferNotFoundException("Offer not found with id: " + id);
        } else {
            repository.deleteById(id);
        }
    }

    @Override
    public List<Offer> findAllOffers() {
        return repository.findAll();
    }
}
