package com.omrobbie.footballapps.view.teamsDetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.gson.Gson

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.adapter.PlayersAdapter
import com.omrobbie.footballapps.model.PlayersItem
import com.omrobbie.footballapps.network.ApiRepository
import com.omrobbie.footballapps.utils.invisible
import com.omrobbie.footballapps.utils.visible
import com.omrobbie.footballapps.view.playersDetail.PlayersDetailActivity

import kotlinx.android.synthetic.main.fragment_teams_players.*

import org.jetbrains.anko.bundleOf

class TeamsPlayersFragment : Fragment(), TeamsPlayersView {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun newInstance(args: String): TeamsPlayersFragment {
            val fragment = TeamsPlayersFragment()
            fragment.arguments = bundleOf(EXTRA_PARAM to args)

            return fragment
        }
    }

    private lateinit var presenter: TeamsPlayersPresenter

    private lateinit var players: MutableList<PlayersItem>
    private lateinit var listAdapter: PlayersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams_players, container, false)
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

    override fun showPlayerList(data: MutableList<PlayersItem>) {
        players.clear()
        players.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        presenter = TeamsPlayersPresenter(this, ApiRepository(), Gson())

        players = mutableListOf()
        listAdapter = PlayersAdapter(players) {
            PlayersDetailActivity.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        presenter.getPlayerAll(arguments?.getString(EXTRA_PARAM).toString())
    }
}
