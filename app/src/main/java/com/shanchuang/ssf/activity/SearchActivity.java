package com.shanchuang.ssf.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.utils.SharedHelper;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/8/13
 */
public class SearchActivity extends BaseActivity {


    @BindView(R.id.iv_return)
    ImageView ivBack;
    @BindView(R.id.toolbar_title)
    EditText toolbarTitle;
    @BindView(R.id.iv_scanning)
    TextView ivScanning;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tag_flow_layout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.ll_hot)
    LinearLayout llHot;
    private List<String> mFlagList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_layout;
    }

    private int type;

    @Override
    protected void initView() {
        toolbar.setNavigationIcon(null);
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            mFlagList = SharedHelper.getDataList(SearchActivity.this, "video");
        } else {
            mFlagList = SharedHelper.getDataList(SearchActivity.this, "shop");
        }

        tagFlowLayout.setAdapter(new TagAdapter<String>(mFlagList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_tag, tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                toolbarTitle.setText(mFlagList.get(position));
                Intent intent = new Intent();
                if (type == 1) {
                    intent.setClass(SearchActivity.this, VideoListActivity.class);
                    intent.putExtra("cid", "0");
                    intent.putExtra("cid2", "0");
                } else {
                    intent.setClass(SearchActivity.this, GoodsSearchActivity.class);

                }
                intent.putExtra("title", toolbarTitle.getText().toString());

                startActivity(intent);
                finish();
                return false;
            }
        });
        toolbarTitle.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    try {
                        mFlagList.add(toolbarTitle.getText().toString());
                        Intent intent = new Intent();
                        if (type == 1) {
                            SharedHelper.setDataList(SearchActivity.this, "video", mFlagList);
                            intent.setClass(SearchActivity.this, VideoListActivity.class);
                        } else {
                            SharedHelper.setDataList(SearchActivity.this, "shop", mFlagList);
                            intent.setClass(SearchActivity.this, GoodsSearchActivity.class);
                        }
                        intent.putExtra("title", toolbarTitle.getText().toString());
                        intent.putExtra("cid", "0");
                        intent.putExtra("cid2", "0");
                        startActivity(intent);
                        finish();
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                                hideSoftInputFromWindow(toolbarTitle.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_return, R.id.tv_clear,R.id.iv_scanning})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_scanning:
                finish();
                break;
            case R.id.tv_clear:
                mFlagList.clear();
                if (type == 1) {
                    SharedHelper.setDataList(SearchActivity.this, "video", null);
                } else {
                    SharedHelper.setDataList(SearchActivity.this, "shop", null);
                }
                tagFlowLayout.onChanged();
                break;
        }
    }
}