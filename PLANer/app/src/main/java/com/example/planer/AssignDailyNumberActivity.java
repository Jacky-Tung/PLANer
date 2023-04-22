package com.example.planer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AssignDailyNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_daily_number);

        EditText dailyNumberInput = findViewById(R.id.daily_number_input);
        MaterialButton assignDailyNumberButton = findViewById(R.id.assign_dailynumber_button);
        MaterialButton returnButton = findViewById(R.id.return_button);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        assignDailyNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dailyNumber = dailyNumberInput.getText().toString();

                if(InputValidator.validTotalAndDailyNumber(dailyNumber)){
                    // fetch specific goal clicked on, set daily number
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid daily number", Toast.LENGTH_SHORT).show();
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