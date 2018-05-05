package io.github.ranolp.hangeulio.vo

/**
 * 두벌식, 세벌식, 네벌식 등의 -벌 을 담은 클래스입니다.
 *
 * 반면 세벌식은 말 그대로 "세 벌(set)"으로 이루어져 있는 자판이기 때문에, 한글의 구성요소인 초성 - 중성 - 종성을 입력하는 키가 각기 따로 할당되어 있다.
 * 때문에 직결식으로 구현할 때, 초성과 중성, 종성의 출력되는 위치를 각기 다르게 할 수 있어, 두벌식 직결식에 비해 훨씬 미려하게 보인다.
 * - 나무위키, 직결식 글꼴 r39 2. 원리에서 발췌
 *
 * 다음과 같은 이유를 Set 을 사용하나, 자료형으로 오해할 수 있으므로 Keyboard 를 붙인다.
 */
enum class KeyboardSet(val id: Int) {
    /**
     * -벌 형식이 아닌 경우입니다, 두벌식과 '유사'하다고 간주합니다.
     */
    UNDEFINED(-1) {
        override fun isSimilar(other: KeyboardSet): Boolean = this == other || other == TWO_SET
    },
    /**
     * 두벌식, 자음과 모음만 존재합니다.
     */
    TWO_SET(2) {
        override fun isSimilar(other: KeyboardSet): Boolean = this == other || other == UNDEFINED
    },
    /**
     * 세벌식, 초성, 중성, 종성이 존재합니다.
     */
    THREE_SET(3) {
        override fun isSimilar(other: KeyboardSet): Boolean = this == other
    };

    abstract fun isSimilar(other: KeyboardSet): Boolean
}
