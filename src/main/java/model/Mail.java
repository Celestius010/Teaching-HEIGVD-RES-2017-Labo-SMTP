package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Benoit Gianinetti
 */
public class Mail {
    private String message;
    private String from;
    private ArrayList<String> to = new ArrayList<String>();

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }
}
