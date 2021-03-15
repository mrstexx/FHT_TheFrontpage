package xyz.thefrontpage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.thefrontpage.dto.MailDto;
import xyz.thefrontpage.util.MailContentBuilder;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(MailDto mail) {
        MimeMessagePreparator messagePrep = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            messageHelper.setFrom("auth@thefrontpage.xyz");
            messageHelper.setTo(mail.getRecipient());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailContentBuilder.build(mail.getBody()));
        };
        try {
            mailSender.send(messagePrep);
            log.info("Activation email send to {}", mail.getRecipient());
        } catch (MailException e) {
            log.error("Sending email to {} failed due to {}", mail.getRecipient(), e.getLocalizedMessage());
            throw new IllegalStateException("Failed to send email");
        }
    }

}
