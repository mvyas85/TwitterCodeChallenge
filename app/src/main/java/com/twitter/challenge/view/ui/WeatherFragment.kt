package com.twitter.challenge.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.twitter.challenge.R
import com.twitter.challenge.databinding.FragmentWeatherBinding
import com.twitter.challenge.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment @Inject constructor() : Fragment(R.layout.fragment_weather), View.OnClickListener {

    private val TAG = this.javaClass.name
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.weatherFragment = this;
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        Log.d(TAG, "WeatherFragment-onCreateView")

        return binding.root
    }

    override fun onClick(v: View) {
        Log.d(TAG, "onClick: v = " + v.id)
        if (v.id == R.id.deviation_btn) {
            viewModel.loadFutureWeatherConditions()
        }
    }

}