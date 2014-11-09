package org.codeprimate.process;

import java.util.EventListener;

/**
 * The ProcessInputStreamListener interface is a callback listener that gets called when input arrives from either a
 * process's standard output steam or standard error stream.
 *
 * @author John J. Blum
 * @see java.util.EventListener
 * @since 1.0.0
 */
public interface ProcessInputStreamListener extends EventListener {

	void onInput(String input);

}
