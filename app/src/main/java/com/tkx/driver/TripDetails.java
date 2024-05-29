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

}

