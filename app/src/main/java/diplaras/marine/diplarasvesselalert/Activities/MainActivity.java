package diplaras.marine.diplarasvesselalert.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import diplaras.marine.diplarasvesselalert.Database.FleetDatabase;
import diplaras.marine.diplarasvesselalert.PullBarView;
import diplaras.marine.diplarasvesselalert.R;

public class MainActivity extends AppCompatActivity {

    TextView searchResultsView;
    View bottomFragment;
    SearchView searchView;
    View mapLayout;
    PullBarView pullbar;
    Toolbar appToolBar;
    TextView fragmentTitle;
    public static FleetDatabase database;


    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initSearchView();

        database = Room
                .databaseBuilder(getApplicationContext(),
                                 FleetDatabase.class,
                          "FleetDatabase")
                .allowMainThreadQueries().build();

        pullbar.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_MOVE:
                    {
                        int y = (int) motionEvent.getY();

                        if (bottomFragment.getHeight() < 60 && y > 0)
                        {
                            break;
                        }

                        bottomFragment.setTop(bottomFragment.getTop() + y);

                        ConstraintLayout.LayoutParams params =
                                new ConstraintLayout.LayoutParams(mapLayout.getWidth(),
                                        mapLayout.getHeight() + y);

                        mapLayout.setLayoutParams(params);
                        mapLayout.setBottom(mapLayout.getBottom() + y);

                    }
                }
                return true;
            }
        });

        Fragment fragment = new MapFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map_layout, fragment, "MapFragment");
        fragmentTransaction.commit();

        initPortView();
    }

    private void initSearchView()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                searchView.onActionViewCollapsed();

                Bundle bundle = new Bundle();
                bundle.putString("query", query);

                final SearchFragment searchFragment = new SearchFragment();

                fragmentTitle.setText("Αναζήτηση");
                searchFragment.setArguments(bundle);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                                .beginTransaction();
                        fragmentTransaction.replace(R.id.db_fragment, searchFragment);
                        fragmentTransaction.commit();
                    }
                }).start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("Fleet")) {initFleetView();}
                return false;
            }
        });
    }

    public void initFleetView()
    {
        FleetFragment fleetFragment = new FleetFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.db_fragment, fleetFragment, "FleetFragment");
        fragmentTitle.setText("Στόλος");
        fragmentTransaction.commit();
    }

    private void initViews()
    {
        mapLayout = findViewById(R.id.map_layout);
        bottomFragment = findViewById(R.id.botFrag);
        pullbar = findViewById(R.id.pullbar);
        searchView = (SearchView) findViewById(R.id.my_search);
        searchResultsView = findViewById(R.id.search_results);
        appToolBar = (Toolbar) findViewById(R.id.myToolbar);
        fragmentTitle = findViewById(R.id.fragment_title);

        setSupportActionBar(appToolBar);
        searchView.setQueryHint("Όνομα πλοίου");

    }

    public void initPortView()
    {
        PortFragment portFragment = new PortFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.db_fragment, portFragment, "PortFragment");
        fragmentTitle.setText("Λιμάνια");
        fragmentTransaction.commit();
    }

    public void initTrafficView()
    {
        PortTrafficFragment portTrafficFragment = new PortTrafficFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.db_fragment, portTrafficFragment);
        fragmentTitle.setText("Πάχη");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        if (fragmentTitle.getText() != "Λιμάνια") {initPortView();}
        else {super.onBackPressed();}
    }

}