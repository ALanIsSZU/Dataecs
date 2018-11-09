package com.example.administrator.dataecs.inte;

import com.example.administrator.dataecs.model.BankInfoModel;
import com.example.administrator.dataecs.model.CheckInfoModel;
import com.example.administrator.dataecs.model.ClassiFicationModel;
import com.example.administrator.dataecs.model.CompanyModel;
import com.example.administrator.dataecs.model.IsPerfectModel;
import com.example.administrator.dataecs.model.JinJiInfoModel;
import com.example.administrator.dataecs.model.JinJiRequestModle;
import com.example.administrator.dataecs.model.LoginNewModle;
import com.example.administrator.dataecs.model.MainListHearModel;
import com.example.administrator.dataecs.model.PerosonInfoModel;
import com.example.administrator.dataecs.model.RecordMdel;
import com.example.administrator.dataecs.model.RenZhenInfoModel;
import com.example.administrator.dataecs.model.SecondCommitModle;
import com.example.administrator.dataecs.model.ShenQinCommitModel;
import com.example.administrator.dataecs.model.TabContentModel;
import com.example.administrator.dataecs.model.UpdateModel;
import com.example.administrator.dataecs.model.VerificationCodeModel;
import com.example.administrator.dataecs.model.WorkInfoModel;
import com.example.administrator.dataecs.model.XiangQingModel;
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
    Call<MainListHearModel> getAllData(@Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * 首页tab
     */
    @POST(BaseServer.MAIN_HEAD_TAB)
    Call<TabContentModel> getTabData(@Query("currentPage") int currentPage, @Query("pageSize") int pageSize, @Query("loanType") String loanType);

    /**
     * 详情页面
     */
    @POST(BaseServer.SELECT_ITME_CONTENT)
    Call<XiangQingModel> getXiangQingData(@Query("sourceId") String sourceId);

    /**
     * 分类页面
     */
    @POST(BaseServer.CLASSI_FICATION)
    Call<ClassiFicationModel> getClassFiationData(@Query("currentPage") int currentPage, @Query("loanType") String loanType,
                                                  @Query("range") String range, @Query("pageSize") int pageSize);

    /**
     * 验证码
     */
    @POST(BaseServer.VERIFICATION_CODE)
    Call<VerificationCodeModel> getVerification(@Query("phone") String phone, @Query("isDury") boolean isDury);

    /**
     * 登录
     */
    @POST(BaseServer.LOGIN)
    Call<LoginNewModle> getLoginData(@Body RequestBody info);

    /**
     * 更新
     */
    @POST(BaseServer.UPDATE)
    Call<UpdateModel> getUpdateNumber();

    /**
     * 获取用户记录
     */
    @POST(BaseServer.RECORD)
    Call<RecordMdel> getRecordInfo(@Query("phone") String phone, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

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
    Call<JinJiRequestModle> commitJinJiInfo(@Body RequestBody info);

    /**
     * 提交银行卡信息
     */
    @POST(BaseServer.COMMIT_BANCK_INFO)
    Call<JinJiRequestModle> commiBanckInfo(@Body RequestBody info);

    /**
     * 获取银行卡信息
     */
    @POST(BaseServer.GET_BANCK_INFO)
    Call<BankInfoModel> getBanckInfo(@Query("phone") String phone);

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


}
