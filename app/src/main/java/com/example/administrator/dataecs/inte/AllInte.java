package com.example.administrator.dataecs.inte;

import com.example.administrator.dataecs.model.AgreementModel;
import com.example.administrator.dataecs.model.BanckCardRequsetModel;
import com.example.administrator.dataecs.model.BankInfoModel;
import com.example.administrator.dataecs.model.CheckInfoModel;
import com.example.administrator.dataecs.model.ClassiFicationModel;
import com.example.administrator.dataecs.model.CompanyModel;
import com.example.administrator.dataecs.model.DaiKuanModel;
import com.example.administrator.dataecs.model.HelpModel;
import com.example.administrator.dataecs.model.HuanKuanInfoModle;
import com.example.administrator.dataecs.model.InformationContentModel;
import com.example.administrator.dataecs.model.IsPerfectModel;
import com.example.administrator.dataecs.model.JinJiInfoModel;
import com.example.administrator.dataecs.model.JinJiRequestModle;
import com.example.administrator.dataecs.model.LoginNewModle;
import com.example.administrator.dataecs.model.MainListHearModel;
import com.example.administrator.dataecs.model.PerfectTypeModel;
import com.example.administrator.dataecs.model.PerosonInfoModel;
import com.example.administrator.dataecs.model.PersonRequestModel;
import com.example.administrator.dataecs.model.RecordMdel;
import com.example.administrator.dataecs.model.RenZhenInfoModel;
import com.example.administrator.dataecs.model.RequsetMailModel;
import com.example.administrator.dataecs.model.SecondCommitModle;
import com.example.administrator.dataecs.model.ShenQinCommitModel;
import com.example.administrator.dataecs.model.SuperLoginModel;
import com.example.administrator.dataecs.model.TabContentModel;
import com.example.administrator.dataecs.model.TaoBaoRequestModel;
import com.example.administrator.dataecs.model.UpdateModel;
import com.example.administrator.dataecs.model.VerificationCodeModel;
import com.example.administrator.dataecs.model.WorkInfoModel;
import com.example.administrator.dataecs.model.XiangQingModel;
import com.example.administrator.dataecs.model.ZhangQiModle;
import com.example.administrator.dataecs.model.ZhiFuAutoInfoModel;
import com.example.administrator.dataecs.model.ZhiFuModel;
import com.example.administrator.dataecs.model.ZhiFuUrlModel;
import com.example.administrator.dataecs.util.BaseServer;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Administrator on 2018/7/16.
 */

public interface AllInte {

    /**
     * 首页内容
     */
    @POST(BaseServer.MAIN_HEAD)
    Call<MainListHearModel> getAllData(@Query("currentPage") int currentPage,
                                       @Query("pageSize") int pageSize);

    /**
     * 首页tab
     */
    @POST(BaseServer.MAIN_HEAD_TAB)
    Call<TabContentModel> getTabData(@Query("currentPage") int currentPage,
                                     @Query("pageSize") int pageSize,
                                     @Query("loanType") String loanType);

    /**
     * 详情页面
     */
    @POST(BaseServer.SELECT_ITME_CONTENT)
    Call<XiangQingModel> getXiangQingData(@Query("sourceId") String sourceId);

    /**
     * 分类页面
     */
    @POST(BaseServer.CLASSI_FICATION)
    Call<ClassiFicationModel> getClassFiationData(@Query("currentPage") int currentPage,
                                                  @Query("loanType") String loanType,
                                                  @Query("range") String range,
                                                  @Query("pageSize") int pageSize);

    /**
     * 验证码
     */
    @POST(BaseServer.VERIFICATION_CODE)
    Call<VerificationCodeModel> getVerification(@Query("phone") String phone,
                                                @Query("isDury") boolean isDury);

    /**
     * 登录
     */
    @POST(BaseServer.LOGIN)
    Call<SuperLoginModel> getLoginData(@Body RequestBody info);

    /**
     * 更新
     */
    @POST(BaseServer.UPDATE)
    Call<UpdateModel> getUpdateNumber();

    /**
     * 获取用户记录
     */
    @POST(BaseServer.RECORD)
    Call<RecordMdel> getRecordInfo(@Query("userid") Long userid,
                                   @Query("pageSize") int pageSize,
                                   @Query("currPage") int currPage);

    /**
     * 提交资料申请
     */
    @POST(BaseServer.COMMIT_INFO)
    Call<ShenQinCommitModel> getCommitInfo(@Query("phone") String phone,
                                           @Query("idCard") String idCard,
                                           @Query("name") String name,
                                           @Query("bankCard") String bankCard);

    /**
     * 查询审核状态
     */
    @POST(BaseServer.CHECK_TYPE)
    Call<CheckInfoModel> getCheckInfo(@Query("phone") String phone);

    /**
     * 立即申请
     */
    @POST(BaseServer.SECOND_COMMIT)
    Call<SecondCommitModle> getSecodeCommitInfo(@Query("phone") String phone);

    /**
     * 提交工作信息
     */
    @POST(BaseServer.COMPANY_COMMIT)
    Call<CompanyModel> getCompanyInfo(@Body RequestBody info);

    /**
     * 获取紧急联系人列表
     */
    @POST(BaseServer.JIN_JI_INFO)
    Call<JinJiInfoModel> getJinJiInfo(@Query("mobile") String phone,
                                      @Query("currentPage") int currentPage,
                                      @Query("pageSize") int pageSize);

    /**
     * 提交紧急联系人信息
     */
    @POST(BaseServer.COMMIT_JIN_JI_NIFO)
    Call<BanckCardRequsetModel> commitJinJiInfo(@Body RequestBody info);

    /**
     * 提交银行卡信息
     */
    @POST(BaseServer.COMMIT_BANCK_INFO)
    Call<BanckCardRequsetModel> commiBanckInfo(@Query("userid") long userid,
                                               @Query("idcard") String idcard,
                                               @Query("bankNumber") String bankNumber,
                                               @Query("phone") String phone,
                                               @Query("username") String username);

    /**
     * 获取银行卡信息
     */
    @POST(BaseServer.GET_BANCK_INFO)
    Call<BankInfoModel> getBanckInfo(@Query("userid") long userid,
                                     @Query("currPage") int currPage,
                                     @Query("pageSize") int pageSize);

    /**
     * 获取验证中心的验证状态
     */
    @POST(BaseServer.GET_INFO_PERFECT)
    Call<IsPerfectModel> getInfoPerferct(@Query("phone") String phone);

    /**
     * 获取个人信息
     */
    @POST(BaseServer.GET_PERSON_INFO)
    Call<PerosonInfoModel> getPersonInfo(@Query("phone") String phone);

    /**
     * 获取个人信息
     */
    @POST(BaseServer.GET_WORK_INFO)
    Call<WorkInfoModel> getWorkInfo(@Query("phone") String phone);

    /**
     * 获取支付宝的privatekey等信息
     */
    @POST(BaseServer.GET_ZHI_FU_BAO_INFO)
    Call<ZhiFuModel> getZhiFuInfo();

    /**
     * 获取跳转支付宝授权的URL
     */
    @POST(BaseServer.GO_ZHI_FU_URL)
    Call<ZhiFuUrlModel> getZhiFuUrl();

    /**
     * 获取跳转支付宝授权的URL
     */
    @POST(BaseServer.GO_ZHI_FU_AUTH_INFO)
    Call<ZhiFuAutoInfoModel> getZhiFuAutoInfo();

    /**
     * 提交基本信息个资质要求
     */
    @POST(BaseServer.BASE_ZHI_INFO)
    Call<JinJiRequestModle> commitBaseInfo();

    /**
     * 获取身份认证的所以信息
     */
    @POST(BaseServer.REN_ZHEN_INFO)
    Call<RenZhenInfoModel> getAllinfo(@Query("phone") String phone);

    /**
     * 注册
     */
    @POST(BaseServer.REGISTER_INFO)
    Call<LoginNewModle> registerInfo(@Body RequestBody registerInfo);

    /**
     * 注册
     */
    @POST(BaseServer.FIND_PASSWORD)
    Call<LoginNewModle> commitFindPassword(@Body RequestBody registerInfo);

    /**
     * 身份证识别
     */
    @POST(BaseServer.ID_CARD_INFO)
    Call<PersonRequestModel> commitIdCardInfo(@Body RequestBody registerInfo);

    /**
     * 人脸识别
     */
    @POST(BaseServer.FACE_FOUSE_INFO)
    Call<PersonRequestModel> commitFaceFouseInfo(@Body RequestBody registerInfo);

    /**
     * 淘宝认证
     */
    @POST(BaseServer.TAO_BAO_ATTESTATION)
    Call<TaoBaoRequestModel> getTaoBaoInfo(@Query("roleid") Long roleid);

    /**
     * 提交贷款
     */
    @POST(BaseServer.COMMIT_REPAY)
    Call<DaiKuanModel> commitDaiKuang(@Query("userid") Long userid,
                                      @Query("money") double money,
                                      @Query("repaymentTime") String repaymentTime,
                                      @Query("days") int days);

    /**
     * 帮助中心
     */
    @POST(BaseServer.HELP_CENTER)
    Call<HelpModel> getHelpInfo(@Query("roleid") Long roleid);

    /**
     * 展期申请
     */
    @POST(BaseServer.Z_Q_COMMIT)
    Call<ZhangQiModle> ZQCommit(@Body RequestBody registerInfo);

    /**
     * 还款
     */
    @POST(BaseServer.REPAY_MONEY)
    Call<ZhangQiModle> repayCommit(@Query("tbUserIndentId") long tbUserIndentId);

    /**
     * 查看是否完善
     */
    @POST(BaseServer.PERFECT_TYPE)
    Call<PerfectTypeModel> getPerfectType(@Query("userid") long userid);

    /**
     * 获取个人资料
     */
    @POST(BaseServer.GET_ALL_PERSON_INFO)
    Call<RequsetMailModel> getAllInfo(@Query("userid") long userid);

    /**
     * 获取消息
     */
    @POST(BaseServer.get_informaytion_content)
    Call<InformationContentModel> getInfomationContent(@Query("roleid") long roleid,
                                                       @Query("currPage") int currPage,
                                                       @Query("pageSize") int pageSize);

    /**
     * 获取借款天数和利率
     */
    @POST(BaseServer.GET_ALL_INFO)
    Call<HuanKuanInfoModle> getDayandOther(@Query("roleid") long roleid,
                                           @Query("userid") long userid);

    /**
     * 获取借款协议
     */
    @POST(BaseServer.AGREEMENT_INFO)
    Call<AgreementModel> getAgreementInfo(@Query("roleid") long roleid);


}
