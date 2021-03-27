package com.jeliliadesina.moviedir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.jeliliadesina.moviedir.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //setContentView(R.layout.activity_main)

    val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
      R.layout.activity_main)

    setSupportActionBar(binding.toolbar)

    val navController = findNavController(R.id.nav_fragment)
    val appBarConfiguration = AppBarConfiguration(navController.graph)

    binding.toolbar.setupWithNavController(navController, appBarConfiguration)
  }
}