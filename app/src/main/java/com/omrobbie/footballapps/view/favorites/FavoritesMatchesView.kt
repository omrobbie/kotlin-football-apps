package com.omrobbie.footballapps.view.favorites

import com.omrobbie.footballapps.model.EventsItem

interface FavoritesMatchesView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showEventList(data: MutableList<EventsItem>)
}
