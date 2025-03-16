package ait.cohort52.final_project.service;

import ait.cohort52.final_project.domain.dto.requestDto.OfferRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.OfferResponseDto;
import ait.cohort52.final_project.domain.entity.Offer;
import ait.cohort52.final_project.repository.OfferRepository;
import ait.cohort52.final_project.service.exception.AlreadyExistException;
import ait.cohort52.final_project.service.exception.OfferNotFoundException;
import ait.cohort52.final_project.service.intrfaces.OfferService;
import ait.cohort52.final_project.service.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {


    private final OfferRepository repository;
    private final MappingService mappingService;


    @Override
    public Offer addNewOffer(OfferRequestDto request) {
        if (CheckIfOfferExists(request.getId())) {
            Offer newOffer = mappingService.getOfferFromDto(request);
            return repository.save(newOffer);
        } else {
            throw new AlreadyExistException(
                    "Offer with id " + request.getId() + " already exists");
        }
    }

    private boolean CheckIfOfferExists(Long id) {
        Optional<Offer> foundedOffer = repository.findOfferById(id);
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

        return Optional.of(mappingService.getOfferDtoFromEntity(foundedOffer.get()));
    }

    @Override
    public Optional<OfferResponseDto> getOfferById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be empty or null");
        }
        return repository.findOfferById(id)
                .map(mappingService::getOfferDtoFromEntity)
                .or(
                        () -> {
                            throw new OfferNotFoundException("Offer not found with id: " + id);
                        }
                );
    }

    @Override
    public Offer updateOffer(OfferRequestDto dto, Long id) {
        return repository.findOfferById(id)
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
    public void deleteOffer(Long id) {
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
