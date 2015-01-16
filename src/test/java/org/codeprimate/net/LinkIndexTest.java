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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codeprimate.net.protocols.http.HttpMethod;
import org.codeprimate.net.protocols.http.Link;
import org.codeprimate.net.protocols.http.LinkIndex;
import org.codeprimate.test.AbstractWebBasedTestCase;
import org.codeprimate.util.CollectionUtils;
import org.junit.Test;

/**
 * The LinkIndexTest class is a test suite of test cases testing the contract and functionality of the LinkIndex class.
 *
 * @author John J. Blum
 * @see org.codeprimate.net.protocols.http.Link
 * @see org.codeprimate.net.protocols.http.LinkIndex
 * @see org.codeprimate.test.AbstractWebBasedTestCase
 * @see org.junit.Test
 * @since 1.1.0
 */
public class LinkIndexTest extends AbstractWebBasedTestCase {

  @Test
  public void testAdd() throws Exception {
    Link link = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"));

    LinkIndex linkIndex = new LinkIndex();

    assertTrue(linkIndex.isEmpty());
    assertEquals(0, linkIndex.size());
    assertEquals(linkIndex, linkIndex.add(link));
    assertFalse(linkIndex.isEmpty());
    assertEquals(1, linkIndex.size());
    assertEquals(linkIndex, linkIndex.add(link)); // test duplicate addition
    assertFalse(linkIndex.isEmpty());
    assertEquals(1, linkIndex.size());
  }

  @Test(expected = NullPointerException.class)
  public void testAddNullLink() {
    LinkIndex linkIndex = new LinkIndex();

    assertTrue(linkIndex.isEmpty());

    try {
      linkIndex.add(null);
    }
    finally {
      assertTrue(linkIndex.isEmpty());
    }
  }

  @Test
  public void testAddAll() throws Exception {
    Link create = new Link("create-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.POST);
    Link retrieve = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"));
    Link update = new Link("update-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.PUT);
    Link delete = new Link("delete-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"), HttpMethod.DELETE);

    LinkIndex linkIndex = new LinkIndex();

    assertTrue(linkIndex.isEmpty());
    assertEquals(linkIndex, linkIndex.addAll(create, retrieve, update, delete));
    assertFalse(linkIndex.isEmpty());
    assertEquals(4, linkIndex.size());
  }

  @Test(expected = NullPointerException.class)
  public void testAddAllWithNullLinks() {
    LinkIndex linkIndex = new LinkIndex();

    assertTrue(linkIndex.isEmpty());

    try {
      linkIndex.addAll((Iterable<Link>) null);
    }
    finally {
      assertTrue(linkIndex.isEmpty());
    }
  }

  @Test
  public void testFind() throws Exception {
    Link list = new Link("get-resources", toUri("http://host.domain.com:port/service/v1/resources"));
    Link create = new Link("create-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.POST);
    Link read = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"));
    Link update = new Link("update-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.PUT);
    Link delete = new Link("delete-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"), HttpMethod.DELETE);

    LinkIndex linkIndex = new LinkIndex();

    assertTrue(linkIndex.isEmpty());
    assertEquals(linkIndex, linkIndex.addAll(list, create, read, update, delete));
    assertFalse(linkIndex.isEmpty());
    assertEquals(5, linkIndex.size());
    assertEquals(list, linkIndex.find("get-resources"));
    assertEquals(read, linkIndex.find("get-resource"));
    assertEquals(update, linkIndex.find("UPDATE-RESOURCE"));
    assertEquals(delete, linkIndex.find("Delete-Resource"));
    assertNull(linkIndex.find("destroy-resource"));
    assertNull(linkIndex.find("save-resource"));
  }

  @Test
  public void testFindAll() throws Exception {
    Link list = new Link("get-resources", toUri("http://host.domain.com:port/service/v1/resources"));
    Link create = new Link("create-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.POST);
    Link readById = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"));
    Link readByName = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{name}"));
    Link update = new Link("update-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.PUT);
    Link delete = new Link("delete-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"), HttpMethod.DELETE);

    LinkIndex linkIndex = new LinkIndex();

    assertTrue(linkIndex.isEmpty());
    assertEquals(linkIndex, linkIndex.addAll(list, create, readById, readByName, update, delete));
    assertFalse(linkIndex.isEmpty());
    assertEquals(6, linkIndex.size());

    Link[] retrieveLinks = linkIndex.findAll("get-resource");

    assertNotNull(retrieveLinks);
    assertEquals(2, retrieveLinks.length);
    assertTrue(Arrays.asList(retrieveLinks).containsAll(Arrays.asList(readById, readByName)));

    Link[] saveLinks = linkIndex.findAll("save-resource");

    assertNotNull(saveLinks);
    assertEquals(0, saveLinks.length);
  }

  @Test
  public void testIterator() throws Exception {
    Link list = new Link("get-resources", toUri("http://host.domain.com:port/service/v1/resources"));
    Link create = new Link("create-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.POST);
    Link readById = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"));
    Link readByName = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{name}"));
    Link update = new Link("update-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.PUT);
    Link delete = new Link("delete-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"), HttpMethod.DELETE);

    LinkIndex linkIndex = new LinkIndex();

    assertTrue(linkIndex.isEmpty());
    assertEquals(linkIndex, linkIndex.addAll(list, create, readById, readByName, update, delete));
    assertFalse(linkIndex.isEmpty());
    assertEquals(6, linkIndex.size());

    Collection<Link> expectedLinks = Arrays.asList(list, create, readById, readByName, update, delete);

    Collection<Link> actualLinks = new ArrayList<Link>(linkIndex.size());

    for (Link link : linkIndex) {
      actualLinks.add(link);
    }

    assertTrue(actualLinks.containsAll(expectedLinks));
  }

  @Test
  public void testToList() throws Exception {
    Link list = new Link("get-resources", toUri("http://host.domain.com:port/service/v1/resources"));
    Link create = new Link("create-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.POST);
    Link readById = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"));
    Link readByName = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{name}"));
    Link update = new Link("update-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.PUT);
    Link delete = new Link("delete-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"), HttpMethod.DELETE);

    LinkIndex linkIndex = new LinkIndex();

    assertTrue(linkIndex.isEmpty());
    assertEquals(linkIndex, linkIndex.addAll(list, create, readById, readByName, update, delete));
    assertFalse(linkIndex.isEmpty());
    assertEquals(6, linkIndex.size());

    List<Link> expectedList = CollectionUtils.asList(list, create, readById, readByName, update, delete);

    Collections.sort(expectedList);

    assertEquals(expectedList, linkIndex.toList());
  }

  @Test
  public void testToMap() throws Exception {
    Link list = new Link("get-resources", toUri("http://host.domain.com:port/service/v1/resources"));
    Link create = new Link("create-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.POST);
    Link readById = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"));
    Link readByName = new Link("get-resource", toUri("http://host.domain.com:port/service/v1/resources/{name}"));
    Link update = new Link("update-resource", toUri("http://host.domain.com:port/service/v1/resources"), HttpMethod.PUT);
    Link delete = new Link("delete-resource", toUri("http://host.domain.com:port/service/v1/resources/{id}"), HttpMethod.DELETE);

    LinkIndex linkIndex = new LinkIndex();

    assertTrue(linkIndex.isEmpty());
    assertEquals(linkIndex, linkIndex.addAll(list, create, readById, readByName, update, delete));
    assertFalse(linkIndex.isEmpty());
    assertEquals(6, linkIndex.size());

    Map<String, List<Link>> expectedMap = new HashMap<String, List<Link>>(5);

    expectedMap.put("get-resources", Arrays.asList(list));
    expectedMap.put("create-resource", Arrays.asList(create));
    expectedMap.put("get-resource", Arrays.asList(readById, readByName));
    expectedMap.put("update-resource", Arrays.asList(update));
    expectedMap.put("delete-resource", Arrays.asList(delete));

    assertEquals(expectedMap, linkIndex.toMap());
  }

}
