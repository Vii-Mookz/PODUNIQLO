package com.hitachi_tstv.mist.it.pod_uniqlo.Bean;

import java.util.List;

/**
 * Created by Vipavee on 15/02/2018.
 */

public class GetJobDetail {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * DO : 1033-20180206-01068
         * Location : Seacon Square
         * StoreCode : 1019
         * Running_No : 18020003
         * StoreType : Seacon
         * Status : Complete
         * ArrivalDT : 15/02/2018 15-02
         * DepartureDT : 06/02/2018 15-02
         * ImgPath :\\172.xx.xx.xxx\Picture_xxx\
         * ImgFileName : PDT_2_xx1234.png
         * ImagePlacement : PDT_2
         */

        private String DO;
        private String Location;
        private String StoreCode;
        private String Running_No;
        private String StoreType;
        private String Status;
        private String ArrivalDT;
        private String DepartureDT;
        private String ImgPath;
        private String ImgFileName;
        private String ImagePlacement;

        public String getDO() {
            return DO;
        }

        public void setDO(String DO) {
            this.DO = DO;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String Location) {
            this.Location = Location;
        }

        public String getStoreCode() {
            return StoreCode;
        }

        public void setStoreCode(String StoreCode) {
            this.StoreCode = StoreCode;
        }

        public String getRunning_No() {
            return Running_No;
        }

        public void setRunning_No(String Running_No) {
            this.Running_No = Running_No;
        }

        public String getStoreType() {
            return StoreType;
        }

        public void setStoreType(String StoreType) {
            this.StoreType = StoreType;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getArrivalDT() {
            return ArrivalDT;
        }

        public void setArrivalDT(String ArrivalDT) {
            this.ArrivalDT = ArrivalDT;
        }

        public String getDepartureDT() {
            return DepartureDT;
        }

        public void setDepartureDT(String DepartureDT) {
            this.DepartureDT = DepartureDT;
        }

        public String getImgPath() {
            return ImgPath;
        }

        public void setImgPath(String ImgPath) {
            this.ImgPath = ImgPath;
        }

        public String getImgFileName() {
            return ImgFileName;
        }

        public void setImgFileName(String ImgFileName) {
            this.ImgFileName = ImgFileName;
        }

        public String getImagePlacement() {
            return ImagePlacement;
        }

        public void setImagePlacement(String ImagePlacement) {
            this.ImagePlacement = ImagePlacement;
        }
    }
}
