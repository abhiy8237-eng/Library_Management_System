package com.example.library.controller;
import com.example.library.entity.Member;
import com.example.library.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/members")
public class MemberController {
 private final LibraryService service;
 public MemberController(LibraryService s){service=s;}
 @GetMapping public List<Member> all(){return service.allMembers();}
 @PostMapping public Member add(@Valid @RequestBody Member m){return service.addMember(m);}
}
