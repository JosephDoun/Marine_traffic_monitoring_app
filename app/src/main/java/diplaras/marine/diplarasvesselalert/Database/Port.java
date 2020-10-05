package diplaras.marine.diplarasvesselalert.Database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Port {

    private String url;
    private String name;
    private String flag;
    private String expected;
    private String docked;
    private LatLng loc;
    private String lastUpdate;
    private List<Vessel> vesselsExpected = new ArrayList<>();
    private List<Vessel> vesselsDocked = new ArrayList<>();

    public Port(String url, String name, String flag, LatLng loc) {
        this.url = url;
        this.name = name;
        this.flag = flag;
        this.loc = loc;
    }


    public String getUrl() {
        return this.url;
    }

    public String getName() {
        return this.name;
    }

    public String getExpected() {
        return this.expected;
    }

    public LatLng getLoc() {
        return this.loc;
    }

    public String getDocked() {
        return this.docked;
    }

    public String getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Vessel> getVesselsDocked() {
        return this.vesselsDocked;
    }

    public void setVesselsDocked(Vessel vessel) {
        this.vesselsDocked.add(vessel);
    }

    public void setVesselsExpected(Vessel vessel) {
        this.vesselsExpected.add(vessel);
    }

    public String getFlag() {
        return this.flag;
    }

    public void updateInfo(Context context) {

        final Toast toast = Toast.makeText(context,
                "Σφάλμα. Ελέγξτε την σύνδεσή σας.",
                Toast.LENGTH_LONG);

        Thread portInfoThread =
        new Thread
                (
                        new Runnable() {
                            @Override
                            public void run() {
                                try
                                    {
                                        Document response = Jsoup.connect(url).get();
                                        Element info = response
                                                .getElementsByClass("pei").get(0);

                                        docked = info.child(1).child(1).text();
                                        expected = info.child(0).child(1).text();

                                        Log.v("debug_port", docked);
                                    }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    toast.show();
                                }
                            }
                        }
                );

        portInfoThread.start();
        try {
            portInfoThread.join();
        }
        catch (InterruptedException e)
        {
            Toast.makeText(context,
                    "Σφάλμα παραλληλότητας. Ξαναπροσπαθήστε",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void findVessels(Context context)
    {
        final Toast toast = Toast.makeText(context,
                "Σφάλμα. Ελέγξτε την σύνδεσή σας.",
                Toast.LENGTH_LONG);

        Thread requestVessels = new Thread
                (
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Document response = Jsoup.connect(url).get();
                                    Elements table = response.getElementsByClass("table");
                                    Elements expected = table.get(0).getElementsByTag("tr");
                                    Elements docked = table.get(3).getElementsByTag("tr");

                                    if (!getExpected().equals("0"))
                                    {
                                        for (int i = 1; i < expected.size(); i++)
                                        {
                                            String urlEx = expected.get(i)
                                                    .children().get(1).getAllElements()
                                                    .get(1).attr("href");
                                            String nameEx = expected.get(i)
                                                    .getElementsByClass("named-title")
                                                    .text();
                                            String typeEx = expected.get(i)
                                                    .getElementsByClass("named-subtitle")
                                                    .text();
                                            String flagExString = expected.get(i)
                                                    .getElementsByClass("m-flag-small flag-icon")
                                                    .attr("style");
                                            String flagEx = flagExString
                                                    .substring
                                                            (
                                                                    flagExString.lastIndexOf("/") + 1,
                                                                    flagExString.lastIndexOf(".")
                                                            );
                                            String countryEx = expected.get(i)
                                                .getElementsByClass("m-flag-small flag-icon")
                                                .attr("title");
                                            String yearEx = expected.get(i)
                                                    .getElementsByClass("is-hidden-mobile cm")
                                                    .get(0).text();

                                            setVesselsExpected
                                                    (
                                                            new Vessel
                                                                    (
                                                                            nameEx,
                                                                            countryEx,
                                                                            flagEx,
                                                                            typeEx,
                                                                            yearEx,
                                                                            urlEx
                                                                    )
                                                    );


                                        }
                                    }

                                    if (!getDocked().equals("0"))
                                    {
                                        for (int i = 1; i < docked.size(); i++)
                                        {
                                            String urlEx = docked.get(i)
                                                    .children().get(1).getAllElements()
                                                    .get(1).attr("href");
                                            String nameEx = docked.get(i)
                                                    .getElementsByClass("named-title")
                                                    .text();
                                            String typeEx = docked.get(i)
                                                    .getElementsByClass("named-subtitle")
                                                    .text();
                                            String flagExString = docked.get(i)
                                                    .getElementsByClass("m-flag-small flag-icon")
                                                    .attr("style");
                                            String flagEx = flagExString
                                                    .substring
                                                            (
                                                                    flagExString.lastIndexOf("/") + 1,
                                                                    flagExString.lastIndexOf(".")
                                                            );
                                            String countryEx = docked.get(i)
                                                    .getElementsByClass("m-flag-small flag-icon")
                                                    .attr("title");
                                            String yearEx = docked.get(i)
                                                    .getElementsByClass("is-hidden-mobile cm")
                                                    .get(0).text();

                                            setVesselsDocked
                                                    (
                                                            new Vessel
                                                                    (
                                                                            nameEx,
                                                                            countryEx,
                                                                            flagEx,
                                                                            typeEx,
                                                                            yearEx,
                                                                            urlEx
                                                                    )
                                                    );
                                        }
                                    }

                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    toast.show();
                                }

                            }
                        }
                );

        requestVessels.start();
        try {
            requestVessels.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.v("debug_ports_interior", String.valueOf(this.vesselsExpected.size()));

    }
}
