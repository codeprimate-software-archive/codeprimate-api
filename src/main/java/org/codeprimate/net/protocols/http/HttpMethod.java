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

package org.codeprimate.net.protocols.http;

/**
 * The HttpMethod enum is an enumeration of all HTTP protocol methods (POST, GET, PUT, DELETE, HEADERS, etc).
 *
 * @author John J. Blum
 * @since 1.1.0
 */
@SuppressWarnings("unused")
public enum HttpMethod {
  CONNECT,
  DELETE,
  GET,
  HEAD,
  OPTIONS,
  POST,
  PUT,
  TRACE;

  public static HttpMethod valueOfIgnoreCase(final String httpMethodName) {
    for (HttpMethod httpMethod : values()) {
      if (httpMethod.name().equalsIgnoreCase(httpMethodName)) {
        return httpMethod;
      }
    }

    return null;
  }

}
