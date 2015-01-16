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

import java.io.IOException;
import java.io.InputStream;

import org.codeprimate.lang.Assert;
import org.springframework.core.io.AbstractResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * The MultipartFileResourceAdapter class is an 'Adapter' adapting the MultipartFile interface into an instance of
 * the Resource interface in a context where a Resource is required instead.
 *
 * @author John J. Blum
 * @see org.springframework.core.io.AbstractResource
 * @see org.springframework.core.io.Resource
 * @see org.springframework.web.multipart.MultipartFile
 * @since 1.1.0
 */
@SuppressWarnings("unused")
public class MultipartFileResourceAdapter extends AbstractResource {

  private final MultipartFile file;

  public MultipartFileResourceAdapter(final MultipartFile file) {
    Assert.notNull(file, "The MultipartFile to adapt as a Resource must not be null!");
    this.file = file;
  }

  protected MultipartFile getMultipartFile() {
    return file;
  }

  @Override
  public long contentLength() throws IOException {
    return getMultipartFile().getSize();
  }

  @Override
  public String getDescription() {
    return getMultipartFile().getName();
  }

  @Override
  public String getFilename() {
    return getMultipartFile().getOriginalFilename();
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return getMultipartFile().getInputStream();
  }

}
