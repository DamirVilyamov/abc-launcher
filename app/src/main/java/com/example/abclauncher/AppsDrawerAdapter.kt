package com.example.abclauncher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class AppsDrawerAdapter(context: Context) :
    RecyclerView.Adapter<AppsDrawerAdapter.Holder>() {
    var appsList = ArrayList<AppInfo>()
    init {
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
    }

    class Holder(itemView: View, var icon: ImageView, var name: TextView) :
        RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.app_list_item, parent, false)

        return Holder(
            itemView,
            itemView.findViewById(R.id.app_icon_imageView),
            itemView.findViewById(R.id.app_name_textView)
        )
    }

    override fun getItemCount(): Int {
        return appsList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.icon.setImageDrawable(appsList[position].icon)
        holder.name.text = appsList[position].label.toString()

        holder.itemView.setOnClickListener {
            val pos: Int = position
            val context: Context = it.context
            val launchIntent = context.packageManager
                .getLaunchIntentForPackage(appsList.get(pos).packageName.toString())
            context.startActivity(launchIntent)
            Toast.makeText(it.context, appsList[pos].label.toString(), Toast.LENGTH_LONG)
                .show()
        }
    }
}