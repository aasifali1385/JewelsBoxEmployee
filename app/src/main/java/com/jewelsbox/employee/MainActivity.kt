package com.jewelsbox.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jewelsbox.employee.home.MainViewModel
import com.jewelsbox.employee.menu.MenuActivity
import com.jewelsbox.employee.retrofit.SP

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: MainViewModel
    lateinit var sp: SP
    lateinit var bottomNavigation : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        sp = SP(this)

        bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setupWithNavController(findNavController(R.id.fragment))


//        val goto = intent.getStringExtra("goto").toString()
//        if (goto == "addLead") bottomNavigation.selectedItemId = R.id.nav_addlead

        findViewById<ImageView>(R.id.menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

    }

}