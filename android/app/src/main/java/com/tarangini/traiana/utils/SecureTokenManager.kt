package com.tarangini.traiana.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecureTokenManager{
  private const val PREFS_NAME = "secure_auth_prefs"
  private const val KEY_TOKEN = "auth_token"

  private fun getPrefs(context : Context) : EncryptedSharedPreferences {
    val masterKey = MasterKey
      .Builder(context)
      .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
      .build()

    return EncryptedSharedPreferences.create(
      context,
      PREFS_NAME,
      masterKey,
      EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
      EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    ) as EncryptedSharedPreferences
  }

  fun saveToken(context: Context , token : String){
    getPrefs(context).edit().putString(KEY_TOKEN,token).apply()
  }

  fun getToken(context: Context) : String? {
    return getPrefs(context).getString(KEY_TOKEN,null)
  }

  fun clearToken(context: Context){
    getPrefs(context).edit().remove(KEY_TOKEN).apply()
  }
}