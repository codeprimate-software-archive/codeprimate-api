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

package org.codeprimate.net;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.codeprimate.net.protocols.http.HttpMethod;
import org.codeprimate.net.protocols.http.Link;
import org.codeprimate.test.AbstractWebBasedTestCase;
import org.codeprimate.util.CollectionUtils;
import org.junit.Test;

/**
 * The LinkTest class is a test suite of test cases testing the contract and functionality of the Link class.
 *
 * @author John J. Blum
 * @see java.net.URI
 * @see org.codeprimate.net.protocols.http.Link
 * @see org.codeprimate.test.AbstractWebBasedTestCase
 * @see org.junit.Test
 * @since 1.1.0
 */
public class LinkTest extends AbstractWebBasedTestCase {

  @Test
  public void testConstructDefaultLink() {
    Link link = new Link();

    assertNotNull(link);
    assertNull(link.getHref());
    assertEquals(HttpMethod.GET, link.getMethod());
    assertNull(link.getRelation());
  }

  @Test
  public void testConstructLinkWithRelationAndHref() throws Exception {
    Link link = new Link("get-resource", toUri("http://host:port/service/v1/resources/{id}"));

    assertNotNull(link);
    assertEquals("http://host:port/service/v1/resources/{id}", toString(link.getHref()));
    assertEquals(HttpMethod.GET, link.getMethod());
    assertEquals("get-resource", link.getRelation());
  }

  @Test
  public void testConstructLinkWithRelationHrefAndMethod() throws Exception {
    Link link = new Link("create-resource", toUri("http://host:port/service/v1/resources"), HttpMethod.POST);

    assertNotNull(link);
    assertEquals("http://host:port/service/v1/resources", toString(link.getHref()));
    assertEquals(HttpMethod.POST, link.getMethod());
    assertEquals("create-resource", link.getRelation());
  }

  @Test(expected = NullPointerException.class)
  public void testHrefWithNull() {
    try {
      new Link().setHref(null);
    }
    catch (NullPointerException expected) {
      assertEquals("The Link URI-based 'href' cannot be null!", expected.getMessage());
      throw expected;
    }
  }

  @Test
  public void testSetAndGetMethod() {
    Link link = new Link();

    assertNotNull(link);
    assertEquals(HttpMethod.GET, link.getMethod());

    link.setMethod(HttpMethod.POST);

    assertEquals(HttpMethod.POST, link.getMethod());

    link.setMethod(null);

    assertEquals(HttpMethod.GET, link.getMethod());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetRelationWithUnspecifiedValue() {
    try {
      new Link().setRelation("  ");
    }
    catch (IllegalArgumentException expected) {
      assertEquals("The Link relation (rel) must be specified!", expected.getMessage());
      throw expected;
    }
  }

  @Test
  public void testCompareTo() throws Exception {
    Link link0 = new Link("resources", toUri("http://host:port/service/v1/resources"));
    Link link1 = new Link("resource", toUri("http://host:port/service/v1/resources"), HttpMethod.POST);
    Link link2 = new Link("resource", toUri("http://host:port/service/v1/resources/{id}"));
    Link link3 = new Link("resource", toUri("http://host:port/service/v1/resources/{name}"));
    Link link4 = new Link("resource", toUri("http://host:port/service/v1/resources/{id}"), HttpMethod.DELETE);

    List<Link> expectedList = new ArrayList<Link>(Arrays.asList(link1, link4, link2, link3, link0));
    List<Link> actualList = CollectionUtils.asList(link0, link1, link2, link3, link4);

    Collections.sort(actualList);

    /*
    System.out.println(toString(expectedList.toArray(new Link[expectedList.size()])));
    System.out.println(toString(actualList.toArray(new Link[actualList.size()])));
    */

    assertEquals(expectedList, actualList);
  }

  @Test
  public void testToHttpRequestLine() throws Exception {
    Link link = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"));

    assertNotNull(link);
    assertEquals(HttpMethod.GET, link.getMethod());
    assertEquals("http://host.domain.com:port/service/v1/resources/{id}", toString(link.getHref()));
    assertEquals("GET ".concat("http://host.domain.com:port/service/v1/resources/{id}"), link.toHttpRequestLine());
  }

}
