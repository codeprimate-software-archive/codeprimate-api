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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This program tests the performance of Thread coordination using the volatile keyword on variables
 * and atomic variables.
 *
 * Run with the following command-line...
 *
 * > java org.codeprimate.lang.concurrent.VolatileVariableVsAtomicVariablePerformanceTest [latch {atomic|volatile}] [vollies {count}]
 *
 * @author John J. Blum
 * @version 1.0.0
 */
public class VolatileVariableVsSynchronizedVsAtomicVariablePerformanceTest {

  private static final long DEFAULT_NUMBER_OF_VOLLIES = 1000000;

  private static long numberOfVollies = DEFAULT_NUMBER_OF_VOLLIES;

  private static final PingPongLatch ATOMIC_VARIABLE_LATCH = new AtomicVariablePingPongLatch();
  private static final PingPongLatch SYNCHRONIZED_VARIABLE_LATCH = new SynchronizedVariablePingPongLatch();
  private static final PingPongLatch VOLATILE_VARIABLE_LATCH = new VolatileVariablePingPongLatch();

  private static final ThreadGroup pingPongThreadGroup = new ThreadGroup("Ping Pong Threads") {
    @Override public void uncaughtException(final Thread t, final Throwable e) {
      System.out.printf("Unhandled exception occurred in Thread (%1$s): %2$s%n", t.getName(), e.getMessage());
      super.uncaughtException(t, e);
    }
  };

  public static void main(final String... args) throws Exception {
    final PingPongLatch latch = parseCommandLineArguments(args);

    if (latch == null || latch == VOLATILE_VARIABLE_LATCH) {
      System.out.println("Testing volatile variable coordination performance between Threads...");
      final long t0 = System.currentTimeMillis();
      startPingPongMatch(VOLATILE_VARIABLE_LATCH);
      final long t1 = System.currentTimeMillis();
      System.out.printf("The Ping Pong Match using volatile variables took (%1$d) ms.%n", (t1 - t0));
    }

    if (latch == null || latch == SYNCHRONIZED_VARIABLE_LATCH) {
      System.out.println("Testing synchronized variable coordination performance between Threads...");
      final long t0 = System.currentTimeMillis();
      startPingPongMatch(SYNCHRONIZED_VARIABLE_LATCH);
      final long t1 = System.currentTimeMillis();
      System.out.printf("The Ping Pong Match using synchronized variables took (%1$d) ms.%n", (t1 - t0));
    }

    if (latch == null || latch == ATOMIC_VARIABLE_LATCH) {
      System.out.println("Testing atomic variable coordination performance between Threads...");
      final long t0 = System.currentTimeMillis();
      startPingPongMatch(ATOMIC_VARIABLE_LATCH);
      final long t1 = System.currentTimeMillis();
      System.out.printf("The Ping Pong Match using atomic variables took (%1$d) ms.%n", (t1 - t0));
    }
  }

  private static PingPongLatch parseCommandLineArguments(final String... args) {
    assert args != null : "The command-line arguments cannot be null!";

    PingPongLatch latch = null;

    for (int index = 0; index < args.length; index++) {
      if ("latch".equalsIgnoreCase(args[index])) {
        latch = "atomic".equalsIgnoreCase(args[++index]) ? ATOMIC_VARIABLE_LATCH : VOLATILE_VARIABLE_LATCH;
      }
      else if ("vollies".equalsIgnoreCase(args[index])) {
        try {
          numberOfVollies = Integer.parseInt(args[++index]);
        }
        catch (NumberFormatException e) {
          numberOfVollies = DEFAULT_NUMBER_OF_VOLLIES;
        }
      }
    }

    return latch;
  }

  private static void startPingPongMatch(final PingPongLatch latch) throws InterruptedException {
    final CountDownLatch mainLatch = new CountDownLatch(1);
    latch.reset();
    createConsumerThread(mainLatch, latch).start();
    createProducerThread(latch).start();
    mainLatch.await();
  }

  private static Runnable createConsumerRunnable(final CountDownLatch mainLatch, final PingPongLatch latch) {
    return new Runnable() {
      public void run() {
        for (long count = numberOfVollies; count > 0; count--) {
          while (!latch.isPong()) {
            // wait for producer to return the ball...
            latch.ready();
          }
          latch.volley();
        }
        mainLatch.countDown();
      }
    };
  }

  private static Thread createConsumerThread(final CountDownLatch mainLatch, final PingPongLatch latch) {
    final Thread consumerThread = new Thread(pingPongThreadGroup, createConsumerRunnable(mainLatch, latch), "Consumer Thread");
    consumerThread.setDaemon(true);
    consumerThread.setPriority(Thread.NORM_PRIORITY);
    return consumerThread;
  }

  private static Runnable createProducerRunnable(final PingPongLatch latch) {
    return new Runnable() {
      public void run() {
        for (long count = numberOfVollies; count > 0; count--) {
          while (!latch.isPing()) {
            // wait for consumer to return the ball...
            latch.ready();
          }
          latch.volley();
        }
      }
    };
  }

  private static Thread createProducerThread(final PingPongLatch latch) {
    final Thread producerThread = new Thread(pingPongThreadGroup, createProducerRunnable(latch), "Producer Thread");
    producerThread.setDaemon(true);
    producerThread.setPriority(Thread.NORM_PRIORITY);
    return producerThread;
  }

  public static interface PingPongLatch {

    public boolean isPing();

    public boolean isPong();

    public void ready();

    public void reset();

    public void volley();

  }

  public static final class VolatileVariablePingPongLatch implements PingPongLatch {

    private volatile boolean latch = true;

    public boolean isPing() {
      return latch;
    }

    public boolean isPong() {
      return !latch;
    }

    public void ready() {
    }

    public void reset() {
      latch = true;
    }

    public void volley() {
      latch = !latch;
    }
  }

  public static final class SynchronizedVariablePingPongLatch implements PingPongLatch {

    private boolean latch = true;

    public synchronized boolean isPing() {
      return latch;
    }

    public synchronized boolean isPong() {
      return !latch;
    }

    public void ready() {
      Thread.yield();
    }

    public synchronized void reset() {
      latch = true;
    }

    public synchronized void volley() {
      latch = !latch;
    }
  }

  public static final class AtomicVariablePingPongLatch implements VolatileVariableVsSynchronizedVsAtomicVariablePerformanceTest.PingPongLatch {

    private final AtomicBoolean latch = new AtomicBoolean(true);

    public boolean isPing() {
      return latch.get();
    }

    public boolean isPong() {
      return !latch.get();
    }

    public void ready() {
    }

    public void reset() {
      latch.set(true);
    }

    public void volley() {
      latch.set(!latch.get());
    }
  }

}
