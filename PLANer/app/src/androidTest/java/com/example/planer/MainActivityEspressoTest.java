package com.example.planer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;


import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.DatePicker;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
        onView(withId(R.id.add_new_goal_button)).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Goal1"), closeSoftKeyboard());
        onView(withId(R.id.description_input)).perform(typeText("some description"), closeSoftKeyboard());
        onView(withId(R.id.goals_counter_input)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.add_goal_button)).perform(click());
        onView(withId(R.id.add_new_goal_button)).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Goal2"), closeSoftKeyboard());
        onView(withId(R.id.description_input)).perform(typeText("some description"), closeSoftKeyboard());
        onView(withId(R.id.goals_counter_input)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.add_goal_button)).perform(click());
        onView(withId(R.id.goals_recycler_view)).check(matches(hasDescendant(withText("Goal2"))));
        onView(withId(R.id.goals_recycler_view)).check(matches(hasDescendant(withText("Goal1"))));
        assertEquals(initialGoalCount + 2, getRecyclerViewItemCount(R.id.goals_recycler_view));
    }

    // User Story 2, Scenario 2
    @Test
    public void deleteGoalTest(){
        int initialGoalCount = getRecyclerViewItemCount(R.id.goals_recycler_view);
        onView(withId(R.id.add_new_goal_button)).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Goal1"), closeSoftKeyboard());
        onView(withId(R.id.description_input)).perform(typeText("some description"), closeSoftKeyboard());
        onView(withId(R.id.goals_counter_input)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.add_goal_button)).perform(click());
        onView(withId(R.id.add_new_goal_button)).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Goal2"), closeSoftKeyboard());
        onView(withId(R.id.description_input)).perform(typeText("some description"), closeSoftKeyboard());
        onView(withId(R.id.goals_counter_input)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.add_goal_button)).perform(click());

        onView(withId(R.id.goals_recycler_view))
                .perform(actionOnItemAtPosition(0, longClick()));

        onView(withText("DELETE"))
                .perform(click());


        assertEquals(initialGoalCount + 2 - 1, getRecyclerViewItemCount(R.id.goals_recycler_view));
    }

    // User Story 5, Scenario 1
    @Test
    public void overdueGoal(){
        onView(withId(R.id.add_new_goal_button)).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Overdue Goal"), closeSoftKeyboard());
        onView(withId(R.id.description_input)).perform(typeText("some description"), closeSoftKeyboard());
        onView(withId(R.id.goals_counter_input)).perform(typeText("10"), closeSoftKeyboard());

        // current time, thus right after the goal is created it is overdue
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        onView(withId(R.id.deadline_textview)).perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month+1, dayOfMonth));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.add_goal_button)).perform(click());

        // check whether overdue output visibility is VISIBLE for first goal
    }

    // User Story 5, Scenario 2
    @Test
    public void notOverdueGoal(){
        onView(withId(R.id.add_new_goal_button)).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Overdue Goal"), closeSoftKeyboard());
        onView(withId(R.id.description_input)).perform(typeText("some description"), closeSoftKeyboard());
        onView(withId(R.id.goals_counter_input)).perform(typeText("10"), closeSoftKeyboard());

        onView(withId(R.id.add_goal_button)).perform(click());

        // check whether overdue output visibility is GONE for first goal
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
}