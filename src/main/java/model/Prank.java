package model;

import SMTP.SMTPClient;

import java.util.ArrayList;

/**
 * @author Benoit Gianinetti
 */
public class Prank {
    private Mail mail;

    public Prank() {
        mail = new Mail();
    }

    public void setSender(Person sender) {
        mail.setFrom(sender.getEmailAddress());
    }

    public void setVictims(ArrayList<Person> victims) {
        ArrayList<String> victimsEmail = new ArrayList<String>(victims.size());
        for (Person p : victims) {
            victimsEmail.add(p.getEmailAddress());
        }

        mail.setTo(victimsEmail);
    }

    public void setMessage(String message) {
        mail.setMessage(message);
    }

    public void sendMail() {
        SMTPClient.getInstance().sendMail(mail);
    }
}
