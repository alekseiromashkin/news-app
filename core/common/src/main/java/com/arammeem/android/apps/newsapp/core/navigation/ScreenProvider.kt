package com.arammeem.android.apps.newsapp.core.navigation

import kotlin.reflect.KClass
import androidx.fragment.app.Fragment
import javax.inject.Inject
import kotlin.reflect.full.createInstance

interface ScreenProvider {
    fun registerScreen(tag: String, fragmentClass: KClass<out Fragment>): ScreenProvider
    fun getScreen(tag: String): Fragment?
}

class ScreenProviderImpl @Inject constructor() : ScreenProvider {

    private val screens = mutableMapOf<String, KClass<out Fragment>>()

    override fun registerScreen(tag: String, fragmentClass: KClass<out Fragment>): ScreenProvider {
        screens[tag] = fragmentClass
        return this
    }

    override fun getScreen(tag: String): Fragment? {
        return screens[tag]?.createInstance()
    }
}