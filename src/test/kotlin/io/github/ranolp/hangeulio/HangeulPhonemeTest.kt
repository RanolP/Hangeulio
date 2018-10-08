package io.github.ranolp.hangeulio

import io.github.ranolp.hangeulio.vo.HangeulPhoneme
import io.github.ranolp.hangeulio.vo.KeyboardSet
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class HangeulPhonemeTest {
    val giyeok = HangeulPhoneme('ㄱ')
    val parenthesizedGiyeok = HangeulPhoneme('㈀')
    val circledGiyeok = HangeulPhoneme('㉠')
    val halfWidthGiyeok = HangeulPhoneme('ﾡ')
    val onsetGiyeok = HangeulPhoneme('ᄀ')
    val codaGiyeok = HangeulPhoneme('ᆨ')

    val a = HangeulPhoneme('ㅏ')
    val nucleusA = HangeulPhoneme('ᅡ')

    @Test
    fun testSimpleData() {
        assertEquals(giyeok.char, 'ㄱ')
        assertTrue(giyeok.isConsonant)
        assertTrue(giyeok.isModern)
        assertEquals(giyeok.keyboardSet, KeyboardSet.TWO_SET)

        assertEquals(parenthesizedGiyeok.toNormalizable?.char, 'ㄱ')
        assertTrue(parenthesizedGiyeok.isConsonant)
        assertTrue(parenthesizedGiyeok.isNotModern)
        assertEquals(parenthesizedGiyeok.keyboardSet, KeyboardSet.UNDEFINED)

        assertEquals(circledGiyeok.toNormalizable?.char, 'ㄱ')
        assertTrue(circledGiyeok.isConsonant)
        assertTrue(circledGiyeok.isNotModern)
        assertEquals(circledGiyeok.keyboardSet, KeyboardSet.UNDEFINED)

        assertEquals(halfWidthGiyeok.toNormalizable?.char, 'ㄱ')
        assertTrue(halfWidthGiyeok.isConsonant)
        assertTrue(halfWidthGiyeok.isNotModern)
        assertEquals(halfWidthGiyeok.keyboardSet, KeyboardSet.TWO_SET)

        assertEquals(onsetGiyeok.char, 'ᄀ')
        assertEquals(onsetGiyeok.toCompatiblePhoneme?.char, 'ㄱ')
        assertTrue(onsetGiyeok.isConsonant)
        assertTrue(onsetGiyeok.isModern)
        assertEquals(onsetGiyeok.keyboardSet, KeyboardSet.THREE_SET)

        assertEquals(a.char, 'ㅏ')
        assertTrue(a.isVowel)
        assertTrue(a.isModern)
        assertEquals(a.keyboardSet, KeyboardSet.TWO_SET)

        assertEquals(nucleusA.char, 'ᅡ')
        assertEquals(nucleusA.toCompatiblePhoneme?.char, 'ㅏ')
        assertTrue(nucleusA.isVowel)
        assertTrue(nucleusA.isModern)
        assertEquals(nucleusA.keyboardSet, KeyboardSet.THREE_SET)

        assertEquals(codaGiyeok.char, 'ᆨ')
        assertEquals(codaGiyeok.toCompatiblePhoneme?.char, 'ㄱ')
        assertTrue(codaGiyeok.isConsonant)
        assertTrue(codaGiyeok.isModern)
        assertEquals(codaGiyeok.keyboardSet, KeyboardSet.THREE_SET)
    }

    @Test
    fun testEquality() {
        assertEquals(giyeok, parenthesizedGiyeok)
        assertEquals(giyeok, circledGiyeok)
        assertEquals(giyeok, halfWidthGiyeok)

        assertNotEquals(giyeok, onsetGiyeok)
        assertNotEquals(giyeok, codaGiyeok)

        assertNotEquals(onsetGiyeok, codaGiyeok)
    }
}
