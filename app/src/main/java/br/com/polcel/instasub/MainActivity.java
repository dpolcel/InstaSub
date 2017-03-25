package br.com.polcel.instasub;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.polcel.instasub.adapters.InstaSubsRecyclerViewCursorAdapter;
import br.com.polcel.instasub.contracts.InstaSubContract;
import br.com.polcel.instasub.helpers.InstaSubDbHelper;
import br.com.polcel.instasub.models.Subtitle;

public class MainActivity extends AppCompatActivity {
    InstaSubDbHelper mInstaSubDbHelper;
    ArrayList<Subtitle>  mResults;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditSubActivity.class);
                startActivity(intent);
            }
        });
        mInstaSubDbHelper = new InstaSubDbHelper(getApplicationContext());
        mResults = new ArrayList<Subtitle>();

        readSubsDb();
    }

    public void readSubsDb() {
        SQLiteDatabase db = mInstaSubDbHelper.getWritableDatabase();

        String[] projection = {InstaSubContract.InstaSub._ID,
                InstaSubContract.InstaSub.COLUMN_NAME_TITLE,
                InstaSubContract.InstaSub.COLUMN_NAME_DESCRIPTION
        };

        String selection = InstaSubContract.InstaSub.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {"Title"};

        String sortOrder = InstaSubContract.InstaSub.COLUMN_NAME_TITLE + " DESC";

        Cursor cursor = db.query(InstaSubContract.InstaSub.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {

            do {
                Long id = cursor.getLong(cursor.getColumnIndexOrThrow(InstaSubContract.InstaSub._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(InstaSubContract.InstaSub.COLUMN_NAME_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(InstaSubContract.InstaSub.COLUMN_NAME_DESCRIPTION));
                //Long created = cursor.getLong(cursor.getColumnIndexOrThrow(InstaSubContract.InstaSub.COLUMN_NAME_CREATED));

                Subtitle subtitle = new Subtitle();
                subtitle.setId(id);
                subtitle.setTitle(title);
                subtitle.setDescription(description);

                mResults.add(subtitle);

            }
            while (cursor.moveToNext());
        }

        InstaSubsRecyclerViewCursorAdapter adapter = new InstaSubsRecyclerViewCursorAdapter(getApplicationContext(), mResults);

        mRecyclerView = (RecyclerView)findViewById(R.id.rvSubtitles);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        long itemId = cursor.getLong(
//                cursor.getColumnIndexOrThrow(InstaSubContract.InstaSub._ID)
//        );

    }

    @Override
    protected void onResume() {
        super.onResume();

        readSubsDb();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_save) {
//            Toast.makeText(getApplicationContext(), "Legenda salva com sucesso!", Toast.LENGTH_LONG).show();
//
//            onBackPressed();
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
