package com.tkx.driver;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "countries")
public class AppCountry {
    @PrimaryKey
    public int id;
    public int merchant_id;
    public int sequance;
    public String country_code;
    public String isoCode;
    public String phonecode;
    public int distance_unit;
    public String default_language;
    public int maxNumPhone;
    public int minNumPhone;
    public String transaction_code;
    public int additional_details;
    public String parameter_name;
    public String placeholder;
    public int country_status;
    public String created_at;
    public String updated_at;
    public String short_code;
    public String name;

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

    public int getSequance() {
        return sequance;
    }

    public void setSequance(int sequance) {
        this.sequance = sequance;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
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

    public String getTransaction_code() {
        return transaction_code;
    }

    public void setTransaction_code(String transaction_code) {
        this.transaction_code = transaction_code;
    }

    public int getAdditional_details() {
        return additional_details;
    }

    public void setAdditional_details(int additional_details) {
        this.additional_details = additional_details;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
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

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
