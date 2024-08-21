package com.tkx.driver;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AppConfigurationsWithSegments {
    @Embedded
    public AppConfigurations configurations;

    @Relation(parentColumn = "id", entityColumn = "id")
    public List<AppSegments> segments;
}