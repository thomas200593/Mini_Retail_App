package com.thomas200593.mini_retail_app.core.design_system.util

object HlpCountryFlags {
    private val emojiMap = mapOf(
        'A' to getEmojiByUnicode(0x1F1E6), 'B' to getEmojiByUnicode(0x1F1E7),
        'C' to getEmojiByUnicode(0x1F1E8), 'D' to getEmojiByUnicode(0x1F1E9),
        'E' to getEmojiByUnicode(0x1F1EA), 'F' to getEmojiByUnicode(0x1F1EB),
        'G' to getEmojiByUnicode(0x1F1EC), 'H' to getEmojiByUnicode(0x1F1ED),
        'I' to getEmojiByUnicode(0x1F1EE), 'J' to getEmojiByUnicode(0x1F1EF),
        'K' to getEmojiByUnicode(0x1F1F0), 'L' to getEmojiByUnicode(0x1F1F1),
        'M' to getEmojiByUnicode(0x1F1F2), 'N' to getEmojiByUnicode(0x1F1F3),
        'O' to getEmojiByUnicode(0x1F1F4), 'P' to getEmojiByUnicode(0x1F1F5),
        'Q' to getEmojiByUnicode(0x1F1F6), 'R' to getEmojiByUnicode(0x1F1F7),
        'S' to getEmojiByUnicode(0x1F1F8), 'T' to getEmojiByUnicode(0x1F1F9),
        'U' to getEmojiByUnicode(0x1F1FA), 'V' to getEmojiByUnicode(0x1F1FB),
        'W' to getEmojiByUnicode(0x1F1FC), 'X' to getEmojiByUnicode(0x1F1FD),
        'Y' to getEmojiByUnicode(0x1F1FE), 'Z' to getEmojiByUnicode(0x1F1FF)
    )

    private fun getCodeByCharacter(character: Char): String = emojiMap[character.uppercaseChar()] ?: String()

    private fun getEmojiByUnicode(unicode: Int) = String(Character.toChars(unicode))

    fun getCountryFlagByCountryCode(countryCode: String): String =
        if (countryCode.length == 2) getCodeByCharacter(countryCode[0]) + getCodeByCharacter(countryCode[1])
        else String()
}