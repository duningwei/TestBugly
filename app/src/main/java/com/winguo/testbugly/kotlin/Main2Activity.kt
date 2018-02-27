package com.winguo.testbugly.kotlin

import android.app.Activity
import android.os.Bundle

import com.winguo.testbugly.R

/**
 * kotlin
 */
class Main2Activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
