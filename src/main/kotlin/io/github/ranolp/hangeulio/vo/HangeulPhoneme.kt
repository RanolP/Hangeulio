package io.github.ranolp.hangeulio.vo

import io.github.ranolp.hangeulio.*

/**
 * 하나의 한글 음소를 저장하는 클래스입니다.
 * ...근데 옛한글 보면 여러 개의 자모가 결합되고 개판이던데 음소가 하나인걸까...
 */
class HangeulPhoneme private constructor(type: Type, val keyboardSet: KeyboardSet, val char: Char) {
    /**
     * 음소의 종류입니다. 자음/모음/알 수 없음이 있습니다.
     */
    enum class Type {
        CONSONANT,
        VOWEL,
        UNKNOWN
    }

    companion object {
        private val cachePool = mutableMapOf<Char, HangeulPhoneme>()

        /**
         * 음소를 갖고 옵니다. 어차피 한글 음소 그거 많지도 않던데 캐싱해두죠 뭐.
         * 무슨 문제 있겠어?
         *
         * @param char 대상 문자입니다
         * @return 캐싱된 음소를 갖고 옵니다. 없다면 새로 만들어 캐싱합니다.
         * @throws Exception 올바르지 않은 한글 문자일 경우 예외가 발생합니다.
         */
        @JvmName("of")
        operator fun invoke(char: Char): HangeulPhoneme = cachePool.getOrPut(char) {
            when (char) {
                in HALFWIDTH_HANGUL_LETTER_CONSONANT,
                in HANGUL_LETTER_CONSONANT -> HangeulPhoneme(Type.CONSONANT, KeyboardSet.TWO_SET, char)

                in HALFWIDTH_HANGUL_LETTER_VOWEL,
                in HANGUL_LETTER_VOWEL -> HangeulPhoneme(Type.VOWEL, KeyboardSet.TWO_SET, char)

                in HANGUL_ONSET_MODERN,
                in HANGUL_ONSET_OLD,
                in HANGUL_CODA_MODERN,
                in HANGUL_CODA_OLD -> HangeulPhoneme(Type.CONSONANT, KeyboardSet.THREE_SET, char)

                in HANGUL_NUCLEUS_MODERN,
                in HANGUL_NUCLEUS_OLD -> HangeulPhoneme(Type.VOWEL, KeyboardSet.THREE_SET, char)

                in PARENTHESIZED_HANGUL_LETTERS,
                in CIRCLED_HANGUL_LETTERS -> HangeulPhoneme(Type.CONSONANT, KeyboardSet.UNDEFINED, char)

            // todo: replace with own exception
                else -> throw Exception("$char is not valid hangeul character")
            }
        }
    }

    /**
     * 자음 여부
     */
    val isConsonant: Boolean = type === Type.CONSONANT

    /**
     * 모음 여부
     */
    val isVowel: Boolean = type === Type.VOWEL

    /**
     * 현대 한글 음소 여부
     */
    val isModern: Boolean by lazy {
        when (char) {
            in HANGUL_LETTER_CONSONANT,
            in HANGUL_LETTER_VOWEL,
            in HANGUL_ONSET_MODERN,
            in HANGUL_NUCLEUS_MODERN,
            in HANGUL_CODA_MODERN -> true
            else -> false
        }
    }

    /**
     * 현대 한글 음소가 아닌 여부 !isModern 의 별칭입니다.
     */
    val isNotModern: Boolean by lazy {
        !isModern
    }

    /**
     * 옛한글 음소 여부
     */
    val isOld: Boolean by lazy {
        when (char) {
            in HANGUL_ONSET_OLD,
            in HANGUL_NUCLEUS_OLD,
            in HANGUL_CODA_OLD -> true
            else -> false
        }
    }

    /**
     * 옛한글 음소가 아닌 여부, !isOld 의 별칭입니다.
     */
    val isNotOld: Boolean by lazy {
        !isOld
    }

    /**
     * 첫 소리가 될 수 있는 여부
     */
    val canBeOnset: Boolean by lazy {
        isConsonant && if (keyboardSet !== KeyboardSet.TWO_SET) {
            when (char) {
                in HANGUL_ONSET_MODERN,
                in HANGUL_ONSET_OLD -> true
                else -> false
            }
        } else true
    }

    /**
     * 가운데 소리가 될 수 있는 여부
     */
    val canBeNucleus: Boolean by lazy {
        isConsonant && if (keyboardSet !== KeyboardSet.TWO_SET) {
            when (char) {
                in HANGUL_NUCLEUS_MODERN,
                in HANGUL_NUCLEUS_OLD -> true
                else -> false
            }
        } else true
    }


    /**
     * 끝 소리가 될 수 있는 여부
     */
    val canBeCoda: Boolean by lazy {
        isConsonant && if (keyboardSet !== KeyboardSet.TWO_SET) {
            when (char) {
                in HANGUL_CODA_MODERN,
                in HANGUL_CODA_OLD -> true
                else -> false
            }
        } else true
    }

    /**
     * 가능한 경우, 현대 한글이나 옛한글로 변환한 값
     */
    val toNormalizable: HangeulPhoneme? by lazy {
        if (isNotModern && isNotOld) {
            when (char) {
                in PARENTHESIZED_HANGUL_LETTERS -> invoke(char - 0xCF)
                in CIRCLED_HANGUL_LETTERS -> invoke(char - 0x12F)
                in HALFWIDTH_HANGUL_LETTER_CONSONANT -> invoke(char - 0xce70)
                else -> null
            }
        } else this
    }

    /**
     * 가능한 경우, 호환 한글 자모로 변환한 값
     */
    val toCompatibleConsonant: HangeulPhoneme? by lazy {
        if (isModern) {
            when (char) {
                in HANGUL_LETTER_CONSONANT,
                in HANGUL_LETTER_VOWEL -> this

                in HANGUL_ONSET_MODERN -> invoke(char + 0x2031)
                in HANGUL_NUCLEUS_MODERN -> invoke(char + 0x1FEE)
                in HANGUL_CODA_MODERN -> invoke(char + 0x1f89)
                else -> null
            }
        } else {
            toNormalizable?.toCompatibleConsonant
        }
    }

    override fun toString(): String = "HangeulPhoneme(type=${when {
        isConsonant -> "Consonant" + if (canBeOnset != canBeCoda) {
            if (canBeOnset) "(Onset)" else "Coda"
        } else ""
        isVowel -> "Vowel"
        else -> "Unknown"
    }}, keyboardSet=$keyboardSet, char=$char${toNormalizable?.char?.takeIf { it != char }?.let { ", modernChar=$it" }
            ?: ""}${toCompatibleConsonant?.char?.takeIf { it != char }?.let { ", compatChar=$it" } ?: ""})"


    /**
     * 같은 '벌'일 때(알 수 없는 경우 두벌식으로 가정함), 문자가 같은지 비교합니다.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HangeulPhoneme

        if (!keyboardSet.isSimilar(other.keyboardSet)) return false

        return toNormalizable?.char == other.toNormalizable?.char
    }

    override fun hashCode(): Int {
        var result = keyboardSet.hashCode()
        result = 31 * result + char.hashCode()
        return result
    }
}
