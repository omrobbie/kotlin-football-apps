package com.omrobbie.footballapps.view.matchesSearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView

import com.google.gson.Gson

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.MatchesAdapter
import com.omrobbie.footballapps.model.EventsItem
import com.omrobbie.footballapps.network.ApiRepository
import com.omrobbie.footballapps.utils.invisible
import com.omrobbie.footballapps.utils.visible
import com.omrobbie.footballapps.view.matchesDetail.MatchesDetailActivity

import kotlinx.android.synthetic.main.activity_matches_search.*

class MatchesSearchActivity : AppCompatActivity(), MatchesSearchView {

    private lateinit var presenter: MatchesSearchPresenter

    private lateinit var events: MutableList<EventsItem>
    private lateinit var listAdapter: MatchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches_search)

        setupEnv()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_view, menu)

        val searchMenu = menu?.findItem(R.id.mn_search_view)
        val searchView = searchMenu?.actionView as SearchView

        searchView.isIconified = false

        listenSearchView(searchView)

        return super.onCreateOptionsMenu(menu)
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
        presenter = MatchesSearchPresenter(this, ApiRepository(), Gson())

        events = mutableListOf()
        listAdapter = MatchesAdapter(events) {
            MatchesDetailActivity.start(this, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
        }

        presenter.getEventsSearch()
    }

    private fun listenSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getEventsSearch(query.toString())

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.toString().isEmpty()) presenter.getEventsSearch(query.toString())

                return true
            }
        })
    }
}
