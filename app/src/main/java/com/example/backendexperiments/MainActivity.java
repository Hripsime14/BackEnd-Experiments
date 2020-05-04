package com.example.backendexperiments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.backendexperiments.Adapters.OverviewAdapter;
import com.example.backendexperiments.Models.BooksOverviewModel;
import com.example.backendexperiments.Util.Parser;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
//    String urlDateString = "https://api.nytimes.com/svc/books/v3/lists/names.json?api-key=ryOX7WaLMNT9uclXL53TkYHMcYkQTYFa";
    String urlDateString;
    MyRequestQueue myQueue;
    Context context;
    ListView listView;
    OverviewAdapter adapter;
    List<BooksOverviewModel> booksOverviewModelList = new ArrayList<>();
    private DrawerLayout drawer;
    private MainActivity currentActivity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
//        int mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
//        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
//        setContentView(view, new ViewGroup.LayoutParams(mScreenWidth, mScreenHeight));

        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.nav_list:
                            intent = new Intent(currentActivity, BookListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            currentActivity.startActivity(intent);
                        break;
                    case R.id.nav_overview:
                        intent = new Intent(currentActivity, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        currentActivity.startActivity(intent);
                        break;
                }
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        myQueue = MyRequestQueue.getInstance(context);
        final DatePicker simpleDatePicker = findViewById(R.id.simpleDatePicker);
        simpleDatePicker.setVisibility(View.INVISIBLE);
        listView = findViewById(R.id.overviewListView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                simpleDatePicker.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        Button selectButton = findViewById(R.id.selectButtonId);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleDatePicker.setVisibility(View.VISIBLE);
            }
        });
        Button okButton = findViewById(R.id.okButtonId);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myQueue.setDate(getDate(simpleDatePicker));
                MakeRequest request = new MakeRequest(context);
                request.execute();
                simpleDatePicker.setVisibility(View.INVISIBLE);
            }
        });
    }

    public String getDate(DatePicker simpleDatePicker) {//poxel datDate- vor menak date sarqi uxarki
//        String year = simpleDatePicker.getYear() + "";
//        String month = simpleDatePicker.getMonth() + "";
//        String day = simpleDatePicker.getDayOfMonth() + "";
//        if(Integer.parseInt(month) < 10) month = "0" + month + 1;
//        if(Integer.parseInt(day) < 10) day  = "0" + day ;

        int year = simpleDatePicker.getYear();
        int month = simpleDatePicker.getMonth() +1;
        int day = simpleDatePicker.getDayOfMonth();


        Log.d("ExperimentsLog", "getDate: url = " + urlDateString);

        return year + "-" + month + "-" + day;
    }

    public void createListStuff() {
        if (adapter == null) adapter = new OverviewAdapter(context, R.layout.simple_overview_item, booksOverviewModelList);

        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public  class MakeRequest extends AsyncTask<String, Void, String> {
        Context context;
        MakeRequest(Context context) {
            this.context = context;
        }
        @Override
        protected String doInBackground(String... strings) {
            StringRequest request = new StringRequest(Request.Method.GET, myQueue.getOverViewURL(urlDateString),
                    new Response.Listener<String>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(String response) {
                    booksOverviewModelList = new Parser().parseListsWithDate(response);
                    createListStuff();
                }
            }, new Response.ErrorListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ExperimentsLog", "doInBackground: url = " + myQueue.getOverViewURL(urlDateString));
                    Log.d("ExperimentsLog", "doInBackground: error = " + error + ", date = " + urlDateString);
                }
            });
            myQueue.addToRequestQueue(request);
            return null;
        }
    }
}
