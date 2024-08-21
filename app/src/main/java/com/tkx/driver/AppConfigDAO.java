package com.tkx.driver;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface AppConfigDAO {
    @Insert
    void insertSegment(AppSegments segment);

    @Insert
    void insertCountry(AppCountry country);

    @Insert
    void insertCountryArea(AppCountryArea countryArea);

    @Insert
    void insertAppData(AppConfigurations appConfigurations);

    @Insert
    void insertSegments(List<AppSegments> segments);

    @Insert
    void insertCountries(List<AppCountry> countries);

    @Insert
    void insertCountryAreas(List<AppCountryArea> countryAreas);

    @Transaction
    @Query("SELECT * FROM countries")
    List<AppCountryWithAreas> getAllCountries();

    @Transaction
    @Query("SELECT * FROM configurations")
    List<AppConfigurationsWithSegments> getAllAppConfigurations();
}