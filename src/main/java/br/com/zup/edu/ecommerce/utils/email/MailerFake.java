package br.com.zup.edu.ecommerce.utils.email;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MailerFake implements Mailer {

    @Override
    public void send(String from, String to, String nameFrom, String subject, String body) {
        System.out.println(body);
        System.out.println(subject);
        System.out.println(nameFrom);
        System.out.println(from);
        System.out.println(to);
    }
}
