package com.omrobbie.footballapps.view.matchesSearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView

import com.omrobbie.footballapps.R

import org.jetbrains.anko.toast

class MatchesSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches_search)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_view, menu)

        val searchMenu = menu?.findItem(R.id.mn_search_view)
        val searchView = searchMenu?.actionView as SearchView

        searchView.isIconified = false

        listenSearchView(searchView)

        return super.onCreateOptionsMenu(menu)
    }

    private fun listenSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                toast(query.toString())

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
    }
}
