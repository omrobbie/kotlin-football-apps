package com.omrobbie.footballapps.view.playersDetail

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.model.PlayersItem

import org.jetbrains.anko.startActivity

class PlayersDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun start(context: Context?, player: PlayersItem) {
            context?.startActivity<PlayersDetailActivity>(EXTRA_PARAM to player)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_detail)

        setupEnv()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEnv() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Player Detail"
    }
}
