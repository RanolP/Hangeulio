@file:JvmName("Hangeulio")

package io.github.ranolp.hangeulio

import io.github.ranolp.hangeulio.vo.HangeulPhoneme
import io.github.ranolp.hangeulio.vo.HangeulSyllable
import io.github.ranolp.hangeulio.vo.Tone

fun isConsonant(char: Char): Boolean = HangeulPhoneme(char).isConsonant

fun isVowel(char: Char): Boolean = HangeulPhoneme(char).isVowel

fun isPhoneme(char: Char): Boolean =
    when (char) {
        in HALFWIDTH_HANGUL_LETTER_CONSONANT,
        in HANGUL_LETTER_CONSONANT,
        in HALFWIDTH_HANGUL_LETTER_VOWEL,
        in HANGUL_LETTER_VOWEL,
        in HANGUL_ONSET_MODERN,
        in HANGUL_ONSET_OLD,
        in HANGUL_CODA_MODERN,
        in HANGUL_CODA_OLD,
        in HANGUL_NUCLEUS_MODERN,
        in HANGUL_NUCLEUS_OLD,
        in PARENTHESIZED_HANGUL_LETTERS,
        in CIRCLED_HANGUL_LETTERS -> true
        else -> false
    }

@Deprecated("Jaeum is 자음's phonetic, So, use isConsonant instead.", ReplaceWith("isConsonant(char)"))
fun isJaeum(char: Char): Boolean = isConsonant(char)


@Deprecated("Jaum is 자음's phonetic, So, use isConsonant instead.", ReplaceWith("isConsonant(char)"))
fun isJaum(char: Char): Boolean = isConsonant(char)


@Deprecated("Moeum is 모음's phonetic, So, use isVowel instead.", ReplaceWith("isVowel(char)"))
fun isMoeum(char: Char): Boolean = isVowel(char)

@Deprecated("Moum is 모음's phonetic, So, use isVowel instead.", ReplaceWith("isVowel(char)"))
fun isMoum(char: Char): Boolean = isVowel(char)

fun isModernHangeulSyllable(char: Char): Boolean {
    return char in HANGUL_SYLLABLES
}

fun isIPFHangeulSyllable(string: String): Boolean {
    return if (string.length <= 1) {
        false
    } else Tone.values().firstOrNull { it.char == string[string.length - 1] }?.let {
        isIPFHangeulSyllable(string.substring(0, string.length - 1))
    } ?: when (string.length) {
        2 -> string[0] in HANGUL_ONSET_MODERN + HANGUL_ONSET_OLD && string[1] in HANGUL_NUCLEUS_MODERN + HANGUL_NUCLEUS_OLD
        3 -> string[2] in HANGUL_CODA_MODERN + HANGUL_CODA_OLD && isIPFHangeulSyllable(string.substring(0, 2))
        else -> false
    }
}

fun isHangeulSyllable(string: String): Boolean {
    if (string.length == 1) {
        return isModernHangeulSyllable(string[0])
    }
    return isIPFHangeulSyllable(string)
}

/**
 * The alias of [isHangeulSyllable], han'gŭl(or hangul) is McCune-Reischauer system notation of 한글,
 * 'Hangeul' is official in South Korea, since 2000
 */
@Deprecated(
    "Hangul is old system notation of 한글, So use isHangeulSyllable instead.",
    ReplaceWith("isHangeulSyllable(string)")
)
fun isHangulSyllable(string: String): Boolean = isHangeulSyllable(string)

fun hasCoda(string: String): Boolean =
    string.takeIf { isHangeulSyllable(it) }?.run { HangeulSyllable(this) }?.coda !== null
