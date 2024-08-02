package com.tkx.driver.Mappers;
import com.tkx.driver.DatabeanTripDetailsSchedule;
import com.tkx.driver.models.ModelFragmenRides;

public class ActiveRidesMapper {
    public static DatabeanTripDetailsSchedule mapToTripDetails(ModelFragmenRides.DataBean tripDetails){
        DatabeanTripDetailsSchedule databeanTripDetailsSchedule = new DatabeanTripDetailsSchedule();
        databeanTripDetailsSchedule.setBooking_id(tripDetails.getBooking_id());
        databeanTripDetailsSchedule.setHighlighted_left_text(tripDetails.getHighlighted_left_text());
        databeanTripDetailsSchedule.setHighlighted_right_text(tripDetails.getHighlighted_right_text());
        databeanTripDetailsSchedule.setPick_location(tripDetails.getPick_location());
        databeanTripDetailsSchedule.setPick_location_visibility(tripDetails.isPick_location_visibility());
        databeanTripDetailsSchedule.setDrop_location(tripDetails.getDrop_location());
        databeanTripDetailsSchedule.setDrop_location_visibility(tripDetails.isDrop_location_visibility());
        databeanTripDetailsSchedule.setUser_description_layout_visibility(tripDetails.isUser_description_layout_visibility());
        databeanTripDetailsSchedule.setCircular_image(tripDetails.getCircular_image().toString());
        databeanTripDetailsSchedule.setUser_name_text(tripDetails.getUser_name_text());
        databeanTripDetailsSchedule.setUser_descriptive_text(tripDetails.getUser_descriptive_text());
        databeanTripDetailsSchedule.setStatus_text(tripDetails.getStatus_text());

        return databeanTripDetailsSchedule;
    }
}
