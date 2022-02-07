package mygoogleserviceapi.shared.exception.handler;

import java.util.Date;

public class ErrorMessage {
    private final String message;
    private final Date date;

    public ErrorMessage(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
