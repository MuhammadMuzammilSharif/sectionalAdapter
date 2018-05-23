package io.github.muhammadmuzammilsharif.sectionaladapterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/*
 * Created by M_Muzammil Sharif on 15-May-18.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sgvd).setOnClickListener(this);
        findViewById(R.id.srvd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.srvd: {
                Intent i = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(i);
            }
            break;
            case R.id.sgvd: {
                Intent i = new Intent(MainActivity.this, GridViewActivity.class);
                startActivity(i);
            }
            break;
        }
    }
}
