package edu.gatech.seclass.crypto6300;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCreatePlayer {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setup() {
        onView(withId(R.id.login_input)).perform(replaceText("CryptoAdmin"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_new_player_button)).perform(click());
        DbHelper.getConnection().reset();
    }

    //Test player creation with no first name provided.
    @Test
    public void testCreatePlayer1() {
        onView(withId(R.id.create_player_txt_ln)).perform(replaceText("Wu"), closeSoftKeyboard());
        onView(withId(R.id.create_player_txt_un)).perform(replaceText("fwu79"), closeSoftKeyboard());
        onView(withId(R.id.create_player_btn_save)).perform(click());
        onView(withId(R.id.create_player_txt_fn)).check(matches(hasErrorText("First name is required.")));
        DbHelper.getConnection().reset();
    }

    //Test player creation with no last name provided.
    @Test
    public void testCreatePlayer2() {
        onView(withId(R.id.create_player_txt_fn)).perform(replaceText("Fan"), closeSoftKeyboard());
        onView(withId(R.id.create_player_txt_un)).perform(replaceText("fwu79"), closeSoftKeyboard());
        onView(withId(R.id.create_player_btn_save)).perform(click());
        onView(withId(R.id.create_player_txt_ln)).check(matches(hasErrorText("Last name is required.")));
        DbHelper.getConnection().reset();
    }

    //Test player creation with no username provided.
    @Test
    public void testCreatePlayer3() {
        onView(withId(R.id.create_player_txt_fn)).perform(replaceText("Fan"), closeSoftKeyboard());
        onView(withId(R.id.create_player_txt_ln)).perform(replaceText("Wu"), closeSoftKeyboard());
        onView(withId(R.id.create_player_btn_save)).perform(click());
        onView(withId(R.id.create_player_txt_un)).check(matches(hasErrorText("Username is required.")));
        DbHelper.getConnection().reset();
    }

    //Test player creation without any information provided.
    @Test
    public void testCreatePlayer4() {
        onView(withId(R.id.create_player_btn_save)).perform(click());
        onView(withId(R.id.create_player_txt_fn)).check(matches(hasErrorText("First name is required.")));
        onView(withId(R.id.create_player_txt_ln)).check(matches(hasErrorText("Last name is required.")));
        onView(withId(R.id.create_player_txt_un)).check(matches(hasErrorText("Username is required.")));
        DbHelper.getConnection().reset();
    }

    //Test player creation with all information provided.
    @Test
    public void testCreatePlayer5() {
        onView(withId(R.id.create_player_txt_fn)).perform(replaceText("Fan"), closeSoftKeyboard());
        onView(withId(R.id.create_player_txt_ln)).perform(replaceText("Wu"), closeSoftKeyboard());
        onView(withId(R.id.create_player_txt_un)).perform(replaceText("fwu79"), closeSoftKeyboard());
        onView(withId(R.id.create_player_btn_save)).perform(click());
        onView(withText("Successfully Created the Player")).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.create_player_txt_fn)).check(matches(withText("")));
        onView(withId(R.id.create_player_txt_ln)).check(matches(withText("")));
        onView(withId(R.id.create_player_txt_un)).check(matches(withText("")));
        DbHelper.getConnection().reset();
    }

    //Test player creation with existing username.
    @Test
    public void testCreatePlayer6() {
        onView(withId(R.id.create_player_txt_fn)).perform(replaceText("Fan"), closeSoftKeyboard());
        onView(withId(R.id.create_player_txt_ln)).perform(replaceText("Wu"), closeSoftKeyboard());
        onView(withId(R.id.create_player_txt_un)).perform(replaceText("fwu79"), closeSoftKeyboard());
        onView(withId(R.id.create_player_btn_save)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.create_player_txt_fn)).perform(replaceText("Fan"), closeSoftKeyboard());
        onView(withId(R.id.create_player_txt_ln)).perform(replaceText("Wu"), closeSoftKeyboard());
        onView(withId(R.id.create_player_txt_un)).perform(replaceText("fwu79"), closeSoftKeyboard());
        onView(withId(R.id.create_player_btn_save)).perform(click());
        onView(withId(R.id.create_player_txt_un)).check(matches(hasErrorText("Username is not unique.")));
        DbHelper.getConnection().reset();
    }
}
