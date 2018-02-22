package com.hitachi_tstv.mist.it.pod_uniqlo.Bean;

import java.util.List;

/**
 * Created by Vipavee on 22/02/2018.
 */

public class GetJobDetailTransfer {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * DO : 1013-20180220-01345
         * Location : Seacon Square
         * StoreCode : 1019
         * Running_No : 18020006
         * StoreType : Seacon
         * Status : Complete
         * Total : 5
         * ImgPath :
         * ImgFileName :
         * ImagePlacement :
         */

        private String DO;
        private String Location;
        private String StoreCode;
        private String Running_No;
        private String StoreType;
        private String Status;
        private String Total;
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

        public String getTotal() {
            return Total;
        }

        public void setTotal(String Total) {
            this.Total = Total;
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
