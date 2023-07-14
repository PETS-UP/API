package com.petsup.api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.petsup.api.dto.DetalhesEnderecoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class GeocodingService {

    @Value("${google.maps.apiKey}")
    private String googleMapsApiKey;

    public String reverseGeocode(double latitude, double longitude) {

        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                latitude + "," + longitude + "&key=" + googleMapsApiKey;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
                String responseBody = scanner.hasNext() ? scanner.next() : "";

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode result = objectMapper.readTree(responseBody);
                return objectMapper.writeValueAsString(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String extrairBairro(GeocodingResult result){
        for (AddressComponent addressComponent : result.addressComponents) {
            for (AddressComponentType type : addressComponent.types) {
                if (type == AddressComponentType.NEIGHBORHOOD) {
                    return addressComponent.longName;
                }
            }
        }

        return null; // Caso não encontre o bairro
    }

    public DetalhesEnderecoDto extrairBairroCidade(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode resultNode = objectMapper.readTree(jsonString);

            if (resultNode.has("results") && resultNode.get("results").isArray()) {
                JsonNode resultsArray = resultNode.get("results");

                if (resultsArray.size() > 0) {
                    JsonNode firstResult = resultsArray.get(0);
                    String formattedAddress = firstResult.path("formatted_address").asText();
                    JsonNode addressComponentsNode = firstResult.path("address_components");

                    String neighborhood = null;
                    String city = null;

                    for (JsonNode component : addressComponentsNode) {
                        JsonNode types = component.path("types");

                        if (types.isArray()) {
                            for (JsonNode type : types) {
                                String typeName = type.asText();
                                if (typeName.equals("sublocality_level_1")) {
                                    neighborhood = component.path("long_name").asText();
                                } else if (typeName.equals("administrative_area_level_2")) {
                                    city = component.path("long_name").asText();
                                }
                            }
                        }
                    }

                    return new DetalhesEnderecoDto(formattedAddress, neighborhood, city);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
