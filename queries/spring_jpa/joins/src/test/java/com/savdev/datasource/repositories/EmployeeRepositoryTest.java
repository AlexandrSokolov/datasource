package com.savdev.datasource.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/scripts/departments.sql")
@Sql("/scripts/employees.sql")
@Sql("/scripts/phones.sql")
@Sql("/scripts/employee_phones.sql")
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Test
  public void testWithJoin() {
    Assertions.assertEquals(4, employeeRepository.withJoin().size());
  }

  @Test
  public void testWithLeftOuterJoin() {
    Assertions.assertEquals(4, employeeRepository.withLeftOuterJoin().size());
  }
}
