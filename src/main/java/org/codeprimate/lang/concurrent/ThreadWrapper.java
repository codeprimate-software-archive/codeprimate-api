package org.codeprimate.lang.concurrent;

import java.lang.Thread.State;
import java.lang.Thread.UncaughtExceptionHandler;

import org.codeprimate.lang.Assert;

/**
 * The ThreadWrapper class is a wrapper around a Java Thread.
 *
 * @author John Blum
 * @see java.lang.Thread
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ThreadWrapper {

  private final Thread thread;

  public ThreadWrapper() {
    this(Thread.currentThread());
  }

  public ThreadWrapper(final Thread delegate) {
    Assert.notNull(delegate, "The Thread wrapped by this wrapper must not be null!");
    this.thread = delegate;
  }

  public boolean isAlive() {
    return thread.isAlive();
  }

  public boolean isBlocked() {
    return State.BLOCKED.equals(getState());
  }

  public boolean isDaemon() {
    return thread.isDaemon();
  }

  public boolean isInterrupted() {
    return thread.isInterrupted();
  }

  public boolean isInTimedWait() {
    return State.TIMED_WAITING.equals(getState());
  }

  public boolean isNew() {
    return State.NEW.equals(getState());
  }

  public boolean isNonDaemon() {
    return !isDaemon();
  }

  public boolean isRunnable() {
    return State.RUNNABLE.equals(getState());
  }

  public boolean isTerminated(final Thread thread) {
    return State.TERMINATED.equals(thread.getState());
  }

  /**
   * Determines whether the specified Thread is in a waiting state, guarding against null Thread references
   *
   * @return a boolean value indicating whether the Thread is in a waiting state.  If the Thread object reference
   * is null, then this method returns false since no Thread is clearly not waiting for anything.
   * @see java.lang.Thread#getState()
   * @see java.lang.Thread.State#WAITING
   */
  public boolean isWaiting() {
    return State.WAITING.equals(getState());
  }

  public ClassLoader getContextClassLoader() {
    return thread.getContextClassLoader();
  }

  public long getId() {
    return thread.getId();
  }

  public String getName() {
    return thread.getName();
  }

  public int getPriority() {
    return thread.getPriority();
  }

  public StackTraceElement[] getStackTrace() {
    return thread.getStackTrace();
  }

  public Thread.State getState() {
    return thread.getState();
  }

  public ThreadGroup getThreadGroup() {
    return thread.getThreadGroup();
  }

  public UncaughtExceptionHandler getUncaughtExceptionHandler() {
    return thread.getUncaughtExceptionHandler();
  }

  public void interrupt() {
    thread.interrupt();
  }

  public void join() throws InterruptedException {
    thread.join();
  }

  public void join(final int milliseconds) throws InterruptedException {
    thread.join(milliseconds);
  }

  public void join(final int milliseconds, final int nanoseconds) throws InterruptedException {
    thread.join(milliseconds, nanoseconds);
  }

  public void run() {
    thread.run();
  }

  public void start() {
    thread.start();
  }

  @Override
  public String toString() {
    return String.format("{ id = %1$d, name = %2$s, daemon = %3$s, priority = %4$d, state = %5$s }",
      getId(), getName(), isDaemon(), getPriority(), getState());
  }

}
