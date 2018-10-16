package com.omrobbie.footballapps.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.model.EventsItem
import com.omrobbie.footballapps.utils.DateTime

import org.jetbrains.anko.*

class MatchesAdapter(private val items: MutableList<EventsItem>,
                     private val clickListener: (EventsItem) -> Unit) : RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    companion object {
        private const val ID_DATE = 1
        private const val ID_TIME = 2
        private const val ID_HOME_TEAM = 3
        private const val ID_HOME_SCORE = 4
        private const val ID_AWAY_TEAM = 5
        private const val ID_AWAY_SCORE = 6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val matchDate: TextView = view.findViewById(ID_DATE)
        private val matchTime: TextView = view.findViewById(ID_TIME)
        private val matchHomeTeam: TextView = view.findViewById(ID_HOME_TEAM)
        private val matchHomeScore: TextView = view.findViewById(ID_HOME_SCORE)
        private val matchAwayTeam: TextView = view.findViewById(ID_AWAY_TEAM)
        private val matchAwayScore: TextView = view.findViewById(ID_AWAY_SCORE)

        fun bind(item: EventsItem, clickListener: (EventsItem) -> Unit) {
            matchDate.text = DateTime.getDateFormat(item.dateEvent)
            matchTime.text = DateTime.getTimeFormat(item.strTime)
            matchHomeTeam.text = item.strHomeTeam ?: "[HOME TEAM]"
            matchHomeScore.text = item.intHomeScore ?: "-"
            matchAwayTeam.text = item.strAwayTeam ?: "[AWAY TEAM]"
            matchAwayScore.text = item.intAwayScore ?: "-"

            itemView.setOnClickListener { clickListener(item) }
        }
    }

    inner class ItemUI : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.VERTICAL
                    padding = dip(8)

                    textView {
                        id = ID_DATE
                        textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, wrapContent)

                    textView {
                        id = ID_TIME
                        textColor = ContextCompat.getColor(context, R.color.colorPrimary)
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, wrapContent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = ID_HOME_TEAM
                            gravity = Gravity.CENTER
                            textSize = 18f
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                id = ID_HOME_SCORE
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                            }

                            textView {
                                text = context.getString(R.string.title_vs)
                            }

                            textView {
                                id = ID_AWAY_SCORE
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                            }
                        }

                        textView {
                            id = ID_AWAY_TEAM
                            gravity = Gravity.CENTER
                            textSize = 18f
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }.lparams(matchParent, matchParent) {
                    setMargins(dip(16), dip(8), dip(16), dip(8))
                }
            }
        }
    }
}
