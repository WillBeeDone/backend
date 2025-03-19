package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.entity.Location;
import de.willbeedone.backend.service.interfaces.LocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    //GET /locations/search?cityName=Berlin
    @GetMapping("/search")
    public List<Location> getLocationByCity(String cityName) {
        return locationService.getLocationByCity(cityName);
    }

   //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public Location addNewLocation(@RequestBody Location location) {
        return locationService.addNewLocation(location); //dto если нужно создам
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }

}
