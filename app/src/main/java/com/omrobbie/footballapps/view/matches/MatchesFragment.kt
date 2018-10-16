package com.omrobbie.footballapps.view.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.ViewPagerAdapter
import com.omrobbie.footballapps.utils.TypeMatches
import com.omrobbie.footballapps.view.matchesSearch.MatchesSearchActivity

import kotlinx.android.synthetic.main.fragment_matches.*

import org.jetbrains.anko.startActivity

class MatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.mn_search -> {
                context?.startActivity<MatchesSearchActivity>()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEnv() {
        setHasOptionsMenu(true)

        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)

            view_pager.adapter = ViewPagerAdapter(supportFragmentManager,
                    mapOf(
                            getString(R.string.title_next).capitalize() to MatchesEventsFragment.newInstance(TypeMatches.NEXT),
                            getString(R.string.title_last).capitalize() to MatchesEventsFragment.newInstance(TypeMatches.LAST)
                    )
            )
            tab_layout.setupWithViewPager(view_pager)
        }
    }
}
