package com.tkx.driver.activities;

public class ModelPayphoneOnline {
    /**
     * version : 11.00
     * result : 1
     * message : Success
     * data : {"status":"REQUEST_PROCESSING","token":"QKfJIVI88ulgX_11jPoeXz0MaLbjxvFlw8PoQpKP77MnKBjbz3jTXzxrZ-PDBYN1brR5ZTclN3av4huA3yx3dLFA0IqeIo1NxGBTQCi72EJ1bcvHzBFvzxjFkDbSdhRkPvf2DL6f274e1Khb-6UgYZGb9jbL2iSu_KyZorKhryYVBEr2su8KEUxrIGFAVDNIC8XLDdU9bDpLPS2YEXCb4ni2qMZVm3P5IhnHE8-R4JhKg3LiI_48mqy_WqoyeRg0oSlsvO2n3fbhupEnZ90ZNRIAZ1vKUdMcOKGVLHjXWvnTiBtGGMIuzxeupj_ARuigTpxd2F1meE4Cjlk4mGAfic7f8Ug","url":"https://pay.payphonetodoesposible.com/api/Sale/5209545"}
     */

    private String version;
    private String result;
    private String message;
    private DataBean data;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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
         * status : REQUEST_PROCESSING
         * token : QKfJIVI88ulgX_11jPoeXz0MaLbjxvFlw8PoQpKP77MnKBjbz3jTXzxrZ-PDBYN1brR5ZTclN3av4huA3yx3dLFA0IqeIo1NxGBTQCi72EJ1bcvHzBFvzxjFkDbSdhRkPvf2DL6f274e1Khb-6UgYZGb9jbL2iSu_KyZorKhryYVBEr2su8KEUxrIGFAVDNIC8XLDdU9bDpLPS2YEXCb4ni2qMZVm3P5IhnHE8-R4JhKg3LiI_48mqy_WqoyeRg0oSlsvO2n3fbhupEnZ90ZNRIAZ1vKUdMcOKGVLHjXWvnTiBtGGMIuzxeupj_ARuigTpxd2F1meE4Cjlk4mGAfic7f8Ug
         * url : https://pay.payphonetodoesposible.com/api/Sale/5209545
         */

        private String status;
        private String token;
        private String url;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}


