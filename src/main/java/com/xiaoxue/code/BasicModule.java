package com.xiaoxue.code;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.xiaoxue.code.repo.PersonRepository;
import com.xiaoxue.code.repo.PersonRepositoryImpl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.sqlite.SQLiteDataSource;

public class BasicModule extends AbstractModule {

  @Override
  protected void configure() {
    final DataSource dataSource = createDataSource();
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    binder().bind(JdbcTemplate.class).toInstance(jdbcTemplate);
    final NamedParameterJdbcTemplate namedParameterJdbcTemplate =
        new NamedParameterJdbcTemplate(dataSource);
    binder().bind(NamedParameterJdbcTemplate.class).toInstance(namedParameterJdbcTemplate);


    binder().bind(PersonRepository.class).to(PersonRepositoryImpl.class).in(Scopes.SINGLETON);
  }

  private DataSource createDataSource() {
    SQLiteDataSource ds = new SQLiteDataSource();
    ds.setUrl("jdbc:sqlite::memory:db1");
    return ds;
  }
}
