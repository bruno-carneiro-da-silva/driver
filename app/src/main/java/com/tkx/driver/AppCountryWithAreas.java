package com.tkx.driver;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AppCountryWithAreas {
    @Embedded
    public AppCountry country;

    @Relation(parentColumn = "id", entityColumn = "id")
    public List<AppCountryArea> countryAreas;
}