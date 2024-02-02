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

    public List<Covid> findByProvince(List<String> provinces) {
        var mappingResponseToCovid = mappingResponseToEntity(Covid.class);
        var filterByProvinces = filterBy.apply(provinces);

        return filterByProvinces.compose(mappingResponseToCovid).apply(requestCovidData().getBody());
    }

    private ResponseEntity<Object[]> requestCovidData() {
        return restTemplate.getForEntity(COVID_API, Object[].class);
    }

    private <T> Function<Object[], List<T>> mappingResponseToEntity(Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        return objects -> Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, type))
                .collect(Collectors.toList());

    }

    private final Function<List<String>, Function<List<Covid>, List<Covid>>> filterBy =
            filterList -> dataList -> dataList.stream()
                    .filter(data -> filterList.isEmpty() || filterList.contains(data.getProvince()))
                    .collect(Collectors.toList());
}
