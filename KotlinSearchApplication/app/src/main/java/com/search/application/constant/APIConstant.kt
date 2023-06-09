package com.v1.application.constant

class APIConstant {
    companion object {
        const val BASE_URL_MAIN_LIST = "https://google.com/"


        // naver
        const val SEARCH_TEXT_NAVER_URL = "https://openapi.naver.com/v1/search/image"
        const val SEARCH_TEXT_NAVER_CLIENT_ID = "yFFwxGSDxHagn6Aa20bQ"
        const val SEARCH_TEXT_NAVER_CLIENT_SECRET = "vJgckIERfK"

        //cacao
        const val SEARCH_TEXT_KAKAO_URL = "https://dapi.kakao.com/v2/search/image"
        const val KAKAO_REST_API_KEY = "e5ade49ee0ab94df973130a331ae0233"
        const val SEARCH_TEXT_KAKAO_REST_API_KEY = "KakaoAK ${KAKAO_REST_API_KEY}"

        const val QUERY = "query"
        const val DISPLAY = "display"
        const val START = "start"
        const val SORT = "sort"
        const val PAGE = "page"
        const val SIZE = "size"
    }
}