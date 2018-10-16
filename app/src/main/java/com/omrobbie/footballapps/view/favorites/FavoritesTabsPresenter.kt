package com.omrobbie.footballapps.view.favorites

import android.content.Context

import com.omrobbie.footballapps.helper.database
import com.omrobbie.footballapps.model.EventsItem
import com.omrobbie.footballapps.model.PlayersItem

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoritesTabsPresenter(private val context: Context?,
                             private val view: FavoritesTabsView) {

    fun getFavoritedEvents() {
        view.showLoading()

        val data: MutableList<EventsItem> = mutableListOf()

        context?.database?.use {
            val favorites = select(EventsItem.TABLE_EVENTS)
                    .parseList(classParser<EventsItem>())

            data.addAll(favorites)
        }

        view.hideLoading()

        if (data.size > 0) {
            view.showEventList(data)
        } else {
            view.showEmptyData()
        }
    }

    fun getFavoritedPlayers() {
        view.showLoading()

        val data: MutableList<PlayersItem> = mutableListOf()

        context?.database?.use {
            val favorites = select(PlayersItem.TABLE_PLAYERS)
                    .parseList(classParser<PlayersItem>())

            data.addAll(favorites)
        }

        view.hideLoading()

        if (data.size > 0) {
            view.showPlayerList(data)
        } else {
            view.showEmptyData()
        }
    }
}
