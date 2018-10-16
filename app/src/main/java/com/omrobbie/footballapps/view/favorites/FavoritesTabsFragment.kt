package com.omrobbie.footballapps.view.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.MatchesAdapter
import com.omrobbie.footballapps.adapter.TeamsAdapter
import com.omrobbie.footballapps.model.EventsItem
import com.omrobbie.footballapps.model.TeamsItem
import com.omrobbie.footballapps.utils.TypeFavorites
import com.omrobbie.footballapps.utils.invisible
import com.omrobbie.footballapps.utils.visible
import com.omrobbie.footballapps.view.matchesDetail.MatchesDetailActivity
import com.omrobbie.footballapps.view.teamsDetail.TeamsDetailActivity

import kotlinx.android.synthetic.main.fragment_favorites_matches.*

import org.jetbrains.anko.bundleOf

class FavoritesTabsFragment : Fragment(), FavoritesTabsView {

    companion object {
        private const val TYPE_FAVORITES = "TYPE_FAVORITES"

        fun newInstance(fragmentType: TypeFavorites): FavoritesTabsFragment {
            val fragment = FavoritesTabsFragment()
            fragment.arguments = bundleOf(TYPE_FAVORITES to fragmentType)

            return fragment
        }
    }

    private lateinit var fragmentType: TypeFavorites

    private lateinit var presenter: FavoritesTabsPresenter

    private lateinit var events: MutableList<EventsItem>
    private lateinit var eventsAdapter: MatchesAdapter

    private lateinit var teams: MutableList<TeamsItem>
    private lateinit var teamsAdapter: TeamsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    override fun onResume() {
        super.onResume()

        if (fragmentType == TypeFavorites.MATCHES) presenter.getFavoritedEvents()
        else presenter.getFavoritedTeams()
    }

    override fun showLoading() {
        progress_bar.visible()
        recycler_view.invisible()
        tv_empty.invisible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
        recycler_view.visible()
        tv_empty.invisible()
    }

    override fun showEmptyData() {
        progress_bar.invisible()
        recycler_view.invisible()
        tv_empty.visible()
    }

    override fun showEventList(data: MutableList<EventsItem>) {
        events.clear()
        events.addAll(data)
        eventsAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    override fun showTeamList(data: MutableList<TeamsItem>) {
        teams.clear()
        teams.addAll(data)
        teamsAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        fragmentType = arguments?.get(TYPE_FAVORITES) as TypeFavorites
        presenter = FavoritesTabsPresenter(context, this)

        when (fragmentType) {
            TypeFavorites.MATCHES -> {
                events = mutableListOf()
                eventsAdapter = MatchesAdapter(events) {
                    MatchesDetailActivity.start(context, it)
                }
            }

            TypeFavorites.TEAMS -> {
                teams = mutableListOf()
                teamsAdapter = TeamsAdapter(teams) {
                    TeamsDetailActivity.start(context, it)
                }
            }
        }

        with(recycler_view) {
            adapter = if (fragmentType == TypeFavorites.MATCHES) eventsAdapter else teamsAdapter
            layoutManager = LinearLayoutManager(context)
            if (fragmentType == TypeFavorites.TEAMS) addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        if (fragmentType == TypeFavorites.MATCHES) presenter.getFavoritedEvents()
        else presenter.getFavoritedTeams()
    }
}
