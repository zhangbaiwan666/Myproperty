package cottee.myproperty.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cottee.myproperty.R;
import cottee.myproperty.activity.ControlSubActivity;
import cottee.myproperty.activity.ResetPassWordActivity;
import cottee.myproperty.adapter.ChooseHouseAdapter;
import cottee.myproperty.adapter.ChoosePropertyAdapter;
import cottee.myproperty.adapter.PreviewBulletinAdapter;
import cottee.myproperty.adapter.SubinfoAdapter;
import cottee.myproperty.constant.BullentinBean;
import cottee.myproperty.constant.BullentinInfo;
import cottee.myproperty.constant.HouseListBean;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.activity.MainActivity;
import cottee.myproperty.activity.SetPasswordActivity;
import cottee.myproperty.constant.PropertyListBean;
import cottee.myproperty.constant.SubInfo;
import cottee.myproperty.constant.SubListBean;
import cottee.myproperty.fragment.RecentBulletinFragment;
import cottee.myproperty.manager.LoginRegisterManager;
import cottee.myproperty.uitils.HealthMap;
import cottee.myproperty.uitils.Session;
import cottee.myproperty.widgets.RefreshListView;

import static cottee.myproperty.fragment.RecentBulletinFragment.adapter;


public class LoginRegisterHandler extends Handler {
    private String email;
    private String password;
    private Context context;
    /*
     * 发送成功返回   0 （要给用户提示注意查看邮件之类的提示）
     * 发送失败返回   1
     * 验证码发送频率过快返回 2  （测试时间100秒）
     * 邮箱已存在返回 3
     * 提交字段有误返回 4
     */
    private static final int SUBMITSUCCESSFUL = 26;
    private static final int SUBMITFAILED = 1;
    private static final int SUBMITFAST = 2;
    private static final int EMAILEXIST = 3;
    private static final int EMAILWRONG = 4;
    /*
     * 执行成功返回 0
     * 验证码正确但写入失败 1
     * 验证码过期并重新发送成功返回 2
     * 验证码过期并重新发送失败返回 3
     * 无该用户记录返回 5*
     * 提交字段有误 返回 4
     */
    private static final int VERSSUCCEED = 0;
    private static final int VERSFAILD = 1;
    private static final int VERTIMOUTPASS = 2;
    private static final int VERTIMOUTFAILED = 3;
    private static final int SUBMITDNULL = 4;
    private static final int VERSWRONG = 5;


    /* * 登陆成功时候返回 0
     * 用户不存在或密码错误返回 1
     * 提交字段有误返回 4*/

    private static final int LOGINSSUCCEED = 32;
    private static final int PSWFAILD = 1;
    private static final int USERUNEXIST = 4;

    /*     * 成功返回 0
     * 验证码未通过匹配 1*/

    private static final int READINSSUCCEED = 26;
    private static final int READINFAILD = 1;

    /*  * 发送成功返回   0 （要给用户提示注意查看邮件之类的提示）
     * 发送失败返回   1
     * 验证码发送频率过快返回 2  （测试时间100秒）
     * 邮箱不存在返回 3
     * 提交字段有误返回 4*/
    private static final int SUBMITSUCCEE = 0;
    private static final int SUBMITFAIL = 1;
    private static final int SUBMITSUCCESFAST = 2;
    private static final int EMAILNULL = 3;
    private static final int PUTEMAILWRONG = 4;

    /* * 执行成功返回 0
     * 验证码错误 1
     * 验证码过期并重新发送失败返回 3
     * 无该用户记录返回 5
     * 提交字段有误 返回 4*/
    private static final int VERSTURE = 0;
    private static final int VERMISTAKE = 1;
    private static final int VEROUT = 3;
    private static final int VERNULL = 5;
    private static final int VERNWORDWRONG = 4;
    /*##提交邮件和密码
    ##提交字段：mail_address（邮件地址）   password（密码）
                * 成功返回 0
                * 失败返回 1*/
    private static final int RESETSUCCEE = 26;
    private static final int RESETFAILED = 1;
    /* #添加子账户
             ##提交字段：father_id（查询拥有房屋返还的id）$user_id（子账户id） power_service（报修权限）
              power_payment（缴费权限） power_affiche（公告权限）
             * 字段提交有误返回1
             * 其他错误暂未发现
             * 显示子账户增加返回值  3
               作用  当前房屋未选定
             成功返回0*/
    private static final int ADDSUCCESS = 1;
    private static final int ADDFAILED = 0;
    private static final int ADDFAILED_NOT_CHOOSE_HOUSE = 3;
    private String property;
    private static int property_home_id;
    private static int property_pro_id;
    /* #更换物业
     *  #提交字段
     *  session
     *  pro_id*/
    private static final int CHANGEPROSUCCESS = 1;
    private static final int CHANGEPROFAILED = 0;
    /* #更换房屋
     *  #提交字段
     *   session
     *  home_id*/
    private static final int CHANGEHOUSESUCCESS = 1;
    private static final int CHANGEHOUSEFAILED = 0;
    /* #删除子账户
     *  #提交字段
     *   session
     *  sub_id*/
    private static final int DELETESUBSUCCESS = 1;
    private static final int DELETESUBFAILED = 0;
    /* #修改子账户
     *  #提交字段
     *   session
     *   sub_remark
     *   sub_phone
     *  sub_id*/
    private static final int UPDATESUBSUCCESS = 1;
    private static final int UPDATESUBFAILED = 0;
    private static ArrayList<String> sub_id_list;
    private static ArrayList<String> sub_phone_list;
    private static ArrayList<String> sub_remark_list;
    private String choosed_property_s;


    public LoginRegisterHandler(Context context, String email, String password) {
        this.context = context;
        this.email = email;
        this.password = password;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Properties.CHECKOUT_EMAIL:
                switch (msg.arg1) {
                    case SUBMITSUCCESSFUL:
                        Toast.makeText(context, "请等待邮件", Toast.LENGTH_LONG).show();
                        break;
                    case EMAILEXIST:
                        Toast.makeText(context, "当前邮箱已注册", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case SUBMITFAILED: {
                    }
                    break;
                    case SUBMITFAST: {
                    }
                    break;
                    case EMAILWRONG: {
                    }
                    break;
                }
                break;
            case Properties.USER_LOGIN:
                switch (msg.arg1) {
                    case PSWFAILD:
                        Toast.makeText(context, "帐号或密码错误", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case LOGINSSUCCEED:
                        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("name", email);
                        editor.putString("psword", password);
                        editor.commit();
                        Toast.makeText(context, "登陆成功", Toast.LENGTH_LONG)
                                .show();
                        LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(context, "", "");
                        LoginRegisterManager loginRegisterManager = new LoginRegisterManager(context, loginRegisterHandler);
                        String session = Session.getSession();
                        loginRegisterManager.ShowAllProperty(session);

                        break;

                    case USERUNEXIST:
                        Toast.makeText(context, "当前账号不存在", Toast.LENGTH_LONG)
                                .show();
                        break;
                }

                break;
            case Properties.CHECKOUT_EMAIL_VER:
                switch (msg.arg1) {
                    case READINSSUCCEED:
                        Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        break;
                    case READINFAILD:
                        Toast.makeText(context, "注册失败", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        break;
                }

                break;
            case Properties.FINISH_USER_REGISTER:
                switch (msg.arg1) {
                    case VERSSUCCEED:
                        Intent intent = new Intent(context, SetPasswordActivity.class);
                        intent.putExtra("email", email);
                        context.startActivity(intent);
                        break;
                    case VERSFAILD:
                        Toast.makeText(context, "验证码不匹配", Toast.LENGTH_LONG).show();
                        break;
                    case VERTIMOUTPASS:
                        Toast.makeText(context, "验证码过期或邮箱不存在返回", Toast.LENGTH_LONG).show();
                        break;
                    case VERTIMOUTFAILED:
                        break;
                    case SUBMITDNULL:
                        break;
                    case VERSWRONG:
                        break;

                }
                break;
            case Properties.FORGET_PASS_WORD:
                switch (msg.arg1) {
                    case SUBMITSUCCEE:
                        Toast.makeText(context, "请等待邮件", Toast.LENGTH_LONG).show();
                        break;
                    case SUBMITFAIL:
                        break;
                    case SUBMITSUCCESFAST:
                        break;
                    case EMAILNULL:
                        break;
                    case PUTEMAILWRONG:
                        break;
                }
                break;
            case Properties.CHECKOUT_FORGET_EMAIL:
                switch (msg.arg1) {
                    case VERSTURE:
                        Intent intent = new Intent(context, ResetPassWordActivity.class);
                        intent.putExtra("forgetmail", email);
                        context.startActivity(intent);
                        Toast.makeText(context, "身份验证成功", Toast.LENGTH_LONG).show();
                        break;
                    case VERMISTAKE:
                        break;
                    case VEROUT:
                        break;
                    case VERNULL:
                        break;
                    case VERNWORDWRONG:
                        break;
                }
                break;
            case Properties.RESET_USER:
                switch (msg.arg1) {
                    case RESETSUCCEE:
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        Toast.makeText(context, "修改成功", Toast.LENGTH_LONG).show();
                        break;
                    case RESETFAILED:
                        break;
                }
                break;
            case Properties.JSON_PROPERTY:
//                Intent intent = new Intent(context, ControlSubActivity.class);
//                intent.putExtra("property_name", msg.obj.toString());
                property = msg.obj.toString();
                property_home_id = msg.arg1;
                property_pro_id = msg.arg2;
//                intent.putExtra("property_home_id", msg.arg1);
//                intent.putExtra("property_pro_id", msg.arg2);
//                context.startActivity(intent);
                break;

            case Properties.ADD_SUB_ACCOUNT_:
                switch (msg.arg1) {
                    case ADDSUCCESS:
                        LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(context, "", "");
                        LoginRegisterManager loginRegisterManager = new LoginRegisterManager(context, loginRegisterHandler);
                        loginRegisterManager.GsonSubList();
                        Toast.makeText(context, "添加子账户成功", Toast.LENGTH_LONG).show();
                        break;
                    case ADDFAILED:
                        Toast.makeText(context, "添加失败", Toast.LENGTH_LONG).show();
                        break;
                    case ADDFAILED_NOT_CHOOSE_HOUSE:
                        Toast.makeText(context, "尚未选择房屋", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        break;
                }
                break;
            case Properties.SHOW_SUB_INFO:
                sub_remark_list = new ArrayList<String>();
                sub_id_list = new ArrayList<>();
                sub_phone_list = new ArrayList<>();
                Object obj = msg.obj;
//                Serializable userBeanList = msg.getData().getSerializable("userBeanList");
//                int test = msg.getData().getInt("test");
//                System.out.println("userBeanList的强转字符串"+userBeanList);
                List<SubListBean> subListBeanslist = (List<SubListBean>) obj;
                System.out.println("张繁" + subListBeanslist);
                for (int i = 0; i < subListBeanslist.size(); i++) {
                    sub_id_list.add(subListBeanslist.get(i).getUser_id());
                    sub_phone_list.add(subListBeanslist.get(i).getPhone_num());
                    sub_remark_list.add(subListBeanslist.get(i).getRemark());
                }
                final ListView listView = (ListView) ((Activity) context).findViewById(R.id.sub_list);
                SubinfoAdapter sub_adapter = new SubinfoAdapter(
                        context, R.layout.layout_list_item, subListBeanslist);
                listView.setAdapter(sub_adapter);
                sub_adapter.notifyDataSetChanged();
                HealthMap.put("sub_list", subListBeanslist);
//                LoginRegisterHandler loginRegisterHandler = new LoginRegisterHandler(context, "","");
//                LoginRegisterManager loginRegisterManager = new LoginRegisterManager(context, loginRegisterHandler);
//                String session = Session.getSession();
//                loginRegisterManager.ShowAllHouse();

                System.out.println("传递前得sub_remark_list为" + sub_remark_list);
                break;
            case Properties.SHOW_SUB_INFO_NULL:
                LoginRegisterHandler loginRegisterHandler3 = new LoginRegisterHandler(context, "", "");
                LoginRegisterManager loginRegisterManager3 = new LoginRegisterManager(context, loginRegisterHandler3);
                String session3 = Session.getSession();
                loginRegisterManager3.ShowAllHouse();
                break;
            case Properties.ALL_PROPERTY_LIST:
                ArrayList<String> property_list = new ArrayList<String>();
                ArrayList<String> pro_id_list = new ArrayList<>();
                Object obj_property = msg.obj;
                System.out.println("userBeanList的强转字符串的test" + obj_property);
                List<PropertyListBean> propertyListBean = (List<PropertyListBean>) obj_property;
                for (int i = 0; i < propertyListBean.size(); i++) {
                    property_list.add(propertyListBean.get(i).getName());
                    pro_id_list.add(propertyListBean.get(i).getPro_id());
                }
                View layoutRight = ((Activity) context).getLayoutInflater().inflate(
                        R.layout.pop_menulist, null);
                ListView menulistRight = layoutRight.findViewById(R.id.menulist);
                ChoosePropertyAdapter listAdapter = new ChoosePropertyAdapter(
                        context, R.layout.pop_menuitem, propertyListBean);
                menulistRight.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
                System.out.println("看你俩谁空menulistRight" + menulistRight);
                System.out.println("看你俩谁空listAdapter" + listAdapter);
                HealthMap.put("property_list", property_list);
                HealthMap.put("pro_id_list", pro_id_list);
                Intent intent2 = new Intent(context, MainActivity.class);

                ((Activity) context).startActivity(intent2);
                break;
            case Properties.ALL_HOUSE_LIST:
                ArrayList<String> address_list = new ArrayList<String>();
                ArrayList<String> home_id_list = new ArrayList<>();
                Object obj_house = msg.obj;


                Object choosed_property_name = HealthMap.get("choosed_property_name");
                if (choosed_property_name == null) {
                    choosed_property_s = "尚未选择物业";
                } else {
                    choosed_property_s = choosed_property_name.toString();
                }
                List<HouseListBean> houseListBean = (List<HouseListBean>) obj_house;
                for (int i = 0; i < houseListBean.size(); i++) {
                    address_list.add(houseListBean.get(i).getAddress());
                    home_id_list.add(houseListBean.get(i).getHome_id());
                }
                Intent intent1 = new Intent(context, ControlSubActivity.class);
                intent1.putStringArrayListExtra("sub_remark_list", sub_remark_list);
                intent1.putStringArrayListExtra("sub_phone_list", sub_phone_list);
                intent1.putStringArrayListExtra("sub_id_list", sub_id_list);
                intent1.putExtra("choosed_property_name", choosed_property_s);

                intent1.putExtra("property_name", property);
                intent1.putExtra("property_pro_id", property_pro_id);
                intent1.putExtra("property_home_id", property_home_id);

                context.startActivity(intent1);

                HealthMap.put("address_list", address_list);
                HealthMap.put("home_id_list", home_id_list);
                break;
            case Properties.CHANGE_UESR_PROPERTY:
                switch (msg.arg1) {
                    case CHANGEPROSUCCESS:
                        break;
                    case CHANGEPROFAILED:
                        break;
                }
                break;
            case Properties.CHANGE_UESR_HOUSE:
                switch (msg.arg1) {
                    case CHANGEHOUSESUCCESS:
                        LoginRegisterHandler loginRegisterHandler1 = new LoginRegisterHandler(context, "", "");
                        LoginRegisterManager loginRegisterManager1 = new LoginRegisterManager(context, loginRegisterHandler1);
                        loginRegisterManager1.GsonSubList();
                        break;
                    case CHANGEHOUSEFAILED:
                        break;
                }
                break;
            case Properties.DELETE_SUB_ACCOUNT_:
                switch (msg.arg1) {
                    case DELETESUBSUCCESS:
                        LoginRegisterHandler loginRegisterHandler1 = new LoginRegisterHandler(context, "", "");
                        LoginRegisterManager loginRegisterManager1 = new LoginRegisterManager(context, loginRegisterHandler1);
//                                loginRegisterManager.GsonProperyt();
                        loginRegisterManager1.GsonSubList();
                        Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();

                        break;
                    case DELETESUBFAILED:
                        Toast.makeText(context, "删除失败", Toast.LENGTH_LONG).show();
                        break;
                }
            case Properties.UPDATE_SUB_ACCOUNT_:
                switch (msg.arg1) {
                    case UPDATESUBSUCCESS:
                        ((Activity) context).finish();
                        Intent intent = new Intent(context, ControlSubActivity.class);
                        ((Activity) context).startActivity(intent);
                        Toast.makeText(context, "修改成功", Toast.LENGTH_LONG).show();
                        break;
                    case UPDATESUBFAILED:
                        Toast.makeText(context, "修改失败,请检查输入信息是否正确", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case Properties.REUSER_LOGIN:
                switch (msg.arg1) {
                    case LOGINSSUCCEED:

                        LoginRegisterHandler loginRegisterHandler1 = new LoginRegisterHandler(context, "", "");
                        LoginRegisterManager loginRegisterManager1 = new LoginRegisterManager(context, loginRegisterHandler1);
                        String session1 = Session.getSession();
                        loginRegisterManager1.ShowAllProperty(session1);

                        break;
                    case PSWFAILD:
                        Toast.makeText(context, "本地账户为空", Toast.LENGTH_LONG)
                                .show();
                        break;
                    case USERUNEXIST:
                        Toast.makeText(context, "当前账号不存在", Toast.LENGTH_LONG)
                                .show();
                        break;
                }
            case Properties.SHOW_NOTICE:
                ArrayList<String> not_title = new ArrayList<>();
                ArrayList<String> not_message = new ArrayList<>();
                ArrayList<String> not_time = new ArrayList<>();
                Object notice_list = msg.obj;
                System.out.println("张繁周三凌晨" + notice_list);
                if (notice_list == null) {
                    break;
                } else {
                    List<BullentinInfo> noticelist = (List<BullentinInfo>) notice_list;
                    System.out.println("张繁周三凌晨转型后" + noticelist);
                    ListView list_preview_bulletin = ((Activity) context).findViewById(R.id.list_preview_bulletin);
                    PreviewBulletinAdapter previewBulletinAdapter = new PreviewBulletinAdapter(context, R.layout.layout_bulletin_list, noticelist);
                    list_preview_bulletin.setAdapter(previewBulletinAdapter);
                    previewBulletinAdapter.notifyDataSetChanged();
                    for (int i = 0; i < noticelist.size(); i++) {
                        not_title.add(noticelist.get(i).getTitle());
                        not_message.add(noticelist.get(i).getOutline());
                        not_time.add(noticelist.get(i).getCreate_time());
                    }
                    HealthMap.put("not_title", not_title);
                    HealthMap.put("not_message", not_message);
                    HealthMap.put("not_time", not_time);
                    break;
                }
            case Properties.VIEW_HOUSE_LIST:
                Object view_house = msg.obj;
                List<HouseListBean> houseList = (List<HouseListBean>) view_house;
                ListView lv_show_house = ((Activity) context).findViewById(R.id.lv_show_house);
                ChooseHouseAdapter houseListAdapter = new ChooseHouseAdapter(
                        context, R.layout.pop_menuitem, houseList);
//                //显示房屋item
//                layoutRight = ((Activity) context).getLayoutInflater().inflate(
//                        R.layout.pop_menulist, null);
//                menulistRight = (ListView) layoutRight
//                        .findViewById(R.id.menulist);
                lv_show_house.setAdapter(houseListAdapter);
                houseListAdapter.notifyDataSetChanged();

                break;
            case Properties.SHOW_RECENT_NOTICE:
                Object recent_notice = msg.obj;
                List<BullentinInfo> list_recent_notice = (List<BullentinInfo>) recent_notice;
                List<BullentinInfo> textList = RecentBulletinFragment.textList;
//                System.out.println("张繁show_list"+textList.size());
//                int size = list_recent_notice.size();
//                for (int i=0;i<=size;i++){
//                    BullentinInfo bullentinInfo = textList.get(i);
//                   textList.add(bullentinInfo);
//                }
                RefreshListView rListView = ((Activity) context).findViewById(R.id.refreshlistview);
                adapter = new RecentBulletinFragment.TabFragmentAdapter(context,R.layout.layout_bulletin_list,list_recent_notice);
                System.out.println("张繁show_notice"+textList.size());
                rListView.setAdapter(adapter);
                break;

            case Properties.SHOW_EXCEPT_LIST:
                break;

        }
    }
}

