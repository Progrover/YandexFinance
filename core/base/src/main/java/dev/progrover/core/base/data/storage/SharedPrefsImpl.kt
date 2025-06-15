package dev.progrover.core.base.data.storage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.lang.reflect.ParameterizedType

open class SharedPrefsImpl(
    application: Application,
    prefsName: String,
    private val moshi: Moshi,
) : Prefs {

    open val prefs: SharedPreferences =
        application.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    override fun putString(
        name: String,
        value: String?,
        commit: Boolean,
    ) =
        prefs.edit(commit) {
            putString(name, value)
        }

    override fun getString(name: String, defaultValue: String?): String? =
        try {
            prefs.getString(name, defaultValue)
        } catch (e: ClassCastException) {
            clearIncorrectlySavedParam(name)
            defaultValue
        }

    override fun putInt(name: String, value: Int) =
        prefs.edit {
            putInt(name, value)
        }

    override fun getInt(name: String, defaultValue: Int): Int =
        try {
            prefs.getInt(name, defaultValue)
        } catch (e: ClassCastException) {
            clearIncorrectlySavedParam(name)
            defaultValue
        }

    @Synchronized
    override fun putBool(name: String, value: Boolean) =
        prefs.edit {
            putBoolean(name, value)
        }

    override fun getBool(name: String, defaultValue: Boolean): Boolean =
        try {
            prefs.getBoolean(name, defaultValue)
        } catch (e: ClassCastException) {
            clearIncorrectlySavedParam(name)
            defaultValue
        }

    override fun putLong(name: String, value: Long) =
        prefs.edit {
            putLong(name, value)
        }

    override fun getLong(name: String, defaultValue: Long): Long =
        try {
            prefs.getLong(name, defaultValue)
        } catch (e: ClassCastException) {
            clearIncorrectlySavedParam(name)
            defaultValue
        }

    override fun putStringSet(name: String, value: Set<String>?) =
        prefs.edit {
            putStringSet(name, value)
        }

    override fun getStringSet(name: String, defaultValue: Set<String>?): Set<String>? =
        try {
            prefs.getStringSet(name, defaultValue)
        } catch (e: ClassCastException) {
            clearIncorrectlySavedParam(name)
            defaultValue
        }

    override fun hasSavedParam(name: String): Boolean =
        prefs.contains(name)

    override fun <T> getJson(key: String, clazz: Class<T>, defaultValue: T?): T? =
        try {
            val json = prefs.getString(key, "") ?: ""
            moshi
                .adapter(clazz)
                .fromJson(json)
        } catch (e: Exception) {
            Timber.e(e)
            defaultValue
        }

    override fun <T> getJson(key: String, type: ParameterizedType, defaultValue: T?): T? =
        try {
            val json = prefs.getString(key, "") ?: ""
            moshi
                .adapter<T>(type)
                .fromJson(json)
        } catch (e: Exception) {
            Timber.e(e)
            defaultValue
        }

    override fun <T> putJson(key: String, value: T, clazz: Class<T>) {
        val json = moshi.adapter(clazz).toJson(value)
        prefs.edit { putString(key, json) }
    }

    override fun <T> putJson(key: String, value: T, type: ParameterizedType) {
        val json = moshi.adapter<T>(type).toJson(value)
        prefs.edit { putString(key, json) }
    }

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener,
    ) =
        prefs.registerOnSharedPreferenceChangeListener(listener)

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener,
    ) =
        prefs.unregisterOnSharedPreferenceChangeListener(listener)

    @Synchronized
    override fun clearParam(name: String) =
        prefs.edit {
            remove(name)
        }

    override fun clearAllParams() {
        prefs.edit {
            clear()
        }
    }

    private fun clearIncorrectlySavedParam(name: String) =
        clearParam(name)
}