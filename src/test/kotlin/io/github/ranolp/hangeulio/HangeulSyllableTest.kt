package io.github.ranolp.hangeulio

import io.github.ranolp.hangeulio.vo.HangeulSyllable
import io.github.ranolp.hangeulio.vo.Tone
import org.junit.Assert.*
import org.junit.Test

class HangeulSyllableTest {
    @Test
    fun testSimple() {
        val syllable = HangeulSyllable('간')
        assertEquals(syllable.onset.char, 'ᄀ')
        assertEquals(syllable.nucleus.char, 'ᅡ')
        assertEquals(syllable.coda?.char, 'ᆫ')
        assertTrue(syllable.isModernHangeul)

        val syllableCompat = syllable.modernize
        assertEquals(syllableCompat.onset.char, 'ㄱ')
        assertEquals(syllableCompat.nucleus.char, 'ㅏ')
        assertEquals(syllableCompat.coda?.char, 'ㄴ')

        val oldSyllable = HangeulSyllable("ᄣᆗᇌ")
        assertEquals(oldSyllable.onset.char, 'ᄣ')
        assertEquals(oldSyllable.nucleus.char, 'ᆗ')
        assertEquals(oldSyllable.coda?.char, 'ᇌ')

        val oldSyllableWithToneMark = HangeulSyllable("ᄊᆡ〮")
        assertEquals(oldSyllableWithToneMark.onset.char, 'ᄊ')
        assertEquals(oldSyllableWithToneMark.nucleus.char, 'ᆡ')
        assertNull(oldSyllableWithToneMark.coda)
        assertEquals(oldSyllableWithToneMark.tone, Tone.SINGLE_DOT)
    }
}
