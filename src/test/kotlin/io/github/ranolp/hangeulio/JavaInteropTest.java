package io.github.ranolp.hangeulio;

import io.github.ranolp.hangeulio.vo.HangeulPhoneme;
import io.github.ranolp.hangeulio.vo.HangeulSyllable;
import org.junit.Test;

import static org.junit.Assert.*;

public class JavaInteropTest {
    @Test
    public void hangeulPhonemeTest() {
        HangeulPhoneme phoneme = HangeulPhoneme.of('ㄱ');
        assertTrue(phoneme.canBeOnset());
        assertFalse(phoneme.canBeNucleus());
        assertTrue(phoneme.canBeCoda());

        assertEquals(phoneme, phoneme.toCompatiblePhoneme());
        assertEquals(phoneme, phoneme.toNormalizable());
    }

    @Test
    public void hangeulSyllableTest() {
        HangeulSyllable syllable = HangeulSyllable.of('간');

        HangeulPhoneme onset = syllable.getOnset().toCompatiblePhoneme();
        assertNotNull(onset);
        assertEquals(onset.getChar(), 'ㄱ');

        HangeulPhoneme nucleus = syllable.getNucleus().toCompatiblePhoneme();
        assertNotNull(nucleus);
        assertEquals(nucleus.getChar(), 'ㅏ');

        HangeulPhoneme codaPre = syllable.getCoda();
        assertNotNull(codaPre);
        HangeulPhoneme coda = codaPre.toCompatiblePhoneme();
        assertNotNull(coda);
        assertEquals(coda.getChar(), 'ㄴ');

        assertNull(syllable.getTone());
    }

    @Test
    public void hangeulioTest() {
        assertTrue(Hangeulio.isConsonant('ㄱ'));
        assertFalse(Hangeulio.isConsonant('ㅙ'));

        assertTrue(Hangeulio.isVowel('ㅏ'));
        assertFalse(Hangeulio.isVowel('ㅍ'));

        assertTrue(Hangeulio.isModernHangeulSyllable('각'));
        assertTrue(Hangeulio.isModernHangeulSyllable('굉'));
        assertFalse(Hangeulio.isModernHangeulSyllable('A'));

        assertTrue(Hangeulio.isIPFHangeulSyllable("ᄊᆡ〮"));
        assertTrue(Hangeulio.isIPFHangeulSyllable("ᄣᆗᇌ"));
        assertFalse(Hangeulio.isIPFHangeulSyllable("각"));


        assertTrue(Hangeulio.isHangeulSyllable("각"));
        assertTrue(Hangeulio.isHangeulSyllable("굉"));
        assertTrue(Hangeulio.isHangeulSyllable("ᄊᆡ〮"));
        assertTrue(Hangeulio.isHangeulSyllable("ᄣᆗᇌ"));
    }
}
