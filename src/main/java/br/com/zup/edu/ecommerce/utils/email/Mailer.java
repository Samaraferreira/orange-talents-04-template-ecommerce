package br.com.zup.edu.ecommerce.utils.email;

public interface Mailer {
    void send(String from, String to, String nameFrom, String subject, String body);
}
