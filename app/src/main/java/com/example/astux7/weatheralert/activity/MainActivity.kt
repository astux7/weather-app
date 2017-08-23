package com.example.astux7.weatheralert.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.astux7.weatheralert.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //add menu to main activity
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
