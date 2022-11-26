package com.example.bt_dbo4o;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bt_dbo4o.Activity.HomeActivity;
import com.example.bt_dbo4o.Activity.RegisterActivity;
import com.example.bt_dbo4o.Model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText username , password;
    TextView txt2, b2;
    List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_nhap);

        // try block to hide Action bar
        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException e) {
        }

        username = (EditText)findViewById(R.id.txtUsername);
        password = (EditText)findViewById(R.id.txtPassword);
        txt2 = (TextView) findViewById(R.id.lbLoi);

        b1 = (Button)findViewById(R.id.btnDangnhap);
        b2 = (TextView)findViewById(R.id.btnDangky);

        String dbPath =  "/data/data/" + getPackageName() + "/Db4oDatabase.db4o";
        DbHelper data =new DbHelper(dbPath);
//        data.DBDrop(dbPath);
        userList=data.getAllUser();
        for (User u: userList) {
            Log.e("List user in system", "User====>" + u.getUSERNAME() + "- TTT -" + u.getFULLNAME());
            Log.e("List user in system", "User====>" + u.getNotes());
        };
        String u_username = data.checkLogin();
        data.closeDB();

        if (!u_username.isEmpty()){
            Intent intent0 = new Intent(MainActivity.this, HomeActivity.class);
            intent0.putExtra("u_username", u_username);
            startActivity(intent0);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty()) {
                        txt2.setText("Không được để trống các trường");
                }else{
                    String dbPath =  "/data/data/" + getPackageName() + "/Db4oDatabase.db4o";
                    DbHelper data = new DbHelper(dbPath);
                    String isLogin = data.login(username.getText().toString(), password.getText().toString());
                    data.closeDB();

                    if(isLogin.isEmpty()){
                        txt2.setText("Đăng nhập không thành công bạn đã sai mật khẩu hoặc tài khoản");
                    } else {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("u_username", isLogin);
                        startActivity(intent);
                    }

                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}