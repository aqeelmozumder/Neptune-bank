package com.neptunebank.app.test.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeirdTest {

	@Test
	void testSameStringLiteralsAreSame() {
		Assertions.assertSame("Neptune", "Neptune");
	}

	@Test
	void testStringObjectsFromSameLiteralAreNotSame() {
		String stringLiteral = "Neptune";
		Assertions.assertNotSame(new String(stringLiteral), new String(stringLiteral));
	}

	@Test
	void testStringObjectsFromSameLiteralAreEqual() {
		String stringLiteral = "Neptune";
		Assertions.assertEquals(new String(stringLiteral), new String(stringLiteral));
	}
}
