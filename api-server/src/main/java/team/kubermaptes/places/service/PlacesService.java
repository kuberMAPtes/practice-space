package team.kubermaptes.places.service;

import team.kubermaptes.places.dto.PlaceDto;
import team.kubermaptes.places.exception.PlaceNotFoundException;

import java.util.List;

public interface PlacesService {

    List<PlaceDto> findPlacesByPlaceName(String placeName);

    PlaceDto findPlaceByLatLng(double latitude, double longitude) throws PlaceNotFoundException;
}
