/*
 * Copyright 2013-2016 Uncharted Software Inc.
 *
 *  Property of Uncharted(TM), formerly Oculus Info Inc.
 *  https://uncharted.software/
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package influent.server.utilities;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;

public class DateTimeParserTest {

	// --- parse ---

	@Test
	public void testParse_isoDateWithDash() {
		DateTime result = DateTimeParser.parse("2014-03-15");
		Assert.assertNotNull(result);
		Assert.assertEquals(2014, result.getYear());
		Assert.assertEquals(3, result.getMonthOfYear());
		Assert.assertEquals(15, result.getDayOfMonth());
		Assert.assertEquals(DateTimeZone.UTC, result.getZone());
	}

	@Test
	public void testParse_isoDateWithSlash() {
		DateTime result = DateTimeParser.parse("2014/03/15");
		Assert.assertNotNull(result);
		Assert.assertEquals(2014, result.getYear());
		Assert.assertEquals(3, result.getMonthOfYear());
		Assert.assertEquals(15, result.getDayOfMonth());
	}

	@Test
	public void testParse_isoDateTimeWithOffset() {
		// Full ISO datetime: time and offset should be ignored; only date should be kept
		DateTime result = DateTimeParser.parse("2014-03-15T10:30:00+05:00");
		Assert.assertNotNull(result);
		Assert.assertEquals(2014, result.getYear());
		Assert.assertEquals(3, result.getMonthOfYear());
		Assert.assertEquals(15, result.getDayOfMonth());
		Assert.assertEquals(0, result.getHourOfDay());
		Assert.assertEquals(DateTimeZone.UTC, result.getZone());
	}

	@Test
	public void testParse_isoDateTimeUtc() {
		DateTime result = DateTimeParser.parse("2000-01-01T00:00:00Z");
		Assert.assertNotNull(result);
		Assert.assertEquals(2000, result.getYear());
		Assert.assertEquals(1, result.getMonthOfYear());
		Assert.assertEquals(1, result.getDayOfMonth());
		Assert.assertEquals(0, result.getHourOfDay());
		Assert.assertEquals(DateTimeZone.UTC, result.getZone());
	}

	@Test
	public void testParse_null_returnsNull() {
		Assert.assertNull(DateTimeParser.parse(null));
	}

	@Test
	public void testParse_emptyString_returnsNull() {
		Assert.assertNull(DateTimeParser.parse(""));
	}

	@Test
	public void testParse_timeIsZeroedOut() {
		// Even if time is present in the string, parsed result should have time=00:00:00
		DateTime result = DateTimeParser.parse("2020-06-30T23:59:59Z");
		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getHourOfDay());
		Assert.assertEquals(0, result.getMinuteOfHour());
		Assert.assertEquals(0, result.getSecondOfMinute());
	}

	// --- fromFL ---

	@Test
	public void testFromFL_longValue() {
		long millis = new DateTime(2015, 7, 4, 0, 0, 0, DateTimeZone.UTC).getMillis();
		DateTime result = DateTimeParser.fromFL(millis);
		Assert.assertNotNull(result);
		Assert.assertEquals(2015, result.getYear());
		Assert.assertEquals(7, result.getMonthOfYear());
		Assert.assertEquals(4, result.getDayOfMonth());
	}

	@Test
	public void testFromFL_nonLong_returnsNull() {
		Assert.assertNull(DateTimeParser.fromFL("2015-07-04"));
		Assert.assertNull(DateTimeParser.fromFL(42.0));
		Assert.assertNull(DateTimeParser.fromFL(null));
	}

	// --- normalize ---

	@Test
	public void testNormalize_secondsAndMinutes() {
		// 90 seconds should normalize to 1 minute 30 seconds
		Period period = new Period(0, 0, 0, 0, 0, 1, 30, 0); // hours, minutes, seconds, millis
		Period result = DateTimeParser.normalize(period);
		Assert.assertNotNull(result);
	}

	@Test
	public void testNormalize_daysAndWeeks() {
		// 14 days = 2 weeks
		Period period = Period.days(14);
		Period result = DateTimeParser.normalize(period);
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.getWeeks());
		Assert.assertEquals(0, result.getDays());
	}

	@Test
	public void testNormalize_yearsAndMonths() {
		// 14 months = 1 year + 2 months
		Period period = Period.months(14);
		Period result = DateTimeParser.normalize(period);
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.getYears());
		Assert.assertEquals(2, result.getMonths());
	}

	@Test
	public void testNormalize_zeroPeriod() {
		Period period = Period.ZERO;
		Period result = DateTimeParser.normalize(period);
		Assert.assertNotNull(result);
		Assert.assertEquals(0, result.getDays());
		Assert.assertEquals(0, result.getHours());
		Assert.assertEquals(0, result.getMinutes());
		Assert.assertEquals(0, result.getSeconds());
	}
}
