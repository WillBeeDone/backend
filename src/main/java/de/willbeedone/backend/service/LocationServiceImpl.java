package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.Location;
import de.willbeedone.backend.repository.LocationRepository;
import de.willbeedone.backend.service.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl  implements LocationService {

    @Autowired
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<String> getAllLocationsCityNames() {
        return locationRepository.findAll()
                .stream()
                .map(Location::getCityName)
                .toList();
    }

    @Override
    public boolean existsByCityName(String cityName) {
        return locationRepository.existsByCityName(cityName);
    }

    @Override
    public Location getLocationByCity(String cityName) {
        return locationRepository.findByCityName(cityName)
                .orElseThrow(() -> new RuntimeException("Location not found: " + cityName));
    }

}
