package team.kubermaptes.places.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.kubermaptes.places.dto.PlaceDto;
import team.kubermaptes.places.service.PlacesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlacesApi {
    private final PlacesService placesService;

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getPlacesByPlaceName(@RequestParam("placeName") String placeName) {
        return ResponseEntity.ok(this.placesService.findPlacesByPlaceName(placeName));
    }
}
