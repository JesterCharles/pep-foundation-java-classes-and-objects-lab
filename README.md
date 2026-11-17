# Classes and Objects lab: a help-desk ticket

You model one real thing, a support ticket on a help desk, as a Java class.
Each ticket object owns its id, title, priority, open or closed state and event history, and no code anywhere can put a ticket into an invalid state.
In seven linked tasks across `Ticket.java` and the enum `Priority.java`, you practise constructors and validation, encapsulation, enums, `toString`, equality and `StringBuilder`.

The full learner instructions are in [`.evolv/instructions.md`](.evolv/instructions.md), which the lab platform shows as the lab's instructions.

## Layout

This repository follows the Exa coding-lab scaffold for Java.

| Path | What it is |
|---|---|
| `src/main/java/tickets/Priority.java` | Lab source |
| `src/main/java/tickets/Ticket.java` | Lab source |
| `src/test/java/tickets/TicketTest.java` | Visible tests |
| `.evolv/instructions.md` | Learner instructions |
| `.evolv/grade.yml` | Grading manifest, used by Run Tests and by the grader |
| `pom.xml` | Maven build: Java 17 and JUnit 5.11.3, matching the scaffold |
| `Dockerfile` | Image for local Run Tests; warms the Maven cache |
| `expected/test-summary.txt` | The result of a finished lab |

There are no held-out tests in this lab: every test is visible.

## Run the tests

On the platform, select **Run Tests**. From a terminal with JDK 17 or later and Apache Maven 3.9:

```
mvn -q test
```

All 7 tests fail until the `TODO`s are done. That is expected.
