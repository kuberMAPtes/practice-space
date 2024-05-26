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

    @Override
    public boolean equals(Object obj) {
        if (isObjNotInstanceOfThis(obj)) {
            return false;
        }
        Place placeToCompare = (Place)obj;
        return this.x == placeToCompare.x
                && this.y == placeToCompare.y;
    }

    private boolean isObjNotInstanceOfThis(Object obj) {
        return !(obj instanceof Place);
    }
}
