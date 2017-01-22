package jp.yitt.navigation_panel.example;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import jp.yitt.navigation_panel.OnItemSelectedListener;
import jp.yitt.navigation_panel.NavigationPanel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final Map<Integer, Integer> colorMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorMap.put(R.id.menu_schedule, R.color.red_A200);
        colorMap.put(R.id.menu_place, R.color.pink_A200);
        colorMap.put(R.id.menu_cost, R.color.purple_A200);
        colorMap.put(R.id.menu_belonging, R.color.deep_purple_A200);
        colorMap.put(R.id.menu_notebook, R.color.indigo_A200);
        colorMap.put(R.id.menu_settings, R.color.blue_A200);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final NavigationPanel panel = (NavigationPanel) findViewById(R.id.panel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel.expand();
            }
        });

        panel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(@IdRes int itemId) {
                panel.hide(NavigationPanel.MODE_FOCUS);
                ColoredFragment fragment = ColoredFragment.newInstance(colorMap.get(itemId));

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, fragment)
                        .setCustomAnimations(R.anim.fragment_in, android.R.anim.fade_out, R.anim.fragment_in, android.R.anim.fade_out)
                        .commit();
            }
        });

        panel.setSelectedItemIndex(0);
    }
}
