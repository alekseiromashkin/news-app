package com.arammeem.android.apps.newsapp.core.navigation

import androidx.fragment.app.Fragment
import org.junit.Test

class ScreenProviderTest {

    private val screenProvider = ScreenProviderImpl()

    @Test
    fun should_return_registered_fragment() {
        class FragmentA : Fragment()
        class FragmentB : Fragment()

        screenProvider.registerScreen("fragment_a", FragmentA::class)
        screenProvider.registerScreen("fragment_b", FragmentB::class)

        val fragmentA = screenProvider.getScreen("fragment_a")

        assert(fragmentA is FragmentA)
    }

    @Test
    fun should_return_null() {
        val fragmentA = screenProvider.getScreen("fragment_a")
        assert(fragmentA == null)
    }
}