package team.kubermaptes.places.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.kubermaptes.places.dto.PlaceDto;
import team.kubermaptes.places.repository.PlacesRepository;
import team.kubermaptes.places.service.PlacesService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlacesServiceImpl implements PlacesService {
    private final PlacesRepository placesRepository;

    @Override
    public List<PlaceDto> findPlacesByPlaceName(String placeName) {
        return this.placesRepository.findByPlaceName(placeName)
                .stream().map(PlaceDto::from)
                .collect(Collectors.toList());
    }
}
