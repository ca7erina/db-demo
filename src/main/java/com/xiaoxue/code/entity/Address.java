package com.xiaoxue.code.entity;

public class Address {
  String street;
  String city;
  String state;
  String postalCode;
  int id;

  /** Constructor for address. */
  public Address(
      final String street,
      final String city,
      final String state,
      final String postalCode,
      final int id) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
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
