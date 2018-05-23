package io.github.muhammadmuzammilsharif.sectionaladapterdemo.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.muhammadmuzammilsharif.sectionaladapter.SectionalRecyclerViewAdapter
import io.github.muhammadmuzammilsharif.sectionaladapterdemo.Model.DummyData
import io.github.muhammadmuzammilsharif.sectionaladapterdemo.R

/*
 * Created by M_Muzammil Sharif on 17-May-18.
 */

class RecyclerViewAdapterKt : SectionalRecyclerViewAdapter<String, DummyData> {
    constructor() : super()

    constructor(data: List<DummyData>) : super(data)

    override fun onBindSectionItemViewHolder(holder: RecyclerView.ViewHolder, obj: DummyData) {
        (holder as SectionItemVH).textView.text = obj.name
        Picasso.with(holder.itemView.context).load("file:///android_asset/imgs/" + obj.img).placeholder(R.drawable.placeholder).into(holder.img)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, obj: String) {
        (holder as SectionHeaderVH).tvHeader.text = obj
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SectionHeaderVH(LayoutInflater.from(parent.context).inflate(R.layout.section_recycler_header, parent, false))
    }

    override fun onCreateSectionItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SectionItemVH(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_vh, parent, false))
    }

    internal inner class SectionHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvHeader: TextView

        init {
            tvHeader = itemView.findViewById(R.id.header_title)
        }
    }

    internal inner class SectionItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView
        var textView: TextView

        init {
            textView = itemView.findViewById(R.id.title)
            img = itemView.findViewById(R.id.img)
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
