package br.com.polcel.instasub;

import android.support.v7.app.ActionBar;
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
    final String LINE_BREAK_CHAR = "";

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

                    Toast.makeText(getApplicationContext(), "ENTER", Toast.LENGTH_SHORT).show();

                    String current = mEdtLegenda.getText().toString();

                    current += "AEHO";

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
                Toast.makeText(getApplicationContext(), "Tinha texto! - onBackPressed", Toast.LENGTH_LONG).show();
                return false;
            } else {
                Toast.makeText(getApplicationContext(), "Não tinha texto. Voltando! - onBackPressed", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(), "Legenda salva com sucesso!", Toast.LENGTH_SHORT).show();

            onBackPressed();
            return true;
        }

        if (id == android.R.id.home) {
            //alert perguntando se tem certeza que deseja descartar o texto
            String legenda = mEdtLegenda.getText().toString();
            if (!legenda.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Tinha texto! - onoptionselected", Toast.LENGTH_LONG).show();
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Não tinha texto. Voltando! - onoptionselected", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
