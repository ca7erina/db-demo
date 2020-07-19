package com.xiaoxue.code.repo;

import static com.xiaoxue.code.Constants.FIRSTNAME_KEY;
import static com.xiaoxue.code.Constants.LASTNAME_KEY;
import static com.xiaoxue.code.Constants.MAPPER;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.xiaoxue.code.entity.Address;
import com.xiaoxue.code.entity.Person;

import java.io.IOException;
import java.io.InputStream;
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

  static Person PERSON1 = new Person(null, "John", "Doe");
  static Person PERSON2 = new Person(null, "Mary", "Lou");
  static Person PERSON2_UPDATE = new Person(2, "Marry2", "Lou2");
  static Person PERSON3 = new Person(null, "Homeless", "Lou");

  static Address ADDRESS1 = new Address(null, "Main Street", "Dublin", "Dublin", "D18");
  static Address ADDRESS2 = new Address(null, "3rd Main Street", "Dublin", "Dublin Co", "D1");
  static Address ADDRESS2_UPDATE = new Address(1, "2 Main Street", "Dublin", "Dublin Co", "D2");
  static PersonRepository personRepository;

  @BeforeClass
  public static void prepare() {
    personRepository =
        new PersonRepositoryImpl(jdbcTemplate, namedParameterJdbcTemplate, transactionTemplate);
  }

  @Test
  public void test01AddPersonAndCount() {

    assertEquals(PERSON_1_ID, personRepository.addPerson(PERSON1));
    assertEquals(PERSON_2_ID, personRepository.addPerson(PERSON2));
    int totalNumberOfPersons = 2;
    assertEquals(totalNumberOfPersons, personRepository.countPersons());
  }

  @Test
  public void test02EditPerson() {
    int affectedRowNumber = 1;
    assertEquals(affectedRowNumber, personRepository.editPerson(PERSON2_UPDATE));
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
    assertEquals(PERSON_2_ID, (int) person.getId());
  }

  @Test
  public void test05AddAddress() {
    assertEquals(ADDRESS_1_ID, personRepository.addAddress(ADDRESS1));
    assertEquals(ADDRESS_2_ID, personRepository.addAddress(ADDRESS2));
  }

  @Test
  public void test06EditAddress() {
    int affectedRowNumber = 1;
    assertEquals(affectedRowNumber, personRepository.editAddress(ADDRESS2_UPDATE));
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
    personRepository.addPerson(PERSON3);

    List<Person> persons = personRepository.getAllPersons();
    assertEquals(2, persons.size());

    Person mary = persons.get(0);
    assertEquals("Marry2", mary.getFirstName());
    assertEquals("Lou2", mary.getLastName());
    assertEquals(PERSON_2_ID, (int) mary.getId());
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

  @Test
  public void test11AddPersonFromJsonFile() throws IOException {
    JsonNode personJson = getJson("person.json");
    Person person = MAPPER.treeToValue(personJson, Person.class);

    personRepository.addPerson(person);
    List<Person> persons = personRepository.getAllPersons();
    assertEquals(3, persons.size());

    Person newPerson = persons.get(2);
    JsonNode newPersonFromDB = MAPPER.readTree(MAPPER.writeValueAsString(newPerson));

    assertEquals(personJson.get(FIRSTNAME_KEY), newPersonFromDB.get(FIRSTNAME_KEY));
    assertEquals(personJson.get(LASTNAME_KEY), newPersonFromDB.get(LASTNAME_KEY));
    assertEquals(4, newPersonFromDB.get("id").intValue());

    Address address = newPerson.getAddresses().get(0);
    // same as jason file content.
    assertEquals("1st Main Street", address.getStreet());
    assertEquals("Dublin", address.getCity());
    assertEquals("Co Dublin", address.getState());
    assertEquals("D2", address.getPostalCode());
  }

  public JsonNode getJson(String path) throws IOException {
    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    return MAPPER.readTree(stream);
  }
}
