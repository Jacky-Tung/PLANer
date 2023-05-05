package com.example.planer;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

/*


    How to use ProgressBar

        create an object
        set goalsCounter & completed counter
        calling the display method


                    MyProgressBar myProgressBar = new MyProgressBar(view);
                    myProgressBar.setGoalsCompletedCounter(0);
                    myProgressBar.setGoalsCounter(goal.getGoalsCounter());
                    myProgressBar.displayProgressBar();




 */

public class MyProgressBar {

    // findViewById(R.id.)
    public int goalsCounter;

    public int goalsCompletedCounter;

    public Button start;

    public ProgressBar progressBar;

    private View view;

    public MyProgressBar(View view) {
        this.view = view.getRootView();
    }

    public int getGoalsCounter() {
        return goalsCounter;
    }

    public void setGoalsCounter(int goalsCounter) {
        this.goalsCounter = goalsCounter;
    }

    public int getGoalsCompletedCounter() {
        return goalsCompletedCounter;
    }

    public void setGoalsCompletedCounter(int goalsCompletedCounter) {
        this.goalsCompletedCounter = goalsCompletedCounter;
    }

    public Button getStart() {
        return start;
    }

    public void setStart(Button start) {
        this.start = start;
    }


    public void displayProgressBar(){

        Log.d("PLANER", "progressBar is " + progressBar);
        progressBar = view.findViewById(R.id.progressBar);
       //  start = progressBar.findViewById(R.id.start);
        start = view.findViewById(R.id.add_goal_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setContentView(R.layout.activity_main);
                progressBar.setIndeterminate(false);
                if(goalsCounter == 0 || goalsCounter/goalsCompletedCounter <= 0 ){
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    progressBar.setMax(goalsCounter);
                    progressBar.setProgress(goalsCompletedCounter);
                }
            }
        });
    }


}
