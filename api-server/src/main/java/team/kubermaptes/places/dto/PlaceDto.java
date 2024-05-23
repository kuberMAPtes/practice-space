package team.kubermaptes.places.dto;

import lombok.*;
import team.kubermaptes.places.domain.Place;

@Builder
@Getter
@Setter
@ToString
public class PlaceDto {
    private String roadAddress;
    private double x;
    private double y;

    public static PlaceDto from(Place place) {
        return PlaceDto.builder()
                .roadAddress(place.getRoadAddress())
                .x(place.getX())
                .y(place.getY())
                .build();
    }
}
