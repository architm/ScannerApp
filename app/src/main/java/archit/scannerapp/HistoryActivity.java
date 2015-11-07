package archit.scannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.couchbase.lite.CouchbaseLiteException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import archit.model.Constants;
import archit.model.History;
import archit.persistence.HistoryRepository;
import archit.persistence.HistoryRepositoryImpl;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = "code";
    private RecyclerView mRecyclerView;

    private HistoryRepository hr;

    private String[] historyList = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.history_toolbar);
        toolbar.setTitle(Constants.HISTORY_LIST_TITLE.getValue());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.rc_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            hr = new HistoryRepositoryImpl(this, Constants.DB_NAME.getValue());
        } catch (IOException | CouchbaseLiteException e) {
            e.printStackTrace();
        }
        Map<String, Object> mp = hr.getAllByKey(Constants.HISTORY_TABLE_NAME.getValue());
        List<History> history = new ArrayList<History>();
        for(Map.Entry<String, Object> entry : mp.entrySet()) {
            if(!(entry.getKey().contains("_id") || entry.getKey().contains("_rev"))) {
                History h = new History();
                h.setDate(entry.getKey());
                h.setData((String) entry.getValue());
                history.add(h);
            }
        }
        Collections.sort(history, new Comparator<History>() {
            DateFormat f = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");

            @Override
            public int compare(History o1, History o2) {
                try {
                    return f.parse(o2.getDate()).compareTo(f.parse(o1.getDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        HistoryAdapter adapter = new HistoryAdapter(HistoryActivity.this, history);
        mRecyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, ScannerActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(this, ScannerActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}