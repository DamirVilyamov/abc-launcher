package com.example.abclauncher


import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AppsDrawerActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    val APP_PREFERENCES = "my_settings"
    val APP_PREFERENCES_ICON_STATE = "IconState"
    val ICON_STATE_GRID = "GRID"
    val ICON_STATE_LIST = "LIST"

    var editor: SharedPreferences.Editor? = null
    var mSettings: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_drawer)

        mSettings =
            getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)



        editor = mSettings?.edit()
        editor?.putString(APP_PREFERENCES_ICON_STATE, ICON_STATE_LIST)
        mSettings?.registerOnSharedPreferenceChangeListener(mListener)
        editor?.apply()

        initRecyclerView()
        //checkLayout()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.apps_recycler_view)
        val adapter = AppsDrawerAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("!@#", "onOptionsItemSelected: ")
        when (item.itemId) {
            R.id.grid_or_list_action -> {
                if (mSettings!!.contains(APP_PREFERENCES_ICON_STATE)) {
                    if (ICON_STATE_LIST == mSettings!!.getString(
                            APP_PREFERENCES_ICON_STATE,
                            "NO_VALUE"
                        )
                    ) {
                        editor?.remove(APP_PREFERENCES_ICON_STATE)
                        editor?.putString(APP_PREFERENCES_ICON_STATE, ICON_STATE_GRID)
                        return true
                    } else if (ICON_STATE_GRID == mSettings!!.getString(
                            APP_PREFERENCES_ICON_STATE,
                            "NO_VALUE"
                        )
                    ) {
                        editor?.remove(APP_PREFERENCES_ICON_STATE)
                        editor?.putString(APP_PREFERENCES_ICON_STATE, ICON_STATE_LIST)
                        return true
                    } else {
                        Log.d("!@#", "onOptionsItemSelected: pref = NOVALUE")
                    }
                } else {
                    Log.d("!@#", "onOptionsItemSelected: ne nashel  pref")
                }
                return true
            }

            else -> {
                Log.d("!@#", "onOptionsItemSelected: не нашел айди айтема")
                return true
            }

        }

    }

    var mListener =
        OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                APP_PREFERENCES_ICON_STATE -> {
                    Log.d("!@#", "listener: srabotal ")
                    if (mSettings!!.contains(APP_PREFERENCES_ICON_STATE)) {
                        if (ICON_STATE_LIST == mSettings!!.getString(
                                APP_PREFERENCES_ICON_STATE,
                                "NO_VALUE"
                            )
                        ) {
                            invalidateOptionsMenu()
                            recyclerView?.layoutManager = GridLayoutManager(this, 3)
                            recyclerView?.adapter = AppsDrawerAdapter(this)
                        } else if (ICON_STATE_GRID == mSettings!!.getString(
                                APP_PREFERENCES_ICON_STATE,
                                "NO_VALUE"
                            )
                        ) {
                            invalidateOptionsMenu()
                            recyclerView?.layoutManager = LinearLayoutManager(this)
                            recyclerView?.adapter = AppsDrawerAdapter(this)
                        }
                    }
                }
            }
        }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val gridOrListItem = menu!!.findItem(R.id.grid_or_list_action)
        if (ICON_STATE_LIST == mSettings!!.getString(
                APP_PREFERENCES_ICON_STATE,
                "NO_VALUE"
            )
        ) {
            gridOrListItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_list_on_24))
        } else if (ICON_STATE_GRID == mSettings!!.getString(
                APP_PREFERENCES_ICON_STATE,
                "NO_VALUE"
            )
        ) {
            gridOrListItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_grid_on_24))
        }

        return super.onPrepareOptionsMenu(menu)
    }
}


