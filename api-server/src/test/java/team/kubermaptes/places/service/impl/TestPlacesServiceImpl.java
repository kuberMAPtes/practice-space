package team.kubermaptes.places.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.kubermaptes.places.service.PlacesService;

@SpringBootTest
class TestPlacesServiceImpl {

    @Autowired
    PlacesService placesService;

    @Test
    void test() {
        System.out.println(placesService.findPlacesByPlaceName("강남"));
    }
}