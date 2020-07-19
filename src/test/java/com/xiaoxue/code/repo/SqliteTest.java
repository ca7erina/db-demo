package com.xiaoxue.code.repo;

import static com.xiaoxue.code.repo.Query.CREATE_ADDRESS_TABLE_QUERY;
import static com.xiaoxue.code.repo.Query.CREATE_PERSON_ADDRESS_TABLE_QUERY;
import static com.xiaoxue.code.repo.Query.CREATE_PERSON_TABLE_QUERY;
import static com.xiaoxue.code.repo.Query.DROP_ADDRESS_TABLE_QUERY;
import static com.xiaoxue.code.repo.Query.DROP_PERSON_ADDRESS_TABLE_QUERY;
import static com.xiaoxue.code.repo.Query.DROP_PERSON_TABLE_QUERY;

import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.sqlite.SQLiteDataSource;

public class SqliteTest {

  static JdbcTemplate jdbcTemplate;
  static NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @BeforeClass
  public static void createDB() {
    SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setUrl("jdbc:sqlite::memory:testDb");
    jdbcTemplate = new JdbcTemplate(dataSource);
    namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    jdbcTemplate.update(DROP_ADDRESS_TABLE_QUERY);
    jdbcTemplate.update(CREATE_ADDRESS_TABLE_QUERY);
    jdbcTemplate.update(DROP_PERSON_TABLE_QUERY);
    jdbcTemplate.update(CREATE_PERSON_TABLE_QUERY);

    jdbcTemplate.update(DROP_PERSON_ADDRESS_TABLE_QUERY);
    jdbcTemplate.update(CREATE_PERSON_ADDRESS_TABLE_QUERY);
  }
}
