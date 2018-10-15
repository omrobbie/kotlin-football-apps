package com.omrobbie.footballapps.view.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*

import com.omrobbie.footballapps.R
import com.omrobbie.footballapps.utils.loadFirstText

import kotlinx.android.synthetic.main.fragment_teams.*

class TeamsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    private fun setupEnv() {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)
        }

        spinner.loadFirstText(context!!)
    }
}
