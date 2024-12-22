package com.aispl.registration_app.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.aispl.registration_app.R
import com.aispl.registration_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tvRegisterMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Home"

        tvRegisterMessage = findViewById(R.id.tvRegisterMessage)

        // Set up Bottom Navigation
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.registerNewMember -> {

                    tvRegisterMessage.visibility = View.GONE

                    val intent = Intent(this, MemberRegistrationActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.displayMemberList -> {

                    tvRegisterMessage.visibility = View.GONE

                    val intent = Intent(this, RegistrationDetailsActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }
}

