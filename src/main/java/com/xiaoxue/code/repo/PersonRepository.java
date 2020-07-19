package com.xiaoxue.code.repo;

import com.xiaoxue.code.entity.Person;

import java.util.List;

public interface PersonRepository {

  int addPerson(final String firstName, final String lastName);

  int editPerson(int id, String firstName, String lastName);

  int deletePerson(int id);

  int countPersons();

  List<Person> getAllPersons();

  int addAddress(String street, String city, String state, String postalCode);

  int editAddress(int id, String street, String city, String state, String postalCode);

  int deleteAddress(int id);

  int addPersonAddress(int personId, int addressId);

  int deletePersonAddress(int personId, int addressId);


}
