package br.com.polcel.instasub;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class EditSubActivity extends AppCompatActivity {

    EditText mEdtLegenda;
    final String LINE_BREAK_CHAR = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub);

        mEdtLegenda = (EditText) findViewById(R.id.edit_sub_edtLegenda);
        mEdtLegenda.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){

                    Toast.makeText(getApplicationContext(), "ENTER", Toast.LENGTH_SHORT).show();
//
                    String current = mEdtLegenda.getText().toString();

                    current += "AEHO";

                    mEdtLegenda.setText(current);
                    mEdtLegenda.setSelection(mEdtLegenda.getText().length());

                    //return false;
                }
                return false;
            }
        });

//        mEdtLegenda.addTextChangedListener(new TextWatcher() {
//
//            int len=0;
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                String str = mEdtLegenda.getText().toString();
//                if(str.length()==4&& len <str.length()){//len check for backspace
//                    mEdtLegenda.append("-");
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                Log.i("Text changed! ", "Enter pressed");
////                Toast.makeText(getApplicationContext(), "Enter pressed!", Toast.LENGTH_SHORT).show();
//
//
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String str = mEdtLegenda.getText().toString();
//                if(str.length()==4&& len <str.length()){//len check for backspace
//                    mEdtLegenda.append("-");
//                }
//            }
//        });
//
//        mEdtLegenda.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                String key = String.format(Locale.getDefault(), "Keycode: %s", keyCode);
//
//                if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                    Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
//
//                    String current = mEdtLegenda.getText().toString();
//
//                    current += "AEHO";
//
//                    mEdtLegenda.setText(current);
//                    mEdtLegenda.setSelection(mEdtLegenda.getText().length());
//
//                    return true;
//                }
//                return false;
//            }
//        });
    }
}
