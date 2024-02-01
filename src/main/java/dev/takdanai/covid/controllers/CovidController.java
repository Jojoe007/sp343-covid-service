package dev.takdanai.covid.controllers;

import dev.takdanai.covid.services.CovidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CovidController {

    private final CovidService covidService;

    public CovidController(CovidService covidService) {
        this.covidService = covidService;
    }

    @GetMapping
    public ResponseEntity<?> getByProvince(
            @RequestParam(required = false, defaultValue = "") String province
    ) {
        List<String> provinces = province.isBlank() ? List.of() : List.of(province.split(","));

        return ResponseEntity.ok(covidService.findByProvince(provinces));
    }

    @GetMapping("/{province}")
    public ResponseEntity<?> getByProvincePath(@PathVariable String province) {
        return ResponseEntity.ok(covidService.findByProvince(List.of(province)));
    }

}
