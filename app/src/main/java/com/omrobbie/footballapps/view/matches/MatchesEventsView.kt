package com.omrobbie.footballapps.view.matches

import com.omrobbie.footballapps.model.EventsItem
import com.omrobbie.footballapps.model.LeagueResponse

interface MatchesEventsView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showEventList(data: MutableList<EventsItem>)
}
