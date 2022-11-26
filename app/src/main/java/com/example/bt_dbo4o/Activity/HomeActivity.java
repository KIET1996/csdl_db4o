package com.example.bt_dbo4o.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.bt_dbo4o.DbHelper;
import com.example.bt_dbo4o.MainActivity;
import com.example.bt_dbo4o.Model.Note;
import com.example.bt_dbo4o.R;

import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<Note> noteList;
    NoteAdapter noteAdapter;
    ListView listViewNote;
    Button btnAdd;
    List<Note> noteList2;
    String u_username;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_ghi_chu);


        Intent intent = getIntent();
        u_username = intent.getStringExtra("u_username");

        String dbPath =  "/data/data/" + getPackageName() + "/Db4oDatabase.db4o";
        DbHelper data =new DbHelper(dbPath);
        noteList = data.getAllNote(u_username);
        noteList2 = data.getTEST();
        int u_id = noteList.size() - 1;
        Log.e("List note in system", "List all home 2====>b " + noteList2.size());
        for (Note n: noteList) {
            Log.e("List note in system", "n====>" + n.getID());
        };

        data.closeDB();

        btnAdd = findViewById(R.id.btnAdd);
        listViewNote = findViewById(R.id.dsGhichu);
        Collections.reverse(noteList);
        noteAdapter = new NoteAdapter(noteList);
        listViewNote.setAdapter(noteAdapter);

        //Lắng nghe bắt sự kiện một phần tử danh sách được chọn
        listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) noteAdapter.getItem(position);
                Log.e("List note in system", "n====>" + note.getCONTENT());
                Intent intent1 = new Intent(HomeActivity.this, EditNoteActivity.class);
                intent1.putExtra("e_username", u_username);
                intent1.putExtra("e_id", String.valueOf(note.getID()));
                intent1.putExtra("e_title", note.getTITLE());
                intent1.putExtra("e_content", note.getCONTENT());
                intent1.putExtra("e_date", note.CREATEDATE);
                startActivity(intent1);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HomeActivity.this, AddNoteActivity.class);
                intent2.putExtra("u_username", u_username);
                intent2.putExtra("u_id", String.valueOf(u_id));
                startActivity(intent2);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.logout:
            String dbPath =  "/data/data/" + getPackageName() + "/Db4oDatabase.db4o";
            DbHelper data =new DbHelper(dbPath);
            boolean isLogout = data.logout(u_username);
            data.closeDB();
            if(isLogout) {
                Intent intent3 = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent3);
                return (true);
            }
    }
        return(super.onOptionsItemSelected(item));
    }
}
