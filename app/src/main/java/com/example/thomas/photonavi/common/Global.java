package com.example.thomas.photonavi.common;


public class Global {

    /****** Debug *********************************************************************************/

    public static final Boolean DEBUG                   = true;
    public static final String TAG                      = "PhotoNavi";

    /****** Server Url ****************************************************************************/


    public static final String REST_AIP_URL = "http://192.168.25.25:8080/pork/api/";
    public static final String USER_LOGIN = "userLogin.do";
    public static final String JOIN_USER = "joinUser.do";
    /****** Setting  ******************************************************************************/

    public static final String FILE_NAME                = "intent_file.txt";
    public static final String PREFIX_JPG               = ".jpg";
    public static final String PREFIX_PNG               = ".png";
    public static final String STATUS_OK                = "0000";

    public static final int ALERT_ERR                   = 1;

    public static final int SELECT_PHOTO                = 10;
    public static final int SELECT_GALLERY              = 11;

    public static final int SEVER_MSG_RESULT            = 30;
    public static final int SEVER_JSON_GPS              = 31;
    public static final int SEVER_JSON_PHONE            = 32;
    public static final int SEVER_JSON_VERSION          = 33;
    public static final int SEVER_JSON_TEL              = 34;
    public static final int SEVER_JSON_SEAL             = 35;
    public static final int SEVER_JSON_DAMAGE           = 36;
    public static final int SEVER_JSON_SIGN             = 37;

    public static final int CALL_GPS_SEERVICE           = 50;
    public static final int CALL_PUSH_SERVICE           = 51;

    public static final int REQUEST_STORAGE             = 60;
    public static final int RESULT_CODE_SEAL            = 61;
    public static final int RESULT_CODE_SIGN            = 62;
    public static final int RESULT_CODE_DAMAGE          = 63;
    public static final int RESULT_CODE_IMG_UPLOAD      = 64;
    public static final int TEST                        = 99;
    public static final int JSON_GPS                    = 80;
    public static final int JSON_SIGN                   = 81;
    public static final int JSON_DAMAGE                 = 82;
    public static final int JSON_SEAL                   = 83;
    public static final int JSON_AUTO_LOGIN             = 84;
    public static final int JSON_AUTO_LOGIN_ID          = 85;
    public static final int JSON_UPLOAD_RESULT          = 86;
    public static final int JSON_PUSH                   = 87;

    /**********************************************************************************************/


}
