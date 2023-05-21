package com.example.viewmodelfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // create view model using delegation
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = null

        // observe ViewModel in Activity
        viewModel.counter.observe(this) {
            println("MainActivity observe counter $it")
            supportActionBar?.subtitle = "($it)"
        }


        // Material bottom navigation setup
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(findViewById(R.id.navhostfragment))
        bottomNavigationView.setupWithNavController(navController)
    }

    // region Material ActionBar options menu setup and events

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.actionClear -> { viewModel.reset(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //endregion
}