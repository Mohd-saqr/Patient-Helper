package com.patient.patienthelper.data;

public class Pharmacy {

    private String name;
    private double lat;
    private double lng;
    private String isOpen;
    private double rating;

    public Pharmacy() {
    }

    public Pharmacy(String name, double lat, double lng, String isOpen, double rating) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.isOpen = isOpen;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setOpen(String open) {
        isOpen = open;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", isOpen=" + isOpen +
                ", rating=" + rating +
                '}';
    }
}
