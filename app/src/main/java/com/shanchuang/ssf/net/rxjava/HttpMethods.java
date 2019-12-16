package com.shanchuang.ssf.net.rxjava;


import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.progress.HttpInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/13.
 */

public class HttpMethods {

    public static final String BASE_URL = "http://k12.w.shanchuang360.com/";
    public static final String BASE_IMG_URL = "http://zerog.ctojiayuan.com";
    public static final String SHARE_TITLE = "省师傅";
    public static final String SHARE_DESC = "省师傅";
    public static final String SHARE_URL = "http://zerog.ctojiayuan.com/api/index/activeContent?active_id=";
    public static final int SUCCESS_CODE = 1;
    private static final int DEFAULT_TIMEOUT = 30;
    private Retrofit retrofit;
    private APIService apiService;
    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        //声明日志类
        HttpInterceptor httpLoggingInterceptor = new HttpInterceptor();
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
                readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).
                addInterceptor(httpLoggingInterceptor).
                writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        apiService = retrofit.create(APIService.class);
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * k12/login/sms
     *
     * @param subscriber
     * @param mobile     手机号
     */
    public void sendCode(Subscriber<BaseBean> subscriber, String mobile, int type, int sq) {
        sub(subscriber, apiService.sendCode(mobile, type,sq));
    }
    /**
     *POST 修改手机号发送验证码 /k12/user/sms
     *
     * @param subscriber
     * @param mobile     手机号
     */
    public void user_Code(Subscriber<BaseBean> subscriber, String mobile, String uid) {
        sub(subscriber, apiService.user_Code(mobile, uid));
    }
    /**
     * 注册接口 /k12/login/register
     *
     * @param subscriber
     *
     */
    public void register(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.register(map));
    }
    /**
     * 注册接口 /k12/login/register
     *
     * @param subscriber
     *
     */
    public void order_comments(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.order_comments(map));
    }
    /**
     * 绑定手机号并注册 /k12/login/sq_register
     *
     * @param subscriber
     *
     */
    public void sq_register(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.sq_register(map));
    }
    /**
     * POST 忘记密码 /k12/login/password
     *
     * @param subscriber
     *
     */
    public void password(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.password(map));
    }
    /**
     *  首页 /k12/index/index
     *
     * @param subscriber
     *
     */
    public void index(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.index(map));
    }
    /**
     *  加入团队 /k12/team/jiaru
     *
     * @param subscriber
     *
     */
    public void jiaru(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.jiaru(map));
    }
    /**
     *   上传简历文件 /k12/team/jlfile
     *
     * @param subscriber
     *
     */
    public void jlfile(Subscriber<BaseBean> subscriber, Map<String,RequestBody> map) {
        sub(subscriber, apiService.jlfile(map));
    }
    /**
     *   POST 上传图片 /k12/user/upload
     *
     * @param subscriber
     *
     */
    public void upload(Subscriber<BaseBean> subscriber, Map<String,RequestBody> map) {
        sub(subscriber, apiService.upload(map));
    }
    /**
     *   上传头像 /k12/user/avatar
     *
     * @param subscriber
     *
     */
    public void avatar(Subscriber<BaseBean> subscriber, Map<String,RequestBody> map) {
        sub(subscriber, apiService.avatar(map));
    }
    /**
     *  课程首页轮播图 /k12/course/advert
     *
     * @param subscriber
     *
     */
    public void advert(Subscriber<BaseBean> subscriber) {
        sub(subscriber, apiService.advert());
    }
    /**
     *  POST 轮播图 /k12/goods/advert
     *
     * @param subscriber
     *
     */
    public void goods_advert(Subscriber<BaseBean> subscriber) {
        sub(subscriber, apiService.goods_advert());
    }
    /**
     * 试卷真题下载图片 /k12/xiti/advert
     *
     * @param subscriber
     *
     */
    public void exercises_advert(Subscriber<BaseBean> subscriber) {
        sub(subscriber, apiService.exercises_advert());
    }
    /**
     *  一级分类 /k12/course/cate
     *
     * @param subscriber
     *
     */
    public void video_cate(Subscriber<BaseBean> subscriber) {
        sub(subscriber, apiService.video_cate());
    }
    /**
     *   一级分类 /k12/xiti/cate
     *
     * @param subscriber
     *
     */
    public void exercises_cate(Subscriber<BaseBean> subscriber) {
        sub(subscriber, apiService.exercises_cate());
    }
    /**
     *   POST 获取分类 /k12/goods/cate
     *
     * @param subscriber
     *
     */
    public void goods_cate(Subscriber<BaseBean> subscriber) {
        sub(subscriber, apiService.goods_cate());
    }
    /**
     *   二级分类 /k12/course/cate2
     *
     * @param subscriber
     *
     */
    public void video_cate2(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.video_cate2(map));
    }
    /**
     *   二级分类 /k12/xiti/cate2
     *
     * @param subscriber
     *
     */
    public void exercises_cate2(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.exercises_cate2(map));
    }
    /**
     *   课程列表 /k12/course/lists
     *
     * @param subscriber
     *
     */
    public void course_lists(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.course_lists(map));
    }
    /**
     *   POST 我的评价 /k12/user/pinglun
     *
     * @param subscriber
     *
     */
    public void pinglun(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.pinglun(map));
    }
    /**
     *   /k12/goods/comments
     *
     * @param subscriber
     *
     */
    public void comments(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.comments(map));
    }
    /**
     *   POST 消息列表 /k12/news/lists
     *
     * @param subscriber
     *
     */
    public void news_lists(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.news_lists(map));
    }
    /**
     *   POST 消息列表 /k12/news/lists
     *
     * @param subscriber
     *
     */
    public void news_detail(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.news_detail(map));
    }
    /**
     *    我的订单列表 /k12/user/order
     *
     * @param subscriber
     *
     */
    public void me_order(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.me_order(map));
    }
    /**
     *    取消订单 /k12/user/order_del
     *
     * @param subscriber
     *
     */
    public void order_del(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.order_del(map));
    }
    /**
     *     确定收货 /k12/user/order_shou
     *
     * @param subscriber
     *
     */
    public void order_shou(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.order_shou(map));
    }
    /**
     *   团队列表 /k12/team/lists
     *
     * @param subscriber
     *
     */
    public void team_lists(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.team_lists(map));
    }
    /**
     *   POST 活动列表 /k12/huodong/lists
     *
     * @param subscriber
     *
     */
    public void huodong_lists(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.huodong_lists(map));
    }
    /**
     *   团队详情 /k12/team/detail
     *
     * @param subscriber
     *
     */
    public void team_detail(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.team_detail(map));
    }
    /**
     *   课程列表 /k12/course/lists
     *
     * @param subscriber
     *
     */
    public void exercises_lists(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.exercises_lists(map));
    }
    /**
     *    我的课程 /k12/user/my_course
     *
     * @param subscriber
     *
     */
    public void my_course(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.my_course(map));
    }
    /**
     *  POST 我的成长 /k12/user/chengzhang
     *
     * @param subscriber
     *
     */
    public void chengzhang(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.chengzhang(map));
    }
    /**
     *  POST 我的收获地址 /k12/user/address
     *
     * @param subscriber
     *
     */
    public void address(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.address(map));
    }
    /**
     *  POST 我的优惠券 /k12/user/youhuiquan
     *
     * @param subscriber
     *
     */
    public void youhuiquan(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.youhuiquan(map));
    }
    /**
     *  删除地址 /k12/user/addr_del
     *
     * @param subscriber
     *
     */
    public void addr_del(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.addr_del(map));
    }
    /**
     *  新增/修改 收获地址 /k12/user/add_addr
     *
     * @param subscriber
     *
     */
    public void add_addr(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.add_addr(map));
    }
    /**
     *  修改收获地址获取数据 /k12/user/edit_addr
     *
     * @param subscriber
     *
     */
    public void edit_addr(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.edit_addr(map));
    }
    /**
     *    商品列表 /k12/goods/lists
     *
     * @param subscriber
     *
     */
    public void goods_lists(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.goods_lists(map));
    }
    /**
     *    POST 会员中心首页 /k12/user/index
     *
     * @param subscriber
     *
     */
    public void user_index(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.user_index(map));
    }
    /**
     *    添加成长记录 /k12/user/add_chengzhang
     *
     * @param subscriber
     *
     */
    public void add_chengzhang(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.add_chengzhang(map));
    }
    /**
     *    我的成长-课程数据 /k12/user/chengzhang_data
     *
     * @param subscriber
     *
     */
    public void chengzhang_data(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.chengzhang_data(map));
    }
    /**
     * 我的积分 /k12/user/jifen
     *
     * @param subscriber
     *
     */
    public void jifen(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.jifen(map));
    }
    /**
     *    POST 会员中心首页 /k12/user/index
     *
     * @param subscriber
     *
     */
    public void user_mobile(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.user_mobile(map));
    }
    /**
     *    保存资料 /k12/user/info
     *
     * @param subscriber
     *
     */
    public void info(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.info(map));
    }
    /**
     *    商品详情 /k12/goods/detail
     *
     * @param subscriber
     *
     */
    public void goods_detail(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.goods_detail(map));
    }
    /**
     *    店铺信息 /k12/goods/store
     *
     * @param subscriber
     *
     */
    public void goods_store(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.goods_store(map));
    }
    /**
     *   优惠券列表 /k12/goods/youhuiquan
     *
     * @param subscriber
     *
     */
    public void goods_youhuiquan(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.goods_youhuiquan(map));
    }
    /**
     *   优惠券领取 /k12/goods/lingqu
     *
     * @param subscriber
     *
     */
    public void goods_lingqu(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.goods_lingqu(map));
    }
    /**
     *    加入购物车 /k12/goods/cart
     *
     * @param subscriber
     *
     */
    public void cart(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.cart(map));
    }
    /**
     *    确认订单 /k12/goods/order
     *
     * @param subscriber
     *
     */
    public void order(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.order(map));
    }
    /**
     *   下单支付 /k12/goods/buy
     *
     * @param subscriber
     *
     */
    public void ali_buy(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.ali_buy(map));
    }
    /**
     *    订单-立即支付 /k12/user/goods_pay
     *
     * @param subscriber
     *
     */
    public void goods_pay(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.goods_pay(map));
    }
    /**
     *  订单详情 /k12/user/order_detail
     *
     * @param subscriber
     *
     */
    public void order_detail(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.order_detail(map));
    }
    /**
     *   下单支付 /k12/goods/buy
     *
     * @param subscriber
     *
     */
    public void wx_buy(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.wx_buy(map));
    }
    /**
     *  订单-立即支付 /k12/user/goods_pay
     *
     * @param subscriber
     *
     */
    public void goods_wx_pay(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.goods_wx_pay(map));
    }
    /**
     *   试卷下载列表 /k12/xiti/shijuan
     *
     * @param subscriber
     *
     */
    public void shijuan(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.shijuan(map));
    }
    /**
     *   积分明细 /k12/user/score_log
     *
     * @param subscriber
     *
     */
    public void score_log(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.score_log(map));
    }
    /**
     *    我的收藏-视频 /k12/user/course
     *
     * @param subscriber
     *
     */
    public void course(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.course(map));
    }
    /**
     *   POST 我的收藏-习题 /k12/user/xiti
     *
     * @param subscriber
     *
     */
    public void xiti(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.xiti(map));
    }
    /**
     *   购物车 /k12/goods/cart_lists
     *
     * @param subscriber
     *
     */
    public void cart_lists(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.cart_lists(map));
    }
    /**
     *    购物车数量增减 /k12/goods/cart_amount
     *
     * @param subscriber
     *
     */
    public void cart_amount(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.cart_amount(map));
    }
    /**
     *   购物车删除 /k12/goods/cart_del
     *
     * @param subscriber
     *
     */
    public void cart_del(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.cart_del(map));
    }
    /**
     *    课程详情 /k12/course/detail
     *
     * @param subscriber
     *
     */
    public void course_detail(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.course_detail(map));
    }
    /**
     *    POST 活动详情 /k12/huodong/detail
     *
     * @param subscriber
     *
     */
    public void huodong_detail(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.huodong_detail(map));
    }
    /**
     *    习题详情 /k12/xiti/detail
     *
     * @param subscriber
     *
     */
    public void xiti_detail(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.xiti_detail(map));
    }
    /**
     *    POST 收藏 /k12/xiti/coll_add
     *
     * @param subscriber
     *
     */
    public void coll_add(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.coll_add(map));
    }
    /**
     *    收藏 /k12/course/coll_add
     *
     * @param subscriber
     *
     */
    public void course_add(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.course_add(map));
    }
    /**
     *    取消收藏 /k12/xiti/coll_del
     *
     * @param subscriber
     *
     */
    public void course_del(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.course_del(map));
    }
    /**
     *    取消收藏 /k12/xiti/coll_del
     *
     * @param subscriber
     *
     */
    public void coll_del(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.coll_del(map));
    }
    /**
     *    商品收藏/取消收藏 /k12/goods/coll
     *
     * @param subscriber
     *
     */
    public void goods_coll(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.goods_coll(map));
    }
    /**
     *   订单确认 /k12/course/order
     *
     * @param subscriber
     *
     */
    public void course_order(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.course_order(map));
    }
    /**
     *   支付 /k12/course/pay
     *
     * @param subscriber
     *
     */
    public void ali_pay(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.ali_pay(map));
    }
    /**
     *   支付 /k12/course/pay
     *
     * @param subscriber
     *
     */
    public void wx_pay(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.wx_pay(map));
    }
    /**
     * POST 授权登陆 /api/login/oauthLogin
     *
     * @param subscriber
     * @param id
     */
    public void oauthLogin(Subscriber<BaseBean> subscriber, String id, int type) {
        sub(subscriber, apiService.oauthLogin(id, type));
    }
    /**
     *    OST 开始做题 /k12/xiti/zuoti
     *
     * @param subscriber
     *
     */
    public void zuoti(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.zuoti(map));
    }
    /**
     *    答案解析 /k12/xiti/jiexi
     *
     * @param subscriber
     *
     */
    public void jiexi(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.jiexi(map));
    }
    /**
     *     交卷操作 /k12/xiti/jiaojuan
     *
     * @param subscriber
     *
     */
    public void jiaojuan(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.jiaojuan(map));
    }
    /**
     *    拉取所有题号 /k12/xiti/tihao
     *
     * @param subscriber
     *
     */
    public void tihao(Subscriber<BaseBean> subscriber, Map<String,Object> map) {
        sub(subscriber, apiService.tihao(map));
    }
    /**
     * 登录
     *
     * @param subscriber
     * @param account    账号
     * @param password   密码
     */
    public void login(Subscriber<BaseBean> subscriber, String account, String password) {
        sub(subscriber, apiService.login(account, password));
    }
    /**
     * 学校列表 /k12/login/school
     *
     * @param subscriber
     * @param keyword    搜索关键词
     * @param page   页码
     */
    public void school(Subscriber<BaseBean> subscriber, String keyword, int page) {
        sub(subscriber, apiService.school(keyword, page));
    }
    /**
     * 绑定学校 /k12/login/bd_school
     *
     * @param subscriber
     * @param school_id    学校id
     * @param uid   用户id
     */
    public void bd_school(Subscriber<BaseBean> subscriber, String school_id, String uid) {
        sub(subscriber, apiService.bd_school(school_id, uid));
    }
    /**
     * @param subscriber
     * @param observable
     */
    private void sub(Subscriber<BaseBean> subscriber, Observable observable) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }





    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }
}
