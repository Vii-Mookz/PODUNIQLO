package com.hitachi_tstv.mist.it.pod_uniqlo.Bean;

import java.util.List;

/**
 * Created by Vipavee on 08/02/2018.
 */

public class Test {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * TruckReg : 1ฒข-4417
         * TruckType : 4-W
         * DriverName : Komson
         * Driversirname :
         * TransportID : SVE
         */

        private String TruckReg;
        private String TruckType;
        private String DriverName;
        private String Driversirname;
        private String TransportID;

        public String getTruckReg() {
            return TruckReg;
        }

        public void setTruckReg(String TruckReg) {
            this.TruckReg = TruckReg;
        }

        public String getTruckType() {
            return TruckType;
        }

        public void setTruckType(String TruckType) {
            this.TruckType = TruckType;
        }

        public String getDriverName() {
            return DriverName;
        }

        public void setDriverName(String DriverName) {
            this.DriverName = DriverName;
        }

        public String getDriversirname() {
            return Driversirname;
        }

        public void setDriversirname(String Driversirname) {
            this.Driversirname = Driversirname;
        }

        public String getTransportID() {
            return TransportID;
        }

        public void setTransportID(String TransportID) {
            this.TransportID = TransportID;
        }
    }
}
