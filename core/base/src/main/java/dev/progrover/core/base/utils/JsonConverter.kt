package dev.progrover.core.base.utils
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.ParameterizedType

class JsonConverter(
    private val moshi: Moshi,
) {

    fun <T> fromJson(json: String, clazz: Class<T>, defaultValue: T?): T? =
        try {
            moshi
                .adapter(clazz)
                .fromJson(json)
        } catch (e: JsonDataException) {
            Timber.e(e)
            defaultValue
        } catch (e: IOException) {
            Timber.e(e)
            defaultValue
        }

    fun <T> fromJson(json: String, type: ParameterizedType, defaultValue: T?): T? =
        try {
            moshi
                .adapter<T>(type)
                .fromJson(json)
        } catch (e: JsonDataException) {
            Timber.e(e)
            defaultValue
        } catch (e: IOException) {
            Timber.e(e)
            defaultValue
        }

    fun <T> toJson(value: T, clazz: Class<T>): String =
        moshi.adapter(clazz).toJson(value)

    fun <T> toJson(value: T, type: ParameterizedType): String =
        moshi.adapter<T>(type).toJson(value)
}