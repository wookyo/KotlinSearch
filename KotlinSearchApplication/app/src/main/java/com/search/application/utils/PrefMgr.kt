package com.search.application.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.search.application.model.HomeListModel.HomeItemModel
import com.v1.application.utils.LogUtils
import org.json.JSONArray
import java.lang.reflect.Type
import java.util.*


object PrefMgr  {

    private lateinit var preferences: SharedPreferences
    private val preferencesName = "search_preference"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(preferencesName, Activity.MODE_PRIVATE)
    }


    fun put(key: String, value: Any?) {
        if (value == null) {
            preferences.edit().putString(key, "").apply()
        }

        if (value is String) {
            if (TextUtils.isEmpty(value)) {
                preferences.edit().putString(key, "").apply()
            }
        }
        val gson = Gson()
        preferences.edit().putString(key, gson.toJson(value)).apply()
    }

    fun getStringArray(key: String): java.util.ArrayList<String> {
        return java.util.ArrayList<String>().let { list ->
            try {
                val array = JSONArray(preferences.getString(key, ""))
                for (i in 0 until array.length()) {
                    val item = array.optString(i)
                    list.add(item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            list
        }
    }

    fun getDataArray(key: String?, type: Type): ArrayList<HomeItemModel>? {
        return try {
            val gsonStr = preferences!!.getString(key!!, null)
            if (gsonStr != null) {
                val gson = GsonBuilder().create()
                val tempArray = ArrayList<HomeItemModel>()
                tempArray.addAll(gson.fromJson(gsonStr, type))
                tempArray
            } else null
        } catch (e: java.lang.Exception) {
            null
        }
    }

}