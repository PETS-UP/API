package com.petsup.api.util;

public class DetalhesEndereco {
    private String formattedAddress;
    private String neighborhood;
    private String city;

    public DetalhesEndereco(String formattedAddress, String neighborhood, String city) {
        this.formattedAddress = formattedAddress;
        this.neighborhood = neighborhood;
        this.city = city;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
