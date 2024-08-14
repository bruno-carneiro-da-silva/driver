package com.tkx.driver.Mappers;

import com.tkx.driver.AppData;
import com.tkx.driver.ModelManualRideStart;

public class TripDataMapper {
    public static AppData mapToTripData(ModelManualRideStart.DataBean tripData){
        AppData data = new AppData();
        data.setId(data.getId());
        data.setMerchant_id(data.getMerchant_id());
        data.setMerchant_booking_id(data.getMerchant_booking_id());
        data.setVehicle_type_id(data.getVehicle_type_id());
        data.setCountry_area_id(data.getCountry_area_id());
        data.setBooking_status(data.getBooking_status());
        data.setDrop_latitude(data.getDrop_latitude());
        data.setPickup_latitude(data.getPickup_latitude());
        data.setPickup_location(data.getPickup_location());
        data.setDrop_longitude(data.getDrop_longitude());
        data.setDrop_location(data.getDrop_location());
        data.setMap_image(data.getMap_image());
        data.setEstimate_distance(data.getEstimate_distance());
        data.setEstimate_time(data.getEstimate_time());
        data.setBill_details(data.getBill_details());
        data.setCreated_at(data.getCreated_at());
        data.setUpdated_at(data.getUpdated_at());

        return data;
    }
}
