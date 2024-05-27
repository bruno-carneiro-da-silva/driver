package com.tkx.driver.NewEarningModule;

import java.util.List;

public class ModelNewDriverEarning {

    /**
     * result : 1
     * message : Your Earning!
     * data : {"current_period":"11 May 2020-17 May 2020","current_period_forsend":"11 May 2020 00:00:00-17 May 2020 23:59:59","total_earnings":{"left_text":"Trip Earnings","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 90","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true},"holder_data":[{"left_text":"Fare Amount in Rides","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 100.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Company Fees in Rides","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" - INR 10.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Tax","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" - INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Toll Amount","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Tip Amount","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Cancellation Amount","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Referral Amount","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Round Off","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true}],"trips":{"left_text":"Total Trips - 1","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 100","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true},"trip_history":{"left_text":"Trip History","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":false},"total_amount_collected":{"left_text":"Total Amount Collected","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":false},"received_cash":{"left_text":"Cash","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 100","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true},"received_in_wallet":{"left_text":"Wallet","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 0","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true},"wallet_balance":{"left_text":"Current Wallet Balance","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR -10.00","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true}}
     */

    private String result;
    private String message;
    private DataBean data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * current_period : 11 May 2020-17 May 2020
         * current_period_forsend : 11 May 2020 00:00:00-17 May 2020 23:59:59
         * total_earnings : {"left_text":"Trip Earnings","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 90","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true}
         * holder_data : [{"left_text":"Fare Amount in Rides","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 100.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Company Fees in Rides","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" - INR 10.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Tax","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" - INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Toll Amount","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Tip Amount","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Cancellation Amount","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Referral Amount","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true},{"left_text":"Round Off","left_text_style":"NORMAL","left_text_color":"333333","left_text_visibility":true,"right_text":" + INR 0.00","right_text_style":"NORMAL","right_text_color":"333333","right_text_visibility":true}]
         * trips : {"left_text":"Total Trips - 1","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 100","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true}
         * trip_history : {"left_text":"Trip History","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":false}
         * total_amount_collected : {"left_text":"Total Amount Collected","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":false}
         * received_cash : {"left_text":"Cash","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 100","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true}
         * received_in_wallet : {"left_text":"Wallet","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR 0","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true}
         * wallet_balance : {"left_text":"Current Wallet Balance","left_text_style":"BOLD","left_text_color":"333333","left_text_visibility":true,"right_text":"INR -10.00","right_text_style":"BOLD","right_text_color":"333333","right_text_visibility":true}
         */

        private String current_period;
        private String current_period_forsend;
        private TotalEarningsBean total_earnings;
        private TripsBean trips;
        private TripHistoryBean trip_history;
        private TotalAmountCollectedBean total_amount_collected;
        private ReceivedCashBean received_cash;
        private ReceivedInWalletBean received_in_wallet;
        private WalletBalanceBean wallet_balance;
        private List<HolderDataBean> holder_data;

        public String getCurrent_period() {
            return current_period;
        }

        public void setCurrent_period(String current_period) {
            this.current_period = current_period;
        }

        public String getCurrent_period_forsend() {
            return current_period_forsend;
        }

        public void setCurrent_period_forsend(String current_period_forsend) {
            this.current_period_forsend = current_period_forsend;
        }

        public TotalEarningsBean getTotal_earnings() {
            return total_earnings;
        }

        public void setTotal_earnings(TotalEarningsBean total_earnings) {
            this.total_earnings = total_earnings;
        }

        public TripsBean getTrips() {
            return trips;
        }

        public void setTrips(TripsBean trips) {
            this.trips = trips;
        }

        public TripHistoryBean getTrip_history() {
            return trip_history;
        }

        public void setTrip_history(TripHistoryBean trip_history) {
            this.trip_history = trip_history;
        }

        public TotalAmountCollectedBean getTotal_amount_collected() {
            return total_amount_collected;
        }

        public void setTotal_amount_collected(TotalAmountCollectedBean total_amount_collected) {
            this.total_amount_collected = total_amount_collected;
        }

        public ReceivedCashBean getReceived_cash() {
            return received_cash;
        }

        public void setReceived_cash(ReceivedCashBean received_cash) {
            this.received_cash = received_cash;
        }

        public ReceivedInWalletBean getReceived_in_wallet() {
            return received_in_wallet;
        }

        public void setReceived_in_wallet(ReceivedInWalletBean received_in_wallet) {
            this.received_in_wallet = received_in_wallet;
        }

        public WalletBalanceBean getWallet_balance() {
            return wallet_balance;
        }

        public void setWallet_balance(WalletBalanceBean wallet_balance) {
            this.wallet_balance = wallet_balance;
        }

        public List<HolderDataBean> getHolder_data() {
            return holder_data;
        }

        public void setHolder_data(List<HolderDataBean> holder_data) {
            this.holder_data = holder_data;
        }

        public static class TotalEarningsBean {
            /**
             * left_text : Trip Earnings
             * left_text_style : BOLD
             * left_text_color : 333333
             * left_text_visibility : true
             * right_text : INR 90
             * right_text_style : BOLD
             * right_text_color : 333333
             * right_text_visibility : true
             */

            private String left_text;
            private String left_text_style;
            private String left_text_color;
            private boolean left_text_visibility;
            private String right_text;
            private String right_text_style;
            private String right_text_color;
            private boolean right_text_visibility;

            public String getLeft_text() {
                return left_text;
            }

            public void setLeft_text(String left_text) {
                this.left_text = left_text;
            }

            public String getLeft_text_style() {
                return left_text_style;
            }

            public void setLeft_text_style(String left_text_style) {
                this.left_text_style = left_text_style;
            }

            public String getLeft_text_color() {
                return left_text_color;
            }

            public void setLeft_text_color(String left_text_color) {
                this.left_text_color = left_text_color;
            }

            public boolean isLeft_text_visibility() {
                return left_text_visibility;
            }

            public void setLeft_text_visibility(boolean left_text_visibility) {
                this.left_text_visibility = left_text_visibility;
            }

            public String getRight_text() {
                return right_text;
            }

            public void setRight_text(String right_text) {
                this.right_text = right_text;
            }

            public String getRight_text_style() {
                return right_text_style;
            }

            public void setRight_text_style(String right_text_style) {
                this.right_text_style = right_text_style;
            }

            public String getRight_text_color() {
                return right_text_color;
            }

            public void setRight_text_color(String right_text_color) {
                this.right_text_color = right_text_color;
            }

            public boolean isRight_text_visibility() {
                return right_text_visibility;
            }

            public void setRight_text_visibility(boolean right_text_visibility) {
                this.right_text_visibility = right_text_visibility;
            }
        }

        public static class TripsBean {
            /**
             * left_text : Total Trips - 1
             * left_text_style : BOLD
             * left_text_color : 333333
             * left_text_visibility : true
             * right_text : INR 100
             * right_text_style : BOLD
             * right_text_color : 333333
             * right_text_visibility : true
             */

            private String left_text;
            private String left_text_style;
            private String left_text_color;
            private boolean left_text_visibility;
            private String right_text;
            private String right_text_style;
            private String right_text_color;
            private boolean right_text_visibility;

            public String getLeft_text() {
                return left_text;
            }

            public void setLeft_text(String left_text) {
                this.left_text = left_text;
            }

            public String getLeft_text_style() {
                return left_text_style;
            }

            public void setLeft_text_style(String left_text_style) {
                this.left_text_style = left_text_style;
            }

            public String getLeft_text_color() {
                return left_text_color;
            }

            public void setLeft_text_color(String left_text_color) {
                this.left_text_color = left_text_color;
            }

            public boolean isLeft_text_visibility() {
                return left_text_visibility;
            }

            public void setLeft_text_visibility(boolean left_text_visibility) {
                this.left_text_visibility = left_text_visibility;
            }

            public String getRight_text() {
                return right_text;
            }

            public void setRight_text(String right_text) {
                this.right_text = right_text;
            }

            public String getRight_text_style() {
                return right_text_style;
            }

            public void setRight_text_style(String right_text_style) {
                this.right_text_style = right_text_style;
            }

            public String getRight_text_color() {
                return right_text_color;
            }

            public void setRight_text_color(String right_text_color) {
                this.right_text_color = right_text_color;
            }

            public boolean isRight_text_visibility() {
                return right_text_visibility;
            }

            public void setRight_text_visibility(boolean right_text_visibility) {
                this.right_text_visibility = right_text_visibility;
            }
        }

        public static class TripHistoryBean {
            /**
             * left_text : Trip History
             * left_text_style : BOLD
             * left_text_color : 333333
             * left_text_visibility : true
             * right_text :
             * right_text_style : BOLD
             * right_text_color : 333333
             * right_text_visibility : false
             */

            private String left_text;
            private String left_text_style;
            private String left_text_color;
            private boolean left_text_visibility;
            private String right_text;
            private String right_text_style;
            private String right_text_color;
            private boolean right_text_visibility;

            public String getLeft_text() {
                return left_text;
            }

            public void setLeft_text(String left_text) {
                this.left_text = left_text;
            }

            public String getLeft_text_style() {
                return left_text_style;
            }

            public void setLeft_text_style(String left_text_style) {
                this.left_text_style = left_text_style;
            }

            public String getLeft_text_color() {
                return left_text_color;
            }

            public void setLeft_text_color(String left_text_color) {
                this.left_text_color = left_text_color;
            }

            public boolean isLeft_text_visibility() {
                return left_text_visibility;
            }

            public void setLeft_text_visibility(boolean left_text_visibility) {
                this.left_text_visibility = left_text_visibility;
            }

            public String getRight_text() {
                return right_text;
            }

            public void setRight_text(String right_text) {
                this.right_text = right_text;
            }

            public String getRight_text_style() {
                return right_text_style;
            }

            public void setRight_text_style(String right_text_style) {
                this.right_text_style = right_text_style;
            }

            public String getRight_text_color() {
                return right_text_color;
            }

            public void setRight_text_color(String right_text_color) {
                this.right_text_color = right_text_color;
            }

            public boolean isRight_text_visibility() {
                return right_text_visibility;
            }

            public void setRight_text_visibility(boolean right_text_visibility) {
                this.right_text_visibility = right_text_visibility;
            }
        }

        public static class TotalAmountCollectedBean {
            /**
             * left_text : Total Amount Collected
             * left_text_style : BOLD
             * left_text_color : 333333
             * left_text_visibility : true
             * right_text :
             * right_text_style : BOLD
             * right_text_color : 333333
             * right_text_visibility : false
             */

            private String left_text;
            private String left_text_style;
            private String left_text_color;
            private boolean left_text_visibility;
            private String right_text;
            private String right_text_style;
            private String right_text_color;
            private boolean right_text_visibility;

            public String getLeft_text() {
                return left_text;
            }

            public void setLeft_text(String left_text) {
                this.left_text = left_text;
            }

            public String getLeft_text_style() {
                return left_text_style;
            }

            public void setLeft_text_style(String left_text_style) {
                this.left_text_style = left_text_style;
            }

            public String getLeft_text_color() {
                return left_text_color;
            }

            public void setLeft_text_color(String left_text_color) {
                this.left_text_color = left_text_color;
            }

            public boolean isLeft_text_visibility() {
                return left_text_visibility;
            }

            public void setLeft_text_visibility(boolean left_text_visibility) {
                this.left_text_visibility = left_text_visibility;
            }

            public String getRight_text() {
                return right_text;
            }

            public void setRight_text(String right_text) {
                this.right_text = right_text;
            }

            public String getRight_text_style() {
                return right_text_style;
            }

            public void setRight_text_style(String right_text_style) {
                this.right_text_style = right_text_style;
            }

            public String getRight_text_color() {
                return right_text_color;
            }

            public void setRight_text_color(String right_text_color) {
                this.right_text_color = right_text_color;
            }

            public boolean isRight_text_visibility() {
                return right_text_visibility;
            }

            public void setRight_text_visibility(boolean right_text_visibility) {
                this.right_text_visibility = right_text_visibility;
            }
        }

        public static class ReceivedCashBean {
            /**
             * left_text : Cash
             * left_text_style : BOLD
             * left_text_color : 333333
             * left_text_visibility : true
             * right_text : INR 100
             * right_text_style : BOLD
             * right_text_color : 333333
             * right_text_visibility : true
             */

            private String left_text;
            private String left_text_style;
            private String left_text_color;
            private boolean left_text_visibility;
            private String right_text;
            private String right_text_style;
            private String right_text_color;
            private boolean right_text_visibility;

            public String getLeft_text() {
                return left_text;
            }

            public void setLeft_text(String left_text) {
                this.left_text = left_text;
            }

            public String getLeft_text_style() {
                return left_text_style;
            }

            public void setLeft_text_style(String left_text_style) {
                this.left_text_style = left_text_style;
            }

            public String getLeft_text_color() {
                return left_text_color;
            }

            public void setLeft_text_color(String left_text_color) {
                this.left_text_color = left_text_color;
            }

            public boolean isLeft_text_visibility() {
                return left_text_visibility;
            }

            public void setLeft_text_visibility(boolean left_text_visibility) {
                this.left_text_visibility = left_text_visibility;
            }

            public String getRight_text() {
                return right_text;
            }

            public void setRight_text(String right_text) {
                this.right_text = right_text;
            }

            public String getRight_text_style() {
                return right_text_style;
            }

            public void setRight_text_style(String right_text_style) {
                this.right_text_style = right_text_style;
            }

            public String getRight_text_color() {
                return right_text_color;
            }

            public void setRight_text_color(String right_text_color) {
                this.right_text_color = right_text_color;
            }

            public boolean isRight_text_visibility() {
                return right_text_visibility;
            }

            public void setRight_text_visibility(boolean right_text_visibility) {
                this.right_text_visibility = right_text_visibility;
            }
        }

        public static class ReceivedInWalletBean {
            /**
             * left_text : Wallet
             * left_text_style : BOLD
             * left_text_color : 333333
             * left_text_visibility : true
             * right_text : INR 0
             * right_text_style : BOLD
             * right_text_color : 333333
             * right_text_visibility : true
             */

            private String left_text;
            private String left_text_style;
            private String left_text_color;
            private boolean left_text_visibility;
            private String right_text;
            private String right_text_style;
            private String right_text_color;
            private boolean right_text_visibility;

            public String getLeft_text() {
                return left_text;
            }

            public void setLeft_text(String left_text) {
                this.left_text = left_text;
            }

            public String getLeft_text_style() {
                return left_text_style;
            }

            public void setLeft_text_style(String left_text_style) {
                this.left_text_style = left_text_style;
            }

            public String getLeft_text_color() {
                return left_text_color;
            }

            public void setLeft_text_color(String left_text_color) {
                this.left_text_color = left_text_color;
            }

            public boolean isLeft_text_visibility() {
                return left_text_visibility;
            }

            public void setLeft_text_visibility(boolean left_text_visibility) {
                this.left_text_visibility = left_text_visibility;
            }

            public String getRight_text() {
                return right_text;
            }

            public void setRight_text(String right_text) {
                this.right_text = right_text;
            }

            public String getRight_text_style() {
                return right_text_style;
            }

            public void setRight_text_style(String right_text_style) {
                this.right_text_style = right_text_style;
            }

            public String getRight_text_color() {
                return right_text_color;
            }

            public void setRight_text_color(String right_text_color) {
                this.right_text_color = right_text_color;
            }

            public boolean isRight_text_visibility() {
                return right_text_visibility;
            }

            public void setRight_text_visibility(boolean right_text_visibility) {
                this.right_text_visibility = right_text_visibility;
            }
        }

        public static class WalletBalanceBean {
            /**
             * left_text : Current Wallet Balance
             * left_text_style : BOLD
             * left_text_color : 333333
             * left_text_visibility : true
             * right_text : INR -10.00
             * right_text_style : BOLD
             * right_text_color : 333333
             * right_text_visibility : true
             */

            private String left_text;
            private String left_text_style;
            private String left_text_color;
            private boolean left_text_visibility;
            private String right_text;
            private String right_text_style;
            private String right_text_color;
            private boolean right_text_visibility;

            public String getLeft_text() {
                return left_text;
            }

            public void setLeft_text(String left_text) {
                this.left_text = left_text;
            }

            public String getLeft_text_style() {
                return left_text_style;
            }

            public void setLeft_text_style(String left_text_style) {
                this.left_text_style = left_text_style;
            }

            public String getLeft_text_color() {
                return left_text_color;
            }

            public void setLeft_text_color(String left_text_color) {
                this.left_text_color = left_text_color;
            }

            public boolean isLeft_text_visibility() {
                return left_text_visibility;
            }

            public void setLeft_text_visibility(boolean left_text_visibility) {
                this.left_text_visibility = left_text_visibility;
            }

            public String getRight_text() {
                return right_text;
            }

            public void setRight_text(String right_text) {
                this.right_text = right_text;
            }

            public String getRight_text_style() {
                return right_text_style;
            }

            public void setRight_text_style(String right_text_style) {
                this.right_text_style = right_text_style;
            }

            public String getRight_text_color() {
                return right_text_color;
            }

            public void setRight_text_color(String right_text_color) {
                this.right_text_color = right_text_color;
            }

            public boolean isRight_text_visibility() {
                return right_text_visibility;
            }

            public void setRight_text_visibility(boolean right_text_visibility) {
                this.right_text_visibility = right_text_visibility;
            }
        }

        public static class HolderDataBean {
            /**
             * left_text : Fare Amount in Rides
             * left_text_style : NORMAL
             * left_text_color : 333333
             * left_text_visibility : true
             * right_text : INR 100.00
             * right_text_style : NORMAL
             * right_text_color : 333333
             * right_text_visibility : true
             */

            private String left_text;
            private String left_text_style;
            private String left_text_color;
            private boolean left_text_visibility;
            private String right_text;
            private String right_text_style;
            private String right_text_color;
            private boolean right_text_visibility;

            public String getLeft_text() {
                return left_text;
            }

            public void setLeft_text(String left_text) {
                this.left_text = left_text;
            }

            public String getLeft_text_style() {
                return left_text_style;
            }

            public void setLeft_text_style(String left_text_style) {
                this.left_text_style = left_text_style;
            }

            public String getLeft_text_color() {
                return left_text_color;
            }

            public void setLeft_text_color(String left_text_color) {
                this.left_text_color = left_text_color;
            }

            public boolean isLeft_text_visibility() {
                return left_text_visibility;
            }

            public void setLeft_text_visibility(boolean left_text_visibility) {
                this.left_text_visibility = left_text_visibility;
            }

            public String getRight_text() {
                return right_text;
            }

            public void setRight_text(String right_text) {
                this.right_text = right_text;
            }

            public String getRight_text_style() {
                return right_text_style;
            }

            public void setRight_text_style(String right_text_style) {
                this.right_text_style = right_text_style;
            }

            public String getRight_text_color() {
                return right_text_color;
            }

            public void setRight_text_color(String right_text_color) {
                this.right_text_color = right_text_color;
            }

            public boolean isRight_text_visibility() {
                return right_text_visibility;
            }

            public void setRight_text_visibility(boolean right_text_visibility) {
                this.right_text_visibility = right_text_visibility;
            }
        }
    }
}
