package io.github.ranolp.hangeulio

import io.github.ranolp.hangeulio.vo.HangeulSyllable
import junit.framework.Assert.assertEquals
import org.junit.Test

class HangeulSyllableTest {
    @Test
    fun testSimple() {
        val syllable = HangeulSyllable('간')
        assertEquals(syllable.onset.char, 'ᄀ')
        assertEquals(syllable.nucleus.char, 'ᅡ')
        assertEquals(syllable.coda?.char, 'ᆫ')

        val syllableCompat = syllable.toOnlyCompatible
        assertEquals(syllableCompat.onset.char, 'ㄱ')
        assertEquals(syllableCompat.nucleus.char, 'ㅏ')
        assertEquals(syllableCompat.coda?.char, 'ㄴ')
    }
}
