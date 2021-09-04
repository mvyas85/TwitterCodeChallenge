package com.twitter.challenge.view.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject

/**
 * This factory is responsible for following Fragments in our project
 * 1. WeatherFragment
 * 2. LoadingFragment
 */
class MainFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className) {
            WeatherFragment::class.java.name -> {
                WeatherFragment()
            }

            LoadingFragment::class.java.name -> {
                LoadingFragment()
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}