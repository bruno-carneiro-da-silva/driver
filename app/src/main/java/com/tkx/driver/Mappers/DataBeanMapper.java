package com.tkx.driver.Mappers;

import com.tkx.driver.DataBeanRoom;
import com.tkx.driver.models.ModelDriverVehicles;

public class DataBeanMapper {
    public static DataBeanRoom mapToDataBeanRoom(ModelDriverVehicles.DataBean vehicle) {
        DataBeanRoom dataBeanRoom = new DataBeanRoom();
        dataBeanRoom.setId(vehicle.getId());
        dataBeanRoom.setMerchant_id(vehicle.getMerchant_id());
        dataBeanRoom.setDriver_id(vehicle.getDriver_id());
        dataBeanRoom.setMerchant_id(vehicle.getMerchant_id());
        dataBeanRoom.setOwner_id(vehicle.getOwner_id());
        dataBeanRoom.setVehicle_make_id(vehicle.getVehicle_make_id());
        dataBeanRoom.setVehicle_model_id(vehicle.getVehicle_model_id());
        dataBeanRoom.setVehicle_number_plate_image(vehicle.getVehicle_number_plate_image());
        dataBeanRoom.setShow_msg(vehicle.getShow_msg());
        dataBeanRoom.setService_types(vehicle.getService_types());
        dataBeanRoom.setVehicleTypeMapImage(vehicle.getVehicleTypeMapImage());
        dataBeanRoom.setService_types(vehicle.getService_types());
        dataBeanRoom.setVehicle_active_status(vehicle.getVehicle_active_status());
        dataBeanRoom.setVehicle_image(vehicle.getVehicle_image());
        dataBeanRoom.setCreated_at(vehicle.getCreated_at());
        dataBeanRoom.setUpdated_at(vehicle.getUpdated_at());
        return dataBeanRoom;
    }
}