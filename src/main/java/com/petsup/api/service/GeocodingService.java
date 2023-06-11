package com.petsup.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
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
//        GeoApiContext context = new GeoApiContext.Builder()
//                .apiKey(googleMapsApiKey)
//                .build();
//
//        try {
//            GeocodingResult[] result = GeocodingApi.reverseGeocode(context, new com.google.maps.model.LatLng
//                                                                  (latitude, longitude)).await();
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;

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
}
