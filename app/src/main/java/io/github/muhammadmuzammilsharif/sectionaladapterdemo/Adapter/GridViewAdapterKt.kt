package io.github.muhammadmuzammilsharif.sectionaladapterdemo.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.muhammadmuzammilsharif.sectionaladapter.SectionalGridAdapter
import io.github.muhammadmuzammilsharif.sectionaladapterdemo.Model.DummyData
import io.github.muhammadmuzammilsharif.sectionaladapterdemo.R
import io.github.muhammadmuzammilsharif.utils.convertDpToPixel

/*
 * Created by M_Muzammil Sharif on 21-May-18.
 */
class GridViewAdapterKt : SectionalGridAdapter<String, DummyData>() {
    override fun getColumnCount(): Short {
        return 3
    }

    override fun getHorizontalSpacing(context: Context): Int {
        return convertDpToPixel(5f, context)
    }

    override fun getVerticalSpacing(context: Context): Int {
        return convertDpToPixel(5f, context)
    }

    override fun getView(obj: DummyData, context: Context): View {
        val v = LayoutInflater.from(context).inflate(R.layout.grid_item, null, false)
        Picasso.with(context).load("file:///android_asset/imgs/" + obj.img).placeholder(R.drawable.placeholder).into(v.findViewById<View>(R.id.img) as ImageView)
        (v.findViewById<View>(R.id.txt) as TextView).text = obj.name
        return v
    }

    override fun getSingleViewHeight(context: Context): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    override fun getHeaderViewHolder(context: Context, parent: ViewGroup): RecyclerView.ViewHolder {
        return HeaderVH(LayoutInflater.from(context).inflate(R.layout.header_vh, parent, false))
    }

    override fun setDataToHeader(holder: RecyclerView.ViewHolder, sectionHeader: String) {
        (holder as HeaderVH).headerTitle.text = sectionHeader
    }

    internal inner class HeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var headerTitle: TextView

        init {
            headerTitle = itemView.findViewById(R.id.header_title)
        }
    }

    override fun compareSectionItems(a: DummyData, b: DummyData): Int {
        if (a.id > b.id) {
            return 1
        } else if (b.id > a.id) {
            return -1
        }
        return 0
    }
}
