/*
 * Copyright 2018-2020 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kunminx.architecture.ui;

import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kunminx.architecture.data.manager.NetworkStateManager;
import com.kunminx.architecture.utils.AdaptScreenUtils;
import com.kunminx.architecture.utils.BarUtils;
import com.kunminx.architecture.utils.ScreenUtils;

/**
 * Create by KunMinX at 19/8/1
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ViewModelProvider mActivityProvider;

    public BaseActivity() {
        super();
    }

    /**
     * 从 AndroidX AppCompat 1.1.0 和 Fragment 1.1.0 开始，可以将布局id通过构造器传入
     * <p>
     * 例如
     * <p>
     * class MyActivity : AppCompatActivity(R.layout.my_activity)
     * class MyFragmentActivity: FragmentActivity(R.layout.my_fragment_activity)
     * class MyFragment : Fragment(R.layout.my_fragment)
     *
     * @param contentLayoutId 布局id
     */
    @ContentView
    public BaseActivity(@LayoutRes int contentLayoutId) {
        super(contentLayoutId);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        BarUtils.setStatusBarLightMode(this, true);

        getLifecycle().addObserver(NetworkStateManager.getInstance());
    }

    public boolean isDebug() {
        return getApplicationContext().getApplicationInfo() != null &&
                (getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }

    protected void showLongToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int stringRes) {
        showLongToast(getApplicationContext().getString(stringRes));
    }

    protected void showShortToast(int stringRes) {
        showShortToast(getApplicationContext().getString(stringRes));
    }

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }
}