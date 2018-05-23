package io.github.muhammadmuzammilsharif.sectionaladapterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.muhammadmuzammilsharif.sectionaladapterdemo.Adapter.GridViewAdapter;
import io.github.muhammadmuzammilsharif.sectionaladapterdemo.Model.DummyData;

public class GridViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(GridViewActivity.this));

        GridViewAdapter adapter = new GridViewAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setDataSet(getData());
    }

    private List<DummyData> getData() {
        List<DummyData> dummyData = new ArrayList<>();
        String[] dates = new String[]{"17-04-2018", "18-04-2018", "19-04-2018", "20-04-2018", "21-04-2018", "22-04-2018", "23-04-2018", "24-04-2018", "25-04-2018"};
        for (int i = 1; i <= 60; i++) {
            dummyData.add(new DummyData(i, i + ".jpg", "Magzine " + i, dates[i % dates.length]));
        }
        return dummyData;
    }
}
