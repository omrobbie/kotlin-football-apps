package com.omrobbie.footballapps.view.teamsDetail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.ViewPagerAdapter

import kotlinx.android.synthetic.main.fragment_matches.*

class TeamsDetailActivity : AppCompatActivity() {

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_detail)

        setupEnv()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorites, menu)
        menuFavorites = menu
        setFavorite()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }

            R.id.mn_favorites -> {
                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEnv() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Team Detail"

        view_pager.adapter = ViewPagerAdapter(supportFragmentManager,
                mapOf(
                        getString(R.string.title_overview) to TeamsOverviewFragment(),
                        getString(R.string.title_players) to TeamsPlayersFragment()
                )
        )
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_full)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border)
        }
    }
}
