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
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton addNewGoalButton = findViewById(R.id.add_new_goal_button);

        addNewGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddGoalActivity.class));
            }
        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Goal> goalList = realm.where(Goal.class).findAll().sort("createdAt", Sort.DESCENDING);

        RecyclerView recyclerView = findViewById(R.id.goals_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), goalList, this);
        recyclerView.setAdapter(myAdapter);

        TextView goalCount = findViewById(R.id.goal_count);
        goalCount.setText("Number of goals: " + myAdapter.getItemCount());

        goalList.addChangeListener(new RealmChangeListener<RealmResults<Goal>>() {
            @Override
            public void onChange(RealmResults<Goal> goals) {
                myAdapter.notifyDataSetChanged();
                goalCount.setText("Number of goals: " + myAdapter.getItemCount());
            }
        });
    }
}