package com.autokartz.autokartz.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.interfaces.GetPayTMApiResponseListener;
import com.autokartz.autokartz.interfaces.GetPayUMoneyApiResponseListener;
import com.autokartz.autokartz.interfaces.OrderAPIResponseListener;
import com.autokartz.autokartz.services.connectionClasses.UserConnection;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.OrderAPI;
import com.autokartz.autokartz.services.webServices.apiRequests.PayTMApi;
import com.autokartz.autokartz.services.webServices.apiRequests.PayUMoneyApi;
import com.autokartz.autokartz.utils.apiRequestBeans.OrderDataBean;
import com.autokartz.autokartz.utils.apiRequestBeans.PayTmRequestBean;
import com.autokartz.autokartz.utils.apiRequestBeans.PayUMoneyRequestBean;
import com.autokartz.autokartz.utils.apiResponses.PayTmResponseBean;
import com.autokartz.autokartz.utils.apiResponses.PayTmTransactionResponse;
import com.autokartz.autokartz.utils.apiResponses.PayUMoneyResponseBean;
import com.autokartz.autokartz.utils.apiResponses.TransactionStatus;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.retrofitAdapter.RetroFitAdapter;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.HsbcParams;
import com.autokartz.autokartz.utils.util.PayTMParams;
import com.autokartz.autokartz.utils.util.PayUMoneyParams;
import com.autokartz.autokartz.utils.util.constants.AppConstantKeys;
import com.autokartz.autokartz.utils.util.constants.ServerApi;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentPayUActivity extends AppCompatActivity implements GetPayUMoneyApiResponseListener, GetPayTMApiResponseListener, OrderAPIResponseListener {

    @BindView(R.id.payment_method_payu_tv)
    TextView mPayUTv;
    @BindView(R.id.payment_method_paytm_tv)
    TextView mPayTmTv;
    @BindView(R.id.payment_method_cash_on_delivery_tv)
    TextView mCOD;
    private Context mContext;
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private PayUmoneySdkInitializer.PaymentParam paymentParam;
    private OrderDataBean orderDataBean;
    private UserDetailBean userDetailBean;
    private AccountDetailHolder mAccountDetailHolder;
    String codeStatus;
    private static final String TAG = PaymentPayUActivity.class.getName();
    protected static final String DEEPLINKING_URL_BASE = "upi://pay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_method);
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        getIntentData();
        init();
        paramsPayment();
    }

    private void init() {
        if (codeStatus.matches("1")) {
            mCOD.setVisibility(View.VISIBLE);
        } else mCOD.setVisibility(View.GONE);
    }

    private void paramsPayment() {
        userDetailBean = new UserDetailBean();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        PayUMoneyParams.EMAIL = mAccountDetailHolder.getUserDetailBean().getEmailId();
        PayUMoneyParams.PHONE = mAccountDetailHolder.getUserDetailBean().getMobile();
        PayUMoneyParams.NAME = mAccountDetailHolder.getUserDetailBean().getGarageOwnerName();
        PayTMParams.CUST_ID = mAccountDetailHolder.getUserDetailBean().getEmailId();
    }

    private void getIntentData() {
        orderDataBean = (OrderDataBean) getIntent().getSerializableExtra("orderDataBean");
        codeStatus = getIntent().getStringExtra("codestatus");
        Float payableAmount = Float.valueOf(orderDataBean.getAmount());
        PayUMoneyParams.PRODUCT_INFO = orderDataBean.getProductInfo();
        PayUMoneyParams.AMOUNT = String.valueOf(payableAmount);
        PayTMParams.TXN_AMOUNT = String.valueOf(payableAmount);
        HsbcParams.payeeAmt = String.valueOf(payableAmount);
    }

    private void initPayUMoneySDK() {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Done");
        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("PayuMoney PNP");
        String surl = "https://www.payumoney.com/mobileapp/payumoney/success.php";
        String furl = "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(Double.parseDouble((PayUMoneyParams.AMOUNT)))    // Payment amount
                .setTxnId(PayUMoneyParams.TXN_ID)             // Transaction ID
                .setPhone(PayUMoneyParams.PHONE)              // User Phone number
                .setProductName(PayUMoneyParams.PRODUCT_INFO) // Product Name or description
                .setFirstName(PayUMoneyParams.NAME)           // User First name
                .setEmail(PayUMoneyParams.EMAIL)              // Use r Email ID
                .setsUrl(surl)                                // Success URL (surl)
                .setfUrl(furl)                                //Failure URL (furl)
                .setIsDebug(false)                            // Integration environment - true (Debug)/ false(Production)
                .setKey(PayUMoneyParams.KEY)
                .setMerchantId(PayUMoneyParams.MID);          // Merchant key
        paymentParam = builder.build();
        paymentParam.setMerchantHash(PayUMoneyParams.HASH);
        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, this, R.style.PayUAppTheme, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* // Result Code is -1 send from Payumoney activity
        Logger.LogDebug("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    AppToast.showToast(mContext, "Your Order has been Placed");
                    orderDataBean.setStatus("1");
                    orderDataBean.setPaymentMode("PayU");
                    orderDataBean.setTxnId(PayUMoneyParams.TXN_ID);
                    mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
                    OrderAPI orderAPI = new OrderAPI(mContext, this, mProgressDialog);
                    orderAPI.callOrderApi(orderDataBean);
                    //Success Transaction
                } else {
                    AppToast.showToast(mContext, "Order failed due to Transaction Failure");
                    orderDataBean.setStatus("3");
                    orderDataBean.setPaymentMode("PayU");
                    orderDataBean.setTxnId(PayUMoneyParams.TXN_ID);
                    mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
                    OrderAPI orderAPI = new OrderAPI(mContext, this, mProgressDialog);
                    orderAPI.callOrderApi(orderDataBean);

                    //Failure Transaction
                }
                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();
                // Response from SURl and FURL
                String transactionDetails = transactionResponse.getTransactionDetails();
            } else if (resultModel != null && resultModel.getError() != null) {
                AppToast.showToast(mContext, "Transaction Response Error");
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                AppToast.showToast(mContext, "Transaction Error");
                Log.d(TAG, "Both objects are null!");
            }
        }*/

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            //String str = data.

            String str = data.getStringExtra("response").toString();
            Toast.makeText(mContext, "hsbc integration", Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
         //  str.concat(txnId);
            //txnId=(tid)&responseCode=(responsecode)&ApprovalRefNo=( ApprovalRefNo )&Status=(status)&txnRef=(tr)
            //  processResponseIntent(responseIntent);
        } else {
            AppToast.showToast(mContext, "Transaction Error");
        }
    }

    @OnClick({R.id.payment_method_payu_tv})
    public void onClickPayUTv() {
        PayUMoneyRequestBean payUMoneyRequestBean = new PayUMoneyRequestBean(PayUMoneyParams.KEY, PayUMoneyParams.SALT, PayUMoneyParams.AMOUNT, PayUMoneyParams.PRODUCT_INFO, PayUMoneyParams.NAME, PayUMoneyParams.EMAIL, PayUMoneyParams.PHONE);
        mProgressDialog = ShowDialog.show(this, "", "Please Wait", true, false);
        PayUMoneyApi payUMoneyApi = new PayUMoneyApi(mContext, this, mProgressDialog);
        payUMoneyApi.callPayUMoneyApi(payUMoneyRequestBean);
        // initPayUMoneySDK();
    }

    @OnClick({R.id.payment_method_cash_on_delivery_tv})
    public void onClickCashOnDeliveryTv() {
        orderDataBean.setStatus("1");
        orderDataBean.setPaymentMode("COD");
        mProgressDialog = ShowDialog.show(this, "", "Please Wait", true, false);
        OrderAPI orderAPI = new OrderAPI(mContext, this, mProgressDialog);
        orderAPI.callOrderApi(orderDataBean);
    }

    @OnClick({R.id.hsbc_method_paytm_tv})
    public void onClickHsbcTv() {
        startHsbcPayment();
    }

    private void startHsbcPayment() {
        Random rand = new Random();
        String num = String.valueOf(rand.nextInt(9000000) + 1000000);
        HsbcParams.txnRef = "AUTO" + num;
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(DEEPLINKING_URL_BASE).append("?")
                .append(HsbcParams.pa).append("=").append(HsbcParams.payeeVpa).append("&")
                .append(HsbcParams.pn).append("=").append(HsbcParams.payeeName).append("&")
                .append(HsbcParams.mc).append("=").append(HsbcParams.payeeMcc).append("&")
                .append(HsbcParams.tid).append("=").append("").append("&")//blank
                .append(HsbcParams.tr).append("=").append(HsbcParams.txnRef).append("&")
                .append(HsbcParams.tn).append("=").append("").append("&")//blank
                .append(HsbcParams.am).append("=").append(HsbcParams.payeeAmt).append("&")
                .append(HsbcParams.mam).append("=").append("").append("&")//blank
                .append(HsbcParams.cu).append("=").append("").append("&")//blank
                .append(HsbcParams.url).append("=").append("");//blank

        String deepLinkUrl = urlBuilder.toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(deepLinkUrl));
        String title = "Pay with";
        // Create intent to show chooser. It will display the list of available PSP apps (which have the same url in
        // the maifest)
        Intent chooser = Intent.createChooser(intent, title);
        // Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, 1);
        }
    }

    @OnClick({R.id.payment_method_paytm_tv})
    public void onClickPayTMTv() {
        PayTmRequestBean payTmRequestBean = new PayTmRequestBean(PayTMParams.MID, PayTMParams.CUST_ID, PayTMParams.INDUSTRY_TYPE_ID, PayTMParams.CHANNEL_ID, PayTMParams.TXN_AMOUNT, PayTMParams.WEBSITE, PayTMParams.M_KEY);
        mProgressDialog = ShowDialog.show(this, "", "Please Wait", true, false);
        PayTMApi payTMApi = new PayTMApi(mContext, this, mProgressDialog);
        payTMApi.callPayTMApi(payTmRequestBean);
    }

    @Override
    public void getPayUApiResponse(boolean isSuccess, PayUMoneyResponseBean responseBean) {
        DismissDialog.dismissWithTryCatch(mProgressDialog);
        if (isSuccess) {
            PayUMoneyParams.TXN_ID = responseBean.getResultTxnId();
            PayUMoneyParams.HASH = responseBean.getResultHash();
            initPayUMoneySDK();
        }
    }

    @Override
    public void getPayTmApiResponse(boolean isSuccess, PayTmResponseBean responseBean) {
        DismissDialog.dismissWithTryCatch(mProgressDialog);
        if (isSuccess) {
            PayTMParams.ORDER_ID = responseBean.getResultOrderId();
            PayTMParams.HASH = responseBean.getResultHash();
            onStartTransaction();
        }
    }

    public void onStartTransaction() {
        // PaytmPGService Service = PaytmPGService.getStagingService();
        PaytmPGService Service = PaytmPGService.getProductionService();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(AppConstantKeys.MID, PayTMParams.MID);
        paramMap.put(AppConstantKeys.ORDER_ID, PayTMParams.ORDER_ID);
        paramMap.put(AppConstantKeys.CUST_ID, PayTMParams.CUST_ID);
        paramMap.put(AppConstantKeys.INDUSTRY_TYPE_ID, PayTMParams.INDUSTRY_TYPE_ID);
        paramMap.put(AppConstantKeys.CHANNEL_ID, PayTMParams.CHANNEL_ID);
        paramMap.put(AppConstantKeys.TXN_AMOUNT, PayTMParams.TXN_AMOUNT);
        paramMap.put(AppConstantKeys.WEBSITE, PayTMParams.WEBSITE);
        paramMap.put(AppConstantKeys.CALLBACK_URL, "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + PayTMParams.ORDER_ID);
        paramMap.put(AppConstantKeys.CHECKSUMHASH, PayTMParams.HASH);
        PaytmOrder Order = new PaytmOrder(paramMap);
        Service.initialize(Order, null);
        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {

                        UserConnection userConnection = RetroFitAdapter.createService(UserConnection.class, ServerApi.SERVER_URL);
                        Call<PayTmTransactionResponse> call = userConnection.getPaytmCheckSum(AppConstantKeys.CONTENT_TYPE, PayTMParams.MID, PayTMParams.ORDER_ID, PayTMParams.M_KEY);

                        call.enqueue(new Callback<PayTmTransactionResponse>() {
                            @Override
                            public void onResponse(Call<PayTmTransactionResponse> call, Response<PayTmTransactionResponse> response) {
                                TransactionStatus body = response.body().getStatus();
                                String bankTxnId = body.getBANKTXNID();
                                if (body.getSTATUS().matches("TXN_SUCCESS")) {
                                    Toast.makeText(getBaseContext(), "Payment Transaction Success ", Toast.LENGTH_LONG).show();
                                    PaytmOrderSuccessStatus(bankTxnId);

                                } else if (body.getSTATUS().matches("TXN_FAILURE") && bankTxnId.matches("")) {
                                    Toast.makeText(getBaseContext(), "Payment Transaction Cancelled ", Toast.LENGTH_LONG).show();
                                    PaytmOrderCancelledStatus();
                                } else if (body.getSTATUS().matches("TXN_FAILURE")) {
                                    Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                                    PaytmOrderFailedStatus(bankTxnId);
                                }
                            }

                            @Override
                            public void onFailure(Call<PayTmTransactionResponse> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void networkNotAvailable() {
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {
                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        // TODO Auto-generated method stub
                        Toast.makeText(getBaseContext(), "Payment Transaction Cancelled ", Toast.LENGTH_LONG).show();
                        PaytmOrderCancelledStatus();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void PaytmOrderFailedStatus(String bankTxnId) {
        orderDataBean.setStatus("3");
        orderDataBean.setPaymentMode("PAYTM");
        orderDataBean.setTxnId(bankTxnId);
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        OrderAPI orderAPI = new OrderAPI(mContext, this, mProgressDialog);
        orderAPI.callOrderApi(orderDataBean);
    }

    private void PaytmOrderCancelledStatus() {
        orderDataBean.setStatus("2");
        orderDataBean.setPaymentMode("PAYTM");
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        OrderAPI orderAPI = new OrderAPI(mContext, this, mProgressDialog);
        orderAPI.callOrderApi(orderDataBean);
    }

    private void PaytmOrderSuccessStatus(String bankTxnId) {
        orderDataBean.setStatus("1");
        orderDataBean.setTxnId(bankTxnId);
        orderDataBean.setPaymentMode("PAYTM");
        mProgressDialog = ShowDialog.show(mContext, "", "Please Wait", true, false);
        OrderAPI orderAPI = new OrderAPI(mContext, this, mProgressDialog);
        orderAPI.callOrderApi(orderDataBean);

    }

    @Override
    public void getOrderResponse(boolean isOrdered, final String message) {
        DismissDialog.dismissWithCheck(mProgressDialog);
        Intent intent = new Intent(mContext, MainDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}