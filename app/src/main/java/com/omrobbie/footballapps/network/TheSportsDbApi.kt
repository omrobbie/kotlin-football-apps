package com.omrobbie.footballapps.network

import com.omrobbie.footballapps.BuildConfig

object TheSportsDbApi {

    private const val url = "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}"

    fun getLeagueAll(): String {
        return "$url/all_leagues.php"
    }

    fun getEventsNext(id: String): String {
        return "$url/eventsnextleague.php?id=$id"
    }

    fun getEventsLast(id: String): String {
        return "$url/eventspastleague.php?id=$id"
    }

    fun getEventsSearch(eventName: String): String {
        return "$url/searchevents.php?e=$eventName"
    }
}
