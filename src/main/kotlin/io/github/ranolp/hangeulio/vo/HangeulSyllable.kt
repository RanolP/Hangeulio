package io.github.ranolp.hangeulio.vo

import io.github.ranolp.hangeulio.isHangeulSyllable
import io.github.ranolp.hangeulio.isIPFHangeulSyllable
import io.github.ranolp.hangeulio.isModernHangeulSyllable
import java.text.Normalizer

/**
 * 하나의 한글 음절을 저장하는 클래스입니다.
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
        ): HangeulSyllable {
            return if (tone != null && onset.isModern && nucleus.isModern && coda?.isNotModern != true) {
                HangeulSyllable(onset, nucleus, coda)
            } else {
                HangeulSyllable(onset, nucleus, coda, tone)
            }
        }

        @JvmStatic
        @JvmName("of")
        operator fun invoke(onset: Char, nucleus: Char, coda: Char? = null, tone: Tone? = null): HangeulSyllable {
            return invoke(HangeulPhoneme(onset), HangeulPhoneme(nucleus), coda?.let { HangeulPhoneme(it) }, tone)
        }

        @JvmStatic
        @JvmName("of")
        operator fun invoke(char: Char): HangeulSyllable {
            if (!isModernHangeulSyllable(char)) {
                throw Exception("$char is not a hangeul")
            }
            val normalized = Normalizer.normalize(char.toString(), Normalizer.Form.NFD)
            if (normalized.length != 2 && normalized.length != 3) {
                error("Normalized value is not correct: $normalized")
            }
            val onset = HangeulPhoneme(normalized[0])
            val nucleus = HangeulPhoneme(normalized[1])
            val coda = if (normalized.length == 3) HangeulPhoneme(normalized[2]) else null
            return HangeulSyllable(onset, nucleus, coda)
        }

        @JvmStatic
        @JvmName("of")
        operator fun invoke(string: String): HangeulSyllable {
            if (isIPFHangeulSyllable(string)) {
                val tone = Tone.values().firstOrNull { it.char == string[string.length - 1] }
                return if (tone != null) {
                    if (string.length == 3) {
                        HangeulSyllable(string[0], string[1], tone = tone)
                    } else {
                        HangeulSyllable(string[0], string[1], string[2], tone)
                    }
                } else {
                    if (string.length == 2) {
                        HangeulSyllable(string[0], string[1])
                    } else {
                        HangeulSyllable(string[0], string[1], string[2])
                    }
                }
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

    val stringfy by lazy {
        Normalizer.normalize(
            "${onset.asOnsetCharacter ?: onset.char}${nucleus.asNucleusCharacter ?: nucleus.char}${coda?.asCodaCharacter
                ?: coda?.char ?: ""}${tone?.char ?: ""}", Normalizer.Form.NFC
        )
    }

    val modernize: HangeulSyllable by lazy {
        HangeulSyllable(onset.toCompatiblePhoneme!!, nucleus.toCompatiblePhoneme!!, coda?.toCompatiblePhoneme)
    }

    operator fun component1(): HangeulPhoneme = onset
    operator fun component2(): HangeulPhoneme = nucleus
    operator fun component3(): HangeulPhoneme? = coda
    operator fun component4(): Tone? = tone
}
