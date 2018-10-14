package com.omrobbie.footballapps.view.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.ViewPagerAdapter
import com.omrobbie.footballapps.utils.MatchType
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
        when (item?.itemId) {
            R.id.mn_search -> {
                context?.startActivity<MatchesSearchActivity>()

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupEnv() {
        setHasOptionsMenu(true)

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
