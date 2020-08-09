package com.elgml.masarif.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.elgml.masarif.R
import com.elgml.masarif.viewmodel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var valueViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializer()
    }

    private fun initializer() {
        bottomNavigationView = findViewById(R.id.mainBottomNavigation)
        val navController =
            Navigation.findNavController(this, R.id.navigationHostFragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        valueViewModel = HomeViewModel(application)
    }

}
