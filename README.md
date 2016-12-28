# MyTestCoordinatorLayout
 - CoordinatorLayout结合自定义beheavior，listView的使用
 - listView作为toorBar的anchor，CircleImageView依赖toorBar
 - 利用自定义的CircleImageView的beheavior实现：滑动listView时，toorBar和CircleImageView随着分别渐隐渐现和放大缩小等效果

### 运行效果如下：

![pic](https://github.com/zongkaili/MyTestCoordinatorLayout-master/blob/master/screenshot/testCoordDemo.gif)
<!--<img src="https://github.com/zongkaili/MyTestCoordinatorLayout-master/blob/master/screenshot/testCoordDemo.gif" width="400">-->

<!--
附上几张截图：
![screen](https://github.com/zongkaili/MyTestCoordinatorLayout-master/blob/master/screenshot/device-2016-12-28-104145.png)
![screen](https://github.com/zongkaili/MyTestCoordinatorLayout-master/blob/master/screenshot/device-2016-12-28-104254.png)
![screen](https://github.com/zongkaili/MyTestCoordinatorLayout-master/blob/master/screenshot/device-2016-12-28-104337.png)-->

###关键代码：

 - 设置自动滑动的动画效果
 ```java
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
```
 - 处理ToolBar的显示
 ```java
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
 ``` 
 - 设置渐变的动画
 ```java
    public static void startAlphaAnimation(View v, long duration, int visibility) {
            AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                    ? new AlphaAnimation(0f, 1f)
                    : new AlphaAnimation(1f, 0f);
    
            alphaAnimation.setDuration(duration);
            alphaAnimation.setFillAfter(true);
            v.startAnimation(alphaAnimation);
    }
 ``` 
 - 控制titleBar下面的View的隐藏显示
 ```java
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
```