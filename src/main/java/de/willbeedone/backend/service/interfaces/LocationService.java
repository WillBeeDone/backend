package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.entity.Location;

import java.util.List;

public interface LocationService {

    List<String> getAllLocationsCityNames();

    boolean existsByCityName(String cityName);

    Location getLocationByCity(String cityName);

}
