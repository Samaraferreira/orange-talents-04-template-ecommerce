package br.com.zup.edu.ecommerce.shared.handler;

import java.util.Map;

public class ExceptionDto {

    private Map<String, String> messages;

    public ExceptionDto(Map<String, String> messages) {
        this.messages = messages;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
}
