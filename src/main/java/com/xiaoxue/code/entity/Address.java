package com.xiaoxue.code.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
  String street;
  String city;
  String state;
  String postalCode;
  Integer id;

  /** Constructor for address. */
  //  public Address(
  //      final String street,
  //      final String city,
  //      final String state,
  //      final String postalCode,
  //      final Integer id) {
  //    this.street = street;
  //    this.city = city;
  //    this.state = state;
  //    this.postalCode = postalCode;
  //    this.id = id;
  //  }

  @JsonCreator
  public Address(
      @JsonProperty("id") Integer id,
      @JsonProperty("street") String street,
      @JsonProperty("city") String city,
      @JsonProperty("state") String state,
      @JsonProperty("postalCode") String postalCode) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public Integer getId() {
    return id;
  }

  public void setStreet(final String street) {
    this.street = street;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public void setState(final String state) {
    this.state = state;
  }

  public void setPostalCode(final String postalCode) {
    this.postalCode = postalCode;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Address{"
        + "street='"
        + street
        + '\''
        + ", city='"
        + city
        + '\''
        + ", state='"
        + state
        + '\''
        + ", postalCode='"
        + postalCode
        + '\''
        + ", id="
        + id
        + '}';
  }
}
