package com.omrobbie.footballapps.view.matchesSearch

import com.omrobbie.footballapps.model.EventsItem

interface MatchesSearchView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showEventList(data: MutableList<EventsItem>)
}
