package com.scm.controllers;

import com.scm.model.Contact;
import com.scm.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Contact> saveMessage(@RequestBody Contact message) {
        Contact saved = contactService.saveMessage(message);
        return ResponseEntity.ok(saved);
    }
}
