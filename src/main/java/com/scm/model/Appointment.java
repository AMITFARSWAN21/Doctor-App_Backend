package com.scm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Appointment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String name;
 private String email;
 private String phone;
 private String date;
 private String time;
 private String reason;
 private String gender;
 private int age;
 private int fees;


 private String doctorName;

}
