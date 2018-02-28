package com.hitachi_tstv.mist.it.pod_uniqlo;

/**
 * Created by Vipavee on 24/01/2018.
 */

public interface Constant {

    String serverString = "http://203.154.103.42/";
    String projectString = "ServiceTransport";
    //GET
    String urlGetUser = serverString + projectString + "/Service.svc/getUserLogin/";
    String urlGetJobList = serverString + projectString + "/Service.svc/GetJobList/";
    String urlGetDate = serverString + projectString + "/Service.svc/GetJobTotalList/";
    String urlGetJobDetail = serverString + projectString + "/Service.svc/GetJobDetail/";
    String urlGetJobDetailTransfer = serverString + projectString + "/Service.svc/GetJobDetailTransfer/";
    //POST
    String urlSaveTimeStamp = serverString + projectString + "/Service.svc/SaveTimeStampOfStore";
    String urlSaveImage = serverString + projectString + "/Service.svc/SaveImage";
    String urlSaveTotalTransfer = serverString + projectString + "/Service.svc/SaveTotalTransfer";


    String serverStringphp = "http://203.154.103.43/";
    String projectStringphp = "TmsUniqloport";
    String pathString = "/app/CenterService/";
    String urlUploadImage = serverStringphp + projectStringphp + pathString + "uploadImage.php";


}
