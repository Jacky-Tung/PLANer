package com.example.planer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        EditText titleInput = findViewById(R.id.title_input);
        EditText descriptionInput = findViewById(R.id.description_input);
        EditText totalNumberInput = findViewById(R.id.total_number_input);
        MaterialButton addGoalButton = findViewById((R.id.add_goal_button));
        MaterialButton returnButton = findViewById(R.id.return_button);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String totalNumber = totalNumberInput.getText().toString();
                long createdAt = System.currentTimeMillis();

                if(InputValidator.validTotalAndDailyNumber(totalNumber) && InputValidator.validTitleAndDescription(title, description)) {
                    Goal goal = realm.createObject(Goal.class);
                    goal.setTitle(title);
                    goal.setDescription(description);
                    goal.setCreatedAt(createdAt);
                    goal.setTotalNumber(Integer.parseInt(totalNumber));
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
                realm.commitTransaction();
                finish();
            }
        });
    }
}