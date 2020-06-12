package com.mzm.sample.iconclassifier

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.list_item.view.*

class IconAdapter : BaseAdapter {

    var context: Context? = null
    var iconList = ArrayList<Icon>()

    constructor(context: Context, iconList: ArrayList<Icon>) : super() {
        this.context = context
        this.iconList = iconList
    }

    override fun getCount(): Int {
        return iconList.size
    }

    override fun getItem(position: Int): Any {
        return iconList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var itemView = inflater.inflate(R.layout.list_item, null)

        val icon = this.iconList[position]
        itemView.iconView.setImageResource(icon.iconId!!)
        itemView.predictionView.setText(icon.prediction)

        return itemView

    }

}