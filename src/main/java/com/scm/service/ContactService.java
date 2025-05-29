package com.scm.service;

import com.scm.model.Contact;
import com.scm.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

   public Contact saveMessage(Contact message)
   {
       return  contactRepository.save(message);
   }


}
