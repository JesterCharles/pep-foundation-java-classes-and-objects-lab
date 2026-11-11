package tickets;

import java.util.ArrayList;

/**
 * Classes and Objects lab: one support ticket of a help desk.
 * Every object of this class is one real ticket: it owns its id, title,
 * priority, open/closed state and event history. Complete the tasks marked
 * TODO, one at a time; each gives the ticket one more piece of real behavior.
 * The public tests create tickets and check only their visible behavior.
 * The README lists the steps for every task. main is complete: it walks one
 * ticket through its life and prints what it sees.
 */
public class Ticket {

    // The fields are private: only this class touches them directly.
    // id is final: a ticket never changes its identity after creation.
    private final String id;
    private String title;
    private Priority priority;
    private boolean open;
    private final ArrayList<String> events = new ArrayList<>();

    // Task 1 (constructor): initialize every field from the parameters.
    // Use `this` to tell the field from the parameter with the same name.
    // Reject bad input at once: a null id or priority with
    // Objects.requireNonNull(value, "name"), a null or blank title with
    // new IllegalArgumentException("title must not be blank").
    // A new ticket starts open.
    public Ticket(String id, String title, Priority priority) {
        // TODO Task 1: validate, then assign the fields
        this.id = null;
        this.title = null;
        this.priority = null;
        this.open = false;
    }

    // Task 2 (getters): expose the private fields read-only. There is no
    // setter for id - identity never changes - and no setter for open either:
    // state changes only through close() and escalate() below.
    public String getId() {
        return "?"; // TODO Task 2
    }

    public String getTitle() {
        return "?"; // TODO Task 2
    }

    public Priority getPriority() {
        return null; // TODO Task 2
    }

    public boolean isOpen() {
        return false; // TODO Task 2
    }

    // Task 4a (behavior): close the ticket and record the event "closed".
    public void close() {
        // TODO Task 4: set the state and add "closed" to events
    }

    // Task 4b (behavior): raise the priority to URGENT and record "escalated".
    public void escalate() {
        // TODO Task 4: change the priority and add "escalated" to events
    }

    // Task 4c (enum in a switch): the service promise for the current
    // priority, chosen with a switch over the enum:
    // LOW is "same week", NORMAL is "next day", URGENT is "today".
    public String slaLabel() {
        return "?"; // TODO Task 4: switch over getPriority()
    }

    // Task 5 (toString): a readable one-line form, exactly:
    //   "NT-1 [URGENT] Printer jam (open)"   while open
    //   "NT-1 [URGENT] Printer jam (closed)" after close()
    @Override
    public String toString() {
        return "?"; // TODO Task 5: compose id, priority, title and state
    }

    // Task 6 (equality): two Ticket objects are the same ticket when their
    // ids are equal, even if they are two separate objects in memory.
    // Override equals to compare ids, and hashCode to match it.
    @Override
    public boolean equals(Object other) {
        return false; // TODO Task 6: same class and equal id
    }

    @Override
    public int hashCode() {
        return 0; // TODO Task 6: derive from the id
    }

    // Complete (references): true only when a and b are the very same object.
    // Compare this with equals above: == asks "same object?",
    // equals asks "same ticket?".
    public static boolean sameTicket(Ticket a, Ticket b) {
        return a == b;
    }

    // Task 7 (StringBuilder): the ticket's events joined with "; ", built
    // with a StringBuilder in a loop - string + in a loop copies the whole
    // text every round, a StringBuilder appends in place.
    // history() of an escalated then closed ticket is "escalated; closed";
    // with no events yet it is "".
    public String history() {
        return "?"; // TODO Task 7: build the text with a StringBuilder
    }

    public static void main(String[] args) {
        Ticket jam = new Ticket("NT-1", "Printer jam", Priority.NORMAL);
        System.out.println("New: " + jam);
        System.out.println("SLA: " + jam.slaLabel());
        jam.escalate();
        System.out.println("Hours after escalate: " + jam.getPriority().responseHours());
        jam.close();
        System.out.println("Done: " + jam);
        System.out.println("History: " + jam.history());
        Ticket copy = new Ticket("NT-1", "Printer jam again", Priority.LOW);
        System.out.println("Equal tickets: " + jam.equals(copy));
        System.out.println("Same object: " + sameTicket(jam, copy));
    }
}
