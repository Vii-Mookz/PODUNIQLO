package com.hitachi_tstv.mist.it.pod_uniqlo;

/**
 * Created by Vipavee on 24/01/2018.
 */

public interface Constant {

    String serverString = "http://203.154.103.42/";
    String projectString = "ServiceTransport";
//static final String serverString = "http://203.154.103.43/";
//    static final String projectString = "DMS_NK";
//    static final String pathString = "/app/CenterService/";
//    static final String urlGetUser = serverString + projectString + pathString + "ServiceUniqlo/getUser.php";
    String urlGetUser = serverString + projectString + "/Service.svc/getUserLogin/";
    String urlGetJobList = serverString + projectString + "/Service.svc/GetJobList/";
//    String urlGetJobListDate = serverString + projectString + "/app/centerservice/ServiceUniqlo/getListJobDate.php";
//    String urlGetJob = serverString + projectString + "/app/centerservice/ServiceUniqlo/getJob.php";
//    String urlGetJobDetail = serverString + projectString + "/app/centerservice/ServiceUniqlo/getJobDetail.php";




}
