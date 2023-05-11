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

import java.util.Objects;

import io.realm.Realm;

public class ModifyGoalActivity extends AppCompatActivity {

    TextView titleTextView, deadlineTextView;
    String modifyTitle, modifyDescription, modifyGoalsCounter, goalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_goal);

        EditText titleInput = findViewById(R.id.title_input_modify);
        EditText descriptionInput = findViewById(R.id.description_input_modify);
        EditText goalsCounterInput = findViewById(R.id.goals_counter_input_modify);
        MaterialButton addGoalButton = findViewById((R.id.save_goal_button));
        MaterialButton returnButton = findViewById(R.id.return_button_modify);
        deadlineTextView = findViewById(R.id.deadline_textview_modify);

        titleTextView = findViewById(R.id.title_textview_modify);
        modifyTitle = getIntent().getStringExtra("title");
        modifyDescription = getIntent().getStringExtra("description");
        modifyGoalsCounter = getIntent().getStringExtra("goalsCounter");
        goalID = getIntent().getStringExtra("goalID");

        titleInput.setText(modifyTitle);
        descriptionInput.setText(modifyDescription);
        goalsCounterInput.setText(modifyGoalsCounter);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Goal goal = realm.copyFromRealm(Objects.requireNonNull(realm.where(Goal.class)
                                               .equalTo("goalID", goalID)
                                               .findFirst()));

        deadlineTextView.setText((goal.getMonth() + 1) + "/" + goal.getDayOfMonth() + "/" + goal.getYear()); //put calendar data to accessors / check for issues


        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String goalsCounter = goalsCounterInput.getText().toString();

                if(InputValidator.validNumberInput(goalsCounter) && InputValidator.validStringInput(title, description)) {
                    goal.setTitle(title);
                    goal.setDescription(description);
                    goal.setGoalsCounter(Integer.parseInt(goalsCounter));
                    realm.copyToRealmOrUpdate(goal);
                    realm.commitTransaction();
                    Toast.makeText(getApplicationContext(), "Goal Saved", Toast.LENGTH_SHORT).show();
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

