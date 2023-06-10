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

@Service
public class GeocodingService {

    @Value("${google.maps.apiKey}")
    private String googleMapsApiKey;

    public GeocodingResult[] reverseGeocode(double latitude, double longitude) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(googleMapsApiKey)
                .build();

        try {
            GeocodingResult[] result = GeocodingApi.reverseGeocode(context, new com.google.maps.model.LatLng
                                                                  (latitude, longitude)).await();
            return result;
        } catch (Exception e) {
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
