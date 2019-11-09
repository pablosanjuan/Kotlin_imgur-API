package com.pabloSj.sofiapp.ui.main

import androidx.fragment.app.FragmentActivity
import com.pabloSj.sofiapp.ui.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    internal fun provideActivity(activity: MainActivity): FragmentActivity = activity
}