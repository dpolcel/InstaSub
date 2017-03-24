package br.com.polcel.instasub;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    InstaSubDbHelper mInstaSubDbHelper;

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

        readSubsDb();
    }

    public void readSubsDb() {
        SQLiteDatabase db = mInstaSubDbHelper.getReadableDatabase();

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

        cursor.moveToFirst();
        long itemId = cursor.getLong(
                cursor.getColumnIndexOrThrow(InstaSubContract.InstaSub._ID)
        );

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
