package com.example.springbatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
//    Index	User Id	First Name	Last Name	Sex	Email	Phone	Date of birth	Job Title
    @Id
    private Long srNo;
    private String userId;
    private String firstName;
    private String lastName;
    private String sex;
    private String email;
    private String jobTitle;



}
