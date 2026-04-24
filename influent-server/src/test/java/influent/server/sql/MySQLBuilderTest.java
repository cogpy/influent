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
package influent.server.sql;

import influent.server.sql.mysql.MySQLBuilder;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests for the MySQL SQL builder, covering MySQL-specific behaviour such as
 * LIMIT / OFFSET instead of TOP N.
 */
public class MySQLBuilderTest {

	// --- basic SELECT ---

	@Test
	public void testSelectAll() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select().from("myTable");
		Assert.assertEquals("SELECT * FROM myTable", sql.build());
	}

	@Test
	public void testSelectColumn() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from("myTable");
		Assert.assertEquals("SELECT col1 FROM myTable", sql.build());
	}

	@Test
	public void testSelectDistinct() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.distinct()
				.column("col1")
				.from("myTable");
		Assert.assertEquals("SELECT DISTINCT col1 FROM myTable", sql.build());
	}

	// --- MySQL does NOT use TOP; it uses LIMIT ---

	@Test
	public void testSelectTopN_usesLimit() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.top(10)
				.column("col1")
				.from("myTable");
		// MySQL wraps the query with LIMIT instead of injecting TOP N
		String built = sql.build();
		Assert.assertFalse("MySQL should not produce TOP keyword", built.contains("TOP"));
		Assert.assertTrue("MySQL should produce LIMIT keyword", built.contains("LIMIT 10"));
	}

	// --- WHERE clause ---

	@Test
	public void testSelectWhereEquals() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from("myTable")
				.where(builder.filter()
						.column("col1")
						.eq(42));
		Assert.assertEquals("SELECT col1 FROM myTable WHERE col1 = 42", sql.build());
	}

	@Test
	public void testSelectWhereAndClause() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.column("col2")
				.from("myTable")
				.where(builder.and(
						builder.filter().column("col1").eq(1),
						builder.filter().column("col2").greaterThan(5)));
		Assert.assertEquals("SELECT col1,col2 FROM myTable WHERE (col1 = 1) AND (col2 > 5)", sql.build());
	}

	@Test
	public void testSelectWhereOrClause() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from("myTable")
				.where(builder.or(
						builder.filter().column("col1").eq(1),
						builder.filter().column("col1").eq(2)));
		Assert.assertEquals("SELECT col1 FROM myTable WHERE (col1 = 1) OR (col1 = 2)", sql.build());
	}

	@Test
	public void testSelectWhereLike() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from("myTable")
				.where(builder.filter()
						.column("col1")
						.like("'%test%'"));
		Assert.assertEquals("SELECT col1 FROM myTable WHERE col1 LIKE '%test%'", sql.build());
	}

	@Test
	public void testSelectWhereIn() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from("myTable")
				.where(builder.filter()
						.column("col1")
						.in(Lists.<Object>newArrayList("'a'", "'b'", "'c'")));
		Assert.assertEquals("SELECT col1 FROM myTable WHERE col1 IN ('a','b','c')", sql.build());
	}

	@Test
	public void testSelectWhereBetween() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from("myTable")
				.where(builder.filter()
						.column("col1")
						.between(10, 20));
		Assert.assertEquals("SELECT col1 FROM myTable WHERE col1 BETWEEN 10 AND 20", sql.build());
	}

	// --- GROUP BY / ORDER BY ---

	@Test
	public void testSelectGroupBy() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1", "c", SQLFunction.Count)
				.from("myTable")
				.groupBy("c");
		Assert.assertEquals("SELECT COUNT(col1) AS c FROM myTable GROUP BY c", sql.build());
	}

	@Test
	public void testSelectOrderByAsc() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from("myTable")
				.orderBy("col1");
		Assert.assertEquals("SELECT col1 FROM myTable ORDER BY col1 ASC", sql.build());
	}

	@Test
	public void testSelectOrderByDesc() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from("myTable")
				.orderBy("col1", false);
		Assert.assertEquals("SELECT col1 FROM myTable ORDER BY col1 DESC", sql.build());
	}

	// --- JOIN ---

	@Test
	public void testSelectInnerJoin() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from(builder.from().table("t1").as("a"))
				.join(builder.innerJoin().table("t2").as("b").on("a.id=b.id"));
		Assert.assertEquals("SELECT col1 FROM t1 AS a INNER JOIN t2 AS b ON a.id=b.id", sql.build());
	}

	// --- sub-query in FROM ---

	@Test
	public void testSelectFromSubQuery() {
		MySQLBuilder builder = new MySQLBuilder();
		SQLSelect sql = builder.select()
				.column("col1")
				.from(builder.from()
						.fromQuery(builder.select().from("inner_table", "i"))
						.as("sub"));
		Assert.assertEquals("SELECT col1 FROM (SELECT * FROM inner_table AS i) AS sub", sql.build());
	}

	// --- escape / unescape ---

	@Test
	public void testEscape() {
		MySQLBuilder builder = new MySQLBuilder();
		Assert.assertEquals("`myCol`", builder.escape("myCol"));
	}

	@Test
	public void testUnescape_alreadyEscaped() {
		MySQLBuilder builder = new MySQLBuilder();
		Assert.assertEquals("myCol", builder.unescape("`myCol`"));
	}

	@Test
	public void testUnescape_notEscaped() {
		MySQLBuilder builder = new MySQLBuilder();
		Assert.assertEquals("myCol", builder.unescape("myCol"));
	}
}
