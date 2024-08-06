package com.tkx.driver;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "trip_details")
public class TripDetails {
    @PrimaryKey(autoGenerate = true)
    private int id;

    // HolderMapImage
    private String map_image;

    // HolderBookingDescription
    private Boolean booking_description_visibility;
    private String highlighted_left_text;

    // HolderPickDropLocation
    private Boolean holder_pick_drop_visibility;
    private String pick_text;
    private String pick_text_visibility;
    private String drop_text;
    private String drop_text_visibility;

    // holderMetering
    private Boolean metering_visibility;
    private String text_one;
    private String text_two;
    private String text_three;

    //holderFamilyMember
    private Boolean holder_family_visibility;
    private String name;
    private String age;
    private String phone_number;

    //holderUser
    private String circular_image;
    private Boolean user_visibility;
    private String highlighted_text;
    private String status_text;
    private String rating_visibility;
    private String rating;

    //ButtonVisibilityBean
    private Boolean visibility;
    private String button_text;
    private String text_color;
    private String text_back_ground;
    private String action;

    // CancelButtonVisibilityBean
    @SerializedName("visibility")
    private Boolean cancelButtonVisibility;

    @SerializedName("button_text")
    private String cancelButtonText;

    @SerializedName("text_color")
    private String cancelButtonTextColor;

    @SerializedName("text_back_ground")
    private String cancelButtonTextBackground;

    @SerializedName("action")
    private String cancelButtonAction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMap_image() {
        return map_image;
    }

    public void setMap_image(String map_image) {
        this.map_image = map_image;
    }

    public Boolean getBooking_description_visibility() {
        return booking_description_visibility;
    }

    public void setBooking_description_visibility(Boolean booking_description_visibility) {
        this.booking_description_visibility = booking_description_visibility;
    }

    public String getHighlighted_left_text() {
        return highlighted_left_text;
    }

    public void setHighlighted_left_text(String highlighted_left_text) {
        this.highlighted_left_text = highlighted_left_text;
    }

    public Boolean getHolder_pick_drop_visibility() {
        return holder_pick_drop_visibility;
    }

    public void setHolder_pick_drop_visibility(Boolean holder_pick_drop_visibility) {
        this.holder_pick_drop_visibility = holder_pick_drop_visibility;
    }

    public String getPick_text() {
        return pick_text;
    }

    public void setPick_text(String pick_text) {
        this.pick_text = pick_text;
    }

    public String getPick_text_visibility() {
        return pick_text_visibility;
    }

    public void setPick_text_visibility(String pick_text_visibility) {
        this.pick_text_visibility = pick_text_visibility;
    }

    public String getDrop_text() {
        return drop_text;
    }

    public void setDrop_text(String drop_text) {
        this.drop_text = drop_text;
    }

    public String getDrop_text_visibility() {
        return drop_text_visibility;
    }

    public void setDrop_text_visibility(String drop_text_visibility) {
        this.drop_text_visibility = drop_text_visibility;
    }

    public Boolean getMetering_visibility() {
        return metering_visibility;
    }

    public void setMetering_visibility(Boolean metering_visibility) {
        this.metering_visibility = metering_visibility;
    }

    public String getText_one() {
        return text_one;
    }

    public void setText_one(String text_one) {
        this.text_one = text_one;
    }

    public String getText_two() {
        return text_two;
    }

    public void setText_two(String text_two) {
        this.text_two = text_two;
    }

    public String getText_three() {
        return text_three;
    }

    public void setText_three(String text_three) {
        this.text_three = text_three;
    }

    public Boolean getHolder_family_visibility() {
        return holder_family_visibility;
    }

    public void setHolder_family_visibility(Boolean holder_family_visibility) {
        this.holder_family_visibility = holder_family_visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCircular_image() {
        return circular_image;
    }

    public void setCircular_image(String circular_image) {
        this.circular_image = circular_image;
    }

    public Boolean getUser_visibility() {
        return user_visibility;
    }

    public void setUser_visibility(Boolean user_visibility) {
        this.user_visibility = user_visibility;
    }

    public String getHighlighted_text() {
        return highlighted_text;
    }

    public void setHighlighted_text(String highlighted_text) {
        this.highlighted_text = highlighted_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getRating_visibility() {
        return rating_visibility;
    }

    public void setRating_visibility(String rating_visibility) {
        this.rating_visibility = rating_visibility;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setPick_text_visibility(boolean pickTextVisibility) {
    }

    public void setDrop_text_visibility(boolean dropTextVisibility) {
    }

    public String getText_back_ground() {
        return text_back_ground;
    }

    public void setText_back_ground(String text_back_ground) {
        this.text_back_ground = text_back_ground;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public String getButton_text() {
        return button_text;
    }

    public void setButton_text(String button_text) {
        this.button_text = button_text;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public Boolean getCancelButtonVisibility() {
        return cancelButtonVisibility;
    }

    public void setCancelButtonVisibility(Boolean cancelButtonVisibility) {
        this.cancelButtonVisibility = cancelButtonVisibility;
    }

    public String getCancelButtonText() {
        return cancelButtonText;
    }

    public void setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
    }

    public String getCancelButtonTextColor() {
        return cancelButtonTextColor;
    }

    public void setCancelButtonTextColor(String cancelButtonTextColor) {
        this.cancelButtonTextColor = cancelButtonTextColor;
    }

    public String getCancelButtonTextBackground() {
        return cancelButtonTextBackground;
    }

    public void setCancelButtonTextBackground(String cancelButtonTextBackground) {
        this.cancelButtonTextBackground = cancelButtonTextBackground;
    }

    public String getCancelButtonAction() {
        return cancelButtonAction;
    }

    public void setCancelButtonAction(String cancelButtonAction) {
        this.cancelButtonAction = cancelButtonAction;
    }
}

