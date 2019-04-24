package com.shadow.cowlogs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.shadow.cowlogs.fragments.DataEntryFragment;
import com.shadow.cowlogs.fragments.HomepageFragment;
import com.shadow.cowlogs.fragments.ProfileFragment;
import com.shadow.cowlogs.fragments.ShowLogEntryFragment;
import com.shadow.cowlogs.models.LogEntry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataEntryFragment.EntryListener,
        HomepageFragment.HomepageListener, ShowLogEntryFragment.ShowLogEntryListener {

    public static List<LogEntry> logEntryList;
    private List<LogEntry> newEntryList = null;
    private ProgressDialog progressDialog;

    private DbHelper dbh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        Toast.makeText(this, "Entries " + logEntryList.size(), Toast.LENGTH_SHORT).show();

        displayFragment(new HomepageFragment());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (newEntryList.size() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Database not saved");
            builder.setMessage("Save entries to DB first");

            builder
                    .setNegativeButton("NO", (DialogInterface dialog, int which) -> {

                        finish();

                    })
                    .setPositiveButton("Yes", (DialogInterface dialog, int which) -> {

                        addEntriesToDB();
                        finish();

                    });

            builder.show();

        }
    }

    private void initComponents() {
        dbh = new DbHelper(this);

        newEntryList = new ArrayList<>();
        logEntryList = dbh.getEntryList();

        progressDialog = new ProgressDialog(this);
    }

    public void displayFragment(Fragment fragment) {
        if (fragment != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_send:
                sendEntries();
                return true;
            case R.id.nav_save:
                addEntriesToDB();
                return true;
            case R.id.nav_profile:
                displayFragment(new ProfileFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendEntries() {
        newEntryList.clear();
        logEntryList.clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure? These will delete all entries.");
        builder.setMessage("Save entries to DB first");

        builder
                .setNegativeButton("CANCEL", (DialogInterface dialog, int which) -> {

                    finish();

                })
                .setPositiveButton("Yes", (DialogInterface dialog, int which) -> {
                    dbh.deleteAllEntries();
                });

        builder.show();
    }

    private void addEntriesToDB() {

        if (newEntryList.size() == 0) {
            Toast.makeText(this, "No Items to Add to Database", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setTitle("Adding entries ti SQLiteDB");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        for (LogEntry entry : newEntryList) {
            dbh.addEntry(entry);
        }

        progressDialog.dismiss();

        newEntryList.clear();
        logEntryList = dbh.getEntryList();
    }

    @Override
    public void saveEntry(LogEntry entry) {
        newEntryList.add(entry);
        logEntryList.add(entry);
    }

    @Override
    public void showLogEtryFrag(String breed) {
        displayFragment(ShowLogEntryFragment.newInstance(breed));
    }

    @Override
    public void displayHomeFrag() {
        displayFragment(new HomepageFragment());
    }

    @Override
    public void loadDataEntry(String breed) {
        displayFragment(DataEntryFragment.newInstance(breed));
    }

    @Override
    public void returnToDataEntry(String breed) {
        displayFragment(DataEntryFragment.newInstance(breed));
    }
}
