package team.kubermaptes.places.domain;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Place {
    private String roadAddress;
    private double x;
    private double y;
}
