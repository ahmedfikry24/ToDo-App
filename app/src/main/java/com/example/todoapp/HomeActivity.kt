package com.example.todoapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapp.fragments.AddBottomSheet
import com.example.todoapp.fragments.ListTodoFragment
import com.example.todoapp.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var addButton: FloatingActionButton
    private lateinit var titleText: TextView
    var tasksListFragment = ListTodoFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        initListeners()
        replaceFragments(tasksListFragment)

    }

    private fun init() {
        bottomNavigation = findViewById(R.id.main_botton_navigation)
        addButton = findViewById(R.id.floating_button)
        titleText = findViewById(R.id.title_text)
        titleText.text = "Tasks List"
    }

    private fun replaceFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .commit()
    }

    private fun initListeners() {
        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.listIcon -> {
                    titleText.text = "Tasks List"
                    replaceFragments(tasksListFragment)
                }
                else -> {
                    titleText.text = "Settings"
                    replaceFragments(SettingsFragment())
                }
            }
            return@setOnItemSelectedListener true
        }

        addButton.setOnClickListener {
            val addBottomSheetDialog: AddBottomSheet = AddBottomSheet()
            addBottomSheetDialog.show(supportFragmentManager, "")
            addBottomSheetDialog.DismissListener = object : AddBottomSheet.OnDismissListener {
                override fun onDismiss() {
                    tasksListFragment.getTasksByDate()
                }

            }
        }
    }


}