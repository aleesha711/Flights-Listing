package com.aleesha.feature_aero.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aleesha.feature_aero.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirlineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airline)
    }
}
