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

public class GuidValidatorTest {

	// --- validateGuidString ---

	@Test
	public void testValidateGuidString_validGuid() {
		Assert.assertTrue(GuidValidator.validateGuidString("550e8400-e29b-41d4-a716-446655440000"));
	}

	@Test
	public void testValidateGuidString_validGuidUpperCase() {
		Assert.assertTrue(GuidValidator.validateGuidString("550E8400-E29B-41D4-A716-446655440000"));
	}

	@Test
	public void testValidateGuidString_validGuidMixedCase() {
		Assert.assertTrue(GuidValidator.validateGuidString("550e8400-E29B-41d4-A716-446655440000"));
	}

	@Test
	public void testValidateGuidString_invalidGuid_empty() {
		Assert.assertFalse(GuidValidator.validateGuidString(""));
	}

	@Test
	public void testValidateGuidString_invalidGuid_notAGuid() {
		Assert.assertFalse(GuidValidator.validateGuidString("not-a-guid"));
	}

	@Test
	public void testValidateGuidString_invalidGuid_tooShort() {
		Assert.assertFalse(GuidValidator.validateGuidString("550e8400-e29b-41d4"));
	}

	@Test
	public void testValidateGuidString_invalidGuid_extraChars() {
		Assert.assertFalse(GuidValidator.validateGuidString("550e8400-e29b-41d4-a716-4466554400001"));
	}

	@Test
	public void testValidateGuidString_invalidGuid_withBraces() {
		// Java UUID.fromString does not accept braces
		Assert.assertFalse(GuidValidator.validateGuidString("{550e8400-e29b-41d4-a716-446655440000}"));
	}

	// --- validateContextString ---

	@Test
	public void testValidateContextString_validFileContext() {
		Assert.assertTrue(GuidValidator.validateContextString("file_550e8400-e29b-41d4-a716-446655440000"));
	}

	@Test
	public void testValidateContextString_validFileContextUpperCase() {
		Assert.assertTrue(GuidValidator.validateContextString("FILE_550e8400-e29b-41d4-a716-446655440000"));
	}

	@Test
	public void testValidateContextString_validColumnContext() {
		Assert.assertTrue(GuidValidator.validateContextString("column_550e8400-e29b-41d4-a716-446655440000"));
	}

	@Test
	public void testValidateContextString_validColumnContextUpperCase() {
		Assert.assertTrue(GuidValidator.validateContextString("COLUMN_550e8400-e29b-41d4-a716-446655440000"));
	}

	@Test
	public void testValidateContextString_invalidPrefix() {
		Assert.assertFalse(GuidValidator.validateContextString("other_550e8400-e29b-41d4-a716-446655440000"));
	}

	@Test
	public void testValidateContextString_missingUnderscore() {
		Assert.assertFalse(GuidValidator.validateContextString("file550e8400-e29b-41d4-a716-446655440000"));
	}

	@Test
	public void testValidateContextString_invalidGuidPart() {
		Assert.assertFalse(GuidValidator.validateContextString("file_not-a-guid"));
	}

	@Test
	public void testValidateContextString_tooManyParts() {
		Assert.assertFalse(GuidValidator.validateContextString("file_550e8400-e29b-41d4-a716-446655440000_extra"));
	}

	@Test
	public void testValidateContextString_emptyString() {
		Assert.assertFalse(GuidValidator.validateContextString("_"));
	}
}
