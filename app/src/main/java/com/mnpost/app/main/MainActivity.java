package com.mnpost.app.main;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mnpost.app.BaseActivity;
import com.mnpost.app.R;
import com.mnpost.app.main.mailer.MailerFragment;
import com.mnpost.app.main.mailer.MailerPresenter;
import com.mnpost.app.main.setting.SettingFragment;
import com.mnpost.app.main.setting.SettingPresenter;
import com.mnpost.app.main.statistical.StatisticalFragment;
import com.mnpost.app.main.statistical.StatisticalPresenter;
import com.mnpost.app.util.ActivityUtils;
import com.mnpost.app.util.BottomNavigationBehavior;

import java.lang.reflect.Field;

public class MainActivity extends BaseActivity {

    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        StatisticalFragment statisFrag = StatisticalFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), statisFrag, R.id.contentFrame);
        new StatisticalPresenter(statisFrag);
        navigation.getMenu().findItem(R.id.home).setChecked(true);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    StatisticalFragment statisFrag = StatisticalFragment.newInstance();
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), statisFrag, R.id.contentFrame);
                    new StatisticalPresenter(statisFrag);
                    navigation.getMenu().findItem(R.id.home).setChecked(true);
                    break;
                case R.id.notification:
                    navigation.getMenu().findItem(R.id.notification).setChecked(true);
                    break;
                case R.id.mailer:
                    MailerFragment mailerFrag = MailerFragment.newInstance();
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mailerFrag, R.id.contentFrame);
                    new MailerPresenter(mailerFrag);
                    navigation.getMenu().findItem(R.id.mailer).setChecked(true);
                    break;
                case R.id.setting:
                    SettingFragment settingFrag = SettingFragment.newInstance();
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), settingFrag, R.id.contentFrame);
                    new SettingPresenter(settingFrag);
                    navigation.getMenu().findItem(R.id.setting).setChecked(true);
                    break;
            }

            return false;
        }
    };


    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }


}
