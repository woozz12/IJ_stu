package com.spring.jpa.chap04_relation.entity;


import lombok.*;

import javax.persistence.*;
@Setter @Getter
//JPA 연관관계 매핑에서 연관관계 데이터는  ToString에서 제회해야 한다.
@ToString(exclude = {"department", "id"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "emp_name", nullable = false)
    private String name;

    // EAGER: 항상 무조건 조인을 수행
    // LAZY: 필요한 경우에만 조인을 수행(실무)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private  Department department;

    public void setDepartment(Department department) {
        this.department = department;
        department.getEmployees().add(this);
    }
}

