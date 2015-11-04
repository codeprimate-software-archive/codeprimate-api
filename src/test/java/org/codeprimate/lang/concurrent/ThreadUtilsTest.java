/*
 * Copyright 2014-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codeprimate.lang.concurrent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
@RunWith(MockitoJUnitRunner.class)
public class ThreadUtilsTest {

  @Mock
  private Thread mockThread;

  @Test
  public void getThreadNameWithNull() {
    assertNull(ThreadUtils.getName(null));
  }

  @Test
  public void getThreadNameWithThread() {
    assertNotNull(ThreadUtils.getName(Thread.currentThread()));
  }

  @Test
  public void interruptWithNullThread() {
    ThreadUtils.interrupt(null);
  }

  @Test
  public void interruptWithNonNullThread() {
    ThreadUtils.interrupt(mockThread);

    verify(mockThread, times(1)).interrupt();
  }

  @Test
  public void isAlive() {
    assertTrue(ThreadUtils.isAlive(Thread.currentThread()));
  }

  @Test
  public void isAliveWithNullThread() {
    assertFalse(ThreadUtils.isAlive(null));
  }

  @Test
  public void isAliveWithNonStartedThread() {
    Thread thread = new Thread(() -> {});
    assertFalse(ThreadUtils.isAlive(thread));
  }

  @Test
  public void isAliveWithStoppedThread() throws InterruptedException {
    AtomicBoolean ran = new AtomicBoolean(false);

    Thread thread = new Thread(() -> ran.set(true));

    thread.start();
    thread.join(50);

    assertFalse(ThreadUtils.isAlive(thread));
    assertTrue(ran.get());
  }

  @Test
  public void sleep() {
    final long t0 = System.currentTimeMillis();
    final long sleepDuration = ThreadUtils.sleep(500);
    final long t1 = System.currentTimeMillis();

    assertTrue((t1 - t0) >= 500);
    assertTrue(sleepDuration >= 500);
  }

  @Test
  public void sleepWithInterrupt() throws Throwable {
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
      ThreadUtils.waitFor(TimeUnit.SECONDS.toMillis(5)).checkEvery(500, TimeUnit.MILLISECONDS).on(() -> sleeperThread != null);
      sleeperThread.interrupt();
    }

    @Override
    public void finish() {
      assert(actualSleepDuration <= expectedSleepDuration);
    }
  }

}
