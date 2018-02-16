package com.hitachi_tstv.mist.it.pod_uniqlo.Bean;

import java.util.List;

/**
 * Created by Vipavee on 15/02/2018.
 */

public class GetDate {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * DeliveryDate : 02/02/2018
         * DoAmount : 2
         */

        private String DeliveryDate;
        private String DoAmount;

        public String getDeliveryDate() {
            return DeliveryDate;
        }

        public void setDeliveryDate(String DeliveryDate) {
            this.DeliveryDate = DeliveryDate;
        }

        public String getDoAmount() {
            return DoAmount;
        }

        public void setDoAmount(String DoAmount) {
            this.DoAmount = DoAmount;
        }
    }
}
