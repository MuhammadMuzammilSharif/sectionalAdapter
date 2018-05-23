package io.github.muhammadmuzammilsharif.sectionaladapterdemo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import io.github.muhammadmuzammilsharif.sectionaladapter.SectionalRecyclerViewAdapter;
import io.github.muhammadmuzammilsharif.sectionaladapterdemo.Model.DummyData;
import io.github.muhammadmuzammilsharif.sectionaladapterdemo.R;

/*
 * Created by M_Muzammil Sharif on 21-May-18.
 */
public class RecyclerViewAdapter extends SectionalRecyclerViewAdapter<String, DummyData> {

    @Override
    public void onBindHeaderViewHolder(@NotNull RecyclerView.ViewHolder holder, String obj) {
        ((SectionHeaderVH) holder).tvHeader.setText(obj);
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(@NotNull ViewGroup parent) {
        return new SectionHeaderVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.section_recycler_header, parent, false));
    }

    @Override
    public void onBindSectionItemViewHolder(@NotNull RecyclerView.ViewHolder holder, @NotNull DummyData obj) {
        ((SectionItemVH) holder).textView.setText(obj.getName());
        Picasso.with(holder.itemView.getContext()).load("file:///android_asset/imgs/" + obj.getImg()).placeholder(R.drawable.placeholder).into(((SectionItemVH) holder).img);
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateSectionItemViewHolder(@NotNull ViewGroup parent) {
        return new SectionItemVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_vh, parent, false));
    }

    class SectionItemVH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView textView;

        SectionItemVH(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
        }
    }

    class SectionHeaderVH extends RecyclerView.ViewHolder {
        TextView tvHeader;

        SectionHeaderVH(View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.header_title);
        }
    }

    @Override
    public int compareSectionItems(DummyData a, DummyData b) {
        if (a.getId() > b.getId()) {
            return 1;
        } else if (b.getId() > a.getId()) {
            return -1;
        }
        return 0;
    }
}