# Extra Credit

* DUE: March 25 (Tuesday), 2025 before start of class

**GitHub Classroom Link:** TBD

This extra credit is worth 1 point out of 100 points for the entire course.
Note that you need to get 100/100 on the autograder to get credit.  Partial
credit will be given under the discretion of the instructor and may not
necessarily equate to your GradeScope score.

## Description

We are going to go back to the MonkeySim program we profiled on Exercise 4.
The game is a simulation of the Collatz Conjecture
(https://en.wikipedia.org/wiki/Collatz_conjecture).  The conjecture states that
the game will eventually end (that is the first monkey will eventually get the
banana).  

Now it turns out that the MonkeySim does not end given certain arguments.  So
have we disproved the conjecture, something that nobody was able to do since
1937?  Are our names going to be on the cover page of newspapers tomorrow?

Sorry for the downer, but not really.  MonkeySim does not end because it has a
defect in its implementation.  For certain arguments, the game falls into an
infinite loop, where a cycle is formed when a group of monkeys pass the banana
among themselves in a repetitive pattern.

Now, this defect is hard to find using just a handful of test cases you can
write using JUnit.  So we are going to use stochastic testing to feed MonkeySim
with a whole bunch of randomized arguments to see which triggers the infinite
loop.  Complete MonkeySimStochasticTest.java to implement the testMonkeySim
method which checks the invariant that no matter which argument is given,
runSimulation never falls into an infinite loop.  Use the @InRange annotation
to make sure the generated random number for the starting monkey is greater
than or equal to 1, to make sure we do meaningful tests.

So how do we detect when runSimulation falls into an infinite loop?  Maybe add
a timeout to the test and say that if the test does not return within 10
minutes, we consider it to be an infinite loop?  But of course, there is no
guarantee since runSimulation may just be taking a long time to return.  In
order to detect infinite loops reliably, you will have to modify MonkeySim to
be able to detect it when it happens, that is when you detect a cycle forming
within a group of monkeys.  On detection, throw an InfiniteLoopException.  In
the testMonkeySim JUnit method, the check can be done by catching the
InfiniteLoopException and fail()-ing that test run.

## What to do

Create a new GitHub classroom repository by following the link posted above.
Note that this repository, while similar to Exercise 4, differs in a couple
of key ways.  It removed the List<Monkey> ml and MonkeyWatcher mw parameters
from its public methods to give you more freedom to choose your own data
structure for the group of monkeys and make other implementation changes.
And you will have to optimize MonkeySim heavily, beyond what you did for
Exercise 4, in order for the program to be performant enough for stochastic
testing.  Stochastic testing does hundreds of random trials and each trial
must be quick for it to end in a reasonable amount of time.

Also, a new **verbose** flag is added that allows MonkeySim to disable system
output, so that MonkeySim can be further sped up during testing.  You are
expected to turn off this flag for your stochastic tests as specified in the
Javadoc comments.

You will have to:

1. Create a memory profile after you do all the Exercise 4 optimizations, but
   before you do any optimizations to reduce memory consumption.  First, insert
the 30 second sleep at the beginning of the main method like you did for
Exercise 4 to make profiling easier.  Then launch the application using the followng commandline:

   ```
   java -cp target/classes edu.pitt.cs.MonkeySim 1000000
   ```

   On VisualVM, open the running application.  Go to the Profiler tab as in
Exercise 4, then click on the "Memory settings" beside "CPU settings".  Then
edit the Profile classes to be "edu.pitt.cs.\*\*" as before.  Then click on the
"Memory" button beside the "CPU" button to start profiling memory objects.
When the execution is dne, create a snapshot.  From the snapshot, click on the
"Save" icon, and chood "Export Objects", then save in PNG format.  Name the
file "memory-before.png".  You should see 1,000,001 Monkey objects created.

1. Optimize MonkeySim to reduce the number of Monkey objects created, while
   making sure MonkeySimPinningTest continues to pass.

1. Create a second memory profile "memory-after.png" using the same steps as
   above.  The new profile should show a much smaller number of Monkeys
created.

1. Modify MonkeySim so that it detects infinite loops and throws the
   InfiniteLoopException when it does, again making sure
MonkeySimPinningTest still passes.

1. Complete the testMainStochastic QuickCheck method in
   MonkeySimStochasticTest.java using the InfiniteLoopException exception.

1. Run testMainStochastic and let QuickCheck find a monkey number argument
   that triggers the infinite loop defect.

1. Create a regular JUnit test case out of that argument named
   testMainInfiniteLoop that checks for infinite loops.  Of course, this
test case will always fail with the current implementation.

Don't forget to recompile after any source code changes:

```
mvn compile
```

In order to run the simulation, you can use the following commandline as in the previous exercise:

```
java -cp target/classes edu.pitt.cs.MonkeySim <starting-monkey-num>
```

In order to run both the MonkeySimPinningTest and MonkeySimStochasticTest JUnit
tests, you can use Maven:

```
mvn test
```

The expectation is that the pinning tests (that check for existing behavior)
should pass and the stochastic tests (that check for the infinite loop
defect), should fail.

## Extra Credit Submission

1. Fill in ReportTemplate.docx with the "memory-before.png" and
   "memory-after.png" files generated above and exort to ReportTemplate.pdf.

1. Commit and push all your code and your report to your GitHub classroom repository.

1. Submit the repository to the **Supplementary Exercise 2 Extra Credit** link.
