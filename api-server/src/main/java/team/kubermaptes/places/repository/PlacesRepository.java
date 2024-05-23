package team.kubermaptes.places.repository;

import team.kubermaptes.places.domain.Place;

import java.util.List;

public interface PlacesRepository {

    public List<Place> findByPlaceName(String placeName);
}
