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
import com.omrobbie.footballapps.adapter.MatchesAdapter
import com.omrobbie.footballapps.model.EventsItem
import com.omrobbie.footballapps.model.LeagueResponse
import com.omrobbie.footballapps.model.LeaguesItem
import com.omrobbie.footballapps.network.ApiRepository
import com.omrobbie.footballapps.utils.TypeMatches
import com.omrobbie.footballapps.utils.invisible
import com.omrobbie.footballapps.utils.loadFirstText
import com.omrobbie.footballapps.utils.visible
import com.omrobbie.footballapps.view.matchesDetail.MatchesDetailActivity

import kotlinx.android.synthetic.main.fragment_matches_events.*

import org.jetbrains.anko.bundleOf

class MatchesEventsFragment : Fragment(), MatchesEventsView {

    companion object {
        private const val TYPE_MATCHES = "TYPE_MATCHES"

        fun newInstance(fragmentType: TypeMatches): MatchesEventsFragment {
            val fragment = MatchesEventsFragment()
            fragment.arguments = bundleOf(TYPE_MATCHES to fragmentType)

            return fragment
        }
    }

    private lateinit var fragmentType: TypeMatches

    private lateinit var presenter: MatchesEventsPresenter

    private lateinit var league: LeaguesItem

    private lateinit var events: MutableList<EventsItem>
    private lateinit var listAdapter: MatchesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matches_events, container, false)
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
        spinner.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as LeaguesItem

                when (fragmentType) {
                    TypeMatches.NEXT -> presenter.getEventsNext(league.idLeague.toString())
                    TypeMatches.LAST -> presenter.getEventsLast(league.idLeague.toString())
                }
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
        fragmentType = arguments?.get(TYPE_MATCHES) as TypeMatches
        presenter = MatchesEventsPresenter(this, ApiRepository(), Gson())

        spinner.loadFirstText(context!!)

        events = mutableListOf()
        listAdapter = MatchesAdapter(events) {
            MatchesDetailActivity.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        presenter.getLeagueAll()
    }
}
