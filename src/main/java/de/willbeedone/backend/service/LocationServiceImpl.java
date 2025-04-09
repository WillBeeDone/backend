package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.Location;
import de.willbeedone.backend.repository.LocationRepository;
import de.willbeedone.backend.service.interfaces.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl  implements LocationService {

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
    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found"));
    }

    @Override
    public Location getLocationByCity(String cityName) {
        return locationRepository.findByCityName(cityName)
                .orElseThrow(() -> new RuntimeException("Location not found: " + cityName));
    }

    @Override
    public Location addNewLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
