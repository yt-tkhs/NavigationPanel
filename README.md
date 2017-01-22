# NavigationPanel
Simple navigation UI library for Android

## Demo
![](https://raw.githubusercontent.com/yt-tkhs/NavigationPanel/master/art/demo.gif)

## Download (Gradle)
```gradle
dependencies {
    compile 'jp.yitt:navigation_panel:0.1.0'
}

```

## Requirements
- minSdkVersion: 21 (4.0+ will be supported in the future)

## Usage
### menu_panel.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/menu_schedule"
        android:icon="@drawable/ic_schedule_white_24dp"
        android:title="Schedule" />

    <item
        android:id="@+id/menu_place"
        android:icon="@drawable/ic_place_white_24dp"
        android:title="Place" />

    <item
        android:id="@+id/menu_cost"
        android:icon="@drawable/ic_account_balance_wallet_white_24dp"
        android:title="Cost" />

    <item
        android:id="@+id/menu_belonging"
        android:icon="@drawable/ic_assignment_white_24dp"
        android:title="Belonging" />

    <item
        android:id="@+id/menu_notebook"
        android:icon="@drawable/ic_book_white_24dp"
        android:title="Notebook" />

    <item
        android:id="@+id/menu_settings"
        android:icon="@drawable/ic_settings_white_24dp"
        android:title="Settings" />
</menu>
```

### activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/colorPrimary"
            android:elevation="4dp"
            android:minHeight="?actionBarSize"
            app:navigationIcon="@drawable/ic_menu_white_24dp"
            app:title="@string/app_name"
            app:titleTextColor="@android:color/white" />

        <FrameLayout
            android:id="@+id/content"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </LinearLayout>

    <jp.yitt.navigation_panel.NavigationPanel
        android:id="@+id/panel"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="?colorPrimaryDark"
        android:elevation="8dp"
        app:panel_menu="@menu/menu_panel"
        app:panel_selected="0" />
</FrameLayout>
```

### MainActivity.java
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final NavigationPanel panel = (NavigationPanel) findViewById(R.id.panel);

        // expanding NavigationPanel
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel.expand();
            }
        });

        // Item is selected
        panel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(@IdRes int itemId) {
                panel.hide(NavigationPanel.MODE_FOCUS);

                // TODO Change Fragment
            }
        });
    }
}

```

## Supported Attributes
- `app:panel_menu`: The menu item of NavigationPanel.
- `app:panel_background`: The background color of NavigationPanel.
- `app:panel_navigationIcon`: The icon of navigation button on the upper left.
- `app:panel_itemTextColor`: The text color of items.
- `app:panel_selected`: The index number of default selected item.

## TODO
- [ ] 4.0+ Support (Currently 5.0+)

## License
```
Copyright 2017 Yuta Takahashi (yt-tkhs)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```