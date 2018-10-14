package com.omrobbie.footballapps.network

import com.omrobbie.footballapps.BuildConfig

object TheSportsDbApi {

    fun getLeagueAll(): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/all_leagues.php"
    }

    fun getLeagueNext(id: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventsnextleague.php?id=${id}"
    }
}
