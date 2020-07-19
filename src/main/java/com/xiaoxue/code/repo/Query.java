package com.xiaoxue.code.repo;

public class Query {
  private Query() {}

  public static final String DROP_PERSON_TABLE_QUERY = "drop table if exists person;";
  public static final String CREATE_PERSON_TABLE_QUERY =
      "create table if not exists person ( firstName string not null, lastName string not null, addressId integer,FOREIGN KEY(addressId) REFERENCES address(rowid))";
  public static final String ADD_PERSON_QUERY =
      " INSERT INTO person(firstname, lastname) VALUES(:firstName, :lastName)";
  public static final String EDIT_PERSON_QUERY =
      " UPDATE person SET firstName=:firstName, lastName=:lastName WHERE rowid=:id";

  public static final String DELELE_PERSON_QUERY = "delete from person where rowid=:id";
  public static final String GET_PERSONS_QUERY = "select rowid,firstName,lastName from person";
  public static final String COUNT_PERSONS_QUERY = "select count(*) from person";

  public static final String DROP_ADDRESS_TABLE_QUERY = "drop table if exists address;";
  public static final String CREATE_ADDRESS_TABLE_QUERY =
      "create table if not exists address (street text NOT NULL,city text NOT NULL,state text NOT NULL,postalCode text NOT NULL);";
  public static final String ADD_ADDRESS_QUERY =
      "INSERT INTO address(street, city, state, postalCode) VALUES(:street, :city,:state,:postalCode)";
  public static final String EDIT_ADDRESS_QUERY =
      "UPDATE address SET street=:street, city=:city, state=:state, postalCode=:postalCode WHERE rowid=:id";
  public static final String DELELE_ADDRESS_QUERY = "delete from address where rowid=:id";


  public static final String DROP_PERSON_ADDRESS_TABLE_QUERY = "drop table if exists personAddress;";
  public static final String CREATE_PERSON_ADDRESS_TABLE_QUERY =
      "create table if not exists personAddress\n"
          + "(\n"
          + "    personId  integer,\n"
          + "    addressId integer,\n"
          + "    FOREIGN KEY (personId) REFERENCES person (rowid),\n"
          + "    FOREIGN KEY (addressId) REFERENCES address (rowid)\n"
          + ")";

  public static final String ADD_PERSON_ADDRESS_QUERY =
      "INSERT INTO personAddress(personId, addressId) VALUES(:personId,:addressId);";
  public static final String DELELE_PERSON_ADDRESS_QUERY =
      "delete from personAddress where personId=:personId and addressId=:addressId";
}
