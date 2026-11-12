package com.example.library.controller;
import com.example.library.entity.Loan;
import com.example.library.service.LibraryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController @RequestMapping("/api/loans")
public class LoanController {
 private final LibraryService service;
 public LoanController(LibraryService s){service=s;}
 @GetMapping public List<Loan> all(){return service.allLoans();}
 @GetMapping("/active") public List<Loan> active(){return service.activeLoans();}
 @PostMapping("/issue") public Loan issue(@RequestBody Map<String,Long> r){return service.issueBook(r.get("bookId"),r.get("memberId"));}
 @PostMapping("/{id}/return") public Loan returnBook(@PathVariable Long id){return service.returnBook(id);}
}
