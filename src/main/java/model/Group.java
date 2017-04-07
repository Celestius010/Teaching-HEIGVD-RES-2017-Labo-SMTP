package model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Person> members;

    public Group() {
        members = new ArrayList<Person>();
    }

    public List<Person> getMembers() {
        return members;
    }

    public void addMember(Person person) {
        members.add(person);
    }
}
