package com.fil.workerappz.retrofit;


import com.fil.workerappz.pojo.AddWalletListJsonPojo;
import com.fil.workerappz.pojo.BalanceListJsonPojo;
import com.fil.workerappz.pojo.BankListPojo;
import com.fil.workerappz.pojo.BankNetworkListJsonPojo;
import com.fil.workerappz.pojo.BeneficiaryListPojo;
import com.fil.workerappz.pojo.CMSListPojo;
import com.fil.workerappz.pojo.CalculateTransferListJsonPojo;
import com.fil.workerappz.pojo.CashNetworkListJsonPojo;
import com.fil.workerappz.pojo.CityListPojo;
import com.fil.workerappz.pojo.CountryListPojo;
import com.fil.workerappz.pojo.CreateBeneficiaryJsonPojo;
import com.fil.workerappz.pojo.CreateCustomerListJsonPojo;
import com.fil.workerappz.pojo.CustomerCardBalanceJsonPojo;
import com.fil.workerappz.pojo.CustomerCardJsonPojo;
import com.fil.workerappz.pojo.DingTransferPayJsonPojo;
import com.fil.workerappz.pojo.DocumentListCountryWiseJsonPojo;
import com.fil.workerappz.pojo.EditBeneficiaryJsonPojo;
import com.fil.workerappz.pojo.IdTypeListJsonPojo;
import com.fil.workerappz.pojo.ImageListPojo;
import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.KYCUpdateDocumentListJsonPojo;
import com.fil.workerappz.pojo.KYCUploadedDocumentListJsonPojo;
import com.fil.workerappz.pojo.LabelListJsonPojo;
import com.fil.workerappz.pojo.LanguagesListPojo;
import com.fil.workerappz.pojo.MessageListJsonPojo;
import com.fil.workerappz.pojo.NotificationListJsonPojo;
import com.fil.workerappz.pojo.PurposeOfTransferListPojo;
import com.fil.workerappz.pojo.QuickPayJsonPojo;
import com.fil.workerappz.pojo.SendMoneyBeneficiaryJsonPojo;
import com.fil.workerappz.pojo.SendPaymentJsonPojo;
import com.fil.workerappz.pojo.SendReceiveMoneyJsonPojo;
import com.fil.workerappz.pojo.TransactionHistoryListJsonPojo;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.pojo.WalletSuggestionListPojo;
import com.fil.workerappz.pojo.WalletToWalletTransferListPojo;
import com.fil.workerappz.pojo.ding.GetBalanceList;
import com.fil.workerappz.pojo.ding.GetCountryList;
import com.fil.workerappz.pojo.ding.GetProductsList;
import com.fil.workerappz.pojo.ding.GetProvidersList;
import com.fil.workerappz.pojo.ding.GetRegionsList;
import com.fil.workerappz.pojo.ding.SendTransferList;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by HS on 12/3/2015.
 */
public interface RestApi {

    @Multipart
    @POST("users/file-upload")
    Call<List<ImageListPojo>> uploadAttachment(@Part MultipartBody.Part filePart, @Part("FilePath") RequestBody filePath, @Part("json") RequestBody json);

    @FormUrlEncoded
    @POST("users/register-user")
    Call<List<UserListPojo>> registerUserJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/login")
    Call<List<UserListPojo>> loginUserJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("country/list-country")
    Call<List<CountryListPojo>> countryListJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/send-ding-transfer")
    Call<List<DingTransferPayJsonPojo>> dingPaymentTransferJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("cmspage/list-cmspages")
    Call<List<CMSListPojo>> cmsPageJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/forgot-password")
    Call<List<JsonListPojo>> forgotPasswordJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/verify-otp")
    Call<List<JsonListPojo>> verifyOTPJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/forgot-pin")
    Call<List<JsonListPojo>> forgotPinJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/verify-pin")
    Call<List<JsonListPojo>> verifyPinJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("kyccountry/country-documents-list")
    Call<List<DocumentListCountryWiseJsonPojo>> documentListCountryWiseJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("userkyc/list-kyc")
    Call<List<KYCUploadedDocumentListJsonPojo>> uploadedKYCDocumentListJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-balance")
    Call<List<BalanceListJsonPojo>> getBalanceListJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/customer-cards")
    Call<List<CustomerCardJsonPojo>>customerCardJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/card-balance")
    Call<List<CustomerCardBalanceJsonPojo>>customerCardBalanceJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/add-balance")
    Call<List<AddWalletListJsonPojo>> addBalanceInToWalletJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("userkyc/delete-kyc")
    Call<List<KYCUploadedDocumentListJsonPojo>> removeKYCDocumentJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-history")
    Call<List<TransactionHistoryListJsonPojo>> walletHistoryJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-bank-network")
    Call<List<BankNetworkListJsonPojo>> getBankNetworkJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/calculate-transfer")
    Call<List<SendReceiveMoneyJsonPojo>> getSendReceiveMoneyJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/update-settings")
    Call<List<JsonListPojo>> updateSettingsJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("user-bank-account/register-bank")
    Call<List<JsonListPojo>> registerBankJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/ritmanpay-send")
    Call<List<SendMoneyBeneficiaryJsonPojo>> sendMoneyJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-purpose-of-transfer-list")
    Call<List<PurposeOfTransferListPojo>> getPurposeOfTransferJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-id-type-list")
    Call<List<IdTypeListJsonPojo>> getIdTypeJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/update-user")
    Call<List<UserListPojo>> updateUserJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-quick-pay")
    Call<List<QuickPayJsonPojo>> getQuickPayJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/ritmanpay-send")
    Call<List<SendPaymentJsonPojo>> sendPaymentJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/get-wallet-suggestion")
    Call<List<WalletSuggestionListPojo>> getWalletSuggestionJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-cash-network")
    Call<List<CashNetworkListJsonPojo>> getCashNetworkJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/calculate-transfer")
    Call<List<CalculateTransferListJsonPojo>> calculateTransferJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("city/list-city")
    Call<List<CityListPojo>> getCityListJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/list-benificary")
    Call<List<BeneficiaryListPojo>> getBeneficiaryListJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/resend-otp")
    Call<List<JsonListPojo>> resendOTPJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("user-bank-account/list-bank")
    Call<List<BankListPojo>> listBankJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("language/list-language")
    Call<List<LanguagesListPojo>> listLanguagesJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/change-password")
    Call<List<JsonListPojo>> changePasswordJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/change-pin")
    Call<List<JsonListPojo>> changePINJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/wallet-to-wallet")
    Call<List<WalletToWalletTransferListPojo>> walletToWalletTransferJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("userkyc/create-kyc")
    Call<List<KYCUploadedDocumentListJsonPojo>> createKYCDocumentJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/create-customer")
    Call<List<CreateCustomerListJsonPojo>> createCustomerJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("userkyc/update-kyc")
    Call<List<KYCUpdateDocumentListJsonPojo>> updateKYCDocumentJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("users/update-settings")
    Call<List<JsonListPojo>> updateUserSettingsJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("notification/get-notification-list")
    Call<List<NotificationListJsonPojo>> notificationListJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("notification/delete-notification")
    Call<List<JsonListPojo>> notificationDeleteJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/create-benificary")
    Call<List<CreateBeneficiaryJsonPojo>> createBeneficiaryJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/edit-benificary")
    Call<List<EditBeneficiaryJsonPojo>> editBeneficiaryJsonCall(@Field("json") String json);

    @GET("wallettransactions/get-ding-countries")
    Call<List<GetCountryList>> getDingCountryListJsonCall();

    @FormUrlEncoded
    @POST("wallettransactions/get-ding-providers")
    Call<List<GetProvidersList>> getProvidersJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-ding-regions")
    Call<List<GetRegionsList>> getRegionsJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-ding-products")
    Call<List<GetProductsList>> getProductsJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("wallettransactions/get-ding-balance")
    Call<List<GetBalanceList>> getDingBalanceJsonCall(@Field("json") String json);


    @FormUrlEncoded
    @POST("users/update-device-token")
    Call<List<JsonListPojo>> updateDeviceTokenJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("language/list-labels")
    Call<List<LabelListJsonPojo>> labelListJsonCall(@Field("json") String json);

    @FormUrlEncoded
    @POST("language/list-msgs")
    Call<List<MessageListJsonPojo>> messageListJsonCall(@Field("json") String json);

}