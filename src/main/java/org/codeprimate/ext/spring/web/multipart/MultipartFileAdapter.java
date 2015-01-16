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

package org.codeprimate.ext.spring.web.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * The MultipartFileAdapter class is an abstract 'Adapter' class meant for extension when implementing
 * the Spring MultipartFile interface so that implementations only need to override the desired behavior.
 *
 * @author John J. Blum
 * @see org.springframework.web.multipart.MultipartFile
 * @since 1.1.0
 */
@SuppressWarnings("unused")
public abstract class MultipartFileAdapter implements MultipartFile {

  protected static final String NOT_IMPLEMENTED = "Not Implemented";

  public byte[] getBytes() throws IOException {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  public String getContentType() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  public boolean isEmpty() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  public InputStream getInputStream() throws IOException {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  public String getName() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  public String getOriginalFilename() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  public long getSize() {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  public void transferTo(final File destination) throws IOException, IllegalStateException {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

}
