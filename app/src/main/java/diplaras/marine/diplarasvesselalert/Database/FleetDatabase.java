package diplaras.marine.diplarasvesselalert.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {Vessel.class}, version = 1)
@TypeConverters({diplaras.marine.diplarasvesselalert.TypeConverters.class})
public abstract class FleetDatabase extends RoomDatabase {
    public abstract FleetDao fleetDao();
}
