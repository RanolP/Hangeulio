package io.github.ranolp.hangeulio.vo

import io.github.ranolp.hangeulio.isHangeulSyllable
import io.github.ranolp.hangeulio.isIPFHangeulSyllable
import io.github.ranolp.hangeulio.isModernHangeulSyllable
import java.text.Normalizer

/**
 * 굳어진 표현은 Hangul + Syllable[McCune-Reischauer 표기법에 의하면 han'gŭl]이나,
 * 2000년에 제정된 공식 표기법을 기준으로 하므로, Hangeul 로 칭합니다.
 *
 * 나무위키와 위키피디아에 의하면
 *
 * 보편적으로 음절 한 단위는 두음(頭音, onset)·음절핵(音節核, nucleus)·말음(末音, coda)으로 나눌 수 있다.
 * 이 중 음절핵과 말음은 합쳐서 운(韻, rhyme[2])이라고 부를 수 있다.
 * - 나무위키, 음절 문서 r226, 2.1 보편적 체계에서 발췌
 *
 * 한국어의 음절은 초성(onset), 중성(nucleus), 종성(coda)으로 이루어져 있다.
 * - 위키백과, 음절 문서 2016년 3월 11일 02:21 판, 2 한국어의 음절에서 발췌
 *
 * 다음과 같이 나타낸다고 합니다. 그러니 choseong, jungseong, jongseong 등의 음차한 표기 대신,
 * onset, nucleus, coda 등의 표기를 사용합니다.
 */
class HangeulSyllable private constructor(
    val onset: HangeulPhoneme,
    val nucleus: HangeulPhoneme,
    val coda: HangeulPhoneme?,
    val tone: Tone?
) {
    companion object {
        @JvmStatic
        @JvmName("of")
        operator fun invoke(
            onset: HangeulPhoneme,
            nucleus: HangeulPhoneme,
            coda: HangeulPhoneme? = null,
            tone: Tone? = null
        ): HangeulSyllable = HangeulSyllable(onset, nucleus, coda, tone)

        @JvmStatic
        @JvmName("of")
        operator fun invoke(
            onset: Char,
            nucleus: Char,
            coda: Char? = null,
            tone: Tone? = null
        ): HangeulSyllable =
            HangeulSyllable(HangeulPhoneme(onset), HangeulPhoneme(nucleus), coda?.let { HangeulPhoneme(it) }, tone)

        @JvmStatic
        @JvmName("of")
        operator fun invoke(char: Char): HangeulSyllable {
            if (!isModernHangeulSyllable(char)) {
                throw Exception("$char is not a hangeul")
            }
            val normalized = Normalizer.normalize(char.toString(), Normalizer.Form.NFD)
            return when (normalized.length) {
                2 -> HangeulSyllable(HangeulPhoneme(normalized[0]), HangeulPhoneme(normalized[1]), null)
                3 -> HangeulSyllable(
                    HangeulPhoneme(normalized[0]),
                    HangeulPhoneme(normalized[1]),
                    HangeulPhoneme(normalized[2])
                )
                else -> throw Exception("Normalized value is not correct: $normalized")
            }
        }

        @JvmStatic
        @JvmName("of")
        operator fun invoke(string: String): HangeulSyllable {
            if (isIPFHangeulSyllable(string)) {
                return Tone.values().firstOrNull { it.char == string[string.length - 1] }?.let {
                    if (string.length == 3) HangeulSyllable(string[0], string[1], tone = it)
                    else HangeulSyllable(string[0], string[1], string[2], it)
                } ?: if (string.length == 2) HangeulSyllable(string[0], string[1])
                else HangeulSyllable(string[0], string[1], string[2])
            }
            if (!isHangeulSyllable(string)) {
                throw Exception("$string is not a hangeul")
            }
            if (string.length == 1) {
                return HangeulSyllable(string[0])
            } else {
                throw Exception("$string is not a hangeul")
            }
        }
    }

    /**
     * The alias of [onset]
     */
    val initialSound: HangeulPhoneme
        get() = onset

    /**
     * The alias of [onset]
     */
    val firstSound: HangeulPhoneme
        get() = onset

    /**
     * The alias of [nucleus]
     */
    val peakSound: HangeulPhoneme
        get() = nucleus

    /**
     * The alias of [coda]
     */
    val finalSound: HangeulPhoneme
        get() = onset


    @Deprecated("choseong is 초성's phonetic. So, use onset instead.", ReplaceWith("onset"))
    val choSeong: HangeulPhoneme
        get() = onset

    @Deprecated("choseong is 초성's phonetic. So, use nucleus instead.", ReplaceWith("onset"))
    val jungSeong: HangeulPhoneme
        get() = nucleus

    @Deprecated("choseong is 초성's phonetic. So, use coda instead.", ReplaceWith("onset"))
    val jongSeong: HangeulPhoneme?
        get() = coda

    val isModernHangeul: Boolean by lazy {
        onset.toCompatiblePhoneme != null && nucleus.toCompatiblePhoneme != null && coda?.let {
            it.toCompatiblePhoneme != null
        } ?: true
    }

    val modernize: HangeulSyllable by lazy {
        HangeulSyllable(onset.toCompatiblePhoneme!!, nucleus.toCompatiblePhoneme!!, coda?.toCompatiblePhoneme)
    }

    operator fun component1(): HangeulPhoneme = onset
    operator fun component2(): HangeulPhoneme = nucleus
    operator fun component3(): HangeulPhoneme? = coda
    operator fun component4(): Tone? = tone
}
