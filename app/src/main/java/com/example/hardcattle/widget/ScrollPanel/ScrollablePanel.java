package com.example.hardcattle.widget.ScrollPanel;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.example.hardcattle.myapplication.R;

import java.util.HashSet;

/**
 * A flexible view for providing a limited window into a large data set,like a two-dimensional recyclerView.
 * but it will pin the itemView of first row and first column in their original location.
 */
public class ScrollablePanel extends FrameLayout {
    protected RecyclerView recyclerView;
    //  protected RecyclerView headerRecyclerView;
    protected PanelLineAdapter panelLineAdapter;
    protected PanelAdapter panelAdapter;
    //protected FrameLayout firstItemView;

    public ScrollablePanel(Context context, PanelAdapter panelAdapter) {
        super(context);
        this.panelAdapter = panelAdapter;
        initView();
    }

    public ScrollablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ScrollablePanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_scrollable_panel, this, true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
       // linearLayoutManager.setAutoMeasureEnabled(true);
        //linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_content_list);
        recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView.setHasFixedSize(true);
       // recyclerView.setNestedScrollingEnabled(false);
        if (panelAdapter != null) {
            panelLineAdapter = new PanelLineAdapter(panelAdapter, recyclerView);
            recyclerView.setAdapter(panelLineAdapter);

        }
    }


    public void notifyDataSetChanged() {
        if (panelLineAdapter != null) {
            panelLineAdapter.notifyDataChanged();
        }
    }

    /**
     * @param panelAdapter {@link PanelAdapter}
     */
    public void setPanelAdapter(PanelAdapter panelAdapter) {
        if (this.panelLineAdapter != null) {
            panelLineAdapter.setPanelAdapter(panelAdapter);
            panelLineAdapter.notifyDataSetChanged();
        } else {
            panelLineAdapter = new PanelLineAdapter(panelAdapter, recyclerView);
            recyclerView.setAdapter(panelLineAdapter);
        }
        this.panelAdapter = panelAdapter;


    }

    /**
     * Adapter used to bind dataSet to cell View that are displayed within every row of {@link ScrollablePanel}.
     */
    private static class PanelLineItemAdapter extends RecyclerView.Adapter {

        private PanelAdapter panelAdapter;
        private int row;

        public PanelLineItemAdapter(int row, PanelAdapter panelAdapter) {
            this.row = row;
            this.panelAdapter = panelAdapter;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return this.panelAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            this.panelAdapter.onBindViewHolder(holder, row, position + 1);
        }

        @Override
        public int getItemViewType(int position) {
            return this.panelAdapter.getItemViewType(row, position + 1);
        }

        @Override
        public int getItemCount() {
            return panelAdapter.getColumnCount() ;
        }

        public void setRow(int row) {
            this.row = row;
        }
    }


    /**
     * Adapter used to bind dataSet to views that are displayed within a{@link ScrollablePanel}.
     */
    private static class PanelLineAdapter extends RecyclerView.Adapter<PanelLineAdapter.ViewHolder> {

        private PanelAdapter panelAdapter;

        private RecyclerView contentRV;
        private HashSet<RecyclerView> observerList = new HashSet<>();
        private int firstPos = -1;
        private int firstOffset = -1;


        public PanelLineAdapter(PanelAdapter panelAdapter, RecyclerView contentRV) {
            this.panelAdapter = panelAdapter;

            this.contentRV = contentRV;


        }

        public void setPanelAdapter(PanelAdapter panelAdapter) {
            this.panelAdapter = panelAdapter;

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return panelAdapter.getRowCount();
        }

        @Override
        public PanelLineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            PanelLineAdapter.ViewHolder viewHolder = new PanelLineAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_content_row, parent, false));
            initRecyclerView(viewHolder.recyclerView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            PanelLineItemAdapter lineItemAdapter = (PanelLineItemAdapter) holder.recyclerView.getAdapter();
            if (lineItemAdapter == null) {
                lineItemAdapter = new PanelLineItemAdapter(position, panelAdapter);
                holder.recyclerView.setAdapter(lineItemAdapter);
            } else {
                lineItemAdapter.setRow(position);
                lineItemAdapter.notifyDataSetChanged();
            }
            if (holder.firstColumnItemVH == null) {//加载第一列
                RecyclerView.ViewHolder viewHolder = panelAdapter.onCreateViewHolder(holder.firstColumnItemView, panelAdapter.getItemViewType(position, 0));
                holder.firstColumnItemVH = viewHolder;
                panelAdapter.onBindViewHolder(holder.firstColumnItemVH, position, 0);
                holder.firstColumnItemView.addView(viewHolder.itemView);
            } else {
                panelAdapter.onBindViewHolder(holder.firstColumnItemVH, position, 0);
            }

        }


        public void notifyDataChanged() {

            notifyDataSetChanged();
        }


        public void initRecyclerView(RecyclerView recyclerView) {
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            layoutManager.setAutoMeasureEnabled(true);
            layoutManager.setSmoothScrollbarEnabled(true);
            if (layoutManager != null && firstPos > 0 && firstOffset > 0) {
                layoutManager.scrollToPositionWithOffset(PanelLineAdapter.this.firstPos + 1, PanelLineAdapter.this.firstOffset);
            }
            recyclerView.setNestedScrollingEnabled(false);
            observerList.add(recyclerView);
            recyclerView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_POINTER_DOWN:
                            for (RecyclerView rv : observerList) {
                                rv.stopScroll();
                            }
                    }
                    return false;
                }
            });

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int firstPos = linearLayoutManager.findFirstVisibleItemPosition();
                    View firstVisibleItem = linearLayoutManager.getChildAt(0);
                    if (firstVisibleItem != null) {
                        int firstRight = linearLayoutManager.getDecoratedRight(firstVisibleItem);
                        for (RecyclerView rv : observerList) {
                            if (recyclerView != rv) {
                                LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
                                if (layoutManager != null) {
                                    PanelLineAdapter.this.firstPos = firstPos;
                                    PanelLineAdapter.this.firstOffset = firstRight;
                                    layoutManager.scrollToPositionWithOffset(firstPos + 1, firstRight);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }

        private HashSet<RecyclerView> getRecyclerViews() {
            HashSet<RecyclerView> recyclerViewHashSet = new HashSet<>();

            for (int i = 0; i < contentRV.getChildCount(); i++) {
                recyclerViewHashSet.add((RecyclerView) contentRV.getChildAt(i).findViewById(R.id.recycler_line_list));
            }
            return recyclerViewHashSet;
        }


        static class ViewHolder extends RecyclerView.ViewHolder {
            public RecyclerView recyclerView;
            public FrameLayout firstColumnItemView;
            public RecyclerView.ViewHolder firstColumnItemVH;

            public ViewHolder(View view) {
                super(view);
                this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_line_list);
                this.firstColumnItemView = (FrameLayout) view.findViewById(R.id.first_column_item);
                this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            }
        }

    }


}
