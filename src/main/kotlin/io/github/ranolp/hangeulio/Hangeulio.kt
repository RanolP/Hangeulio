@file:JvmName("Hangeulio")

package io.github.ranolp.hangeulio

import io.github.ranolp.hangeulio.vo.HangeulPhoneme

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

fun isHangeulSyllable(string: String): Boolean {
    if (string.length == 1) {
        return isModernHangeulSyllable(string[0])
    }
    // TODO: Normalize 해야하는 상태의 경우
    // TODO: 옛 한글 상태의 경우
    return false
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
