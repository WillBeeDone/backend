package de.willbeedone.backend.service;


import de.willbeedone.backend.domain.dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.response_dto.OfferResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.exception.AlreadyExistException;
import de.willbeedone.backend.exception.OfferNotFoundException;
import de.willbeedone.backend.repository.OfferRepository;
import de.willbeedone.backend.service.interfaces.OfferService;
import de.willbeedone.backend.service.mapping.OfferMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {


    private final OfferRepository repository;
    private final OfferMappingService mappingService;


    @Override
    public Offer addNewOffer(OfferRequestDto request) {
        if (checkIfOfferExists(request.getId())) {
            Offer newOffer = mappingService.mapDtoToResponseEntity(request);
            return repository.save(newOffer);
        } else {
            throw new AlreadyExistException(
                    "Offer with id " + request.getId() + " already exists");
        }
    }

    private boolean checkIfOfferExists(Long id) {
        Optional<Offer> foundedOffer = repository.findById(id);
        return foundedOffer.isEmpty();
    }

    @Override
    public Optional<OfferResponseDto> getOfferByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }

        Optional<Offer> foundedOffer = repository.findOfferByTitle(title);
        if (foundedOffer.isEmpty()) {
            throw new OfferNotFoundException("Offer not found with title: " + title);
        }

        return Optional.of(mappingService.mapResponseEntityToDto(foundedOffer.get()));
    }

    @Override
    public Optional<OfferResponseDto> getOfferById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be empty or null");
        }
        return repository.findById(id)
                .map(mappingService::mapResponseEntityToDto)
                .or(
                        () -> {
                            throw new OfferNotFoundException("Offer not found with id: " + id);
                        }
                );
    }

    @Override
    public Offer updateOffer(OfferRequestDto dto, Long id) {
        return repository.findById(id)
                .map(existingOffer -> {
                    existingOffer.setCategory(dto.getCategory());
                    existingOffer.setId(dto.getId());
                    existingOffer.setDescription(dto.getDescription());
                    existingOffer.setTitle(dto.getTitle());
                    return repository.save(existingOffer);
                })
                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id: " + id));
    }

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
