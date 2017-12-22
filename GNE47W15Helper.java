/**
 * @(#)GNE47W15Helper.java
 *
 * Copyright (c) 2017 GABA CORPORATION.
 */
package jp.co.gaba.ges.app.gne47w15;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import jp.co.gaba.common.domain.model.codelist.ScheduleStatus;
import jp.co.gaba.common.domain.model.dto.common.TextbookDTO;
import jp.co.gaba.ges.app.GabaGesAppHelper;
import jp.co.gaba.ges.domain.model.common.CodeBean;
import jp.co.gaba.ges.domain.model.common.DynamicFieldsInfo;
import jp.co.gaba.ges.domain.model.dto.gne47w11.GetFutureTextbookInfoOutputDTO;
import jp.co.gaba.ges.domain.model.gne47w07.CounselingDynamicFieldsInfo;
import jp.co.gaba.ges.domain.service.gne47w15.GNE47W15InputBean;
import jp.co.gaba.ges.domain.service.gne47w15.GNE47W15OutputBean;
import jp.co.gaba.ges.domain.service.gne47w15.SimpleCheckListBean;
import jp.co.gaba.ges.domain.service.gne47w15.TextTableBean;
import jp.co.gaba.security.auth.ges.domain.model.UserMenuBean;
import jp.co.gaba.security.auth.ges.domain.service.userdetails.EmployeeUser;

/**
 * <pre>
 * Schedule Record(LS Meeting - Counseling - FL)機能のHelperクラス
 * [[GNE47W15]]
 * </pre>
 * @author NTT DATA
 * @version $Revision$
 */
@Component
public class GNE47W15Helper extends GabaGesAppHelper {

    /**
     * textbook level all value
     */
    private final int levelAll = -2;

    /**
     * セッションに格納しているGaba／Jaba識別情報
     */
    @Inject
    private UserMenuBean userMenuBean;

    /**
     * <pre>
     * GNE47W15OP01のINPUT_BEANを作成する
     * </pre>
     * @param form 入力フォーム
     * @param employeeUser ログインユーザ情報
     * @return GNE47W15の入力フォーム
     */
    public GNE47W15InputBean makeOP01InputBean(GNE47W15Form form,
            EmployeeUser employeeUser) {
        GNE47W15InputBean input = new GNE47W15InputBean();
        input.setScheduleId(form.getScheduleId());
        input.setGabaJabaFlg(userMenuBean.getLessonLanguage());
        input.setUserId(Integer.valueOf(employeeUser.getEmployeeInfo().getEmployeeId()));
        return input;
    }

    /**
     * <pre>
     * GNE47W15OP02のINPUT_BEANを作成する
     * </pre>
     * @param form 入力フォーム
     * @param employeeUser ログインユーザ情報
     * @return GNE47W15の入力フォーム
     */
    public GNE47W15InputBean makeOP02InputBean(GNE47W15Form form,
            EmployeeUser employeeUser) {
        GNE47W15InputBean input = new GNE47W15InputBean();

        input.setGabaJabaFlg(userMenuBean.getLessonLanguage());
        input.setUserId(Integer.valueOf(employeeUser.getEmployeeInfo().getEmployeeId()));
        if (form.getRelatedOrder() != null && 
                StringUtils.isNotBlank(form.getRelatedOrder().getId())) {
            input.setRelatedOrder(Integer.valueOf(form.getRelatedOrder().getId()));
        }
        input.setQtr(form.getQtr());
        input.setHalfway(form.getHalfway());
        input.setRetention(form.getRetention());
        input.setFLFlowVideoAccess(form.getFlFlowVideoAccess());
        input.setCurriculumVideoAccess(form.getCurriculumVideoAccess());
        input.setCheckListTable(mapCheckListBean(form.getCheckListTable()));
        if (form.getFlDatetime() != null && form.getFlDatetime().getId() != null) {
            input.setFLDateTime(Integer.valueOf(form.getFlDatetime().getId()));
        }
        input.setCommentBox(form.getCommentBox());
        if (NumberUtils.isDigits(form.getTantoCounselor().getId())) {
            input.setTantoCounselor(Integer.valueOf(form.getTantoCounselor().getId()));
        }
        input.setDate2(form.getDate2());
        if (NumberUtils.isDigits(form.getCounselingLs().getId())) {
            input.setCounselingLs(Integer.valueOf(form.getCounselingLs().getId()));
        }
        if (NumberUtils.isDigits(form.getCounselingCounselor().getId())) {
            input.setCounselingCounselor(Integer.valueOf(form.getCounselingCounselor().getId()));
        }
        input.setTextTable(this.mapTextTableBean(form.getTextTable()));
        input.setUpdateAtSchedule(form.getUpdateAtSchedule());
        input.setUpdateAtCounseling(form.getUpdateAtCounseling());
        input.setUpdateAtClientMygabaInformation(form.getUpdateAtClientMygabaInformation());
        input.setScheduleId(form.getScheduleId());
        input.setCounselingId(form.getCounselingId());
        input.setOperationButton(Integer.valueOf(form.getOperationButton()));

        return input;
    }

    /**
     * <pre>
     * GNE47W15OP05のINPUT_BEANを作成する
     * </pre>
     * @param form 入力フォーム
     * @param employeeUser ログインユーザ情報
     * @return GNE47W15の入力フォーム
     */
    public GNE47W15InputBean makeOP05InputBean(GNE47W15Form form,
            EmployeeUser employeeUser) {
        return this.makeOP02InputBean(form, employeeUser);
    }

    /**
     * <pre>
     * GNE47W15OP06のINPUT_BEANを作成する
     * </pre>
     * @param form 入力フォーム
     * @param employeeUser ログインユーザ情報
     * @return GNE47W15の入力フォーム
     */
    public GNE47W15InputBean makeOP06InputBean(GNE47W15Form form,
            EmployeeUser employeeUser) {
        GNE47W15InputBean input = new GNE47W15InputBean();

        input.setUserId(Integer.valueOf(employeeUser.getEmployeeInfo().getEmployeeId()));

        input.setScheduleId(form.getScheduleId());
        if (NumberUtils.isDigits(form.getRelatedOrder().getId())) {
            input.setRelatedOrder(Integer.valueOf(form.getRelatedOrder().getId()));
        }
        input.setQtr(form.getQtr());
        input.setHalfway(form.getHalfway());
        input.setRetention(form.getRetention());
        input.setUpdateAtSchedule(form.getUpdateAtSchedule());

        return input;
    }

    /**
     * <pre>
     * 画面表示のメインフォームを作成する
     * </pre>
     * @param output サービス側の戻り値
     * @param input 入力フォーム
     * @param errorFlg エラー戻り有無
     * @return メインフォーム
     */
    public GNE47W15Form makeMainForm(GNE47W15OutputBean output,
            GNE47W15Form input, boolean errorFlg) {
        GNE47W15Form form = new GNE47W15Form();
        form.setStatus(output.getStatus());
        form.setScheduleIconId(output.getScheduleIconId());
        form.setScheduleRecordItemName(output.getScheduleRecordItemName());
        form.setUpdatedFirstName(output.getUpdatedFirstName());
        form.setUpdatedLastName(output.getUpdatedLastName());
        form.setUpdatedDate(output.getUpdatedDate());
        form.setRelatedOrder(this.map(output.getRelatedOrder(),
                GNE47W15SimpleDropdownForm.class));
        form.setQtr(output.getQtr().getStatus());
        form.setQtrValue(output.getQtr().getValue());
        form.setHalfway(output.getHalfway().getStatus());
        form.setHalfwayValue(output.getHalfway().getValue());
        form.setRetention(output.getRetention().getStatus());
        form.setRetentionValue(output.getRetention().getValue());
        form.setTextGiven(output.getTextGiven());
        form.setContractGiven(output.getContractGiven());
        form.setMyGabaAccount(output.getMyGabaAccount());
        form.setOnlinePcl(output.getOnlinePcl());
        form.setFlFlowVideoAccess(output.getFlFlowVideoAccess());
        form.setCurriculumVideoAccess(output.getCurriculumVideoAccess());
        form.setCheckListTable(mapCheckListForm(output.getCheckListTable()));
        form.setFlDatetime(map(output.getFlDatetime(),
                GNE47W15SimpleDropdownForm.class));
        form.setInstructor(output.getInstructor());
        form.setCommentBox(output.getCommentBox());
        form.setTantoCounselor(map(output.getTantoCounselor(),
                GNE47W15SimpleDropdownForm.class));
        form.setRelatedOrder2(output.getRelatedOrder2());
        form.setDate2(output.getDate2());
        form.setCounselingLs(map(output.getCounselingLs(),
                GNE47W15SimpleDropdownForm.class));
        form.setCounselingCounselor(map(output.getCounselingCounselor(),
                GNE47W15SimpleDropdownForm.class));
        form.setNowTextBook(output.getNowTextBook());
        if (errorFlg) {
            form.setTextTable(this.setTextTable(input.getTextTable(), output.getTextbookList()));
        } else {
            form.setTextTable(this.mapTextTableForm(output));
        }
        form.setPlannedDate(output.getPlannedDate());
        form.setPlannedTantoCounselor(output.getPlannedTantoCounselor());
        form.setDoneDate(output.getDoneDate());
        form.setDoneTantoCounselor(output.getDoneTantoCounselor());
        form.setScheduleRecordDoneDate(output.getScheduleRecordDoneDate());
        form.setScheduleRecordDoneCounselor(output.getScheduleRecordDoneCounselor());
        form.setConfirmation(output.getConfirmation());
        form.setCounselingPurposeText(output.getCounselingPurposeText());
        form.setUpdateAtSchedule(output.getUpdateAtSchedule());
        form.setUpdateAtCounseling(output.getUpdateAtCounseling());
        form.setUpdateAtClientMygabaInformation(output.getUpdateAtClientMygabaInformation());

        form.setCounselingId(output.getCounselingId());
        form.setFixed(output.getFixed());
        form.setOrderFlag(output.getOrderFlag());
        form.setTextbookList(this.mapTextBookForm(output.getTextbookList()));
        form.setLevelList(output.getLevelList());

        form.setPartnersFlag(output.getPartnersFlag());
        form.setCommonMessage(output.getCommonMessage());
        form.setScheduleId(input.getScheduleId());
        form.setBackForm(input.getBackForm());
        return form;
    }

    /**
     * <pre>
     * 画面遷移のメインフォームを作成する
     * </pre>
     * @param form 入力フォーム
     * @return メインフォーム
     */
    public GNE47W15Form makeRedirectForm(GNE47W15Form form) {
        GNE47W15Form redirectForm = new GNE47W15Form();
        redirectForm.setScheduleId(form.getScheduleId());
        redirectForm.setBackForm(form.getBackForm());
        return redirectForm;
    }

    /**
     * <pre>
     * 画面動的項目の型転換
     * </pre>
     * @param list DynamicFieldsInfo
     * @return List<CounselingDynamicFieldsInfo>
     */
    public List<CounselingDynamicFieldsInfo> castDynamicFieldsInfo(
            List<? extends DynamicFieldsInfo> list) {
        List<CounselingDynamicFieldsInfo> counselingList = new ArrayList<CounselingDynamicFieldsInfo>();
        if (list != null) {
            list.stream().filter(p -> p instanceof CounselingDynamicFieldsInfo).forEach(
                    p -> counselingList.add((CounselingDynamicFieldsInfo) p));
        }
        return counselingList;
    }

    /**
     * <pre>
     * List[SimpleCheckListBean]からList[GNE47W15SimpleCheckListForm]に転換する
     * </pre>
     * @param listBean List[SimpleCheckListBean]
     * @return List [GNE47W15SimpleCheckListForm]
     */
    private List<GNE47W15SimpleCheckListForm> mapCheckListForm(
            List<SimpleCheckListBean> listBean) {
        if (listBean == null) {
            return new ArrayList<GNE47W15SimpleCheckListForm>();
        }
        List<GNE47W15SimpleCheckListForm> listForm = new ArrayList<GNE47W15SimpleCheckListForm>();
        listBean.stream().sequential().forEach(
                p -> listForm.add(map(p, GNE47W15SimpleCheckListForm.class)));
        return listForm;
    }

    /**
     * <pre>
     * List[GNE47W15SimpleCheckListForm]からList[SimpleCheckListBean]に転換する
     * </pre>
     * @param listForm List[GNE47W15SimpleCheckListForm]
     * @return List[SimpleCheckListBean]
     */
    private List<SimpleCheckListBean> mapCheckListBean(
            List<GNE47W15SimpleCheckListForm> listForm) {
        if (listForm == null) {
            return new ArrayList<SimpleCheckListBean>();
        }
        List<SimpleCheckListBean> listBean = new ArrayList<SimpleCheckListBean>();
        listForm.stream().sequential().forEach(
                p -> listBean.add(map(p, SimpleCheckListBean.class)));
        return listBean;
    }

    /**
     * <pre>
     * serviceで取得したデータを変換する。
     * List[GetFutureTextbookInfoOutputDTO]からList[GNE47W15TextTableForm]に転換する
     * </pre>
     * @param listBean List[TextTableBean]
     * @return List [GNE47W15TextTableForm]
     */
    private List<GNE47W15TextTableForm> mapTextTableForm(
            GNE47W15OutputBean output) {
        List<GetFutureTextbookInfoOutputDTO> textbookList = output.getTextTable();
        if (CollectionUtils.isEmpty(textbookList)) {
            return this.createEmptyTextTable(output.getTextbookList());
        }
        ArrayList<GNE47W15TextTableForm> listForm = new ArrayList<GNE47W15TextTableForm>();
        for (GetFutureTextbookInfoOutputDTO futureTextbook : textbookList) {
            GNE47W15TextTableForm textTable = new GNE47W15TextTableForm();
            textTable.setT1(futureTextbook.getSortOrder());
            //#11959
            Integer gabaLevel = null;
            if (futureTextbook.getGabaLevel() != null) {
                textTable.setLevel(futureTextbook.getGabaLevel());
                gabaLevel = futureTextbook.getGabaLevel();
            } else if (futureTextbook.getTextbookId() == null) {//level nullでtextbookId　nullならall
                textTable.setLevel(levelAll);
                gabaLevel = levelAll;
            }
            if (futureTextbook.getTextbookId() != null) {
                textTable.setTextBook(String.valueOf(futureTextbook.getTextbookId()));
            }
            if (futureTextbook.getEstimatedLessons() != null) {
                textTable.setLessons(String.valueOf(futureTextbook.getEstimatedLessons()));
            }
            textTable.setTextBookList(
                  this.createTextBookList(gabaLevel, output.getTextbookList()));
            listForm.add(textTable);
        }
        return listForm;
    }

    /**
     * texttableリストが空の場合、初期texttableを作成する。
     * @param textbookList 
     * @return 
     */
    private List<GNE47W15TextTableForm> createEmptyTextTable(List<TextbookDTO> textbookList) {
        ArrayList<GNE47W15TextTableForm> textTableList = new ArrayList<GNE47W15TextTableForm>();
        GNE47W15TextTableForm textTable = new GNE47W15TextTableForm();
        textTable.setT1(1);
        textTable.setLevel(levelAll);
        ArrayList<CodeBean> textBookList = new ArrayList<CodeBean>();
        if (CollectionUtils.isNotEmpty(textbookList)) {
            CodeBean firstCodeBean = new CodeBean();
            firstCodeBean.setLabel(StringUtils.EMPTY);
            textBookList.add(firstCodeBean);
            textbookList.stream().sequential().forEach(p -> {
                CodeBean codeBean = new CodeBean();
                codeBean.setValue(String.valueOf(p.getTextbookId()));
                codeBean.setLabel(p.getTextbookName());
                textBookList.add(codeBean);
            });
        }
        textTable.setTextBookList(textBookList);
        textTableList.add(textTable);
        return textTableList;
    }

    /**
     * <pre>
     * List[GNE47W15TextTableForm]からList[TextTableBean]に転換する
     * </pre>
     * @param listForm List[GNE47W15TextTableForm]
     * @return List [TextTableBean]
     */
    private List<TextTableBean> mapTextTableBean(
            List<GNE47W15TextTableForm> listForm) {
        if (listForm == null) {
            return new ArrayList<TextTableBean>();
        }
        List<TextTableBean> listBean = new ArrayList<TextTableBean>();
        listForm.stream().sequential().forEach(
                p -> listBean.add(map(p, TextTableBean.class)));
        return listBean;
    }

    /**
     * <pre>
     * List[TextbookDTO]からList[GNE47W15TextBookForm]に転換する
     * </pre>
     * @param listBean List[TextBookBean]
     * @return List [GNE47W15TextBookForm]
     */
    private List<GNE47W15TextBookForm> mapTextBookForm(
            List<TextbookDTO> dtoList) {
        if (dtoList == null) {
            return new ArrayList<GNE47W15TextBookForm>();
        }
        List<GNE47W15TextBookForm> listForm = new ArrayList<GNE47W15TextBookForm>();
        dtoList.stream().sequential().forEach(p -> {
            GNE47W15TextBookForm bean = new GNE47W15TextBookForm();
            bean.setValue1(p.getTextbookId());
            bean.setValue2(p.getGabaLevel());
            bean.setValue3(p.getTextbookName());
            listForm.add(bean);
        });
        return listForm;
    }

    /**
     * 選択中のlevelに対するtextbookリストを設定する。
     * @param tableform 
     * @param textbookList 
     * @return GNE47W15TextTableForm list
     */
    private List<GNE47W15TextTableForm> setTextTable(
            List<GNE47W15TextTableForm> tableformList, 
            List<TextbookDTO> textbookList) {
        if (CollectionUtils.isEmpty(tableformList)) {
            return tableformList;
        }
        for (int i = 0; i < tableformList.size(); i++) {
            GNE47W15TextTableForm tableform = tableformList.get(i);
            tableform.setT1(i + 1);
            tableform.setTextBookList(
                    this.createTextBookList(tableform.getLevel(), textbookList));
        }
        return tableformList;
    }

    /**
     * <pre>
     * levelに対するtextbooklistを設定する。
     * levelに対するTextbookがある場合、対象の値を入れる。
     * </pre>
     * @param level 
     * @param textbookList 
     * @return codebean list
     */
    protected List<CodeBean> createTextBookList(Integer level, List<TextbookDTO> textbookList) {
        ArrayList<CodeBean> beanList = new ArrayList<CodeBean>();
        CodeBean firstCodeBean = new CodeBean();
        firstCodeBean.setLabel(StringUtils.EMPTY);
        beanList.add(firstCodeBean);
        if (CollectionUtils.isEmpty(textbookList)) {
            return beanList;
        }
        //levelにnullが許容され、値を持っている
        if (level == null) {//"-" 
            textbookList.stream()
            .filter(p -> p.getGabaLevel() == null)
            .forEach(p -> {
                CodeBean codeBean = new CodeBean();
                codeBean.setValue(String.valueOf(p.getTextbookId()));
                codeBean.setLabel(p.getTextbookName());
                beanList.add(codeBean);
            });
        } else if (level.intValue() == levelAll) {//all
            textbookList.stream().sequential().forEach(p -> {
                CodeBean codeBean = new CodeBean();
                codeBean.setValue(String.valueOf(p.getTextbookId()));
                codeBean.setLabel(p.getTextbookName());
                beanList.add(codeBean);
            });
        } else {//level 0-10
            textbookList.stream()
            .filter(p -> p.getGabaLevel() != null)
            .filter(p -> p.getGabaLevel().equals(level))
            .forEach(p -> {
                CodeBean codeBean = new CodeBean();
                codeBean.setValue(String.valueOf(p.getTextbookId()));
                codeBean.setLabel(p.getTextbookName());
                beanList.add(codeBean);
            });
        }
        return beanList;
    }

    /**
     * <pre>
     * Doneボタン/NoNeedボタンを表示するかどうか判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return 表示する場合はtrue
     */
    public boolean resolveDoneBtnNoNeedBtnDisplayed(GNE47W15OutputBean output, GNE47W15Form form) {
        if(form.getOrderFlag() && 
                (ScheduleStatus.IN_PROGRESS.getCodeValue().equals(output.getScheduleStatusId())
                || ScheduleStatus.SENDBACK.getCodeValue().equals(output.getScheduleStatusId()))){
            if(output.getGroupFlag()){
                return true;
            }else{
                return form.getPartnersFlag();
            }
        }
        return false;
    }

    /**
     * <pre>
     * Cancelボタンを表示するかどうか判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return 表示する場合はtrue
     */
    public boolean resolveCancelBtnDisplayed(GNE47W15OutputBean output, GNE47W15Form form) {
        if(ScheduleStatus.IN_PROGRESS.getCodeValue().equals(output.getScheduleStatusId())
                || ScheduleStatus.SENDBACK.getCodeValue().equals(output.getScheduleStatusId())){
            if(form.getPartnersFlag()){
                return true;
            }else{
                return output.getGroupFlag();
            }
        }
        return false;
    }

    /**
     * <pre>
     * Saveボタンを表示するかどうか判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return 表示する場合はtrue
     */
    public boolean resolveSaveBtnDisplayed(GNE47W15OutputBean output, GNE47W15Form form) {
        if(ScheduleStatus.IN_PROGRESS.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.SENDBACK.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.DONE.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.EVALUATED.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.NONEED.getCodeValue().equals(output.getScheduleStatusId())){
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * Printボタンを表示するかどうか判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return 表示する場合はtrue
     */
    public boolean resolvePrintBtnDisplayed(GNE47W15OutputBean output, GNE47W15Form form) {
        if(ScheduleStatus.IN_PROGRESS.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.SENDBACK.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.DONE.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.EVALUATED.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.NONEED.getCodeValue().equals(output.getScheduleStatusId())){
            if(form.getPartnersFlag()){
                return true;
            }else{
                return output.getGroupFlag();
            }
        }
        return false;
    }

    /**
     * <pre>
     * ドロップダウンRelated_Orderを表示するかどうか判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return 表示する場合はtrue
     */
    public boolean resolveDRelatedOrderDisplayed(GNE47W15OutputBean output, GNE47W15Form form) {
        if(ScheduleStatus.IN_PROGRESS.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.SENDBACK.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.DONE.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.EVALUATED.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.NONEED.getCodeValue().equals(output.getScheduleStatusId())){
            if(form.getOrderFlag() && 0 == form.getFixed()){
                return true;
            }
        }
        return false;
    }

    /**
     * <pre>
     * ドロップダウンFL_Date_Timeを表示するかどうか判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return 表示する場合はtrue
     */
    public boolean resolveDFlDateTimeDisplayed(GNE47W15OutputBean output, GNE47W15Form form) {
        if(form.getOrderFlag() && 
                (ScheduleStatus.IN_PROGRESS.getCodeValue().equals(output.getScheduleStatusId())
                  || ScheduleStatus.SENDBACK.getCodeValue().equals(output.getScheduleStatusId()))){
            return true;
        }else if(ScheduleStatus.DONE.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.EVALUATED.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.NONEED.getCodeValue().equals(output.getScheduleStatusId())) {
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * ドロップダウンCounseling_LS、Counseling_Counselorを表示するかどうか判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return 表示する場合はtrue
     */
    public boolean resolveDCounselingDisplayed(GNE47W15OutputBean output, GNE47W15Form form) {
        if(ScheduleStatus.IN_PROGRESS.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.SENDBACK.getCodeValue().equals(output.getScheduleStatusId())) {
            return true;
        } else if ((ScheduleStatus.EVALUATED.getCodeValue().equals(output.getScheduleStatusId()) ||
                ScheduleStatus.DONE.getCodeValue().equals(output.getScheduleStatusId()))
                && output.getGroupFlag()) {
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * テキストCounseling_LS、Counseling_Counselorを表示するかどうか判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return 表示する場合はtrue
     */
    public boolean resolveTCounselingDisplayed(GNE47W15OutputBean output, GNE47W15Form form) {
        if(ScheduleStatus.NONEED.getCodeValue().equals(output.getScheduleStatusId())
                || ScheduleStatus.CANCEL.getCodeValue().equals(output.getScheduleStatusId())) {
            return true;
        } else if ((ScheduleStatus.DONE.getCodeValue().equals(output.getScheduleStatusId())
                || ScheduleStatus.EVALUATED.getCodeValue().equals(output.getScheduleStatusId()))
                && !output.getGroupFlag()) {
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * ScheduleStatusがCancelかを判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return Cancelの場合はtrue
     */
    public boolean resolveIsCancel(GNE47W15OutputBean output, GNE47W15Form form) {
        return ScheduleStatus.CANCEL.getCodeValue().equals(output.getScheduleStatusId());
    }

    /**
     * <pre>
     * チェックボックス QTR / C_Halfway / C_Retention を非活性にするか判定する。
     * </pre>
     * @param output GNE47W15OutputBean
     * @param form GNE47W15Form
     * @return 非活性にする場合はtrue
     */
    public boolean resolveCheckboxDisabled(GNE47W15OutputBean output, GNE47W15Form form) {
        if(!form.getOrderFlag() || 1 == form.getFixed()) {
            return true;
        }
        return false;
    }
}
