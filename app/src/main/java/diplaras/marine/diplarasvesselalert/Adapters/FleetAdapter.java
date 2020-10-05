package diplaras.marine.diplarasvesselalert.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import diplaras.marine.diplarasvesselalert.Activities.MainActivity;
import diplaras.marine.diplarasvesselalert.Database.Vessel;
import diplaras.marine.diplarasvesselalert.R;

public class FleetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Vessel> myFleet = new ArrayList<>();
    Context context;

    public FleetAdapter(Context context)
    {
        this.context = context;
    }

    public static class FleetViewHolder extends RecyclerView.ViewHolder
    {
        public CardView cardView;
        public LinearLayout rowLayout;
        public TextView nameTextView;
        public TextView typeTextView;
        public TextView countryTextView;
        public TextView yearTextView;
        public ImageView flagView;

        public FleetViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.searchCards);
            this.rowLayout = itemView.findViewById(R.id.card_layout);
            this.nameTextView = itemView.findViewById(R.id.nameViewSearch);
            this.typeTextView = itemView.findViewById(R.id.vessel_type);
            this.countryTextView = itemView.findViewById(R.id.countryViewSearch);
            this.yearTextView = itemView.findViewById(R.id.yearMadeView);
            this.flagView = itemView.findViewById(R.id.fleet_flag);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fleet_cards, parent, false);

        return new FleetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FleetViewHolder currentHolder = (FleetViewHolder) holder;
        final Vessel current = myFleet.get(position);

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
            {currentHolder.flagView.setImageDrawable
                    (
                            ResourcesCompat.getDrawable(context.getResources(), flag, null)
                    );}
        catch (Exception ignore) {}

        currentHolder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        current.monitor();
                    }
                }).start();

            }
        });

    }

    @Override
    public int getItemCount() {
        return myFleet.size();
    }

    public void loadFleetData()
    {
        myFleet = MainActivity.database.fleetDao().load();
        notifyDataSetChanged();
    }
}
