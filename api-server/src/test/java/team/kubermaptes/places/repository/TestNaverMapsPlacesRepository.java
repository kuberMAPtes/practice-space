package team.kubermaptes.places.repository;

import static org.assertj.core.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import team.kubermaptes.places.domain.Place;
import team.kubermaptes.places.exception.PlaceNotFoundException;
import team.kubermaptes.places.repository.externapi.NaverMapsPlacesRepository;

import java.util.List;

@Slf4j
@SpringBootTest
class TestNaverMapsPlacesRepository {

    @Autowired
    private PlacesRepository placesRepository;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public PlacesRepository placesRepository(@Value("${naver-cloud.maps.access-key}") String accessKey, @Value("${naver-cloud.maps.secret-key}") String secretKey) {
            return new NaverMapsPlacesRepository(accessKey, secretKey);
        }
    }

    @Test
    void test() {
        List<Place> result = this.placesRepository.findByPlaceName("강남");
        result.forEach((r) -> {
            System.out.printf("roadAddress=%s\n", r.getRoadAddress());
            System.out.printf("x=%f\n", r.getX());
            System.out.printf("x=%f\n\n", r.getY());
        });
    }

    @Test
    void reverse() throws PlaceNotFoundException {
        Place byLatLng1 = this.placesRepository.findByLatLng(37.593051, 127.051061);
        Place byLatLng2 = this.placesRepository.findByLatLng(37.592989, 127.050959);
        Place byLatLng3 = this.placesRepository.findByLatLng(37.592934, 127.050971);

        assertThat(byLatLng1).isEqualTo(byLatLng2);
        assertThat(byLatLng2).isEqualTo(byLatLng3);

        log.info("byLatLng1={}", byLatLng1);
        log.info("byLatLng2={}", byLatLng2);
        log.info("byLatLng3={}", byLatLng3);

        Place byLatLng4 = this.placesRepository.findByLatLng(37.592983, 127.050849);

        assertThat(byLatLng4).isNotEqualTo(byLatLng1);
        log.info("byLatLng4={}", byLatLng4);
    }

}