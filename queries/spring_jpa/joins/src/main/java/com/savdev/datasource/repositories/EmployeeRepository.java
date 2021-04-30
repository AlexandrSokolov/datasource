package com.savdev.datasource.repositories;

import com.savdev.datasource.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;
import java.util.Map;

@Eager
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  /**
   *
   * @return only employees, who have phones
   */
  //List<Employee> withJoin();
  Map<String, String> withJoin();

  /**
   *
   * @return employees, with or without phones
   */
  List<Employee> withLeftOuterJoin();

//  /**
//   *
//   * @return only employees, who have phones, using `where`, without `join`
//   */
//  List<Employee> joinWithWhere();
}
