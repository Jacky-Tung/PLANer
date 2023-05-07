package com.example.planer;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.UUID;

import io.realm.Realm;

public class AddGoalActivity extends AppCompatActivity {

    TextView titleTextView, deadlineTextView;
    String editTitle, editDescription, editGoalsCounter, goalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        EditText titleInput = findViewById(R.id.title_input);
        EditText descriptionInput = findViewById(R.id.description_input);
        EditText goalsCounterInput = findViewById(R.id.goals_counter_input);
        MaterialButton addGoalButton = findViewById((R.id.add_goal_button));
        MaterialButton returnButton = findViewById(R.id.return_button);
        deadlineTextView = findViewById(R.id.deadline_textview);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Goal goal = realm.createObject(Goal.class, UUID.randomUUID().toString()); // realm exception error try

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String goalsCounter = goalsCounterInput.getText().toString();

                long createdAt = System.currentTimeMillis();

                if(InputValidator.validNumberInput(goalsCounter) && InputValidator.validStringInput(title, description)) {
                    goal.setTitle(title);
                    goal.setDescription(description);
                    goal.setCreatedAt(createdAt);
                    goal.setGoalsCounter(Integer.parseInt(goalsCounter));
                    goal.setGoalsCompletedCounter(0);
                    goal.setCompleted(false);
                    realm.commitTransaction();
                    Toast.makeText(getApplicationContext(), "Goal added", Toast.LENGTH_SHORT).show();
                    finish();


                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid title, description, or total number", Toast.LENGTH_SHORT).show();
                }
            }
        });



        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goal.deleteFromRealm();
                realm.commitTransaction();
                finish();
            }
        });

        deadlineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(goal);
            }
        });
    }

    private void showDatePickerDialog(Goal goal) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // display selected deadline
                        deadlineTextView.setText((month + 1) + "/" + dayOfMonth + "/" + year);

                        // add date to database
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, month);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        goal.setDeadline(selectedDate.getTime());
                        goal.setYear(year);
                        goal.setMonth(month);
                        goal.setDayOfMonth(dayOfMonth);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}

