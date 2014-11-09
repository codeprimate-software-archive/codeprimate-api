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
