package com.hichemtabtech.wame

import org.junit.Assert.assertEquals
import org.junit.Test

class SanitizerTest {
    @Test
    fun testSanitizeStartingWithZero() {
        assertEquals("+213550123456", Sanitizer.sanitizePhoneNumber("0550123456"))
    }

    @Test
    fun testSanitizeWithCountryCode() {
        assertEquals("+213550123456", Sanitizer.sanitizePhoneNumber("213550123456"))
    }

    @Test
    fun testSanitizeWithPlus() {
        assertEquals("+213550123456", Sanitizer.sanitizePhoneNumber("+213550123456"))
    }

    @Test
    fun testSanitizeWithSpacesAndDashes() {
        assertEquals("+213550123456", Sanitizer.sanitizePhoneNumber("05-50 12 34 56"))
    }

    @Test
    fun testExtractFromText() {
        assertEquals("+213550123456", Sanitizer.sanitizePhoneNumber("call me at 0550123456 tomorrow"))
    }

    @Test
    fun testInternational() {
        assertEquals("+33123456789", Sanitizer.sanitizePhoneNumber("+33123456789"))
    }

    @Test
    fun testNoNumberInText() {
        assertEquals("", Sanitizer.sanitizePhoneNumber("hello world"))
    }

    @Test
    fun testEmptyInput() {
        assertEquals("", Sanitizer.sanitizePhoneNumber(""))
    }

    @Test
    fun testAlreadyHasPlusAndZero() {
        assertEquals("+213550123456", Sanitizer.sanitizePhoneNumber("+0550123456"))
    }

    @Test
    fun testNineDigitsNoZero() {
        // User copies "550123456"
        assertEquals("+213550123456", Sanitizer.sanitizePhoneNumber("550123456"))
    }
}
