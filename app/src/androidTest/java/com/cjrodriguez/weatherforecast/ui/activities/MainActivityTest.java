package com.cjrodriguez.weatherforecast.ui.activities;


import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import android.Manifest;
import android.app.Instrumentation;
import android.os.Build;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import com.cjrodriguez.weatherforecast.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Test
    public void testIfMainActivityIsDisplayed(){

        grantPermission();

        Espresso.onView(ViewMatchers.withId(R.id.open_button)).check(matches(isDisplayed()));
    }

    private void grantPermission(){
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        if (Build.VERSION.SDK_INT >= 23) {
            UiObject allowPermission;
            String allowText;
            if (Build.VERSION.SDK_INT == 23){
                allowText = "Allow";
            } else if(Build.VERSION.SDK_INT <=28){
                allowText = "ALLOW";
            } else if(Build.VERSION.SDK_INT == 29){
                allowText = "Allow only while using the app";
            } else {
                allowText = "While using the app";
            }
            allowPermission = UiDevice.getInstance(instrumentation).findObject(new UiSelector().text(allowText));

            if(allowPermission.exists()){
                try {
                    allowPermission.click();
                } catch (UiObjectNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
