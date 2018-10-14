package com.omrobbie.footballapps.view.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.ViewPagerAdapter
import com.omrobbie.footballapps.utils.MatchType

import kotlinx.android.synthetic.main.fragment_matches.*

class MatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matches, container, false)
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
                            getString(R.string.title_next) to MatchesEventsFragment.newInstance(MatchType.NEXT),
                            getString(R.string.title_last) to MatchesEventsFragment.newInstance(MatchType.LAST)
                    )
            )
            tab_layout.setupWithViewPager(view_pager)
        }
    }
}
