package SMTP;

import configuration.Configuration;
import model.Mail;

import java.io.*;
import java.net.Socket;

/**
 * @author Benoit Gianinetti
 */
public class SMTPClient {

    private static final int port = 25;
    private static SMTPClient instance;
    private static String HELLO = "EHLO";
    private static String FROM = "MAIL FROM:";
    private static String TO = "RCPT TO:";
    private static String DATA = "DATA";
    private static String QUIT = "QUIT";

    private SMTPClient() {}

    public static SMTPClient getInstance() {
        if (instance == null) {
            instance = new SMTPClient();
        }

        return instance;
    }

    public void sendMail(Mail mail) {
        Configuration config = Configuration.getInstance();
        Socket socket = null;
        try {
            socket = new Socket(config.smtpServerAddress(), config.smtpServerPort());
            BufferedOutputStream bw = new BufferedOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            read(br);

            //HELLO
            send(bw,HELLO);

            while(socket.getInputStream().available()!=0) {
                read(br);
            }

            //MAIL FROM
            send(bw,from(mail.getFrom()));
            read(br);

            //RECIPIENTS
            for (String recipient : mail.getTo()) {
                send(bw, to(recipient));
                read(br);
            }

            //SENDING DATA
            send(bw,DATA);
            read(br);

            //EFFECTIVELY SENDING DATA
            send(bw, data(mail.getMessage()));
            read(br);

            //BYE BYE
            send(bw,QUIT);
            read(br);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String data(String message) {
        return message + "\r\n.";
    }

    private String from(String from) {
        return FROM + "<" + from + ">";
    }

    private String to(String to) {
        return TO + "<" + to + ">";
    }

    private void send(BufferedOutputStream bw, String cmd) throws IOException {
        bw.write((cmd+"\r\n").getBytes("ISO-8859-1"));
        bw.flush();
    }

    private void read(BufferedReader br) throws IOException{
        String line =  br.readLine();
        System.out.println(line);
    }
}
;