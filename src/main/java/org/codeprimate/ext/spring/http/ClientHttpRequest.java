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

package org.codeprimate.ext.spring.http;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codeprimate.lang.Assert;
import org.codeprimate.lang.ObjectUtils;
import org.codeprimate.net.UriUtils;
import org.codeprimate.net.protocols.http.Link;
import org.codeprimate.util.CollectionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

/**
 * The ClientHttpRequest class is an abstraction modeling an HTTP request sent by a client and serves as the envelop
 * encapsulating all the necessary information (headers, request parameters, body, etc) to send the client's request
 * using HTTP.
 * 
 * The required information for an HTTP request comes from a combination of the Link class containing the reference
 * uniquely identifying the resource or location of where the request will be sent, along with the HttpHeaders class
 * capturing the headers for the request as well as the generic container, HttpEntity to write the body of the request.
 * 
 * This implementation of HttpRequest should not be confused with Spring's
 * org.springframework.http.client.ClientHttpRequest interface, which is often created by factory using a specific
 * HTTP client technology, like the Java HttpURLConnection or Apache's HTTP components, and so on.
 * 
 * @author John J. Blum
 * @see java.net.URI
 * @see org.codeprimate.lang.Filter
 * @see org.codeprimate.net.UriUtils
 * @see org.codeprimate.net.protocols.http.Link
 * @see org.springframework.http.HttpEntity
 * @see org.springframework.http.HttpHeaders
 * @see org.springframework.http.HttpMethod
 * @see org.springframework.http.HttpRequest
 * @see org.springframework.http.MediaType
 * @see org.springframework.util.MultiValueMap
 * @see org.springframework.web.util.UriComponentsBuilder
 * @see org.springframework.web.util.UriTemplate
 * @since 1.2.0
 */
@SuppressWarnings("unused")
public class ClientHttpRequest implements HttpRequest {

  // the HTTP headers to be sent with the client's request message
  private final HttpHeaders requestHeaders = new HttpHeaders();

  // the Link referencing the URI and method used with HTTP for the client's request
  private final Link link;

  // the mapping of request parameter name and values encoded for HTTP and sent with/in the client's request message
  private final MultiValueMap<String, Object> requestParameters = new LinkedMultiValueMap<>();

  // the content/media or payload for the body of the client's HTTP request
  private Object content;

  /**
   * Constructs an instance of the ClientHttpRequest class initialized with the specified Link containing the URI
   * and method for the client's HTTP request.
   * 
   * @param link the Link encapsulating the URI and method for the client's HTTP request.
   * @see org.codeprimate.net.protocols.http.Link
   */
  public ClientHttpRequest(final Link link) {
    Assert.notNull(link, "The Link containing the URI and method for the client's HTTP request cannot be null!");
    this.link = link;
  }

  /**
   * Gets the HTTP headers that will be sent in the client's HTTP request message.
   * 
   * @return the HTTP headers that will be sent in the client's HTTP request message.
   * @see org.springframework.http.HttpHeaders
   * @see org.springframework.http.HttpMessage#getHeaders()
   */
  @Override
  public HttpHeaders getHeaders() {
    return requestHeaders;
  }

  /**
   * Gets the Link containing the URI and method used to send the client's HTTP request.
   * 
   * @return the Link encapsulating the URI and method for the client's HTTP request.
   * @see org.codeprimate.net.protocols.http.Link
   */
  public Link getLink() {
    return link;
  }

  /**
   * Gets the HTTP method indicating the operation to perform on the resource identified in the client's HTTP request.
   * This method converts HttpMethod enumerated value from the Link into a corresponding Spring HttpMethod
   * enumerated value.
   * 
   * @return a Spring HttpMethod enumerated value indicating the operation to perform on the resource identified in the
   * client's HTTP request.
   * @see org.codeprimate.net.protocols.http.Link#getMethod()
   * @see org.springframework.http.HttpMethod
   * @see org.springframework.http.HttpRequest#getMethod()
   */
  @Override
  public org.springframework.http.HttpMethod getMethod() {
    switch (getLink().getMethod()) {
      case DELETE:
        return org.springframework.http.HttpMethod.DELETE;
      case HEAD:
        return org.springframework.http.HttpMethod.HEAD;
      case OPTIONS:
        return org.springframework.http.HttpMethod.OPTIONS;
      case POST:
        return org.springframework.http.HttpMethod.POST;
      case PUT:
        return org.springframework.http.HttpMethod.PUT;
      case TRACE:
        return org.springframework.http.HttpMethod.TRACE;
      case GET:
      default:
        return org.springframework.http.HttpMethod.GET;
    }
  }

  /**
   * Determines whether this is an HTTP DELETE request.
   * 
   * @return a boolean value indicating if the HTTP method is DELETE.
   * @see #getMethod()
   * @see org.springframework.http.HttpMethod#DELETE
   */
  public boolean isDelete() {
    return org.springframework.http.HttpMethod.DELETE.equals(getMethod());
  }

  /**
   * Determines whether this is an HTTP GET request.
   * 
   * @return a boolean value indicating if the HTTP method is GET.
   * @see #getMethod()
   * @see org.springframework.http.HttpMethod#GET
   */
  public boolean isGet() {
    return org.springframework.http.HttpMethod.GET.equals(getMethod());
  }

  /**
   * Determines whether this is an HTTP POST request.
   * 
   * @return a boolean value indicating if the HTTP method is POST.
   * @see #getMethod()
   * @see org.springframework.http.HttpMethod#POST
   */
  public boolean isPost() {
    return org.springframework.http.HttpMethod.POST.equals(getMethod());
  }

  /**
   * Determines whether this is an HTTP PUT request.
   * 
   * @return a boolean value indicating if the HTTP method is PUT.
   * @see #getMethod()
   * @see org.springframework.http.HttpMethod#PUT
   */
  public boolean isPut() {
    return org.springframework.http.HttpMethod.PUT.equals(getMethod());
  }

  /**
   * Gets the request parameters that will be sent in the client's HTTP request message.
   * 
   * @return a MultiValueMap of request parameters and values that will be sent in the client's HTTP request message.
   * @see org.springframework.util.MultiValueMap
   */
  public MultiValueMap<String, Object> getParameters() {
    return requestParameters;
  }

  /**
   * Gets the path variables in the URI template.  Note, this would be better placed in the Link class, but Link cannot
   * contain an Spring dependencies!
   * 
   * @return a List of Strings for each path variable in the URI template.
   * @see #getURI()
   * @see org.springframework.web.util.UriTemplate
   */
  protected List<String> getPathVariables() {
    return Collections.unmodifiableList(new UriTemplate(UriUtils.decode(getURI().toString())).getVariableNames());
  }

  /**
   * Gets the URI for the client's HTTP request.  The URI may actually be an encoded URI template containing
   * path variables requiring expansion.
   * 
   * @return the URI of the resource targeted in the request by the client using HTTP.
   * @see java.net.URI
   * @see org.springframework.http.HttpRequest#getURI()
   */
  @Override
  public URI getURI() {
    return getLink().getHref();
  }

  /**
   * Gets the URL for the client's HTTP request.
   * 
   * @return a URL as a URI referring to the location of the resource requested by the client via HTTP.
   * @see #getURL(java.util.Map)
   * @see java.net.URI
   */
  public URI getURL() {
    return getURL(Collections.<String, Object>emptyMap());
  }

  /**
   * Gets the URL for the client's HTTP request.
   * 
   * @param uriVariables a Map of URI path variables to values in order to expand the URI template into a URI.
   * @return a URL as a URI referring to the location of the resource requested by the client via HTTP.
   * @see #getURI()
   * @see java.net.URI
   * @see org.springframework.web.util.UriComponents
   * @see org.springframework.web.util.UriComponentsBuilder
   */
  public URI getURL(final Map<String, ?> uriVariables) {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(UriUtils.decode(getURI().toString()));

    if (isGet() || isDelete()) {
      final List<String> pathVariables = getPathVariables();

      // get query parameters to append to the URI/URL based on the request parameters that are not path variables...
      Map<String, List<Object>> queryParameters = CollectionUtils.filter(
        new LinkedMultiValueMap<>(getParameters()), (Map.Entry<String, List<Object>> entry) ->
          !pathVariables.contains(entry.getKey()));

      for (String queryParameterName : queryParameters.keySet()) {
        uriBuilder.queryParam(queryParameterName, getParameters().get(queryParameterName).toArray());
      }
    }

    return uriBuilder.build().expand(UriUtils.encode(new HashMap<>(uriVariables))).encode().toUri();
  }

  /**
   * Gets the HTTP request entity encapsulating the headers and body of the HTTP message.  The body of the HTTP request
   * message will consist of an URL encoded application form (a mapping of key-value pairs) for POST/PUT HTTP requests.
   * 
   * @return an HttpEntity with the headers and body for the HTTP request message.
   * @see #getParameters()
   * @see org.springframework.http.HttpEntity
   * @see org.springframework.http.HttpHeaders
   */
  public HttpEntity<?> createRequestEntity() {
    if (isPost() || isPut()) {
      // NOTE HTTP request parameters take precedence over HTTP message body content/media
      if (!getParameters().isEmpty()) {
        getHeaders().setContentType(determineContentType(MediaType.APPLICATION_FORM_URLENCODED));
        return new HttpEntity<>(getParameters(), getHeaders());
      }
      else {
        // NOTE the HTTP "Content-Type" header will be determined and set by the appropriate HttpMessageConverter
        // based on the Class type of the "content".
        return new HttpEntity<>(getContent(), getHeaders());
      }
    }
    else {
      return new HttpEntity<>(getHeaders());
    }
  }

  /**
   * Tries to determine the content/media type of this HTTP request iff the HTTP "Content-Type" header was not
   * explicitly set by the user, otherwise the user provided value is used.  If the "Content-Type" HTTP header value
   * is null, then the content/media/payload of this HTTP request is inspected to determine the content type.
   * 
   * The simplest evaluation sets the content type to "application/x-www-form-urlencoded" if this is a POST or PUT
   * HTTP request, unless any request parameter value is determined to have multiple parts, the the content type will be
   * "multipart/form-data".
   * 
   * @param defaultContentType the default content/media type to use when the content type cannot be determined from
   * this HTTP request.
   * @return a MediaType for the value of the HTTP Content-Type header as determined from this HTTP request.
   * @see #getHeaders()
   * @see org.springframework.http.HttpHeaders#getContentType()
   * @see org.springframework.http.MediaType
   */
  protected MediaType determineContentType(final MediaType defaultContentType) {
    MediaType contentType = getHeaders().getContentType();

    // if the content type HTTP header was not explicitly set, try to determine the media type from the content body
    // of the HTTP request
    if (contentType == null) {
      if (isPost() || isPut()) {
        OUT : for (String name : getParameters().keySet()) {
          for (Object value : getParameters().get(name)) {
            if (value != null && !(value instanceof String)) {
              contentType = MediaType.MULTIPART_FORM_DATA;
              break OUT;
            }
          }
        }

        // since this is a POST/PUT HTTP request, default the content/media type to "application/x-www-form-urlencoded"
        contentType = ObjectUtils.defaultIfNull(contentType, MediaType.APPLICATION_FORM_URLENCODED);
      }
      // NOTE the "Content-Type" HTTP header is not applicable to GET/DELETE and other methods of HTTP requests
      // since there is typically no content (media/payload/request body/etc) to send.  Any request parameters
      // are encoded in the URL as query parameters.
    }

    return ObjectUtils.defaultIfNull(contentType, defaultContentType);
  }

  public Object getContent() {
    return content;
  }

  public void setContent(final Object content) {
    this.content = content;
  }

  /**
   * Adds 1 or more values for the specified HTTP header.
   * 
   * @param headerName a String specifying the name of the HTTP header.
   * @param headerValues the array of values to set for the HTTP header.
   * @see org.springframework.http.HttpHeaders#add(String, String)
   */
  public void addHeaderValues(final String headerName, final String... headerValues) {
    if (headerValues != null) {
      for (String headerValue : headerValues) {
        getHeaders().add(headerName, headerValue);
      }
    }
  }

  /**
   * Gets the first value for the specified HTTP header or null if the HTTP header is not set.
   * 
   * @param headerName a String specifying the name of the HTTP header.
   * @return the first value in the list of values for the HTTP header, or null if the HTTP header is not set.
   * @see org.springframework.http.HttpHeaders#getFirst(String)
   */
  public String getHeaderValue(final String headerName) {
    return getHeaders().getFirst(headerName);
  }

  /**
   * Gets all values for the specified HTTP header or an empty List if the HTTP header is not set.
   * 
   * @param headerName a String specifying the name of the HTTP header.
   * @return a list of String values for the specified HTTP header.
   * @see org.springframework.http.HttpHeaders#get(Object)
   */
  public List<String> getHeaderValues(final String headerName) {
    return Collections.unmodifiableList(getHeaders().get(headerName));
  }

  /**
   * Sets the specified HTTP header to the given value, overriding any previously set values for the HTTP header.
   * 
   * @param headerName a String specifying the name of the HTTP header.
   * @param headerValue a String containing the value of the HTTP header.
   * @see org.springframework.http.HttpHeaders#set(Object, Object)
   */
  public void setHeader(final String headerName, final String headerValue) {
    getHeaders().set(headerName, headerValue);
  }

  /**
   * Adds 1 or more parameter values to the HTTP request.
   * 
   * @param requestParameterName a String specifying the name of the HTTP request parameter.
   * @param requestParameterValues the array of values to set for the HTTP request parameter.
   * @see org.springframework.util.MultiValueMap#add(Object, Object)
   */
  public void addParameterValues(final String requestParameterName, final Object... requestParameterValues) {
    if (requestParameterValues != null) {
      for (Object requestParameterValue : requestParameterValues) {
        getParameters().add(requestParameterName, requestParameterValue);
      }
    }
  }

  /**
   * Gets the first value for the specified HTTP request parameter or null if the HTTP request parameter is not set.
   * 
   * @param requestParameterName a String specifying the name of the HTTP request parameter.
   * @return the first value in the list of values for the HTTP request parameter, or null if the HTTP request parameter
   * is not set.
   * @see org.springframework.util.MultiValueMap#getFirst(Object)
   */
  public Object getParameterValue(final String requestParameterName) {
    return getParameters().getFirst(requestParameterName);
  }

  /**
   * Gets all values for the specified HTTP request parameter or an empty List if the HTTP request parameter is not set.
   * 
   * @param requestParameterName a String specifying the name of the HTTP request parameter.
   * @return a list of String values for the specified HTTP request parameter.
   * @see org.springframework.util.MultiValueMap#get(Object)
   */
  public List<Object> getParameterValues(final String requestParameterName) {
    return Collections.unmodifiableList(getParameters().get(requestParameterName));
  }

  /**
   * Sets the specified HTTP request parameter to the given value, overriding any previously set values for
   * the HTTP request parameter.
   * 
   * @param name a String specifying the name of the HTTP request parameter.
   * @param value a String containing the value of the HTTP request parameter.
   * @see org.springframework.util.MultiValueMap#set(Object, Object)
   */
  public void setParameter(final String name, final Object value) {
    getParameters().set(name, value);
  }

}
