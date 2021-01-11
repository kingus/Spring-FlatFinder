package com.wat.flatfinder.services;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(String username, int offerId, boolean isHtmlContent) throws MessagingException;
}