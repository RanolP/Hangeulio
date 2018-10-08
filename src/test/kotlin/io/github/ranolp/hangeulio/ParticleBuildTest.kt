package io.github.ranolp.hangeulio

import kotlin.test.Test
import kotlin.test.assertEquals

class ParticleBuildTest {
    @Test
    fun test() {
        assertEquals(Particle.I_GA.attach("달"), "달이")
        assertEquals(Particle.I_GA.attach("해"), "해가")
        assertEquals(Particle.I_GA.attach("ABC"), "ABC(이)가")

        assertEquals(Particle.EUL_REUL.attach("달"), "달을")
        assertEquals(Particle.EUL_REUL.attach("해"), "해를")
        assertEquals(Particle.EUL_REUL.attach("ABC"), "ABC(을)를")

        assertEquals(Particle.EURO_RO.attach("톱"), "톱으로")
        assertEquals(Particle.EURO_RO.attach("달"), "달로")
        assertEquals(Particle.EURO_RO.attach("해"), "해로")
        assertEquals(Particle.EURO_RO.attach("ABC"), "ABC(으)로")

        assertEquals(Particle.GWA_WA.attach("달"), "달과")
        assertEquals(Particle.GWA_WA.attach("해"), "해와")
        assertEquals(Particle.GWA_WA.attach("ABC"), "ABC(과)와")

        assertEquals(Particle.IRANG_RANG.attach("달"), "달이랑")
        assertEquals(Particle.IRANG_RANG.attach("해"), "해랑")
        assertEquals(Particle.IRANG_RANG.attach("ABC"), "ABC(이)랑")

        assertEquals(Particle.A_YA.attach("달"), "달아")
        assertEquals(Particle.A_YA.attach("해"), "해야")
        assertEquals(Particle.A_YA.attach("ABC"), "ABC(아)야")

        assertEquals(Particle.IYEO_YEO.attach("달"), "달이여")
        assertEquals(Particle.IYEO_YEO.attach("해"), "해여")
        assertEquals(Particle.IYEO_YEO.attach("ABC"), "ABC(이)여")

        assertEquals(Particle.EUN_NEUN.attach("달"), "달은")
        assertEquals(Particle.EUN_NEUN.attach("해"), "해는")
        assertEquals(Particle.EUN_NEUN.attach("ABC"), "ABC(은)는")

        assertEquals(Particle.INA_NA.attach("달"), "달이나")
        assertEquals(Particle.INA_NA.attach("해"), "해나")
        assertEquals(Particle.INA_NA.attach("ABC"), "ABC(이)나")

        assertEquals(Particle.IRAN_RAN.attach("달"), "달이란")
        assertEquals(Particle.IRAN_RAN.attach("해"), "해란")
        assertEquals(Particle.IRAN_RAN.attach("ABC"), "ABC(이)란")

        assertEquals(Particle.IDEUNJI_DEUNJI.attach("달"), "달이든지")
        assertEquals(Particle.IDEUNJI_DEUNJI.attach("해"), "해든지")
        assertEquals(Particle.IDEUNJI_DEUNJI.attach("ABC"), "ABC(이)든지")
    }
}
