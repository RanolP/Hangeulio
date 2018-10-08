package io.github.ranolp.hangeulio

import io.github.ranolp.hangeulio.vo.HangeulSyllable

sealed class Particle {
    private class BasedOnCodaExistence(
        private val hasCoda: String, private val noCoda: String, private val composed: String
    ) : Particle() {
        override fun attach(text: String): String {
            val last = text.lastHangeulPronounce ?: return ""
            return if (isHangeulSyllable(last)) {
                return text + (if (hasCoda(last)) hasCoda else noCoda)
            } else {
                "$text$composed"
            }
        }
    }

    private object Euro : Particle() {
        override fun attach(text: String): String {
            val last = text.lastHangeulPronounce ?: return ""
            return if (isHangeulSyllable(last)) {
                val coda = HangeulSyllable(last).coda?.toCompatiblePhoneme?.char
                return text + (if (coda != null && coda != 'ㄹ') "으로" else "로")
            } else {
                "$text(으)로"
            }
        }
    }

    companion object {
        private fun simple(hasCoda: String, noCoda: String): Particle {
            return BasedOnCodaExistence(hasCoda, noCoda, "($hasCoda)$noCoda")
        }

        private fun skippable(skipOnNoCoda: String, base: String): Particle {
            return BasedOnCodaExistence("$skipOnNoCoda$base", base, "($skipOnNoCoda)$base")
        }

        /**
         * 주격/보격 조사 이/가
         */
        val I_GA = Particle.simple("이", "가")
        /**
         * 목적격 조사 을/를
         */
        val EUL_REUL = Particle.simple("을", "를")
        /**
         * 방향격 조사 으로/로
         */
        val EURO_RO: Particle = Euro
        /**
         * 동반격/접속 조사 과/와
         */
        val GWA_WA = Particle.simple("과", "와")
        /**
         * 동반격/접속 조사 이랑/랑
         */
        val IRANG_RANG = Particle.skippable("이", "랑")
        /**
         * 호격 조사 아/야
         */
        val A_YA = Particle.simple("아", "야")
        /**
         * 호격 조사 이여/여
         */
        val IYEO_YEO = Particle.skippable("이", "여")
        /**
         * 보조사 은/는
         */
        val EUN_NEUN = Particle.simple("은", "는")
        /**
         * 보조사 이나/나
         */
        val INA_NA = Particle.skippable("이", "나")
        /**
         * 보조사 이란/란
         */
        val IRAN_RAN = Particle.skippable("이", "란")
        /**
         * 보조사 이든지/든지
         */
        val IDEUNJI_DEUNJI = Particle.skippable("이", "든지")
        /**
         * 보조사 이나마/나마
         */
        val INAMA_NAMA = Particle.skippable("이", "나마")
        /**
         * 보조사 이나마/나마
         */
        val IYAMALRO_YAMALRO = Particle.skippable("이", "야말로")
    }


    abstract fun attach(text: String): String
}
