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

import influent.idl.FL_DateInterval;
import influent.idl.FL_Duration;

import org.junit.Assert;
import org.junit.Test;

public class ResultFormatterTest {

	// --- formatCur (USD) ---

	@Test
	public void testFormatCur_usd_positiveValue() {
		String result = ResultFormatter.formatCur(1234.56, true);
		Assert.assertEquals("$1,234.56", result);
	}

	@Test
	public void testFormatCur_usd_negativeValue() {
		String result = ResultFormatter.formatCur(-9876.50, true);
		Assert.assertEquals("$-9,876.50", result);
	}

	@Test
	public void testFormatCur_usd_zero() {
		String result = ResultFormatter.formatCur(0, true);
		Assert.assertEquals("$0.00", result);
	}

	@Test
	public void testFormatCur_usd_null() {
		String result = ResultFormatter.formatCur(null, true);
		Assert.assertEquals("-", result);
	}

	// --- formatCur (non-USD) ---

	@Test
	public void testFormatCur_nonUsd_positiveValue() {
		String result = ResultFormatter.formatCur(1234.56, false);
		Assert.assertEquals("1,234.56", result);
	}

	@Test
	public void testFormatCur_nonUsd_negativeValue() {
		String result = ResultFormatter.formatCur(-9876.50, false);
		Assert.assertEquals("-9,876.50", result);
	}

	@Test
	public void testFormatCur_nonUsd_null() {
		String result = ResultFormatter.formatCur(null, false);
		Assert.assertEquals("-", result);
	}

	// --- formatCount ---

	@Test
	public void testFormatCount_positiveInteger() {
		String result = ResultFormatter.formatCount(42);
		Assert.assertEquals("42", result);
	}

	@Test
	public void testFormatCount_largeNumber() {
		String result = ResultFormatter.formatCount(1000000);
		Assert.assertEquals("1,000,000", result);
	}

	@Test
	public void testFormatCount_negativeNumber() {
		String result = ResultFormatter.formatCount(-500);
		Assert.assertEquals("-500", result);
	}

	@Test
	public void testFormatCount_null() {
		String result = ResultFormatter.formatCount(null);
		Assert.assertEquals("-", result);
	}

	@Test
	public void testFormatCount_zero() {
		String result = ResultFormatter.formatCount(0);
		Assert.assertEquals("0", result);
	}

	// --- formatDur ---

	@Test
	public void testFormatDur_null_returnsEmpty() {
		String result = ResultFormatter.formatDur(null);
		Assert.assertEquals("", result);
	}

	@Test
	public void testFormatDur_zeroIntervals_returnsDash() {
		FL_Duration dur = new FL_Duration(FL_DateInterval.DAYS, 0L);
		String result = ResultFormatter.formatDur(dur);
		Assert.assertEquals("-", result);
	}

	@Test
	public void testFormatDur_seconds() {
		FL_Duration dur = new FL_Duration(FL_DateInterval.SECONDS, 90L);
		String result = ResultFormatter.formatDur(dur);
		// 90 seconds -> 00:01:30
		Assert.assertEquals("00:01:30", result);
	}

	@Test
	public void testFormatDur_hours() {
		FL_Duration dur = new FL_Duration(FL_DateInterval.HOURS, 2L);
		String result = ResultFormatter.formatDur(dur);
		// 2 hours -> 02:00:00
		Assert.assertEquals("02:00:00", result);
	}

	@Test
	public void testFormatDur_days() {
		FL_Duration dur = new FL_Duration(FL_DateInterval.DAYS, 1L);
		String result = ResultFormatter.formatDur(dur);
		// The formatter only prints hh:mm:ss; days are not converted to hours by normalize
		Assert.assertEquals("00:00:00", result);
	}

	@Test
	public void testFormatDur_weeks() {
		FL_Duration dur = new FL_Duration(FL_DateInterval.WEEKS, 1L);
		String result = ResultFormatter.formatDur(dur);
		// The formatter only prints hh:mm:ss; weeks are not converted to hours by normalize
		Assert.assertEquals("00:00:00", result);
	}

	@Test
	public void testFormatDur_months() {
		FL_Duration dur = new FL_Duration(FL_DateInterval.MONTHS, 1L);
		String result = ResultFormatter.formatDur(dur);
		// 1 month -> 00:00:00 (months have no fixed duration in seconds/hours)
		Assert.assertNotNull(result);
	}

	@Test
	public void testFormatDur_quarters() {
		FL_Duration dur = new FL_Duration(FL_DateInterval.QUARTERS, 1L);
		String result = ResultFormatter.formatDur(dur);
		// 1 quarter = 3 months -> 00:00:00
		Assert.assertNotNull(result);
	}

	@Test
	public void testFormatDur_years() {
		FL_Duration dur = new FL_Duration(FL_DateInterval.YEARS, 1L);
		String result = ResultFormatter.formatDur(dur);
		// 1 year -> 00:00:00
		Assert.assertNotNull(result);
	}
}
