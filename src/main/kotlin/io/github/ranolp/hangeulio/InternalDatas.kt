package io.github.ranolp.hangeulio


// U+1100, Hangul Choseong
internal const val STD_CHO = "ᄀᄁᄂᄃᄄᄅᄆᄇᄈᄉᄊᄋᄌᄍᄎᄏᄐᄑᄒ"
// U+1161, Hangul Jungseong
internal const val STD_JUNG = "ᅡᅢᅣᅤᅥᅦᅧᅨᅩᅪᅫᅬᅭᅮᅯᅰᅱᅲᅳᅴᅵ"
// U+11A8, Hangul Jongseong
internal const val STD_JONG = "ᆨᆩᆪᆫᆬᆭᆮᆯᆰᆱᆲᆳᆴᆵᆶᆷᆸᆹᆺᆻᆼᆽᆾᆿᇀᇁᇂ"

// U+3131, Hangul Letter
internal const val COMPAT_CHO = "ㄱㄲㄳㄴㄵㄶㄷㄸㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅃㅄㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ"
// U+314F, Hangul Letter
internal const val COMPAT_JUNG = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ"

internal val HANGUL_ONSET_MODERN = 'ᄀ'..'ᄒ'
internal val HANGUL_ONSET_OLD = ('ᄓ'..'ᅞ') + ('ꥠ'..'ꥼ')
internal val HANGUL_NUCLEUS_MODERN = 'ᅡ'..'ᅵ'
internal val HANGUL_NUCLEUS_OLD = ('ᅶ'..'ᆧ') + ('ힰ'..'ퟆ')
internal val HANGUL_CODA_MODERN = 'ᆨ'..'ᇂ'
internal val HANGUL_CODA_OLD = ('ᇃ'..'ᇿ') + ('ퟋ'..'ퟻ')


internal val HANGUL_LETTER_CONSONANT = 'ㄱ'..'ㅎ'
internal val HANGUL_LETTER_VOWEL = 'ㅏ'..'ㅣ'

internal const val WON_SIGN = '₩'
internal const val FULLWIDTH_WON_SIGN = '￦'

internal val PARENTHESIZED_HANGUL_LETTERS = '㈀'..'㈍'
internal val PARENTHESIZED_HANGUL_SYLLABLES = '㈎'..'㈛'
internal val PARENTHESIZED_KOREAN_WORDS = "㈜㈝㈞"
internal val CIRCLED_HANGUL_LETTERS = '㉠'..'㉭'
internal val CIRCLED_HANGUL_SYLLABLES = '㉮'..'㉻'
internal val CIRCLED_KOREAN_WORDS = "㉼㉽"
internal val CIRCLED_KOREAN_SYLLABLE = "㉾"
internal val KOREA_STANDARD_SYMBOL = "㉿"

internal val HANGUL_SYLLABLES = '가'..'힣'

internal val HALFWIDTH_HANGUL_LETTER_CONSONANT = 'ﾡ'..'ﾾ'
internal val HALFWIDTH_HANGUL_LETTER_VOWEL = ('ￂ'..'ￇ') + ('ￊ'..'ￏ') + ('ￒ'..'ￗ') + ('ￚ'..'ￜ')
