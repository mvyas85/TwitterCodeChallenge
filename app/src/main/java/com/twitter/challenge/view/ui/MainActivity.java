package com.twitter.challenge.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.twitter.challenge.R;
import com.twitter.challenge.databinding.ActivityMainBinding;
import com.twitter.challenge.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();

    private ActivityMainBinding binding;
    private WeatherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        viewModel.loadCurrentWeatherConditions();

        binding.setViewModel(viewModel);
        binding.setMainActivity(this);
        binding.setLifecycleOwner(this);
    }

    public void onClick(View v) {
        Log.d(TAG, "onClick: v = " + v.getId());
        if (v.getId() == R.id.deviation_btn) {
            viewModel.loadFutureWeatherConditions();
        }
    }
}
