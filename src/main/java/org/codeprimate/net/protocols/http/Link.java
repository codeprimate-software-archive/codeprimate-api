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

import java.io.Serializable;
import java.net.URI;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.codeprimate.lang.Assert;
import org.codeprimate.lang.ObjectUtils;
import org.codeprimate.lang.StringUtils;
import org.codeprimate.net.UriUtils;
import org.codeprimate.util.ComparatorAccumulator;

/**
 * The Link class models hypermedia controls/link relations along with the corresponding HTTP operation (method)
 * used to operate on the resource identified at the specified link.
 *
 * @author John J. Blum
 * @see java.lang.Comparable
 * @see java.io.Serializable
 * @see java.net.URI
 * @see javax.xml.bind.annotation.XmlAttribute
 * @see javax.xml.bind.annotation.XmlType
 * @since 1.1.0
 */
@SuppressWarnings("unused")
@XmlType(name = "link", propOrder = { "method", "href", "relation" })
public class Link implements Comparable<Link>, Serializable {

  protected static final HttpMethod DEFAULT_HTTP_METHOD = HttpMethod.GET; // READ

  protected static final String HREF_ATTRIBUTE_NAME = "href";
  protected static final String LINK_ELEMENT_NAME = "link";
  protected static final String METHOD_ATTRIBUTE_NAME = "method";
  protected static final String RELATION_ATTRIBUTE_NAME = "rel";

  private HttpMethod method;

  private String relation;

  private URI href;

  public Link() {
  }

  public Link(final String relation, final URI href) {
    this(relation, href, DEFAULT_HTTP_METHOD);
  }

  public Link(final String relation, final URI href, final HttpMethod method) {
    setRelation(relation);
    setHref(href);
    setMethod(method);
  }

  @XmlAttribute(name = HREF_ATTRIBUTE_NAME)
  public URI getHref() {
    return href;
  }

  public final void setHref(final URI href) {
    Assert.notNull(href, "The Link URI-based 'href' cannot be null!");
    this.href = href;
  }

  @XmlAttribute(name = METHOD_ATTRIBUTE_NAME, required = false)
  public HttpMethod getMethod() {
    return ObjectUtils.defaultIfNull(method, DEFAULT_HTTP_METHOD);
  }

  public final void setMethod(final HttpMethod method) {
    this.method = method;
  }

  @XmlAttribute(name = RELATION_ATTRIBUTE_NAME)
  public String getRelation() {
    return relation;
  }

  public final void setRelation(final String relation) {
    Assert.legalArgument(StringUtils.hasText(relation), "The Link relation (rel) must be specified!");
    this.relation = relation;
  }

  @Override
  public int compareTo(final Link link) {
    return new ComparatorAccumulator()
      .doCompare(getRelation(), link.getRelation())
      .doCompare(getHref(), link.getHref())
      .doCompare(getMethod(), link.getMethod())
      .getResult();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Link)) {
      return false;
    }

    Link that = (Link) obj;

    return ObjectUtils.nullSafeEquals(getHref(), that.getHref())
      && ObjectUtils.nullSafeEquals(getMethod(), that.getMethod());
  }

  @Override
  public int hashCode() {
    int hashValue = 17;
    hashValue = 37 * hashValue + ObjectUtils.hashCode(getHref());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(getMethod());
    return hashValue;
  }

  /**
   * The HTTP Request-Line begins with a method token, followed by the Request-URI and the protocol version, and ending
   * with CRLF.  However, this method just returns a String representation similar to the HTTP Request-Line based on
   * values of the Link's properties, which only includes method and request URI.
   *
   * @return a String representation of the HTTP request-line.
   * @see java.net.URI
   * @link http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html
   */
  public String toHttpRequestLine() {
    return String.format("%1$S %2$s", getMethod().name(), UriUtils.decode(getHref().toString()));
  }

  @Override
  public String toString() {
    return String.format("{ class = %1$s, rel = %2$s, href = %3$s, method = %4$s }", getClass().getName(),
      getRelation(), getHref(), getMethod());
  }

}
