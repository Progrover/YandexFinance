package dev.progrover.core.base.data.storage

import android.content.SharedPreferences
import dev.progrover.core.base.model.PinCode

interface SecurePrefs {
    fun setPinCodeValue(pinCode: Int)

    fun setPinCodeMode(switchedOn: Boolean): Boolean

    fun getFullPinCodeInfo(): PinCode?

    fun createPinCode(pinCode: PinCode)

    fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)

    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)
}