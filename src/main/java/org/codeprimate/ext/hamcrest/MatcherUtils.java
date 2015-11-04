/*
 * Copyright 2010-2013 the original author or authors.
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

package org.codeprimate.ext.hamcrest;

import org.codeprimate.lang.Assert;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * MatcherUtils is a utility class with support for additional Hamcrest Matchers
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class MatcherUtils {

	public static <T extends Comparable<T>> Matcher<T> greaterThan(T value) {
		return new GreaterThanMatcher<>(value);
	}

	public static <T extends Comparable<T>> Matcher<T> lessThan(T value) {
		return new LessThanMatcher<>(value);
	}

	protected static abstract class AbstractRelationalMatcher<T extends Comparable<T>> extends BaseMatcher<T> {

		private final Comparable<T> value;

		public AbstractRelationalMatcher(final Comparable<T> value) {
			Assert.notNull(value, "Comparable value must not be null");
			this.value = value;
		}

		protected Comparable<T> getValue() {
			return value;
		}

		@Override
		public void describeTo(final Description description) {
			description.appendText(String.format("value must be %1$s a Comparable value of the same type",
				describeToQualifier(description)));
		}

		protected abstract String describeToQualifier(Description description);
	}

	protected static class GreaterThanMatcher<T extends Comparable<T>> extends AbstractRelationalMatcher<T> {

		protected GreaterThanMatcher(final Comparable<T> value) {
			super(value);
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean matches(final Object item) {
			return (item instanceof Comparable && ((Comparable) item).compareTo(getValue()) > 0);
		}

		@Override
		protected String describeToQualifier(final Description description) {
			return "greater than";
		}
	}

	protected static class LessThanMatcher<T extends Comparable<T>> extends AbstractRelationalMatcher<T> {

		protected LessThanMatcher(final Comparable<T> value) {
			super(value);
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean matches(final Object item) {
			return (item instanceof Comparable && ((Comparable) item).compareTo(getValue()) < 0);
		}

		@Override
		protected String describeToQualifier(final Description description) {
			return "less than";
		}
	}

}
