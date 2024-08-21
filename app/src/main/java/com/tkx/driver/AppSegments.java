package com.tkx.driver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "segments")
public class AppSegments {
    @PrimaryKey
    public int id;
    public String icon;
    public String name;
    public String description;
    public String slag;
    public int is_coming_soon;
    public String created_at;
    public String updated_at;

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getIs_coming_soon() {
        return is_coming_soon;
    }

    public void setIs_coming_soon(int is_coming_soon) {
        this.is_coming_soon = is_coming_soon;
    }

    public String getSlag() {
        return slag;
    }

    public void setSlag(String slag) {
        this.slag = slag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}