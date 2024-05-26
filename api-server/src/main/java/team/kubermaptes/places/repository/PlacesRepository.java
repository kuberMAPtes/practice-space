package team.kubermaptes.places.repository;

import team.kubermaptes.places.domain.Place;
import team.kubermaptes.places.exception.PlaceNotFoundException;

import java.util.List;

public interface PlacesRepository {

    public List<Place> findByPlaceName(String placeName);

    public Place findByLatLng(double latitude, double longitude) throws PlaceNotFoundException;
}
