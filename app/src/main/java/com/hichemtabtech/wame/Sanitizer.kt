package com.hichemtabtech.wame

object Sanitizer {
    /**
     * Sanitizes a phone number.
     * 1. If it's a long text, it tries to extract something that looks like a phone number.
     * 2. If it starts with '0', replace with '+213'.
     * 3. If it doesn't start with '+', add it.
     * 4. Removes all non-digit characters (except the leading '+').
     */
    fun sanitizePhoneNumber(input: String): String {
        if (input.isBlank()) return ""

        // Regex to find potential phone numbers: 
        // Starts with + or digit, followed by digits, spaces, or dashes.
        // We look for at least 9 digits in total.
        val regex = Regex("""(\+?\d[\s\-.]*){9,}""")
        val match = regex.find(input)
        
        var result = if (match != null) {
            match.value.trim()
        } else {
            input.trim()
        }

        // Basic normalization: remove all except digits and '+'
        result = result.filter { it.isDigit() || it == '+' }

        if (result.startsWith("0")) {
            result = "+213" + result.substring(1)
        } else if (!result.startsWith("+")) {
            result = "+$result"
        }

        // Final cleanup: ensure only one + at the start and only digits follow
        val finalDigits = result.filter { it.isDigit() }
        return if (result.startsWith("+")) "+$finalDigits" else "+$finalDigits"
    }
}
