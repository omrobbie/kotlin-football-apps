package com.omrobbie.footballapps.view.matches

import com.omrobbie.footballapps.model.LeagueResponse

interface MatchesNextView {

    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: LeagueResponse)
}
