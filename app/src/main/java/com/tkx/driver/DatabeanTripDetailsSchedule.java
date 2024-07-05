package com.tkx.driver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_schedule_trip")
public class DatabeanTripDetailsSchedule {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String highlighted_left_text;
    private String highlighted_left_text_style;
    private String highlighted_left_text_color;
    private String small_left_text;
    private String small_left_text_style;
    private String small_left_text_color;
    private String highlighted_right_text;
    private String highlighted_right_text_style;
    private String highlighted_right_text_color;
    private String small_right_text;
    private String small_right_text_style;
    private String small_right_text_color;
    private String pick_location;
    private boolean pick_location_visibility;
    private String drop_location;
    private boolean drop_location_visibility;
    private boolean user_description_layout_visibility;
    private String circular_image;
    private String user_name_text;
    private String user_descriptive_text;
    private String status_text;
    private String status_text_syle;
    private String status_text_color;
    private String booking_id;
    private String estimate_bill;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHighlighted_left_text() {
        return highlighted_left_text;
    }

    public void setHighlighted_left_text(String highlighted_left_text) {
        this.highlighted_left_text = highlighted_left_text;
    }

    public String getHighlighted_left_text_style() {
        return highlighted_left_text_style;
    }

    public void setHighlighted_left_text_style(String highlighted_left_text_style) {
        this.highlighted_left_text_style = highlighted_left_text_style;
    }

    public String getHighlighted_left_text_color() {
        return highlighted_left_text_color;
    }

    public void setHighlighted_left_text_color(String highlighted_left_text_color) {
        this.highlighted_left_text_color = highlighted_left_text_color;
    }

    public String getSmall_left_text() {
        return small_left_text;
    }

    public void setSmall_left_text(String small_left_text) {
        this.small_left_text = small_left_text;
    }

    public String getSmall_left_text_style() {
        return small_left_text_style;
    }

    public void setSmall_left_text_style(String small_left_text_style) {
        this.small_left_text_style = small_left_text_style;
    }

    public String getSmall_left_text_color() {
        return small_left_text_color;
    }

    public void setSmall_left_text_color(String small_left_text_color) {
        this.small_left_text_color = small_left_text_color;
    }

    public String getHighlighted_right_text() {
        return highlighted_right_text;
    }

    public void setHighlighted_right_text(String highlighted_right_text) {
        this.highlighted_right_text = highlighted_right_text;
    }

    public String getHighlighted_right_text_style() {
        return highlighted_right_text_style;
    }

    public void setHighlighted_right_text_style(String highlighted_right_text_style) {
        this.highlighted_right_text_style = highlighted_right_text_style;
    }

    public String getHighlighted_right_text_color() {
        return highlighted_right_text_color;
    }

    public void setHighlighted_right_text_color(String highlighted_right_text_color) {
        this.highlighted_right_text_color = highlighted_right_text_color;
    }

    public String getSmall_right_text() {
        return small_right_text;
    }

    public void setSmall_right_text(String small_right_text) {
        this.small_right_text = small_right_text;
    }

    public String getSmall_right_text_style() {
        return small_right_text_style;
    }

    public void setSmall_right_text_style(String small_right_text_style) {
        this.small_right_text_style = small_right_text_style;
    }

    public String getSmall_right_text_color() {
        return small_right_text_color;
    }

    public void setSmall_right_text_color(String small_right_text_color) {
        this.small_right_text_color = small_right_text_color;
    }

    public String getPick_location() {
        return pick_location;
    }

    public void setPick_location(String pick_location) {
        this.pick_location = pick_location;
    }

    public boolean isPick_location_visibility() {
        return pick_location_visibility;
    }

    public void setPick_location_visibility(boolean pick_location_visibility) {
        this.pick_location_visibility = pick_location_visibility;
    }

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public boolean isDrop_location_visibility() {
        return drop_location_visibility;
    }

    public void setDrop_location_visibility(boolean drop_location_visibility) {
        this.drop_location_visibility = drop_location_visibility;
    }

    public boolean isUser_description_layout_visibility() {
        return user_description_layout_visibility;
    }

    public void setUser_description_layout_visibility(boolean user_description_layout_visibility) {
        this.user_description_layout_visibility = user_description_layout_visibility;
    }

    public String getCircular_image() {
        return circular_image;
    }

    public void setCircular_image(String circular_image) {
        this.circular_image = circular_image;
    }

    public String getUser_name_text() {
        return user_name_text;
    }

    public void setUser_name_text(String user_name_text) {
        this.user_name_text = user_name_text;
    }

    public String getUser_descriptive_text() {
        return user_descriptive_text;
    }

    public void setUser_descriptive_text(String user_descriptive_text) {
        this.user_descriptive_text = user_descriptive_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text_syle() {
        return status_text_syle;
    }

    public void setStatus_text_syle(String status_text_syle) {
        this.status_text_syle = status_text_syle;
    }

    public String getStatus_text_color() {
        return status_text_color;
    }

    public void setStatus_text_color(String status_text_color) {
        this.status_text_color = status_text_color;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getEstimate_bill() {
        return estimate_bill;
    }

    public void setEstimate_bill(String estimate_bill) {
        this.estimate_bill = estimate_bill;
    }
}
