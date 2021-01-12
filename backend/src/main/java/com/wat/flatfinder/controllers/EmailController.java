package com.wat.flatfinder.controllers;

import com.wat.flatfinder.services.EmailService;
import com.wat.flatfinder.services.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailController {

        private final EmailService emailService;

        @Autowired
        public EmailController(EmailServiceImpl emailService) {
            this.emailService = emailService;
        }

        @GetMapping("/api/send-email/{id}")
        @CrossOrigin(origins = "http://localhost:3000")
        public String sendMail(@AuthenticationPrincipal String username, @PathVariable(value = "id") int offerId) throws MessagingException {

            emailService.sendMail(username, offerId, true);
            return "Email has been sent";
        }

}
