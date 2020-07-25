package com.example.abclauncher


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.Drawable as Drawable


class AppsDrawerActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_drawer)
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
        when (item.itemId) {
            R.id.grid_or_list_action -> {
                val drawableGrid:Drawable = resources.getDrawable(R.drawable.ic_grid_on_24, null)
                val drawableList:Drawable = resources.getDrawable(R.drawable.ic_list_on_24, null)

                if (item.icon.equals(drawableList)) {
                    item.setIcon(R.drawable.ic_grid_on_24)
                    recyclerView?.layoutManager = GridLayoutManager(this, 3)
                    recyclerView?.adapter = AppsDrawerAdapter(this)

                } else if (item.icon == drawableGrid) {
                    item.setIcon(R.drawable.ic_list_on_24)
                    recyclerView?.layoutManager = LinearLayoutManager(this)
                    recyclerView?.adapter = AppsDrawerAdapter(this)
                }
                return true
            }

            else ->
                //
                return true
        }

    }

    fun checkLayout(item: MenuItem) {
        if (item.icon.equals(R.drawable.ic_list_on_24)) {
            item.setIcon(R.drawable.ic_grid_on_24)
            return
        } else if (item.icon.equals(R.drawable.ic_grid_on_24)) {
            item.setIcon(R.drawable.ic_list_on_24)
        }

    }
}


