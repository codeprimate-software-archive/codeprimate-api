package org.codeprimate.lang.concurrent;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicBoolean;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The ThreadUtilsTest class is a test suite of test cases for testing the contract and functionality of the ThreadUtils
 * class.
 *
 * @author John J. Blum
 * @see org.codeprimate.lang.concurrent.ThreadUtils
 * @see org.jmock.Mockery
 * @see org.junit.Test
 * @see edu.umd.cs.mtc.MultithreadedTestCase
 * @see edu.umd.cs.mtc.TestFramework
 * @since 1.0.0
 */
public class ThreadUtilsTest {

  protected Mockery mockContext;

  @Before
  public void setup() {
    mockContext = new Mockery() {{
      setImposteriser(ClassImposteriser.INSTANCE);
    }};
  }

  @After
  public void tearDown() {
    mockContext.assertIsSatisfied();
  }

  @Test
  public void testGetThreadNameWithNull() {
    assertNull(ThreadUtils.getName(null));
  }

  @Test
  public void testGetThreadNameWithThread() {
    assertNotNull(ThreadUtils.getName(Thread.currentThread()));
  }

  @Test
  public void testInterruptWithNullThread() {
    ThreadUtils.interrupt(null);
  }

  @Test
  public void testInterruptWithNonNullThread() {
    final Thread mockThread = mockContext.mock(Thread.class, "Interrupted Thread");

    mockContext.checking(new Expectations() {{
      oneOf(mockThread).interrupt();
    }});

    ThreadUtils.interrupt(mockThread);
  }

  @Test
  public void testIsAlive() {
    assertTrue(ThreadUtils.isAlive(Thread.currentThread()));
  }

  @Test
  public void testIsAliveWithNullThread() {
    assertFalse(ThreadUtils.isAlive(null));
  }

  @Test
  public void testIsAliveWithUnstartedThread() {
    final Thread thread = new Thread(new Runnable() {
      public void run() {
      }
    });
    assertFalse(ThreadUtils.isAlive(thread));
  }

  @Test
  public void testIsAliveWithStoppedThread() throws InterruptedException {
    final AtomicBoolean ran = new AtomicBoolean(false);

    final Thread thread = new Thread(new Runnable() {
      public void run() {
        ran.set(true);
      }
    });

    thread.start();
    thread.join(50);

    assertFalse(ThreadUtils.isAlive(thread));
    assertTrue(ran.get());
  }

  @Test
  public void testSleep() {
    final long t0 = System.currentTimeMillis();
    final long sleepDuration = ThreadUtils.sleep(500);
    final long t1 = System.currentTimeMillis();

    assertTrue((t1 - t0) >= 500);
    assertTrue(sleepDuration >= 500);
  }

  @Test
  public void testSleepWithInterrupt() throws Throwable {
    TestFramework.runOnce(new SleepInterruptedMultithreadedTestCase(5 * 1000));
  }

  @SuppressWarnings("unused")
  protected static final class SleepInterruptedMultithreadedTestCase extends MultithreadedTestCase {

    private final long expectedSleepDuration;
    private volatile long actualSleepDuration;

    private volatile Thread sleeperThread;

    public SleepInterruptedMultithreadedTestCase(final long expectedSleepDuration) {
      assert expectedSleepDuration > 0 : "The duration of sleep must be greater than equal to 0!";
      this.expectedSleepDuration = expectedSleepDuration;
    }

    public void thread1() {
      assertTick(0);

      sleeperThread = Thread.currentThread();
      sleeperThread.setName("Sleeper Thread");
      actualSleepDuration = ThreadUtils.sleep(expectedSleepDuration);
    }

    public void thread2() {
      assertTick(0);

      Thread.currentThread().setName("Interrupting Thread");
      sleeperThread.interrupt();
    }

    @Override
    public void finish() {
      assert(actualSleepDuration <= expectedSleepDuration);
    }
  }

}
