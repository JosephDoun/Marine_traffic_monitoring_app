package diplaras.marine.diplarasvesselalert.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.List;

import diplaras.marine.diplarasvesselalert.Activities.MainActivity;
import diplaras.marine.diplarasvesselalert.Database.Vessel;
import diplaras.marine.diplarasvesselalert.R;


public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String searchSize;
    private int pages;
    Elements ids;
    Elements types;
    Elements years;
    private Context context;
    public List<Vessel> searchVesselsList = new ArrayList<>();

    public SearchAdapter(Context context)
    {
        this.context = context;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder
    {
        public CardView cardView;
        public LinearLayout rowLayout;
        public TextView nameTextView;
        public TextView typeTextView;
        public TextView countryTextView;
        public TextView yearTextView;
        public ImageView flagView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.searchCards);
            this.rowLayout = itemView.findViewById(R.id.card_layout);
            this.nameTextView = itemView.findViewById(R.id.nameViewSearch);
            this.typeTextView = itemView.findViewById(R.id.vessel_type);
            this.countryTextView = itemView.findViewById(R.id.countryViewSearch);
            this.yearTextView = itemView.findViewById(R.id.yearMadeView);
            this.flagView = itemView.findViewById(R.id.search_flag);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_cards, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        SearchViewHolder currentHolder = (SearchViewHolder) holder;
        final Vessel current = searchVesselsList.get(position);

        currentHolder.rowLayout.setTag(current);
        currentHolder.nameTextView.setText(current.name);
        currentHolder.typeTextView.setText(current.type);
        currentHolder.countryTextView.setText(current.country);
        currentHolder.yearTextView.setText(current.year);

        int flag = context.getResources()
                .getIdentifier("@drawable/" + current.flag,
                        "png",
                        context.getPackageName());

        try
            {
                currentHolder.flagView.setImageDrawable
                    (
                            ResourcesCompat.getDrawable(context.getResources(),
                                    flag,
                                    null)
                    );
            }
        catch (Exception ignored)
        {

        }

        currentHolder.rowLayout.setOnLongClickListener
                (
                        new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {

                                ((MainActivity) view.getContext()).initFleetView();
                                MainActivity.database.fleetDao().insert(current);
                                return true;

                            }
                        }
                );
        }



    @Override
    public int getItemCount() {
        return searchVesselsList.size();
    }

    public String searchVessels(String query, Context context)
    {
        searchVesselsList.clear();

        try {

            String URL = Vessel.baseUrl + "vessels?name=" + query.toUpperCase();
            Document response = Jsoup.connect(URL).get();

            ids = response.getElementsByClass("ship-link");
            years = response.getElementsByClass("v3");
            types = response.getElementsByTag("small");

            String total = response.getElementsByClass("pagination-totals")
                    .text()
                    .split(" ")[0];

            if (ids.size() > 0)
            {
                searchSize = total + " Αποτελέσματα";
                years.remove(0);

                int sum = Integer.parseInt(total.replace(",", ""));
                int residual = sum % ids.size();

                pages = (residual > 0) ? (sum / ids.size()) + 1 : sum / ids.size();


                Log.v("debug_pages", String.valueOf(pages));

            }
            else { searchSize = "0 Αποτελέσματα"; }
        }
        catch (Exception e)
        {
            Toast.makeText(context,
                    "Σφάλμα, ελέγξτε τη σύνδεσή σας...",
                    Toast.LENGTH_LONG).show();
        }

        return searchSize;
    }

    public void loadSearchData()
    {
        for (int i =0; i < ids.size(); ++i)
        {

            String url = ids.get(i).attr("href");
            String name = ids.get(i).text();
            String country = ids.get(i).child(0).attr("title");
            String flag = ids.get(i).child(0).attr("class").substring
                    (
                            ids.get(i).child(0).attr("class").lastIndexOf("-")
                            +
                            1
                    );

            if (flag == "do") {flag = "dom";}

            String type = types.get(i).text();
            String year = years.get(i).text();

            searchVesselsList.add
                    (
                            new Vessel
                                    (
                                            name,
                                            country,
                                            flag,
                                            type,
                                            year,
                                            url
                                    )
                    );
        }
        notifyDataSetChanged();
    }


    public void saveVessel(int position)
    {

    }

}
