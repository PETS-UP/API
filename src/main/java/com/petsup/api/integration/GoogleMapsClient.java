package com.petsup.api.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "distance-matrix-api", url = "https://maps.googleapis.com/maps/api/distancematrix")
public interface GoogleMapsClient {

//    @GetMapping("/json?origins={origins}&destinations={destinations}&key={apiKey}")
//    List
}
