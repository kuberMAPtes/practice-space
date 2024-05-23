package team.kubermaptes.places.service;

import team.kubermaptes.places.dto.PlaceDto;

import java.util.List;

public interface PlacesService {

    List<PlaceDto> findPlacesByPlaceName(String placeName);
}
