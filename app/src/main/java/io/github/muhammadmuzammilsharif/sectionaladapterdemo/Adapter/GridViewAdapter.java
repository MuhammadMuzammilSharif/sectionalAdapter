package io.github.muhammadmuzammilsharif.sectionaladapterdemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import io.github.muhammadmuzammilsharif.sectionaladapter.SectionalGridAdapter;
import io.github.muhammadmuzammilsharif.sectionaladapterdemo.Model.DummyData;
import io.github.muhammadmuzammilsharif.sectionaladapterdemo.R;
import io.github.muhammadmuzammilsharif.utils.ParserKt;

/*
 * Created by M_Muzammil Sharif on 23-Apr-18.
 */
public class GridViewAdapter extends SectionalGridAdapter<String, DummyData> {
    @Override
    protected short getColumnCount() {
        return 3;
    }

    @Override
    protected int getHorizontalSpacing(@NotNull Context context) {
        return ParserKt.convertDpToPixel(5, context);
    }

    @Override
    protected int getVerticalSpacing(@NotNull Context context) {
        return ParserKt.convertDpToPixel(5, context);
    }

    @NotNull
    @Override
    protected View getView(@NotNull DummyData obj, @NotNull Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid_item, null, false);
        Picasso.with(context).load("file:///android_asset/imgs/" + obj.getImg()).placeholder(R.drawable.placeholder).into((ImageView) v.findViewById(R.id.img));
        ((TextView) v.findViewById(R.id.txt)).setText(obj.getName());
        return v;
    }

    @Override
    protected int getSingleViewHeight(@NotNull Context context) {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @NotNull
    @Override
    protected RecyclerView.ViewHolder getHeaderViewHolder(@NotNull Context context, @NotNull ViewGroup parent) {
        return new HeaderVH(LayoutInflater.from(context).inflate(R.layout.header_vh, parent, false));
    }

    @Override
    protected void setDataToHeader(@NotNull RecyclerView.ViewHolder holder, @NotNull String sectionHeader) {
        ((HeaderVH) holder).headerTitle.setText(sectionHeader);
    }

    class HeaderVH extends RecyclerView.ViewHolder {
        TextView headerTitle;

        HeaderVH(View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.header_title);
        }
    }

    @Override
    protected int compareSectionItems(@NotNull DummyData a, @NotNull DummyData b) {
        int a1, b1;
        a1 = a.getId();
        b1 = b.getId();
        if (a1 > b1) {
            return 1;
        } else if (b1 > a1) {
            return -1;
        }
        return 0;
    }
}
