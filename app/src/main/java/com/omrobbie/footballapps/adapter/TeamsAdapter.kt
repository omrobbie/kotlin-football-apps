package com.omrobbie.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.omrobbie.footballapps.model.TeamsItem
import com.squareup.picasso.Picasso

import org.jetbrains.anko.*

class TeamsAdapter(private val items: MutableList<TeamsItem>,
                   private val clickListener: (TeamsItem) -> Unit) : RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    companion object {
        private const val ID_IMAGE = 1
        private const val ID_NAME = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(ItemUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val teamImage: ImageView = view.findViewById(ID_IMAGE)
        private val teamName: TextView = view.findViewById(ID_NAME)

        fun bind(item: TeamsItem, clickListener: (TeamsItem) -> Unit) {
            Picasso.get()
                    .load(item.strTeamBadge)
                    .into(teamImage)

            teamName.text = item.strTeam

            itemView.setOnClickListener { clickListener(item) }
        }
    }

    inner class ItemUI : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL

                verticalLayout {
                    orientation = LinearLayout.HORIZONTAL
                    padding = dip(16)

                    imageView {
                        id = ID_IMAGE
                    }.lparams(dip(50), dip(50))

                    textView {
                        id = ID_NAME
                    }.lparams(wrapContent, wrapContent) {
                        gravity = Gravity.CENTER_VERTICAL
                        leftMargin = dip(16)
                    }
                }
            }
        }
    }
}
