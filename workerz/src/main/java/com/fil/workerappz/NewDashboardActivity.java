package com.fil.workerappz;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewDashboardActivity extends AppCompatActivity {

    @BindView(R.id.drawer_img_btn)
    ImageButton drawerImgBtn;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dashboard);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.drawer_img_btn)
    public void onClick() {
        drawerLayout.isDrawerOpen(Gravity.START);
        drawerLayout.openDrawer(Gravity.LEFT);
    }
}
