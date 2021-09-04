package com.twitter.challenge.view.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.twitter.challenge.R
import com.twitter.challenge.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    @Inject
    lateinit var factory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.fragmentFactory = factory
        addLoadingFragment()

        val viewModel: WeatherViewModel by viewModels()
        viewModel.loadCurrentWeatherConditions()

        viewModel.loadingComplete.observe(this, Observer {
            Log.d(TAG, "onCreate: loading complete observed : $it")
            addWeatherFragment()
        })
    }

    private fun addLoadingFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, LoadingFragment::class.java, null)
                .commit()
    }

    private fun addWeatherFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, WeatherFragment::class.java, null)
                .commit()
    }
}