package org.codeprimate.lang.concurrent;

import java.util.concurrent.TimeUnit;

import org.codeprimate.lang.Assert;

/**
 * The ThreadUtils class is an abstract utility class for interacting with Threads.
 * 
 * @author John J. Blum
 * @see java.lang.Thread
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class ThreadUtils {

  /**
   * Determines whether the specified Thread is alive, guarding against null Thread references.
   *
   * @param thread the Thread to determine for aliveness.
   * @return a boolean value indicating whether the specified Thread is alive.  Will return false if the Thread object
   * reference is null.
   * @see java.lang.Thread#isAlive()
   */
  public static boolean isAlive(final Thread thread) {
    return (thread != null && thread.isAlive());
  }

  public static boolean isDaemon(final Thread thread) {
    return (thread != null && thread.isDaemon());
  }

  public static boolean isNonDaemon(final Thread thread) {
    return (thread != null && !isDaemon(thread));
  }

  public static ClassLoader getContextClassLoader(final Thread thread) {
    return (thread != null ? thread.getContextClassLoader() : null);
  }

  /**
   * Gets the id of the specified Thread or 0 if the Thread object reference is null.
   *
   * @param thread the Thread object whose id is determined.
   * @return a long value indicating the id of the Thread or 0 if the Thread object reference is null.
   * @see java.lang.Thread#getId()
   */
  public static long getId(final Thread thread) {
    return (thread != null ? thread.getId() : 0l);
  }

  /**
   * Gets the name of the specified Thread or null if the Thread object reference is null.
   *
   * @param thread the Thread object whose name is determined.
   * @return a String value indicating the name of the Thread or null if the Thread object reference is null.
   * @see java.lang.Thread#getName()
   */
  public static String getName(final Thread thread) {
    return (thread != null ? thread.getName() : null);
  }

  /**
   * Gets the priority of the specified Thread or 0 if the Thread object reference is null.
   *
   * @param thread the Thread object whose priority is determined.
   * @return a integer value indicating the priority of the Thread or 0 if the Thread object reference is null.
   * @see java.lang.Thread#getPriority()
   */
  public static int getPriority(final Thread thread) {
    return (thread != null ? thread.getPriority() : 0);
  }

  public static StackTraceElement[] getStackTrace(final Thread thread) {
    return (thread != null ? thread.getStackTrace() : new StackTraceElement[0]);
  }

  /**
   * Gets the state of the specified Thread or null if the Thread object reference is null.
   *
   * @param thread the Thread object whose state is determined.
   * @return a Thread.State representing the state of the Thread or null if the Thread object reference is null.
   * @see java.lang.Thread#getState()
   */
  public static Thread.State getState(final Thread thread) {
    return (thread != null ? thread.getState() : null);
  }

  /**
   * Gets the thread group of the specified Thread or null if the Thread object reference is null.
   *
   * @param thread the Thread object whose thread group is determined.
   * @return a ThreadGroup representing the group of the Thread or null if the Thread object reference is null.
   * @see java.lang.Thread#getThreadGroup()
   */
  public static ThreadGroup getThreadGroup(final Thread thread) {
    return (thread != null ? thread.getThreadGroup() : null);
  }

  public static ThreadWrapper thread() {
    return thread(null);
  }

  public static ThreadWrapper thread(final Thread thread) {
    return new ThreadWrapper(thread != null ? thread : Thread.currentThread());
  }

  public static void dumpStack(final String tag) {
    System.err.printf("Thread @ (%1$s): %2$d-%3$s%n", tag, Thread.currentThread().getId(),
      Thread.currentThread().getName());
    Thread.dumpStack();
  }

  /**
   * Interrupts the specified Thread, guarding against null.
   * 
   * @param thread the Thread to interrupt.
   * @see java.lang.Thread#interrupt()
   */
  public static void interrupt(final Thread thread) {
    if (thread != null) {
      thread.interrupt();
    }
  }

  /**
   * Causes the current Thread to sleep for the specified number of milliseconds.  If the current Thread is interrupted
   * during sleep, the interrupt is ignored and the duration, in milliseconds, of the completed sleep is returned.
   * 
   * @param milliseconds an integer value specifying the number of milliseconds the current Thread should sleep.
   * @return a long value indicating duration in milliseconds of completed sleep by the current Thread.
   * @see java.lang.System#currentTimeMillis()
   * @see java.lang.Thread#sleep(long)
   */
  public static long sleep(final long milliseconds) {
    final long t0 = System.currentTimeMillis();

    try {
      Thread.sleep(milliseconds);
    }
    catch (InterruptedException ignore) {
    }

    return (System.currentTimeMillis() - t0);
  }

  public static WaitTask waitFor(final long duration) {
    return waitFor(duration, WaitTask.DEFAULT_TIME_UNIT);
  }

  public static WaitTask waitFor(final long duration, final TimeUnit timeUnit) {
    return new WaitTask().waitFor(duration, timeUnit);
  }

  public static interface CompletableTask {
    boolean isComplete();
  }

  public static class WaitTask {

    protected static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    private long duration;
    private long interval;

    private final Object waitTaskMonitor = new Object();

    private TimeUnit durationTimeUnit;
    private TimeUnit intervalTimeUnit;

    public long getDuration() {
      return duration;
    }

    public TimeUnit getDurationTimeUnit() {
      return durationTimeUnit;
    }

    public long getInterval() {
      return (interval != 0 ? interval : getDuration());
    }

    public TimeUnit getIntervalTimeUnit() {
      return defaultIfNull(intervalTimeUnit, getDurationTimeUnit());
    }

    private static <T> T defaultIfNull(final T value, final T defaultValue) {
      return (value != null ? value : defaultValue);
    }

    public WaitTask waitFor(final long duration) {
      return waitFor(duration, DEFAULT_TIME_UNIT);
    }

    public WaitTask waitFor(final long duration, final TimeUnit durationTimeUnit) {
      Assert.legalArgument(duration > 0, String.format("'Duration' (%1$d) must be greater than 0!", duration));
      this.duration = duration;
      this.durationTimeUnit = defaultIfNull(durationTimeUnit, DEFAULT_TIME_UNIT);
      return this;
    }

    private boolean isValidInterval(final long interval, final TimeUnit intervalTimeUnit) {
      return (interval > 0 && intervalTimeUnit.toMillis(interval) <= this.durationTimeUnit.toMillis(this.duration));
    }

    public WaitTask checkEvery(final long interval) {
      return checkEvery(interval, DEFAULT_TIME_UNIT);
    }

    public WaitTask checkEvery(final long interval, final TimeUnit intervalTimeUnit) {
      this.intervalTimeUnit = defaultIfNull(intervalTimeUnit, DEFAULT_TIME_UNIT);

      Assert.legalArgument(isValidInterval(interval, intervalTimeUnit), String.format(
        "'Interval' (%1$d %2$s) must be greater than 0 and less than equal to 'duration' (%3$d %4$s)!",
        interval, intervalTimeUnit, this.duration, this.durationTimeUnit));

      this.interval = interval;
      this.intervalTimeUnit = intervalTimeUnit;

      return this;
    }

    public boolean on(final CompletableTask completableTask) {
      Assert.notNull(completableTask, "The task to complete must not be null!");

      final long timeout = (System.currentTimeMillis() + getDurationTimeUnit().toMillis(getDuration()));

      while (!completableTask.isComplete() && System.currentTimeMillis() < timeout) {
        try {
          synchronized (waitTaskMonitor) {
            getIntervalTimeUnit().timedWait(waitTaskMonitor, getInterval());
          }
        }
        catch (InterruptedException ignore) {
        }
      }

      return completableTask.isComplete();
    }
  }

}
