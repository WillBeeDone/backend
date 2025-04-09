package de.willbeedone.backend.controller;

import de.willbeedone.backend.service.interfaces.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/locations")
@Tag(name = "Location controller", description = "Controller for various operations with Users' (Offers') Locations.")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Getting all city names",
            description = "Returns the list of all city names (e.g. for dropdown on the Home Page.")
    @GetMapping
    public List<String> getAllLocationsCityNames(@RequestParam Map<String, String> allParams) {
        System.out.println("Query params: " + allParams); //  консоль
        if (!allParams.isEmpty()) {
            throw new IllegalArgumentException("Unexpected query parameter(s): " + allParams.keySet());
        }
        return locationService.getAllLocationsCityNames();
    }
}
