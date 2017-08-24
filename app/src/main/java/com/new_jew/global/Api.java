package com.new_jew.global;

/**
 * Created by zhangpei on 2016/11/8.
 */
public class Api {
    public static final String pic = "http://192.168.3.3:8001/";//本地
    public static final String domain_name = "http://192.168.3.3:8001/api/";//本地
//        public static final String pic = "http://api2.jew315.com";//云
//    public static final String domain_name = "http://api2.jew315.com/api/";//云
    public static final String users = "users";
    //上传所有图片返回ID
    public interface create_files {

        String create_files = domain_name + "files/";

    }

    //登录
    public interface create_toten {
        String toten = domain_name + "files/token/";


    }

    //创建公司
    public interface collection_companies {

        String createCompany = domain_name + "collection_companies/";

    }

//搜索催收公司

    public interface search_companies {

        String searchCompany = domain_name + "collection_companies/";

    }

//加入公司

    public interface joinCompany {
        String joincompany = domain_name + "collection_companies/";


    }

    //库房
    public interface garages {
        String GARAGES = domain_name + "garages/";

    }

    // 债务大厅

    public interface debts {

        String debts = domain_name + "debts/";

    }
//我的

    public interface me {

        String me = domain_name + "users/me/";


    }

    //我的消息数量
    public interface message_count {
        String message_count = domain_name + "my_messages/count/";


    }

    //消息
    public interface my_message {
        String my_message = domain_name + "my_messages/";


    }
//我的公司

    public interface my_company {

        String my_company = domain_name + "my_company/";

    }

    //人员申请消息列表
    public interface reviewing_collectors {
        String reviewing_collectors = my_company.my_company + "reviewing_collectors/";


    }
    //读取文件

    public interface read_files {

        String read_files = domain_name + "files/";

    }

    public interface my_orders {
        String my_orders = domain_name + "my_orders/";


    }

    //  催记
    public interface my_collection_records {

        String my_collection_records = domain_name + "my_collection_records/";

    }

    //交接证明
    public interface my_handover_evidence {
        String my_handover_evidence = domain_name + "my_handover_evidence/";


    }

    //催收员

    public interface my_collectors {
        String my_collectors = domain_name + "my_collectors/";


    }

//公司委单

    public interface company_orders {

        String company_orders = domain_name + "company_orders/";

    }

    //注册
    public interface Register {

        String Register = domain_name + users + "/reg/";

    }

    public interface LogIn {

        String LogIn = domain_name + users + "/token/";

    }

    public interface Verify {
        String Verify = domain_name + "telephone_verify/";


    }

    public interface Me {

        String Me = domain_name + users + "/me/";

    }

    public interface my_profile {

        String my_profile = domain_name + users + "/my_profile/";

    }

    public interface debt {

        String debt = domain_name + "car_debts/";

    }

    public interface my_car_orders {

        String my_car_orders = domain_name + "my_car_orders/";

    }

    public interface collection_company_staffs {

        String collection_company_staffs = domain_name + "collection_company_staffs/";

    }

    public interface messages {

        String messages = domain_name + "messages/";
    }

//催收员

    public interface staff_car_orders {
        String staff_car_orders = domain_name + "staff_car_orders/";

    }

    //修改密码
    public interface password {
        String password = domain_name + users + "password/";

    }

    //申请延期
    public interface car_oders {
        String car_oders = domain_name + "car_orders/";


    }

    //更新
    public interface app_update {

        String app_update = domain_name + "app_update/";
    }

    /****
     * 资产处置
     */

    //登录
    public interface log_in {
        String LOGIN = domain_name + "authorizes/token/";

    }

    //债权列表
    public interface my_creditor_rights {
        String MY_CREDITOR_RIGHTS = domain_name + "my_creditor_rights/";

    }
}
