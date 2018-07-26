package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dofun.sxl.R;
import com.dofun.sxl.fragment.LessonFragment;
import com.dofun.sxl.fragment.MainFragment;
import com.dofun.sxl.fragment.MeFragment;
import com.dofun.sxl.fragment.NoteFragment;

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

        initView();
        initFragment();
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
                hideAllFragment(ft);
                switch (checkedId) {
                    case R.id.rb_main:
                        if (mainFragment == null) {
                            mainFragment = new MainFragment();
                            ft.add(R.id.container, mainFragment);
                        } else {
                            ft.show(mainFragment);
                        }
                        break;
                    case R.id.rb_lesson:
                        if (lessonFragment == null) {
                            lessonFragment = new LessonFragment();
                            ft.add(R.id.container, lessonFragment);
                        } else {
                            ft.show(lessonFragment);
                        }
                        break;
                    case R.id.rb_note:
                        if (noteFragment == null) {
                            noteFragment = new NoteFragment();
                            ft.add(R.id.container, noteFragment);
                        } else {
                            ft.show(noteFragment);
                        }
                        break;
                    case R.id.rb_me:
                        if (meFragment == null) {
                            meFragment = new MeFragment();
                            ft.add(R.id.container, meFragment);
                        } else {
                            ft.show(meFragment);
                        }
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

}
