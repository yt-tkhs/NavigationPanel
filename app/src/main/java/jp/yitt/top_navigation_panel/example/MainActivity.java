package jp.yitt.top_navigation_panel.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import jp.yitt.top_navigation_panel.TopNavigationPanel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TopNavigationPanel panel = (TopNavigationPanel) findViewById(R.id.panel);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel.setState(TopNavigationPanel.STATE_EXPANDED);
            }
        });
    }
}
