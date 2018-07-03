package com.fil.workerappz.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by FUSION on 08/05/2018.
 */

public class MessagelistData implements Serializable {
    @SerializedName("JSON_ERROR")
    @Expose
    private String jSONERROR;
    @SerializedName("NORECORD")
    @Expose
    private String nORECORD;
    @SerializedName("RECORDFOUND")
    @Expose
    private String rECORDFOUND;
    @SerializedName("INVALIDOTP")
    @Expose
    private String iNVALIDOTP;
    @SerializedName("INVALID_OLD_PASS")
    @Expose
    private String iNVALIDOLDPASS;
    @SerializedName("PASSWORD_CHANGED")
    @Expose
    private String pASSWORDCHANGED;
    @SerializedName("INVALID_PIN")
    @Expose
    private String iNVALIDPIN;
    @SerializedName("VALID_PIN")
    @Expose
    private String vALIDPIN;
    @SerializedName("INVALID_PIN_SIZE")
    @Expose
    private String iNVALIDPINSIZE;
    @SerializedName("PIN_CHANGED")
    @Expose
    private String pINCHANGED;
    @SerializedName("INVALID_USERPASS")
    @Expose
    private String iNVALIDUSERPASS;
    @SerializedName("FORGOT_PASSWORD")
    @Expose
    private String fORGOTPASSWORD;
    @SerializedName("FORGOT_PIN")
    @Expose
    private String fORGOTPIN;
    @SerializedName("RESEND_OTP")
    @Expose
    private String rESENDOTP;
    @SerializedName("REGISTERUSER_SUCCESS")
    @Expose
    private String rEGISTERUSERSUCCESS;
    @SerializedName("LOGIN_SUCCESS")
    @Expose
    private String lOGINSUCCESS;
    @SerializedName("VERFIFYOTP_SUCCESS")
    @Expose
    private String vERFIFYOTPSUCCESS;
    @SerializedName("CHANGEPASSWORD_SUCCESS")
    @Expose
    private String cHANGEPASSWORDSUCCESS;
    @SerializedName("CHANGEPIN_SUCCESS")
    @Expose
    private String cHANGEPINSUCCESS;
    @SerializedName("CHANGEPROFILE_SUCCESS")
    @Expose
    private String cHANGEPROFILESUCCESS;
    @SerializedName("WALLET_INVALID")
    @Expose
    private String wALLETINVALID;
    @SerializedName("BALANCE_INVALID")
    @Expose
    private String bALANCEINVALID;
    @SerializedName("RECEIVER_WALLET_LIMIT")
    @Expose
    private String rECEIVERWALLETLIMIT;
    @SerializedName("SENDER_WALLET_LIMIT")
    @Expose
    private String sENDERWALLETLIMIT;
    @SerializedName("TOPUP_SUCCESS")
    @Expose
    private String tOPUPSUCCESS;
    @SerializedName("TRANSFER_SUCCESS")
    @Expose
    private String tRANSFERSUCCESS;
    @SerializedName("ADDBANK_SUCCESS")
    @Expose
    private String aDDBANKSUCCESS;
    @SerializedName("EDITBANK_SUCCESS")
    @Expose
    private String eDITBANKSUCCESS;
    @SerializedName("IS_MISSING")
    @Expose
    private String iSMISSING;
    @SerializedName("RITMANPAY_ERROR")
    @Expose
    private String rITMANPAYERROR;
    @SerializedName("SETTINGS_SAVED")
    @Expose
    private String sETTINGSSAVED;
    @SerializedName("ANDROID_URL")
    @Expose
    private String aNDROIDURL;
    @SerializedName("IPHONE_URL")
    @Expose
    private String iPHONEURL;
    @SerializedName("DEVICE_UPDATED")
    @Expose
    private String dEVICEUPDATED;
    @SerializedName("DELETE_SUCCESS")
    @Expose
    private String dELETESUCCESS;
    @SerializedName("select_country")
    @Expose
    private String selectCountry;
    @SerializedName("enter_mobile_number")
    @Expose
    private String enterMobileNumber;
    @SerializedName("select_plan")
    @Expose
    private String selectPlan;
    @SerializedName("no_record_found")
    @Expose
    private String noRecordFound;
    @SerializedName("request_successful")
    @Expose
    private String requestSuccessful;
    @SerializedName("enter_valid_mobile_number")
    @Expose
    private String enterValidMobileNumber;
    @SerializedName("select_nationality")
    @Expose
    private String selectNationality;
    @SerializedName("enter_first_name")
    @Expose
    private String enterFirstName;
    @SerializedName("enter_last_name")
    @Expose
    private String enterLastName;
    @SerializedName("enter_nickName")
    @Expose
    private String enterNickName;
    @SerializedName("enter_email")
    @Expose
    private String enterEmail;
    @SerializedName("enter_valid_email")
    @Expose
    private String enterValidEmail;
    @SerializedName("select_Date_Of_Birth")
    @Expose
    private String selectDateOfBirth;
    @SerializedName("enter_address")
    @Expose
    private String enterAddress;
    @SerializedName("enter_landmark")
    @Expose
    private String enterLandmark;
    @SerializedName("enter_zipCode")
    @Expose
    private String enterZipCode;
    @SerializedName("enter_valid_zipCode")
    @Expose
    private String enterValidZipCode;
    @SerializedName("select_IdType")
    @Expose
    private String selectIdType;
    @SerializedName("enter_idNumber")
    @Expose
    private String enterIdNumber;
    @SerializedName("enter_destination_address")
    @Expose
    private String enterDestinationAddress;
    @SerializedName("enter_destination_landmark")
    @Expose
    private String enterDestinationLandmark;
    @SerializedName("enter_destination_zipCode")
    @Expose
    private String enterDestinationZipCode;
    @SerializedName("select_available_agents")
    @Expose
    private String selectAvailableAgents;
    @SerializedName("select_purpose_of_transfer")
    @Expose
    private String selectPurposeOfTransfer;
    @SerializedName("beneficiary_updated_successfully")
    @Expose
    private String beneficiaryUpdatedSuccessfully;
    @SerializedName("balance_updated_successfully")
    @Expose
    private String balanceUpdatedSuccessfully;
    @SerializedName("enter_old_PIN")
    @Expose
    private String enterOldPIN;
    @SerializedName("enter_6_digit_PIN")
    @Expose
    private String enter6DigitPIN;
    @SerializedName("enter_new_PIN")
    @Expose
    private String enterNewPIN;
    @SerializedName("enter_repeat_PIN")
    @Expose
    private String enterRepeatPIN;
    @SerializedName("PIN_does_not_match")
    @Expose
    private String pINDoesNotMatch;
    @SerializedName("select_Gender")
    @Expose
    private String selectGender;
    @SerializedName("select_Country_of_Residence")
    @Expose
    private String selectCountryOfResidence;
    @SerializedName("enter_Passport_no")
    @Expose
    private String enterPassportNo;
    @SerializedName("passport_no_number_should_be_10_digits")
    @Expose
    private String passportNoNumberShouldBe10Digits;
    @SerializedName("enter_Emirates_Id")
    @Expose
    private String enterEmiratesId;
    @SerializedName("emiratesId_should_be_10_digits")
    @Expose
    private String emiratesIdShouldBe10Digits;
    @SerializedName("camera_preview_not_available")
    @Expose
    private String cameraPreviewNotAvailable;
    @SerializedName("enter_amount")
    @Expose
    private String enterAmount;
    @SerializedName("enter_valid_amount")
    @Expose
    private String enterValidAmount;
    @SerializedName("amount_is_larger_than_your_balance")
    @Expose
    private String amountIsLargerThanYourBalance;
    @SerializedName("enter_name")
    @Expose
    private String enterName;
    @SerializedName("mobile_number_is_not_registered")
    @Expose
    private String mobileNumberIsNotRegistered;
    @SerializedName("enter_valid_idNumber")
    @Expose
    private String enterValidIdNumber;
    @SerializedName("enter_send_money")
    @Expose
    private String enterSendMoney;
    @SerializedName("enter_receive_money")
    @Expose
    private String enterReceiveMoney;
    @SerializedName("enter_Account_No")
    @Expose
    private String enterAccountNo;
    @SerializedName("enter_Bank_Name")
    @Expose
    private String enterBankName;
    @SerializedName("enter_Branch_Code")
    @Expose
    private String enterBranchCode;
    @SerializedName("enter_Branch_address")
    @Expose
    private String enterBranchAddress;
    @SerializedName("select_Bank")
    @Expose
    private String selectBank;
    @SerializedName("enter_minimum_12_digit_Account_number")
    @Expose
    private String enterMinimum12DigitAccountNumber;
    @SerializedName("phone_number_should_be_at_least_9_digits")
    @Expose
    private String phoneNumberShouldBeAtLeast9Digits;
    @SerializedName("enter_valid_bank_details")
    @Expose
    private String enterValidBankDetails;
    @SerializedName("enter_a_valid_amount")
    @Expose
    private String enterAValidAmount;
    @SerializedName("PIN_is_invalid")
    @Expose
    private String pINIsInvalid;
    @SerializedName("enter_Email_or_Mobile_Number")
    @Expose
    private String enterEmailOrMobileNumber;
    @SerializedName("enter_Valid_Email_or_Mobile_number")
    @Expose
    private String enterValidEmailOrMobileNumber;
    @SerializedName("enter_Verification_Code")
    @Expose
    private String enterVerificationCode;
    @SerializedName("minimum_6_Characters_Required")
    @Expose
    private String minimum6CharactersRequired;
    @SerializedName("mobile_number_should_be_at_least_9_digits")
    @Expose
    private String mobileNumberShouldBeAtLeast9Digits;
    @SerializedName("file_uploaded_successfully")
    @Expose
    private String fileUploadedSuccessfully;
    @SerializedName("something_went_wrong")
    @Expose
    private String somethingWentWrong;
    @SerializedName("your_registration_is_done")
    @Expose
    private String yourRegistrationIsDone;

    public String getJSONERROR() {
        return jSONERROR;
    }

    public void setJSONERROR(String jSONERROR) {
        this.jSONERROR = jSONERROR;
    }

    public String getNORECORD() {
        return nORECORD;
    }

    public void setNORECORD(String nORECORD) {
        this.nORECORD = nORECORD;
    }

    public String getRECORDFOUND() {
        return rECORDFOUND;
    }

    public void setRECORDFOUND(String rECORDFOUND) {
        this.rECORDFOUND = rECORDFOUND;
    }

    public String getINVALIDOTP() {
        return iNVALIDOTP;
    }

    public void setINVALIDOTP(String iNVALIDOTP) {
        this.iNVALIDOTP = iNVALIDOTP;
    }

    public String getINVALIDOLDPASS() {
        return iNVALIDOLDPASS;
    }

    public void setINVALIDOLDPASS(String iNVALIDOLDPASS) {
        this.iNVALIDOLDPASS = iNVALIDOLDPASS;
    }

    public String getPASSWORDCHANGED() {
        return pASSWORDCHANGED;
    }

    public void setPASSWORDCHANGED(String pASSWORDCHANGED) {
        this.pASSWORDCHANGED = pASSWORDCHANGED;
    }

    public String getINVALIDPIN() {
        return iNVALIDPIN;
    }

    public void setINVALIDPIN(String iNVALIDPIN) {
        this.iNVALIDPIN = iNVALIDPIN;
    }

    public String getVALIDPIN() {
        return vALIDPIN;
    }

    public void setVALIDPIN(String vALIDPIN) {
        this.vALIDPIN = vALIDPIN;
    }

    public String getINVALIDPINSIZE() {
        return iNVALIDPINSIZE;
    }

    public void setINVALIDPINSIZE(String iNVALIDPINSIZE) {
        this.iNVALIDPINSIZE = iNVALIDPINSIZE;
    }

    public String getPINCHANGED() {
        return pINCHANGED;
    }

    public void setPINCHANGED(String pINCHANGED) {
        this.pINCHANGED = pINCHANGED;
    }

    public String getINVALIDUSERPASS() {
        return iNVALIDUSERPASS;
    }

    public void setINVALIDUSERPASS(String iNVALIDUSERPASS) {
        this.iNVALIDUSERPASS = iNVALIDUSERPASS;
    }

    public String getFORGOTPASSWORD() {
        return fORGOTPASSWORD;
    }

    public void setFORGOTPASSWORD(String fORGOTPASSWORD) {
        this.fORGOTPASSWORD = fORGOTPASSWORD;
    }

    public String getFORGOTPIN() {
        return fORGOTPIN;
    }

    public void setFORGOTPIN(String fORGOTPIN) {
        this.fORGOTPIN = fORGOTPIN;
    }

    public String getRESENDOTP() {
        return rESENDOTP;
    }

    public void setRESENDOTP(String rESENDOTP) {
        this.rESENDOTP = rESENDOTP;
    }

    public String getREGISTERUSERSUCCESS() {
        return rEGISTERUSERSUCCESS;
    }

    public void setREGISTERUSERSUCCESS(String rEGISTERUSERSUCCESS) {
        this.rEGISTERUSERSUCCESS = rEGISTERUSERSUCCESS;
    }

    public String getLOGINSUCCESS() {
        return lOGINSUCCESS;
    }

    public void setLOGINSUCCESS(String lOGINSUCCESS) {
        this.lOGINSUCCESS = lOGINSUCCESS;
    }

    public String getVERFIFYOTPSUCCESS() {
        return vERFIFYOTPSUCCESS;
    }

    public void setVERFIFYOTPSUCCESS(String vERFIFYOTPSUCCESS) {
        this.vERFIFYOTPSUCCESS = vERFIFYOTPSUCCESS;
    }

    public String getCHANGEPASSWORDSUCCESS() {
        return cHANGEPASSWORDSUCCESS;
    }

    public void setCHANGEPASSWORDSUCCESS(String cHANGEPASSWORDSUCCESS) {
        this.cHANGEPASSWORDSUCCESS = cHANGEPASSWORDSUCCESS;
    }

    public String getCHANGEPINSUCCESS() {
        return cHANGEPINSUCCESS;
    }

    public void setCHANGEPINSUCCESS(String cHANGEPINSUCCESS) {
        this.cHANGEPINSUCCESS = cHANGEPINSUCCESS;
    }

    public String getCHANGEPROFILESUCCESS() {
        return cHANGEPROFILESUCCESS;
    }

    public void setCHANGEPROFILESUCCESS(String cHANGEPROFILESUCCESS) {
        this.cHANGEPROFILESUCCESS = cHANGEPROFILESUCCESS;
    }

    public String getWALLETINVALID() {
        return wALLETINVALID;
    }

    public void setWALLETINVALID(String wALLETINVALID) {
        this.wALLETINVALID = wALLETINVALID;
    }

    public String getBALANCEINVALID() {
        return bALANCEINVALID;
    }

    public void setBALANCEINVALID(String bALANCEINVALID) {
        this.bALANCEINVALID = bALANCEINVALID;
    }

    public String getRECEIVERWALLETLIMIT() {
        return rECEIVERWALLETLIMIT;
    }

    public void setRECEIVERWALLETLIMIT(String rECEIVERWALLETLIMIT) {
        this.rECEIVERWALLETLIMIT = rECEIVERWALLETLIMIT;
    }

    public String getSENDERWALLETLIMIT() {
        return sENDERWALLETLIMIT;
    }

    public void setSENDERWALLETLIMIT(String sENDERWALLETLIMIT) {
        this.sENDERWALLETLIMIT = sENDERWALLETLIMIT;
    }

    public String getTOPUPSUCCESS() {
        return tOPUPSUCCESS;
    }

    public void setTOPUPSUCCESS(String tOPUPSUCCESS) {
        this.tOPUPSUCCESS = tOPUPSUCCESS;
    }

    public String getTRANSFERSUCCESS() {
        return tRANSFERSUCCESS;
    }

    public void setTRANSFERSUCCESS(String tRANSFERSUCCESS) {
        this.tRANSFERSUCCESS = tRANSFERSUCCESS;
    }

    public String getADDBANKSUCCESS() {
        return aDDBANKSUCCESS;
    }

    public void setADDBANKSUCCESS(String aDDBANKSUCCESS) {
        this.aDDBANKSUCCESS = aDDBANKSUCCESS;
    }

    public String getEDITBANKSUCCESS() {
        return eDITBANKSUCCESS;
    }

    public void setEDITBANKSUCCESS(String eDITBANKSUCCESS) {
        this.eDITBANKSUCCESS = eDITBANKSUCCESS;
    }

    public String getISMISSING() {
        return iSMISSING;
    }

    public void setISMISSING(String iSMISSING) {
        this.iSMISSING = iSMISSING;
    }

    public String getRITMANPAYERROR() {
        return rITMANPAYERROR;
    }

    public void setRITMANPAYERROR(String rITMANPAYERROR) {
        this.rITMANPAYERROR = rITMANPAYERROR;
    }

    public String getSETTINGSSAVED() {
        return sETTINGSSAVED;
    }

    public void setSETTINGSSAVED(String sETTINGSSAVED) {
        this.sETTINGSSAVED = sETTINGSSAVED;
    }

    public String getANDROIDURL() {
        return aNDROIDURL;
    }

    public void setANDROIDURL(String aNDROIDURL) {
        this.aNDROIDURL = aNDROIDURL;
    }

    public String getIPHONEURL() {
        return iPHONEURL;
    }

    public void setIPHONEURL(String iPHONEURL) {
        this.iPHONEURL = iPHONEURL;
    }

    public String getDEVICEUPDATED() {
        return dEVICEUPDATED;
    }

    public void setDEVICEUPDATED(String dEVICEUPDATED) {
        this.dEVICEUPDATED = dEVICEUPDATED;
    }

    public String getDELETESUCCESS() {
        return dELETESUCCESS;
    }

    public void setDELETESUCCESS(String dELETESUCCESS) {
        this.dELETESUCCESS = dELETESUCCESS;
    }

    public String getSelectCountry() {
        return selectCountry;
    }

    public void setSelectCountry(String selectCountry) {
        this.selectCountry = selectCountry;
    }

    public String getEnterMobileNumber() {
        return enterMobileNumber;
    }

    public void setEnterMobileNumber(String enterMobileNumber) {
        this.enterMobileNumber = enterMobileNumber;
    }

    public String getSelectPlan() {
        return selectPlan;
    }

    public void setSelectPlan(String selectPlan) {
        this.selectPlan = selectPlan;
    }

    public String getNoRecordFound() {
        return noRecordFound;
    }

    public void setNoRecordFound(String noRecordFound) {
        this.noRecordFound = noRecordFound;
    }

    public String getRequestSuccessful() {
        return requestSuccessful;
    }

    public void setRequestSuccessful(String requestSuccessful) {
        this.requestSuccessful = requestSuccessful;
    }

    public String getEnterValidMobileNumber() {
        return enterValidMobileNumber;
    }

    public void setEnterValidMobileNumber(String enterValidMobileNumber) {
        this.enterValidMobileNumber = enterValidMobileNumber;
    }

    public String getSelectNationality() {
        return selectNationality;
    }

    public void setSelectNationality(String selectNationality) {
        this.selectNationality = selectNationality;
    }

    public String getEnterFirstName() {
        return enterFirstName;
    }

    public void setEnterFirstName(String enterFirstName) {
        this.enterFirstName = enterFirstName;
    }

    public String getEnterLastName() {
        return enterLastName;
    }

    public void setEnterLastName(String enterLastName) {
        this.enterLastName = enterLastName;
    }

    public String getEnterNickName() {
        return enterNickName;
    }

    public void setEnterNickName(String enterNickName) {
        this.enterNickName = enterNickName;
    }

    public String getEnterEmail() {
        return enterEmail;
    }

    public void setEnterEmail(String enterEmail) {
        this.enterEmail = enterEmail;
    }

    public String getEnterValidEmail() {
        return enterValidEmail;
    }

    public void setEnterValidEmail(String enterValidEmail) {
        this.enterValidEmail = enterValidEmail;
    }

    public String getSelectDateOfBirth() {
        return selectDateOfBirth;
    }

    public void setSelectDateOfBirth(String selectDateOfBirth) {
        this.selectDateOfBirth = selectDateOfBirth;
    }

    public String getEnterAddress() {
        return enterAddress;
    }

    public void setEnterAddress(String enterAddress) {
        this.enterAddress = enterAddress;
    }

    public String getEnterLandmark() {
        return enterLandmark;
    }

    public void setEnterLandmark(String enterLandmark) {
        this.enterLandmark = enterLandmark;
    }

    public String getEnterZipCode() {
        return enterZipCode;
    }

    public void setEnterZipCode(String enterZipCode) {
        this.enterZipCode = enterZipCode;
    }

    public String getEnterValidZipCode() {
        return enterValidZipCode;
    }

    public void setEnterValidZipCode(String enterValidZipCode) {
        this.enterValidZipCode = enterValidZipCode;
    }

    public String getSelectIdType() {
        return selectIdType;
    }

    public void setSelectIdType(String selectIdType) {
        this.selectIdType = selectIdType;
    }

    public String getEnterIdNumber() {
        return enterIdNumber;
    }

    public void setEnterIdNumber(String enterIdNumber) {
        this.enterIdNumber = enterIdNumber;
    }

    public String getEnterDestinationAddress() {
        return enterDestinationAddress;
    }

    public void setEnterDestinationAddress(String enterDestinationAddress) {
        this.enterDestinationAddress = enterDestinationAddress;
    }

    public String getEnterDestinationLandmark() {
        return enterDestinationLandmark;
    }

    public void setEnterDestinationLandmark(String enterDestinationLandmark) {
        this.enterDestinationLandmark = enterDestinationLandmark;
    }

    public String getEnterDestinationZipCode() {
        return enterDestinationZipCode;
    }

    public void setEnterDestinationZipCode(String enterDestinationZipCode) {
        this.enterDestinationZipCode = enterDestinationZipCode;
    }

    public String getSelectAvailableAgents() {
        return selectAvailableAgents;
    }

    public void setSelectAvailableAgents(String selectAvailableAgents) {
        this.selectAvailableAgents = selectAvailableAgents;
    }

    public String getSelectPurposeOfTransfer() {
        return selectPurposeOfTransfer;
    }

    public void setSelectPurposeOfTransfer(String selectPurposeOfTransfer) {
        this.selectPurposeOfTransfer = selectPurposeOfTransfer;
    }

    public String getBeneficiaryUpdatedSuccessfully() {
        return beneficiaryUpdatedSuccessfully;
    }

    public void setBeneficiaryUpdatedSuccessfully(String beneficiaryUpdatedSuccessfully) {
        this.beneficiaryUpdatedSuccessfully = beneficiaryUpdatedSuccessfully;
    }

    public String getBalanceUpdatedSuccessfully() {
        return balanceUpdatedSuccessfully;
    }

    public void setBalanceUpdatedSuccessfully(String balanceUpdatedSuccessfully) {
        this.balanceUpdatedSuccessfully = balanceUpdatedSuccessfully;
    }

    public String getEnterOldPIN() {
        return enterOldPIN;
    }

    public void setEnterOldPIN(String enterOldPIN) {
        this.enterOldPIN = enterOldPIN;
    }

    public String getEnter6DigitPIN() {
        return enter6DigitPIN;
    }

    public void setEnter6DigitPIN(String enter6DigitPIN) {
        this.enter6DigitPIN = enter6DigitPIN;
    }

    public String getEnterNewPIN() {
        return enterNewPIN;
    }

    public void setEnterNewPIN(String enterNewPIN) {
        this.enterNewPIN = enterNewPIN;
    }

    public String getEnterRepeatPIN() {
        return enterRepeatPIN;
    }

    public void setEnterRepeatPIN(String enterRepeatPIN) {
        this.enterRepeatPIN = enterRepeatPIN;
    }

    public String getPINDoesNotMatch() {
        return pINDoesNotMatch;
    }

    public void setPINDoesNotMatch(String pINDoesNotMatch) {
        this.pINDoesNotMatch = pINDoesNotMatch;
    }

    public String getSelectGender() {
        return selectGender;
    }

    public void setSelectGender(String selectGender) {
        this.selectGender = selectGender;
    }

    public String getSelectCountryOfResidence() {
        return selectCountryOfResidence;
    }

    public void setSelectCountryOfResidence(String selectCountryOfResidence) {
        this.selectCountryOfResidence = selectCountryOfResidence;
    }

    public String getEnterPassportNo() {
        return enterPassportNo;
    }

    public void setEnterPassportNo(String enterPassportNo) {
        this.enterPassportNo = enterPassportNo;
    }

    public String getPassportNoNumberShouldBe10Digits() {
        return passportNoNumberShouldBe10Digits;
    }

    public void setPassportNoNumberShouldBe10Digits(String passportNoNumberShouldBe10Digits) {
        this.passportNoNumberShouldBe10Digits = passportNoNumberShouldBe10Digits;
    }

    public String getEnterEmiratesId() {
        return enterEmiratesId;
    }

    public void setEnterEmiratesId(String enterEmiratesId) {
        this.enterEmiratesId = enterEmiratesId;
    }

    public String getEmiratesIdShouldBe10Digits() {
        return emiratesIdShouldBe10Digits;
    }

    public void setEmiratesIdShouldBe10Digits(String emiratesIdShouldBe10Digits) {
        this.emiratesIdShouldBe10Digits = emiratesIdShouldBe10Digits;
    }

    public String getCameraPreviewNotAvailable() {
        return cameraPreviewNotAvailable;
    }

    public void setCameraPreviewNotAvailable(String cameraPreviewNotAvailable) {
        this.cameraPreviewNotAvailable = cameraPreviewNotAvailable;
    }

    public String getEnterAmount() {
        return enterAmount;
    }

    public void setEnterAmount(String enterAmount) {
        this.enterAmount = enterAmount;
    }

    public String getEnterValidAmount() {
        return enterValidAmount;
    }

    public void setEnterValidAmount(String enterValidAmount) {
        this.enterValidAmount = enterValidAmount;
    }

    public String getAmountIsLargerThanYourBalance() {
        return amountIsLargerThanYourBalance;
    }

    public void setAmountIsLargerThanYourBalance(String amountIsLargerThanYourBalance) {
        this.amountIsLargerThanYourBalance = amountIsLargerThanYourBalance;
    }

    public String getEnterName() {
        return enterName;
    }

    public void setEnterName(String enterName) {
        this.enterName = enterName;
    }

    public String getMobileNumberIsNotRegistered() {
        return mobileNumberIsNotRegistered;
    }

    public void setMobileNumberIsNotRegistered(String mobileNumberIsNotRegistered) {
        this.mobileNumberIsNotRegistered = mobileNumberIsNotRegistered;
    }

    public String getEnterValidIdNumber() {
        return enterValidIdNumber;
    }

    public void setEnterValidIdNumber(String enterValidIdNumber) {
        this.enterValidIdNumber = enterValidIdNumber;
    }

    public String getEnterSendMoney() {
        return enterSendMoney;
    }

    public void setEnterSendMoney(String enterSendMoney) {
        this.enterSendMoney = enterSendMoney;
    }

    public String getEnterReceiveMoney() {
        return enterReceiveMoney;
    }

    public void setEnterReceiveMoney(String enterReceiveMoney) {
        this.enterReceiveMoney = enterReceiveMoney;
    }

    public String getEnterAccountNo() {
        return enterAccountNo;
    }

    public void setEnterAccountNo(String enterAccountNo) {
        this.enterAccountNo = enterAccountNo;
    }

    public String getEnterBankName() {
        return enterBankName;
    }

    public void setEnterBankName(String enterBankName) {
        this.enterBankName = enterBankName;
    }

    public String getEnterBranchCode() {
        return enterBranchCode;
    }

    public void setEnterBranchCode(String enterBranchCode) {
        this.enterBranchCode = enterBranchCode;
    }

    public String getEnterBranchAddress() {
        return enterBranchAddress;
    }

    public void setEnterBranchAddress(String enterBranchAddress) {
        this.enterBranchAddress = enterBranchAddress;
    }

    public String getSelectBank() {
        return selectBank;
    }

    public void setSelectBank(String selectBank) {
        this.selectBank = selectBank;
    }

    public String getEnterMinimum12DigitAccountNumber() {
        return enterMinimum12DigitAccountNumber;
    }

    public void setEnterMinimum12DigitAccountNumber(String enterMinimum12DigitAccountNumber) {
        this.enterMinimum12DigitAccountNumber = enterMinimum12DigitAccountNumber;
    }

    public String getPhoneNumberShouldBeAtLeast9Digits() {
        return phoneNumberShouldBeAtLeast9Digits;
    }

    public void setPhoneNumberShouldBeAtLeast9Digits(String phoneNumberShouldBeAtLeast9Digits) {
        this.phoneNumberShouldBeAtLeast9Digits = phoneNumberShouldBeAtLeast9Digits;
    }

    public String getEnterValidBankDetails() {
        return enterValidBankDetails;
    }

    public void setEnterValidBankDetails(String enterValidBankDetails) {
        this.enterValidBankDetails = enterValidBankDetails;
    }

    public String getEnterAValidAmount() {
        return enterAValidAmount;
    }

    public void setEnterAValidAmount(String enterAValidAmount) {
        this.enterAValidAmount = enterAValidAmount;
    }

    public String getPINIsInvalid() {
        return pINIsInvalid;
    }

    public void setPINIsInvalid(String pINIsInvalid) {
        this.pINIsInvalid = pINIsInvalid;
    }

    public String getEnterEmailOrMobileNumber() {
        return enterEmailOrMobileNumber;
    }

    public void setEnterEmailOrMobileNumber(String enterEmailOrMobileNumber) {
        this.enterEmailOrMobileNumber = enterEmailOrMobileNumber;
    }

    public String getEnterValidEmailOrMobileNumber() {
        return enterValidEmailOrMobileNumber;
    }

    public void setEnterValidEmailOrMobileNumber(String enterValidEmailOrMobileNumber) {
        this.enterValidEmailOrMobileNumber = enterValidEmailOrMobileNumber;
    }

    public String getEnterVerificationCode() {
        return enterVerificationCode;
    }

    public void setEnterVerificationCode(String enterVerificationCode) {
        this.enterVerificationCode = enterVerificationCode;
    }

    public String getMinimum6CharactersRequired() {
        return minimum6CharactersRequired;
    }

    public void setMinimum6CharactersRequired(String minimum6CharactersRequired) {
        this.minimum6CharactersRequired = minimum6CharactersRequired;
    }

    public String getMobileNumberShouldBeAtLeast9Digits() {
        return mobileNumberShouldBeAtLeast9Digits;
    }

    public void setMobileNumberShouldBeAtLeast9Digits(String mobileNumberShouldBeAtLeast9Digits) {
        this.mobileNumberShouldBeAtLeast9Digits = mobileNumberShouldBeAtLeast9Digits;
    }

    public String getFileUploadedSuccessfully() {
        return fileUploadedSuccessfully;
    }

    public void setFileUploadedSuccessfully(String fileUploadedSuccessfully) {
        this.fileUploadedSuccessfully = fileUploadedSuccessfully;
    }

    public String getSomethingWentWrong() {
        return somethingWentWrong;
    }

    public void setSomethingWentWrong(String somethingWentWrong) {
        this.somethingWentWrong = somethingWentWrong;
    }

    public String getYourRegistrationIsDone() {
        return yourRegistrationIsDone;
    }

    public void setYourRegistrationIsDone(String yourRegistrationIsDone) {
        this.yourRegistrationIsDone = yourRegistrationIsDone;
    }

    public String getMessage(String object) {
        String str = "";

        switch (object) {
            case "JSON_ERROR":
                str = getJSONERROR();
                break;
            case "INVALID_USERPASS":
                str = getINVALIDUSERPASS();
                break;
            case "NORECORD":
                str = getNORECORD();
                break;
            case "RECORDFOUND":
                str = getRECORDFOUND();
                break;
            case "INVALIDOTP":
                str = getINVALIDOTP();
                break;
            case "INVALID_OLD_PASS":
                str = getINVALIDOLDPASS();
                break;
            case "PASSWORD_CHANGED":
                str = getPASSWORDCHANGED();
                break;
            case "INVALID_PIN":
                str = getINVALIDPIN();
                break;
            case "VALID_PIN":
                str = getVALIDPIN();
                break;
            case "PIN_CHANGED":
                str = getPINCHANGED();
                break;
            case "FORGOT_PASSWORD":
                str = getFORGOTPASSWORD();
                break;
            case "FORGOT_PIN":
                str = getFORGOTPIN();
                break;
            case "RESEND_OTP":
                str = getRESENDOTP();
                break;
            case "REGISTERUSER_SUCCESS":
                str = getREGISTERUSERSUCCESS();
                break;
            case "LOGIN_SUCCESS":
                str = getLOGINSUCCESS();
                break;
            case "INVALID_PIN_SIZE":
                str = getINVALIDPINSIZE();
                break;
            case "VERFIFYOTP_SUCCESS":
                str = getVERFIFYOTPSUCCESS();
                break;
            case "CHANGEPASSWORD_SUCCESS":
                str = getCHANGEPASSWORDSUCCESS();
                break;
            case "CHANGEPIN_SUCCESS":
                str = getCHANGEPINSUCCESS();
                break;
            case "CHANGEPROFILE_SUCCESS":
                str = getCHANGEPROFILESUCCESS();
                break;
            case "WALLET_INVALID":
                str = getWALLETINVALID();
                break;
            case "BALANCE_INVALID":
                str = getBALANCEINVALID();
                break;
            case "RECEIVER_WALLET_LIMIT":
                str = getRECEIVERWALLETLIMIT();
                break;
            case "SENDER_WALLET_LIMIT":
                str = getSENDERWALLETLIMIT();
                break;
            case "TOPUP_SUCCESS":
                str = getTOPUPSUCCESS();
                break;
            case "TRANSFER_SUCCESS":
                str = getTRANSFERSUCCESS();
                break;
            case "ADDBANK_SUCCESS":
                str = getADDBANKSUCCESS();
                break;
            case "EDITBANK_SUCCESS":
                str = getEDITBANKSUCCESS();
                break;
            case "IS_MISSING":
                str = getISMISSING();
                break;
            case "SETTINGS_SAVED":
                str = getSETTINGSSAVED();
                break;
            case "RITMANPAY_ERROR":
                str = getRITMANPAYERROR();
                break;
            case "ANDROID_URL":
                str = getANDROIDURL();
                break;
            case "DEVICE_UPDATED":
                str = getDEVICEUPDATED();
                break;
            case "DELETE_SUCCESS":
                str = getDELETESUCCESS();
                break;
            case "something_went_wrong":
                str = getSomethingWentWrong();
                break;
            case "file_uploaded_successfully":
                str = getFileUploadedSuccessfully();
                break;
        }

        return str;
    }
}
