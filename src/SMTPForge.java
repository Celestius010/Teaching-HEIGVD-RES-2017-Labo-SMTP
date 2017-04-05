import java.io.*;
import java.net.Socket;

public class SMTPForge {

    private static final int port = 25;
    private static final String SMTPServer = "smtp.heig-vd.ch";

    private static final String FROM = "miguel.santamaria@heig-vd.ch";
    private static final String RECPT = "colin.lavanchy@heig-vd.ch";

    private static final String MSG = ("From: \"Miguel Santamaria\" <"+FROM+">\r\n" +
            "To: Colin Lavanchy <"+RECPT+">\r\n" +
            "Date: Tue, 04 April 2017 16:20:09 -0500\r\n" +
            "Subject: [RES] SMTP - colin.lavanchy@heig-vd.ch\r\n\r\n" +
            "Laboratoire réussi.\r\n.");

    public enum SMTP_COMMANDS{

        HELLO("EHLO"),
        MAIL_FROM("MAIL FROM:<"+FROM+">"),
        RCPT_TO("RCPT TO:<"+RECPT+">"),
        DATA("DATA"),
        QUIT("QUIT");

        private final String s;

        SMTP_COMMANDS(String s){
            this.s = s;
        }

        public String s(){
            return s;
        }

    }

    public static void main(String... args) {
        try {
            forge();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void forge() throws IOException{
        Socket socket = new Socket(SMTPServer,port);

        BufferedOutputStream bw = new BufferedOutputStream(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        read(br);

        //HELLO
        send(bw,SMTP_COMMANDS.HELLO.s());

        while(socket.getInputStream().available()!=0) {
            read(br);
        }

        //MAIL FROM
        send(bw,SMTP_COMMANDS.MAIL_FROM.s());
        read(br);

       //RECIPIENT
        send(bw,SMTP_COMMANDS.RCPT_TO.s());
        read(br);

        //SENDING DATA
        send(bw,SMTP_COMMANDS.DATA.s());
        read(br);

        //EFFECTIVELY SENDING DATA
        send(bw,MSG);
        read(br);

        //BYE BYE
        send(bw,SMTP_COMMANDS.QUIT.s());
        read(br);

        br.close();
        bw.close();

    }

    private static void send(BufferedOutputStream bw, String cmd) throws IOException {
        bw.write((cmd+"\r\n").getBytes("ISO-8859-1"));
        bw.flush();
    }

    private static void read(BufferedReader br) throws IOException{
        String line =  br.readLine();
        System.out.println(line);
    }
}
;