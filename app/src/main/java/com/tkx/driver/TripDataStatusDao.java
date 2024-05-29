import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TripDataStatusDao {
    @Insert
    void insert(TripDataStatus tripDataStatus);

    @Query("SELECT * FROM TripDataStatus ORDER BY id DESC")
    List<TripDataStatus> getAll();

    @Query("SELECT * FROM TripDataStatus WHERE id = :id")
    TripDataStatus getById(int id);

    @Query("DELETE FROM TripDataStatus WHERE id = :id")
    void deleteAll();
}