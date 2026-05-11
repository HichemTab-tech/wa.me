package com.hichemtabtech.wame

object Sanitizer {
    /**
     * Sanitizes a phone number.
     * 1. If it's a long text, it tries to extract something that looks like a phone number.
     * 2. If it starts with '0', replace with '+213'.
     * 3. If it starts with '+0', replace with '+213'.
     * 4. If it doesn't start with '+', add it.
     * 5. Removes all non-digit characters (except the leading '+').
     * 6. If no digits are found after extraction, returns empty string.
     */
    fun sanitizePhoneNumber(input: String): String {
        if (input.isBlank()) return ""

        // Regex to find potential phone numbers: 
        // Starts with + or digit, followed by digits, spaces, or dashes.
        // We look for at least 9 digits in total.
        val regex = Regex("""(\+?\d[\s\-.]*){9,}""")
        val match = regex.find(input)
        
        // If no match found by regex, it might be a short number entered manually
        val result = match?.value?.trim() ?: input.trim()

        // Remove all non-essential characters early to simplify logic
        // but keep '+' if it's at the very start
        val hasLeadingPlus = result.startsWith("+")
        var digits = result.filter { it.isDigit() }
        
        if (digits.isEmpty()) return ""

        if (digits.startsWith("0")) {
            // Case "0..." or "+0..." -> Replace 0 with 213
            digits = "213" + digits.substring(1)
        } else if (!hasLeadingPlus && !digits.startsWith("213") && digits.length == 9) {
            // Edge case: user enters "550123456" (9 digits, likely local mobile without 0)
            // We can assume it's Algerian if it's 9 digits starting with 5, 6 or 7
            if (digits.startsWith("5") || digits.startsWith("6") || digits.startsWith("7")) {
                digits = "213$digits"
            }
        }

        return "+$digits"
    }
}
