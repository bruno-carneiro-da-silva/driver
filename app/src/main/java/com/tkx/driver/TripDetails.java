package com.tkx.driver;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip_details")
public class TripDetails {
    @PrimaryKey(autoGenerate = true)
    public int id;

    // HolderMapImage
    public String map_image;

    // HolderBookingDescription
    public Boolean booking_description_visibility;
    public String highlighted_left_text;

    // HolderPickDropLocation
    public Boolean holder_pick_drop_visibility;
    public String pick_text;
    public String pick_text_visibility;
    public String drop_text;
    public String drop_text_visibility;

    // holderMetering
    public Boolean metering_visibility;
    public String text_one;
    public String text_two;
    public String text_three;

    //holderFamilyMember
    public Boolean holder_family_visibility;
    public String name;
    public String age;
    public String phone_number;

    //holderUser
    public Boolean user_visibility;
    public String highlighted_text;
    public String status_text;
    public String rating_visibility;
    public String rating;

    public TripDetails(int id, String map_image, Boolean booking_description_visibility, String highlighted_left_text, Boolean holder_pick_drop_visibility, String pick_text, String pick_text_visibility, String drop_text, String drop_text_visibility, Boolean metering_visibility, String text_one, String text_two, String text_three, Boolean holder_family_visibility, String name, String age, String phone_number, Boolean user_visibility, String highlighted_text, String status_text, String rating_visibility, String rating) {
        this.id = id;
        this.map_image = map_image;
        this.booking_description_visibility = booking_description_visibility;
        this.highlighted_left_text = highlighted_left_text;
        this.holder_pick_drop_visibility = holder_pick_drop_visibility;
        this.pick_text = pick_text;
        this.pick_text_visibility = pick_text_visibility;
        this.drop_text = drop_text;
        this.drop_text_visibility = drop_text_visibility;
        this.metering_visibility = metering_visibility;
        this.text_one = text_one;
        this.text_two = text_two;
        this.text_three = text_three;
        this.holder_family_visibility = holder_family_visibility;
        this.name = name;
        this.age = age;
        this.phone_number = phone_number;
        this.user_visibility = user_visibility;
        this.highlighted_text = highlighted_text;
        this.status_text = status_text;
        this.rating_visibility = rating_visibility;
        this.rating = rating;
    }

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
}

