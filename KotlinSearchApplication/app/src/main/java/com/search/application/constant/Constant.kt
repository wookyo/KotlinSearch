package com.search.application.constant

class Constant {
    companion object {
        const val PREF_KEY_MAIN_SELECT = "PREF_KEY_MAIN_SELECT"
    }

    enum class TYPE(val item: String) {
        KAKAO("KAKAO"),
        NAVER("NAVER")
    }


}