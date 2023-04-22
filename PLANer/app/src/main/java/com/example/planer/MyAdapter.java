package com.example.planer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
                menu.getMenu().add("ASSIGN CUMULATIVE INDEX");
//                menu.getMenu().add("ASSIGN DAILY NUMBER");
                //---//
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //---//
                        if(menuItem.getTitle().equals("ASSIGN CUMULATIVE INDEX")) {
                            Intent intent = new Intent(activity, AssignDailyNumberActivity.class);
//                            intent.putExtra("goalObject", goal);
                            activity.startActivity(intent);
                        }
//                        if(menuItem.getTitle().equals("ASSIGN DAILY NUMBER")) {
//                            //link to text field in goal_view and completedGoals/totalGoals
//                        }
                        //---//
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
