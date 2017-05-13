package com.example.no_clay.toolsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.no_clay.toolsapp.Activitys.AnalysisActivity;
import com.example.no_clay.toolsapp.Activitys.FirstAndFollowActivity;
import com.example.no_clay.toolsapp.Activitys.GrammaticalAnalysisActivity;
import com.example.no_clay.toolsapp.Adapters.HomeMenuAdapter;
import com.example.no_clay.toolsapp.Utils.MyRecyclerViewDivider;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements HomeMenuAdapter.OnItemClickListener {


    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;
    private LinearLayout mActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ButterKnife.bind(this);
        initToolBar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        HomeMenuAdapter adapter = new HomeMenuAdapter(this);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MyRecyclerViewDivider(this));
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("工具箱");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help: {
                break;
            }
            case R.id.set: {
                break;
            }
        }
        return true;
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0: {
                Intent intent = new Intent(this, AnalysisActivity.class);
                startActivity(intent);
                break;
            }
            case 1:{
                startActivity(new Intent(this, FirstAndFollowActivity.class));
                break;
            }
            case 2:{
                startActivity(new Intent(this, GrammaticalAnalysisActivity.class));
                break;
            }
            default:
                Toast.makeText(this, "未开发", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);

    }

}
