package io.github.muhammadmuzammilsharif.sectionaladapter

/*
    Copyright (C) 2018 Muhammad Muzammil Sharif

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import android.content.Context
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import io.github.muhammadmuzammilsharif.interfaces.SectionalUniqueObject
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/*
 * Created by M_Muzammil Sharif on 22-Apr-18.
 */
abstract class SectionalGridAdapter<K, T : SectionalUniqueObject<K>> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    constructor() {}

    constructor(data: List<T>) {
        setDataSet(data)
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            setDataToHeader(holder, getHeader(position))
        } else {
            loadSection(holder, getData(position))
        }
    }

    private fun getHeader(position: Int): K {
        var size = 1
        for (k: K in keys!!) {
            if (position == (size - 1)) {
                return k
            }
            var temp = dataSet!!.get(k)!!.size
            temp = (temp / getColumn_Count() + (if (temp % getColumn_Count() > 0) {
                1
            } else {
                0
            }))
            size += (temp + 1)
        }
        return null!!
    }

    private fun getData(position: Int): List<T>? {
        var size = 1
        var key: K? = null
        var pos = 0
        for (k: K in keys!!) {
            var temp = dataSet!!.get(k)!!.size
            temp = (temp / getColumn_Count() + (if (temp % getColumn_Count() > 0) {
                1
            } else {
                0
            }))
            if (position > size + temp) {
                size += (temp + 1)
                continue
            } else {
                key = k
                pos = position - size
                break
            }
        }
        val data = ArrayList<T>(dataSet!!.get(key)!!.values)
        var i = 0
        val dataR = ArrayList<T>()
        for (k in pos * getColumn_Count() until data.size) {
            if (i < getColumn_Count()) {
                dataR.add(data.get(k))
            } else {
                data.clear()
                return dataR
            }
            ++i
        }
        data.clear()
        return dataR
    }

    private var dataSet: HashMap<K, SortedMap<Any, T>>? = null
    private var keys: List<K>? = null

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val l = LinearLayout(parent.context)
            l.orientation = LinearLayout.HORIZONTAL
            l.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
            return SectionalViewHolder(l)
        } else {
            return getHeaderViewHolder(parent.context, parent)
        }
    }

    private fun loadSection(holder: RecyclerView.ViewHolder, data: List<T>?) {
        (holder.itemView as LinearLayout).removeAllViews()

        if (data == null || data.isEmpty()) {
            return
        }
        holder.itemView.setPadding(0, 0, 0, getVerticalSpacing(holder.itemView.context))
        for (i in 0 until getColumn_Count()) {
            val params = LinearLayout.LayoutParams(0, getSingleViewHeight(holder.itemView.context), 1f)
            if (i < getColumn_Count() - 1) {
                params.rightMargin = getHorizontalSpacing(holder.itemView.context)
            }
            if (i < data.size) {
                (holder.itemView as LinearLayout).addView(getView(data[i], holder.itemView.context), params)
            } else {
                val v = View(holder.itemView.context)
                v.layoutParams = params
                v.visibility = View.INVISIBLE
                (holder.itemView as LinearLayout).addView(v)
            }
        }
    }

    final override fun getItemCount(): Int {
        if (keys == null) {
            return 0
        } else {
            var size = 0
            for (k: K in keys!!) {
                var temp = dataSet!!.get(k)!!.size
                temp = (temp / getColumn_Count() + (if (temp % getColumn_Count() > 0) {
                    1
                } else {
                    0
                }))
                size += temp
            }
            size += keys!!.size
            return size
        }
    }

    final override fun getItemViewType(position: Int): Int {
        var size = 1
        for (k: K in keys!!) {
            if (position == (size - 1)) {
                return 0
            }
            var temp = dataSet!!.get(k)!!.size
            temp = (temp / getColumn_Count() + (if (temp % getColumn_Count() > 0) {
                1
            } else {
                0
            }))
            size += (temp + 1)
        }
        return 1
    }

    private fun getColumn_Count(): Short {
        if (getColumnCount() < 2) {
            return 2
        } else {
            return getColumnCount()
        }
    }

    /**@return Short of range starting from 2 to on words*/
    protected abstract fun getColumnCount(): Short

    protected abstract fun getHorizontalSpacing(context: Context): Int

    protected abstract fun getVerticalSpacing(context: Context): Int

    protected abstract fun getView(obj: T, context: Context): View

    protected abstract fun getSingleViewHeight(context: Context): Int

    protected open fun compareSections(o1: K, o2: K): Int {
        return o1.toString().compareTo(o2.toString(), ignoreCase = true)
    }

    protected open fun compareSectionItems(o1: T, o2: T): Int {
        return o1.getUniqueKey().toString().compareTo(o2.getUniqueKey().toString(), ignoreCase = true)
    }

    private fun getSectionalDataSort_comparator(key: K): Comparator<Any> {
        return Comparator { a, b -> compareSectionItems(dataSet!![key]!![a]!!, dataSet!![key]!![b]!!) }
    }

    @NonNull
    protected abstract fun getHeaderViewHolder(context: Context, parent: ViewGroup): RecyclerView.ViewHolder

    protected abstract fun setDataToHeader(holder: RecyclerView.ViewHolder, sectionHeader: K)

    public fun addToData(@NonNull obj: T?) {
        if (dataSet == null) {
            dataSet = HashMap()
        }
        if (obj == null) {
            return
        }
        if (obj.getSection() == null || obj.getUniqueKey() == null) {
            throw Exception(obj.javaClass.name + " must return somthing in \"getSection()\" and \"getUniqueKey()\" method.")
        }
        if (dataSet!![obj.getSection()] == null) {
            dataSet!![obj.getSection()] = TreeMap()
        }
        dataSet!![obj.getSection()]!![obj.getUniqueKey()] = obj
        keys = ArrayList(dataSet!!.keys)
        if (keys!!.size > 1) {
            Collections.sort(keys!!, Comparator { a, b -> compareSections(a, b) })
        }
        if (dataSet!![obj.getSection()] != null && dataSet!![obj.getSection()]!!.size > 1) {
            dataSet!![obj.getSection()] = dataSet!![obj.getSection()]!!.toSortedMap(getSectionalDataSort_comparator(obj.getSection()))
        }
        notifyDataSetChanged()
    }

    public fun setDataSet(data: List<T>) {
        this.dataSet = null
        addToData(data)
    }

    public fun addToData(@NonNull data: List<T>) {
        if (dataSet == null) {
            dataSet = HashMap()
        }
        if (data == null || data.isEmpty()) {
            return
        }
        for (t: T in data) {
            if (t.getSection() == null || t.getUniqueKey() == null) {
                throw Exception(t.javaClass.name + " must return somthing in \"getSection()\" and \"getUniqueKey()\" method.")
            }
            if (dataSet!![t.getSection()] == null) {
                dataSet!![t.getSection()] = TreeMap()
            }
            dataSet!![t.getSection()]!![t.getUniqueKey()] = t
        }
        keys = ArrayList(dataSet!!.keys)
        if (keys!!.size > 1) {
            Collections.sort(keys!!, Comparator { a, b -> compareSections(a, b) })
        }
        for (k: K in keys!!) {
            if (dataSet!![k] != null && dataSet!![k]!!.size > 1) {
                dataSet!![k] = dataSet!![k]!!.toSortedMap(getSectionalDataSort_comparator(k))
            }
        }
        notifyDataSetChanged()
    }

    internal inner class SectionalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    public final fun removeFromData(obj: T) {
        if (dataSet != null && !dataSet!!.isEmpty()) {
            dataSet!![obj.getSection()]!!.remove(obj.getUniqueKey())
            notifyDataSetChanged()
        }
    }

    public final fun clearData() {
        dataSet = HashMap()
    }
}
