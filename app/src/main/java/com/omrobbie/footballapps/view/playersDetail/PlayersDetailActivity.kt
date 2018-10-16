package com.omrobbie.footballapps.view.playersDetail

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.model.PlayersItem

import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_players_detail.*

import org.jetbrains.anko.startActivity

class PlayersDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun start(context: Context?, player: PlayersItem) {
            context?.startActivity<PlayersDetailActivity>(EXTRA_PARAM to player)
        }
    }

    private lateinit var player: PlayersItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_detail)

        setupEnv()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEnv() {
        player = intent.getParcelableExtra(EXTRA_PARAM)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = player.strPlayer

        loadData()
    }

    private fun loadData() {
        Picasso.get()
                .load(player.strFanart1)
                .into(iv_fanart)

        tv_weight.text = player.strWeight
        tv_height.text = player.strHeight
        tv_position.text = player.strPosition
        tv_description.text = player.strDescriptionEN
    }
}
