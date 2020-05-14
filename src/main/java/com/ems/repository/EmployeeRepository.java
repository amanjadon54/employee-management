package com.ems.repository;

import com.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByNameContaining(String name);

    List<Employee> findByAge(int age);

    @Query(value = "SELECT name FROM employee  WHERE name ~ ?1 order by created_at desc", nativeQuery = true)
    List<String> findByName(String name);
}
