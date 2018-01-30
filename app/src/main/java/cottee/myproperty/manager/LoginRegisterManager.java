package cottee.myproperty.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import cottee.myproperty.adapter.ChoosePropertyAdapter;
import cottee.myproperty.constant.HouseListBean;
import cottee.myproperty.constant.Properties;
import cottee.myproperty.constant.PropertyListBean;
import cottee.myproperty.constant.Repair;
import cottee.myproperty.constant.SubListBean;
import cottee.myproperty.handler.LoginRegisterHandler;
import cottee.myproperty.uitils.Session;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginRegisterManager implements Serializable {
    private Context context;
    private LoginRegisterHandler loginRegisterHandler;
    private List<Repair.ProinfoBean> proinfo;
    public LoginRegisterManager(Context context, LoginRegisterHandler handler) {
        this.context = context;
        this.loginRegisterHandler = handler;
    }
    private Handler handler;
   public  LoginRegisterManager(Handler handler){
       this.handler=handler;

   }
    /**
     * 用户登陆验证
     *
     * @param mailAddress 用户账号
     * @param password    用户密码
     * @return 情况反馈
     * 登陆成功时候 返回 0
     * 密码错误 返回 1
     * 用户不存在 返回 2
     * 提交字段有误 返回 3
     */
    public void UserLogin(final String mailAddress, final String password) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("username", mailAddress).add("password", password).build();
                    Request request = new Request.Builder().url(Properties.LOGIN_PATH).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        //str为session
                        Session.setSession(str);
                        System.out.println("服务器响应为: " + str);
                        Message message = new Message();
                        message.what = Properties.USER_LOGIN;
                        message.arg1 = str.length();
//                        SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
//                        //session存到本地
//                        SharedPreferences.Editor editor=preferences.edit();
//                        editor.putString("session", str);
//                        editor.commit();
                        loginRegisterHandler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    static final int EMAILEXIST = 1;
    static final int STRINGERROR = 3;

    /**
     static final int SUBMITSUCCESSFUL = 0;
     * 请求验证码
     *
     * @param email 邮箱字符串
     * @return 情况反馈
     * 邮箱已存在返回 1
     * 发送成功返回   0
     * 提交字段有误 返回 3
     */
    public void CheckOutEmail(final String email) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("mail_address",
                                    email).build();
                    Request request = new Request.Builder().url(Properties
                            .EMAIL_SUBMIT_PATH).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        System.out.println("服务器响应为: " + str);
                        Message msg = new Message();
                        msg.obj = Properties.CHECKOUT_EMAIL;
                        msg.arg1 = Integer.parseInt(str);
                        loginRegisterHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 提交验证码和邮件地址
     *
     * @param email       邮件地址
     * @param verPassword 验证码
     * @return
     */
    public void CheckOutEmailFirPsw(final String email, final String verPassword) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("mail_address",
                            email).add(" mail_ver", verPassword).build();
                    Request request = new Request.Builder().url(Properties
                            .MAIL_VERIFICATION_PATH).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        Message msg = new Message();
                        msg.what = Properties.FINISH_USER_REGISTER;
                        msg.arg1 = Integer.parseInt(str);
                        loginRegisterHandler.sendMessage(msg);
                        System.out.println("服务器响应为: " + str);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     * 提交邮箱和密码
     *
     * @param email    邮箱
     * @param Password 密码
     * @return 情况反馈
     * 成功返回 0
     * 验证码未通过匹配 1
     * 邮箱不存在或已过期返回 2
     * 提交字段有误 返回 3
     */
    public void RegestUser(final String email, final String Password) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder().add("mail_address", email).add(" password", Password).build();
                        Request request = new Request.Builder().url(Properties.USER_BUILD_PATH).post(requestBody).build();
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            String str = response.body().string();
                            Message msg = new Message();
                            msg.what = Properties.CHECKOUT_EMAIL_VER;
                            msg.arg1 = str.length();
                            loginRegisterHandler.sendMessage(msg);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

    }

    /**
     * 请求验证码
     ##提交字段：mail_address（邮件地址）
     * 发送成功返回   0 （要给用户提示注意查看邮件之类的提示）
     * 发送失败返回   1
     * 验证码发送频率过快返回 2  （测试时间100秒）
     * 邮箱不存在返回 3
     * 提交字段有误返回 4
     */
    public void CheckForgetedEmail(final String email){
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add
                            ("mail_address",
                                    email).build();
                    Request request = new Request.Builder().url(Properties
                            .FORGET_SUBMIT_PATH).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        System.out.println("服务器响应为: " + str);
                        Message msg = new Message();
                        msg.what = Properties.FORGET_PASS_WORD;
                        msg.arg1 = Integer.parseInt(str);
                        loginRegisterHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     ##提交验证码和邮件地址
     ##提交字段：mail_address（邮件地址）   mail_ver（验证码）
     * 执行成功返回 0
     * 验证码错误 1
     * 验证码过期并重新发送失败返回 3
     * 无该用户记录返回 5
     * 提交字段有误 返回 4
     */
    public void CheckForgetEmailPsw(final String email, final String verPassword) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("mail_address",
                            email).add(" mail_ver", verPassword).build();
                    Request request = new Request.Builder().url(Properties
                            .FORGET_VALIDATE_PATH).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        Message msg = new Message();
                        msg.what = Properties.CHECKOUT_FORGET_EMAIL;
                        msg.arg1 = Integer.parseInt(str);
                        loginRegisterHandler.sendMessage(msg);
                        System.out.println("服务器响应为: " + str);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     ##提交邮件和密码
     ##提交字段：mail_address（邮件地址）   password（密码）
     * 成功返回 0
     * 失败返回 1
     */
    public void ResetPassWord(final String email, final String Password){
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("mail_address", email).add(" password", Password).build();
                    Request request = new Request.Builder().url(Properties.RESET_USER_PATH).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        Message msg = new Message();
                        msg.what = Properties.RESET_USER;
                        msg.arg1 = str.length();
                        loginRegisterHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    /**
     ##请求物业json
     */
//    public void GsonProperyt(){
//        new Thread(){
//        public void run() {
//            try {
//                SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
////                String session=preferences.getString("session", "");
//                String session = Session.getSession();
//                OkHttpClient client = new OkHttpClient();
//                RequestBody requestBody = new FormBody.Builder().add("session",session).build();
//                Request request = new Request.Builder().url(Properties.PROPERTY_PATH).post(requestBody).build();
//                Response response = client.newCall(request).execute();
//                String json = response.body().string();
//                if (json.trim().equals("250")){
//                    String email=preferences.getString("name", "");
//                    String password=preferences.getString("psword", "");
//                    final String id = email;
//                    final String psw = password;
//                    OkHttpClient client1 = new OkHttpClient();
//                    RequestBody requestBody1 = new FormBody.Builder().add("username", id).add("password", psw).build();
//                    Request request1 = new Request.Builder().url(Properties.LOGIN_PATH).post(requestBody1).build();
//                    Response response1 = client1.newCall(request1).execute();
//                    if (response1.isSuccessful()) {
//                        //获得新的session
//                        String str = response1.body().string();
//                        Session.setSession(str);
////                                    SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
//                        //新session存到本地
////                        SharedPreferences.Editor editor=preferences.edit();
////                        editor.putString("session", str);
////                        editor.commit();
//                        GsonProperyt();
//                    }
//
//                }
//                else {
//                    Log.d("Property","Json" + json);
//                    parseJSONWithpropertyGSON(json);
//                }
//                //Json的解析类对象
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        private void parseJSONWithpropertyGSON(String jsonData) {
//            //使用轻量级的Gson解析得到的json
//            Gson gson = new Gson();
//            List<PropertyBean> appList = gson.fromJson(jsonData, new TypeToken<List<PropertyBean>>() {}.getType());
//            for (PropertyBean app : appList) {
//                Log.d("Property","appList" + appList);
//                //控制台输出结果，便于查看
//                Log.d("MainActivity", "name" + app.getName());
//                Log.d("MainActivity", "home_id" + app.getHomeid());
//                Log.d("MainActivity", "pro_id" + app.getProid());
//                Message msg = new Message();
//                msg.what=Properties.JSON_PROPERTY;
//                msg.obj=app.getName();
//                msg.arg1=Integer.parseInt(app.getProid());
//                msg.arg2=Integer.parseInt(app.getHomeid());
//                loginRegisterHandler.sendMessage(msg);
//            }
//        }
//    }.start();
//    }
    /**
     ##添加子账户
     */
    public void AddSubAccount(final String sub_id,final String sub_remark,final String sub_phone) {
        new Thread() {
            @Override
            public void run() {
                try {
                    //从SharedPreferences中获得登陆时存的session
                    SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
//                    String session=preferences.getString("session", "");
                    String session = Session.getSession();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("son_id",sub_id)
                            .add("remark",sub_remark)
                            .add("phone",sub_phone)
                            .add("session",session).build();
                    //把session add到requestbody中
                    Log.d("添加子账户的remark", "pro_id" +sub_remark);
                    Log.d("添加子账户phone", "home_id" +sub_phone);
                    Request request = new Request.Builder().url(Properties.ADD_SUB_ACCOUNT).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                        String recode = response.body().string();
                    String recode_trim = recode.trim();
                    //如果返回250，表示session过期，如果session通过返回之前正常的0,1逻辑
                        if (recode_trim.equals("250")){
                            //本地做重新登录得动作
                            String email=preferences.getString("name", "");
                            String password=preferences.getString("psword", "");
                            final String id = email;
                            final String psw = password;
                            OkHttpClient client1 = new OkHttpClient();
                            RequestBody requestBody1 = new FormBody.Builder().add("username", id).add("password", psw).build();
                            Request request1 = new Request.Builder().url(Properties.LOGIN_PATH).post(requestBody1).build();
                            Response response1 = client1.newCall(request1).execute();
                            if (response1.isSuccessful()) {
                                //获得新的session
                                String str = response1.body().string();
//                                    SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
                                //新session存到本地
//                                SharedPreferences.Editor editor=preferences.edit();
//                                editor.putString("session", str);
//                                editor.commit();
                                Session.setSession(str);
                                AddSubAccount(sub_id,sub_remark,sub_phone);
                            }

                        }else {
                            //sesssion没过期执行的正常逻辑
                            Message msg = new Message();
                            msg.what = Properties.ADD_SUB_ACCOUNT_;
                            msg.arg1 = Integer.parseInt(recode_trim);
                            loginRegisterHandler.sendMessage(msg);
                            Log.d("MainActivity", "返回值" + recode);
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
//    public void FixHouse() {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    RequestBody requestBody = new FormBody.Builder().build();
//                    Request request = new Request.Builder().url(Properties.FIX_HOUSE).post(requestBody).build();
//                    Response response = client.newCall(request).execute();
//                    if (response.isSuccessful()) {
//                        String str = response.body().string();
//                        Gson gson = new Gson();
//                        FixBean fixBean = gson.fromJson(str, FixBean.class);
//                        Object o = fixBean.getProject();
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }

    public void GsonSubList(){ new Thread(){
        public void run() {
            try {
                SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
//                String session=preferences.getString("session", "");
                String session = Session.getSession();
                System.out.println("当前session为"+session);
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder().add("session",session).build();
                Request request = new Request.Builder().url(Properties.SUB_LIST_PATH).post(requestBody).build();
                Response response = client.newCall(request).execute();
                String json = response.body().string();
                if (json.trim().equals("250")){
                    String email=preferences.getString("name", "");
                    String password=preferences.getString("psword", "");
                    final String id = email;
                    final String psw = password;
                                OkHttpClient client1 = new OkHttpClient();
                                RequestBody requestBody1 = new FormBody.Builder().add("username", id).add("password", psw).build();
                                Request request1 = new Request.Builder().url(Properties.LOGIN_PATH).post(requestBody1).build();
                                Response response1 = client1.newCall(request1).execute();
                                if (response1.isSuccessful()) {
                                    //获得新的session
                                    String str = response1.body().string();
//                                    SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
                                    //新session存到本地
//                                    SharedPreferences.Editor editor=preferences.edit();
//                                    editor.putString("session", str);
//                                    editor.commit();
                                    Session.setSession(str);
                                    GsonSubList();
                                }

                }
                else if (json.trim().equals("3")){
                    Message msg = new Message();
                    msg.what=Properties.SHOW_SUB_INFO_NULL;
                    loginRegisterHandler.sendMessage(msg);
                }else {

                    parseJSONWithGSON(json);
                }
                Log.d("Property","Json" + json);
                //Json的解析类对象
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void parseJSONWithGSON(final String jsonData) {
            //使用轻量级的Gson解析得到的json
            JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
            System.out.println("获得服务器器器器的json数据"+jsonObject);
            System.out.println("获得服务器器器器的json数据"+jsonObject);
            System.out.println("获得服务器器器器的json数据"+jsonObject);
            System.out.println("获得服务器器器器的json数据"+jsonObject);
            System.out.println("获得服务器器器器的json数据"+jsonObject);
            System.out.println("获得服务器器器器的json数据"+jsonObject);
            System.out.println("获得服务器器器器的json数据"+jsonObject);
            System.out.println("获得服务器器器器的json数据"+jsonObject);

            JsonArray son_show = jsonObject.getAsJsonArray("son_show");
            System.out.println(son_show);
            Gson gson = new Gson();
                List<SubListBean> userBeanList = gson.fromJson(son_show, new TypeToken<List<SubListBean>>() {
                }.getType());

            System.out.println("userBeanList"+userBeanList);
            Message msg = new Message();
            msg.what=Properties.SHOW_SUB_INFO;
            msg.obj=userBeanList;
            System.out.println("SUB_LIST的传出字符串"+userBeanList);
            loginRegisterHandler.sendMessage(msg);
        }
    }.start();
    }
    private void login() {
        SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String email=preferences.getString("name", "");
        String password=preferences.getString("psword", "");
        final String id = email;
        final String psw = password;
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("username", id).add("password", psw).build();
                    Request request = new Request.Builder().url(Properties.LOGIN_PATH).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        //获得新的session
                        String str = response.body().string();
                        SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
                        //新session存到本地
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("session", str);
                        editor.commit();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public static void deleteTable() {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("  http://thethreestooges.cn/consumer/test/temp_delete.php").get().build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        System.out.println(response.body().string());
                    }
                    /*request= new Request.Builder().url(" " + "http://thethreestooges.cn/consumer/test/user_delete.php").get().build();
                    Response execute = client.newCall(request).execute();*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void sendRequestOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("http://120.25.96.141/temp/file/project_static.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    System.out.println(response);
                    ParseJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    public void ParseJSONObject(final String responseData) {
        Gson gson = new Gson();
        proinfo = gson.fromJson(responseData, Repair
                .class).getProinfo();
        for (int i = 0; i < 7; i++) {
            System.out.println(proinfo.get(i).getKind());
            Message message = new Message();
            message.what = 0;
            message.obj = proinfo.get(i).getKind();
           handler.sendMessage(message);
        }


    }
    /**
     ##切换物业
     */
    public void ShowAllProperty(final String session){
        new Thread() {
            @Override
            public void run() {
                try {
                    SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
                    String session = Session.getSession();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("session",session).build();
                    //把session add到requestbody中
                    Request request = new Request.Builder().url(Properties.SHOW_ALL_PROPERTY).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String recode = response.body().string();
                    String recode_trim = recode.trim();
                    System.out.println("获得的物业表是"+recode_trim);
                    System.out.println("获得的物业表是"+recode_trim);
                    System.out.println("获得的物业表是"+recode_trim);
                    //如果返回250，表示session过期，如果session通过返回之前正常的0,1逻辑
                    if (recode_trim.equals("250")){
                        //本地做重新登录得动作
                        String email=preferences.getString("name", "");
                        String password=preferences.getString("psword", "");
                        final String id = email;
                        final String psw = password;
                        OkHttpClient client1 = new OkHttpClient();
                        RequestBody requestBody1 = new FormBody.Builder().add("username", id).add("password", psw).build();
                        Request request1 = new Request.Builder().url(Properties.LOGIN_PATH).post(requestBody1).build();
                        Response response1 = client1.newCall(request1).execute();
                        if (response1.isSuccessful()) {
                            //获得新的session
                            String str = response1.body().string();
//                                    SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
                            //新session存到本地
//                                SharedPreferences.Editor editor=preferences.edit();
//                                editor.putString("session", str);
//                                editor.commit();
                            Session.setSession(str);
                            ShowAllProperty(session);
                        }

                    }else {
                      parseJSONWithGSON(recode_trim);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            private void parseJSONWithGSON(String recode_trim) {
                //使用轻量级的Gson解析得到的json
                JsonObject jsonObject = new JsonParser().parse(recode_trim).getAsJsonObject();
                JsonArray son_show = jsonObject.getAsJsonArray("pro_list");
                System.out.println(son_show);
                Gson gson = new Gson();
                List<PropertyListBean> propertyListBeans = gson.fromJson(son_show, new TypeToken<List<PropertyListBean>>() {
                }.getType());

                System.out.println("propertyListBeans"+propertyListBeans);
                Message msg = new Message();
                msg.what=Properties.ALL_PROPERTY_LIST;
                msg.obj=propertyListBeans;
                System.out.println("propertyListBeans的传出字符串"+propertyListBeans);
                loginRegisterHandler.sendMessage(msg);
            }
        }.start();

    }

    public void ChooseProperty(final String session,final String pro_id){
        new Thread() {
            @Override
            public void run() {
                try {
                    SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("session", session)
                            .add(" pro_id", pro_id).build();
                    Request request = new Request.Builder().url(Properties.CHOOSE_PROPERTY)
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String recode = response.body().string();
                    String recode_trim = recode.trim();
                    if (recode_trim.equals("250")){
                        //本地做重新登录得动作
                        String email=preferences.getString("name", "");
                        String password=preferences.getString("psword", "");
                        final String id = email;
                        final String psw = password;
                        OkHttpClient client1 = new OkHttpClient();
                        RequestBody requestBody1 = new FormBody.Builder().add("username", id).add("password", psw).build();
                        Request request1 = new Request.Builder().url(Properties.LOGIN_PATH).post(requestBody1).build();
                        Response response1 = client1.newCall(request1).execute();
                        if (response1.isSuccessful()) {
                            //获得新的session
                            String str = response1.body().string();
//                                    SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
                            //新session存到本地
//                                SharedPreferences.Editor editor=preferences.edit();
//                                editor.putString("session", str);
//                                editor.commit();
                            Session.setSession(str);
                            ChooseProperty(session,pro_id);
                        }else{
                            Message msg = new Message();
                            msg.what = Properties.CHANGE_UESR_PROPERTY;
                            msg.arg1 = Integer.parseInt(recode_trim);
                            loginRegisterHandler.sendMessage(msg);
                            Log.d("MainActivity", "返回值" + recode);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void ShowAllHouse(final String session){
        new Thread() {
            @Override
            public void run() {
                try {
                    SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
                    String session = Session.getSession();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("session",session).build();
                    //把session add到requestbody中
                    Request request = new Request.Builder().url(Properties.SHOW_ALL_HOUSE).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String recode = response.body().string();
                    String recode_trim = recode.trim();
                    System.out.println("获得的房屋表是"+recode_trim);
                    System.out.println("获得的房屋表是"+recode_trim);
                    System.out.println("获得的房屋表是"+recode_trim);
                    //如果返回250，表示session过期，如果session通过返回之前正常的0,1逻辑
                    if (recode_trim.equals("250")){
                        //本地做重新登录得动作
                        String email=preferences.getString("name", "");
                        String password=preferences.getString("psword", "");
                        final String id = email;
                        final String psw = password;
                        OkHttpClient client1 = new OkHttpClient();
                        RequestBody requestBody1 = new FormBody.Builder().add("username", id).add("password", psw).build();
                        Request request1 = new Request.Builder().url(Properties.LOGIN_PATH).post(requestBody1).build();
                        Response response1 = client1.newCall(request1).execute();
                        if (response1.isSuccessful()) {
                            //获得新的session
                            String str = response1.body().string();
//                                    SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
                            //新session存到本地
//                                SharedPreferences.Editor editor=preferences.edit();
//                                editor.putString("session", str);
//                                editor.commit();
                            Session.setSession(str);
                            ShowAllProperty(session);
                        }

                    }else {
                        parseJSONWithGSON(recode_trim);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            private void parseJSONWithGSON(String recode_trim) {
                //使用轻量级的Gson解析得到的json
                JsonObject jsonObject = new JsonParser().parse(recode_trim).getAsJsonObject();
                JsonArray son_show = jsonObject.getAsJsonArray("home_list");
                System.out.println(son_show);
                Gson gson = new Gson();
                List<HouseListBean> houseListBeans = gson.fromJson(son_show, new TypeToken<List<HouseListBean>>() {
                }.getType());

                System.out.println("propertyListBeans"+houseListBeans);
                Message msg = new Message();
                msg.what=Properties.ALL_HOUSE_LIST;
                msg.obj=houseListBeans;
                System.out.println("propertyListBeans的传出字符串"+houseListBeans);
                loginRegisterHandler.sendMessage(msg);
            }
        }.start();

    }

    public void ChooseHouse(final String session,final String home_id){
        new Thread() {
            @Override
            public void run() {
                try {
                    SharedPreferences preferences=context.getSharedPreferences("user", Context.MODE_PRIVATE);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("session", session)
                            .add("home_id", home_id).build();
                    Request request = new Request.Builder().url(Properties.CHOOSE_ALL_HOUSE)
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String recode = response.body().string();
                    String recode_trim = recode.trim();
                    System.out.println("选择的房屋信息服务器返回值"+recode);
                    System.out.println("选择的房屋信息服务器返回值"+recode);
                    System.out.println("选择的房屋信息服务器返回值"+recode);
                    System.out.println("选择的房屋信息服务器返回值"+recode);
                    System.out.println("选择的房屋信息服务器返回值"+recode);
                    System.out.println("选择的房屋信息服务器返回值"+recode);
                    System.out.println("选择的房屋信息服务器返回值"+recode);
                    System.out.println("选择的房屋信息服务器返回值"+recode);
                    System.out.println("选择的房屋信息服务器返回值"+recode);
                    if (recode_trim.equals("250")){
                        //本地做重新登录得动作
                        String email=preferences.getString("name", "");
                        String password=preferences.getString("psword", "");
                        final String id = email;
                        final String psw = password;
                        OkHttpClient client1 = new OkHttpClient();
                        RequestBody requestBody1 = new FormBody.Builder().add("username", id).add("password", psw).build();
                        Request request1 = new Request.Builder().url(Properties.LOGIN_PATH).post(requestBody1).build();
                        Response response1 = client1.newCall(request1).execute();
                        if (response1.isSuccessful()) {
                            //获得新的session
                            String str = response1.body().string();
//                                    SharedPreferences preferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
                            //新session存到本地
//                                SharedPreferences.Editor editor=preferences.edit();
//                                editor.putString("session", str);
//                                editor.commit();
                            Session.setSession(str);
                            ChooseHouse(session,home_id);
                        }else{
                            Message msg = new Message();
                            msg.what = Properties.CHANGE_UESR_HOUSE;
                            msg.arg1 = Integer.parseInt(recode_trim);
                            loginRegisterHandler.sendMessage(msg);
                            Log.d("MainActivity", "返回值" + recode);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

