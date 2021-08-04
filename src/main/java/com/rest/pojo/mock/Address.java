package com.rest.pojo.mock;

public class Address {

    public Address(Geo geo, String street,String suite, String city, String zipcode){
        this.geo = geo;
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setAddress(Geo geo) {
        this.geo = geo;
    }

    private Geo geo;
    private String zipcode;
    private String street;
    private String suite;
    private String city;

}
