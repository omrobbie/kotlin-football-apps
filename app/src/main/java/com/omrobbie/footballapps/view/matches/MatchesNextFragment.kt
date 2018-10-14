package com.omrobbie.footballapps.view.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.google.gson.Gson

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.model.LeagueResponse
import com.omrobbie.footballapps.model.LeaguesItem
import com.omrobbie.footballapps.network.ApiRepository
import com.omrobbie.footballapps.utils.loadFirstText
import com.omrobbie.footballapps.utils.invisible
import com.omrobbie.footballapps.utils.visible

import kotlinx.android.synthetic.main.fragment_matches_next.*

import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class MatchesNextFragment : Fragment(), MatchesNextView {

    private lateinit var presenter: MatchesNextPresenter

    private lateinit var league: LeaguesItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matches_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
    }

    override fun showLeagueList(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as LeaguesItem

                toast(league.strLeague as String)
            }
        }
    }

    private fun setupEnv() {
        presenter = MatchesNextPresenter(this, ApiRepository(), Gson())

        presenter.getLeagueAll()
        spinner.loadFirstText(ctx)
    }
}
