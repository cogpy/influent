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
package influent.idlhelper;

import influent.idl.FL_GeoData;
import influent.idl.FL_PropertyType;
import influent.idl.FL_SingletonRange;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class SingletonRangeHelperTest {

	// --- value() ---

	@Test
	public void testValue_singletonRange_returnsValue() {
		FL_SingletonRange range = FL_SingletonRange.newBuilder()
				.setType(FL_PropertyType.STRING)
				.setValue("hello")
				.build();
		Assert.assertEquals("hello", SingletonRangeHelper.value(range));
	}

	@Test
	public void testValue_nonSingletonRange_returnsNull() {
		Assert.assertNull(SingletonRangeHelper.value("not a singleton range"));
		Assert.assertNull(SingletonRangeHelper.value(null));
		Assert.assertNull(SingletonRangeHelper.value(42));
	}

	// --- from(String) ---

	@Test
	public void testFromString() {
		FL_SingletonRange range = SingletonRangeHelper.from("test");
		Assert.assertEquals(FL_PropertyType.STRING, range.getType());
		Assert.assertEquals("test", range.getValue());
	}

	@Test
	public void testFromString_empty() {
		FL_SingletonRange range = SingletonRangeHelper.from("");
		Assert.assertEquals(FL_PropertyType.STRING, range.getType());
		Assert.assertEquals("", range.getValue());
	}

	// --- from(Boolean) ---

	@Test
	public void testFromBoolean_true() {
		FL_SingletonRange range = SingletonRangeHelper.from(Boolean.TRUE);
		Assert.assertEquals(FL_PropertyType.BOOLEAN, range.getType());
		Assert.assertEquals(Boolean.TRUE, range.getValue());
	}

	@Test
	public void testFromBoolean_false() {
		FL_SingletonRange range = SingletonRangeHelper.from(Boolean.FALSE);
		Assert.assertEquals(FL_PropertyType.BOOLEAN, range.getType());
		Assert.assertEquals(Boolean.FALSE, range.getValue());
	}

	// --- from(Date) ---

	@Test
	public void testFromDate() {
		Date now = new Date();
		FL_SingletonRange range = SingletonRangeHelper.from(now);
		Assert.assertEquals(FL_PropertyType.DATE, range.getType());
		Assert.assertEquals(now.getTime(), range.getValue());
	}

	// --- from(Integer) ---

	@Test
	public void testFromInteger() {
		FL_SingletonRange range = SingletonRangeHelper.from(Integer.valueOf(99));
		Assert.assertEquals(FL_PropertyType.INTEGER, range.getType());
		Assert.assertEquals(99, range.getValue());
	}

	@Test
	public void testFromInteger_zero() {
		FL_SingletonRange range = SingletonRangeHelper.from(Integer.valueOf(0));
		Assert.assertEquals(FL_PropertyType.INTEGER, range.getType());
		Assert.assertEquals(0, range.getValue());
	}

	// --- from(Long) ---

	@Test
	public void testFromLong() {
		FL_SingletonRange range = SingletonRangeHelper.from(Long.valueOf(123456789L));
		Assert.assertEquals(FL_PropertyType.LONG, range.getType());
		Assert.assertEquals(123456789L, range.getValue());
	}

	// --- from(Float) ---

	@Test
	public void testFromFloat_convertsToDouble() {
		FL_SingletonRange range = SingletonRangeHelper.from(Float.valueOf(3.14f));
		Assert.assertEquals(FL_PropertyType.DOUBLE, range.getType());
		Assert.assertTrue(range.getValue() instanceof Double);
	}

	// --- from(Double) ---

	@Test
	public void testFromDouble() {
		FL_SingletonRange range = SingletonRangeHelper.from(Double.valueOf(2.71828));
		Assert.assertEquals(FL_PropertyType.DOUBLE, range.getType());
		Assert.assertEquals(2.71828, (Double) range.getValue(), 0.00001);
	}

	// --- from(FL_GeoData) ---

	@Test
	public void testFromGeoData() {
		FL_GeoData geo = FL_GeoData.newBuilder()
				.setCc("US")
				.setLat(37.0)
				.setLon(-122.0)
				.setText("United States")
				.build();
		FL_SingletonRange range = SingletonRangeHelper.from(geo);
		Assert.assertEquals(FL_PropertyType.GEO, range.getType());
		Assert.assertEquals(geo, range.getValue());
	}

	// --- from(Object, FL_PropertyType) ---

	@Test
	public void testFromObjectAndType() {
		FL_SingletonRange range = SingletonRangeHelper.from("custom", FL_PropertyType.STRING);
		Assert.assertEquals(FL_PropertyType.STRING, range.getType());
		Assert.assertEquals("custom", range.getValue());
	}

	// --- fromUnknown ---

	@Test
	public void testFromUnknown_string() {
		FL_SingletonRange range = SingletonRangeHelper.fromUnknown("hello");
		Assert.assertEquals(FL_PropertyType.STRING, range.getType());
		Assert.assertEquals("hello", range.getValue());
	}

	@Test
	public void testFromUnknown_integer() {
		FL_SingletonRange range = SingletonRangeHelper.fromUnknown(42);
		Assert.assertEquals(FL_PropertyType.INTEGER, range.getType());
		Assert.assertEquals(42, range.getValue());
	}

	@Test
	public void testFromUnknown_long() {
		FL_SingletonRange range = SingletonRangeHelper.fromUnknown(Long.valueOf(9999999L));
		Assert.assertEquals(FL_PropertyType.LONG, range.getType());
		Assert.assertEquals(9999999L, range.getValue());
	}

	@Test
	public void testFromUnknown_double() {
		FL_SingletonRange range = SingletonRangeHelper.fromUnknown(3.14);
		Assert.assertEquals(FL_PropertyType.DOUBLE, range.getType());
		Assert.assertEquals(3.14, (Double) range.getValue(), 0.00001);
	}

	@Test
	public void testFromUnknown_boolean() {
		FL_SingletonRange range = SingletonRangeHelper.fromUnknown(Boolean.TRUE);
		Assert.assertEquals(FL_PropertyType.BOOLEAN, range.getType());
		Assert.assertEquals(Boolean.TRUE, range.getValue());
	}

	@Test
	public void testFromUnknown_date() {
		Date now = new Date();
		FL_SingletonRange range = SingletonRangeHelper.fromUnknown(now);
		Assert.assertEquals(FL_PropertyType.DATE, range.getType());
		Assert.assertEquals(now.getTime(), range.getValue());
	}

	@Test
	public void testFromUnknown_geoData() {
		FL_GeoData geo = FL_GeoData.newBuilder()
				.setCc("CA")
				.setLat(45.0)
				.setLon(-75.0)
				.setText("Canada")
				.build();
		FL_SingletonRange range = SingletonRangeHelper.fromUnknown(geo);
		Assert.assertEquals(FL_PropertyType.GEO, range.getType());
	}

	@Test
	public void testFromUnknown_null_returnsNull() {
		Assert.assertNull(SingletonRangeHelper.fromUnknown(null));
	}

	@Test
	public void testFromUnknown_arbitraryObject_usesToString() {
		// Objects that don't match known types fall through to toString() as STRING
		Object obj = new Object() {
			@Override public String toString() { return "custom-object"; }
		};
		FL_SingletonRange range = SingletonRangeHelper.fromUnknown(obj);
		Assert.assertEquals(FL_PropertyType.STRING, range.getType());
		Assert.assertEquals("custom-object", range.getValue());
	}
}
