package com.omrobbie.footballapps.view.teams

import com.google.gson.Gson
import com.omrobbie.footballapps.model.LeagueResponse

import com.omrobbie.footballapps.model.TeamResponse
import com.omrobbie.footballapps.network.ApiRepository
import com.omrobbie.footballapps.network.TheSportsDbApi

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamsPresenter(private val view: TeamsView,
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

    fun getTeamAll(leagueName: String = "") {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getTeamAll(leagueName)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showTeamList(data.teams)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }

    fun getTeamSearch(teamName: String = "") {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getTeamSearch(teamName)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showTeamList(data.teams)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }
}
