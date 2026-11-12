package com.example.library.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="loans")
public class Loan {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Book book;
 @ManyToOne(optional=false) private Member member;
 @Column(nullable=false) private LocalDate issueDate;
 @Column(nullable=false) private LocalDate dueDate;
 private LocalDate returnDate;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private LoanStatus status=LoanStatus.ISSUED;
 public Loan() {}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Book getBook(){return book;} public void setBook(Book v){book=v;}
 public Member getMember(){return member;} public void setMember(Member v){member=v;}
 public LocalDate getIssueDate(){return issueDate;} public void setIssueDate(LocalDate v){issueDate=v;}
 public LocalDate getDueDate(){return dueDate;} public void setDueDate(LocalDate v){dueDate=v;}
 public LocalDate getReturnDate(){return returnDate;} public void setReturnDate(LocalDate v){returnDate=v;}
 public LoanStatus getStatus(){return status;} public void setStatus(LoanStatus v){status=v;}
}
