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
import kotlinx.android.synthetic.main.app_list_item.view.*


class AppsDrawerAdapter(val appsList: ArrayList<AppInfo>) :
    RecyclerView.Adapter<AppsDrawerAdapter.Holder>() {



    class Holder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(appInfo: AppInfo) {
            itemView.app_icon_imageView.setImageDrawable(appInfo.icon)
            itemView.app_name_textView.text = appInfo.label.toString()
            val context = itemView.context
            itemView.setOnClickListener {
                val launchIntent = context.packageManager
                    .getLaunchIntentForPackage(appInfo.packageName.toString())
                context.startActivity(launchIntent)
                Toast.makeText(context, appInfo.label.toString(), Toast.LENGTH_LONG)
                    .show()
            }
            itemView.setOnLongClickListener {



                /*if (!providersList.isNullOrEmpty() || !servicesList.isNullOrEmpty() || !receiversList.isNullOrEmpty()) {
                    val intent = Intent(context, AppInfoActivity::class.java)
                    intent.putStringArrayListExtra("providersList", providersList)
                    intent.putStringArrayListExtra("servicesList", servicesList)
                    intent.putStringArrayListExtra("receiversList", receiversList)
                    Log.d(
                        "!@#",
                        "onBindViewHolder: ${providersList.isNullOrEmpty()} ${servicesList.isNullOrEmpty()} ${receiversList.isNullOrEmpty()}"
                    )
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "No info", Toast.LENGTH_LONG)
                        .show()
                }*/

                val intent = Intent(context, AppInfoActivity::class.java)
                intent.putExtra("packageName", appInfo.packageName.toString())
                context.startActivity(intent)
                return@setOnLongClickListener true


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.app_list_item, parent, false)

        return Holder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return appsList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(appsList[position])
    }



}