/**
 * @(#)GNE47W15HelperTest.java
 * 
 * Copyright (c) 2017 GABA CORPORATION.
 */
package jp.co.gaba.ges.app.gne47w15;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jp.co.gaba.ges.domain.service.gne47w15.GNE47W15OutputBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.excel.XlsDataSet;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.util.ResourceUtils;

/**
 * [このクラスの説明を書きましょう]
 * @author NTT DATA
 * @version $Revision$
 */
@RunWith(Theories.class)
@Slf4j
public class GNE47W15HelperTest {
    private static String excelTestDataFile = "classpath:testdata/gne47w15/helper.xlsx";

    /** テストパターンのエクセルのオブジェクト */
    private static IDataSet xlsDataSet;

    /**
     * 試験条件等の記述シート名
     */
    private static final String TESTPARAM = "_TESTPARAM";

    /** 検証(assert)のエラーを集めておくList */
    private static List<AssertionError> assertionErrors = new ArrayList<AssertionError>();

    private GNE47W15Helper helper;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        File file = ResourceUtils.getFile(excelTestDataFile);
        xlsDataSet = new XlsDataSet(file);
    }
    @Data
    static class TestParam {

        /**
         * 試験項番
         */
        String no;

        // outputの設定値
        Boolean groupFlag;

        Integer scheduleStatusId;

        // formの設定値
        Boolean partnersFlag;

        Boolean orderFlag;
        Integer fixed;

        String targetMethod;

        Boolean expect;

        String memo;
    }

    @Before
    public void setUp() throws Exception {
        helper = new GNE47W15Helper();
    }

    @DataPoints
    public static TestParam[] inputTestParams() throws Exception {

        ArrayList<TestParam> tpList = new ArrayList<TestParam>();
        try {
            ITable paramTable = xlsDataSet.getTable(TESTPARAM);

            for (int i = 0; i < paramTable.getRowCount(); i++) {
                TestParam tp = new TestParam();
                tp.setNo(toStr(paramTable, i, "no"));
                tp.setGroupFlag(toBool(paramTable, i, "groupFlag"));
                tp.setScheduleStatusId(toInt(paramTable, i, "scheduleStatusId"));
                tp.setPartnersFlag(toBool(paramTable, i, "partnersFlag"));
                tp.setOrderFlag(toBool(paramTable, i, "orderFlag"));
                tp.setTargetMethod(toStr(paramTable, i, "targetMethod"));
                tp.setFixed(toInt(paramTable, i, "fixed"));
                tp.setExpect(toBool(paramTable, i, "expect"));
                tp.setMemo(toStr(paramTable, i, "memo"));
                tpList.add(tp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tpList.toArray(new TestParam[tpList.size()]);
    }

    @Theory
    public void testCreateUrl(TestParam tp) throws Exception {
        try {
            GNE47W15Form form = new GNE47W15Form();
            form.setOrderFlag(tp.getOrderFlag());
            form.setPartnersFlag(tp.getPartnersFlag());
            form.setFixed(tp.getFixed());

            GNE47W15OutputBean bean = new GNE47W15OutputBean();
            bean.setGroupFlag(tp.getGroupFlag());
            bean.setScheduleStatusId(tp.getScheduleStatusId());

            Method method = GNE47W15Helper.class.getMethod(
                    tp.getTargetMethod(), GNE47W15OutputBean.class,
                    GNE47W15Form.class);
            Boolean result = (Boolean) method.invoke(helper, bean, form);
            assertThat(result, is(tp.getExpect()));

        } catch (AssertionError e) {
            // assert失敗の場合はそのままスローせずに貯めておく。
            log.error(tp.no + " memo = " +tp.memo);
            assertionErrors.add(e);
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (assertionErrors.size() > 0) {
            for (AssertionError e : assertionErrors) {
                log.error(e.getMessage());
            }
            fail(assertionErrors.size() + "件のAssertionErrorが発生");
        }
    }

    private static Integer toInt(ITable it, int i, String name)
                                                               throws Exception {
        Object o = it.getValue(i, name);
        if (o != null) {
            return Integer.parseInt((o.toString()));
        }
        return null;
    }

    private static Boolean toBool(ITable it, int i, String name)
                                                                throws Exception {
        Object o = it.getValue(i, name);
        if (o != null) {
            return Boolean.parseBoolean((o.toString()));
        }
        return null;
    }

    private static String toStr(ITable it, int i, String name) throws Exception {
        Object o = it.getValue(i, name);
        if (o != null) {
            return o.toString();
        }
        return null;
    }
}
