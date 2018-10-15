package com.omrobbie.footballapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamsItem(val strTeamBadge: String?,
                     val strTeam: String?,
                     val intFormedYear: String?,
                     val strStadium: String?,
                     val strDescriptionEN: String?) : Parcelable
