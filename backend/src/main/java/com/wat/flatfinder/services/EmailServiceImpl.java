package com.wat.flatfinder.services;
import com.wat.flatfinder.entities.Offer;
import com.wat.flatfinder.repositories.OfferRepository;
import com.wat.flatfinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, UserRepository userRepository, OfferRepository offerRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
    }

    public void sendMail(String username, int offerId,
                         boolean isHtmlContent) throws MessagingException {

        Offer offer = offerRepository.findById(offerId).get();
        String subject = "FlatFinder - nowa oferta dla Ciebie";
        String text = "Sprawdź ofertę, która Cię zainteresowała!<br>" +offer.getOfferUrl()  +"<br><br><img src=" + offer.getImgUrl() + "></img>";
        String userEmail = userRepository.findByUsername(username).get().getEmail();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(userEmail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }
}