package com.tkx.driver.models;

import java.util.List;

public class ModelvehicleConfiguration {
    /**
     * result : 1
     * message : Vehicle Configuration
     * data : {"vehicle_type":[{"id":1,"vehicleTypeName":"Sedan","vehicleTypeImage":"vehicle/nyP6FvIZiRjrpi3HNtd5KPPKyytBYABbCJGngYJ3.png","vehicleTypeMapImage":"vehicle/ZpgdY077yfmVYz3t4O6qVIQgVwDhyLNKYd9g2b18.png","services":[{"id":1,"merchant_id":"1","serviceName":"Normal","serviceStatus":"1","created_at":"2018-05-18 20:30:00","updated_at":"2018-05-18 21:30:00"}]},{"id":2,"vehicleTypeName":"Luxury","vehicleTypeImage":"vehicle/TNlHOKqN1sIQu1yUtX0bOU2AqBgkwe6uZrbXcPh4.jpeg","vehicleTypeMapImage":"vehicle/0jr43fkvZxZMbVB1d4LcTxD6JPl0we3a36dZm2Xr.jpeg","services":[{"id":1,"merchant_id":"1","serviceName":"Normal","serviceStatus":"1","created_at":"2018-05-18 20:30:00","updated_at":"2018-05-18 21:30:00"}]},{"id":3,"vehicleTypeName":"Mini","vehicleTypeImage":"vehicle/h0Abg0rgZIaL9V6TRsGw6Soan7U2kWIP8nIve6eq.png","vehicleTypeMapImage":"vehicle/dE3aw0bTXmzGVTjERAnvT9AQHTHN6Y4o5MHcZ1xZ.png","services":[{"id":1,"merchant_id":"1","serviceName":"Normal","serviceStatus":"1","created_at":"2018-05-18 20:30:00","updated_at":"2018-05-18 21:30:00"}]},{"id":4,"vehicleTypeName":"Micro","vehicleTypeImage":"vehicle/44h4bqGH1RMXvPTc4XrAZpWnJqqLdUKxgbmDw5pC.png","vehicleTypeMapImage":"vehicle/f7s3T8rdkTNZHRAEztziztwzjjH9RFhvEvBFe9np.png","services":[{"id":2,"merchant_id":"1","serviceName":"Rental","serviceStatus":"1","created_at":"2018-05-18 23:30:00","updated_at":"2018-05-18 22:30:00"}]},{"id":5,"vehicleTypeName":"Micro english","vehicleTypeImage":"vehicle/SCaNip93Sfrwtk6Oy0yi478488TLitAVZdWsXxaw.jpeg","vehicleTypeMapImage":"vehicle/BS8sPUrfVH3nAAvXHFix3MvlFbMpy82F02WuWWLr.jpeg","services":[{"id":3,"merchant_id":"1","serviceName":"Transfer","serviceStatus":"1","created_at":"2018-05-18 22:30:00","updated_at":"2018-05-19 01:30:00"}]}],"vehicle_make":[{"id":1,"merchant_id":"1","vehicleMakeLogo":"vehicle/abjoZ7LusR8yWeBRQg8XU661nYhpDsPozmxm6tbo.png","vehicleMakeStatus":"1","created_at":"2018-10-10 06:30:06","updated_at":"2018-10-23 01:16:37","vehicleMakeName":"BMW"},{"id":2,"merchant_id":"1","vehicleMakeLogo":"vehiclelogo/uSfRG5pdFMqbmaJ9ljsGYiwmW6D6ZXA60JXaU66x.jpeg","vehicleMakeStatus":"1","created_at":"2018-10-10 07:22:00","updated_at":"2018-10-10 07:22:00","vehicleMakeName":"Mercury Benz"},{"id":3,"merchant_id":"1","vehicleMakeLogo":"vehiclelogo/teKDea7Eon86zVieKngkkAsm6kjAE3nE6uRYoVJf.jpeg","vehicleMakeStatus":"1","created_at":"2018-10-10 07:26:26","updated_at":"2018-10-10 07:26:26","vehicleMakeName":"swift"},{"id":4,"merchant_id":"1","vehicleMakeLogo":"vehiclelogo/Lk1JM0uNMXN5o0bdS6w22f645lNPGZQEDM6Du2or.jpeg","vehicleMakeStatus":"1","created_at":"2018-10-11 10:42:39","updated_at":"2018-10-11 10:42:39","vehicleMakeName":"वोक्सवैगन"}]}
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
        private List<VehicleTypeBean> vehicle_type;
        private List<VehicleMakeBean> vehicle_make;
        private List<VehicleTractionBean> vehicle_traction;
        private List<VehicleColorBean> vehicle_color;
        private List<VehicleAnttBean> vehicle_antt;

        public List<VehicleTypeBean> getVehicle_type() {
            return vehicle_type;
        }

        public void setVehicle_type(List<VehicleTypeBean> vehicle_type) {
            this.vehicle_type = vehicle_type;
        }

        public List<VehicleMakeBean> getVehicle_make() {
            return vehicle_make;
        }

        public void setVehicle_make(List<VehicleMakeBean> vehicle_make) {
            this.vehicle_make = vehicle_make;
        }

        public List<VehicleTractionBean> getVehicle_traction() {
            return vehicle_traction;
        }

        public void setVehicle_traction(List<VehicleTractionBean> vehicle_traction_name) {
            this.vehicle_traction = vehicle_traction_name;
        }

        public List<VehicleColorBean> getVehicle_color() {
            return vehicle_color;
        }

        public void setVehicle_color(List<VehicleColorBean> vehicle_color_name) {
            this.vehicle_color = vehicle_color_name;
        }

        public List<VehicleAnttBean> getVehicle_antt() {
            return vehicle_antt;
        }

        public void setVehicle_antt(List<VehicleAnttBean> vehicle_antt_name) {
            this.vehicle_antt = vehicle_antt_name;
        }

        public static class VehicleTypeBean {
            /**
             * id : 1
             * vehicleTypeName : Sedan
             * vehicleTypeImage : vehicle/nyP6FvIZiRjrpi3HNtd5KPPKyytBYABbCJGngYJ3.png
             * vehicleTypeMapImage : vehicle/ZpgdY077yfmVYz3t4O6qVIQgVwDhyLNKYd9g2b18.png
             * services : [{"id":1,"merchant_id":"1","serviceName":"Normal","serviceStatus":"1","created_at":"2018-05-18 20:30:00","updated_at":"2018-05-18 21:30:00"}]
             */

            private int id;
            private String vehicleTypeName;
            private String vehicleTypeImage;
            private String vehicleTypeMapImage;
            private List<ServicesBean> services;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getVehicleTypeName() {
                return vehicleTypeName;
            }

            public void setVehicleTypeName(String vehicleTypeName) {
                this.vehicleTypeName = vehicleTypeName;
            }

            public String getVehicleTypeImage() {
                return vehicleTypeImage;
            }

            public void setVehicleTypeImage(String vehicleTypeImage) {
                this.vehicleTypeImage = vehicleTypeImage;
            }

            public String getVehicleTypeMapImage() {
                return vehicleTypeMapImage;
            }

            public void setVehicleTypeMapImage(String vehicleTypeMapImage) {
                this.vehicleTypeMapImage = vehicleTypeMapImage;
            }

            public List<ServicesBean> getServices() {
                return services;
            }

            public void setServices(List<ServicesBean> services) {
                this.services = services;
            }

            public static class ServicesBean {
                /**
                 * id : 1
                 * merchant_id : 1
                 * serviceName : Normal
                 * serviceStatus : 1
                 * created_at : 2018-05-18 20:30:00
                 * updated_at : 2018-05-18 21:30:00
                 */

                private int id;
                private String merchant_id;
                private String serviceName;
                private String serviceStatus;
                private String created_at;
                private String updated_at;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getMerchant_id() {
                    return merchant_id;
                }

                public void setMerchant_id(String merchant_id) {
                    this.merchant_id = merchant_id;
                }

                public String getServiceName() {
                    return serviceName;
                }

                public void setServiceName(String serviceName) {
                    this.serviceName = serviceName;
                }

                public String getServiceStatus() {
                    return serviceStatus;
                }

                public void setServiceStatus(String serviceStatus) {
                    this.serviceStatus = serviceStatus;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }

                public String getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(String updated_at) {
                    this.updated_at = updated_at;
                }
            }
        }

        public static class VehicleMakeBean {
            /**
             * id : 1
             * merchant_id : 1
             * vehicleMakeLogo : vehicle/abjoZ7LusR8yWeBRQg8XU661nYhpDsPozmxm6tbo.png
             * vehicleMakeStatus : 1
             * created_at : 2018-10-10 06:30:06
             * updated_at : 2018-10-23 01:16:37
             * vehicleMakeName : BMW
             */

            private int id;
            private String merchant_id;
            private String vehicleMakeLogo;
            private String vehicleMakeStatus;
            private String created_at;
            private String updated_at;
            private String vehicleMakeName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getVehicleMakeLogo() {
                return vehicleMakeLogo;
            }

            public void setVehicleMakeLogo(String vehicleMakeLogo) {
                this.vehicleMakeLogo = vehicleMakeLogo;
            }

            public String getVehicleMakeStatus() {
                return vehicleMakeStatus;
            }

            public void setVehicleMakeStatus(String vehicleMakeStatus) {
                this.vehicleMakeStatus = vehicleMakeStatus;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getVehicleMakeName() {
                return vehicleMakeName;
            }

            public void setVehicleMakeName(String vehicleMakeName) {
                this.vehicleMakeName = vehicleMakeName;
            }
        }

        public static class VehicleTractionBean {
            private int id;
            private String merchant_id;
            private String locale;
            private String vehicle_traction_name;
            private String deleted_at;
            private String created_at;
            private String updated_at;            

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }

            public String getVehicleTractionName() {
                return vehicle_traction_name;
            }

            public void setVehicleTractionName(String vehicle_traction_name) {
                this.vehicle_traction_name = vehicle_traction_name;
            }

            public String getVehicleDeleted_at() {
                return deleted_at;
            }

            public void setVehicleDeleted_at(String deleted_at) {
                this.deleted_at = deleted_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }            
        }

        public static class VehicleColorBean {
            private int id;
            private String merchant_id;
            private String locale;
            private String vehicle_color_name;
            private String deleted_at;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }

            public String getVehicleColorName() {
                return vehicle_color_name;
            }

            public void setVehicleColorName(String vehicle_color_name) {
                this.vehicle_color_name = vehicle_color_name;
            }

            public String getVehicleDeleted_at() {
                return deleted_at;
            }

            public void setVehicleDeleted_at(String deleted_at) {
                this.deleted_at = deleted_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }

        public static class VehicleAnttBean {
            private int id;
            private String merchant_id;
            private String locale;
            private String vehicle_antt_name;
            private String deleted_at;
            private String created_at;
            private String updated_at;            

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }

            public String getVehicleAnttName() {
                return vehicle_antt_name;
            }

            public void setVehicleAnttName(String vehicle_antt_name) {
                this.vehicle_antt_name = vehicle_antt_name;
            }

            public String getVehicleDeleted_at() {
                return deleted_at;
            }

            public void setVehicleDeleted_at(String deleted_at) {
                this.deleted_at = deleted_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }            
        }
    }
}
