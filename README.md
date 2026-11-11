# Classes and Objects lab: a help-desk ticket

## Goal

Model one real thing - a support ticket on a help desk - as a Java class, in seven linked tasks across `Ticket.java` and the enum `Priority.java`.
Every object of your class is one ticket that owns its id, title, priority, open/closed state and event history, and no code anywhere can put a ticket into an invalid state.
You practise constructors and validation, encapsulation, enums, `toString`, equality and `StringBuilder` on one domain class, the way real services are built.
You build and test it with the real JDK and Maven on your own computer.

## Files

**Edit exactly two files.** Every `TODO Task N` comment marks a task; the test failure lines carry the same task numbers:

- `starter/src/main/java/tickets/Ticket.java` - Tasks 1, 2, 4, 5, 6 and 7: the ticket's construction, state, behavior, text form, equality and history
- `starter/src/main/java/tickets/Priority.java` - Task 3: give the enum constants their response times

**Do not change anything else.** In particular:

- `tests/tickets/TicketTest.java` - the public tests your class must pass
- `starter/pom.xml` - the build is prewired
- `expected/test-summary.txt` - the finished-state reference

## Prerequisites

You need JDK 25 and Apache Maven 3.9 or later, the same setup as the earlier Java labs.
Check both in a new terminal:

```
java -version
mvn -v
```

- `java -version` must report version 25.
- `mvn -v` must report Apache Maven 3.9 or later, and its `Java version:` line must also say 25.

This lab works the same on macOS, Linux and Windows.
On Windows, use PowerShell or Command Prompt; the commands are the same.
No Docker or other container tool is needed.

## Setup (once, needs the internet)

Open a terminal in the lab's root folder - the one that holds this README, `starter/` and `tests/` - and change into `starter` with `cd starter`; every command in this lab runs there. Then run:

```
mvn -q dependency:go-offline
```

It prints nothing when it succeeds.
If you finished an earlier Java lab on this machine, it finds everything already downloaded.
After this step, everything else in the lab runs offline.

### A look at the POM

Open `starter/pom.xml` before you code: the JUnit dependency there carries `<scope>test</scope>`.
That is why the tests can use JUnit while your `Ticket` class cannot - test-scope dependencies exist only while tests compile and run, and never ship with the program.

## Tasks

Open the lab's root folder in your IDE or editor; it holds one Maven project, `starter/pom.xml`, and if your editor asks, open or import that file as a Maven project, then open `starter/src/main/java/tickets/Ticket.java` and `Priority.java`. Whatever your editor shows, the test command below, run in a terminal in `starter`, is the result that counts.
Each task is marked with a `TODO` comment that says exactly what the code must do.
The tests create tickets and check only their visible behavior.

After each task, run the one test command from the `starter` folder:

```
mvn -q -o test
```

Every failure line starts with its task number, so you can work through the tasks in order.

### Task 1: the constructor

1. In `Ticket.java`, find the constructor and its `TODO`.
2. Assign every field from its parameter, writing `this.id = id;` - `this` picks the field when parameter and field share a name.
3. Validate before assigning, so no invalid ticket can ever exist:
   a null `id` or `priority` fails with `Objects.requireNonNull(value, "name")` (add `import java.util.Objects;`),
   and a `title` that is null or blank fails with `throw new IllegalArgumentException("title must not be blank")`.
4. A new ticket starts open: set `this.open = true;`.
5. While it is wrong, you see lines such as `Task 1: a null id must be rejected with Objects.requireNonNull`.
6. Done when no line starting with `Task 1` appears.

### Task 2: getters

1. The fields are `private`: code in other classes cannot read them directly, and that is the point - only `Ticket` decides how its state changes.
2. Return each field from its getter: `getId`, `getTitle`, `getPriority`, `isOpen`.
3. Notice what has **no** setter: `id` is `final` because a ticket never changes identity, and `open` changes only through `close()`.
   (`final` wears three hats in Java: on a variable it stops reassignment, as here; on a method it forbids overriding; on a class it forbids subclassing.)
4. Done when no line starting with `Task 2` appears.

### Task 3: the Priority enum

1. Open `Priority.java`. The three constants already exist; make them carry data.
2. Write the response hours in parentheses behind each constant - `LOW(48)`, `NORMAL(24)`, `URGENT(4)` - then add a `private final int` field and a constructor that assigns it, and return the field from `responseHours()`.
3. An enum constructor is never called by you; the enum calls it once per constant.
4. Done when no line starting with `Task 3` appears.

### Task 4: behavior

1. `close()` sets the ticket to closed and records the event `"closed"` in the `events` list; `escalate()` raises the priority to `Priority.URGENT` and records `"escalated"`.
2. `slaLabel()` chooses the promise with a switch expression over `getPriority()`: `LOW` is `"same week"`, `NORMAL` is `"next day"`, `URGENT` is `"today"`.
   A switch over an enum needs no default once every constant is covered.
3. The tests also check that closing one ticket leaves another ticket open: each object has its own fields.
4. Done when no line starting with `Task 4` appears.

### Task 5: toString

1. Compose the one-line form exactly as the comment shows: `NT-1 [URGENT] Printer jam (open)`, with `(closed)` after `close()`.
2. `toString` is what `System.out.println(ticket)` prints; a readable form turns every log line into a sentence.
3. Done when no line starting with `Task 5` appears.

### Task 6: equality

1. Two `Ticket` objects mean the same ticket when their ids are equal - a second report of `NT-1` is still `NT-1`.
   Override `equals(Object other)`: the same object is equal to itself; anything that is not a `Ticket` (including `null`) is not equal; otherwise compare the ids with `equals`.
2. Override `hashCode()` to return `id.hashCode()`, so equal tickets always carry equal hash codes.
3. Compare with the complete `sameTicket` method next to it: `==` asks "same object in memory?", your `equals` asks "same ticket?". The tests check both answers on the same pair.
4. Done when no line starting with `Task 6` appears.

### Task 7: history

1. Build `history()` with a `StringBuilder`: append each event from the `events` list, with `"; "` between events.
2. A `String` and `+` in a loop would copy the whole text on every round, because strings are immutable; the builder appends in place and becomes a `String` once, with `toString()`.
3. Done when no line starting with `Task 7` appears and the whole run prints nothing.

## Using an AI assistant

If you use an AI assistant in this lab, ask it to explain, not to answer: "why does my switch over an enum not compile?" teaches you the rule; "write the Ticket class for me" does not.
Verify any explanation against a test run before you trust it.

## Test

The one test command, run from the `starter` folder, is:

```
mvn -q -o test
```

- Before you start, all seven tests fail, and Maven ends with `Tests run: 7, Failures: 7`.
- Each test lists what behavior was wrong. A passing run shows the class behaves correctly; it does not check which Java constructs your code uses, so compare your code with each task's steps yourself.
- Tell the two failure kinds apart: `COMPILATION ERROR` with a file and `[line,column]` means the code does not compile yet - fix that line first; a `Failures:` count with `Task N:` lines means the code compiles but behaves wrongly.

You can also run the class itself, which walks one ticket through its life:

```
mvn -q -o compile
java -cp target/classes tickets.Ticket
```

## Expected output

When every task is complete, `mvn -q -o test` prints nothing and exits with status 0.
`expected/test-summary.txt` shows the full expected result, including the seven lines the program prints.

## Offline note

Only the setup step uses the network.
The test command uses `-o` (offline), so it never downloads anything.
If it reports that an artifact is missing from the local repository, run the setup step once more while online.

## Cleanup

The lab writes only the `starter/target` folder (compiled classes and test reports). Delete it from the `starter` folder:

- macOS and Linux: `rm -rf target`
- Windows PowerShell: `Remove-Item -Recurse -Force target`

The JUnit and plugin downloads stay in your local Maven repository (`~/.m2/repository` by default) so later Java labs can reuse them.

`starter/.gitignore` already keeps Maven's `starter/target` folder out of Git. Your editor and operating system may also create local files in the lab's root folder, such as `.idea/`, `.vscode/` or `.DS_Store`; they are not part of the lab, so if `git status` lists any of them, add them to a `.gitignore` file in the lab's root folder instead of committing them.

## Troubleshooting

- **`mvn: command not found`, or `'mvn' is not recognized`.** Maven's `bin` folder is not on your `PATH`. Add it, then open a new terminal.
- **`mvn -v` shows a Java version other than 25.** Maven uses the JDK named by `JAVA_HOME` when it is set, otherwise the `java` on your `PATH`. Set `JAVA_HOME` to your JDK 25 folder, or unset it, then open a new terminal.
- **`release version 25 not supported`.** Maven is running on an older JDK. Fix it as in the previous item.
- **`Cannot access central in offline mode`, or a missing artifact.** The setup step has not completed. Run `mvn -q dependency:go-offline` once while online.
- **`variable id might not have been initialized`.** A `final` field must be assigned on every path through the constructor.
- **The switch in Task 4 does not compile.** A switch expression must cover every `Priority` constant; list all three cases.
