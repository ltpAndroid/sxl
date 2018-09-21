package com.dofun.sxl.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.dofun.sxl.MyApplication;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.personal.term.PerfectInfoActivity;
import com.dofun.sxl.bean.UserInfo;
import com.dofun.sxl.fragment.LessonFragment;
import com.dofun.sxl.fragment.MainFragment;
import com.dofun.sxl.fragment.MeFragment;
import com.dofun.sxl.fragment.NoteFragment;
import com.dofun.sxl.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.rb_main)
    RadioButton rbMain;
    @BindView(R.id.rb_lesson)
    RadioButton rbLesson;
    @BindView(R.id.rb_note)
    RadioButton rbNote;
    @BindView(R.id.rb_me)
    RadioButton rbMe;
    @BindView(R.id.bottom_group)
    RadioGroup bottomGroup;

    MainFragment mainFragment;
    LessonFragment lessonFragment;
    NoteFragment noteFragment;
    MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        isFirstLogin();
        initView();
        initFragment();
    }

    private void isFirstLogin() {
        UserInfo userInfo = (UserInfo) SPUtils.getBaseBean(SPUtils.USER, UserInfo.class);
        if (userInfo.getSchoolId() == 0) {
            showSureDialog(R.string.perfect_information, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("main", true);
                    ActivityUtils.startActivity(bundle, PerfectInfoActivity.class);
                    return;
                }
            });
        }
    }

    private void initView() {
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mainFragment = new MainFragment();
        ft.add(R.id.container, mainFragment).commit();
        rbMain.setChecked(true);
    }

    private void initFragment() {
        bottomGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //开启事务
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //hideAllFragment(ft);
                switch (checkedId) {
                    case R.id.rb_main:
                        //                        if (mainFragment == null) {
                        //                            mainFragment = new MainFragment();
                        //                            ft.add(R.id.container, mainFragment);
                        //                        } else {
                        //                            ft.show(mainFragment);
                        //                        }
                        if (mainFragment == null) {
                            mainFragment = new MainFragment();
                        }
                        ft.replace(R.id.container, mainFragment);
                        break;
                    case R.id.rb_lesson:
                        //                        if (lessonFragment == null) {
                        //                            lessonFragment = new LessonFragment();
                        //                            ft.add(R.id.container, lessonFragment);
                        //                        } else {
                        //                            ft.show(lessonFragment);
                        //                        }
                        lessonFragment = new LessonFragment();
                        ft.replace(R.id.container, lessonFragment);
                        break;
                    case R.id.rb_note:
                        //                        if (noteFragment == null) {
                        //                            noteFragment = new NoteFragment();
                        //                            ft.add(R.id.container, noteFragment);
                        //                        } else {
                        //                            ft.show(noteFragment);
                        //                        }
                        noteFragment = new NoteFragment();
                        ft.replace(R.id.container, noteFragment);
                        break;
                    case R.id.rb_me:
                        //                        if (meFragment == null) {
                        //                            meFragment = new MeFragment();
                        //                            ft.add(R.id.container, meFragment);
                        //                        } else {
                        //                            ft.show(meFragment);
                        //                        }
                        meFragment = new MeFragment();
                        ft.replace(R.id.container, meFragment);
                        break;
                }
                ft.commit();
            }
        });
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        if (lessonFragment != null) {
            transaction.hide(lessonFragment);
        }
        if (noteFragment != null) {
            transaction.hide(noteFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
    }

    long exitTime = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                MyApplication.exitApp();//System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public static void navToOrder(Activity activity, int index) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("bottomIndex", index);
        NavUtils.navigateUpTo(activity, intent);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //        int index = getIntent().getIntExtra("bottomIndex", 0);
        //        if (index == 2) {
        rbNote.setChecked(true);
        //            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //            ft.replace(R.id.container, noteFragment).commit();
        //        }
    }
}
