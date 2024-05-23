package team.kubermaptes.places.repository.externapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import team.kubermaptes.places.domain.Place;
import team.kubermaptes.places.repository.PlacesRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class NaverMapsPlacesRepository implements PlacesRepository {
    private static final String GEOCODE_ACCESS_KEY_HEADER = "X-NCP-APIGW-API-KEY-ID";
    private static final String GEOCODE_SECRET_KEY_HEADER = "X-NCP-APIGW-API-KEY";
    private static final String NAVER_CLOUD_GEOCODE_API_URL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
    private static final String QUERY_PARAMETER_KEY_QUERY = "query";

    private final String accessKey;
    private final String secretKey;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public NaverMapsPlacesRepository(@Value("${naver-cloud.maps.access-key}") String accessKey, @Value("${naver-cloud.maps.secret-key}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Place> findByPlaceName(String placeName) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(GEOCODE_ACCESS_KEY_HEADER, this.accessKey);
            headers.add(GEOCODE_SECRET_KEY_HEADER, this.secretKey);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            String url = UriComponentsBuilder.fromHttpUrl(NAVER_CLOUD_GEOCODE_API_URL)
                    .queryParam(QUERY_PARAMETER_KEY_QUERY, placeName).encode().toUriString();
            log.debug("request url={}", url);

            RequestEntity<Void> requestEntity = RequestEntity.get(new URI(url))
                    .headers(headers).build();

            ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);
            JSONObject jsonObject = new JSONObject(responseEntity.getBody());
            JSONArray addresses = jsonObject.getJSONArray("addresses");
            List<Place> places = new ArrayList<>();
            for (int i = 0; i < addresses.length(); i++) {
                JSONObject jsonObj = (JSONObject)addresses.get(i);
                places.add(new Place(jsonObj.getString("roadAddress"),
                        Double.parseDouble(jsonObj.getString("x")),
                        Double.parseDouble(jsonObj.getString("y"))));
            }
            return places;
        } catch (URISyntaxException e) {
            throw new InvalidDataAccessResourceUsageException("URI syntax error for Naver Maps", e); // TODO
        }
    }
}
