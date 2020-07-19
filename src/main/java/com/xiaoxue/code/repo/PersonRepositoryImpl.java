package com.xiaoxue.code.repo;

import static com.xiaoxue.code.Constants.ADDRESS_ID_KEY;
import static com.xiaoxue.code.Constants.CITY_KEY;
import static com.xiaoxue.code.Constants.FIRSTNAME_KEY;
import static com.xiaoxue.code.Constants.ID_KEY;
import static com.xiaoxue.code.Constants.LASTNAME_KEY;
import static com.xiaoxue.code.Constants.PERSON_ID_KEY;
import static com.xiaoxue.code.Constants.POSTAL_CODE_KEY;
import static com.xiaoxue.code.Constants.STATE_KEY;
import static com.xiaoxue.code.Constants.STREET_KEY;
import static com.xiaoxue.code.repo.Query.ADD_ADDRESS_QUERY;
import static com.xiaoxue.code.repo.Query.ADD_PERSON_ADDRESS_QUERY;
import static com.xiaoxue.code.repo.Query.ADD_PERSON_QUERY;
import static com.xiaoxue.code.repo.Query.COUNT_PERSONS_QUERY;
import static com.xiaoxue.code.repo.Query.DELELE_PERSON_ADDRESS_QUERY;
import static com.xiaoxue.code.repo.Query.DELETE_ADDRESS_QUERY;
import static com.xiaoxue.code.repo.Query.DELETE_PERSON_QUERY;
import static com.xiaoxue.code.repo.Query.EDIT_ADDRESS_QUERY;
import static com.xiaoxue.code.repo.Query.EDIT_PERSON_QUERY;
import static com.xiaoxue.code.repo.Query.GET_PERSONS_ADDRESS_QUERY;

import com.xiaoxue.code.entity.Address;
import com.xiaoxue.code.entity.Person;
import com.xiaoxue.code.repo.mapper.PersonsResultSetExtractor;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

public class PersonRepositoryImpl implements PersonRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryImpl.class);
  JdbcTemplate jdbcTemplate;
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  TransactionTemplate transaction;

  /** Constructor. */
  @Inject
  public PersonRepositoryImpl(
      JdbcTemplate jdbcTemplate,
      NamedParameterJdbcTemplate namedParameterJdbcTemplate,
      TransactionTemplate transaction) {
    this.jdbcTemplate = jdbcTemplate;
    this.transaction = transaction;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public int addPerson(Person person) {
    String firstName = person.getFirstName();
    String lastName = person.getLastName();
    Objects.requireNonNull(firstName);
    Objects.requireNonNull(lastName);
    List<Address> addresses = person.getAddresses();
    if (addresses.size() == 0) {
      final MapSqlParameterSource params =
          new MapSqlParameterSource()
              .addValue(FIRSTNAME_KEY, firstName)
              .addValue(LASTNAME_KEY, lastName);
      final KeyHolder keyHolder = new GeneratedKeyHolder();
      namedParameterJdbcTemplate.update(ADD_PERSON_QUERY, params, keyHolder);
      return Objects.requireNonNull(keyHolder.getKey()).intValue();
    } else {
      // transaction add addresses and person
      final KeyHolder keyHolder = new GeneratedKeyHolder();
      return Objects.requireNonNull(
          transaction.execute(
              status -> {
                try {

                  final MapSqlParameterSource params =
                      new MapSqlParameterSource()
                          .addValue(FIRSTNAME_KEY, firstName)
                          .addValue(LASTNAME_KEY, lastName);

                  namedParameterJdbcTemplate.update(ADD_PERSON_QUERY, params, keyHolder);

                  int personId = keyHolder.getKey().intValue();
                  for (Address address : addresses) {
                    int addressId = addAddress(address);
                    addPersonAddress(personId, addressId);
                  }
                } catch (Exception e) {
                  LOGGER.error("Add Person failed. {}", e.getMessage());
                  status.setRollbackOnly();
                }

                return keyHolder.getKey().intValue();
              }));
    }
  }

  @Override
  public int editPerson(Person person) {
    String firstName = person.getFirstName();
    String lastName = person.getLastName();
    Integer id = person.getId();

    Objects.requireNonNull(firstName);
    Objects.requireNonNull(lastName);
    Objects.requireNonNull(id);

    final MapSqlParameterSource params =
        new MapSqlParameterSource()
            .addValue(FIRSTNAME_KEY, firstName)
            .addValue(LASTNAME_KEY, lastName)
            .addValue(ID_KEY, id);

    return namedParameterJdbcTemplate.update(EDIT_PERSON_QUERY, params);
  }

  @Override
  public int deletePerson(final int id) {
    final MapSqlParameterSource params = new MapSqlParameterSource().addValue(ID_KEY, id);
    return namedParameterJdbcTemplate.update(DELETE_PERSON_QUERY, params);
  }

  @Override
  public int countPersons() {
    return jdbcTemplate.queryForObject(COUNT_PERSONS_QUERY, Integer.class);
  }

  @Override
  public List<Person> getAllPersons() {
    return jdbcTemplate.query(GET_PERSONS_ADDRESS_QUERY, new PersonsResultSetExtractor());
  }

  @Override
  public int addAddress(Address address) {

    String street = address.getStreet();
    String city = address.getCity();
    String state = address.getState();
    String postalCode = address.getPostalCode();

    Objects.requireNonNull(street);
    Objects.requireNonNull(city);
    Objects.requireNonNull(state);
    Objects.requireNonNull(postalCode);

    final MapSqlParameterSource params =
        new MapSqlParameterSource()
            .addValue(STREET_KEY, street)
            .addValue(CITY_KEY, city)
            .addValue(STATE_KEY, state)
            .addValue(POSTAL_CODE_KEY, postalCode);

    final KeyHolder keyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(ADD_ADDRESS_QUERY, params, keyHolder);
    return Objects.requireNonNull(keyHolder.getKey()).intValue();
  }

  @Override
  public int editAddress(Address address) {

    String street = address.getStreet();
    String city = address.getCity();
    String state = address.getState();
    String postalCode = address.getPostalCode();
    Integer id = address.getId();

    Objects.requireNonNull(street);
    Objects.requireNonNull(city);
    Objects.requireNonNull(state);
    Objects.requireNonNull(postalCode);
    Objects.requireNonNull(id);

    final MapSqlParameterSource params =
        new MapSqlParameterSource()
            .addValue(STREET_KEY, street)
            .addValue(CITY_KEY, city)
            .addValue(STATE_KEY, state)
            .addValue(POSTAL_CODE_KEY, postalCode)
            .addValue(ID_KEY, id);
    return namedParameterJdbcTemplate.update(EDIT_ADDRESS_QUERY, params);
  }

  @Override
  public int deleteAddress(final int id) {
    final MapSqlParameterSource params = new MapSqlParameterSource().addValue(ID_KEY, id);
    return namedParameterJdbcTemplate.update(DELETE_ADDRESS_QUERY, params);
  }

  @Override
  public int addPersonAddress(int personId, int addressId) {

    final MapSqlParameterSource params =
        new MapSqlParameterSource()
            .addValue(PERSON_ID_KEY, personId)
            .addValue(ADDRESS_ID_KEY, addressId);
    return namedParameterJdbcTemplate.update(ADD_PERSON_ADDRESS_QUERY, params);
  }

  @Override
  public int deletePersonAddress(int personId, int addressId) {
    final MapSqlParameterSource params =
        new MapSqlParameterSource()
            .addValue(PERSON_ID_KEY, personId)
            .addValue(ADDRESS_ID_KEY, addressId);
    return namedParameterJdbcTemplate.update(DELELE_PERSON_ADDRESS_QUERY, params);
  }
}
