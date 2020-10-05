package diplaras.marine.diplarasvesselalert;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

public final class TypeConverters {

    @TypeConverter
    public static LatLng fromString(String string)
    {
        String[] array = string == null ? null : string.split("-");
        return array == null ? null : new LatLng(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
    }

    @TypeConverter
    public static String fromLatLng(LatLng latLng)
    {
        return latLng == null ? null : latLng.latitude + "-" + latLng.longitude;
    }

}