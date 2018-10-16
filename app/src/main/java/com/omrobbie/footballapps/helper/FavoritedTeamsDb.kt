package com.omrobbie.footballapps.helper

import android.content.Context
import android.database.sqlite.SQLiteConstraintException

import com.omrobbie.footballapps.model.TeamsItem

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class FavoritedTeamsDb {

    fun addFavorites(context: Context, data: TeamsItem) {
        try {
            context.database.use {
                insert(TeamsItem.TABLE_TEAMS,
                        TeamsItem.ID_TEAM to data.idTeam,
                        TeamsItem.TEAM_BADGE to data.strTeamBadge,
                        TeamsItem.TEAM to data.strTeam,
                        TeamsItem.FORMED_YEAR to data.intFormedYear,
                        TeamsItem.STADIUM to data.strStadium,
                        TeamsItem.DESCRIPTION to data.strDescriptionEN)
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun removeFavorites(context: Context, data: TeamsItem) {
        try {
            context.database.use {
                delete(TeamsItem.TABLE_TEAMS,
                        TeamsItem.ID_TEAM + " = {id}",
                        "id" to data.idTeam.toString())
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun isFavorite(context: Context, data: TeamsItem): Boolean {
        var bFavorite = false

        context.database.use {
            val favorites = select(TeamsItem.TABLE_TEAMS)
                    .whereArgs(TeamsItem.ID_TEAM + " = {id}",
                            "id" to data.idTeam.toString())
                    .parseList(classParser<TeamsItem>())

            bFavorite = !favorites.isEmpty()
        }

        return bFavorite
    }
}
