package com.guc.ui.dialogchooseinfo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guc.ui.R;

import java.util.ArrayList;
import java.util.List;

public class DialogChooseInfo extends DialogFragment {
    public static final String TAG = "DialogChooseInfo";
    static final String DATA = "data";
    public OnChooseInfoClickListener mOnChooseInfoClickListener;
    private TextView mTvTile;
    private LinearLayout mLlSearch;
    private EditText mEtSearch;
    private TextView mTvSearch;
    private RecyclerView mRlvChooseInfo;
    private List<BeanChooseOption> mBeanChooseOptionList;
    private BeanChooseInfo mLocalData;
    private String mHttpUrl;
    private String mTitle;
    private LinearLayout mLlContainer;
    private AdapterChooseInfo mAdapterChooseInfo;
    private String selectValue;//选中的项value(code值）


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable());
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choose_info, container, false);
        init(view);
        return view;
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
    }

    private void init(View view) {
        mBeanChooseOptionList = new ArrayList<>();
        mTvTile = view.findViewById(R.id.tv_title);
        mLlSearch = view.findViewById(R.id.ll_search);
        mEtSearch = view.findViewById(R.id.et_search);
        mTvSearch = view.findViewById(R.id.tv_search);
        mRlvChooseInfo = view.findViewById(R.id.rlv_choose_info);
        mTvSearch = view.findViewById(R.id.tv_search);
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterData();
            }
        });
        getBundleInfo();
        initView();
    }

    public void setOnChooseInfoClickListener(OnChooseInfoClickListener listener) {
        mOnChooseInfoClickListener = listener;
    }

    private void getBundleInfo() {
        Bundle bundle = getArguments();
        mLocalData = bundle.getParcelable(DATA);
        mTitle = mLocalData.title;
    }

    private void initView() {
        setTitle();
        initRecyclerView();
        setContent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setTitle() {
        if (!TextUtils.isEmpty(mTitle)) {
            mTvTile.setText(mTitle);
        }
    }

    private void setContent() {
        if (mLocalData.beanChooseOptionList != null) {
            if (mLocalData.beanChooseOptionList.size() > 10) {
                mLlSearch.setVisibility(View.VISIBLE);
            }
            getDataFromLocal();
        }
    }

    public void initRecyclerView() {
        mAdapterChooseInfo = new AdapterChooseInfo(getActivity(), mBeanChooseOptionList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRlvChooseInfo.setLayoutManager(manager);
        mRlvChooseInfo.setAdapter(mAdapterChooseInfo);
        MultiItemDivider itemDivider = new MultiItemDivider(getActivity(), MultiItemDivider.VERTICAL_LIST, R.drawable.shape_divider);
        itemDivider.setDividerMode(MultiItemDivider.INSIDE);
        mRlvChooseInfo.addItemDecoration(itemDivider);
        mAdapterChooseInfo.setOnChooseInfoClickListener(new AdapterChooseInfo.OnChooseInfoClickListener() {
            @Override
            public void click(int position, BeanChooseOption beanChooseInfo) {
                mOnChooseInfoClickListener.onClick(position, beanChooseInfo);
                dismiss();
            }
        });
    }

    private void getDataFromLocal() {
        mBeanChooseOptionList.addAll(mLocalData.beanChooseOptionList);
        for (int i = 0; i < mBeanChooseOptionList.size(); i++) {
            if (mBeanChooseOptionList.get(i).value.equals(selectValue)) {
                mBeanChooseOptionList.get(i).selected = true;
            }
        }
        mAdapterChooseInfo.notifyDataSetChanged();
    }

    private void filterData() {
        mBeanChooseOptionList.clear();
        mBeanChooseOptionList.addAll(mLocalData.beanChooseOptionList);
        String searchStr = mEtSearch.getText().toString().trim();
        for (int i = 0; i < mLocalData.beanChooseOptionList.size(); i++) {
            if (!mLocalData.beanChooseOptionList.get(i).key.contains(searchStr)) {
                mBeanChooseOptionList.remove(mLocalData.beanChooseOptionList.get(i));
            }
        }
        mAdapterChooseInfo.notifyDataSetChanged();
    }

    public interface OnChooseInfoClickListener {
        void onClick(int position, BeanChooseOption beanChooseInfo);
    }
}
