package com.petsup.api.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/distance")
public class GoogleMapsDistanceController {
    @Value("${google.api.key}")
    private String apiKey;

    @GetMapping
    public ResponseEntity<DistanceMatrixResponse> getDistance(
            @RequestParam String origin,
            @RequestParam String destination) {
        String apiUrl = "https://maps.googleapis.com/maps/api/distancematrix/json";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("origins", origin)
                .queryParam("destinations", destination)
                .queryParam("key", apiKey);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DistanceMatrixResponse> response = restTemplate.getForEntity(builder.toUriString(), DistanceMatrixResponse.class);

        return response;
    }
}

class DistanceMatrixResponse {
    @JsonProperty("rows")
    private DistanceMatrixRow[] rows;

    public DistanceMatrixRow[] getRows() {
        return rows;
    }

    public void setRows(DistanceMatrixRow[] rows) {
        this.rows = rows;
    }

    static class DistanceMatrixRow {
        @JsonProperty("elements")
        private DistanceMatrixElement[] elements;

        public DistanceMatrixElement[] getElements() {
            return elements;
        }

        public void setElements(DistanceMatrixElement[] elements) {
            this.elements = elements;
        }
    }

    static class DistanceMatrixElement {
        @JsonProperty("distance")
        private Distance distance;

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }
    }

    static class Distance {
        @JsonProperty("text")
        private String text;

        @JsonProperty("value")
        private int value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}

