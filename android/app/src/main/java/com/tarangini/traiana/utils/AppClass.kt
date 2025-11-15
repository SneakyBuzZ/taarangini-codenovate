package com.tarangini.traiana.utils

import android.app.Application
import com.airbnb.mvrx.Mavericks

class App : Application(){
  override fun onCreate() {
    super.onCreate()
    RetrofitInstance.init(this)
    Mavericks.initialize(this)
  }
}