package com.example.abclauncher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Filter
import android.widget.Filterable

class Apps(val context: Context) : Filterable {

    private val appsList = getAllAppsList()
    private val appsListFull = ArrayList(appsList)


    private fun getAllAppsList(): ArrayList<AppInfo> {
        val appsList = ArrayList<AppInfo>()
        val pm: PackageManager = context.packageManager
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        val allApps = pm.queryIntentActivities(i, 0)
        for (ri in allApps) {
            val app = AppInfo()
            app.label = ri.loadLabel(pm)
            app.packageName = ri.activityInfo.packageName
            app.icon = ri.activityInfo.loadIcon(pm)
            appsList.add(app)
        }
        return appsList
    }

    fun getList(newText: String): ArrayList<AppInfo> {
        filter.filter(newText)
        return appsList
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<AppInfo>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(appsListFull)
                } else {
                    val searchPattern = constraint.toString().toLowerCase().trim()
                    for (item in appsListFull) {
                        if (item.label.toString().toLowerCase().contains(searchPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults();
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                appsList.clear()
                appsList.addAll(results?.values as List<AppInfo>)
            }

        }
    }


}
