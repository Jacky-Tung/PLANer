package com.example.planer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity implements MyAdapterListener{

    MyAdapter myAdapter;
    Realm realm;
    RecyclerView recyclerView;
    RealmResults<Goal> goalList;
    MaterialButton addNewGoalButton;
    TextView goalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewGoalButton = findViewById(R.id.add_new_goal_button);
        goalCount = findViewById(R.id.goal_count);
        recyclerView = findViewById(R.id.goals_recycler_view);

        getGoals();
        setMyAdapter();
        setRecyclerView();
        setGoalCount();

        addNewGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddGoalActivity.class));
            }
        });

        goalList.addChangeListener(new RealmChangeListener<RealmResults<Goal>>() {
            @Override
            public void onChange(RealmResults<Goal> goals) {
                myAdapter.notifyDataSetChanged();
                setGoalCount();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        myAdapter.updateOverdueStatus();
        myAdapter.updateCompletedStatus();
    }

    @Override
    public void onInputSaved() {
        myAdapter.updateCompletedStatus();
    }

    private void setGoalCount(){
        goalCount.setText("Number of goals: " + myAdapter.getItemCount());
    }

    private void setMyAdapter(){
        myAdapter = new MyAdapter(getApplicationContext(), goalList, this);
        myAdapter.listener = this;
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    private void getGoals(){
        Realm.init(getApplicationContext());
//        Reset Database if we modify instance variables for Goal class
//        RealmConfiguration config = Realm.getDefaultConfiguration();
//        Realm.deleteRealm(config);
        realm = Realm.getDefaultInstance();

        goalList = realm.where(Goal.class).findAll().sort("createdAt", Sort.DESCENDING);
    }
}