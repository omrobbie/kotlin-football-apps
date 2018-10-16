package com.omrobbie.footballapps.view.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.MatchesAdapter
import com.omrobbie.footballapps.model.EventsItem
import com.omrobbie.footballapps.utils.invisible
import com.omrobbie.footballapps.utils.visible
import com.omrobbie.footballapps.view.matchesDetail.MatchesDetailActivity

import kotlinx.android.synthetic.main.fragment_favorites_matches.*

class FavoritesMatchesFragment : Fragment(), FavoritesMatchesView {

    private lateinit var presenter: FavoritesMatchesPresenter

    private lateinit var events: MutableList<EventsItem>
    private lateinit var listAdapter: MatchesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    override fun onResume() {
        super.onResume()

        presenter.getFavoritesAll()
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
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        presenter = FavoritesMatchesPresenter(context,this)

        events = mutableListOf()
        listAdapter = MatchesAdapter(events) {
            MatchesDetailActivity.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        presenter.getFavoritesAll()
    }
}
