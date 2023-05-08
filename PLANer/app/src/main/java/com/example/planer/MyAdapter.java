package com.example.planer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.metrics.BundleSession;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    RealmResults<Goal> goalList;

    public MyAdapter(Context context, RealmResults<Goal> goalList, Activity activity) {
        this.context = context;
        this.goalList = goalList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.goal_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        holder.titleOutput.setText(goal.getTitle());
        holder.descriptionOutput.setText(goal.getDescription());
        if(goal.getDeadline() != null){
            holder.deadlineOutput.setText("Deadline: " + goal.getDeadline().toString());
        }
        else{
            holder.deadlineOutput.setText("Deadline not assigned");
        }

        if(goal.isOverdue() && goal.getDeadline() != null){
            holder.overdueOutput.setVisibility(View.VISIBLE);
        }
        else{
            holder.overdueOutput.setVisibility(View.GONE);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                PopupMenu menu = new PopupMenu(context, view);
                menu.getMenu().add("MODIFY GOAL");
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("MODIFY GOAL")) {
                            Intent intent = new Intent(context, ModifyGoalActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("description", goal.getDescription());
                            intent.putExtra("title", goal.getTitle());
                            intent.putExtra("goalsCounter", String.valueOf(goal.getGoalsCounter()));    // needed string
                            intent.putExtra("goalID", goal.getGoalID());

                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            realm.copyFromRealm(goal);
                            context.startActivity(intent);
                            realm.commitTransaction();
                        }
                        if(menuItem.getTitle().equals("DELETE")) {
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            goal.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Goal deleted", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Enter progress completed");
                builder.setMessage("Please enter your progress:");
                builder.setCancelable(false);

                final EditText goalsInput = new EditText(activity);
                builder.setView(goalsInput);

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userGoalsInput = goalsInput.getText().toString();
                        if(InputValidator.validNumberInput(userGoalsInput)) {
                            goal.updateGoalsCompletedCounter(Integer.parseInt(userGoalsInput));
                            MyProgressBar progressBar = new MyProgressBar(view);
                            progressBar.setGoalsCompletedCounter(goal.getGoalsCompletedCounter());
                            progressBar.setGoalsCounter(goal.getGoalsCounter());
                            progressBar.displayProgressBar();
//                                        goal.setProgressBar(new MyProgressBar(view));
//                                        goal.getProgressBar().setGoalsCounter(goal.getGoalsCounter());
//                                        goal.getProgressBar().setGoalsCompletedCounter(goal.getGoalsCompletedCounter());
//                                        goal.getProgressBar().displayProgressBar();
                            realm.commitTransaction();
                            Toast.makeText(context, "Progress updated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            realm.commitTransaction();
                            Toast.makeText(context, "Invalid progress", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realm.commitTransaction();
                        dialogInterface.cancel();
                    }
                });

                if (!activity.isFinishing() && !activity.isDestroyed()) {
                    builder.show();
                }
            }
        });
   }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        TextView descriptionOutput;
        TextView deadlineOutput;
        TextView overdueOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.title_output);
            descriptionOutput = itemView.findViewById(R.id.description_output);
            deadlineOutput = itemView.findViewById(R.id.deadline_output);
            overdueOutput = itemView.findViewById(R.id.overdue_output);
        }
    }

    public void updateOverdueStatus(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Date currentTime = new Date();

        for(Goal goal : goalList){
            if(goal.getDeadline() != null && currentTime.after(goal.getDeadline())){
                goal.setOverdue(true);
            }
            else{goal.setOverdue(false);}
        }

        realm.commitTransaction();

        notifyDataSetChanged();
    }
}