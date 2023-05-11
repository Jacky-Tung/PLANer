package com.example.planer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity implements MyAdapterListener{

    MyAdapter myAdapter;
    Realm realm;
    RecyclerView recyclerView;
    RealmResults<Goal> goalList;
    List<RecyclerView.ViewHolder> viewHolderList = new ArrayList<>();

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
//        Reset Database if we modify instance variables for Goal class
        RealmConfiguration config = Realm.getDefaultConfiguration();
        Realm.deleteRealm(config);
        realm = Realm.getDefaultInstance();

        goalList = realm.where(Goal.class).findAll().sort("createdAt", Sort.DESCENDING);

        recyclerView = findViewById(R.id.goals_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(getApplicationContext(), goalList, this);
        myAdapter.listener = this;
        recyclerView.setAdapter(myAdapter);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                viewHolderList.add(viewHolder);
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                viewHolderList.remove(viewHolder);
            }
        });

        TextView goalCount = findViewById(R.id.goal_count);
        goalCount.setText("Number of goals: " + myAdapter.getItemCount());

        goalList.addChangeListener(new RealmChangeListener<RealmResults<Goal>>() {
            @Override
            public void onChange(RealmResults<Goal> goals) {
                myAdapter.notifyDataSetChanged();
                goalCount.setText("Number of goals: " + myAdapter.getItemCount());
                updateProgressBar();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // checks for overdue for each goal while MainActivity is in resume state
        myAdapter.updateOverdueStatus();
        updateProgressBar();
    }

    public void updateProgressBar(){
        if(myAdapter.getItemCount() != 0) {
            int i = 0;
            for (RecyclerView.ViewHolder viewHolder : viewHolderList) {
                ProgressBar progressBar = viewHolder.itemView.findViewById(R.id.progressBar);
                Goal goal = goalList.get(i);
                progressBar.setMax(goal.getGoalsCounter());
                progressBar.setProgress(goal.getGoalsCompletedCounter());
                if (i < myAdapter.getItemCount() - 1) i++;
            }
        }
    }

    @Override
    public void onInputSaved() {
        updateProgressBar();
    }
}