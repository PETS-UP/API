//package com.petsup.api.controllers;
//
//import com.petsup.api.service.GeocodingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class GeocodingController {
//
//    @Autowired
//    private GeocodingService geocodingService;
//
//    @GetMapping("/geocode")
//    public String reverseGeocode(@RequestParam("latitude") double latitude,
//                                 @RequestParam("longitude") double longitude) {
//        return geocodingService.reverseGeocode(latitude, longitude);
//    }
//}
