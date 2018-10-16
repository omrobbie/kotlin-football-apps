package com.omrobbie.footballapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayersItem(
        val strCutout: String?,
        val strPlayer: String?,
        val strPosition: String?,
        val strWeight: String?,
        val strHeight: String?,
        val strDescriptionEN: String?,
        val strFanart1: String?) : Parcelable {

    companion object {
        const val TABLE_PLAYERS = "TABLE_PLAYER"
        const val ID = "ID"
        const val CUTOUT = "CUTOUT"
        const val PLAYER = "PLAYER"
        const val POSITION = "POSITION"
        const val WEIGHT = "WEIGHT"
        const val HEIGHT = "HEIGHT"
        const val DESCRIPTION = "DESCRIPTION"
        const val FAN_ART = "FAN_ART"
    }
}
