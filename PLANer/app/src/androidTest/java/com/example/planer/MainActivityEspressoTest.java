package com.example.planer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.localplaner.R;

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
        onView(withText("Add new goal")).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Goal1"), closeSoftKeyboard());
        onView(withId(R.id.add_goal_button)).perform(click());
        onView(withText("Add new goal")).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Goal2"), closeSoftKeyboard());
        onView(withId(R.id.add_goal_button)).perform(click());
        onView(withId(R.id.goals_recycler_view)).check(matches(hasDescendant(withText("Goal2"))));
        onView(withId(R.id.goals_recycler_view)).check(matches(hasDescendant(withText("Goal1"))));
        assertEquals(initialGoalCount + 2, getRecyclerViewItemCount(R.id.goals_recycler_view));
    }

    // User Story 2, Scenario 2
    @Test
    public void deleteGoalTest(){
        int initialGoalCount = getRecyclerViewItemCount(R.id.goals_recycler_view);
        onView(withText("Add new goal")).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Goal1"), closeSoftKeyboard());
        onView(withId(R.id.add_goal_button)).perform(click());
        onView(withText("Add new goal")).perform(click());
        onView(withId(R.id.title_input)).perform(typeText("Goal2"), closeSoftKeyboard());
        onView(withId(R.id.add_goal_button)).perform(click());

        onView(withId(R.id.goals_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withText("DELETE"))
                .perform(click());


        assertEquals(initialGoalCount + 2 - 1, getRecyclerViewItemCount(R.id.goals_recycler_view));
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
}