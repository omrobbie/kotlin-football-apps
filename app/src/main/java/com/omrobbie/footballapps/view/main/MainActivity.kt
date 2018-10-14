package com.omrobbie.footballapps.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.view.favorites.FavoritesFragment
import com.omrobbie.footballapps.view.matches.MatchesFragment
import com.omrobbie.footballapps.view.teams.TeamsFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEnv()
    }

    private fun setupEnv() {
        setFragment(MatchesFragment())
        listenBottomNavigationView()
    }

    private fun listenBottomNavigationView() {
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                bottom_navigation_view.selectedItemId -> return@setOnNavigationItemSelectedListener false

                R.id.bnv_matches -> {
                    setFragment(MatchesFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.bnv_teams -> {
                    setFragment(TeamsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.bnv_favorites -> {
                    setFragment(FavoritesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun setFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}
