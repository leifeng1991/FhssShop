package com.fhss.shop.base

import com.fhss.shop.bean.*
import com.zrq.base.model.BaseBean
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/9 11:03
 */

interface FhssAPI {
    companion object {
        const val BASE_URL = "http://47.93.116.213:8088/"// 基地址
    }

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/appSelectUserByphone.do")
    fun login(@Field("userPhone") userPhone: String, @Field("vcode") code: String): Call<User>

    /**
     *微信登录
     */
    @GET("FuhessApp_AdminUserModule//user/wxLogin.do")
    fun wxLogin(@Query("code") code: String): Call<BaseBean>

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/sms.do")
    fun getYzm(@Field("phone") phone: String): Call<BaseBean>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/regist.do")
    fun register(@Field("identityCard") identityCard: String, @Field("userPhone") userPhone: String, @Field("userName") userName: String, @Field("byGeneralize") generalize: String): Call<BaseBean>

    /**
     * 注册微信用户
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/registUser.do")
    fun registUser(@Field("userName") userName: String, @Field("userImg") userImg: String, @Field("openId") openId: String): Call<WeChatUserInfoBean>

    /**
     * 完善个人信息
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/saveUserInfo.do")
    fun saveUserInfo(@Field("identityCard") identityCard: String,
                     @Field("userPhone") userPhone: String,
                     @Field("userName") userName: String, @Field("byGeneralize") generalize: String): Call<User>

    /**
     * 修改用户信息
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/updateUser.do")
    fun updateUserInfo(@FieldMap map: Map<String, String>): Call<User>

    /**
     * 上传头像
     */
    @Multipart
    @POST("FuhessApp_AdminUserModule/user/upload.do")
    fun upload(@Part("userId") userId: Int, @Part file: MultipartBody.Part): Call<UploadBean>

    /**
     * 添加配送地址
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/insertUserAddrress.do")
    fun insertUserAddress(@FieldMap map: Map<String, String>): Call<BaseBean>

    /**
     * 配送地址列表
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/selectUserAddrress.do")
    fun selectUserAddress(@Field("userId") userId: String): Call<MyAddressListBean>

    /**
     * 银行卡列表
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/selectBankCard.do")
    fun selectBankCard(@Field("userId") userId: String): Call<BankListBean>

    /**
     * 添加银行卡
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/insertBankCard.do")
    fun insertBankCard(@FieldMap map: Map<String, String>): Call<BaseBean>

    /**
     * 删除银行卡
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/deleteBankCard.do")
    fun deleteBankCard(@FieldMap map: Map<String, String>): Call<BaseBean>

    /**
     * 添加收藏
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/insertGoods.do")
    fun insertCellGoods(@Field("userId") userId: String, @Field("goodsId") goodsId: String): Call<BaseBean>

    /**
     * 删除收藏
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/deleteGoods.do")
    fun deleteCellGoods(@Field("goodsId") goodsId: String): Call<BaseBean>

    /**
     * 获取收藏列表
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/selectGoodsList.do")
    fun selectCellGoodsList(@Field("userId") userId: String): Call<CollectListBean>

    /**
     * 加入购物车
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/insertCart.do")
    fun insertCart(@FieldMap map: Map<String, String>): Call<BaseBean>

    /**
     * 获取购物车商品列表
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/selectCartByUserId.do")
    fun getShopCartGoodsList(@Field("userId") userId: String): Call<ShopCartGoodsListBean>

    /**
     * 获取用户积分
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/selectUserIntegral.do")
    fun selectUserIntegral(@Field("userId") userId: String): Call<IntegralBean>

    /**
     * 会员套餐的列表
     */
    @POST("FuhessApp_AdminUserModule/user/selectALLMemberCombo.do")
    fun selectALLMemberCombo(): Call<ComBoChoiceListBean>

    /**
     * 增添用户的积分记录
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/insertUserIntegral.do")
    fun insertUserIntegral(@Field("userId") userId: String, @Field("integralType") integralType: String, @Field("record") record: String): Call<BaseBean>

    /**
     * 添加记录
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/insertMemberRecharge.do")
    fun insertMemberRecharge(@Field("userId") userId: String, @Field("price") price: String, @Field("isGathering") isGathering: String): Call<BaseBean>

    /**
     * 添加会员
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/insertMemberTime.do")
    fun insertMemberTime(@Field("userId") userId: String, @Field("timeCycle") timeCycle: String, @Field("price") price: String): Call<BaseBean>

    /**
     * 获取用户积分列表
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/selectAllByUserId.do ")
    fun selectAllByUserId(@Field("userId") userId: String): Call<BaseBean>

    /**
     * 是否有消息
     */
    @FormUrlEncoded
    @POST("FuhessApp_AdminUserModule/user/selectUserInfo.do ")
    fun selectUserInfo(@Field("userId") userId: String): Call<BaseBean>

    /**
     * 一级分类
     */
    @POST("FuhessApp_goodsModule/app/findParCla.do")
    abstract fun classifyFindAll(): Call<ClassifyListBean>

    /**
     * 二三级分类
     */
    @FormUrlEncoded
    @POST("FuhessApp_goodsModule/app/queryNext.do")
    fun classifyChild(@Field("cId") cId: String): Call<ClassifyChildListBean>

    /**
     * 查询所有品牌
     */
    @POST("FuhessApp_goodsModule/app/brandList.do")
    fun appBrandList(): Call<BrandListBean>

    /**
     * banner
     */
    @POST("FuhessApp_goodsModule/app/bannerList.do")
    fun bannerList(): Call<BannerBean>

    /**
     * 商品分页查询
     */
    @FormUrlEncoded
    @POST("FuhessApp_goodsModule/app/goodsPage.do")
    fun appGoodsList(@Field("pageNum") pageNum: String, @Field("pageSize") pageSize: String): Call<GoodsListBean>

    /**
     * 商品是否新品
     */
    @FormUrlEncoded
    @POST("FuhessApp_goodsModule/app/goodsNew.do")
    fun appGoodsNewList(@Field("pageNum") pageNum: String, @Field("pageSize") pageSize: String): Call<GoodsNewListBean>

    /**
     * 商品是否热销
     */
    @FormUrlEncoded
    @POST("FuhessApp_goodsModule/app/goodsHot.do")
    fun appGoodsHotList(@Field("pageNum") pageNum: String, @Field("pageSize") pageSize: String): Call<GoodsNewListBean>

    /**
     * 商品详情
     */
    @FormUrlEncoded
    @POST("FuhessApp_goodsModule/app/oneGoods.do")
    fun getGoodsDes(@Field("goodsId") goodsId: String): Call<GoodsDetailBean>

    /**
     * 商品规格
     */
    @FormUrlEncoded
    @POST("FuhessApp_goodsModule/app/specById.do")
    fun getGoodsSpecById(@Field("sId") goodsId: String): Call<BaseBean>

    /**
     * 根据商品名搜索商品
     */
    @FormUrlEncoded
    @POST("FuhessApp_goodsModule/app/findByGname.do")
    fun findByGname(@Field("fGoods") fGoods: String): Call<SearchGoodsListBean>

    /**
     * 根据商分类id搜索商品
     */
    @FormUrlEncoded
    @POST("FuhessApp_goodsModule/app/findByCid.do")
    fun findByCid(@Field("cId") cId: String): Call<SearchGoodsListBean>

    /**
     *
     */
    @FormUrlEncoded
    @POST("FuhessApp_orderModule/info/findByIdDesc.do")
    fun g(@Field("userId") userId: String): Call<BaseBean>

    /**
     * 创建订单
     */
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST("FuhessApp_orderModule/order/createOrder.do")
    fun createOrder(@Body info: RequestBody): Call<BaseBean>

    /**
     * 获取全部订单列表
     */
    @FormUrlEncoded
    @POST("FuhessApp_orderModule/order/findAllGoods.do")
    fun findAllGoods(@Field("userId") userId: String): Call<MyOrderListBean>

    /**
     * 获取订单列表
     */
    @FormUrlEncoded
    @POST("FuhessApp_orderModule/order/findGoods.do")
    fun getOrderList(@Field("userId") userId: String, @Field("orderStatus") orderStatus: String): Call<MyOrderListBean>

    /**
     * 物流信息
     */
    @FormUrlEncoded
    @POST("FuhessApp_orderModule/order/findLogistics.do")
    fun findLogistics(@Field("orderId") userId: String): Call<LogisticsInformationBean>

    /**
     * 获取消息列表
     */
    @FormUrlEncoded
    @POST("FuhessApp_orderModule/UserInfo/findAllByUid.do")
    fun findAllByUid(@Field("userId") userId: String): Call<MessageListBean>
}