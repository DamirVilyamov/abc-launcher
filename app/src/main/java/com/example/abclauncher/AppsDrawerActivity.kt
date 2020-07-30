package com.example.abclauncher


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_apps_drawer.*


class AppsDrawerActivity : AppCompatActivity() {
    val TAG = "!@#"
    private var recyclerView: RecyclerView? = null
    val APP_PREFERENCES = "my_settings"
    val APP_PREFERENCES_ICON_STATE = "IconState"
    val ICON_STATE_GRID = "GRID"
    val ICON_STATE_LIST = "LIST"
    val context = this

    var editor: SharedPreferences.Editor? = null
    var mSettings: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_drawer)
        val appsList = Apps(context).appsList
        mSettings =
            getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)



        editor = mSettings?.edit()
        editor?.putString(APP_PREFERENCES_ICON_STATE, ICON_STATE_GRID)
        mSettings?.registerOnSharedPreferenceChangeListener(mListener)
        editor?.apply()

        initRecyclerView()

    }

    private fun initRecyclerView() {
        recyclerView = apps_recycler_view
        val adapter = AppsDrawerAdapter(appsList)
        val linearLayoutManager = LinearLayoutManager(this)
        apps_recycler_view.layoutManager = linearLayoutManager
        recyclerView?.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawer, menu)
        val menuItem: MenuItem? = menu?.findItem(R.id.search_action_item)

        val searchView: SearchView = menuItem?.actionView as SearchView

        searchView.queryHint = "e.g. Youtube"
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val adapter = AppsDrawerAdapter(context)

                adapter.apps.filter(newText)

                recyclerView?.adapter = adapter
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("!@#", "onOptionsItemSelected: ")
        when (item.itemId) {
            R.id.grid_or_list_action -> {
                Log.d("!@#", "onOptionsItemSelected: itemid = gridorlistAction")
                if (mSettings!!.contains(APP_PREFERENCES_ICON_STATE)) {
                    Log.d("!@#", "onOptionsItemSelected: msettings contains iconstate")
                    if (ICON_STATE_LIST == mSettings!!.getString(
                            APP_PREFERENCES_ICON_STATE,
                            "NO_VALUE"
                        )
                    ) {
                        Log.d(TAG, "onOptionsItemSelected: value = $ICON_STATE_LIST")
                        Log.d(TAG, "onOptionsItemSelected: editor null? = ${editor != null}")
                        editor = mSettings?.edit()
                        editor?.remove(APP_PREFERENCES_ICON_STATE)
                        editor?.putString(APP_PREFERENCES_ICON_STATE, ICON_STATE_GRID)
                        editor?.apply()
                        return true
                    } else if (ICON_STATE_GRID == mSettings!!.getString(
                            APP_PREFERENCES_ICON_STATE,
                            "NO_VALUE"
                        )
                    ) {
                        editor = mSettings?.edit()
                        editor?.remove(APP_PREFERENCES_ICON_STATE)
                        editor?.putString(APP_PREFERENCES_ICON_STATE, ICON_STATE_LIST)
                        editor?.apply()
                        return true
                    } else {
                        Log.d("!@#", "onOptionsItemSelected: pref = NOVALUE")
                    }
                } else {
                    Log.d("!@#", "onOptionsItemSelected: ne nashel  pref")
                }
                return true
            }

           /* R.id.search_action_item -> {

            }*/
            else -> {
                Log.d("!@#", "onOptionsItemSelected: не нашел айди айтема")
                return true
            }

        }

    }

    val mListener =
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


                        } else if (ICON_STATE_GRID == mSettings!!.getString(
                                APP_PREFERENCES_ICON_STATE,
                                "NO_VALUE"
                            )
                        ) {
                            invalidateOptionsMenu()
                            recyclerView?.layoutManager = LinearLayoutManager(this)


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
            gridOrListItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_list_on_24)
        } else if (ICON_STATE_GRID == mSettings!!.getString(
                APP_PREFERENCES_ICON_STATE,
                "NO_VALUE"
            )
        ) {
            gridOrListItem.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid_on_24)
        }

        return super.onPrepareOptionsMenu(menu)
    }


}


