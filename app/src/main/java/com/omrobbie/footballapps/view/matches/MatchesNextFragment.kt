package com.omrobbie.footballapps.view.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.google.gson.Gson

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.MatchAdapter
import com.omrobbie.footballapps.model.EventsItem
import com.omrobbie.footballapps.model.LeagueResponse
import com.omrobbie.footballapps.model.LeaguesItem
import com.omrobbie.footballapps.network.ApiRepository
import com.omrobbie.footballapps.utils.invisible
import com.omrobbie.footballapps.utils.loadFirstText
import com.omrobbie.footballapps.utils.visible

import kotlinx.android.synthetic.main.fragment_matches_next.*

import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class MatchesNextFragment : Fragment(), MatchesNextView {

    private lateinit var presenter: MatchesNextPresenter

    private lateinit var league: LeaguesItem

    private lateinit var events: MutableList<EventsItem>
    private lateinit var listAdapter: MatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matches_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
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

    override fun showLeagueList(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as LeaguesItem

                presenter.getEventsNext(league.idLeague.toString())
            }
        }
    }

    override fun showEventList(data: MutableList<EventsItem>) {
        events.clear()
        events.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        presenter = MatchesNextPresenter(this, ApiRepository(), Gson())

        spinner.loadFirstText(ctx)
        presenter.getLeagueAll()

        events = mutableListOf()
        listAdapter = MatchAdapter(events) {
            toast("${it.strHomeTeam.toString()} ${getString(R.string.title_vs)} ${it.strAwayTeam.toString()}")
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(ctx)
        }
    }
}
