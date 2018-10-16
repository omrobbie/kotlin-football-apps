package com.omrobbie.footballapps.view.matchesDetail

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView

import com.google.gson.Gson

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.R.id.mn_favorites
import com.omrobbie.footballapps.helper.FavoritedEventsDb
import com.omrobbie.footballapps.model.EventsItem
import com.omrobbie.footballapps.model.TeamsItem
import com.omrobbie.footballapps.network.ApiRepository
import com.omrobbie.footballapps.utils.*

import com.squareup.picasso.Picasso

import org.jetbrains.anko.*

class MatchesDetailActivity : AppCompatActivity(), MatchesDetailView {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        private const val ID_HOME_BADGE = 0
        private const val ID_AWAY_BADGE = 1

        fun start(context: Context?, event: EventsItem) {
            context?.startActivity<MatchesDetailActivity>(EXTRA_PARAM to event)
        }
    }

    private lateinit var presenter: DetailPresenter

    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView

    private lateinit var event: EventsItem

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false

    private val favoritedEventsDb = FavoritedEventsDb()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        event = intent.getParcelableExtra(EXTRA_PARAM)

        setupLayout(event)
        setupEnv(event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorites, menu)
        menuFavorites = menu
        setFavorite()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }

            mn_favorites -> {
                if (isFavorite) {
                    favoritedEventsDb.removeFavorites(ctx, event)
                } else {
                    favoritedEventsDb.addFavorites(ctx, event)
                }

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBar.visible()
        scrollView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        scrollView.visible()
    }

    override fun showTeamDetail(dataHomeTeam: MutableList<TeamsItem>, dataAwayTeam: MutableList<TeamsItem>) {
        val imgHomeBadge = find<ImageView>(ID_HOME_BADGE)
        val imgAwayBadge = find<ImageView>(ID_AWAY_BADGE)

        Picasso.get()
                .load(dataHomeTeam[0].strTeamBadge)
                .into(imgHomeBadge)

        Picasso.get()
                .load(dataAwayTeam[0].strTeamBadge)
                .into(imgAwayBadge)
    }

    private fun setupLayout(data: EventsItem) {
        relativeLayout {
            scrollView = scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    // date
                    textCenter(DateTime.getDateFormat(data.dateEvent))
                    textCenter(DateTime.getTimeFormat(data.strTime))

                    // score
                    linearLayout {
                        gravity = Gravity.CENTER

                        textTitle(data.intHomeScore ?: "-")
                        textTitle(context.getString(R.string.title_vs))
                        textTitle(data.intAwayScore ?: "-")
                    }

                    // team
                    linearLayout {
                        layoutTeamBadge(ID_HOME_BADGE, data.strHomeTeam, data.strHomeFormation)
                                .lparams(matchParent, wrapContent, 1f)

                        layoutTeamBadge(ID_AWAY_BADGE, data.strAwayTeam, data.strAwayFormation)
                                .lparams(matchParent, wrapContent, 1f)
                    }

                    line()

                    layoutDetailItem("Goals", data.strHomeGoalDetails, data.strAwayGoalDetails)
                    layoutDetailItem("Shots", data.intHomeShots, data.intAwayShots)

                    line()

                    // lineups
                    textSubTitle("Lineups")

                    layoutDetailItem("Goal Keeper", data.strHomeLineupGoalkeeper, data.strAwayLineupGoalkeeper)
                    layoutDetailItem("Defense", data.strHomeLineupDefense, data.strAwayLineupDefense)
                    layoutDetailItem("Midfield", data.strHomeLineupMidfield, data.strAwayLineupMidfield)
                    layoutDetailItem("Forward", data.strHomeLineupForward, data.strAwayLineupForward)
                    layoutDetailItem("Substitutes", data.strHomeLineupSubstitutes, data.strAwayLineupSubstitutes)
                }
            }

            progressBar = progressBar().lparams {
                centerInParent()
            }
        }
    }

    private fun setupEnv(data: EventsItem) {
        presenter = DetailPresenter(this, ApiRepository(), Gson())
        isFavorite = favoritedEventsDb.isFavorite(ctx, event)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"

        presenter.getTeamDetail(data.idHomeTeam.toString(), data.idAwayTeam.toString())
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_full)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border)
        }
    }
}
