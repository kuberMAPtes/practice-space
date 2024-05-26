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
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import team.kubermaptes.places.domain.Place;
import team.kubermaptes.places.exception.PlaceNotFoundException;
import team.kubermaptes.places.repository.PlacesRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class NaverMapsPlacesRepository implements PlacesRepository {
    private static final String GEOCODE_ACCESS_KEY_HEADER = "X-NCP-APIGW-API-KEY-ID";
    private static final String GEOCODE_SECRET_KEY_HEADER = "X-NCP-APIGW-API-KEY";
    private static final String NAVER_CLOUD_GEOCODE_API_URL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
    private static final String NAVER_CLOUD_REVERSE_GEOCODE_API_URL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc";

    private final String accessKey;
    private final String secretKey;
    private final RestTemplate restTemplate;

    private static final String QUERY_PARAMETER_KEY_QUERY = "query";

    public NaverMapsPlacesRepository(@Value("${naver-cloud.maps.access-key}") String accessKey, @Value("${naver-cloud.maps.secret-key}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<Place> findByPlaceName(String placeName) {
        try {

            String url = UriComponentsBuilder.fromHttpUrl(NAVER_CLOUD_GEOCODE_API_URL)
                    .queryParam(QUERY_PARAMETER_KEY_QUERY, placeName).encode().toUriString();
            log.debug("request url={}", url);

            RequestEntity<Void> requestEntity = RequestEntity.get(new URI(url))
                    .headers(getCommonHeaders()).build();

            ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);
            JSONObject jsonObject = new JSONObject(responseEntity.getBody());
            JSONArray addresses = jsonObject.getJSONArray("addresses");
            List<Place> places = new ArrayList<>();
            for (int i = 0; i < addresses.length(); i++) {
                JSONObject jsonObj = (JSONObject) addresses.get(i);
                places.add(new Place(jsonObj.getString("roadAddress"),
                        Double.parseDouble(jsonObj.getString("x")),
                        Double.parseDouble(jsonObj.getString("y"))));
            }
            return places;
        } catch (URISyntaxException e) {
            throw new InvalidDataAccessResourceUsageException("URI syntax error for Naver Maps", e); // TODO
        }
    }

    private static final String QUERY_PARAMETER_KEY_COORDS = "coords";
    private static final String QUERY_PARAMETER_KEY_OUTPUT = "output";
    private static final String QUERY_PARAMETER_KEY_ORDERS = "orders";
    private static final String RESPONSE_TYPE = "json";
    private static final String ORDERS = "roadaddr";
    private static final String LAT_LNG_DELIMITER = ",";

    @Override
    public Place findByLatLng(double latitude, double longitude) throws PlaceNotFoundException {
        try {
            HttpHeaders headers = getCommonHeaders();

            String url = UriComponentsBuilder.fromHttpUrl(NAVER_CLOUD_REVERSE_GEOCODE_API_URL)
                    .queryParam(QUERY_PARAMETER_KEY_COORDS, String.format("%f%s%f", longitude, LAT_LNG_DELIMITER, latitude))
                    .queryParam(QUERY_PARAMETER_KEY_OUTPUT, RESPONSE_TYPE)
                    .queryParam(QUERY_PARAMETER_KEY_ORDERS, ORDERS)
                    .encode().toUriString();
            log.debug("request url={}", url);

            RequestEntity<Void> requestEntity = RequestEntity.get(new URI(url))
                    .headers(headers).build();

            ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);
            JSONObject root = new JSONObject(responseEntity.getBody());
            System.out.println(root);
            JSONArray results = root.getJSONArray("results");
            if (results.length() < 1) {
                throw new PlaceNotFoundException("다음 위치 좌표에 해당하는 건물이 없습니다=" + latitude + "," + longitude);
            }
            JSONObject result = results.getJSONObject(0);
            JSONObject land = result.getJSONObject("land");
            String name = land.getString("name");
            String number = land.getString("number1");
            return this.findByPlaceName(name + " " + number).get(0);
        } catch (URISyntaxException e) {
            throw new InvalidDataAccessResourceUsageException("URI syntax error for Naver Maps", e); // TODO
        }
    }

    private HttpHeaders getCommonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(GEOCODE_ACCESS_KEY_HEADER, this.accessKey);
        headers.add(GEOCODE_SECRET_KEY_HEADER, this.secretKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
