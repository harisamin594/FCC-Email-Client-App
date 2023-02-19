package com.example.fccemailclient

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fccemailclient.databinding.SecondMainActivityBinding

class SecondMainActivity : AppCompatActivity() {

    private lateinit var binding: SecondMainActivityBinding
    private lateinit var navController: NavController

    private val PREF = "pk.edu.fccollege.MyPrefs"
    private val user = ""


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        val item = menu.findItem(R.id.app_bar_search)
        val search = item.actionView as SearchView


        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false

            }

            override fun onQueryTextChange(s: String): Boolean {
                return false

            }

        })

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.write_Email -> {
                val fragment = Email()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.nav_host_frag, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                true

            }


            R.id.logoutmenu -> {
                val sharedPreferences = getSharedPreferences(PREF, MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString(user, "none")
                startActivity(Intent(this, MainActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        // Retrieve Nav Controller from the NavHostFragment
        navController = navHostFragment.navController
        //setupActionBarWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }




}
