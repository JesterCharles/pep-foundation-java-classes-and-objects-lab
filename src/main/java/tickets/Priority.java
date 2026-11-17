package tickets;

/**
 * Task 3: the priority of a support ticket, as an enum.
 * An enum makes the three levels a real type: no ticket can ever hold a
 * misspelled string or an unexpected number. Give each constant its promised
 * response time by adding a field, a constructor and the responseHours()
 * method body.
 */
public enum Priority {
    LOW,
    NORMAL,
    URGENT;

    // TODO Task 3: give each constant a response time in hours - LOW 48,
    // NORMAL 24, URGENT 4. Add a private final int field, a constructor that
    // sets it, write the hours in parentheses behind each constant above,
    // and return the field here.
    int responseHours() {
        return -1; // TODO: return the constant's own response time
    }
}
