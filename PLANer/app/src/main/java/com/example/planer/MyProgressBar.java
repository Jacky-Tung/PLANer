package com.example.planer;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

/*


    How to use ProgressBar

        create an object
        set goalsCounter & completed counter
        calling the display method


 */

public class MyProgressBar {

    // findViewById(R.id.)
    public int goalsCounter;

    public int goalsCompletedCounter;

    public Button start;

    public ProgressBar progressBar;


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

        progressBar = progressBar.findViewById(R.id.progressBar);
       //  start = progressBar.findViewById(R.id.start);

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
