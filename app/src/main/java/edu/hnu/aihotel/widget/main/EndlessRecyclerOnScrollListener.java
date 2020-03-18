package edu.hnu.aihotel.widget.main;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;
    //标识RecyclerView的LayoutManager是哪种
    protected int layoutManagerType;
    public static final int LINEAR = 0;
    public static final int GRID = 1;
    public static final int STAGGERED_GRID = 2;
    // 最后一个的位置
    protected int lastVisibleItem;
    // 瀑布流的最后一个的位置
    protected int[] lastPositions;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            layoutManagerType = LINEAR;
        } else if (layoutManager instanceof GridLayoutManager) {
            layoutManagerType = GRID;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            layoutManagerType = STAGGERED_GRID;
        } else {
            throw new RuntimeException(
                    "Unsupported LayoutManager used. Valid ones are " +
                            "LinearLayoutManager, GridLayoutManager and " +
                            "StaggeredGridLayoutManager");
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItem = ((LinearLayoutManager) layoutManager)
                        .findLastCompletelyVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItem = ((GridLayoutManager) layoutManager)
                        .findLastCompletelyVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager
                        = (StaggeredGridLayoutManager) layoutManager;
                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
                lastVisibleItem = findMax(lastPositions);
                break;
        }
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的itemPosition
            int itemCount = layoutManager.getItemCount();

            System.out.println(itemCount - 1  + "  " + lastVisibleItem );
            // 判断是否滑动到了最后一个item，并且是向上滑动
            if (lastVisibleItem == (itemCount - 1) && isSlidingUpward) {
                //加载更多
                onLoadMore();
            }
        }
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;
        System.out.println("上滑");
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();
}

