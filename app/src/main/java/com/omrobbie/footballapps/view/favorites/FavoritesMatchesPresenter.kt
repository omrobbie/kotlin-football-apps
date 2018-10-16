package com.omrobbie.footballapps.view.favorites

import android.content.Context

import com.omrobbie.footballapps.helper.database
import com.omrobbie.footballapps.model.EventsItem

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoritesMatchesPresenter(private val context: Context?,
                                private val view: FavoritesMatchesView) {

    fun getFavoritesAll() {
        view.showLoading()

        val data: MutableList<EventsItem> = mutableListOf()

        context?.database?.use {
            val favorites = select(EventsItem.TABLE_FAVORITES)
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
}
