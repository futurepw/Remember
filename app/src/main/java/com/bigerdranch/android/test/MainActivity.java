package com.bigerdranch.android.test;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigerdranch.android.test.dao.DatabaseHelper;
import com.bigerdranch.android.test.dao.MyDao;
import com.bigerdranch.android.test.setting.Scan;
import com.bigerdranch.android.test.setting.qrCreat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.*;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv_content;
    private EditText et_input;
    private ImageView img;
    private Button btnSan;
    private Button btn_generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //二维码，初始化监控
       init_all();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //初始化
    private void init_all(){
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        databaseHelper.getWritableDatabase().query("pwdinf",null,null,null,null,null,null);
        tv_content = (TextView) findViewById(R.id.tv_content);
        et_input = (EditText) findViewById(R.id.et_input);
        img = (ImageView) findViewById(R.id.img);
        btnSan = (Button) findViewById(R.id.btnSan);
        btn_generate = (Button) findViewById(R.id.btn_generate);
        btnSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setOrientationLocked(true)
                        .setCaptureActivity(Scan.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
            }
        });
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = et_input.getText().toString();//输入的内容
                if (str.equals("")) {
                    Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Bitmap bitmap = qrCreat.createQRCodeBitmap(str,480,480);
                    img.setImageBitmap(bitmap);
                }
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
                tv_content.setText(result.getContents());
                tv_content.setHeight(20);
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
        getMenuInflater().inflate(R.menu.main, menu);
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
            intent.setClass(MainActivity.this, settings.class);
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
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.password) {
            intent.setClass(MainActivity.this, password.class);
            startActivity(intent);
        } else if (id == R.id.management) {
            intent.setClass(MainActivity.this, management.class);
            startActivity(intent);
        } else if (id == R.id.other) {
            intent.setClass(MainActivity.this, other.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
