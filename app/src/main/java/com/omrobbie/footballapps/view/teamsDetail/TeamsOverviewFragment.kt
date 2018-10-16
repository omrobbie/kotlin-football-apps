package com.omrobbie.footballapps.view.teamsDetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.omrobbie.footballapps.R

import kotlinx.android.synthetic.main.fragment_teams_overview.*

import org.jetbrains.anko.bundleOf

class TeamsOverviewFragment : Fragment() {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun newInstance(args: String): TeamsOverviewFragment {
            val fragment = TeamsOverviewFragment()
            fragment.arguments = bundleOf(EXTRA_PARAM to args)

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_overview.text = arguments?.getString(EXTRA_PARAM)
    }
}
