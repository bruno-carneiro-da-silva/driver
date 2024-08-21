package com.tkx.driver.Mappers;

import com.tkx.driver.AppCountry;
import com.tkx.driver.AppCountryArea;
import com.tkx.driver.AppSegments;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataMapper {

    public static List<AppSegments> mapSegments(JSONArray segmentsArray) throws Exception {
        List<AppSegments> segments = new ArrayList<>();
        for (int i = 0; i < segmentsArray.length(); i++) {
            JSONObject segmentObject = segmentsArray.getJSONObject(i);
            AppSegments segment = new AppSegments();
            segment.id = segmentObject.getInt("id");
            segment.icon = segmentObject.getString("icon");
            segment.name = segmentObject.getString("name");
            segment.description = segmentObject.getString("description");
            segment.slag = segmentObject.getString("slag");
            segment.is_coming_soon = segmentObject.getInt("is_coming_soon");
            segment.created_at = segmentObject.getString("created_at");
            segment.updated_at = segmentObject.getString("updated_at");
            segments.add(segment);
        }
        return segments;
    }

    public static List<AppCountry> mapCountries(JSONArray countriesArray) throws Exception {
        List<AppCountry> countries = new ArrayList<>();
        for (int i = 0; i < countriesArray.length(); i++) {
            JSONObject countryObject = countriesArray.getJSONObject(i);
            AppCountry country = new AppCountry();
            country.id = countryObject.getInt("id");
            country.merchant_id = countryObject.getInt("merchant_id");
            country.sequance = countryObject.getInt("sequance");
            country.country_code = countryObject.getString("country_code");
            country.isoCode = countryObject.getString("isoCode");
            country.phonecode = countryObject.getString("phonecode");
            country.distance_unit = countryObject.getInt("distance_unit");
            country.default_language = countryObject.getString("default_language");
            country.maxNumPhone = countryObject.getInt("maxNumPhone");
            country.minNumPhone = countryObject.getInt("minNumPhone");
            country.transaction_code = countryObject.getString("transaction_code");
            country.additional_details = countryObject.getInt("additional_details");
            country.parameter_name = countryObject.getString("parameter_name");
            country.placeholder = countryObject.getString("placeholder");
            country.country_status = countryObject.getInt("country_status");
            country.created_at = countryObject.getString("created_at");
            country.updated_at = countryObject.getString("updated_at");
            country.short_code = countryObject.getString("short_code");
            country.name = countryObject.getString("name");

//            JSONArray countryAreaArray = countryObject.getJSONArray("country_area");
//            country.country_area = mapCountryAreas(countryAreaArray);

            countries.add(country);
        }
        return countries;
    }

    public static List<AppCountryArea> mapCountryAreas(JSONArray countryAreaArray) throws Exception {
        List<AppCountryArea> countryAreas = new ArrayList<>();
        for (int i = 0; i < countryAreaArray.length(); i++) {
            JSONObject countryAreaObject = countryAreaArray.getJSONObject(i);
            AppCountryArea countryArea = new AppCountryArea ();
            countryArea.id = countryAreaObject.getInt("id");
            countryArea.merchant_id = countryAreaObject.getInt("merchant_id");
            countryArea.country_id = countryAreaObject.getInt("country_id");
            countryArea.is_geofence = countryAreaObject.getInt("is_geofence");
            countryArea.auto_upgradetion = countryAreaObject.getInt("auto_upgradetion");
            countryArea.timezone = countryAreaObject.getString("timezone");
            countryArea.minimum_wallet_amount = countryAreaObject.getString("minimum_wallet_amount");
            countryArea.pool_postion = countryAreaObject.getInt("pool_postion");
            countryArea.status = countryAreaObject.getInt("status");
            countryArea.driver_earning_duration = countryAreaObject.getInt("driver_earning_duration");
            countryArea.manual_toll_price = countryAreaObject.getString("manual_toll_price");
            countryArea.created_at = countryAreaObject.getString("created_at");
            countryArea.updated_at = countryAreaObject.getString("updated_at");
            countryArea.AreaName = countryAreaObject.getString("AreaName");
            countryAreas.add(countryArea);
        }
        return countryAreas;
    }
}
