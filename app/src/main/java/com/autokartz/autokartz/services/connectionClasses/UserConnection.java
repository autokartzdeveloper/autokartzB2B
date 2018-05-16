package com.autokartz.autokartz.services.connectionClasses;

import com.autokartz.autokartz.utils.apiResponses.CategoryPartListResultBean;
import com.autokartz.autokartz.utils.apiResponses.DisputeResponse;
import com.autokartz.autokartz.utils.apiResponses.EnquiryFormsListResponseBean;
import com.autokartz.autokartz.utils.apiResponses.EnquiryResponseBean;
import com.autokartz.autokartz.utils.apiResponses.OrderApiResponse;
import com.autokartz.autokartz.utils.apiResponses.OrderDetailApiResponse;
import com.autokartz.autokartz.utils.apiResponses.PayTmResponseBean;
import com.autokartz.autokartz.utils.apiResponses.PayTmTransactionResponse;
import com.autokartz.autokartz.utils.apiResponses.PayUMoneyResponseBean;
import com.autokartz.autokartz.utils.apiResponses.ProductOrderAPIResponse;
import com.autokartz.autokartz.utils.apiResponses.ProductSuggestionResponseBean;
import com.autokartz.autokartz.utils.apiResponses.ForgotPasswordBeanResult;
import com.autokartz.autokartz.utils.apiResponses.UserBankDetailResult;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBeanResult;
import com.autokartz.autokartz.utils.util.constants.ServerApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Cortana on 1/11/2018.
 */

public interface UserConnection {

    @FormUrlEncoded
    @POST(ServerApi.MANUAL_LOGIN)
    Call<UserDetailBeanResult> userSignIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST(ServerApi.MANUAL_SIGNUP)
    Call<UserDetailBeanResult> newUserSignUp(@Field("garage_owner_name") String garage_owner_name,
                                             @Field("customer_name") String customer_name,
                                             @Field("address") String address,
                                             @Field("city") String city,
                                             @Field("state") String state,
                                             @Field("pin") String pin,
                                             @Field("email") String email,
                                             @Field("phone") String phone,
                                             @Field("password") String password);

    @FormUrlEncoded
    @POST(ServerApi.FORGOT_PASSWORD)
    Call<ForgotPasswordBeanResult> forgotPassword(@Field("email") String email, @Field("phone") String phone);


    @FormUrlEncoded
    @POST(ServerApi.RESET_PASSWORD)
    Call<ForgotPasswordBeanResult> resetPassword(@Field("user_id") String user_id, @Field("password") String password);

    @FormUrlEncoded
    @POST(ServerApi.FCM_TOKEN)
    Call<UserDetailBeanResult> userFcmToken(@Field("token") String token, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(ServerApi.BANK_INFO)
    Call<UserDetailBeanResult> UserBankInformation(@Field("user_id") String user_id,
                                                   @Field("account_no") String account_no,
                                                   @Field("ifsc") String ifsc,
                                                   @Field("branch") String branch,
                                                   @Field("pan") String pan,
                                                   @Field("gst") String gst);


    @FormUrlEncoded
    @POST(ServerApi.DISPUTE)
    Call<DisputeResponse> newDisputeForm(@Field("user_id") String user_id,
                                         @Field("order_id") String order_id,
                                         @Field("suggestion_id") String suggestion_id,
                                         @Field("dispute_reason") String dispute_reason,
                                         @Field("dispute_detail") String dispute_detail);

    @GET(ServerApi.CATEGORY)
    Call<CategoryPartListResultBean> getCategoryPart();

    @Headers({"Content-Type: application/json"})
    @POST(ServerApi.ENQUIRY)
    Call<EnquiryResponseBean> callEnuiryApi(@Body String jsonDetails);

    @GET(ServerApi.ENQUIRY_FORMS)
    Call<EnquiryFormsListResponseBean> getEnquiryForms(@Query("user_id") String userId);

    @GET(ServerApi.PRODUCT_SUGGETIONS)
    Call<ProductSuggestionResponseBean> getSuggestions(@Query("user_id") String userId,
                                                       @Query("id") String enquiryId);

    @FormUrlEncoded
    @POST(ServerApi.PAYUMONEY)
    Call<PayUMoneyResponseBean> getPayUMoneyHash(@Header("Content-Type") String authtoken,
                                                 @Field("key") String key,
                                                 @Field("salt") String salt,
                                                 @Field("amount") String amount,
                                                 @Field("productinfo") String productinfo,
                                                 @Field("firstname") String firstname,
                                                 @Field("email") String email);

    @FormUrlEncoded
    @POST(ServerApi.PAYTM)
    Call<PayTmResponseBean> getPaytmHash(@Header("Content-Type") String authtoken,
                                         @Field("MID") String MID,
                                         @Field("CUST_ID") String CUST_ID,
                                         @Field("INDUSTRY_TYPE_ID") String INDUSTRY_TYPE_ID,
                                         @Field("CHANNEL_ID") String CHANNEL_ID,
                                         @Field("TXN_AMOUNT") String TXN_AMOUNT,
                                         @Field("WEBSITE") String WEBSITE,
                                         @Field("M_KEY") String M_KEY);


    @FormUrlEncoded
    @POST(ServerApi.PAYTMCHECKSUM)
    Call<PayTmTransactionResponse> getPaytmCheckSum(@Header("Content-Type") String authtoken,
                                                    @Field("MID") String MID,
                                                    @Field("ORDERID") String ORDERID,
                                                    @Field("M_KEY") String M_KEY);


    @FormUrlEncoded
    @POST(ServerApi.PRODUCT_ORDER)
    Call<ProductOrderAPIResponse> callProductOrder(@Header("Content-Type") String authtoken,
                                                   @Field("user_id") String user_id,
                                                   @Field("txn_id") String txn_id,
                                                   @Field("amount") String amount,
                                                   @Field("shipping_time") int shipping_time,
                                                   @Field("status") String status,
                                                   @Field("product_info") String product_info,
                                                   @Field("payment_mode") String payment_mode);

    @GET(ServerApi.ORDERS)
    Call<OrderApiResponse> getOrdersList(@Query("user_id") String userId);

    @GET(ServerApi.GETBANK_INFO)
    Call<UserBankDetailResult> getBankInfo(@Query("user_id") String userId);

    @GET(ServerApi.ORDERDETAIL)
    Call<OrderDetailApiResponse> getOrderDetails(@Query("user_id") String userId, @Query("order_id") String orderid);

}