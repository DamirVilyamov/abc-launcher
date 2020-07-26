package com.example.abclauncher

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView


class AppsDrawerAdapter(val appsList:ArrayList<AppInfo>) :
    RecyclerView.Adapter<AppsDrawerAdapter.Holder>(), Filterable {
    val appsListFull = ArrayList(appsList)

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

    override fun getFilter(): Filter {
        return mFilter
    }
    val mFilter:Filter = object:Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<AppInfo>()
            if (constraint == null || constraint.length == 0){
                filteredList.addAll(appsListFull)
            } else {
                val searchPattern = constraint.toString().toLowerCase().trim()
                for (item in appsListFull){
                    if (item.label.toString().toLowerCase().contains(searchPattern)){
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
            notifyDataSetChanged()
        }

    }

}