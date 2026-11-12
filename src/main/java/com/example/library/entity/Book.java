package com.example.library.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
@Entity @Table(name="books")
public class Book {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Column(nullable=false) private String title;
 @NotBlank @Column(nullable=false) private String author;
 @Column(unique=true) private String isbn;
 private String category;
 @Min(0) @Column(nullable=false) private int totalCopies=1;
 @Min(0) @Column(nullable=false) private int availableCopies=1;
 public Book() {}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getAuthor(){return author;} public void setAuthor(String v){author=v;}
 public String getIsbn(){return isbn;} public void setIsbn(String v){isbn=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public int getTotalCopies(){return totalCopies;} public void setTotalCopies(int v){totalCopies=v;}
 public int getAvailableCopies(){return availableCopies;} public void setAvailableCopies(int v){availableCopies=v;}
}
