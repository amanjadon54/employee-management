package com.ems.repository;

import com.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    ArrayList<Employee> findByNameContaining(String name);

//    @Query(value = "select e.name from employee e where name :likeName order by created_at desc")
//    String findLastUsedName(String likeName);
    ArrayList<Employee> findByAge(int age);
}
