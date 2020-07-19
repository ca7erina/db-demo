package com.xiaoxue.code.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
  private Integer id;
  private final String firstName;
  private final String lastName;
  private List<Address> addresses;

  /** Constructor for Person. */
  public Person(Integer id, final String firstName, final String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    addresses = new ArrayList<>();
  }

  /** Constructor. */
  @JsonCreator
  public Person(
      @JsonProperty("id") Integer id,
      @JsonProperty("firstName") final String firstName,
      @JsonProperty("lastName") final String lastName,
      @JsonProperty("addresses") List<Address> addresses) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.addresses = addresses;
  }

  public Integer getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public void setAddresses(final List<Address> addresses) {
    this.addresses = addresses;
  }

  @Override
  public String toString() {
    return "Person{"
        + "id="
        + id
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", addresses="
        + addresses
        + '}';
  }
}
