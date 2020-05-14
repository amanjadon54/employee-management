package com.ems.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(indexes = {@Index(columnList = "name", name = "emp_name_idx"),
        @Index(columnList = "age", name = "emp_age_idx")})
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;
    private int age;

    @Column(name = "payroll_id")
    private String payrollId;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.updatedAt = this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
