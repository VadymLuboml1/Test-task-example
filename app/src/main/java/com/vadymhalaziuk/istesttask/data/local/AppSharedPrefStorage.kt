package com.vadymhalaziuk.istesttask.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vadymhalaziuk.istesttask.data.local.AppSharedPrefStorage.Companion.Keys.LIST
import com.vadymhalaziuk.istesttask.data.local.model.CooledDownActionLocalDto

class AppSharedPrefStorage(
    context: Context,
    private val gson: Gson
) {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFS_NAME_SPACE, Context.MODE_PRIVATE)
    }

    var cooledDownActions: List<CooledDownActionLocalDto>
        get() = sharedPreferences.getString(Keys.LIST, "[]")
            .let { gson.fromJson(it, object : TypeToken<List<CooledDownActionLocalDto>>() {}.type) }
        set(value) = sharedPreferences.edit().putString(
            LIST,
            gson.toJson(value),
        ).apply()

    private companion object {
        object Keys {
            const val LIST = "key_list_is"
        }

        const val SHARED_PREFS_NAME_SPACE = "is_text_app_namespace"
    }
}