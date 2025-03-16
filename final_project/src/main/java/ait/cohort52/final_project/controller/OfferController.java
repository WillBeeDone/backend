package ait.cohort52.final_project.controller;


import ait.cohort52.final_project.domain.dto.requestDto.OfferRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.OfferResponseDto;
import ait.cohort52.final_project.domain.entity.Offer;

import ait.cohort52.final_project.service.OfferServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferServiceImpl service;

    @PostMapping(value = "/add")
    public Offer createUser(@Valid @RequestBody OfferRequestDto request) {
        return service.addNewOffer(request);
    }
    @GetMapping(value = "/{id}")
    public Optional<OfferResponseDto> getOfferById(Long id) {
        return service.getOfferById(id);
    }
    @GetMapping(value = "/title/{title}")
    public Optional<OfferResponseDto> getOfferByTitle(String title) {
        return service.getOfferByTitle(title);
    }
@PutMapping(value = "/update")
    Offer updateOffer(OfferRequestDto offer, Long id){
        return service.updateOffer(offer, id);
}
@DeleteMapping(value = "/delete")
    void deleteOffer(Long id){
        service.deleteOffer(id);
}
@GetMapping(value = "/all")
List<Offer>findAllOffers(){
    return service.findAllOffers();
}
}
