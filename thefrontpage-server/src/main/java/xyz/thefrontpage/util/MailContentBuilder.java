package xyz.thefrontpage.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;
    private final Lock lock = new ReentrantLock();

    public String build(String message) {
        Context context = new Context();
        lock.lock();
        try {
            context.setVariable("message", message);
        } finally {
            lock.unlock();
        }
        return templateEngine.process("mail-template", context);
    }

}
