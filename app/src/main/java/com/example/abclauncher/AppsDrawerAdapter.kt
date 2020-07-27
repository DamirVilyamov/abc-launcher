package com.example.abclauncher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class AppsDrawerAdapter(val appsList: ArrayList<AppInfo>, val context: Context) :
    RecyclerView.Adapter<AppsDrawerAdapter.Holder>(), Filterable {
    val appsListFull = ArrayList(appsList)

    class Holder(itemView: View, var icon: ImageView, var name: TextView) :
        RecyclerView.ViewHolder(itemView)

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
        val pos: Int = position
        holder.itemView.setOnClickListener {
            val launchIntent = context.packageManager
                .getLaunchIntentForPackage(appsList[pos].packageName.toString())
            context.startActivity(launchIntent)
            Toast.makeText(it.context, appsList[pos].label.toString(), Toast.LENGTH_LONG)
                .show()

        }

        holder.itemView.setOnLongClickListener{

            val providersInfo = context.packageManager.getPackageInfo(
                appsList[pos].packageName.toString(),
                PackageManager.GET_PROVIDERS
            )
            val servicesInfo = context.packageManager.getPackageInfo(
                appsList[pos].packageName.toString(),
                PackageManager.GET_SERVICES
            )
            val receiversInfo = context.packageManager.getPackageInfo(
                appsList[pos].packageName.toString(),
                PackageManager.GET_RECEIVERS
            )

            val providersList = ArrayList<String>()
            val servicesList = ArrayList<String>()
            val receiversList = ArrayList<String>()

            if (!providersInfo.providers.isNullOrEmpty()) {
                for (provider in providersInfo.providers) {
                    providersList.add(provider.name)
                }
            }
            if (!servicesInfo.services.isNullOrEmpty()) {
                for (service in servicesInfo.services) {
                    servicesList.add(service.name)
                }
            }
            if (!receiversInfo.receivers.isNullOrEmpty()) {
                for (receiver in receiversInfo.receivers) {
                    receiversList.add(receiver.name)
                }
            }

            if (!providersList.isNullOrEmpty() || !servicesList.isNullOrEmpty() || !receiversList.isNullOrEmpty()) {
                val intent = Intent(context, AppInfoActivity::class.java)
                intent.putStringArrayListExtra("providersList", providersList)
                intent.putStringArrayListExtra("servicesList", servicesList)
                intent.putStringArrayListExtra("receiversList", receiversList)
                Log.d(
                    "!@#",
                    "onBindViewHolder: ${providersList.isNullOrEmpty()} ${servicesList.isNullOrEmpty()} ${receiversList.isNullOrEmpty()}"
                )
                context.startActivity(intent)
                return@setOnLongClickListener true
            } else {
                Toast.makeText(context, "No info", Toast.LENGTH_LONG)
                    .show()
                return@setOnLongClickListener true
            }

        }
    }

    override fun getFilter(): Filter {
        return mFilter
    }

    val mFilter: Filter = object : Filter() {
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
            notifyDataSetChanged()
        }

    }

}