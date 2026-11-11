package tickets;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Public tests for the Classes and Objects lab. Each test creates tickets and
 * checks only their visible behavior: what the methods return and how the
 * object's state changes.
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
class TicketTest {

    @Test
    void task1Constructor() {
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> new Ticket(null, "Printer jam", Priority.NORMAL),
                        "Task 1: a null id must be rejected with Objects.requireNonNull"),
                () -> assertThrows(NullPointerException.class,
                        () -> new Ticket("NT-1", "Printer jam", null),
                        "Task 1: a null priority must be rejected with Objects.requireNonNull"),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Ticket("NT-1", "   ", Priority.NORMAL),
                        "Task 1: a blank title must be rejected with IllegalArgumentException"),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Ticket("NT-1", null, Priority.NORMAL),
                        "Task 1: a null title must be rejected with IllegalArgumentException"));
    }

    @Test
    void task2Getters() {
        Ticket jam = new Ticket("NT-1", "Printer jam", Priority.NORMAL);
        Ticket vpn = new Ticket("NT-2", "VPN drops", Priority.LOW);
        assertAll(
                () -> assertEquals("NT-1", jam.getId(), "Task 2: getId() after new Ticket(\"NT-1\", ...)"),
                () -> assertEquals("Printer jam", jam.getTitle(), "Task 2: getTitle()"),
                () -> assertEquals(Priority.NORMAL, jam.getPriority(), "Task 2: getPriority()"),
                () -> assertTrue(jam.isOpen(), "Task 2: a new ticket must start open"),
                () -> assertEquals("NT-2", vpn.getId(),
                        "Task 2: each object keeps its own field values"),
                () -> assertEquals(Priority.LOW, vpn.getPriority(),
                        "Task 2: each object keeps its own priority"));
    }

    @Test
    void task3PriorityEnum() {
        assertAll(
                () -> assertEquals(48, Priority.LOW.responseHours(), "Task 3: LOW.responseHours()"),
                () -> assertEquals(24, Priority.NORMAL.responseHours(), "Task 3: NORMAL.responseHours()"),
                () -> assertEquals(4, Priority.URGENT.responseHours(), "Task 3: URGENT.responseHours()"));
    }

    @Test
    void task4Behavior() {
        Ticket jam = new Ticket("NT-1", "Printer jam", Priority.NORMAL);
        assertEquals("next day", jam.slaLabel(), "Task 4: slaLabel() of a NORMAL ticket");
        jam.escalate();
        assertAll(
                () -> assertEquals(Priority.URGENT, jam.getPriority(),
                        "Task 4: escalate() must raise the priority to URGENT"),
                () -> assertEquals("today", jam.slaLabel(), "Task 4: slaLabel() after escalate()"),
                () -> assertTrue(jam.isOpen(), "Task 4: escalating must not close the ticket"));
        jam.close();
        assertFalse(jam.isOpen(), "Task 4: close() must close the ticket");
        Ticket calm = new Ticket("NT-3", "Slow login", Priority.LOW);
        assertAll(
                () -> assertEquals("same week", calm.slaLabel(), "Task 4: slaLabel() of a LOW ticket"),
                () -> assertTrue(calm.isOpen(),
                        "Task 4: closing NT-1 must not touch NT-3; every object has its own state"));
    }

    @Test
    void task5ToString() {
        Ticket jam = new Ticket("NT-1", "Printer jam", Priority.URGENT);
        assertEquals("NT-1 [URGENT] Printer jam (open)", jam.toString(),
                "Task 5: toString() of an open ticket");
        jam.close();
        assertEquals("NT-1 [URGENT] Printer jam (closed)", jam.toString(),
                "Task 5: toString() after close()");
    }

    @Test
    void task6Equality() {
        Ticket a = new Ticket("NT-1", "Printer jam", Priority.NORMAL);
        Ticket b = new Ticket("NT-1", "Printer jam, second report", Priority.LOW);
        Ticket c = new Ticket("NT-2", "VPN drops", Priority.NORMAL);
        assertAll(
                () -> assertEquals(a, b, "Task 6: tickets with the same id are the same ticket"),
                () -> assertNotEquals(a, c, "Task 6: different ids are different tickets"),
                () -> assertFalse(Ticket.sameTicket(a, b),
                        "Task 6: equal tickets are still two separate objects, so == is false"),
                () -> assertTrue(Ticket.sameTicket(a, a), "Task 6: a == a"),
                () -> assertEquals(a.hashCode(), b.hashCode(),
                        "Task 6: equal tickets must have equal hash codes"),
                () -> assertNotEquals(a, "NT-1", "Task 6: a ticket never equals a plain string"),
                () -> assertFalse(a.equals(null), "Task 6: equals(null) must be false, not an error"));
    }

    @Test
    void task7History() {
        Ticket jam = new Ticket("NT-1", "Printer jam", Priority.NORMAL);
        assertEquals("", jam.history(), "Task 7: history() of a fresh ticket is empty");
        jam.escalate();
        jam.close();
        assertEquals("escalated; closed", jam.history(),
                "Task 7: history() joins the events with \"; \"");
        Ticket calm = new Ticket("NT-2", "Slow login", Priority.LOW);
        calm.close();
        assertEquals("closed", calm.history(), "Task 7: history() with a single event");
    }
}
