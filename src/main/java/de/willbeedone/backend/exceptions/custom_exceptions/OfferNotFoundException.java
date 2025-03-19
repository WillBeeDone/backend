package de.willbeedone.backend.exceptions.custom_exceptions;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(String param) {
        super(String.format("Offer with characteristic: %s not found", param));
    }

    public OfferNotFoundException(Long id) {
        super(String.format("Offer with id: %d not found", id));
    }
    public OfferNotFoundException() {
        super("No offers found");
    }}
