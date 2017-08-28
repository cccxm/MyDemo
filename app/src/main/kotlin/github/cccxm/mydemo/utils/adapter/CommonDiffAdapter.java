package github.cccxm.mydemo.utils.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cxm
 * on 2017/8/28.
 * <p>
 * 抽象的增量更新适配器
 * <p>
 * 如果使用该适配器,需要重写对象的{@link #equals(Object)}方法,适配器将会调用此方法来判断两个Bean实例是否相等.
 */

public abstract class CommonDiffAdapter<D extends CommonDiffAdapter.DiffBean, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    /**
     * 用来保存数据的集合
     */
    @NonNull
    private List<D> mItems = new LinkedList<>();
    /**
     * 当前在增量更新时是否进行移动检查 {@link #setMoveChcek(boolean)},默认进行检查
     */
    private boolean mIsMoveCheck = true;
    private int mCurrentPosition;

    /**
     * 设置增量更新的时候是否进行移动检查,默认为检查
     * <p>
     * 如果设置为true,则检测在增量更新过程中发生数据移动.但会增加系统开销.
     * 如果设置为false,则没有检测数据移动的过程,如果中途发生数据移位,那么移位前后的两个相同的数据就会被当作两个两个不同的数据进行显示
     *
     * @param moveCheck 是否进行移动检查
     */
    public void setMoveChcek(boolean moveCheck) {
        mIsMoveCheck = moveCheck;
    }

    /**
     * 获得已经存在的数据列表,该方法会对数据进行复制而不是直接获得数据的引用
     */
    public List<D> getOldItems() {
        return new LinkedList<>(mItems);
    }

    /**
     * 向当前数据中追加一段数据
     */
    public void addItems(@Nullable List<D> items) {
        if (items != null) {
            List<D> oldItems = getOldItems();
            LinkedList<D> newItems = new LinkedList<>(mItems);
            newItems.addAll(items);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack<>(oldItems, mItems), mIsMoveCheck);
            mItems = newItems;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    /**
     * 重置数据
     */
    public void resetItems(@Nullable List<D> items) {
        if (items != null && !items.isEmpty()) {
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    /**
     * 增量更新数据.
     * <p>
     * 在增量更新的过程中,新数据和旧数据的数据源不能相同.否则无法进行正常的更新.
     *
     * @param newItems 新数据
     */
    public void diffItems(@Nullable List<D> newItems) {
        if (newItems != null && !newItems.isEmpty()) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack<>(mItems, newItems), mIsMoveCheck);
            mItems = newItems;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, @SuppressLint("RecyclerView") int position) {
        mCurrentPosition = position;
        if (holder != null) {
            if (position < mItems.size()) {
                D item = mItems.get(position);
                if (item != null) {
                    onBindViewHolder(holder, item);
                }
            }
        }
    }

    protected int getPosition() {
        return mCurrentPosition;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected abstract void onBindViewHolder(@NonNull VH holder, @NonNull D item);

    private static class DiffCallBack<T extends DiffBean> extends DiffUtil.Callback {
        @NonNull
        private List<T> mOldItems;
        @NonNull
        private List<T> mNewItems;

        /**
         * @param oldItems 旧数据
         * @param newItems 新数据
         */
        DiffCallBack(@NonNull List<T> oldItems, @NonNull List<T> newItems) {
            this.mOldItems = oldItems;
            this.mNewItems = newItems;
        }

        /**
         * @return 旧数据的长度
         */
        @Override
        public int getOldListSize() {
            return mOldItems.size();
        }

        /**
         * @return 新数据的长度
         */
        @Override
        public int getNewListSize() {
            return mOldItems.size();
        }

        /**
         * Called by the DiffUtil to decide whether two object represent the same Item.
         * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list
         * @return True if the two items represent the same object or false if they are different.
         */
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            T oldItem = mOldItems.get(oldItemPosition);
            T newItem = mNewItems.get(newItemPosition);
            //noinspection unchecked
            return oldItem != null && oldItem.isSameItem(newItem);
        }

        /**
         * Called by the DiffUtil when it wants to check whether two items have the same data.
         * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
         * <p>
         * DiffUtil uses this information to detect if the contents of an item has changed.
         * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
         * <p>
         * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
         * DiffUtil 用这个方法替代equals方法去检查是否相等。
         * <p>
         * so that you can change its behavior depending on your UI.
         * 所以你可以根据你的UI去改变它的返回值
         * <p>
         * For example, if you are using DiffUtil with a
         * {@link android.support.v7.widget.RecyclerView.Adapter RecyclerView.Adapter}, you should
         * return whether the items' visual representations are the same.
         * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
         * <p>
         * This method is called only if {@link #areItemsTheSame(int, int)} returns
         * {@code true} for these items.
         * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list which replaces the
         *                        oldItem
         * @return True if the contents of the items are the same or false if they are different.
         */
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            T oldItem = mOldItems.get(oldItemPosition);
            T newItem = mNewItems.get(newItemPosition);
            //noinspection unchecked
            return oldItem != null && oldItem.isSameContent(newItem);
        }
    }

    /**
     * {@link CommonDiffAdapter} 会根据其中抽象方法的实现来决定数据的加载方式
     * <p>
     * 泛型{@link E} 为当前实现该接口的实现类类型.
     */
    public interface DiffBean<E> {

        /**
         * 判断两个 Bean 对象是否代表同一个 Item
         * <p>
         * 例如:
         * 当前 Item 的唯一性由其中的 ID 字段决定,则只要两个对象的 ID 相同这里就应该返回 true
         *
         * @param newItem 新的 Item Bean 对象
         * @return 如果两个Bean对象代表同一个Item, 则返回true
         */
        boolean isSameItem(@Nullable E newItem);

        /**
         * 当 {@link #isSameItem(Object)} 返回 true 时,该方法会被调用,用来判断两个代表相同Item的Bean对象的内容是否改变
         * <p>
         * 比如:
         * 两个数据ID为1,但是评论数量不同,则可以返回false表示当前ItemView需要刷新
         *
         * @param newItem 新的 Item Bean 对象
         * @return 如果两个代表相同Item的对象的内容相同, 则返回true, 否则返回false
         */
        boolean isSameContent(@Nullable E newItem);
    }
}
