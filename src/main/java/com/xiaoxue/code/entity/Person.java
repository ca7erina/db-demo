package com.xiaoxue.code.entity;

import java.util.ArrayList;
import java.util.List;

public class Person {
  private int id;
  private final String firstName;
  private final String lastName;
  private List<Address> addresses;

  /** Constructor for Person. */
  public Person(final int id, final String firstName, final String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    addresses = new ArrayList<>();
  }

  public Person(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Person(
      final int id, final String firstName, final String lastName, List<Address> addresses) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.addresses = addresses;
  }

  public int getId() {
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
        + '}';
  }
}
