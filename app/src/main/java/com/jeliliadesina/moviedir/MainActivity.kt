package com.jeliliadesina.moviedir

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.jeliliadesina.moviedir.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //setContentView(R.layout.activity_main)

    val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_main)

//    setSupportActionBar(binding.toolbar)
//
//    val navController = findNavController(R.id.nav_fragment)
//    val appBarConfiguration = AppBarConfiguration(navController.graph)
//
//    binding.toolbar.setupWithNavController(navController, appBarConfiguration)
  }

  interface OnBackPressedListener {
    fun onBackPressed(): Boolean
  }

  override fun onBackPressed() {
    val fragment = this.supportFragmentManager.findFragmentById(R.id.nav_fragment) as? NavHostFragment
    val currentFragment = fragment?.childFragmentManager?.fragments?.get(0)
    if (currentFragment is OnBackPressedListener?) {
      currentFragment?.onBackPressed()?.takeIf { it }?.let {
        super.onBackPressed()
      }
    } else {
      moveTaskToBack(true)
    }
  }
}