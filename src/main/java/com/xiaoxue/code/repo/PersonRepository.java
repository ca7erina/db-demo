package com.xiaoxue.code.repo;

import com.xiaoxue.code.entity.Address;
import com.xiaoxue.code.entity.Person;

import java.util.List;

public interface PersonRepository {

  int addPerson(Person person);

  int editPerson(Person person);

  int deletePerson(int id);

  int countPersons();

  List<Person> getAllPersons();

  int addAddress(Address address);

  int editAddress(Address address);

  int deleteAddress(int id);

  int addPersonAddress(int personId, int addressId);

  int deletePersonAddress(int personId, int addressId);
}
