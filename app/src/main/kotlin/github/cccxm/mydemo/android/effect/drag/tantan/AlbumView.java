//package github.cccxm.mydemo.android.effect.drag.tantan;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Rect;
//import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.util.TypedValue;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.animation.OvershootInterpolator;
//import android.widget.AbsListView;
//import android.widget.GridLayout;
//import android.widget.ImageView;
//
//import com.nineoldandroids.animation.Animator;
//import com.nineoldandroids.animation.AnimatorListenerAdapter;
//import com.nineoldandroids.animation.AnimatorSet;
//import com.nineoldandroids.animation.ObjectAnimator;
//import com.nineoldandroids.animation.PropertyValuesHolder;
//import com.nineoldandroids.view.ViewHelper;
//
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * 模仿探探 相册View
// */
//public class AlbumView extends ViewGroup implements OnTouchListener {
//
//    private RecyclerView.Adapter mAdapter;
//
//    /**
//     * 动画是否结束标记
//     */
//    private boolean mAnimationEnd = true;
//
//    /**
//     * item 其余宽高
//     */
//    private int ItemWidth;
//    private int hidePosition = -1;
//    /**
//     * 根据数据 获取的 最大可拖拽移动换位的范围
//     */
//    private int maxSize;
//    /**
//     * 当前控件 距离屏幕 顶点 的高度
//     */
//    private int mTopHeight = -1;
//
//    /**
//     * 每个item之间的间隙
//     */
//    public int padding = -1;
//    /**
//     * 正在拖拽的view的position(第几个子类)
//     */
//    private int mDragPosition;
//    /**
//     * 按下的X点
//     */
//    private int mDownX;
//    /**
//     * 按下的Y点
//     */
//    private int mDownY;
//
//    /**
//     * 是否点下显示镜像(是否是点击)
//     */
//    private boolean isOnItemClick = false;
//
//    /**
//     * 刚开始拖拽的item对应的View
//     */
//    private View mStartDragItemView = null;
//
//    /**
//     * 用于拖拽的镜像，这里直接用一个ImageView
//     */
//    private ImageView mDragImageView;
//
//    /**
//     * 我们拖拽的item对应的Bitmap
//     */
//    private Bitmap mDragBitmap;
//    /**
//     * 点击item的宽的中点
//     */
//    private int dragPointX;
//    /**
//     * 点击item的高的中点
//     */
//    private int dragPointY;
//    /**
//     * x坐标移动的距离
//     */
//    private int dragOffsetX;
//
//    /**
//     * 设置默认的显示图片
//     */
//    private int defaultImage = 0;
//    private int mDragPosition1;
//
//    public void setDefaultImage(int defaultImage) {
//        this.defaultImage = defaultImage;
//    }
//
//    /**
//     * y坐标移动的距离
//     */
//    private int dragOffsetY;
//
//    private GridLayout RootView;
//
//    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
//        @Override
//        public void onChanged() {
//            initUI();
//        }
//    };
//
//
//    /**
//     * 为了兼容小米那个日了狗的系统 就不用 WindowManager了
//     */
//    public void setRootView(GridLayout rootView) {
//        RootView = rootView;
//    }
//
//    public AlbumView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        padding = dp2px(4, context);
//    }
//
//    /**
//     * 滑动时的X点
//     */
//    int moveX;
//    /**
//     * 滑动时的Y点
//     */
//    int moveY;
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mHandler.removeCallbacks(mDragRunnable);
//                mDownX = (int) ev.getX();
//                mDownY = (int) ev.getY();
//                mDragPosition = pointToPosition(mDownX, mDownY);
//            /*
//             * 判断获取的这个组件是否超出可以滑动的组件范围，如果超出了将分发事件
//			 */
//                if (mDragPosition > maxSize) {
//                    return super.dispatchTouchEvent(ev);
//                }
//            /*
//             * 判断触摸的组件是否符合范围，不符合 将分发事件
//			 */
//                if (mDragPosition == -1) {
//                    return super.dispatchTouchEvent(ev);
//                }
//            /*
//             * 根据position获取该item所对应的View
//			 */
//                mStartDragItemView = getChildAt(mDragPosition);
//            /*
//             * 设置不销毁此View的cach
//			 */
//                mStartDragItemView.setDrawingCacheEnabled(true);
//            /*
//             * 获取此View的BitMap对象
//			 */
//                mDragBitmap = Bitmap.createBitmap(mStartDragItemView
//                        .getDrawingCache());
//            /*
//             * 销毁cache
//			 */
//                mStartDragItemView.destroyDrawingCache();
//
//                dragPointX = mStartDragItemView.getWidth() / 2;
//                dragPointY = mStartDragItemView.getHeight() / 2;
//                dragOffsetX = (int) (ev.getRawX() - mDownX);
//                dragOffsetY = (int) (ev.getRawY() - mDownY);
//            /*
//             * 将多线程加入消息队列并延迟50毫秒执行
//			 */
//                mHandler.postDelayed(mDragRunnable, 50);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveX = (int) ev.getX();
//                moveY = (int) ev.getY();
//                if (mDragImageView != null) {
//                    onDragItem(moveX - dragPointX + dragOffsetX, moveY - dragPointY
//                            + dragOffsetY - mTopHeight);
//                    onSwapItem(moveX, moveY);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                onStopDrag();
//                mHandler.removeCallbacks(mDragRunnable);
//                if (onItemActionListener != null)
//                    onItemActionListener.onActionUp(mDragPosition1, swapPosition);
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//    private Handler mHandler = new Handler();
//
//    /**
//     * 用来处理是否为长按的Runnable
//     */
//    private Runnable mDragRunnable = new Runnable() {
//        @Override
//        public void run() {
//            /*
//             * 根据我们按下的点显示item镜像
//			 */
//            if (isOnItemClick)
//                return;
//            createDragImage();
//            mStartDragItemView.setVisibility(View.GONE);
//        }
//
//    };
//
//    private Rect mTouchFrame;
//
//    /**
//     * 判断按下的位置是否在Item上 并返回Item的位置,即是第几的一个item
//     * {@link AbsListView #pointToPosition(int, int)}
//     */
//    public int pointToPosition(int x, int y) {
//        Rect frame = mTouchFrame;
//        if (frame == null) {
//            mTouchFrame = new Rect();
//            frame = mTouchFrame;
//        }
//        int count = getChildCount();
//        for (int i = count - 1; i >= 0; i--) {
//            final View child = getChildAt(i);
//            if (child.getVisibility() == View.VISIBLE) {
//                child.getHitRect(frame);
//                if (frame.contains(x, y)) {
//                    return i;
//                }
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * 创建拖动的镜像
//     */
//    private void createDragImage() {
//        int[] location = new int[2];
//        mStartDragItemView.getLocationOnScreen(location);
//        float drX = location[0];
//        float drY = location[1] - mTopHeight;
//        /*
//         * 创建一个ImageView并将你点击的那一个item的Bitmap存进去
//         */
//        mDragImageView = new ImageView(getContext());
//        mDragImageView.setImageBitmap(mDragBitmap);
//        RootView.addView(mDragImageView);
//
//        int drH = (int) (ItemWidth * 0.8);
//        float w = mStartDragItemView.getWidth();
//        final float scale = drH / w;
//        createTranslationAnimations(mDragImageView, drX,
//                mDownX - dragPointX + dragOffsetX, drY,
//                mDownY - dragPointY + dragOffsetY - mTopHeight, scale, scale)
//                .setDuration(200).start();
//    }
//
//    /**
//     * 从界面上面移除拖动镜像
//     */
//    private void removeDragImage() {
//        if (mDragImageView != null) {
//            RootView.removeView(mDragImageView);
//            mDragImageView = null;
//            if (mStartDragItemView != null)
//                mStartDragItemView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /**
//     * 拖动item，在里面实现了item镜像的位置更新，item的相互交换以及GridView的自行滚动
//     */
//    private void onDragItem(int x, int y) {
//        if (mDragImageView != null) {
//            ViewHelper.setTranslationX(mDragImageView, x);
//            ViewHelper.setTranslationY(mDragImageView, y);
//        }
//    }
//
//    private int swapPosition = -1;
//
//    /**
//     * 交换item
//     */
//    private void onSwapItem(int moveX, int moveY) {
//        if (mDragImageView != null) {
//            /*
//             * 获取移动时经过的坐标是第几个item
//             */
//            int tempPosition = pointToPosition(moveX, moveY);
//            /*
//             * 如果这个大于最大的那个那么就不做为什么，如果小于则判断是否是自己那一个View，是否不存在，交换动画是否执行完毕
//             */
//            if (tempPosition > maxSize) {
//                return;
//            }
//            if (tempPosition != mDragPosition && tempPosition != -1
//                    && mAnimationEnd) {
//                animationReorder(mDragPosition, tempPosition);
//            }
//        }
//    }
//
//    /**
//     * 停止拖拽我们将之前隐藏的item显示出来，并将镜像移除
//     */
//    private void onStopDrag() {
//        removeDragImage();
//        hidePosition = -1;
//    }
//
//    /**
//     * 获取当前控件 距离屏幕 顶点 的高度
//     */
//    private int getTopHeight(Context context) {
//        int statusHeight;
//        Rect ViewRect = new Rect();
//        getGlobalVisibleRect(ViewRect);
//        statusHeight = ViewRect.top;
//        if (0 == statusHeight) {
//            Rect localRect = new Rect();
//            getWindowVisibleDisplayFrame(localRect);
//            statusHeight = localRect.top;
//            if (0 == statusHeight) {
//                Class<?> localClass;
//                try {
//                    localClass = Class.forName("com.android.internal.R$dimen");
//                    Object localObject = localClass.newInstance();
//                    int i5 = Integer.parseInt(localClass
//                            .getField("status_bar_height").get(localObject)
//                            .toString());
//                    statusHeight = context.getResources()
//                            .getDimensionPixelSize(i5);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            // 注意 如果上边方法 未能 获取成功 那么 请根据个人 应用情况 加上相应的值
//            // 比如 +45 我加的是一个 大概Title 的值
//            // 如果当前控件 上边 有其他控件 请加上其他控件的高度
//            statusHeight += dp2px(45, context);
//        }
//
//        return statusHeight;
//    }
//
//    public void setAdapter(@NonNull RecyclerView.Adapter adapter) {
//        this.mAdapter = adapter;
//        mAdapter.registerAdapterDataObserver(adapterDataObserver);
//        initUI();
//    }
//
//    /**
//     * 初始化View集合，并向views里面添加数据
//     */
//    @SuppressWarnings("unchecked")
//    public void initUI() {
//        if (mAdapter == null) return;
//        /*
//         * 清空集合
//         */
////        views.clear();
//        /*
//         * 清view对象
//         */
//        removeAllViews();
//        int itemCount = mAdapter.getItemCount();
//        maxSize = itemCount > 5 ? 5 : itemCount - 1;
//        int count = 6;
//        for (int i = 0; i < itemCount && count > 0; i++, count--) {
//            RecyclerView.ViewHolder holder = mAdapter.onCreateViewHolder(this, 0);
//            mAdapter.onBindViewHolder(holder, i);
//            View itemView = holder.itemView;
//            itemView.setTag(i);
//            itemView.setOnTouchListener(this);
////            views.add(itemView);
//            addView(itemView);
//        }
//        if (defaultImage != 0)
//            while (count-- > 0) {
//                ImageView imageView = new ImageView(getContext());
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setImageResource(defaultImage);
//                imageView.setOnTouchListener(this);
//                addView(imageView);
//            }
//    }
//
//    /**
//     * 创建移动动画
//     *
//     * @param view   动画执行的View
//     * @param startX 动画开始的X坐标
//     * @param endX   结束时的X坐标
//     * @param startY 开始时的Y坐标
//     * @param endY   结束时的Y坐标
//     * @return 返回一个动画集合
//     */
//    private AnimatorSet createTranslationAnimations(View view, float startX,
//                                                    float endX, float startY, float endY) {
//        AnimatorSet animSetXY = new AnimatorSet();
//        animSetXY.playTogether(ObjectAnimator.ofPropertyValuesHolder(view,
//                PropertyValuesHolder.ofFloat("translationX", startX, endX),
//                PropertyValuesHolder.ofFloat("translationY", startY, endY)));
//        return animSetXY;
//    }
//
//    /**
//     * 缩放动画加平移动画
//     *
//     * @param view   将要执行动画的View组件
//     * @param startX 开始时的X坐标
//     * @param endX   结束时的X坐标
//     * @param startY 开始时的Y坐标
//     * @param endY   结束时的Y坐标
//     * @param scaleX X轴的缩放比例
//     * @param scaleY Y轴的缩放比列
//     * @return 返回一个动画集合
//     */
//    private AnimatorSet createTranslationAnimations(View view, float startX,
//                                                    float endX, float startY, float endY, float scaleX, float scaleY) {
//        AnimatorSet animSetXY = new AnimatorSet();
//        animSetXY.playTogether(ObjectAnimator.ofPropertyValuesHolder(view,
//                PropertyValuesHolder.ofFloat("translationX", startX, endX),
//                PropertyValuesHolder.ofFloat("translationY", startY, endY),
//                PropertyValuesHolder.ofFloat("scaleX", 1.0f, scaleX),
//                PropertyValuesHolder.ofFloat("scaleY", 1.0f, scaleY)));
//        return animSetXY;
//    }
//
//    @SuppressWarnings("unchecked")
//    public void swap(List<?> List, int index1, int index2) {
//        List<Object> rawList = (java.util.List<Object>) List;
//        rawList.set(index2, rawList.set(index1, rawList.get(index2)));
//    }
//
//    /**
//     * item的交换动画效果
//     *
//     * @param oldPosition 正在拖拽的那一个View的编号
//     * @param newPosition 当前触摸到的那个组件的编号
//     */
//    private void animationReorder(int oldPosition, int newPosition) {
//        if (mAdapter == null) return;
//        swapPosition = newPosition;
//        /*
//         * 判断触摸到的坐标的那一个View的编号是否大于现在正在拖拽的那一个坐标
//         */
//        boolean isForward = newPosition > oldPosition;
//        final List<Animator> resultList = new LinkedList<>();
//        if (isForward) {
//            for (int pos = oldPosition + 1; pos <= newPosition; pos++) {
//                View view = getChildAt(pos);
//                if (pos == 1) {
//                    float h = view.getWidth() / 2;
//                    float mSpacing = padding / 2;
//                    float w = getChildAt(0).getWidth();
//                    float scale = w / view.getWidth();
//                    resultList.add(createTranslationAnimations(view, 0,
//                            -(view.getWidth() + padding + mSpacing + h), 0, h
//                                    + mSpacing, scale, scale));
//                    swap(mAdapter.getItems(), pos, pos - 1);
//                }
//                if (pos == 2) {
//                    resultList.add(createTranslationAnimations(view, 0, 0, 0,
//                            -(view.getWidth() + padding)));
//                    swap(mAdapter.getItems(), pos, pos - 1);
//                }
//                if (pos == 3) {
//                    resultList.add(createTranslationAnimations(view, 0, 0, 0,
//                            -(view.getWidth() + padding)));
//                    swap(mAdapter.getItems(), pos, pos - 1);
//                }
//                if (pos == 4) {
//                    resultList.add(createTranslationAnimations(view, 0,
//                            view.getWidth() + padding, 0, 0));
//                    swap(mAdapter.getItems(), pos, pos - 1);
//                }
//                if (pos == 5) {
//                    resultList.add(createTranslationAnimations(view, 0,
//                            view.getWidth() + padding, 0, 0));
//                    swap(mAdapter.getItems(), pos, pos - 1);
//                }
//            }
//        } else {
//            for (int pos = newPosition; pos < oldPosition; pos++) {
//                View view = getChildAt(pos);
//                if (pos == 0) {
//                    float h = getChildAt(1).getWidth() / 2;
//                    float mSpacing = padding / 2;
//                    float w = getChildAt(0).getWidth();
//                    float scale = getChildAt(1).getWidth() / w;
//                    resultList.add(createTranslationAnimations(view, 0,
//                            getChildAt(1).getWidth() + padding + mSpacing + h,
//                            0, -(h + mSpacing), scale, scale));
//                }
//                if (pos == 1) {
//                    resultList.add(createTranslationAnimations(view, 0, 0, 0,
//                            view.getWidth() + padding));
//                }
//                if (pos == 2) {
//                    resultList.add(createTranslationAnimations(view, 0, 0, 0,
//                            view.getWidth() + padding));
//                }
//                if (pos == 3) {
//                    resultList.add(createTranslationAnimations(view, 0,
//                            -(view.getWidth() + padding), 0, 0));
//                }
//                if (pos == 4) {
//                    resultList.add(createTranslationAnimations(view, 0,
//                            -(view.getWidth() + padding), 0, 0));
//                }
//            }
//            for (int i = oldPosition; i > newPosition; i--) {
//                swap(mAdapter.getItems(), i, i - 1);
//            }
//        }
//
//        hidePosition = newPosition;
//        resultSet = new AnimatorSet();
//        /*
//         * 给动画填充动画集
//         */
//        resultSet.playTogether(resultList);
//        /*
//         * 设置动画时间
//         */
//        resultSet.setDuration(150);
//        /*
//         * 设置其播放模式
//         */
//        resultSet.setInterpolator(new OvershootInterpolator(1.6f));
//        resultSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                mAnimationEnd = false;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator arg0) {
//                if (!mAnimationEnd) {
//                    initUI();
//                    resultSet.removeAllListeners();
//                    resultSet.clone();
//                    resultSet = null;
//                    mDragPosition = hidePosition;
//                }
//                mAnimationEnd = true;
//            }
//        });
//        resultSet.start();
//        resultList.clear();
//    }
//
//    AnimatorSet resultSet = null;
//    /**
//     * 点击Item事件
//     */
//    OnItemClickListener clickListener;
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        clickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener {
//        void ItemClick(View view, int position, boolean Photo);
//    }
//
//    OnItemActionListener onItemActionListener;
//
//    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
//        this.onItemActionListener = onItemActionListener;
//    }
//
//    public interface OnItemActionListener {
//        void onActionDown(int downPosition);
//
//        void onActionUp(int downPosition, int upPosition);
//    }
//
//    /**
//     * 触摸时的x点坐标
//     */
//    int ItemDownX;
//    /**
//     * 触摸时Y点坐标
//     */
//    int ItemDownY;
//    /**
//     * 触摸开始时系统时间
//     */
//    long strTime;
//
//    /**
//     * 此方法用于判断是否是点击事件还是滑动
//     */
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                ItemDownX = (int) event.getX();
//                ItemDownY = (int) event.getY();
//                strTime = System.currentTimeMillis();
//                Object tag = v.getTag();
//                mDragPosition1 = -1;
//                if (tag != null && tag instanceof Integer) mDragPosition1 = (int) tag;
//                if (mDragPosition1 != -1 && onItemActionListener != null)
//                    onItemActionListener.onActionDown(mDragPosition1);
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//                /*
//                 * 获取点击的是第几个item
//                 */
//                Object tag = v.getTag();
//                int mDragPosition = -1;
//                if (tag != null && tag instanceof Integer) mDragPosition = (int) tag;
//                /*
//                 * 判断是点击的item是否超出了范围，如果超出了则响应点击事件
//                 */
//                if (mDragPosition >= 0 && mDragPosition <= maxSize) {
//                    /*
//                     * 获取触摸时距离widget的原点的X坐标
//                     */
//                    int moveX = (int) event.getX();
//                    int moveY = (int) event.getY();
//                    /*
//                     * 计算按下去和抬起来X点坐标的绝对值
//                     */
//                    float abslMoveDistanceX = Math.abs(moveX - ItemDownX);
//                    float abslMoveDistanceY = Math.abs(moveY - ItemDownY);
//                    /*
//                     * 如果X和Y的绝对值都小雨2.0并且抬起的时间和按下的时间差小于50，并且点击事件不等于null,
//                     * 则将触发item的点击事件，改变为点下的状态。并传出这个VIEW和下标。 否则改变为没有点下状态
//                     */
//                    boolean b = abslMoveDistanceX < 2.0 && abslMoveDistanceY < 2.0 && (System.currentTimeMillis() - strTime) < 500;
//                    if (b) {
//                        if (clickListener != null) {
//                            isOnItemClick = true;
//                            clickListener.ItemClick(getChildAt(mDragPosition),
//                                    mDragPosition, true);
//                        } else {
//                            isOnItemClick = false;
//                        }
//                    } else {
//                        isOnItemClick = false;
//                    }
//                } else {
//                    /*
//                     * 如果不为null则响应点击事件并改为点击，否则改为没点击
//                     */
//                    if (clickListener != null) {
//                        isOnItemClick = true;
//                        clickListener.ItemClick(getChildAt(mDragPosition),
//                                mDragPosition, false);
//                    } else {
//                        isOnItemClick = false;
//                    }
//                }
//                break;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        /*
//         * 获取父容器的宽度
//         */
//        int Width = getMeasuredWidth();
//        /*
//         * 容器的宽度/3分-item之间的间隙
//         */
//        ItemWidth = Width / 3 - padding - (padding / 3);
//        int mItemOne;
//        for (int i = 0, size = getChildCount(); i < size; i++) {
//            View view = getChildAt(i);
//            if (i == 0) {
//                mItemOne = ItemWidth * 2 + padding;
//                l += padding;
//                t += padding;
//                view.layout(l, t, l + mItemOne, t + mItemOne);
//                l += mItemOne + padding;
//            } else if (i == 1) {
//                view.layout(l, t, l + ItemWidth, t + ItemWidth);
//                t += ItemWidth + padding;
//            } else if (i == 2) {
//                view.layout(l, t, l + ItemWidth, t + ItemWidth);
//                t += ItemWidth + padding;
//            } else if (i >= 3) {
//                view.layout(l, t, l + ItemWidth, t + ItemWidth);
//                l -= ItemWidth + padding;
//            }
//            /*
//             * 如果当前绘制的view与拖动的view的是一样则让其隐藏
//             */
//            if (i == hidePosition) {
//                view.setVisibility(View.GONE);
//                mStartDragItemView = view;
//            }
//        }
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int resWidth;
//        int resHeight;
//        /*
//         * 根据传入的参数，分别获取测量模式和测量值
//         */
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        /*
//         * 如果宽或者高的测量模式非精确值
//         */
//        if (widthMode != MeasureSpec.EXACTLY
//                || heightMode != MeasureSpec.EXACTLY) {
//            /*
//             * 主要设置为背景图的高度
//             */
//            resWidth = getSuggestedMinimumWidth();
//            /*
//             * 如果未设置背景图片，则设置为屏幕宽高的默认值
//             */
//            resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;
//
//            resHeight = getSuggestedMinimumHeight();
//            /*
//             * 如果未设置背景图片，则设置为屏幕宽高的默认值
//             */
//            resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
//        } else {
//            /*
//             * 如果都设置为精确值，则直接取小值；
//             */
//            resWidth = resHeight = Math.min(width, height);
//        }
//
//        setMeasuredDimension(resWidth, resHeight);
//    }
//
//    /**
//     * 获得默认该layout的尺寸
//     */
//    private int getDefaultWidth() {
//        WindowManager wm = (WindowManager) getContext().getSystemService(
//                Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
//    }
//
//    /**
//     * 像素转换，dp转换为px
//     */
//    public int dp2px(int dp, Context context) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
//                context.getResources().getDisplayMetrics());
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            if (mTopHeight <= 0) {
//                mTopHeight = getTopHeight(getContext());
//            }
//        }
//    }
//
//}