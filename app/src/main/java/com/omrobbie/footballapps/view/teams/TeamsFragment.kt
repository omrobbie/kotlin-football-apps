package com.omrobbie.footballapps.view.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView

import com.google.gson.Gson

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.TeamsAdapter
import com.omrobbie.footballapps.model.LeagueResponse
import com.omrobbie.footballapps.model.LeaguesItem
import com.omrobbie.footballapps.model.TeamsItem
import com.omrobbie.footballapps.network.ApiRepository
import com.omrobbie.footballapps.utils.gone
import com.omrobbie.footballapps.utils.invisible
import com.omrobbie.footballapps.utils.loadFirstText
import com.omrobbie.footballapps.utils.visible
import com.omrobbie.footballapps.view.teamsDetail.TeamsDetailActivity

import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment(), TeamsView {

    private lateinit var presenter: TeamsPresenter

    private lateinit var league: LeaguesItem

    private lateinit var teams: MutableList<TeamsItem>
    private lateinit var listAdapter: TeamsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_search_view, menu)

        val searchMenu = menu?.findItem(R.id.mn_search_view)
        val searchView = searchMenu?.actionView as SearchView

        listenSearchView(searchView)
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

                presenter.getTeamAll(league.strLeague.toString())
            }
        }
    }

    override fun showTeamList(data: MutableList<TeamsItem>) {
        teams.clear()
        teams.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        setHasOptionsMenu(true)

        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)
        }

        presenter = TeamsPresenter(this, ApiRepository(), Gson())

        spinner.loadFirstText(context!!)

        teams = mutableListOf()
        listAdapter = TeamsAdapter(teams) {
            TeamsDetailActivity.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        presenter.getLeagueAll()
    }

    private fun listenSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getTeamSearch(query.toString())

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.toString().isEmpty()) {
                    spinner_container.visible()
                    presenter.getTeamAll(spinner.selectedItem.toString())
                } else spinner_container.gone()

                return true
            }
        })
    }
}
