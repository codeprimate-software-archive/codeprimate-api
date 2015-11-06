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

package org.codeprimate.lang.concurrent;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The TimeUnitComparator class is a Comparator implementation that compares 2 java.util.concurrent.TimeUnit objects
 * for order.
 *
 * @author John J. Blum
 * @see java.util.Comparator
 * @see java.util.concurrent.TimeUnit
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class TimeUnitComparator implements Comparator<TimeUnit> {

  private static final Map<TimeUnit, Integer> TIME_UNIT_VALUE_MAP = new HashMap<>(TimeUnit.values().length + 1);

  static {
    int value = 0;
    TIME_UNIT_VALUE_MAP.put(TimeUnit.NANOSECONDS, ++value);
    TIME_UNIT_VALUE_MAP.put(TimeUnit.MICROSECONDS, ++value);
    TIME_UNIT_VALUE_MAP.put(TimeUnit.MILLISECONDS, ++value);
    TIME_UNIT_VALUE_MAP.put(TimeUnit.SECONDS, ++value);
    TIME_UNIT_VALUE_MAP.put(TimeUnit.MINUTES, ++value);
    TIME_UNIT_VALUE_MAP.put(TimeUnit.HOURS, ++value);
    TIME_UNIT_VALUE_MAP.put(TimeUnit.DAYS, ++value);
    TIME_UNIT_VALUE_MAP.put(null, Integer.MAX_VALUE);
  }

  @Override
  public int compare(final TimeUnit timeUnitOne, final TimeUnit timeUnitTwo) {
    return (TIME_UNIT_VALUE_MAP.get(timeUnitOne) - TIME_UNIT_VALUE_MAP.get(timeUnitTwo));
  }

}
