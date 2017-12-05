package com.bigerdranch.android.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigerdranch.android.test.dao.AesTest;
import com.bigerdranch.android.test.dao.MyDao;
import com.bigerdranch.android.test.dao.PwdData;
import com.bigerdranch.android.test.setting.SwipeListView;
import com.bigerdranch.android.test.setting.qrCreat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class password extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<PwdData> pwdDatasList = new ArrayList<PwdData>();//所有列表数据
    private Set<SwipeListView> sets = new HashSet();
    MyDao myDao = new MyDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //初始化操作
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
        ListView listView = (ListView) findViewById(R.id.password_listView);
        try {
            Cursor cursor = myDao.getAll();
            while (cursor.moveToNext()) {
                String id = myDao.getId(cursor);
                String name = myDao.getName(cursor);
                String username = myDao.getUserName(cursor);
                String password = myDao.getPassword(cursor);
                PwdData pwdData = new PwdData(id, name, username, password);
                pwdDatasList.add(pwdData);
            }
        } catch (SQLException e) {

        }

        if (pwdDatasList.isEmpty()) {
//            LinearLayout password_LinerLayout = (LinearLayout) findViewById(R.id.password_listView);
//            TextView textViewEmptyShow = new TextView(this);
//            textViewEmptyShow.setTextSize(24);
//            textViewEmptyShow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            textViewEmptyShow.setText("暂无数据，请添加");
//            password_LinerLayout.addView(textViewEmptyShow);
//            listView.addView(textViewEmptyShow);
        } else {
            //显示
            listView.setAdapter(new myAdapter());
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState) {
                        //当listview开始滑动时，若有item的状态为Open，则Close，然后移除
                        case SCROLL_STATE_TOUCH_SCROLL:
                            if (sets.size() > 0) {
                                for (SwipeListView s : sets) {
                                    s.setStatus(SwipeListView.Status.Close, true);
                                    sets.remove(s);
                                }
                            }
                            break;
                    }
                }
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {

                }
            });
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Toast.makeText(password.this,i,Toast.LENGTH_LONG).show();
//                }
//            });
 /*           for (PwdData p : pwdDatasList){
                TextView textViewNotEmptyShow = new TextView(this);
                textViewNotEmptyShow.setTextSize(36);
                textViewNotEmptyShow.setText("名称：  "+p.getName());
                LinearLayout linearLayoutNotEmptyShow = new LinearLayout(this);
                linearLayoutNotEmptyShow.addView(textViewNotEmptyShow);
                password_LinerLayout.addView(linearLayoutNotEmptyShow);
            }
 */
        }
    }

    class MyOnSlipStatusListener implements SwipeListView.OnSwipeStatusListener {

        private SwipeListView slipListLayout;

        public MyOnSlipStatusListener(SwipeListView slipListLayout) {
            this.slipListLayout = slipListLayout;
        }

        @Override
        public void onStatusChanged(SwipeListView.Status status) {
            if (status == SwipeListView.Status.Open) {
                //若有其他的item的状态为Open，则Close，然后移除
                if (sets.size() > 0) {
                    for (SwipeListView s : sets) {
                        s.setStatus(SwipeListView.Status.Close, true);
                        sets.remove(s);
                    }
                }
                sets.add(slipListLayout);
            } else {
                if (sets.contains(slipListLayout))
                    sets.remove(slipListLayout);
            }
        }

        @Override
        public void onStartCloseAnimation() {

        }

        @Override
        public void onStartOpenAnimation() {

        }

    }

    //适配器类
    class myAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return pwdDatasList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return pwdDatasList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int arg0, View view, ViewGroup arg2) {
            if (view == null) {
                view = LayoutInflater.from(password.this).inflate(
                        R.layout.slip_item_layout, null);
            }
            //listView显示
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_username = (TextView) view.findViewById(R.id.tv_username);
//            TextView tv_password = (TextView) view.findViewById(R.id.tv_password);
            tv_name.setText(pwdDatasList.get(arg0).getName());
            tv_username.setText(pwdDatasList.get(arg0).getUsername());
//            tv_password.setText(pwdDatasList.get(arg0).getPassword());
            final SwipeListView sll_main = (SwipeListView) view
                    .findViewById(R.id.sll_main);
            TextView tv_edit = (TextView) view.findViewById(R.id.tv_edit);
            TextView tv_share = (TextView) view.findViewById(R.id.tv_share);
            TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            TextView tv_see = (TextView) view.findViewById(R.id.tv_see);
            sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(
                    sll_main));

            //侧滑查看监控
            tv_see.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    sll_main.setStatus(SwipeListView.Status.Close, true);
                    final PwdData pwdData = pwdDatasList.get(arg0);

                    final AlertDialog builder = new AlertDialog.Builder(password.this).create();
                    builder.show();
                    Window window = builder.getWindow();
                    window.setContentView(R.layout.password_show);
                    TextView passwordShow_name = (TextView) window.findViewById(R.id.passwordShow_name);
                    TextView passwordShow_username = (TextView) window.findViewById(R.id.passwordShow_username);
                    final TextView passwordShow_password = (TextView) window.findViewById(R.id.passwordShow_password);
                    final CheckBox password_passwordShow = (CheckBox) window.findViewById(R.id.password_passwordShow);
                    Button passwordShow_cancal = (Button) window.findViewById(R.id.passwordShow_cancal);
                    //页面设值
                    final String space = "     ";
                    passwordShow_name.setText(space + pwdData.getName());
                    passwordShow_username.setText(space + pwdData.getUsername());
                    passwordShow_password.setText(space + "************");
                    password_passwordShow.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            // 通过这个方法，来监听当前的checkbox是否被选中
                            if (isChecked) {
                                passwordShow_password.setText(space + pwdData.getPassword());
                            } else {
                                passwordShow_password.setText(space + "************");
                            }
                        }
                    });
                    //取消键退出
                    passwordShow_cancal.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            notifyDataSetChanged();
                            builder.cancel();
                        }
                    });
//                    notifyDataSetChanged();
                }

            });

            //侧滑分享监控
            tv_share.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //AlertDialog标题设置
                    TextView title = new TextView(password.this);
                    title.setText("二维码分享密码");
                    title.setPadding(10, 10, 10, 10);
                    title.setGravity(Gravity.CENTER);
                    // title.setTextColor(getResources().getColor(R.color.greenBG));
                    title.setTextSize(23);

                    sll_main.setStatus(SwipeListView.Status.Close, true);
                    PwdData pwdData = pwdDatasList.get(arg0);
                    String str = pwdData.toString();

                    //加密字符串
                    String password = "1234567890ABCDEF";
                    AesTest aesTest = new AesTest();
                    try {
                        byte[] result = AesTest.encrypt(str.getBytes(), password);
                        str = AesTest.byte2String(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = qrCreat.createQRCodeBitmap(str, 480, 480);
                    ImageView imageView = new ImageView(password.this);
                    imageView.setImageBitmap(bitmap);
                    AlertDialog.Builder builder = new AlertDialog.Builder(password.this);
                    builder.setCustomTitle(title)
                            .setView(imageView)
                            .setNegativeButton("取消", null)
                            .show();
                    notifyDataSetChanged();
                }
            });
            //侧滑置顶监控
//            tv_Top.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    sll_main.setStatus(SwipeListView.Status.Close, true);
//                    PwdData str = pwdDatasList.get(arg0);
//                    pwdDatasList.remove(arg0);
//                    pwdDatasList.add(0, str);
//                    notifyDataSetChanged();
//                }
//            });
            //侧滑编辑监控
            tv_edit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    sll_main.setStatus(SwipeListView.Status.Close, true);
                    final PwdData pwdData = pwdDatasList.get(arg0);
                    //设置AlertDialog样式，自定义
                    final AlertDialog builder = new AlertDialog.Builder(password.this).create();
                    builder.show();
                    Window window = builder.getWindow();
                    window.setContentView(R.layout.edit_password);
                    final EditText edit_editText_name = (EditText) window.findViewById(R.id.edit_editText_name);
                    final EditText edit_editText_username = (EditText) window.findViewById(R.id.edit_editText_username);
                    final EditText edit_editText_password = (EditText) window.findViewById(R.id.edit_editText_password);
                    Button edit_button_submit = (Button) window.findViewById(R.id.edit_button_submit);
                    Button edit_button_cancal = (Button) window.findViewById(R.id.edit_button_cancal);
                    //提示框填值
                    edit_editText_name.setText(pwdData.getName());
                    edit_editText_username.setText(pwdData.getUsername());
                    edit_editText_password.setText(pwdData.getPassword());
                    edit_button_submit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String name = edit_editText_name.getText().toString();
                            String username = edit_editText_username.getText().toString();
                            String password = edit_editText_password.getText().toString();
                            pwdData.setName(name);
                            pwdData.setUsername(username);
                            pwdData.setPassword(password);
                            myDao.update(pwdData, pwdData.getId());
                            notifyDataSetChanged();
                            builder.cancel();
                        }
                    });
                    edit_button_cancal.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            builder.cancel();//退出AlertDialo
                        }
                    });
                }
            });
            //侧滑删除监控
            tv_delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //AlertDialog标题设置
                    TextView title = new TextView(password.this);
                    title.setText("提示");
                    title.setPadding(10, 10, 10, 10);
                    title.setGravity(Gravity.CENTER);
                    // title.setTextColor(getResources().getColor(R.color.greenBG));
                    title.setTextSize(23);
                    sll_main.setStatus(SwipeListView.Status.Close, true);
                    PwdData str = pwdDatasList.get(arg0);
                    final PwdData str_in = str;
                    AlertDialog.Builder builder = new AlertDialog.Builder(password.this);
                    builder.setCustomTitle(title)
                            .setMessage("是否删除该密码")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    myDao.delete(str_in.getId());
                                    pwdDatasList.remove(arg0);
                                    notifyDataSetChanged();
                                    Toast.makeText(password.this, str_in.getName() + "已删除", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(password.this, str_in.getName() + "未删除", Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                }
            });
            return view;
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
        getMenuInflater().inflate(R.menu.password, menu);
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
            intent.setClass(password.this, settings.class);
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
            intent.setClass(password.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.password) {
            intent.setClass(password.this, password.class);
            startActivity(intent);
        } else if (id == R.id.management) {
            intent.setClass(password.this, management.class);
            startActivity(intent);
        } else if (id == R.id.other) {
            intent.setClass(password.this, other.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
