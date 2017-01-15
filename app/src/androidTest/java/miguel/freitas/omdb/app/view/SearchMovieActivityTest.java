package miguel.freitas.omdb.app.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import miguel.freitas.omdb.app.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SearchMovieActivityTest extends ActivityInstrumentationTestCase2<SearchMovieActivity> {

	@Rule
	public ActivityTestRule<SearchMovieActivity> searchMovieActivityActivityTestRule = new ActivityTestRule<>(SearchMovieActivity.class);

	public SearchMovieActivityTest() {
		super(SearchMovieActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		getActivity();
	}

	@Test
	public void itemsFound() {
		onView(withId(R.id.search)).perform(click());
		onView(isAssignableFrom(EditText.class)).perform(typeText("50"), pressImeActionButton());
		onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
		onView(withText("50/50")).perform(click());
		assertFalse(searchMovieActivityActivityTestRule.getActivity().isFinishing());
	}

	@Test
	public void itemsNotFound() {
		onView(withId(R.id.search)).perform(click());
		onView(isAssignableFrom(EditText.class)).perform(typeText("50"), pressImeActionButton());
		onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
		onView(withText("50/50")).perform(click());
		assertFalse(searchMovieActivityActivityTestRule.getActivity().isFinishing());
	}
}