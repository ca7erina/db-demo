package com.xiaoxue.code.repo.mapper;

import static com.xiaoxue.code.Constants.ADDRESS_ID_KEY;
import static com.xiaoxue.code.Constants.CITY_KEY;
import static com.xiaoxue.code.Constants.FIRSTNAME_KEY;
import static com.xiaoxue.code.Constants.LASTNAME_KEY;
import static com.xiaoxue.code.Constants.PERSON_ID_KEY;
import static com.xiaoxue.code.Constants.POSTAL_CODE_KEY;
import static com.xiaoxue.code.Constants.STATE_KEY;
import static com.xiaoxue.code.Constants.STREET_KEY;

import com.xiaoxue.code.entity.Address;
import com.xiaoxue.code.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;

public class PersonsResultSetExtractor implements ResultSetExtractor<List<Person>> {

  @Override
  public List<Person> extractData(ResultSet rs) throws SQLException {

    Map<Integer, Person> persons = new HashMap<>();
    while (rs.next()) {
      int personId = rs.getInt(PERSON_ID_KEY);
      String firstName = rs.getString(FIRSTNAME_KEY);
      String lastName = rs.getString(LASTNAME_KEY);
      int addressId = rs.getInt(ADDRESS_ID_KEY);
      if (rs.wasNull()) { // person has no address
        if (!persons.containsKey(personId)) {
          persons.put(personId, new Person(personId, firstName, lastName));
        }
      } else {
        // person has address
        String street = rs.getString(STREET_KEY);
        String city = rs.getString(CITY_KEY);
        String state = rs.getString(STATE_KEY);
        String postalCode = rs.getString(POSTAL_CODE_KEY);

        Address address = new Address(addressId,street, city, state, postalCode);

        if (persons.containsKey(personId)) {
          persons.get(personId).getAddresses().add(address);
        } else {

          Person person = new Person(personId, firstName, lastName);
          person.getAddresses().add(address);
          persons.put(personId, person);
        }
      }
    }

    return new ArrayList<>(persons.values());
  }
}
