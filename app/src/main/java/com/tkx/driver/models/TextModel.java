package com.tkx.driver.models;

import java.util.List;

public class TextModel {   

    private DataBean data;
    private String result;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        
        private NavigationDrawerBean navigation_drawer;
        private RegisterBean register;
        private GeneralConfigBean general_config;
        private CustomerSupportBean customer_support;
        private AppVersionDialogBean app_version_dialog;
        private AppMaintainanceBean app_maintainance;
        private RideConfigBean ride_config;
        private TrackingBean tracking;
        private ReceivingBean receiving;
        private LoginBean login;
        private ThemeCofigBean theme_cofig;
        private List<SegmentsBean> segments;
        private List<CountriesBean> countries;
        private List<DriverCommissionChoicesBean> driver_commission_choices;
        private List<LanguagesBean> languages;
        private List<?> paymentOption;

        public NavigationDrawerBean getNavigation_drawer() {
            return navigation_drawer;
        }

        public void setNavigation_drawer(NavigationDrawerBean navigation_drawer) {
            this.navigation_drawer = navigation_drawer;
        }

        public RegisterBean getRegister() {
            return register;
        }

        public void setRegister(RegisterBean register) {
            this.register = register;
        }

        public GeneralConfigBean getGeneral_config() {
            return general_config;
        }

        public void setGeneral_config(GeneralConfigBean general_config) {
            this.general_config = general_config;
        }

        public CustomerSupportBean getCustomer_support() {
            return customer_support;
        }

        public void setCustomer_support(CustomerSupportBean customer_support) {
            this.customer_support = customer_support;
        }

        public AppVersionDialogBean getApp_version_dialog() {
            return app_version_dialog;
        }

        public void setApp_version_dialog(AppVersionDialogBean app_version_dialog) {
            this.app_version_dialog = app_version_dialog;
        }

        public AppMaintainanceBean getApp_maintainance() {
            return app_maintainance;
        }

        public void setApp_maintainance(AppMaintainanceBean app_maintainance) {
            this.app_maintainance = app_maintainance;
        }

        public RideConfigBean getRide_config() {
            return ride_config;
        }

        public void setRide_config(RideConfigBean ride_config) {
            this.ride_config = ride_config;
        }

        public TrackingBean getTracking() {
            return tracking;
        }

        public void setTracking(TrackingBean tracking) {
            this.tracking = tracking;
        }

        public ReceivingBean getReceiving() {
            return receiving;
        }

        public void setReceiving(ReceivingBean receiving) {
            this.receiving = receiving;
        }

        public LoginBean getLogin() {
            return login;
        }

        public void setLogin(LoginBean login) {
            this.login = login;
        }

        public ThemeCofigBean getTheme_cofig() {
            return theme_cofig;
        }

        public void setTheme_cofig(ThemeCofigBean theme_cofig) {
            this.theme_cofig = theme_cofig;
        }

        public List<SegmentsBean> getSegments() {
            return segments;
        }

        public void setSegments(List<SegmentsBean> segments) {
            this.segments = segments;
        }

        public List<CountriesBean> getCountries() {
            return countries;
        }

        public void setCountries(List<CountriesBean> countries) {
            this.countries = countries;
        }

        public List<DriverCommissionChoicesBean> getDriver_commission_choices() {
            return driver_commission_choices;
        }

        public void setDriver_commission_choices(List<DriverCommissionChoicesBean> driver_commission_choices) {
            this.driver_commission_choices = driver_commission_choices;
        }

        public List<LanguagesBean> getLanguages() {
            return languages;
        }

        public void setLanguages(List<LanguagesBean> languages) {
            this.languages = languages;
        }

        public List<?> getPaymentOption() {
            return paymentOption;
        }

        public void setPaymentOption(List<?> paymentOption) {
            this.paymentOption = paymentOption;
        }

        public static class NavigationDrawerBean {
            /**
             * language : true
             * customer_support : true
             * report_issue : true
             * cms_page : true
             * wallet : true
             */

            private boolean language;
            private boolean customer_support;
            private boolean report_issue;
            private boolean cms_page;
            private boolean wallet;

            public boolean isLanguage() {
                return language;
            }

            public void setLanguage(boolean language) {
                this.language = language;
            }

            public boolean isCustomer_support() {
                return customer_support;
            }

            public void setCustomer_support(boolean customer_support) {
                this.customer_support = customer_support;
            }

            public boolean isReport_issue() {
                return report_issue;
            }

            public void setReport_issue(boolean report_issue) {
                this.report_issue = report_issue;
            }

            public boolean isCms_page() {
                return cms_page;
            }

            public void setCms_page(boolean cms_page) {
                this.cms_page = cms_page;
            }

            public boolean isWallet() {
                return wallet;
            }

            public void setWallet(boolean wallet) {
                this.wallet = wallet;
            }
        }

        public static class RegisterBean {
            /**
             * smoker : false
             * driver_commission_choice : false
             * email : true
             * driver_email_otp : false
             * phone : true
             * driver_phone_otp : false
             * gender : false
             */

            private boolean smoker;
            private boolean driver_commission_choice;
            private boolean email;
            private boolean driver_email_otp;
            private boolean phone;
            private boolean driver_phone_otp;
            private boolean gender;

            public boolean isSmoker() {
                return smoker;
            }

            public void setSmoker(boolean smoker) {
                this.smoker = smoker;
            }

            public boolean isDriver_commission_choice() {
                return driver_commission_choice;
            }

            public void setDriver_commission_choice(boolean driver_commission_choice) {
                this.driver_commission_choice = driver_commission_choice;
            }

            public boolean isEmail() {
                return email;
            }

            public void setEmail(boolean email) {
                this.email = email;
            }

            public boolean isDriver_email_otp() {
                return driver_email_otp;
            }

            public void setDriver_email_otp(boolean driver_email_otp) {
                this.driver_email_otp = driver_email_otp;
            }

            public boolean isPhone() {
                return phone;
            }

            public void setPhone(boolean phone) {
                this.phone = phone;
            }

            public boolean isDriver_phone_otp() {
                return driver_phone_otp;
            }

            public void setDriver_phone_otp(boolean driver_phone_otp) {
                this.driver_phone_otp = driver_phone_otp;
            }

            public boolean isGender() {
                return gender;
            }

            public void setGender(boolean gender) {
                this.gender = gender;
            }
        }

        public static class GeneralConfigBean {
            /**
             * manual_dispatch : true
             * demo : false
             * add_multiple_vehicle : true
             * auto_accept_mode : false
             * subscription_package : false
             * driver_limit : false
             * default_language : ar
             * driver_wallet_package : [{"amount":"10"},{"amount":"20"},{"amount":"30"}]
             * chat : true
             * splash_screen : TestApp
             * emergency_contact : true
             * vehicle_owner : false
             * vehicle_ac_enable : false
             * enable_super_driver : false
             * bank_details_enable : true
             * home_address_enable : false
             * show_logo_main : true
             * existing_vehicle_enable : true
             * baby_seat_enable : true
             * wheel_chair_enable : false
             * online_transaction_code : {"name":"IFSC Code","placeholder":"Please enter IFSC Code Code"}
             * driver_rating_enable : false
             */

            private boolean manual_dispatch;
            private boolean demo;
            private boolean add_multiple_vehicle;
            private boolean auto_accept_mode;
            private boolean subscription_package;
            private boolean driver_limit;
            private String default_language;
            private boolean chat;
            private String splash_screen;
            private boolean emergency_contact;
            private boolean vehicle_owner;
            private boolean vehicle_ac_enable;
            private boolean enable_super_driver;
            private boolean bank_details_enable;
            private boolean home_address_enable;
            private boolean show_logo_main;
            private boolean existing_vehicle_enable;
            private boolean baby_seat_enable;
            private boolean wheel_chair_enable;
            private OnlineTransactionCodeBean online_transaction_code;
            private boolean driver_rating_enable;
            private List<DriverWalletPackageBean> driver_wallet_package;

            public boolean isManual_dispatch() {
                return manual_dispatch;
            }

            public void setManual_dispatch(boolean manual_dispatch) {
                this.manual_dispatch = manual_dispatch;
            }

            public boolean isDemo() {
                return demo;
            }

            public void setDemo(boolean demo) {
                this.demo = demo;
            }

            public boolean isAdd_multiple_vehicle() {
                return add_multiple_vehicle;
            }

            public void setAdd_multiple_vehicle(boolean add_multiple_vehicle) {
                this.add_multiple_vehicle = add_multiple_vehicle;
            }

            public boolean isAuto_accept_mode() {
                return auto_accept_mode;
            }

            public void setAuto_accept_mode(boolean auto_accept_mode) {
                this.auto_accept_mode = auto_accept_mode;
            }

            public boolean isSubscription_package() {
                return subscription_package;
            }

            public void setSubscription_package(boolean subscription_package) {
                this.subscription_package = subscription_package;
            }

            public boolean isDriver_limit() {
                return driver_limit;
            }

            public void setDriver_limit(boolean driver_limit) {
                this.driver_limit = driver_limit;
            }

            public String getDefault_language() {
                return default_language;
            }

            public void setDefault_language(String default_language) {
                this.default_language = default_language;
            }

            public boolean isChat() {
                return chat;
            }

            public void setChat(boolean chat) {
                this.chat = chat;
            }

            public String getSplash_screen() {
                return splash_screen;
            }

            public void setSplash_screen(String splash_screen) {
                this.splash_screen = splash_screen;
            }

            public boolean isEmergency_contact() {
                return emergency_contact;
            }

            public void setEmergency_contact(boolean emergency_contact) {
                this.emergency_contact = emergency_contact;
            }

            public boolean isVehicle_owner() {
                return vehicle_owner;
            }

            public void setVehicle_owner(boolean vehicle_owner) {
                this.vehicle_owner = vehicle_owner;
            }

            public boolean isVehicle_ac_enable() {
                return vehicle_ac_enable;
            }

            public void setVehicle_ac_enable(boolean vehicle_ac_enable) {
                this.vehicle_ac_enable = vehicle_ac_enable;
            }

            public boolean isEnable_super_driver() {
                return enable_super_driver;
            }

            public void setEnable_super_driver(boolean enable_super_driver) {
                this.enable_super_driver = enable_super_driver;
            }

            public boolean isBank_details_enable() {
                return bank_details_enable;
            }

            public void setBank_details_enable(boolean bank_details_enable) {
                this.bank_details_enable = bank_details_enable;
            }

            public boolean isHome_address_enable() {
                return home_address_enable;
            }

            public void setHome_address_enable(boolean home_address_enable) {
                this.home_address_enable = home_address_enable;
            }

            public boolean isShow_logo_main() {
                return show_logo_main;
            }

            public void setShow_logo_main(boolean show_logo_main) {
                this.show_logo_main = show_logo_main;
            }

            public boolean isExisting_vehicle_enable() {
                return existing_vehicle_enable;
            }

            public void setExisting_vehicle_enable(boolean existing_vehicle_enable) {
                this.existing_vehicle_enable = existing_vehicle_enable;
            }

            public boolean isBaby_seat_enable() {
                return baby_seat_enable;
            }

            public void setBaby_seat_enable(boolean baby_seat_enable) {
                this.baby_seat_enable = baby_seat_enable;
            }

            public boolean isWheel_chair_enable() {
                return wheel_chair_enable;
            }

            public void setWheel_chair_enable(boolean wheel_chair_enable) {
                this.wheel_chair_enable = wheel_chair_enable;
            }

            public OnlineTransactionCodeBean getOnline_transaction_code() {
                return online_transaction_code;
            }

            public void setOnline_transaction_code(OnlineTransactionCodeBean online_transaction_code) {
                this.online_transaction_code = online_transaction_code;
            }

            public boolean isDriver_rating_enable() {
                return driver_rating_enable;
            }

            public void setDriver_rating_enable(boolean driver_rating_enable) {
                this.driver_rating_enable = driver_rating_enable;
            }

            public List<DriverWalletPackageBean> getDriver_wallet_package() {
                return driver_wallet_package;
            }

            public void setDriver_wallet_package(List<DriverWalletPackageBean> driver_wallet_package) {
                this.driver_wallet_package = driver_wallet_package;
            }

            public static class OnlineTransactionCodeBean {
                /**
                 * name : IFSC Code
                 * placeholder : Please enter IFSC Code Code
                 */

                private String name;
                private String placeholder;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPlaceholder() {
                    return placeholder;
                }

                public void setPlaceholder(String placeholder) {
                    this.placeholder = placeholder;
                }
            }

            public static class DriverWalletPackageBean {
                /**
                 * amount : 10
                 */

                private String amount;

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }
            }
        }

        public static class CustomerSupportBean {
            /**
             * mail : nooralmonacib@gmail.com
             * phone : +33676541815
             */

            private String mail;
            private String phone;

            public String getMail() {
                return mail;
            }

            public void setMail(String mail) {
                this.mail = mail;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }

        public static class AppVersionDialogBean {
            /**
             * show_dialog : false
             * mandatory : false
             * dialog_message : Application is Update to date
             */

            private boolean show_dialog;
            private boolean mandatory;
            private String dialog_message;

            public boolean isShow_dialog() {
                return show_dialog;
            }

            public void setShow_dialog(boolean show_dialog) {
                this.show_dialog = show_dialog;
            }

            public boolean isMandatory() {
                return mandatory;
            }

            public void setMandatory(boolean mandatory) {
                this.mandatory = mandatory;
            }

            public String getDialog_message() {
                return dialog_message;
            }

            public void setDialog_message(String dialog_message) {
                this.dialog_message = dialog_message;
            }
        }

        public static class AppMaintainanceBean {
            /**
             * show_dialog : false
             * show_message :
             */

            private boolean show_dialog;
            private String show_message;

            public boolean isShow_dialog() {
                return show_dialog;
            }

            public void setShow_dialog(boolean show_dialog) {
                this.show_dialog = show_dialog;
            }

            public String getShow_message() {
                return show_message;
            }

            public void setShow_message(String show_message) {
                this.show_message = show_message;
            }
        }

        public static class RideConfigBean {
            /**
             * outstation : true
             * location_update_timeband : 5
             * tracking_screen_refresh_timeband : null
             * slide_button : true
             * drop_outside_area : false
             * outstation_notification_popup : false
             */

            private boolean outstation;
            private String location_update_timeband;
            private Object tracking_screen_refresh_timeband;
            private boolean slide_button;
            private boolean drop_outside_area;
            private boolean outstation_notification_popup;

            public boolean isOutstation() {
                return outstation;
            }

            public void setOutstation(boolean outstation) {
                this.outstation = outstation;
            }

            public String getLocation_update_timeband() {
                return location_update_timeband;
            }

            public void setLocation_update_timeband(String location_update_timeband) {
                this.location_update_timeband = location_update_timeband;
            }

            public Object getTracking_screen_refresh_timeband() {
                return tracking_screen_refresh_timeband;
            }

            public void setTracking_screen_refresh_timeband(Object tracking_screen_refresh_timeband) {
                this.tracking_screen_refresh_timeband = tracking_screen_refresh_timeband;
            }

            public boolean isSlide_button() {
                return slide_button;
            }

            public void setSlide_button(boolean slide_button) {
                this.slide_button = slide_button;
            }

            public boolean isDrop_outside_area() {
                return drop_outside_area;
            }

            public void setDrop_outside_area(boolean drop_outside_area) {
                this.drop_outside_area = drop_outside_area;
            }

            public boolean isOutstation_notification_popup() {
                return outstation_notification_popup;
            }

            public void setOutstation_notification_popup(boolean outstation_notification_popup) {
                this.outstation_notification_popup = outstation_notification_popup;
            }
        }

        public static class TrackingBean {
            /**
             * scroll : true
             */

            private boolean scroll;

            public boolean isScroll() {
                return scroll;
            }

            public void setScroll(boolean scroll) {
                this.scroll = scroll;
            }
        }

        public static class ReceivingBean {
            /**
             * drop_point : true
             * estimate_fare : true
             */

            private boolean drop_point;
            private boolean estimate_fare;

            public boolean isDrop_point() {
                return drop_point;
            }

            public void setDrop_point(boolean drop_point) {
                this.drop_point = drop_point;
            }

            public boolean isEstimate_fare() {
                return estimate_fare;
            }

            public void setEstimate_fare(boolean estimate_fare) {
                this.estimate_fare = estimate_fare;
            }
        }

        public static class LoginBean {
            /**
             * email : false
             * phone : true
             */

            private boolean email;
            private boolean phone;

            public boolean isEmail() {
                return email;
            }

            public void setEmail(boolean email) {
                this.email = email;
            }

            public boolean isPhone() {
                return phone;
            }

            public void setPhone(boolean phone) {
                this.phone = phone;
            }
        }

        public static class ThemeCofigBean {
            /**
             * primary_color_driver : #d3304d
             * chat_button_color_driver : #90a446
             * share_button_color_driver : #53792a
             * call_button_color_driver : #26e884
             * cancel_button_color_driver : #b4080b
             */

            private String primary_color_driver;
            private String chat_button_color_driver;
            private String share_button_color_driver;
            private String call_button_color_driver;
            private String cancel_button_color_driver;

            public String getPrimary_color_driver() {
                return primary_color_driver;
            }

            public void setPrimary_color_driver(String primary_color_driver) {
                this.primary_color_driver = primary_color_driver;
            }

            public String getChat_button_color_driver() {
                return chat_button_color_driver;
            }

            public void setChat_button_color_driver(String chat_button_color_driver) {
                this.chat_button_color_driver = chat_button_color_driver;
            }

            public String getShare_button_color_driver() {
                return share_button_color_driver;
            }

            public void setShare_button_color_driver(String share_button_color_driver) {
                this.share_button_color_driver = share_button_color_driver;
            }

            public String getCall_button_color_driver() {
                return call_button_color_driver;
            }

            public void setCall_button_color_driver(String call_button_color_driver) {
                this.call_button_color_driver = call_button_color_driver;
            }

            public String getCancel_button_color_driver() {
                return cancel_button_color_driver;
            }

            public void setCancel_button_color_driver(String cancel_button_color_driver) {
                this.cancel_button_color_driver = cancel_button_color_driver;
            }
        }

        public static class SegmentsBean {
            /**
             * id : 1
             * icon : segment/holder_taxi.png
             * name : Taxi
             * description : Taxi
             * slag : TAXI
             * created_at : 2019-04-02 00:00:00
             * updated_at : 2019-04-02 00:00:00
             */

            private int id;
            private String icon;
            private String name;
            private String description;
            private String slag;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getSlag() {
                return slag;
            }

            public void setSlag(String slag) {
                this.slag = slag;
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

        public static class CountriesBean {
            
            private int id;
            private int merchant_id;
            private String isoCode;
            private String phonecode;
            private int distance_unit;
            private String default_language;
            private int maxNumPhone;
            private int minNumPhone;
            private int country_status;
            private String created_at;
            private String updated_at;
            private String name;
            private List<CountryAreaBean> country_area;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(int merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getIsoCode() {
                return isoCode;
            }

            public void setIsoCode(String isoCode) {
                this.isoCode = isoCode;
            }

            public String getPhonecode() {
                return phonecode;
            }

            public void setPhonecode(String phonecode) {
                this.phonecode = phonecode;
            }

            public int getDistance_unit() {
                return distance_unit;
            }

            public void setDistance_unit(int distance_unit) {
                this.distance_unit = distance_unit;
            }

            public String getDefault_language() {
                return default_language;
            }

            public void setDefault_language(String default_language) {
                this.default_language = default_language;
            }

            public int getMaxNumPhone() {
                return maxNumPhone;
            }

            public void setMaxNumPhone(int maxNumPhone) {
                this.maxNumPhone = maxNumPhone;
            }

            public int getMinNumPhone() {
                return minNumPhone;
            }

            public void setMinNumPhone(int minNumPhone) {
                this.minNumPhone = minNumPhone;
            }

            public int getCountry_status() {
                return country_status;
            }

            public void setCountry_status(int country_status) {
                this.country_status = country_status;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<CountryAreaBean> getCountry_area() {
                return country_area;
            }

            public void setCountry_area(List<CountryAreaBean> country_area) {
                this.country_area = country_area;
            }

            public static class CountryAreaBean {
                /**
                 * id : 215
                 * merchant_id : 90
                 * country_id : 155
                 * AreaCoordinates : [{"latitude":"28.631060382133025","longitude":"75.1112154709574"},{"latitude":"29.049688129231246","longitude":"75.58088099830115"},{"latitude":"28.929565374262552","longitude":"76.2153414475199"},{"latitude":"28.877869723389004","longitude":"76.56827723853553"},{"latitude":"28.82133573559533","longitude":"76.94318568580115"},{"latitude":"28.992046621581043","longitude":"77.5611666428324"},{"latitude":"28.495972249852883","longitude":"77.9127291428324"},{"latitude":"27.968449591856025","longitude":"77.8468111740824"},{"latitude":"27.72073508748725","longitude":"77.0503023850199"},{"latitude":"28.041198966625647","longitude":"76.5229586350199"},{"latitude":"28.486316466586302","longitude":"74.9683932053324"}]
                 * auto_upgradetion : 2
                 * timezone : Asia/Kolkata
                 * minimum_wallet_amount : 10
                 * pool_postion : 1
                 * status : 1
                 * created_at : 2019-04-09 14:35:16
                 * updated_at : 2019-04-24 11:09:52
                 * AreaName : Gurugram
                 */

                private int id;
                private int merchant_id;
                private String country_id;
                private String AreaCoordinates;
                private int auto_upgradetion;
                private String timezone;
                private String minimum_wallet_amount;
                private int pool_postion;
                private int status;
                private String created_at;
                private String updated_at;
                private String AreaName;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMerchant_id() {
                    return merchant_id;
                }

                public void setMerchant_id(int merchant_id) {
                    this.merchant_id = merchant_id;
                }

                public String getCountry_id() {
                    return country_id;
                }

                public void setCountry_id(String country_id) {
                    this.country_id = country_id;
                }

                public String getAreaCoordinates() {
                    return AreaCoordinates;
                }

                public void setAreaCoordinates(String AreaCoordinates) {
                    this.AreaCoordinates = AreaCoordinates;
                }

                public int getAuto_upgradetion() {
                    return auto_upgradetion;
                }

                public void setAuto_upgradetion(int auto_upgradetion) {
                    this.auto_upgradetion = auto_upgradetion;
                }

                public String getTimezone() {
                    return timezone;
                }

                public void setTimezone(String timezone) {
                    this.timezone = timezone;
                }

                public String getMinimum_wallet_amount() {
                    return minimum_wallet_amount;
                }

                public void setMinimum_wallet_amount(String minimum_wallet_amount) {
                    this.minimum_wallet_amount = minimum_wallet_amount;
                }

                public int getPool_postion() {
                    return pool_postion;
                }

                public void setPool_postion(int pool_postion) {
                    this.pool_postion = pool_postion;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
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

                public String getAreaName() {
                    return AreaName;
                }

                public void setAreaName(String AreaName) {
                    this.AreaName = AreaName;
                }
            }
        }

        public static class DriverCommissionChoicesBean {
            /**
             * id : 1
             * lang_data : Subscription Based
             */

            private int id;
            private String lang_data;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLang_data() {
                return lang_data;
            }

            public void setLang_data(String lang_data) {
                this.lang_data = lang_data;
            }
        }

        public static class LanguagesBean {
            /**
             * id : 1
             * name : English
             * locale : en
             * created_at : 2018-05-27 21:30:00
             * updated_at : 2018-05-28 00:30:00
             */

            private int id;
            private String name;
            private String locale;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
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
