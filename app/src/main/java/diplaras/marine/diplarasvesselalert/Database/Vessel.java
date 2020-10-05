package diplaras.marine.diplarasvesselalert.Database;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

@Entity(tableName = "Fleet")
public class Vessel {

    public static String baseUrl = "https://www.vesselfinder.com/";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "Country")
    public String country;

    @ColumnInfo(name = "flag")
    public String flag;

    @ColumnInfo(name = "Type")
    public String type;

    @ColumnInfo(name = "Year")
    public String year;

    @ColumnInfo(name = "Location")
    public LatLng loc;

    @ColumnInfo(name = "Speed")
    public float speed;

    @ColumnInfo(name = "Heading")
    public float heading;

    @ColumnInfo(name = "Destination")
    public String destination;

    @ColumnInfo(name = "Status")
    public String status;

    @ColumnInfo(name = "Last transmission")
    public String lastTransmission;

    @ColumnInfo(name = "Url")
    public String url;

    public Vessel(String name, String country, String flag, String type, String year, String url)
    {
        this.name = name;
        this.country = country;
        this.flag = flag;
        this.type = type;
        this.year = year;
        this.url = url;
    }

    public void monitor() {

        /**
         *  Contact server, fetch html
         *  and parse info for loc etc.
         */

        try
        {
            Document response = Jsoup.connect(baseUrl + url).get();
            Elements data = response.getElementsByTag("td");
            String[] headingSpeed = data.get(19).text().split("[^0-9.]+");
            this.heading = Float.parseFloat(headingSpeed[0]);
            this.speed = Float.parseFloat(headingSpeed[1]);
            this.lastTransmission = data.get(25).text();
            this.status = data.get(23).text();
            this.destination = data.get(7).text();
            String[] location = data.get(21).text().split("[^0-9.]+");
            this.loc = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
