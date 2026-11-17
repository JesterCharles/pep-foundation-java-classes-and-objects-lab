# Classes and Objects lab: a help-desk ticket

You model one real thing, a support ticket on a help desk, as a Java class.
Each ticket object owns its id, title, priority, open or closed state and event history, and no code anywhere can put a ticket into an invalid state.
In seven linked tasks across `Ticket.java` and the enum `Priority.java`, you practise constructors and validation, encapsulation, enums, `toString`, equality and `StringBuilder`.

## Quick start

1. Open the lab project. The files you edit are in `src/main/java/tickets/`; the checklist below names them.
2. Select **Run Tests** before you change anything.

   **All 7 tests fail at first. That is expected: the TODOs are not done yet.**
   Every failure message starts with its task number, such as `Task 1:`.
3. Work through the checklist below in order. After each task, select **Run Tests** again and read the remaining `Task N:` messages.
4. **You are finished when all 7 tests pass.** Then select **Submit**.

```
OPEN project -> RUN TESTS: 7 fail (expected)
  -> READ the "Task N:" messages -> EDIT the next TODO -> RUN TESTS again
  -> Ticket.java (Tasks 1-2) -> Priority.java (3) -> Ticket.java (4-7)
  -> SUBMIT when all 7 tests pass
```

In words: open the project, run the tests, read which tasks fail, complete one task's TODO, run the tests again, and submit when they all pass.

## Checklist

Every task is marked with a `TODO` comment in one of these two files in `src/main/java/tickets/`:

- [ ] Task 1: `Ticket.java`, the constructor (validation)
- [ ] Task 2: `Ticket.java`, the getters (encapsulation)
- [ ] Task 3: `Priority.java`, response times on the enum constants (enums with data)
- [ ] Task 4: `Ticket.java`, `close`, `escalate` and `slaLabel` (behavior)
- [ ] Task 5: `Ticket.java`, `toString`
- [ ] Task 6: `Ticket.java`, `equals` and `hashCode` (equality)
- [ ] Task 7: `Ticket.java`, `history` (`StringBuilder`)

A task is done when no failure message starts with its number.
Edit only `Ticket.java` and `Priority.java`. The tests in `src/test/java/tickets/` are restored to their original state before your work is graded, so changes to them do not count. Leave the build file `pom.xml` and `expected/test-summary.txt` as they are.

## Task steps

Each `TODO` comment says exactly what the code must do.
The tests create tickets and check only their visible behavior.

Before you code, open `pom.xml`. Its JUnit dependency carries `<scope>test</scope>`.
That is why the tests can use JUnit while your `Ticket` class cannot: test-scope dependencies exist only while tests compile and run, and they never ship with the program.

### Task 1: the constructor

1. In `Ticket.java`, find the constructor and its `TODO`.
2. Assign every field from its parameter, writing `this.id = id;`. `this` picks the field when the parameter and the field share a name.
3. Validate before assigning, so no invalid ticket can ever exist:
   - A null `id` or `priority` fails with `Objects.requireNonNull(value, "name")`. Add `import java.util.Objects;` at the top of the file.
   - A `title` that is null or blank fails with `throw new IllegalArgumentException("title must not be blank")`.
4. A new ticket starts open: set `this.open = true;`.
5. While it is wrong, you see messages such as `Task 1: a null id must be rejected with Objects.requireNonNull`.

<details><summary>Hint</summary>

If you see `variable id might not have been initialized`, a `final` field is not assigned on every path through the constructor.

</details>

### Task 2: getters

1. The fields are `private`. Code in other classes cannot read them directly, and that is the point: only `Ticket` decides how its state changes.
2. Return each field from its getter: `getId`, `getTitle`, `getPriority`, `isOpen`.
3. Notice what has **no** setter. `id` is `final` because a ticket never changes identity, and `open` changes only through `close()`.
   `final` has three jobs in Java: on a variable it stops reassignment, as here; on a method it forbids overriding; on a class it forbids subclassing.

### Task 3: the Priority enum

1. Open `Priority.java`. The three constants already exist; now make them carry data.
2. Write the response hours in parentheses after each constant: `LOW(48)`, `NORMAL(24)`, `URGENT(4)`.
   Then add a `private final int` field and a constructor that assigns it, and return the field from `responseHours()`.
3. You never call an enum constructor yourself; the enum calls it once per constant.

### Task 4: behavior

1. `close()` sets the ticket to closed and records the event `"closed"` in the `events` list. `escalate()` raises the priority to `Priority.URGENT` and records `"escalated"`.
2. `slaLabel()` chooses the promise with a switch expression over `getPriority()`: `LOW` is `"same week"`, `NORMAL` is `"next day"`, and `URGENT` is `"today"`.
   A switch over an enum needs no `default` once every constant is covered.
3. The tests also check that closing one ticket leaves another ticket open: each object has its own fields.

<details><summary>Hint</summary>

If the switch does not compile, list all three cases.

</details>

### Task 5: toString

1. Build the one-line form exactly as the comment shows: `NT-1 [URGENT] Printer jam (open)`, ending in `(closed)` after `close()`.
2. `toString` is what `System.out.println(ticket)` prints. A readable form turns every log line into a sentence.

### Task 6: equality

1. Two `Ticket` objects mean the same ticket when their ids are equal: a second report of `NT-1` is still `NT-1`.
   Override `equals(Object other)`:
   - the same object is equal to itself;
   - anything that is not a `Ticket`, including `null`, is not equal;
   - otherwise compare the ids with `equals`.
2. Override `hashCode()` to return `id.hashCode()`, so equal tickets always have equal hash codes.
3. Compare with the complete `sameTicket` method next to it. `==` asks "same object in memory?", and your `equals` asks "same ticket?". The tests check both answers on the same pair.

### Task 7: history

1. Build `history()` with a `StringBuilder`: append each event from the `events` list, with `"; "` between events.
2. Strings are immutable, so `+` on a `String` in a loop would copy the whole text on every round. The builder appends in place, and becomes a `String` once, with `toString()`.

### About the test results

A passing run shows the class behaves correctly.
It does not check which Java constructs your code uses, so compare your code with each task's steps yourself.
`COMPILATION ERROR` means the code does not compile yet. Failing tests with `Task N:` messages mean it compiles but behaves wrongly.

## Using an AI assistant

If you use an AI assistant, ask it to explain, not to answer.
"Why does my switch over an enum not compile?" teaches you the rule; "write the Ticket class for me" does not.
Check any explanation with a test run before you trust it.

## Run the program (optional)

The class walks one ticket through its life. In a terminal, from the project folder:

```
mvn -q compile
java -cp target/classes tickets.Ticket
```

`expected/test-summary.txt` shows the finished result, including the seven lines the program prints.

## Troubleshooting

- **`COMPILATION ERROR`.** Your code does not compile yet. Maven names the file, then `[line,column]`, then the problem. Fix that line and run the tests again.
