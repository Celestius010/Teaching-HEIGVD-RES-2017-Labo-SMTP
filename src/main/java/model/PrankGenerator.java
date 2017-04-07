package model;

import configuration.Configuration;

import java.util.*;

/**
 * @author Benoit Gianinetti
 */
public class PrankGenerator {

    private Configuration config;
    private int victimsPerGroup;
    ArrayList<Person> victims;

    public PrankGenerator() {
        config = new Configuration().getInstance();
        victims = (ArrayList<Person>) config.getVictims();
        victimsPerGroup = victims.size() / config.getNumberOfGroups();
    }

    public void generatePranks() {

        if (victimsPerGroup >= 3) {
            ArrayList<String> messages = (ArrayList<String>) config.getMessages();
            Iterator<String> messageIterator = messages.iterator();

            for (Group g : generateRandomGroups()) {
                Prank prank = new Prank();
                ArrayList<Person> members = (ArrayList<Person>) g.getMembers();
                prank.setSender(members.remove(0));
                prank.setVictims(members);

                if (!messageIterator.hasNext())
                    messageIterator = messages.iterator();

                prank.setMessage(messageIterator.next());
                prank.sendMail();
            }
        }
    }

    private List<Group> generateRandomGroups() {
        ArrayList<Person> victims = (ArrayList<Person>) config.getVictims();

        ArrayList<Group> result = new ArrayList<Group>();

        // Randomly shuffle all victims
        Collections.shuffle(victims);

        for (int i = 0, j = 0; i < config.getNumberOfGroups(); i++) {
            Group g = new Group();

            g.addMember(victims.get(j));

            for (int k = 0; k < victimsPerGroup; k++) {
                g.addMember(victims.get(j++));
            }

            result.add(g);
        }

        return result;
    }
}
