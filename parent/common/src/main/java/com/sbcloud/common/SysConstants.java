package com.sbcloud.common;


import java.math.BigDecimal;

public class SysConstants {
    /**
     * 默认一页数量
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 操作人 系统
     */
    public static final Integer SYS_OPERATOR_ID = 0;

    /**
     * 状态 启用
     * 
     */
    public static final Byte STATUS_ENALEB = 0;
    
    /**
     * 状态 未读
     * 
     */
    public static final Byte STATUS_UNREAD = 0;
    // 信息状态 已读
    public static final Byte STATUS_READ = 1;
    /**
     * 账号状态 正常
     * 
     */
    public static final Byte USER_ACCOUNT_STATUS_NORMAL = 1;

    /**
     * 否 如 IS_DELETE
     */
    public static final Byte NO = 0;

    /**
     * 是 如 IS_DELETE
     */
    public static final Byte YES = 1;

    public static final BigDecimal BIGDECIMAL_ZERO = BigDecimal.ZERO;
    public static final BigDecimal BIGDECIMAL_HUNDRED = BigDecimal.valueOf(100);
    public static final BigDecimal BIGDECIMAL_TENTHOUSAND = BigDecimal.valueOf(10000);

    public static final String ORDER_TYPE_TEST_NUM = "99";
    public static final byte PUSHMESSAGE_APPTYPE_DYLC = 1; // 多盈理财
    public static final byte PUSHMESSAGE_APPTYPE_YHLC = 2; // 多盈银行理财

    public static final String USERSESSION = null;

    public static final String QYUSERSESSION = null;

    public static final String CHARSET = "UTF-8";

    /**
     * ws同步数据状态,开始
     */
    public static final Byte SYNCHRONIZATION_STATUS_START = 0;

    /**
     * ws同步数据状态,結束
     */
    public static final Byte SYNCHRONIZATION_STATUS_END = 2;

    /**
     * 业务来源 同步
     */
    public static final Byte BUSINESS_SOURCE_SYN = 2;

    /**
     * 业务来源 手动添加
     */
    public static final Byte BUSINESS_SOURCE_MANU = 3;
    /**
     * 借款人类型 个人
     */
    public static final Byte BORROWER_TYPE_PERSONAL = 0;

    /**
     * 借款人类型 企业
     */
    public static final Byte BORROWER_TYPE_COMPANY = 1;

    /**
     * 附件关联主表
     */
    public static final String FILE_HOST_BORROWER = "bm_borrower";
    public static final String FILE_HOST_LOAN_BASE = "lm_loan_info";
    public static final String FILE_HOST_AUTHINFO = "lm_auth_info";
    public static final String FILE_HOST_COLLATERAL = "lm_collateral";
    // 业务归属
    public static final Integer BUSINESS_OWNERSHIP_SYN = 0;

    public static final String AREA_PARENT_CODE = "1";

    public static final Object AREA_SHI = "市辖区";

    public static final String DATA_AREA_TYPE_PERSONAL_AREA = "personal_area";

    public static final String DATA_AREA_TYPE_PERSONAL_CITY = "personal_city";

    public static final Byte ROOT = 0;
    public static final String ROOT_STR = "0";

    /**
     * 更改借款信息类型,还款银行
     */
    public static final String UPDATE_TYPE_REPAYMENT_BANK = "0";

    public static final String ADMIN = "-1";

    /**
     * 还款计划期数类型 :尾期
     */
    public static final Byte PERIOD_TYPE_LAST = 1;

    public static final Integer BAD_DUTE_DAY_COUNT =480;
    /**
     * 账号类型 0 管理员 1 资产方 2 资金方
     */
    public static final Byte ACCOUNT_TYPE_CUSTOMER = 1;
    public static final Byte ACCOUNT_TYPE_FUND = 2;
    public static final Byte ACCOUNT_TYPE_MANAGER = 0;

    public static final String DEFAULT_PASSWORD = "111111";

    /**
     * 一般頻度
     */
    public static final Byte RM_FREQUENCY_DETAIL = 0;
    /**
     * 跨事件频度统计
     */
    public static final Byte RM_FREQUENCY_DETAIL_CROSS_FREQUENCY = 1;

    /**
     * 跨事件字段比较
     */
    public static final Byte RM_FREQUENCY_DETAIL_CROSS_EVENT = 2;

    public static final String SYS_OPERATOR_NAME = "SYSYEM";

    /**
     * WS 计划,总额,允许误差金额
     */
    public static final double AMOUNT_THRESHOLD = 0.1;
    /**
     * LoanInfo表的共通状态字段(status )
     */
    public static final class LoanInfoMark {

        /**
         * 终审金额与发放金额不匹配
         */
        public static final Byte LOAN_INFO_MARK_DIS_APP_AMOUNT_NOT_MATCH = 0;
        
        
        /**
         * 订单信息小计和其他的不匹配
         */
        public static final Byte LOAN_INFO_TOTAL_NOT_MATCH =1;

        //首期不匹配
        public static final Byte LOAN_INFO_FIRST_NOT_MATCH = 2;

        //尾期不匹配
        public static final Byte LOAN_INFO_LAST_NOT_MATCH = 3;

        //期中不匹配
        public static final Byte LOAN_INFO_EVEN_NOT_MATCH = 4;

        //计划中存在不匹配的金额
        public static final Byte LOAN_INFO_PLAN_NOT_MATCH = 5;

        //计划的总金额与订单总金额不匹配
        public static final Byte LOAN_INFO_PLAN_TOTAL_NOT_MATCH = 6;
        
        
        
    }

    public static final class ApprovalStatus {

        // 未审核
        public static final Byte UN_APPROVAL = 0;

        public static final Byte APPROVAL_SUCCESS = 1;

        public static final Byte APPROVAL_FAIL = 2;

        public static final Byte CUSTOMER_APPROVAL = 3;

        public static final Byte RE_APPROVAL = 4;
    };

    public static final class PlanStatus {

        /**
         * 一般结清
         */
        public static final Byte NORMAL_REPAYED = 3;
        /**
         * 提前结清
         */
        public static final Byte EARLY_REPAYED = 4;

        /**
         * 坏账
         */
        public static final Byte BAD_DUTE = 7;
        /**
         * 逾期
         */
        public static final Byte OVERDUE = 5;
        /**
         * 逾期结清
         */
        public static final Byte OVERDUE_REPAYED = 6;
        public static final Byte BADDUTE_REPAYED = 8;
        public static final Byte UN_STARTED = 0;
        public static final Byte STARTED = 1;
    }

    public static class Sex {

        public static final Byte OTHER = 0;
        public static final Byte MAN = 1;
        public static final Byte WOMEN = 2;
    }

    public static class RedisKey {
        public static final String AREA = "AREA";
        public static final String CUSTOMER_SOURCE_CATEGORY = "CUSTOMER_SOURCE_CATEGORY";
        public static final String CUSTOMER_SOURCE = "CUSTOMER_SOURCE";
        
        public static final String SEQ = "SEQ";
        public static final String DICT = "DICT";
    }

    public static class SessionKey {

        public static final String DATA_AUTH_OPTION = "DATA_AUTH_OPTION";
    }

    public static class LoanStatus {
        
        /**
         *   4 已结清
         */
        public static final Byte NORMAL_REPAYED = 4;
        /**
         * // 7 提前结清
         */
        public static final Byte EARLY_REPAYED = 7; 
        
        /** 0 申请中*/
        public static final Byte UN_APPLY = 0;
        /**
         *  // 1 未放款
         */
        public static final Byte UN_DISBURSEMENT = 1;
        /**
         *    // 5 逾期追讨中
         */
        public static final Byte OVERDUE = 5;
        /**
         *   // 6 坏账
         */
        public static final Byte BADDUTE = 6;
      
        
     
      
     
      
        
        
        /**
         *2 已放款
         */
        public static final Byte DISBURSEMENTED = 2;

        /**
         *  3 还款计划执行中
         */
        public static final Byte STARTED = 3;
        /**
         * 逾期结清9
         */
        public static final Byte OVERDUE_REPAYED = 9;
        /**
         * 坏账结清 10
         */
        public static final Byte BADDUTE_REPAYED = 10;
    }

}
