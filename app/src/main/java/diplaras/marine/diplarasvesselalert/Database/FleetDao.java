package diplaras.marine.diplarasvesselalert.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao()
public interface FleetDao {

    @Insert
    void insert(Vessel... vessels);

    @Query("SELECT * FROM Fleet")
    List<Vessel> load();

    @Delete
    void delete(Vessel vessel);

}
