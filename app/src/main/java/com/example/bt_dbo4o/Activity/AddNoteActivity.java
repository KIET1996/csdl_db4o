package com.example.bt_dbo4o.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bt_dbo4o.DbHelper;
import com.example.bt_dbo4o.R;


public class AddNoteActivity extends AppCompatActivity {
    EditText title, content;
    Button btnAddNote;
    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_ghi_chu);
        title = findViewById(R.id.txtAddTieude);
        content = findViewById(R.id.txtAddNoidung);
        btnAddNote = findViewById(R.id.btnAddNote);
        txt2 = findViewById(R.id.lbAddError);

        Intent intent = getIntent();
        String u_username = intent.getStringExtra("u_username");
        String u_id = intent.getStringExtra("u_id");
        int noteID = Integer.parseInt(u_id);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt2.setText("");
                if(title.getText().toString().isEmpty() ||
                        content.getText().toString().isEmpty()) {
                    txt2.setText("Không được để trống các trường");
                }else{
                    String dbPath =  "/data/data/" + getPackageName() + "/Db4oDatabase.db4o";
                    DbHelper data = new DbHelper(dbPath);
                    Log.e("DEBUG", "========================>"+noteID);
                    boolean isAddNote = data.addNote(u_username, noteID, title.getText().toString(), content.getText().toString());
                    data.closeDB();
                    Toast.makeText(AddNoteActivity.this, "Thêm ghi chú thành công" ,
                            Toast.LENGTH_SHORT).show();
                    if(!isAddNote){
                        txt2.setText("Thêm ghi chú không thành công");
                    } else {
                        title.setText("");
                        content.setText("");
                        Intent intent = new Intent(AddNoteActivity.this, HomeActivity.class);
                        intent.putExtra("u_username", u_username);
                        startActivity(intent);
                    }
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
