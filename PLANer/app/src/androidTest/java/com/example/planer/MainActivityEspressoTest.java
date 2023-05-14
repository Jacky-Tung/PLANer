package com.example.planer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.icu.util.Calendar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    // User Story 2, Scenario 1
    @Test
    public void addNewGoalTest(){
        int initialGoalCount = getRecyclerViewItemCount(R.id.goals_recycler_view);

        addAGoal("Goal1", "some description", "10");
        submitAddGoal();
        addAGoal("Goal2", "some description", "10");
        submitAddGoal();

        assertEquals(initialGoalCount + 2, getRecyclerViewItemCount(R.id.goals_recycler_view));
    }

    // User Story 2, Scenario 2
    @Test
    public void deleteGoalTest(){
        int initialGoalCount = getRecyclerViewItemCount(R.id.goals_recycler_view);

        addAGoal("Goal1", "some description", "10");
        submitAddGoal();
        addAGoal("Goal2", "some description", "10");
        submitAddGoal();

        deleteLatestGoal();

        assertEquals(initialGoalCount + 2 - 1, getRecyclerViewItemCount(R.id.goals_recycler_view));
    }

    // User Story 5, Scenario 1
    @Test
    public void overdueGoal(){
        deleteExistingGoals();

        addAGoal("Overdue Goal", "some description", "10");
        selectCurrentDate();
        submitAddGoal();

        checkVisibility(R.id.overdue_output, ViewMatchers.Visibility.VISIBLE);
    }

    // User Story 5, Scenario 2
    @Test
    public void notAssignedDeadlineGoal(){
        deleteExistingGoals();

        addAGoal("Not Assigned Deadline Goal", "some description", "10");
        submitAddGoal();

        checkVisibility(R.id.overdue_output, ViewMatchers.Visibility.GONE);
    }

    // User story #9, Scenario 1
    @Test
    public void completedGoal(){
        deleteExistingGoals();

        addAGoal("Completed goal", "some description", "10");
        submitAddGoal();

        updateProgress("10", "Completed goal");

        checkVisibility(R.id.completed_output, ViewMatchers.Visibility.VISIBLE);
    }

    // User story #9, Scenario 2
    @Test
    public void incompleteGoal(){
        deleteExistingGoals();

        addAGoal("Incomplete goal", "some description", "100");
        submitAddGoal();

        updateProgress("10", "Incomplete goal");

        checkVisibility(R.id.completed_output, ViewMatchers.Visibility.GONE);
    }

    // User story #7, Scenario 1
    @Test
    public void goalWithProgress(){
        deleteExistingGoals();

        addAGoal("Goal With Progress", "some description", "10");
        submitAddGoal();

        updateProgress("2", "Goal With Progress");

        checkProgressBar("2");
    }

    // User story #7, Scenario 2
    @Test
    public void goalWithNoProgress(){
        deleteExistingGoals();

        addAGoal("Goal Without Progress", "some description", "10");
        submitAddGoal();

        checkProgressBar("0");
    }

    // User story #8, Scenario 1
    @Test
    public void modifyExistingGoal() {

        deleteExistingGoals();

        addAGoal("Original Goal", "Original Description", "100");
        submitAddGoal();
        String originalTitle = getText(withId(R.id.title_output));


        modifyAGoal("Modified Goal", "Modified Description", "200");
        submitModifiedGoal();
        String modifiedTitle = getText(withId(R.id.title_output));

        assertNotEquals(originalTitle, modifiedTitle);
    }

    // User story #8, Scenario 2
    @Test
    public void modifyExistingGoalCancel() {

        deleteExistingGoals();

        addAGoal("Original Goal", "Original Description", "100");
        submitAddGoal();
        String originalTitle = getText(withId(R.id.title_output));

        modifyAGoal("Modified Goal", "Modified Description", "200");
        onView(withId(R.id.return_button_modify)).perform(click());
        String modifiedTitle = getText(withId(R.id.title_output));

        assertTrue(originalTitle.equals(modifiedTitle));
    }

    private String getGoalTitle() {
        String title = onView(withId(R.id.title_output)).toString();
        return title;
    }

    private int getRecyclerViewItemCount(int recyclerViewId) {
        AtomicReference<Integer> itemCount = new AtomicReference<>();
        onView(withId(recyclerViewId)).check(new RecyclerViewItemCountAssertion(itemCount));
        return itemCount.get();
    }

    static class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final AtomicReference<Integer> itemCount;

        RecyclerViewItemCountAssertion(AtomicReference<Integer> itemCount) {
            this.itemCount = itemCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }
            if (!(view instanceof RecyclerView)) {
                throw new AssertionError("The asserted view is not a RecyclerView");
            }
            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null) {
                itemCount.set(adapter.getItemCount());
            }
        }
    }

    private static ViewAction setDate(final int year, final int monthOfYear, final int dayOfMonth) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(DatePicker.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "set date";
            }

            @Override
            public void perform(UiController uiController, View view) {
                DatePicker datePicker = (DatePicker) view;
                datePicker.updateDate(year, monthOfYear, dayOfMonth);
            }
        };
    }

    private void deleteExistingGoals(){
        int goalCount = getRecyclerViewItemCount(R.id.goals_recycler_view);
        while(goalCount != 0){
            onView(withId(R.id.goals_recycler_view))
                    .perform(actionOnItemAtPosition(0, longClick()));

            onView(withText("DELETE"))
                    .perform(click());

            goalCount = getRecyclerViewItemCount(R.id.goals_recycler_view);
        }
    }

    private void addAGoal(String title, String description, String goalsCounter){
        onView(withId(R.id.add_new_goal_button)).perform(click());
        onView(withId(R.id.title_input)).perform(typeText(title), closeSoftKeyboard());
        onView(withId(R.id.description_input)).perform(typeText(description), closeSoftKeyboard());
        onView(withId(R.id.goals_counter_input)).perform(typeText(goalsCounter), closeSoftKeyboard());
    }

    private void modifyAGoal(String modifiedTitle, String modifiedDescription, String modifiedGoalsCounter){

        onView(withId(R.id.goals_recycler_view)).perform(actionOnItemAtPosition(0, longClick()));
        onView(withText("MODIFY GOAL")).perform(click());

        onView(withId(R.id.title_input_modify)).perform(replaceText(modifiedTitle));
        onView(withId(R.id.description_input_modify)).perform(replaceText(modifiedDescription));
        onView(withId(R.id.goals_counter_input_modify)).perform(replaceText(modifiedGoalsCounter));
    }

    private void deleteLatestGoal(){
        onView(withId(R.id.goals_recycler_view))
                .perform(actionOnItemAtPosition(0, longClick()));

        onView(withText("DELETE"))
                .perform(click());
    }

    private void selectCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        onView(withId(R.id.deadline_textview)).perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month+1, dayOfMonth));
        onView(withText("OK")).perform(click());
    }

    private void submitAddGoal(){
        onView(withId(R.id.add_goal_button)).perform(click());
    }

    private void submitModifiedGoal() {
        onView(withId(R.id.save_goal_button)).perform(click());
    }

    private void checkVisibility(int id, ViewMatchers.Visibility visibility){
        onView(ViewMatchers.withId(R.id.goals_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withId(id))));

        onView(withId(id)).check(matches(withEffectiveVisibility(visibility)));
    }

    private void updateProgress(String progress, String text){
        onView(withText(text)).perform(click());
        onView(withText("Enter progress completed")).check(matches(isDisplayed()));
        onView(withText("Please enter your progress:")).check(matches(isDisplayed()));
        onView(isAssignableFrom(EditText.class)).perform(typeText(progress), closeSoftKeyboard());
        onView(withText("OK")).perform(click());
    }

    private void checkProgressBar(String expectedProgress){
        activityRule.getScenario().onActivity(activity -> {
            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            assertEquals(Integer.parseInt(expectedProgress), progressBar.getProgress());
        });
    }

    private String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public String getDescription() {
                return "retrieving text from textview";
            }

            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView textView = (TextView)view;
                stringHolder[0] = textView.getText().toString();
            }
        });
        return stringHolder[0];
    }
}