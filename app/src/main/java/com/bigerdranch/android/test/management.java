package com.bigerdranch.android.test;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigerdranch.android.test.dao.AesTest;
import com.bigerdranch.android.test.dao.JsonTodata;
import com.bigerdranch.android.test.dao.MyDao;
import com.bigerdranch.android.test.dao.PwdData;
import com.bigerdranch.android.test.setting.RandomPassword;
import com.bigerdranch.android.test.setting.Scan;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class management extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //表单数据
    private EditText pwd_editText_name;
    private EditText pwd_editText_pass;
    private EditText pwd_editText_username;
    private ImageView pwd_img;
    private Button pwd_button_submit;
    private Button pwd_button_createQr;
    private Button pwd_button_clean;
    private Button pwd_button_createPassword;
    private Button shareToDb_button;
    final MyDao myDao = new MyDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //获取控件,监控操作
        init_all();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init_all() {
        pwd_editText_name = (EditText) findViewById(R.id.pwd_editText_name);
        pwd_editText_pass = (EditText) findViewById(R.id.pwd_editText_pass);
        pwd_editText_username = (EditText) findViewById(R.id.pwd_editText_username);
//        pwd_img = (ImageView) findViewById(R.id.pwd_img);
        pwd_button_submit = (Button) findViewById(R.id.pwd_button_submit);
//        pwd_button_createQr = (Button) findViewById(R.id.pwd_button_createQr);
        pwd_button_clean = (Button) findViewById(R.id.pwd_button_clean);
        pwd_button_createPassword = (Button) findViewById(R.id.pwd_button_createPassword);
        shareToDb_button = (Button) findViewById(R.id.ShareToDb_button);
        //数据库对象
        Cursor cursor = null;//存储查询对象

        //自定义数据库帮助类
        if (cursor != null) {
            stopManagingCursor(cursor);
            cursor.close();
        }
        pwd_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = pwd_editText_name.getText().toString();
                String pass = pwd_editText_pass.getText().toString();
                String user = pwd_editText_username.getText().toString();
                if (name.equals("") && pass.equals("") && user.equals("")) {
                    Toast.makeText(management.this, "名称,用户名,密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(management.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    PwdData pwdData = new PwdData();
                    pwdData.setName(name);
                    pwdData.setUsername(user);
                    pwdData.setPassword(pass);
                    myDao.insert(pwdData);
                    Toast.makeText(management.this, "名称,用户名,密码写入数据库", Toast.LENGTH_SHORT).show();
                }

            }
        });
//生成二维码
//        pwd_button_createQr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = pwd_editText_name.getText().toString();
//                String pass = pwd_editText_pass.getText().toString();
//                String user = pwd_editText_username.getText().toString();
//                if (name.equals("") && pass.equals("") && user.equals("")) {
//                    Toast.makeText(management.this, "名称,用户名,密码不能为空", Toast.LENGTH_SHORT).show();
//                } else if (pass.equals("")) {
//                    Toast.makeText(management.this, "密码不能为空", Toast.LENGTH_SHORT).show();
//                } else {
//                    PwdData pwdData = new PwdData();
//                    pwdData.setName(name);
//                    pwdData.setUsername(user);
//                    pwdData.setPassword(pass);
//                    String str = pwdData.toString();
//                    Bitmap bitmap = qrCreat.createQRCodeBitmap(str, 480, 480);
//                    pwd_img.setImageBitmap(bitmap);
//                }
//            }
//        });

        pwd_button_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd_editText_name.setText("");
                pwd_editText_pass.setText("");
            }
        });

        pwd_button_createPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog builder = new AlertDialog.Builder(management.this).create();
                builder.show();
                Window window = builder.getWindow();
                window.setContentView(R.layout.create_password);
                final SeekBar seekBar = (SeekBar) window.findViewById(R.id.seekBar);
                final CheckBox checkBox_upperCase = (CheckBox) window.findViewById(R.id.checkBox_uppercase);
                final CheckBox checkBox_lowerCase = (CheckBox) window.findViewById(R.id.checkBox_lowercase);
                final CheckBox checkBox_digital = (CheckBox) window.findViewById(R.id.checkBox_digital);
                final CheckBox checkBox_specialChar = (CheckBox) window.findViewById(R.id.checkBox_specialchar);
                final TextView length = (TextView)window.findViewById(R.id.length);
                Button button_determine = (Button) window.findViewById(R.id.button_determine);
                Button button_cancal = (Button) window.findViewById(R.id.button_cancal);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        length.setText(seekBar.getProgress()+"");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                //确定键设值
                button_determine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String string = "";
                        if (checkBox_upperCase.isChecked()) {
                            string += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                        }
                        if (checkBox_lowerCase.isChecked()) {
                            string += "abcdefghijklmnopqrstuvwxyz";
                        }
                        if (checkBox_digital.isChecked()) {
                            string += "1234567890";
                        }
                        if (checkBox_specialChar.isChecked()) {
                            string += "~!@#$%^&*.?";
                        }
                        int PasswordLenth = seekBar.getProgress();
                        //密码生成
                        RandomPassword randomPassword = new RandomPassword();
                        String password = randomPassword.makeRandomPassword(PasswordLenth,string);
                        //显示密码
                        pwd_editText_pass.setText(password);
                        builder.cancel();//退出
                    }
                });
                //取消键设值
                button_cancal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.cancel();//退出
                    }
                });
            }
        });

        shareToDb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(management.this);
                intentIntegrator.setOrientationLocked(true)
                        .setCaptureActivity(Scan.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
            }
        });
    }

    //二维码扫描
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                String password = "1234567890ABCDEF";
                String string = result.getContents();
                try {
                    byte[] decryResult = AesTest.decrypt(AesTest.str2Byte(string), password);
                    string = new String(decryResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                JsonTodata jsonTodata = gson.fromJson(string, JsonTodata.class);
                PwdData pwdData = new PwdData(jsonTodata);
                myDao.insert(pwdData);
                Toast.makeText(management.this, "名称,用户名,密码写入数据库", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.management, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = new Intent();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent.setClass(management.this, settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent();
        if (id == R.id.qr_code) {
            // Handle the camera action
            intent.setClass(management.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.password) {
            intent.setClass(management.this, password.class);
            startActivity(intent);
        } else if (id == R.id.management) {
            intent.setClass(management.this, management.class);
            startActivity(intent);
        } else if (id == R.id.other) {
            intent.setClass(management.this, other.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
