package com.example.bt_dbo4o.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bt_dbo4o.DbHelper;
import com.example.bt_dbo4o.MainActivity;
import com.example.bt_dbo4o.R;

public class RegisterActivity extends AppCompatActivity {
    EditText name, username, password;
    TextView error, b2;
    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky);
        name = (EditText)findViewById(R.id.txtAddName);
        username = (EditText)findViewById(R.id.txtAddUsername);
        password = (EditText)findViewById(R.id.txtAddPassword);
        error = (TextView) findViewById(R.id.lbAddLoi);
        b1 = (Button)findViewById(R.id.btnAddDangky);
        b2 = (TextView) findViewById(R.id.btnAddDangnhap);
        // try block to hide Action bar
        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                error.setText("");
                if(name.getText().toString().isEmpty() ||
                        username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    error.setText("Không được để trống các trường");
                }else{
                    // Create the DB:
                    String dbPath =  "/data/data/" + getPackageName() + "/Db4oDatabase.db4o";
                    DbHelper data = new DbHelper(dbPath);

                    boolean isExistUser = data.findUser(username.getText().toString());
                    if(isExistUser){
                        data.addUser(name.getText().toString(), username.getText().toString(), password.getText().toString());
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công",
                                Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        name.setText("");
                    }
                    else{
                        error.setText("Đăng ký không thành công, tài khoản đã được đăng ký");
                    }
                    data.closeDB();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
