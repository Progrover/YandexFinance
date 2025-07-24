package dev.progrover.core.base.data.storage

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.squareup.moshi.Moshi
import dev.progrover.core.base.model.PinCode
import dev.progrover.core.base.utils.PIN
import timber.log.Timber

/**
 * Класс-реализация SecurePrefs. Необходим для работы с зашифрованными данными
 */
open class EncryptedSharedPrefsImpl(
    application: Application,
    prefsName: String,
    private val moshi: Moshi,
) : SecurePrefs {

    private val masterKey = MasterKey.Builder(application)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    open val prefs = EncryptedSharedPreferences.create(
        application,
        prefsName,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun setPinCodeValue(pinCode: Int) {
        try {
            val json = prefs.getString(PIN, "") ?: ""
            val currentPin = moshi
                .adapter(PinCode::class.java)
                .fromJson(json)
            prefs.edit {
                putString(
                    PIN, moshi.adapter(PinCode::class.java)
                        .toJson(currentPin!!.copy(pinCode = pinCode))
                )
            }
        } catch (e: Exception) {
            Timber.e("Secure prefs: unable to update pincode - ${e.message}")
        }
    }

    override fun setPinCodeMode(switchedOn: Boolean): Boolean =
        try {
            val json = prefs.getString(PIN, "") ?: ""
            val currentPin = moshi
                .adapter(PinCode::class.java)
                .fromJson(json)
            prefs.edit {
                putString(
                    PIN, moshi.adapter(PinCode::class.java)
                        .toJson(currentPin!!.copy(pinCodeModeOn = switchedOn))
                )
            }
            true
        } catch (e: Exception) {
            Timber.e("Secure prefs: unable to update pincode mode - ${e.message}")
            false
        }

    override fun getFullPinCodeInfo(): PinCode? =
        try {
            moshi
                .adapter(PinCode::class.java)
                .fromJson(prefs.getString(PIN, "") ?: "")
        } catch (e: Exception) {
            Timber.e("Secure prefs: unable to receive pincode - ${e.message}")
            null
        }

    override fun createPinCode(pinCode: PinCode) {
        prefs.edit {
            putString(
                PIN, moshi.adapter(PinCode::class.java)
                    .toJson(pinCode)
            )
        }
    }

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener,
    ) =
        prefs.registerOnSharedPreferenceChangeListener(listener)

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener,
    ) =
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
}