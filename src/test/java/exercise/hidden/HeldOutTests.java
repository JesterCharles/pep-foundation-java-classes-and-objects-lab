package exercise.hidden;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exercise.Priority;
import exercise.Ticket;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/** Held-out tests: graded on submit, never shown to the learner. Edge cases for every task. */
@TestMethodOrder(MethodOrderer.MethodName.class)
class HeldOutTests {

    @Test
    void task1ConstructorEdgeCases() {
        assertAll(
                () -> assertThrows(NullPointerException.class,
                        () -> new Ticket("NT-1", "Printer jam", null),
                        "Task 1: a null priority must be rejected with Objects.requireNonNull"),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Ticket("NT-1", null, Priority.NORMAL),
                        "Task 1: a null title must be rejected with IllegalArgumentException"));
    }

    @Test
    void task2GettersEdgeCases() {
        Ticket jam = new Ticket("NT-1", "Printer jam", Priority.NORMAL);
        Ticket vpn = new Ticket("NT-2", "VPN drops", Priority.LOW);
        assertAll(
                () -> assertEquals("NT-2", vpn.getId(),
                        "Task 2: each object keeps its own field values"),
                () -> assertEquals(Priority.LOW, vpn.getPriority(),
                        "Task 2: each object keeps its own priority"));
    }

    @Test
    void task3PriorityEnumEdgeCases() {
        assertEquals(4, Priority.URGENT.responseHours(), "Task 3: URGENT.responseHours()");
    }

    @Test
    void task4BehaviorEdgeCases() {
        Ticket jam = new Ticket("NT-1", "Printer jam", Priority.NORMAL);
        Ticket calm = new Ticket("NT-3", "Slow login", Priority.LOW);
        jam.close();
        assertAll(
                () -> assertEquals("same week", calm.slaLabel(), "Task 4: slaLabel() of a LOW ticket"),
                () -> assertTrue(calm.isOpen(),
                        "Task 4: closing NT-1 must not touch NT-3; every object has its own state"));
    }

    @Test
    void task5ToStringEdgeCases() {
        Ticket jam = new Ticket("NT-1", "Printer jam", Priority.URGENT);
        jam.close();
        assertEquals("NT-1 [URGENT] Printer jam (closed)", jam.toString(),
                "Task 5: toString() after close()");
    }

    @Test
    void task6EqualityEdgeCases() {
        Ticket a = new Ticket("NT-1", "Printer jam", Priority.NORMAL);
        Ticket b = new Ticket("NT-1", "Printer jam, second report", Priority.LOW);
        assertAll(
                () -> assertEquals(a.hashCode(), b.hashCode(),
                        "Task 6: equal tickets must have equal hash codes"),
                () -> assertNotEquals(a, "NT-1", "Task 6: a ticket never equals a plain string"),
                () -> assertFalse(a.equals(null), "Task 6: equals(null) must be false, not an error"));
    }

    @Test
    void task7HistoryEdgeCases() {
        Ticket calm = new Ticket("NT-2", "Slow login", Priority.LOW);
        calm.close();
        assertEquals("closed", calm.history(), "Task 7: history() with a single event");
    }
}
