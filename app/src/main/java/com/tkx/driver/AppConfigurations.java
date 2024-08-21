package com.tkx.driver;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "configurations")
public class AppConfigurations {
    @PrimaryKey
    public int id;
    public String configName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
}