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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                PopupMenu menu = new PopupMenu(context, view);
                //---//
                menu.getMenu().add("EDIT");
                //---//
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //---//
                        if(menuItem.getTitle().equals("EDIT")) {
                            Intent intent = new Intent(context, AddGoalActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("description", goal.getDescription());
                            intent.putExtra("title", goal.getTitle());
                            intent.putExtra("goalsCounter", goal.getGoalsCounter());
                            context.startActivity(intent);

                            /// current functionality delete note then add new note with previous fields ///
                            /// add flag for add edit so note doesn't get deleted on click //
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            goal.deleteFromRealm();
                            realm.commitTransaction();
                        //---//
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
                builder.setTitle("Enter goals input");
                builder.setMessage("Please enter your goals input:");
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
                            goal.setGoalsInput(Integer.parseInt(userGoalsInput));
                            realm.commitTransaction();
                            Toast.makeText(context, "Goals input saved", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            realm.commitTransaction();
                            Toast.makeText(context, "Invalid goals input", Toast.LENGTH_LONG).show();
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.title_output);
            descriptionOutput = itemView.findViewById(R.id.description_output);
        }
    }
}
