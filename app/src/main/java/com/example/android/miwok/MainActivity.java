/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 找到允许在两个fragments间滑动的view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // 创建一个adapter，了解滑动的每页是什么内容
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 找到 tab layout 显示 tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // 用view pager 链接tab layout，然后：
        //   1. 当 viewpager滑动时，更新tab layout
        //   2. 当 tab 被选择时，更新view pager
        //   3. 用view pager's adapter's titles设置tab layout的tab名字
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);

    }

}
