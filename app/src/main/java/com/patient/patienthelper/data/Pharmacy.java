package com.patient.patienthelper.data;

public class Pharmacy {

    private String id;
    private String name;
    private double lat;
    private double lng;
    private String isOpen;
    private double rating;
    private String address;
    private String opening_hours;
    private String phone_number;

    public Pharmacy() {
    }

    public Pharmacy(String id, String name, String isOpen) {
        this.id = id;
        this.isOpen = isOpen;
        this.name = name;
    }

    public Pharmacy(String id, String name, double lat, double lng, String isOpen, double rating, String address, String opening_hours, String phone_number) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.isOpen = isOpen;
        this.rating = rating;
        this.address = address;
        this.opening_hours = opening_hours;
        this.phone_number = phone_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", isOpen='" + isOpen + '\'' +
                ", rating=" + rating +
                ", address='" + address + '\'' +
                ", opening_hours='" + opening_hours + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
