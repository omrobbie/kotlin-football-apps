package com.omrobbie.footballapps.view.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.ViewPagerAdapter
import com.omrobbie.footballapps.utils.TypeFavorites

import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    private fun setupEnv() {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)

            view_pager.adapter = ViewPagerAdapter(supportFragmentManager,
                    mapOf(
                            getString(R.string.title_matches).capitalize() to FavoritesTabsFragment.newInstance(TypeFavorites.MATCHES),
                            getString(R.string.title_teams).capitalize() to FavoritesTabsFragment.newInstance(TypeFavorites.TEAMS)
                    )
            )
            tab_layout.setupWithViewPager(view_pager)
        }
    }
}
