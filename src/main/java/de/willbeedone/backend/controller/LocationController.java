package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.entity.Location;
import de.willbeedone.backend.service.interfaces.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@Tag(name = "Location controller", description = "Controller for various operations with Users' (Offers') Locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Getting all city names",
            description = "Returns the list of all city names (e.g. for dropdown on the Home Page.")
    @GetMapping
    public List<String> getAllLocationsCityNames() {
        return locationService.getAllLocationsCityNames();
    }

//    @GetMapping("/{id}")
//    public Location getLocationById(@PathVariable Long id) {
//        return locationService.getLocationById(id);
//    }
//
//    //GET /locations/search?cityName=Berlin
//    @GetMapping("/search")
//    public List<Location> getLocationByCity(String cityName) {
//        return locationService.getLocationByCity(cityName);
//    }
//
//   //@PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/new")
//    public Location addNewLocation(@RequestBody Location location) {
//        return locationService.addNewLocation(location); //dto если нужно создам
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteLocation(@PathVariable Long id) {
//        locationService.deleteLocation(id);
//    }

}
