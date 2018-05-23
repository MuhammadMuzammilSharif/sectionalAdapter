package io.github.muhammadmuzammilsharif.sectionaladapter

import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.github.muhammadmuzammilsharif.interfaces.SectionalUniqueObject
import java.util.*
import kotlin.collections.ArrayList

/*
 * Created by M_Muzammil Sharif on 07-May-18.
 */

public abstract class SectionalRecyclerViewAdapter<K, T : SectionalUniqueObject<K>> : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var dataSet: HashMap<K, SortedMap<Any, T>>? = null
    private var keys: List<K>? = null

    constructor() {}

    constructor(data: List<T>) {
        setDataSet(data)
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            onCreateHeaderViewHolder(parent)
        } else {
            onCreateSectionItemViewHolder(parent)
        }
    }

    final override fun getItemCount(): Int {
        if (dataSet == null || dataSet!!.isEmpty() || keys == null || keys!!.isEmpty()) {
            return 0
        } else {
            var size = 0
            for (k: K in keys!!) {
                size += dataSet!![k]!!.size
            }
            size += keys!!.size
            return size
        }
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            onBindHeaderViewHolder(holder, getHeaderObj(position))
        } else {
            onBindSectionItemViewHolder(holder, getItem(position))
        }
    }

    private fun getHeaderObj(position: Int): K {
        var size = 1
        for (key: K in keys!!) {
            if (position == (size - 1)) {
                return key
            }
            size += dataSet!![key]!!.size
            ++size
        }
        return null!!
    }

    private fun getItem(position: Int): T {
        var size: Int = 1
        var pos = 0
        var obj: T? = null
        for (key: K in keys!!) {
            if (position >= size && position < (size + dataSet!!.get(key)!!.size)) {
                pos = position - size
                var data = ArrayList(dataSet!![key]!!.values)
                obj = data[pos]
                break
            }
            size += dataSet!!.get(key)!!.size
            ++size
        }
        return obj!!
    }

    final override fun getItemViewType(position: Int): Int {
        var size: Int = 1
        for (key: K in keys!!) {
            if (position == (size - 1)) {
                return 0
            }
            size += dataSet!!.get(key)!!.size
            ++size
        }
        return 1
    }

    public fun addToData(@NonNull obj: T?) {
        if (dataSet == null) {
            dataSet = HashMap()
        }
        if (obj == null) {
            return
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

    protected open fun compareSections(o1: K, o2: K): Int {
        return o1.toString().compareTo(o2.toString(), ignoreCase = true)
    }

    protected open fun compareSectionItems(a: T, b: T): Int {
        return a.getUniqueKey().toString().compareTo(b.getUniqueKey().toString(), ignoreCase = true)
    }

    private fun getSectionalDataSort_comparator(key: K): Comparator<Any> {
        return Comparator { a, b -> compareSectionItems(dataSet!![key]!![a]!!, dataSet!![key]!![b]!!) }
    }

    public final fun removeFromData(obj: T) {
        if (dataSet != null && !dataSet!!.isEmpty()) {
            dataSet!![obj.getSection()]!!.remove(obj.getUniqueKey())
            notifyDataSetChanged()
        }
    }

    abstract fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, obj: K)

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    abstract fun onBindSectionItemViewHolder(holder: RecyclerView.ViewHolder, obj: T)

    abstract fun onCreateSectionItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
}