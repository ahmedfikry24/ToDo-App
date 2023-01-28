package com.example.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapp.fragments.BottomSheet
import com.example.todoapp.fragments.ListTodoFragment
import com.example.todoapp.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var addButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        initListeners()
        replaceFragments(ListTodoFragment())

    }

    fun init() {
        bottomNavigation = findViewById(R.id.main_botton_navigation)
        addButton = findViewById(R.id.floating_button)
    }

    fun replaceFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .commit()
    }

    fun initListeners() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.listIcon -> {
                    replaceFragments(ListTodoFragment())
                }
                else -> {
                    replaceFragments(SettingsFragment())
                }
            }
            return@setOnItemSelectedListener true
        }

        addButton.setOnClickListener {
            val bottomSheet = BottomSheet().show(supportFragmentManager, "")
        }
    }


}