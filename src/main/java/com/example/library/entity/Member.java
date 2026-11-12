package com.example.library.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Entity @Table(name="members")
public class Member {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Column(nullable=false) private String fullName;
 @Email @Column(unique=true) private String email;
 private String phone;
 public Member() {}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
}
