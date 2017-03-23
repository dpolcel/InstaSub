package br.com.polcel.instasub;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditSubActivity extends AppCompatActivity {

    EditText mEdtLegenda;
    final String LINE_BREAK_CHAR = "⠀⠀⠀";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub);

        Toolbar editSubToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(editSubToolbar);

        ActionBar editSubActionBar = getSupportActionBar();
        editSubActionBar.setDisplayHomeAsUpEnabled(true);

        mEdtLegenda = (EditText) findViewById(R.id.edit_sub_edtLegenda);
        mEdtLegenda.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    //Toast.makeText(getApplicationContext(), "ENTER", Toast.LENGTH_SHORT).show();

                    String current = mEdtLegenda.getText().toString();

                    current += LINE_BREAK_CHAR;

                    mEdtLegenda.setText(current);
                    mEdtLegenda.setSelection(mEdtLegenda.getText().length());
                }
                return false;
            }
        });

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

        if (id == R.id.action_save) {
            Toast.makeText(getApplicationContext(), R.string.saved_sucessfuly, Toast.LENGTH_SHORT).show();

            onBackPressed();
            return true;
        }

        if (id == android.R.id.home) {
            String legenda = mEdtLegenda.getText().toString();
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

    private void showConfirmDialog() {
        //melhorar. usar tema posteriormente
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
    }
}
