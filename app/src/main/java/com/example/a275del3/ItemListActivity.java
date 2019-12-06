package com.example.a275del3;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.a275del3.dummy.MatchupChartDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a275del3.dummy.MatchupChart;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements MatchupChartAdapter.OnChartIndClick {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private MatchupChartDatabase chartDatabase;
    private List<MatchupChart.Chart> charts;
    private MatchupChartAdapter chartsAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeToSettings = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(changeToSettings);
            }
        });

        FloatingActionButton createNew = (FloatingActionButton) findViewById(R.id.createNew);
        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeToCreation = new Intent(view.getContext(), CreationActivity.class);
                startActivity(changeToCreation);
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void displayList() {
        chartDatabase = MatchupChartDatabase.getInstance(ItemListActivity.this);
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void, Void, List<MatchupChart.Chart>> {

        private WeakReference<ItemListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(ItemListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<MatchupChart.Chart> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().chartDatabase.getNoteDao().getCharts();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<MatchupChart.Chart> charts) {
            if (charts != null && charts.size() > 0) {
                activityReference.get().charts.clear();
                activityReference.get().charts.addAll(charts);

                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().chartsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewMsg = (TextView) findViewById(R.id.tv__empty);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ItemListActivity.this));
        charts = new ArrayList<>();
        chartsAdapter = new MatchupChartAdapter(charts, ItemListActivity.this);
        recyclerView.setAdapter(chartsAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(ItemListActivity.this, CreationActivity.class), 100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                charts.add((MatchupChart.Chart) data.getSerializableExtra("note"));
            } else if (resultCode == 2) {
                charts.set(pos, (MatchupChart.Chart) data.getSerializableExtra("note"));
            }
            listVisibility();
        }
    }

    @Override
    public void onChartClick(final int pos) {
        new AlertDialog.Builder(ItemListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                chartDatabase.getNoteDao().delete(charts.get(pos));
                                charts.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                ItemListActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(ItemListActivity.this,
                                                CreationActivity.class).putExtra("chart", charts.indexOf(i)),
                                        100);

                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (charts.size() == 0){ // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        chartsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        chartDatabase.cleanUp();
        super.onDestroy();
    }

}


//    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
//        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, MatchupChart.ITEMS, mTwoPane));
//    }


