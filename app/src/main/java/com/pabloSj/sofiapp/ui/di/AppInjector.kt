package com.pabloSj.sofiapp.ui.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.pabloSj.sofiapp.SofiApp
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {
	fun init(main: SofiApp) {
		DaggerAppComponent.builder().application(main).build().inject(main)
		main.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
			override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
				handleActivity(activity)
			}
			
			override fun onActivityStarted(activity: Activity) {}
			override fun onActivityResumed(activity: Activity) {}
			override fun onActivityPaused(activity: Activity) {}
			override fun onActivityStopped(activity: Activity) {}
			override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
			override fun onActivityDestroyed(activity: Activity) {}
		})
	}
	
	private fun handleActivity(activity: Activity) {
		if (activity is HasSupportFragmentInjector) {
			AndroidInjection.inject(activity)
		}
		if (activity is androidx.fragment.app.FragmentActivity) {
			activity.supportFragmentManager
					.registerFragmentLifecycleCallbacks(
							object : androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks() {
								override fun onFragmentCreated(fm: androidx.fragment.app.FragmentManager, f: androidx.fragment.app.Fragment, savedInstanceState: Bundle?) {
									if (f is Injectable) {
										AndroidSupportInjection.inject(f)
									}
								}
							}, true)
		}
	}
}