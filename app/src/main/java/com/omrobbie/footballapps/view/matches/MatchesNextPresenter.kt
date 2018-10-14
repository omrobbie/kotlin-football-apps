package com.omrobbie.footballapps.view.matches

import com.google.gson.Gson
import com.omrobbie.footballapps.model.LeagueResponse
import com.omrobbie.footballapps.network.ApiRepository
import com.omrobbie.footballapps.network.TheSportsDbApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchesNextPresenter(private val view: MatchesNextView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson) {

    fun getLeagueAll() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getLeagueAll()),
                    LeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showLeagueList(data)
            }
        }
    }
}
