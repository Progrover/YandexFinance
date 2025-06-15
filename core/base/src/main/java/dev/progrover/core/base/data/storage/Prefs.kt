package dev.progrover.core.base.data.storage

import android.content.SharedPreferences
import java.lang.reflect.ParameterizedType

interface Prefs {

    fun putString(name: String, value: String?, commit: Boolean = false)

    fun getString(name: String, defaultValue: String? = null): String?

    fun putInt(name: String, value: Int)

    fun getInt(name: String, defaultValue: Int = 0): Int

    fun putBool(name: String, value: Boolean)

    fun getBool(name: String, defaultValue: Boolean = false): Boolean

    fun putLong(name: String, value: Long)

    fun getLong(name: String, defaultValue: Long = 0): Long

    fun putStringSet(name: String, value: Set<String>?)

    fun getStringSet(name: String, defaultValue: Set<String>? = null): Set<String>?

    fun hasSavedParam(name: String): Boolean

    fun <T> getJson(key: String, clazz: Class<T>, defaultValue: T? = null): T?

    fun <T> getJson(key: String, type: ParameterizedType, defaultValue: T? = null): T?

    fun <T> putJson(key: String, value: T, clazz: Class<T>)

    fun <T> putJson(key: String, value: T, type: ParameterizedType)

    fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)

    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)

    fun clearParam(name: String)

    fun clearAllParams()
}