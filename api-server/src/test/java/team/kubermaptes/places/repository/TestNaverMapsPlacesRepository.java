package team.kubermaptes.places.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import team.kubermaptes.places.domain.Place;
import team.kubermaptes.places.repository.externapi.NaverMapsPlacesRepository;

import java.util.List;

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
}