package es.unex.giiis.asee.tiviclone.view


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.giiis.asee.tiviclone.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LibraryActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun libraryActivityTest() {
        val materialButton = onView(
            withId(R.id.bt_register)
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            withId(R.id.et_username)
        )
        appCompatEditText.perform(replaceText("espresso"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            withId(R.id.et_password)
        )
        appCompatEditText2.perform(replaceText("latte"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            withId(R.id.et_repassword)
        )
        appCompatEditText3.perform(replaceText("latte"), closeSoftKeyboard())

        val materialButton2 = onView(
            withId(R.id.bt_register)
        )
        materialButton2.perform(click())

        val materialButton3 = onView(
                withId(R.id.bt_login)
        )
        materialButton3.perform(click())

        val recyclerView = onView(
            withId(R.id.rv_show_list)
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, longClick()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.libraryFragment), withContentDescription("Library"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.tv_title), withText("Game of Thrones"),
                withParent(withParent(withId(R.id.cv_Item))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Game of Thrones")))

        val textView2 = onView(
            allOf(
                withId(com.google.android.material.R.id.navigation_bar_item_large_label_view),
                withText("Library"),
                withParent(
                    allOf(
                        withId(com.google.android.material.R.id.navigation_bar_item_labels_group),
                        withParent(
                            allOf(
                                withId(R.id.libraryFragment),
                                withContentDescription("Library")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Library")))

        val view = onView(
            allOf(
                withId(com.google.android.material.R.id.navigation_bar_item_active_indicator_view),
                withParent(
                    allOf(
                        withId(com.google.android.material.R.id.navigation_bar_item_icon_container),
                        withParent(
                            allOf(
                                withId(R.id.libraryFragment),
                                withContentDescription("Library")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        view.check(matches(isDisplayed()))

        val recyclerView2 = onView(
            withId(R.id.rv_lib_show_list)
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val switch_ = onView(
            withId(R.id.sw_fav)
        )
        switch_.check(matches(isChecked()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
