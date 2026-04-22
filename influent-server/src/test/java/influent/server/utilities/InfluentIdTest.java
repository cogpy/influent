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

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InfluentIdTest {

	// --- fromNativeId ---

	@Test
	public void testFromNativeId_account() {
		InfluentId id = InfluentId.fromNativeId(InfluentId.ACCOUNT, "myType", "native123");
		Assert.assertEquals(InfluentId.ACCOUNT, id.getIdClass());
		Assert.assertEquals("myType", id.getIdType());
		Assert.assertEquals("native123", id.getNativeId());
		Assert.assertEquals("myType.native123", id.getTypedId());
		Assert.assertEquals("a.myType.native123", id.getInfluentId());
	}

	@Test
	public void testFromNativeId_cluster() {
		InfluentId id = InfluentId.fromNativeId(InfluentId.CLUSTER, "clusterType", "clu001");
		Assert.assertEquals(InfluentId.CLUSTER, id.getIdClass());
		Assert.assertEquals("clusterType", id.getIdType());
		Assert.assertEquals("clu001", id.getNativeId());
		Assert.assertEquals("clusterType.clu001", id.getTypedId());
		Assert.assertEquals("c.clusterType.clu001", id.getInfluentId());
	}

	@Test
	public void testFromNativeId_file() {
		InfluentId id = InfluentId.fromNativeId(InfluentId.FILE, "ft", "file99");
		Assert.assertEquals(InfluentId.FILE, id.getIdClass());
		Assert.assertEquals("f.ft.file99", id.getInfluentId());
	}

	// --- fromInfluentId ---

	@Test
	public void testFromInfluentId_wellFormed() {
		InfluentId id = InfluentId.fromInfluentId("a.someType.nativeX");
		Assert.assertEquals(InfluentId.ACCOUNT, id.getIdClass());
		Assert.assertEquals("someType", id.getIdType());
		Assert.assertEquals("nativeX", id.getNativeId());
		Assert.assertEquals("someType.nativeX", id.getTypedId());
		Assert.assertEquals("a.someType.nativeX", id.getInfluentId());
	}

	@Test
	public void testFromInfluentId_nativeIdWithDots() {
		// native id may contain dots when split with limit 3
		InfluentId id = InfluentId.fromInfluentId("a.type.native.id.with.dots");
		Assert.assertEquals(InfluentId.ACCOUNT, id.getIdClass());
		Assert.assertEquals("type", id.getIdType());
		Assert.assertEquals("native.id.with.dots", id.getNativeId());
	}

	@Test
	public void testFromInfluentId_shortId_returnsNullFields() {
		// ids shorter than 3 chars fall into the else branch
		InfluentId id = InfluentId.fromInfluentId("ab");
		Assert.assertNull(id.getNativeId());
		Assert.assertNull(id.getIdType());
		Assert.assertNull(id.getInfluentId());
		Assert.assertNull(id.getTypedId());
	}

	@Test
	public void testFromInfluentId_nullId_returnsDefaults() {
		InfluentId id = InfluentId.fromInfluentId(null);
		Assert.assertNull(id.getNativeId());
		Assert.assertNull(id.getIdType());
	}

	@Test
	public void testFromInfluentId_toString() {
		String raw = "a.myType.native123";
		InfluentId id = InfluentId.fromInfluentId(raw);
		Assert.assertEquals(raw, id.toString());
	}

	// --- fromTypedId ---

	@Test
	public void testFromTypedId_wellFormed() {
		InfluentId id = InfluentId.fromTypedId(InfluentId.ACCOUNT, "myType.nativeId");
		Assert.assertEquals(InfluentId.ACCOUNT, id.getIdClass());
		Assert.assertEquals("myType", id.getIdType());
		Assert.assertEquals("nativeId", id.getNativeId());
	}

	@Test
	public void testFromTypedId_noDot_usesFullStringAsNativeId() {
		InfluentId id = InfluentId.fromTypedId(InfluentId.ACCOUNT, "plainNativeId");
		Assert.assertEquals(InfluentId.ACCOUNT, id.getIdClass());
		Assert.assertNull(id.getIdType());
		Assert.assertEquals("plainNativeId", id.getNativeId());
	}

	// --- hasIdClass ---

	@Test
	public void testHasIdClass_true() {
		Assert.assertTrue(InfluentId.hasIdClass("a.myType.native123", InfluentId.ACCOUNT));
	}

	@Test
	public void testHasIdClass_false() {
		Assert.assertFalse(InfluentId.hasIdClass("a.myType.native123", InfluentId.CLUSTER));
	}

	// --- nativeFromInfluentIds (no filter) ---

	@Test
	public void testNativeFromInfluentIds_noFilter() {
		List<String> influentIds = Arrays.asList("a.t1.n1", "c.t2.n2", "f.t3.n3");
		List<String> natives = InfluentId.nativeFromInfluentIds(influentIds);
		Assert.assertEquals(Arrays.asList("n1", "n2", "n3"), natives);
	}

	@Test
	public void testNativeFromInfluentIds_emptyList() {
		List<String> natives = InfluentId.nativeFromInfluentIds(Collections.<String>emptyList());
		Assert.assertTrue(natives.isEmpty());
	}

	// --- nativeFromInfluentIds (with class filter) ---

	@Test
	public void testNativeFromInfluentIds_withFilter() {
		List<String> influentIds = Arrays.asList("a.t1.n1", "c.t2.n2", "a.t3.n3");
		List<String> natives = InfluentId.nativeFromInfluentIds(influentIds, InfluentId.ACCOUNT);
		Assert.assertEquals(Arrays.asList("n1", "n3"), natives);
	}

	@Test
	public void testNativeFromInfluentIds_withFilter_noMatches() {
		List<String> influentIds = Arrays.asList("a.t1.n1", "a.t3.n3");
		List<String> natives = InfluentId.nativeFromInfluentIds(influentIds, InfluentId.CLUSTER);
		Assert.assertTrue(natives.isEmpty());
	}

	// --- typedFromInfluentIds (no filter) ---

	@Test
	public void testTypedFromInfluentIds_noFilter() {
		List<String> influentIds = Arrays.asList("a.t1.n1", "c.t2.n2");
		List<String> typed = InfluentId.typedFromInfluentIds(influentIds);
		Assert.assertEquals(Arrays.asList("t1.n1", "t2.n2"), typed);
	}

	// --- typedFromInfluentIds (with filter) ---

	@Test
	public void testTypedFromInfluentIds_withFilter() {
		List<String> influentIds = Arrays.asList("a.t1.n1", "c.t2.n2", "a.t3.n3");
		List<String> typed = InfluentId.typedFromInfluentIds(influentIds, InfluentId.ACCOUNT);
		Assert.assertEquals(Arrays.asList("t1.n1", "t3.n3"), typed);
	}

	// --- filterInfluentIds ---

	@Test
	public void testFilterInfluentIds() {
		List<String> influentIds = Arrays.asList("a.t1.n1", "c.t2.n2", "a.t3.n3");
		List<String> filtered = InfluentId.filterInfluentIds(influentIds, InfluentId.ACCOUNT);
		Assert.assertEquals(Arrays.asList("a.t1.n1", "a.t3.n3"), filtered);
	}

	@Test
	public void testFilterInfluentIds_noMatches() {
		List<String> influentIds = Arrays.asList("a.t1.n1", "a.t3.n3");
		List<String> filtered = InfluentId.filterInfluentIds(influentIds, InfluentId.FILE);
		Assert.assertTrue(filtered.isEmpty());
	}

	// --- filterTypedIds ---

	@Test
	public void testFilterTypedIds() {
		List<String> influentIds = Arrays.asList("a.t1.n1", "c.t2.n2", "c.t3.n3");
		List<String> filtered = InfluentId.filterTypedIds(influentIds, InfluentId.CLUSTER);
		Assert.assertEquals(Arrays.asList("t2.n2", "t3.n3"), filtered);
	}

	// --- influentFromNativeIds ---

	@Test
	public void testInfluentFromNativeIds() {
		List<String> nativeIds = Arrays.asList("n1", "n2");
		List<String> influentIds = InfluentId.influentFromNativeIds(InfluentId.ACCOUNT, "myType", nativeIds);
		Assert.assertEquals(Arrays.asList("a.myType.n1", "a.myType.n2"), influentIds);
	}

	@Test
	public void testInfluentFromNativeIds_empty() {
		List<String> influentIds = InfluentId.influentFromNativeIds(InfluentId.ACCOUNT, "t", Collections.<String>emptyList());
		Assert.assertTrue(influentIds.isEmpty());
	}

	// --- typedFromNativeIds ---

	@Test
	public void testTypedFromNativeIds() {
		List<String> nativeIds = Arrays.asList("n1", "n2");
		List<String> typedIds = InfluentId.typedFromNativeIds("myType", nativeIds);
		Assert.assertEquals(Arrays.asList("myType.n1", "myType.n2"), typedIds);
	}

	// --- id class constants ---

	@Test
	public void testIdClassConstants() {
		Assert.assertEquals('a', InfluentId.ACCOUNT);
		Assert.assertEquals('o', InfluentId.ACCOUNT_OWNER);
		Assert.assertEquals('c', InfluentId.CLUSTER);
		Assert.assertEquals('s', InfluentId.CLUSTER_SUMMARY);
		Assert.assertEquals('f', InfluentId.FILE);
		Assert.assertEquals('l', InfluentId.LINK);
	}

	// --- null handling in "null" string segments ---

	@Test
	public void testFromInfluentId_nullSegments() {
		// When idType or nativeId are the string "null", they should resolve to null fields
		InfluentId id = InfluentId.fromInfluentId("a.null.null");
		Assert.assertNull(id.getIdType());
		Assert.assertNull(id.getNativeId());
		Assert.assertNull(id.getInfluentId());
		Assert.assertNull(id.getTypedId());
	}
}
