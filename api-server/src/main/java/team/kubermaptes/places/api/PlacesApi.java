package team.kubermaptes.places.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.kubermaptes.places.dto.PlaceDto;
import team.kubermaptes.places.exception.PlaceNotFoundException;
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

    @GetMapping("/lat-lng")
    public ResponseEntity<PlaceDto> getPlaceByLatLng(@RequestParam("lat") String latitude,
                                                     @RequestParam("lng") String longitude) {
        try {
            return ResponseEntity.ok(this.placesService.findPlaceByLatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
        } catch (PlaceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
