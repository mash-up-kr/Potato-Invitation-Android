package com.mashup.patatoinvitation.util

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtils {
    private val TAG = PreferenceUtils::class.java.simpleName

    const val PREFERENCES_NAME = "preference_util"

    private const val DEFAULT_STRING = ""
    private const val DEFAULT_BOOLEAN = false
    private const val DEFAULT_BOOLEAN_TRUE = true
    private const val DEFAULT_INT = 0
    private const val DEFAULT_LONG = 0L
    private const val DEFAULT_FLOAT = 0f

    /**
     * Preferences를 얻어온다.
     */
    private fun getPreferences(context: Context?): SharedPreferences? {
        return context?.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Preferences 초기화.
     */
    fun clearPreferences(context: Context?) {
        val prefs = getPreferences(context)

        if (null != prefs) {
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
        }
    }

    /**
     * String 저장
     */
    fun setString(context: Context?, key: String?, value: String?) {
        val prefs = getPreferences(context)
        if (null != prefs) {
            val editor = prefs.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }

    /**
     * boolean 저장
     */
    fun setBoolean(context: Context?, key: String?, value: Boolean) {
        val prefs = getPreferences(context)
        if (null != prefs) {
            val editor = prefs.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    /**
     * int 저장
     */
    fun setInt(context: Context?, key: String?, value: Int) {
        val prefs = getPreferences(context)
        if (null != prefs) {
            val editor = prefs.edit()
            editor.putInt(key, value)
            editor.apply()
        }
    }

    /**
     * long 저장
     */
    fun setLong(context: Context?, key: String?, value: Long) {
        val prefs = getPreferences(context)
        if (null != prefs) {
            val editor = prefs.edit()
            editor.putLong(key, value)
            editor.apply()
        }
    }

    /**
     * float 저장
     */
    fun setFloat(context: Context?, key: String?, value: Float) {
        val prefs = getPreferences(context)
        if (null != prefs) {
            val editor = prefs.edit()
            editor.putFloat(key, value)
            editor.apply()
        }
    }

    /**
     * String 가져오기
     */
    fun getString(context: Context?, key: String?, defaultValue: String): String {
        val prefs = getPreferences(context)
        return if (null != prefs) {
            prefs.getString(key, defaultValue) ?: defaultValue
        } else {
            defaultValue
        }
    }

    /**
     * String 가져오기
     */
    fun getString(context: Context?, key: String?): String {
        return getString(context, key, DEFAULT_STRING)
    }

    /**
     * boolean 가져오기
     */
    fun getBoolean(context: Context?, key: String?, defaultValue: Boolean): Boolean {
        val prefs = getPreferences(context)
        return prefs?.getBoolean(key, defaultValue) ?: defaultValue
    }

    /**
     * boolean 가져오기
     */
    fun getBoolean(context: Context?, key: String?): Boolean {
        return getBoolean(context, key, DEFAULT_BOOLEAN)
    }

    /**
     * boolean 가져오기
     * default true
     */
    fun getBooleanTrue(context: Context?, key: String?): Boolean {
        return getBoolean(context, key, DEFAULT_BOOLEAN_TRUE)
    }

    /**
     * int 가져오기
     */
    fun getInt(context: Context?, key: String?, defaultValue: Int): Int {
        val prefs = getPreferences(context)
        return prefs?.getInt(key, defaultValue) ?: defaultValue
    }

    /**
     * int 가져오기
     */
    fun getInt(context: Context?, key: String?): Int {
        return getInt(context, key, DEFAULT_INT)
    }

    /**
     * long 가져오기
     */
    fun getLong(context: Context?, key: String?, defaultValue: Long): Long {
        val prefs = getPreferences(context)
        return prefs?.getLong(key, defaultValue) ?: defaultValue
    }

    /**
     * long 가져오기
     */
    fun getLong(context: Context?, key: String?): Long {
        return getLong(context, key, DEFAULT_LONG)
    }

    /**
     * float 가져오기
     */
    fun getFloat(context: Context?, key: String?, defaultValue: Float): Float {
        val prefs = getPreferences(context)
        return prefs?.getFloat(key, defaultValue) ?: defaultValue
    }

    /**
     * float 가져오기
     */
    fun getFloat(context: Context?, key: String?): Float {
        return getFloat(context, key, DEFAULT_FLOAT)
    }

    /**
     * 키에 맞는 값의 존재여부를 반환한다.
     */
    fun contains(context: Context?, key: String?): Boolean {
        val preferences = getPreferences(context)
        return preferences?.contains(key) ?: false
    }

    fun remove(context: Context?, key: String?) {
        val preferences = getPreferences(context)
        if (null != preferences) {
            val editor = preferences.edit()
            editor.remove(key)
            editor.apply()
        }
    }
}