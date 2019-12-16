package com.shanchuang.ssf.net.rxjava;



import com.shanchuang.ssf.bean.ActiveDetailBean;
import com.shanchuang.ssf.bean.ActiveListBean;
import com.shanchuang.ssf.bean.AddrBean;
import com.shanchuang.ssf.bean.AddrEditBean;
import com.shanchuang.ssf.bean.AdvertMainBean;
import com.shanchuang.ssf.bean.AuthBean;
import com.shanchuang.ssf.bean.CouponBean;
import com.shanchuang.ssf.bean.CouponListBean;
import com.shanchuang.ssf.bean.DefaultBean;
import com.shanchuang.ssf.bean.ExerCisesMsgBean;
import com.shanchuang.ssf.bean.ExercisesAdvertBean;
import com.shanchuang.ssf.bean.ExercisesBean;
import com.shanchuang.ssf.bean.ExercisesListBean;
import com.shanchuang.ssf.bean.FileBean;
import com.shanchuang.ssf.bean.FinishSubjectBean;
import com.shanchuang.ssf.bean.GoodsEvaBean;
import com.shanchuang.ssf.bean.GroupBean;
import com.shanchuang.ssf.bean.GroupMsgBean;
import com.shanchuang.ssf.bean.JYShopCarBean;
import com.shanchuang.ssf.bean.MainBean;
import com.shanchuang.ssf.bean.MessageDetailsBean;
import com.shanchuang.ssf.bean.MessageListBean;
import com.shanchuang.ssf.bean.MyCourseBean;
import com.shanchuang.ssf.bean.OrderAllBean;
import com.shanchuang.ssf.bean.OrderDetailsBean;
import com.shanchuang.ssf.bean.PayBean;
import com.shanchuang.ssf.bean.SchoolDataBean;
import com.shanchuang.ssf.bean.ScoreBean;
import com.shanchuang.ssf.bean.ShopCarBean;
import com.shanchuang.ssf.bean.ShopDetailsBean;
import com.shanchuang.ssf.bean.ShopEvaBean;
import com.shanchuang.ssf.bean.ShopGoodsBean;
import com.shanchuang.ssf.bean.ShopMsgBean;
import com.shanchuang.ssf.bean.SubJectBean;
import com.shanchuang.ssf.bean.SubJectNumBean;
import com.shanchuang.ssf.bean.SubjectListBean;
import com.shanchuang.ssf.bean.TeamIntroBean;
import com.shanchuang.ssf.bean.TeamMsgBean;
import com.shanchuang.ssf.bean.UploadBean;
import com.shanchuang.ssf.bean.VideoCateBean;
import com.shanchuang.ssf.bean.VideoListBean;
import com.shanchuang.ssf.bean.VideoMainBean;
import com.shanchuang.ssf.bean.VideoPlayBean;
import com.shanchuang.ssf.bean.WxPayBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.entity.ImgBean;
import com.shanchuang.ssf.net.entity.LoginBean;
import com.shanchuang.ssf.net.entity.UserBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/13.
 */

interface APIService {
    /**
     * 发送验证码接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/login/sms")
    Observable<BaseBean> sendCode(@Field("mobile") String mobile, @Field("type") int type, @Field("sq") int sq);
    /**
     * POST 修改手机号发送验证码 /k12/user/sms
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/sms")
    Observable<BaseBean> user_Code(@Field("mobile") String mobile, @Field("uid") String uid);
    /**
     * 注册接口 /k12/login/register
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/login/register")
    Observable<BaseBean<LoginBean>> register(@FieldMap Map<String,Object> map);
    /**
     *  订单评价提交 /k12/user/order_comments
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/order_comments")
    Observable<BaseBean> order_comments(@FieldMap Map<String,Object> map);
    /**
     * 绑定手机号并注册 /k12/login/sq_register
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/login/sq_register")
    Observable<BaseBean<LoginBean>> sq_register(@FieldMap Map<String,Object> map);
    /**
     * 首页 /k12/index/index
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/index/index")
    Observable<BaseBean<MainBean>> index(@FieldMap Map<String,Object> map);
    /**
     * 加入团队 /k12/team/jiaru
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/team/jiaru")
    Observable<BaseBean> jiaru(@FieldMap Map<String,Object> map);
    /**
     *  上传简历文件 /k12/team/jlfile
     *
     * @param params
     * @return
     */
    @POST("k12/team/jlfile")
    @Multipart
    Observable<BaseBean<FileBean>> jlfile(@PartMap Map<String, RequestBody> params);
    /**
     *   POST 上传图片 /k12/user/upload
     *
     * @param params
     * @return
     */
    @POST("k12/user/upload")
    @Multipart
    Observable<BaseBean<UploadBean>> upload(@PartMap Map<String, RequestBody> params);
    /**
     *   POST 上传图片 /k12/user/upload
     *
     * @param params
     * @return
     */
    @POST("k12/user/avatar")
    @Multipart
    Observable<BaseBean<ImgBean>> avatar(@PartMap Map<String, RequestBody> params);
    /**
     * 课程首页轮播图 /k12/course/advert
     *
     * @return
     */
    @POST("k12/course/advert")
    Observable<BaseBean<AdvertMainBean>> advert();
    /**
     * POST 轮播图 /k12/goods/advert
     *
     * @return
     */
    @POST("k12/goods/advert")
    Observable<BaseBean<AdvertMainBean>> goods_advert();
    /**
     * 课程首页轮播图 /k12/course/advert
     *
     * @return
     */
    @POST("/k12/xiti/advert")
    Observable<BaseBean<ExercisesAdvertBean>> exercises_advert();
    /**
     * 一级分类 /k12/course/cate
     *
     * @return
     */
    @POST("k12/course/cate")
    Observable<BaseBean<VideoCateBean>> video_cate();
    /**
     *  一级分类 /k12/xiti/cate
     *
     * @return
     */
    @POST("k12/xiti/cate")
    Observable<BaseBean<VideoCateBean>> exercises_cate();
    /**
     * POST 获取分类 /k12/goods/cate
     *
     * @return
     */
    @POST("k12/goods/cate")
    Observable<BaseBean<VideoCateBean>> goods_cate();
    /**
     *  二级分类 /k12/course/cate2
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/course/cate2")
    Observable<BaseBean<VideoMainBean>> video_cate2(@FieldMap Map<String,Object> map);
    /**
     *  二级分类 /k12/xiti/cate2
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/cate2")
    Observable<BaseBean<VideoMainBean>> exercises_cate2(@FieldMap Map<String,Object> map);
    /**
     *  课程列表 /k12/course/lists
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/course/lists")
    Observable<BaseBean<VideoListBean>> course_lists(@FieldMap Map<String,Object> map);
    /**
     *  课程列表 /k12/course/lists
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/pinglun")
    Observable<BaseBean<ShopEvaBean>> pinglun(@FieldMap Map<String,Object> map);
    /**
     *  /k12/goods/comments
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/comments")
    Observable<BaseBean<GoodsEvaBean>> comments(@FieldMap Map<String,Object> map);
    /**
     *  课程列表 /k12/course/lists
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/news/lists")
    Observable<BaseBean<MessageListBean>> news_lists(@FieldMap Map<String,Object> map);
    /**
     * 消息详情 /k12/news/detail
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/news/detail")
    Observable<BaseBean<MessageDetailsBean>> news_detail(@FieldMap Map<String,Object> map);
    /**
     * 我的订单列表 /k12/user/order
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/order")
    Observable<BaseBean<OrderAllBean>> me_order(@FieldMap Map<String,Object> map);
    /**
     * 取消订单 /k12/user/order_del
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/order_del")
    Observable<BaseBean> order_del(@FieldMap Map<String,Object> map);
    /**
     * 确定收货 /k12/user/order_shou
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/order_shou")
    Observable<BaseBean> order_shou(@FieldMap Map<String,Object> map);
    /**
     *   团队列表 /k12/team/lists
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/team/lists")
    Observable<BaseBean<TeamIntroBean>> team_lists(@FieldMap Map<String,Object> map);
    /**
     *   POST 活动列表 /k12/huodong/lists
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/huodong/lists")
    Observable<BaseBean<ActiveListBean>> huodong_lists(@FieldMap Map<String,Object> map);
    /**
     *  团队详情 /k12/team/detail
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/team/detail")
    Observable<BaseBean<TeamMsgBean>> team_detail(@FieldMap Map<String,Object> map);
    /**
     *  试卷列表 /k12/xiti/lists
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/lists")
    Observable<BaseBean<ExercisesBean>> exercises_lists(@FieldMap Map<String,Object> map);
    /**
     * 我的课程 /k12/user/my_course
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/my_course")
    Observable<BaseBean<MyCourseBean>> my_course(@FieldMap Map<String,Object> map);
    /**
     *   POST 我的成长 /k12/user/chengzhang
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/chengzhang")
    Observable<BaseBean<GroupBean>> chengzhang(@FieldMap Map<String,Object> map);
    /**
     *     POST 我的收获地址 /k12/user/address
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/address")
    Observable<BaseBean<AddrBean>> address(@FieldMap Map<String,Object> map);
    /**
     *  POST 我的优惠券 /k12/user/youhuiquan
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/youhuiquan")
    Observable<BaseBean<CouponListBean>> youhuiquan(@FieldMap Map<String,Object> map);
    /**
     *     POST 我的收获地址 /k12/user/address
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/addr_del")
    Observable<BaseBean> addr_del(@FieldMap Map<String,Object> map);
    /**
     *    新增/修改 收获地址 /k12/user/add_addr
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/add_addr")
    Observable<BaseBean> add_addr(@FieldMap Map<String,Object> map);
    /**
     *   修改收获地址获取数据 /k12/user/edit_addr
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/edit_addr")
    Observable<BaseBean<AddrEditBean>> edit_addr(@FieldMap Map<String,Object> map);
    /**
     *   商品列表 /k12/goods/lists
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/lists")
    Observable<BaseBean<ShopGoodsBean>> goods_lists(@FieldMap Map<String,Object> map);
    /**
     *     POST 会员中心首页 /k12/user/index
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/index")
    Observable<BaseBean<UserBean>> user_index(@FieldMap Map<String,Object> map);
    /**
     *      添加成长记录 /k12/user/add_chengzhang
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/add_chengzhang")
    Observable<BaseBean> add_chengzhang(@FieldMap Map<String,Object> map);
    /**
     *      我的成长-课程数据 /k12/user/chengzhang_data
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/chengzhang_data")
    Observable<BaseBean<GroupMsgBean>> chengzhang_data(@FieldMap Map<String,Object> map);
    /**
     *    我的积分 /k12/user/jifen
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/jifen")
    Observable<BaseBean<DefaultBean>> jifen(@FieldMap Map<String,Object> map);
    /**
     *     POST 会员中心首页 /k12/user/index
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/mobile")
    Observable<BaseBean> user_mobile(@FieldMap Map<String,Object> map);
    /**
     *    保存资料 /k12/user/info
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/info")
    Observable<BaseBean> info(@FieldMap Map<String,Object> map);
    /**
     *   商品详情 /k12/goods/detail
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/detail")
    Observable<BaseBean<ShopDetailsBean>> goods_detail(@FieldMap Map<String,Object> map);
    /**
     *   店铺信息 /k12/goods/store
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/store")
    Observable<BaseBean<ShopMsgBean>> goods_store(@FieldMap Map<String,Object> map);
    /**
     *   优惠券列表 /k12/goods/youhuiquan
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/youhuiquan")
    Observable<BaseBean<CouponBean>> goods_youhuiquan(@FieldMap Map<String,Object> map);
    /**
     *  优惠券领取 /k12/goods/lingqu
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/lingqu")
    Observable<BaseBean> goods_lingqu(@FieldMap Map<String,Object> map);
    /**
     *    加入购物车 /k12/goods/cart
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/cart")
    Observable<BaseBean> cart(@FieldMap Map<String,Object> map);
    /**
     *     确认订单 /k12/goods/order
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/order")
    Observable<BaseBean<ShopCarBean>> order(@FieldMap Map<String,Object> map);
    /**
     *   下单支付 /k12/goods/buy
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/buy")
    Observable<BaseBean<PayBean>> ali_buy(@FieldMap Map<String,Object> map);
    /**
     *   下单支付 /k12/goods/buy
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/goods_pay")
    Observable<BaseBean<PayBean>> goods_pay(@FieldMap Map<String,Object> map);
    /**
     *   订单详情 /k12/user/order_detail
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/order_detail")
    Observable<BaseBean<OrderDetailsBean>> order_detail(@FieldMap Map<String,Object> map);
    /**
     *   下单支付 /k12/goods/buy
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/buy")
    Observable<BaseBean<WxPayBean>> wx_buy(@FieldMap Map<String,Object> map);
    /**
     *   下单支付 /k12/goods/buy
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/goods_pay")
    Observable<BaseBean<WxPayBean>> goods_wx_pay(@FieldMap Map<String,Object> map);
    /**
     *  试卷下载列表 /k12/xiti/shijuan
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/shijuan")
    Observable<BaseBean<ExercisesListBean>> shijuan(@FieldMap Map<String,Object> map);
    /**
     *    积分明细 /k12/user/score_log
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/score_log")
    Observable<BaseBean<ScoreBean>> score_log(@FieldMap Map<String,Object> map);
    /**
     *    我的收藏-视频 /k12/user/course
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/course")
    Observable<BaseBean<VideoListBean>> course(@FieldMap Map<String,Object> map);
    /**
     *    我的收藏-视频 /k12/user/xiti
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/user/xiti")
    Observable<BaseBean<SubjectListBean>> xiti(@FieldMap Map<String,Object> map);
    /**
     *  购物车 /k12/goods/cart_lists
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/cart_lists")
    Observable<BaseBean<JYShopCarBean>> cart_lists(@FieldMap Map<String,Object> map);
    /**
     *   购物车数量增减 /k12/goods/cart_amount
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/cart_amount")
    Observable<BaseBean> cart_amount(@FieldMap Map<String,Object> map);
    /**
     *   购物车删除 /k12/goods/cart_del
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/cart_del")
    Observable<BaseBean> cart_del(@FieldMap Map<String,Object> map);
    /**
     *   课程详情 /k12/course/detail
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/course/detail")
    Observable<BaseBean<VideoPlayBean>> course_detail(@FieldMap Map<String,Object> map);
    /**
     *   POST 活动详情 /k12/huodong/detail
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/huodong/detail")
    Observable<BaseBean<ActiveDetailBean>> huodong_detail(@FieldMap Map<String,Object> map);
    /**
     *     习题详情 /k12/xiti/detail
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/detail")
    Observable<BaseBean<ExerCisesMsgBean>> xiti_detail(@FieldMap Map<String,Object> map);
    /**
     *    POST 收藏 /k12/xiti/coll_add
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/coll_add")
    Observable<BaseBean> coll_add(@FieldMap Map<String,Object> map);
    /**
     *    收藏 /k12/course/coll_add
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/course/coll_add")
    Observable<BaseBean> course_add(@FieldMap Map<String,Object> map);
    /**
     *    取消收藏 /k12/xiti/coll_del
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/coll_del")
    Observable<BaseBean> coll_del(@FieldMap Map<String,Object> map);
    /**
     *    取消收藏 /k12/xiti/coll_del
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/course/coll_del")
    Observable<BaseBean> course_del(@FieldMap Map<String,Object> map);
    /**
     *    取消收藏 /k12/xiti/coll_del
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/goods/coll")
    Observable<BaseBean> goods_coll(@FieldMap Map<String,Object> map);
    /**
     *   订单确认 /k12/course/order
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/course/order")
    Observable<BaseBean<VideoPlayBean>> course_order(@FieldMap Map<String,Object> map);
    /**
     *   支付 /k12/course/pay
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/course/pay")
    Observable<BaseBean<PayBean>> ali_pay(@FieldMap Map<String,Object> map);
    /**
     *   支付 /k12/course/pay
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/course/pay")
    Observable<BaseBean<WxPayBean>> wx_pay(@FieldMap Map<String,Object> map);
    /**
     *  POST 忘记密码 /k12/login/password
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/login/password")
    Observable<BaseBean> password(@FieldMap Map<String,Object> map);
    /**
     *   OST 开始做题 /k12/xiti/zuoti
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/zuoti")
    Observable<BaseBean<SubJectBean>> zuoti(@FieldMap Map<String,Object> map);
    /**
     *  答案解析 /k12/xiti/jiexi
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/jiexi")
    Observable<BaseBean<SubJectBean>> jiexi(@FieldMap Map<String,Object> map);
    /**
     *   交卷操作 /k12/xiti/jiaojuan
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/jiaojuan")
    Observable<BaseBean<FinishSubjectBean>> jiaojuan(@FieldMap Map<String,Object> map);
    /**
     * 拉取所有题号 /k12/xiti/tihao
     *
     * @return
     */
    @FormUrlEncoded
    @POST("k12/xiti/tihao")
    Observable<BaseBean<SubJectNumBean>> tihao(@FieldMap Map<String,Object> map);
    /**
     * POST 授权登陆 /api/login/oauthLogin
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/k12/login/shouquan")
    Observable<BaseBean<AuthBean>> oauthLogin(@Field("openid") String id, @Field("type") int type);


    /**
     * 忘记密码短信验证码发送
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/sms/forgetsend")
    Observable<BaseBean> forgetsend(@Field("mobile") String mobile, @Field("event") String event);

    /**
     * 忘记密码
     *
     * @param mobile     手机号
     * @param repassword 重复密码
     * @param password   密码
     * @param code       验证码
     * @return
     */
    @FormUrlEncoded
    @POST("/api/login/findPassword")
    Observable<BaseBean> forgetpassword(@Field("mobile") String mobile,
                                        @Field("repassword") String repassword,
                                        @Field("password") String password,
                                        @Field("code") String code);

    /**
     * 安全密码
     *
     * @param mobile     手机号
     * @param repassword 重复密码
     * @param password   密码
     * @param code       验证码
     * @return
     */
    @FormUrlEncoded
    @POST("/api/login/setSafePwd")
    Observable<BaseBean> setSafePwd(@Field("mobile") String mobile,
                                    @Field("repassword") String repassword,
                                    @Field("password") String password,
                                    @Field("code") String code);

    /**
     * 修改密码
     *
     * @param token
     * @param repassword  重复密码
     * @param password    密码
     * @param oldpassword
     * @return
     */
    @FormUrlEncoded
    @POST("api/index/deitpassword")
    Observable<BaseBean> deitpassword(@Field("token") String token,
                                      @Field("repassword") String repassword,
                                      @Field("password") String password,
                                      @Field("old_password") String oldpassword);

    /**
     * 会员登录
     *
     * @param account  账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("k12/login/login")
    Observable<BaseBean<LoginBean>> login(@Field("mobile") String account, @Field("password") String password);
    /**
     *学校列表 /k12/login/school
     *
     * @param keyword  搜索关键词
     * @param page 页码
     * @return
     */
    @FormUrlEncoded
    @POST("k12/login/school")
    Observable<BaseBean<SchoolDataBean>> school(@Field("keyword") String keyword, @Field("page") int page);
    /**
     * 绑定学校 /k12/login/bd_school
     *
     * @param school_id    学校id
     * @param uid   用户id
     */
    @FormUrlEncoded
    @POST("k12/login/bd_school")
    Observable<BaseBean> bd_school(@Field("school_id") String school_id, @Field("uid") String uid);
    /**
     * 注册接口
     *
     * @param mobile     手机号
     * @param password   密码
     * @param repassword 重复密码
     * @param tjcode     邀请码
     * @param code       验证码
     * @return
     */
    @FormUrlEncoded
    @POST("/api/login/regUser")
    Observable<BaseBean<LoginBean>> userRegister(@Field("mobile") String mobile,
                                                 @Field("password") String password,
                                                 @Field("repassword") String repassword,
                                                 @Field("active_code") String tjcode,
                                                 @Field("code") String code);


}
