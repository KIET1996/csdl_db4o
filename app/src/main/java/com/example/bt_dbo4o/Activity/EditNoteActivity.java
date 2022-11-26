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

public class EditNoteActivity extends AppCompatActivity {
    EditText title, content;
    Button btnEditSave, btnEditDelete;
    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sua_ghi_chu);

        Intent intent = getIntent();
        String u_username = intent.getStringExtra("e_username");
        String u_title = intent.getStringExtra("e_title");
        String u_content = intent.getStringExtra("e_content");
        String u_ID = intent.getStringExtra("e_id");
        int u_noteID = Integer.parseInt(u_ID);
        title = findViewById(R.id.txtEditTieude);
        content = findViewById(R.id.txtEditNoidung);
        txt2 = findViewById(R.id.txtEditError);
        btnEditSave = findViewById(R.id.btnEdit);
        btnEditDelete = findViewById(R.id.btnDelete);

        title.setText(u_title);
        content.setText(u_content);

        btnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt2.setText("");
                if(title.getText().toString().isEmpty() ||
                        content.getText().toString().isEmpty()) {
                    txt2.setText("Không được để trống các trường");
                }else{
                    String dbPath =  "/data/data/" + getPackageName() + "/Db4oDatabase.db4o";
                    DbHelper data = new DbHelper(dbPath);
                    Log.e("DEBUG", "===================>" + u_ID);
                    boolean isEditNote = data.editNote(u_noteID, title.getText().toString(), content.getText().toString());
                    Toast.makeText(EditNoteActivity.this, "Chỉnh sửa ghi chú thành công" ,
                            Toast.LENGTH_SHORT).show();
                    data.closeDB();
                    if(!isEditNote){
                        txt2.setText("Chỉnh sửa ghi chú không thành công");
                    } else {
                        title.setText("");
                        content.setText("");
                        Intent intent = new Intent(EditNoteActivity.this, HomeActivity.class);
                        intent.putExtra("u_username", u_username);
                        startActivity(intent);
                    }
                }
            }
        });

        btnEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt2.setText("");
                Log.e("DEBUG  ", "================>" + u_noteID);
                String dbPath =  "/data/data/" + getPackageName() + "/Db4oDatabase.db4o";
                DbHelper data = new DbHelper(dbPath);
                boolean isDeleteNote = data.deleteNote(u_noteID);
                Toast.makeText(EditNoteActivity.this, "Xóa ghi chú thành công" , Toast.LENGTH_SHORT).show();
                data.closeDB();
                if(!isDeleteNote){
                    txt2.setText("Xóa ghi chú không thành công");
                } else {
                    title.setText("");
                    content.setText("");
                    Intent intent = new Intent(EditNoteActivity.this, HomeActivity.class);
                    intent.putExtra("u_username", u_username);
                    startActivity(intent);
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
