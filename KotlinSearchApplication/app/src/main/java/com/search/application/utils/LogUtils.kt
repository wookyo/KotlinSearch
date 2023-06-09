package com.v1.application.utils

import android.util.Log
import com.search.application.BuildConfig


object LogUtils {

    fun d(tag: String, vararg messages: Any) {
        // Only log DEBUG if build type is DEBUG
        if (BuildConfig.DEBUG) {
            log(tag, Log.INFO, null, *messages)
        }
    }

    fun e(tag: String, vararg messages: Any) {
        log(tag, Log.ERROR, null, *messages)
    }

    fun log(tag: String, level: Int, t: Throwable?, vararg messages: Any) {
        if (Log.isLoggable(tag, level)) {
            val message = if (t == null && messages != null && messages.size == 1) {
                // handle this common case without the extra cost of creating a stringbuffer:
                messages[0].toString()
            } else {
                val sb = StringBuilder()
                if (messages != null)
                    for (m in messages) {
                        sb.append(m)
                    }
                if (t != null) {
                    sb.append('\n').append(Log.getStackTraceString(t))
                }
                sb.toString()
            }
            Log.println(level, tag, message)
        }
    }
}