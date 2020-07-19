package com.xiaoxue.code.repo.mapper;

import static com.xiaoxue.code.Constants.FIRSTNAME_KEY;
import static com.xiaoxue.code.Constants.LASTNAME_KEY;
import static com.xiaoxue.code.Constants.ROW_ID_KEY;

import com.xiaoxue.code.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper<Person> {
  @Override
  public Person mapRow(final ResultSet resultSet, final int i) throws SQLException {
    String fristName = resultSet.getString(FIRSTNAME_KEY);
    String lastName = resultSet.getString(LASTNAME_KEY);
    int id = resultSet.getInt(ROW_ID_KEY);

    return  new Person(id, fristName, lastName);
  }
}
