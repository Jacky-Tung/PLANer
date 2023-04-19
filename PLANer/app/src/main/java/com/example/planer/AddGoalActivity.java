package com.example.planer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.localplaner.R;
import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        EditText titleInput = findViewById(R.id.title_input);
        EditText descriptionInput = findViewById(R.id.description_input);
        MaterialButton addGoalButton = findViewById((R.id.add_goal_button));

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                long createdAt = System.currentTimeMillis();

                realm.beginTransaction();
                Goal goal = realm.createObject(Goal.class);
                goal.setTitle(title);
                goal.setDescription(description);
                goal.setCreatedAt(createdAt);
                realm.commitTransaction();
                Toast.makeText(getApplicationContext(), "Goal added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}