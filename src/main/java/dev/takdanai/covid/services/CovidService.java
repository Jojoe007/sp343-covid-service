package dev.takdanai.covid.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.takdanai.covid.entities.Covid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CovidService {

    @Value("${covid.api}")
    private String COVID_API;
    private final RestTemplate restTemplate;

    @Autowired
    public CovidService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final Function<List<String>, Function<Covid, Boolean>> filterByProvince =
            provinces -> covid -> provinces.contains(covid.getProvince());

    public List<Covid> findByProvince(List<String> provinces) {
        return requestCovidData().stream()
                .filter(covid -> provinces.isEmpty() || filterByProvince.apply(provinces).apply(covid))
                .collect(Collectors.toList());
    }

    private List<Covid> requestCovidData() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity(COVID_API, Object[].class);
        if (Objects.requireNonNull(response.getBody()).length != 0) {
            Object[] data = response.getBody();
            return mappingEntity(data, Covid.class);
        } else {
            return List.of();
        }
    }

    private <T> List<T> mappingEntity(Object[] objects, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();

        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, type))
                .collect(Collectors.toList());
    }

}
