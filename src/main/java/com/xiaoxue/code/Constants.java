package com.xiaoxue.code;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constants {

  private Constants() {}

  public static final String FIRSTNAME_KEY = "firstName";
  public static final String LASTNAME_KEY = "lastName";

  public static final String ID_KEY = "id";
  public static final String PERSON_ID_KEY = "personId";
  public static final String ADDRESS_ID_KEY = "addressId";

  public static final String STREET_KEY = "street";
  public static final String CITY_KEY = "city";
  public static final String STATE_KEY = "state";
  public static final String POSTAL_CODE_KEY = "postalCode";

  public static final String ROW_ID_KEY = "rowid";

  public static final ObjectMapper MAPPER = new ObjectMapper().disable(WRITE_DATES_AS_TIMESTAMPS);
}
