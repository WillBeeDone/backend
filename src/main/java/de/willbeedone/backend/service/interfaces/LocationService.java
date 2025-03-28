package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.entity.Location;

import java.util.List;

public interface LocationService {

    List<String> getAllLocationsCityNames();

    Location getLocationById(Long id);

    Location getLocationByCity(String cityName);

    Location addNewLocation(Location location);

    void deleteLocation(Long id);

}
