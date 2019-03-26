package idv.code.concurrent;

import static org.junit.Assert.fail;

import org.junit.Test;

public class ClassWithThreadingProblemTest {

    @Test
    public void twoThreadsShouldFailEventually() throws Exception {
        final ClassWithThreadingProblem classWithThreadingProblem = new ClassWithThreadingProblem();

        Runnable runnable = () -> classWithThreadingProblem.takeNextId();

        for (int i = 0; i < 500000; ++i) {
            int startingId = classWithThreadingProblem.nextId;
            int expectedResult = 2 + startingId;

            Thread t1 = new Thread(runnable);
            Thread t2 = new Thread(runnable);
            t1.start();
            t2.start();
            t1.join();
            t2.join();

            int endingId = classWithThreadingProblem.nextId;

            if (endingId != expectedResult) {
                return;
            }
        }

        fail("Should have exposeed a threading issue but it did not.");
    }
}
