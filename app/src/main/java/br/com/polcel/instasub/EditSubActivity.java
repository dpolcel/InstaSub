package br.com.polcel.instasub;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Locale;

import br.com.polcel.instasub.contracts.InstaSubContract;
import br.com.polcel.instasub.helpers.InstaSubDbHelper;
import br.com.polcel.instasub.models.SubtitleModel;
import br.com.polcel.instasub.utils.Tools;

public class EditSubActivity extends AppCompatActivity {

    EditText mEdtLegenda;
    EditText mEdtTitle;
    TextView mTvAction;
    InstaSubDbHelper mInstaSubDbHelper;
    long mSubtitleId = 0;
    FloatingActionButton mActionOpenInstagram;
    FloatingActionButton mActionCopyContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subtitle);

        Toolbar editSubToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(editSubToolbar);

        ActionBar editSubActionBar = getSupportActionBar();
        editSubActionBar.setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        mEdtTitle = (EditText) findViewById(R.id.activity_edit_sub_et_title);

        mEdtLegenda = (EditText) findViewById(R.id.activity_edit_sub_et_subtitle);
        mEdtLegenda.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    int cursor = mEdtLegenda.getSelectionStart();
                    mEdtLegenda.getText().insert(cursor, Tools.LINE_BREAK_CHAR);
                }
                return false;
            }
        });


        mActionOpenInstagram = (FloatingActionButton) findViewById(R.id.activity_edit_sub_fab_open_instagram);
        mActionOpenInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(Tools.INSTAGRAM_PACKAGE_NAME);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            }
        });

        mActionCopyContent = (FloatingActionButton) findViewById(R.id.activity_edit_sub_fab_copy_content);
        mActionCopyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.action_content_copied, Toast.LENGTH_SHORT).show();

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(Tools.CLIPBOARD_PARAMETER_NAME, mEdtLegenda.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });

        mInstaSubDbHelper = new InstaSubDbHelper(getApplicationContext());

        Intent intent = getIntent();
        mSubtitleId = intent.getLongExtra(Tools.SUBTITLE_INTENT_PARAMETER, 0);
        String actionDescription = getString(R.string.activity_edit_create);
        SubtitleModel subtitle = new SubtitleModel();

        if (mSubtitleId > 0) {
            actionDescription = getString(R.string.activity_edit_edit);
            SQLiteDatabase db = mInstaSubDbHelper.getReadableDatabase();

            String[] projection = {
                    InstaSubContract.InstaSub._ID,
                    InstaSubContract.InstaSub.COLUMN_NAME_TITLE,
                    InstaSubContract.InstaSub.COLUMN_NAME_DESCRIPTION,
                    InstaSubContract.InstaSub.COLUMN_NAME_CREATED
            };

            String selection = InstaSubContract.InstaSub.COLUMN_NAME_ID + " = ?";
            String[] selectionArgs = {String.valueOf(mSubtitleId)};

            Cursor cursor = db.query(InstaSubContract.InstaSub.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                subtitle.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(InstaSubContract.InstaSub.COLUMN_NAME_TITLE)));
                subtitle.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(InstaSubContract.InstaSub.COLUMN_NAME_DESCRIPTION)));

                mEdtTitle.setText(subtitle.getTitle());
                mEdtLegenda.setText(subtitle.getDescription());
            }
        }

        mTvAction = (TextView) findViewById(R.id.activity_edit_sub_tv_action);
        mTvAction.setText(actionDescription);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String legenda = mEdtLegenda.getText().toString();
            if (!legenda.isEmpty()) {
                showConfirmDialog();
                return false;
            } else {
                onBackPressed();
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String legenda = mEdtLegenda.getText().toString();
        String titulo = mEdtTitle.getText().toString();

        if (id == R.id.action_save) {
            if (!legenda.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.saved_sucessfuly, Toast.LENGTH_SHORT).show();
                saveSubtitle(legenda, titulo);
            }

            finish();
            return true;
        }

        if (id == android.R.id.home) {
            if (!legenda.isEmpty()) {
                showConfirmDialog();
                return true;
            } else {
                //Toast.makeText(getApplicationContext(), "Não tinha texto. Voltando! - onoptionselected", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveSubtitle(String legenda, String titulo) {

        SQLiteDatabase db = (mSubtitleId > 0) ? mInstaSubDbHelper.getReadableDatabase() : mInstaSubDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(InstaSubContract.InstaSub.COLUMN_NAME_TITLE, titulo);
        values.put(InstaSubContract.InstaSub.COLUMN_NAME_DESCRIPTION, legenda);

        Calendar calendar = Calendar.getInstance();
        long date = calendar.getTimeInMillis();

        values.put(InstaSubContract.InstaSub.COLUMN_NAME_CREATED, date);

        try {
            if (mSubtitleId > 0) {

                String selection = InstaSubContract.InstaSub.COLUMN_NAME_ID + " = ?";
                String[] selectionArgs = {String.valueOf(mSubtitleId)};

                int count = db.update(InstaSubContract.InstaSub.TABLE_NAME, values, selection, selectionArgs);

                mSubtitleId = 0;
            } else {
                long newRowId = db.insertOrThrow(InstaSubContract.InstaSub.TABLE_NAME, null, values);
            }
        } catch (SQLiteException ex) {
            Log.i(Tools.LOG_TAG, ex.getMessage());
        }

    }

    private void showConfirmDialog() {
        //TODO: melhorar. usar tema posteriormente
        if (mSubtitleId == 0) {
            AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(this);
            confirmDialogBuilder
                    .setTitle(R.string.message_modifications_cancel_title)
                    .setMessage(R.string.message_modifications_cancel);

            confirmDialogBuilder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            confirmDialogBuilder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            confirmDialogBuilder.create();
            confirmDialogBuilder.show();
        } else {
            finish();
        }
    }
}
