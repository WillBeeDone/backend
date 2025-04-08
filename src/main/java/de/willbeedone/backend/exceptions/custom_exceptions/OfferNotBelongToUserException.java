package de.willbeedone.backend.exceptions.custom_exceptions;

public class OfferNotBelongToUserException extends RuntimeException {

    public OfferNotBelongToUserException(Long offerId, String email) {
        super(String.format("Offer with id: %d doesn't belong to user with email: %s", offerId, email));
    }

}
