package com.example.library.service;
import com.example.library.entity.*;
import com.example.library.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
@Service
public class LibraryService {
 private final BookRepository books; private final MemberRepository members; private final LoanRepository loans;
 public LibraryService(BookRepository b,MemberRepository m,LoanRepository l){books=b;members=m;loans=l;}
 public List<Book> searchBooks(String q){return q==null||q.isBlank()?books.findAll():books.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q,q);}
 @Transactional public Book addBook(Book b){b.setAvailableCopies(b.getTotalCopies());return books.save(b);}
 @Transactional public Book updateBook(Long id,Book in){
  Book b=books.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found"));
  int issued=b.getTotalCopies()-b.getAvailableCopies();
  if(in.getTotalCopies()<issued)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Total copies cannot be less than issued copies");
  b.setTitle(in.getTitle());b.setAuthor(in.getAuthor());b.setIsbn(in.getIsbn());b.setCategory(in.getCategory());
  b.setTotalCopies(in.getTotalCopies());b.setAvailableCopies(in.getTotalCopies()-issued);return books.save(b);
 }
 @Transactional public void deleteBook(Long id){
  Book b=books.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found"));
  if(b.getAvailableCopies()!=b.getTotalCopies())throw new ResponseStatusException(HttpStatus.CONFLICT,"Return issued copies before deleting");
  books.delete(b);
 }
 public List<Member> allMembers(){return members.findAll();}
 public Member addMember(Member m){return members.save(m);}
 @Transactional public Loan issueBook(Long bookId,Long memberId){
  Book b=books.findById(bookId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found"));
  Member m=members.findById(memberId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Member not found"));
  if(b.getAvailableCopies()<1)throw new ResponseStatusException(HttpStatus.CONFLICT,"No copies available");
  b.setAvailableCopies(b.getAvailableCopies()-1);books.save(b);
  Loan l=new Loan();l.setBook(b);l.setMember(m);l.setIssueDate(LocalDate.now());l.setDueDate(LocalDate.now().plusDays(14));l.setStatus(LoanStatus.ISSUED);return loans.save(l);
 }
 @Transactional public Loan returnBook(Long id){
  Loan l=loans.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Loan not found"));
  if(l.getStatus()==LoanStatus.RETURNED)throw new ResponseStatusException(HttpStatus.CONFLICT,"Already returned");
  Book b=l.getBook();b.setAvailableCopies(b.getAvailableCopies()+1);books.save(b);
  l.setReturnDate(LocalDate.now());l.setStatus(LoanStatus.RETURNED);return loans.save(l);
 }
 public List<Loan> allLoans(){return loans.findAll();}
 public List<Loan> activeLoans(){return loans.findByStatus(LoanStatus.ISSUED);}
}
