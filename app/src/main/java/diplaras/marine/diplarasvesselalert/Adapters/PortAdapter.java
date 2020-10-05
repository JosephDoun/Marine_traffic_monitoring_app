package diplaras.marine.diplarasvesselalert.Adapters;

import android.content.Context;
import android.util.Log;
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

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import diplaras.marine.diplarasvesselalert.Database.Port;
import diplaras.marine.diplarasvesselalert.R;

public class PortAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Port> myPorts = new ArrayList<>();
    Context context;

    public PortAdapter(Context context)
    {
        this.context = context;
        this.myPorts.add
                (
                        new Port
                                (
                                        "https://www.vesselfinder.com/ports/ELEUSIS-GREECE-24363",
                                        "ΠΑΧΗ (ΠΡΟΒΛΗΤΑ)",
                                        "gr",
                                        new LatLng(37.9703, 23.3809)
                                )
                );
    }

    static class PortViewHolder extends RecyclerView.ViewHolder
    {
        public CardView cardView;
        public LinearLayout rowLayout;
        public TextView nameView;
        public ImageView flagView;
        public TextView arrivedView;
        public TextView expectedView;

        public PortViewHolder(@NonNull View itemView) {

            super(itemView);
            this.cardView = itemView.findViewById(R.id.portCards);
            this.rowLayout = itemView.findViewById(R.id.port_layout);
            this.nameView = itemView.findViewById(R.id.port_name);
            this.flagView = itemView.findViewById(R.id.port_flag);
            this.arrivedView = itemView.findViewById(R.id.size_arrived);
            this.expectedView = itemView.findViewById(R.id.size_expected);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.port_cards, parent, false);

        return new PortViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PortViewHolder currentHolder = (PortViewHolder) holder;
        final Port current = myPorts.get(position);

        currentHolder.rowLayout.setTag(current);
        currentHolder.nameView.setText(current.getName());
        currentHolder.arrivedView.setText(current.getDocked());
        currentHolder.expectedView.setText(current.getExpected());

        int flag = context.getResources().getIdentifier
                ("@drawable/" + current.getFlag(),
                "png",
                context.getPackageName());
        try
            {currentHolder.flagView.setImageDrawable
                    (
                            ResourcesCompat.getDrawable(context.getResources(), flag, null)
                    );}
        catch (Exception e) {e.printStackTrace();}

        currentHolder.rowLayout.setOnClickListener
                (
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

//                                ((MainActivity) view.getContext()).initTrafficView();
                                current.findVessels(context);
                                Log.v("debug_ports", String.valueOf(current.getVesselsDocked().size()));
                            }
                        }
                );
    }

    @Override
    public int getItemCount() {
        return myPorts.size();
    }

    public void searchPortTraffic() {
        for (Port port : myPorts)
        {
            port.updateInfo(context);
        }
        notifyDataSetChanged();
    }
}
