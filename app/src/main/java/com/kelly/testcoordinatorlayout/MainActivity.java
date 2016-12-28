package com.kelly.testcoordinatorlayout;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AbsListView.OnScrollListener, View.OnTouchListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    // 控制ToolBar的变量
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private ConversationAdapter mRecentChatAdapter;
    private LinearLayout mLlQTSwitcher;

    @Bind(R.id.main_iv_placeholder)
    ImageView mIvPlaceholder; // 大图片

    @Bind(R.id.main_ll_title_container)
    LinearLayout mLlTitleContainer; // Title的LinearLayout

    @Bind(R.id.main_fl_title)
    FrameLayout mFlTitleContainer; // Title的FrameLayout

    @Bind(R.id.main_abl_app_bar)
    AppBarLayout mAblAppBar; // 整个可以滑动的AppBar

    @Bind(R.id.ib_common_title_back)
    ImageButton mIvToolbarBack; // 标题栏back按钮

    @Bind(R.id.main_tv_toolbar_title)
    TextView mTvToolbarTitle; // 标题栏右边textView

    @Bind(R.id.main_tb_toolbar)
    Toolbar mTbToolbar; // 工具栏

    @Bind(R.id.chat_msg_listView)
    ListView mChatMsgListView;

    @Bind(R.id.chat_common_list_header)
    RelativeLayout mRlTitleBarShadow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initWidge();

        setListener();

        initParallaxValues(); // 自动滑动效果
    }

    private void initWidge() {
        mTbToolbar.setTitle("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mChatMsgListView.setNestedScrollingEnabled(true);
        }
        mRlTitleBarShadow = (RelativeLayout) findViewById(R.id.chat_common_list_header);
        View headerView = LayoutInflater.from(this).inflate(R.layout.chat_act_common_list_header, null);
        mLlQTSwitcher = (LinearLayout) headerView.findViewById(R.id.ll_select_tongcheng_or_quanguo);
        mChatMsgListView.addHeaderView(headerView);
        mRecentChatAdapter = new ConversationAdapter(this);
        mChatMsgListView.setAdapter(mRecentChatAdapter);

        mChatMsgListView.setOnScrollListener(this);
        mChatMsgListView.setOnTouchListener(this);
    }

    private void setListener() {
        // AppBar的监听
        mAblAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                handleAlphaOnTitle(percentage);
                handleToolbarTitleVisibility(percentage);
            }
        });

        mIvToolbarBack.setOnClickListener(this);
        mTvToolbarTitle.setOnClickListener(this);
    }

    // 设置自动滑动的动画效果
    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mIvPlaceholder.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mFlTitleContainer.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mIvPlaceholder.setLayoutParams(petDetailsLp);
        mFlTitleContainer.setLayoutParams(petBackgroundLp);
    }

    // 处理ToolBar的显示
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTbToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTbToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    // 控制Title的显示
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    // 设置渐变的动画
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_common_title_back:
                finish();
                break;
            case R.id.main_tv_toolbar_title:
                Toast.makeText(this,"点击了注销按钮",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int top = getYLocationOnScreen(mLlQTSwitcher);
        Log.d(TAG, "top : " + top + " location1[1] : " + getYLocationOnScreen(mLlQTSwitcher) + " location2[1] : " + getYLocationOnScreen(mRlTitleBarShadow));

        Log.d(TAG," mLlQTSwitcher.getY() : " + mLlQTSwitcher.getY()+" getYLocationOnScreen(mTbToolbar) : " + getYLocationOnScreen(mLlQTSwitcher));
            if (getYLocationOnScreen(mTbToolbar) == CommonUtils.getStatusBarHeight(this) && (top < getYLocationOnScreen(mRlTitleBarShadow) || firstVisibleItem >= 1)) {
                mRlTitleBarShadow.setVisibility(View.VISIBLE);
            } else {
                mRlTitleBarShadow.setVisibility(View.GONE);
            }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private int getYLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }
}
