package com.tkx.driver.Mappers;
import com.tkx.driver.TripDetails;
import com.tkx.driver.models.ModelSpecificTripDetails;


public class TripDetailsMapper {
    public static TripDetails mapToTripDetails(
            ModelSpecificTripDetails.DataBeanXXXXXX.HolderMapImageBean mapImageBean,
            ModelSpecificTripDetails.DataBeanXXXXXX.HolderBookingDescriptionBean bookingDescriptionBean,
            ModelSpecificTripDetails.DataBeanXXXXXX.HolderPickdropLocationBean pickDropLocationBean,
            ModelSpecificTripDetails.DataBeanXXXXXX.HolderMeteringBean meteringBean,
            ModelSpecificTripDetails.DataBeanXXXXXX.HolderFamilyMemberBean familyMemberBean,
            ModelSpecificTripDetails.DataBeanXXXXXX.HolderUserBean userBean,
            ModelSpecificTripDetails.DataBeanXXXXXX.ButtonVisibilityBean buttonBean,
            ModelSpecificTripDetails.DataBeanXXXXXX.CancelButtonVisibilityBean cancelButtonBean
    ) {
        TripDetails entity = new TripDetails();

        // HolderMapImage
        entity.setMap_image(mapImageBean.getData().getMap_image());

        // HolderBookingDescription
        entity.setHighlighted_left_text(bookingDescriptionBean.getData().getHighlighted_left_text());

        // HolderPickDropLocation
        entity.setHolder_pick_drop_visibility(pickDropLocationBean.getData().isDrop_text_visibility());
        entity.setPick_text(pickDropLocationBean.getData().getPick_text());
        entity.setPick_text_visibility(pickDropLocationBean.getData().isPick_text_visibility());
        entity.setDrop_text(pickDropLocationBean.getData().getDrop_text());
        entity.setDrop_text_visibility(pickDropLocationBean.getData().isDrop_text_visibility());

        // HolderMetering
        entity.setText_one(meteringBean.getData().getText_one());
        entity.setText_two(meteringBean.getData().getText_two());
        entity.setText_three(meteringBean.getData().getText_three());

        // HolderFamilyMember
        entity.setName(familyMemberBean.getName());
        entity.setAge(familyMemberBean.getAge());

        // HolderUser
        entity.setCircular_image(userBean.getData().getCircular_image());
        entity.setHighlighted_text(userBean.getData().getHighlighted_text());
        entity.setStatus_text(userBean.getData().getStatus_text());

        //ButtonVisibilityBean
        entity.setButton_text(buttonBean.getButton_text());
        entity.setText_color(buttonBean.getText_color());
        entity.setText_back_ground(buttonBean.getText_back_ground());
        entity.setAction(buttonBean.getAction());

        //CancelButton
        entity.setButton_text(cancelButtonBean.getButton_text());
        entity.setText_color(cancelButtonBean.getText_color());
        entity.setText_back_ground(cancelButtonBean.getText_back_ground());
        entity.setAction(cancelButtonBean.getAction());
        return entity;
    }
}