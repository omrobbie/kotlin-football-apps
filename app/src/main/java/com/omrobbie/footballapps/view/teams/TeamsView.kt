package com.omrobbie.footballapps.view.teams

import com.omrobbie.footballapps.model.LeagueResponse
import com.omrobbie.footballapps.model.TeamsItem

interface TeamsView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showTeamList(data: MutableList<TeamsItem>)
}
