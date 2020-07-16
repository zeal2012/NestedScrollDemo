package com.keyidabj.nestedscrolldemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class NestedParent extends NestedScrollView {

    String TAG = "NestedParent";

    //方便测试先固定。
    private int maxHeight = 0;
//    private View maxHeightView;
    private RecyclerView mRecyclerView;

    public void removeRecyclerView(){
        mRecyclerView = null;
    }

    public NestedParent(Context context) {
        super(context);
    }

    public NestedParent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedParent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public void setMaxHeightView(View view) {
//        this.maxHeightView = view;
//    }

    public void setMaxHeight(int maxHeight){
        this.maxHeight = maxHeight;
    }

    public void resetMaxHeight() {
        maxHeight = 0;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (maxHeightView != null){
//            if (maxHeight == 0){
//                //int titleBarHeight = getContext().getResources().getDimensionPixelSize(R.dimen.home_title_height);
//                int titleBarHeight = 0;
//                int StatusBar = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? StatusBarUtil.getStatusBarHeight(getContext()) : 0;
//                maxHeight = maxHeightView.getHeight() - StatusBar - titleBarHeight;
//                TLog.i(TAG, "maxHeight: " + maxHeight);
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    //对应子view 的dispatchNestedPreScroll方法， 最后一个数组代表消耗的滚动量，下标0代表x轴，下标1代表y轴
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(target, dx, dy, consumed, type);
//        Log.i(TAG, "onNestedPreScroll --- dy: " +dy);
        if (maxHeight == 0){
            super.onNestedPreScroll(target, dx, dy, consumed);
            return;
        }
        int scrollY = getScrollY();
        TLog.i(TAG, "onNestedPreScroll --- scrollY: " + scrollY);
        TLog.i(TAG, "onNestedPreScroll --- dy: " + dy);
        //向下滑动时
        if (dy > 0) {
            if (scrollY + dy <= maxHeight) {
                offset(dy, consumed);
            }else{
                offset(maxHeight - scrollY, consumed);
            }
        }
    }

    //继承android.support.v4.widget.NestedScrollView 时走这个方法
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    //返回true代表父view消耗滑动速度，子View将不会滑动
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i(TAG, "onNestedPreScroll ---" );
        if (maxHeight == 0){
            return super.onNestedPreFling(target, velocityX, velocityY);
        }
        if (null == mRecyclerView)
            mRecyclerView = (RecyclerView) target;
        if (mRecyclerView.computeVerticalScrollOffset() != 0) {
            return false;
        }
        this.fling((int) velocityY);
        return true;
    }

    private void offset(int dy, int[] consumed) {
        if (dy != 0) {
            scrollBy(0, dy);
            consumed[1] = dy;  //dy： 父view消耗了多少
        }
    }

    /********************  滑动监听 *******************************/

    private OnScrollListener listener;

    public interface OnScrollListener {
        void onScroll(int scrollY);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.onScroll(t);
        }
    }
    /********************  滑动监听end *******************************/
}
