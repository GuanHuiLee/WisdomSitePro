package com.ljkj.qxn.wisdomsitepro.ui.quality;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.ljkj.qxn.wisdomsitepro.R;
import com.ljkj.qxn.wisdomsitepro.Utils.PickerDialogHelper;
import com.ljkj.qxn.wisdomsitepro.Utils.widget.ItemView;
import com.ljkj.qxn.wisdomsitepro.contract.common.QueryFileContract;
import com.ljkj.qxn.wisdomsitepro.contract.quality.QualityPatrolContract;
import com.ljkj.qxn.wisdomsitepro.data.FileEntity;
import com.ljkj.qxn.wisdomsitepro.data.entity.PageInfo;
import com.ljkj.qxn.wisdomsitepro.data.entity.QualityPatrolDetailInfo;
import com.ljkj.qxn.wisdomsitepro.data.entity.QualityPatrolInfo;
import com.ljkj.qxn.wisdomsitepro.model.CommonModel;
import com.ljkj.qxn.wisdomsitepro.model.QualityModel;
import com.ljkj.qxn.wisdomsitepro.presenter.common.QueryFilePresenter;
import com.ljkj.qxn.wisdomsitepro.presenter.quality.QualityPatrolPresenter;
import com.ljkj.qxn.wisdomsitepro.ui.common.adapter.SelectImageAdapter;
import com.ljkj.qxn.wisdomsitepro.ui.common.adapter.ShowImageAdapter;
import com.ljkj.qxn.wisdomsitepro.ui.quality.adapter.QualityInspectionReformAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cdsp.android.ui.base.BaseActivity;
import cdsp.android.ui.widget.DividerGridItemDecoration;

public class QualityInspectionDetailV2Activity extends BaseActivity implements QualityPatrolContract.View {

    @BindView(R.id.scroll_View)
    protected NestedScrollView scrollView;

    @BindView(R.id.tv_title)
    protected TextView titleText;

    @BindView(R.id.tv_check_result)
    protected TextView checkResultText;

    @BindView(R.id.ll_check_content)
    protected LinearLayout checkContentLayout;

    @BindView(R.id.item_code)
    protected ItemView codeItem;

    @BindView(R.id.item_check_date)
    protected ItemView checkDateItem;

    @BindView(R.id.item_group)
    protected ItemView groupItem;

    @BindView(R.id.item_reason)
    protected ItemView reasonItem;

    @BindView(R.id.item_loss)
    protected ItemView lossItem;

    @BindView(R.id.tv_rectify_content)
    protected TextView rectifyContentText;

    @BindView(R.id.rl_rectify_image)
    protected RecyclerView rectifyImageRV;

    @BindView(R.id.item_post_man)
    protected ItemView postManItem;

    @BindView(R.id.item_group_leader)
    protected ItemView groupLeaderItem;

    @BindView(R.id.rl_reform)
    protected RecyclerView reformRV;

    @BindView(R.id.et_qualified)
    protected EditText qualifiedEdit;

    @BindView(R.id.item_whether_qualified)
    protected ItemView whetherQualifiedItem;

    @BindView(R.id.ll_examine)
    protected LinearLayout examineLayout;

    @BindView(R.id.rl_scene_image)
    protected RecyclerView sceneImageRV;

    @BindView(R.id.tv_rectify)
    protected TextView rectifyText; //班组整改回复二

    @BindView(R.id.ll_rectify)
    protected LinearLayout rectifyLayout;

    @BindView(R.id.item_check_date2)
    protected ItemView checkDate2Item;

    @BindView(R.id.item_group_leader2)
    protected ItemView groupLeader2Item;

    @BindView(R.id.item_post_man2)
    protected ItemView postMan2Item;

    @BindView(R.id.et_rectify_content)
    EditText rectifyContentEdit;

    @BindView(R.id.tv_next)
    protected TextView nextText;

    @BindView(R.id.item_check_position)
    protected ItemView positionItem;

    protected ShowImageAdapter rectifyImageAdapter;
    protected SelectImageAdapter sceneImageAdapter;
    protected QualityInspectionReformAdapter inspectionReformAdapter;
    protected QualityPatrolPresenter patrolPresenter;
    protected QueryFilePresenter queryFilePresenter;
    protected QualityPatrolDetailInfo qualityPatrolDetailInfo;

    protected String id;
    protected String title;

    public static void startActivity(Context context, String id, String title, Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_inspection_detail_v2);
    }

    @Override
    protected void initUI() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        titleText.setText(title);

        reformRV.setNestedScrollingEnabled(false);
        rectifyImageRV.setNestedScrollingEnabled(false);
        sceneImageRV.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        reformRV.setLayoutManager(new LinearLayoutManager(this));
        reformRV.setAdapter(inspectionReformAdapter = new QualityInspectionReformAdapter(this));

        rectifyImageRV.setLayoutManager(new GridLayoutManager(this, 3));
        rectifyImageRV.setAdapter(rectifyImageAdapter = new ShowImageAdapter(this));
        rectifyImageRV.addItemDecoration(new DividerGridItemDecoration(this, ContextCompat.getDrawable(this, R.drawable.divider_of_select_image)));

        sceneImageRV.setLayoutManager(new GridLayoutManager(this, 3));
        sceneImageRV.setAdapter(sceneImageAdapter = new SelectImageAdapter(this));
        sceneImageRV.addItemDecoration(new DividerGridItemDecoration(this, ContextCompat.getDrawable(this, R.drawable.divider_of_select_image)));

        patrolPresenter = new QualityPatrolPresenter(this, QualityModel.newInstance());
        addPresenter(patrolPresenter);
        patrolPresenter.getQualityPatrolDetail(id);
//        test();

        queryFilePresenter = new QueryFilePresenter(new QueryFileContract.View() {
            @Override
            public void showFiles(List<FileEntity> fileEntities) {
                rectifyImageAdapter.loadData(fileEntities);
            }

            @Override
            public void showProgress(String message) {
            }

            @Override
            public void hideProgress() {
            }

            @Override
            public void showError(String message) {
            }


        }, CommonModel.newInstance());
        addPresenter(queryFilePresenter);
    }

    @OnClick({R.id.tv_back, R.id.tv_check_result, R.id.tv_next, R.id.item_whether_qualified})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_check_result:
                checkContentLayout.setVisibility(checkContentLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                Drawable right = checkContentLayout.getVisibility() == View.VISIBLE ? ContextCompat.getDrawable(this, R.mipmap.ic_collapse) : ContextCompat.getDrawable(this, R.mipmap.ic_expand);
                checkResultText.setCompoundDrawablesWithIntrinsicBounds(null, null, right, null);
                break;
            case R.id.tv_next:
                if (checkData()) {
                    doNext();
                }
                break;
            case R.id.item_whether_qualified:
                showOptionDialog(whetherQualifiedItem);
                break;
            default:
                break;
        }
    }

    protected boolean checkData() {
        return true;
    }

    protected void doNext() {

    }


    private void showOptionDialog(final ItemView itemView) {
        if (isFastDoubleClick()) {
            return;
        }
        PickerDialogHelper.showPickerOption(this, Arrays.asList("合格", "不合格"), 0, true, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                itemView.setContent(options1 == 0 ? "合格" : "不合格");
                qualifiedEdit.setVisibility(options1 == 0 ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public void showQualityPatrolList(PageInfo<QualityPatrolInfo> pageInfo) {

    }

    @Override
    public void showQualityPatrolDetail(QualityPatrolDetailInfo data) {
        List<QualityPatrolDetailInfo.ReformBean> reform = data.getReform();
        QualityPatrolDetailInfo.CheckBean check = data.getCheck();
        this.qualityPatrolDetailInfo = data;
        String checkCode = check.getCheckCode();
        codeItem.setContent(TextUtils.isEmpty(checkCode) ? "" : checkCode);
        checkDateItem.setContent(check.getCheckDate());
        groupItem.setContent(check.getTeam());//整改班组
        reasonItem.setContent(check.getWhyType());//按事故发生原因分类
        lossItem.setContent(check.getLossType());//事故造成的伤亡或者损失分类
        rectifyContentText.setText(check.getCheckInfo());
        postManItem.setContent(check.getSpokesman());//发文人
        groupLeaderItem.setContent(check.getTeamManager());//整改班组负责人
        positionItem.setContent(check.getCheckPosition());

        inspectionReformAdapter.setTeamLeader(check.getTeamManager());
        inspectionReformAdapter.loadData(reform);

        queryFilePresenter.queryFile(id);
    }
}