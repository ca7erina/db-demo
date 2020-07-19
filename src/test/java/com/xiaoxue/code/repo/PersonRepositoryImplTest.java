package com.xiaoxue.code.repo;

import static org.junit.Assert.assertEquals;

import com.xiaoxue.code.entity.Address;
import com.xiaoxue.code.entity.Person;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonRepositoryImplTest extends SqliteTest {

  static int PERSON_1_ID = 1;
  static int PERSON_2_ID = 2;
  static int ADDRESS_1_ID = 1;
  static int ADDRESS_2_ID = 2;
  static PersonRepository personRepository;

  @BeforeClass
  public static void prepare() {
    personRepository = new PersonRepositoryImpl(jdbcTemplate, namedParameterJdbcTemplate);
  }

  @Test
  public void test01AddPersonAndCount() {

    assertEquals(PERSON_1_ID, personRepository.addPerson("John", "Doe"));
    assertEquals(PERSON_2_ID, personRepository.addPerson("Mary", "Lou"));
    int totalNumberOfPersons = 2;
    assertEquals(totalNumberOfPersons, personRepository.countPersons());
  }

  @Test
  public void test02EditPerson() {
    int affectedRowNumber = 1;
    assertEquals(affectedRowNumber, personRepository.editPerson(PERSON_2_ID, "Marry2", "Lou2"));
  }

  @Test
  public void test03DeletePersonAndCount() {
    int affectedRowNumber = 1;
    assertEquals(affectedRowNumber, personRepository.deletePerson(PERSON_1_ID));
    int currentPersonsCount = 1;
    assertEquals(currentPersonsCount, personRepository.countPersons());
  }

  @Test
  public void test04GetAllPersons() {
    List<Person> persons = personRepository.getAllPersons();
    assertEquals(1, persons.size());
    Person person = persons.get(0);

    assertEquals("Marry2", person.getFirstName());
    assertEquals("Lou2", person.getLastName());
    assertEquals(PERSON_2_ID, person.getId());
  }

  @Test
  public void test05AddAddress() {
    assertEquals(
        ADDRESS_1_ID, personRepository.addAddress("Main Street", "Dublin", "Dublin", "D18"));
    assertEquals(
        ADDRESS_2_ID, personRepository.addAddress("3rd Main Street", "Dublin", "Dublin Co", "D1"));
  }

  @Test
  public void test06EditAddress() {
    int affectedRowNumber = 1;
    assertEquals(
        affectedRowNumber,
        personRepository.editAddress(1, "2 Main Street", "Dublin", "Dublin Co", "D2"));
  }

  @Test
  public void test07DeleteAddress() {
    int affectedRowNumber = 1;
    assertEquals(affectedRowNumber, personRepository.deleteAddress(ADDRESS_1_ID));
  }

  @Test
  public void test08AddPersonAddress() {
    int affectedRowNumber = 1;
    assertEquals(affectedRowNumber, personRepository.addPersonAddress(2, 2));
  }

  @Test
  public void test09GetAllPersonsWithAddresses() {
    personRepository.addPerson("Homeless", "Lou");

    List<Person> persons = personRepository.getAllPersons();
    assertEquals(2, persons.size());

    Person mary = persons.get(0);
    assertEquals("Marry2", mary.getFirstName());
    assertEquals("Lou2", mary.getLastName());
    assertEquals(PERSON_2_ID, mary.getId());
    Address address = mary.getAddresses().get(0);
    assertEquals("3rd Main Street", address.getStreet());
    assertEquals("Dublin", address.getCity());
    assertEquals("Dublin Co", address.getState());
    assertEquals("D1", address.getPostalCode());

    Person homeless = persons.get(1);
    assertEquals("Homeless", homeless.getFirstName());
    assertEquals("Lou", homeless.getLastName());
    assertEquals(0, homeless.getAddresses().size());
  }
}
