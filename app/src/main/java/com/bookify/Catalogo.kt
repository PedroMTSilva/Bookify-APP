package com.bookify

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bookify.ui.BookFragment
import com.bookify.ui.CatalogFragment
import com.bookify.ui.MaisFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Catalogo : AppCompatActivity() {

    private lateinit var navview:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navview = findViewById(R.id.catalogNav)

        replace(CatalogFragment())

        navview.setOnItemSelectedListener {
            when(it.itemId){
                R.id.catalog -> {
                    replace(CatalogFragment())
                }
                R.id.userAdvertisements -> {
                    replace(BookFragment())
                }
                R.id.addAdvertisement -> {
                    replace(MaisFragment())
                }
            }
            true
        }
    }

    private fun replace(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navhost, fragment)
        fragmentTransaction.commit()
    }
}