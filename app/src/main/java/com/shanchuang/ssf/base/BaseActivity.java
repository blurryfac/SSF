package com.shanchuang.ssf.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.application.ActivityBean;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.view.RxToast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by G on 2018/3/16.`
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public TextView title, menu;
    public TextView back;
//    public boolean isFinish = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindView();
        initToolbar();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        ActivityBean bean = getIntent().getParcelableExtra("ActivityBean");
        bean.getUnbinder().unbind();
        super.onDestroy();
    }

    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    private void bindView() {
        ActivityBean bean = new ActivityBean();
        Unbinder unbinder = ButterKnife.bind(this);
        bean.setUnbinder(unbinder);
        getIntent().putExtra("ActivityBean", bean);
    }

    public void showToams(String s)
    {
        RxToast.normal(s);
    }
    /**
     * 加载toolbar
     * 注意:有的子类布局不需要toolbar,但是父类还是会走initToolbar方法,为了避免空指针异常,加一个非空判断
     */
    protected void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        back = findViewById(R.id.iv_back);
        title = findViewById(R.id.toolbar_title);
        menu = findViewById(R.id.toolbar_menu);
        if (toolbar != null) {
            toolbar.setTitle("");
            toolbar.setNavigationIcon(R.mipmap.ic_return);
            setSupportActionBar(toolbar);
        }
    }

    /**
     * 添加控件
     */
    protected abstract void initView();

    /**
     * 添加数据
     */
    protected abstract void initData();

    /**
     * startActivity
     */
    protected void readyGo(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    private void changeBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //实现状态栏图标和文字颜色为暗色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, 22);
    }

    /**
     * 在有输入文本打开输入法的时候，点击其他空白区域收起输入法
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this
                    .getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当点击toolbar上的返回箭头，退出当前activity
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                if(isFinish){
                finish();
//                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean isNull(EditText editText)
    {
        if (editText.getText().toString()==null|editText.getText().toString().equals(""))
        {
            return true;
        }else {
            return false;
        }
    }
    public boolean isNull(TextView editText)
    {
        if (editText.getText().toString().isEmpty())
        {
            return true;
        }else {
            return false;
        }
    }
    public void setImg(ImageView imageView,Object object)
    {
        Glide.with(this).load(object).into(imageView);
    }
    public boolean isNull(String editText)
    {
        if (editText==null||editText.equals(""))
        {
            return true;
        }else {
            return false;
        }
    }
    public String getString(EditText editText)
    {
        return editText.getText().toString();
    }
    public String getString(TextView editText)
    {
        return editText.getText().toString();
    }

    public int getUid()
    {
        return Integer.parseInt(SharedHelper.readId(this));
    }
}
