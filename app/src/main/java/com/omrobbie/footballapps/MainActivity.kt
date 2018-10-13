package com.omrobbie.footballapps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.omrobbie.footballapps.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEnv()
    }

    private fun setupEnv() {
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                bottom_navigation_view.selectedItemId -> return@setOnNavigationItemSelectedListener false

                R.id.bnv_matches -> {
                    toast("matches")
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.bnv_teams -> {
                    toast("teams")
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.bnv_favorites -> {
                    toast("favorites")
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }
}
