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

    fun getTeamDetail(id: String): String {
        return "$url/lookupteam.php?id=$id"
    }

    fun getTeamAll(leagueName: String): String {
        return "$url/search_all_teams.php?l=$leagueName"
    }

    fun getTeamSearch(teamName: String): String {
        return "$url/searchteams.php?t=$teamName"
    }

    fun getPlayerAll(teamName: String): String {
        return "$url/searchplayers.php?t=$teamName"
    }
}
