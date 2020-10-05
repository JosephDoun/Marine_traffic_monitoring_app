package diplaras.marine.diplarasvesselalert.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import diplaras.marine.diplarasvesselalert.Database.Vessel;
import diplaras.marine.diplarasvesselalert.R;

public class DockedTrafficAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Vessel> docked = new ArrayList<>();
    Context context;

    public DockedTrafficAdapter(Context context)
    {
        this.context = context;
    }

    public static class DockedTrafficViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView nameView;
        TextView infoView;
        ImageView flagView;

        public DockedTrafficViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.aeCards);
            this.nameView = itemView.findViewById(R.id.ships_name);
            this.infoView = itemView.findViewById(R.id.expected_or_arrived);
            this.flagView = itemView.findViewById(R.id.ae_flag);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.traffic_recycler, parent);

        return new DockedTrafficViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Vessel current = docked.get(position);
        DockedTrafficViewHolder currentHolder = (DockedTrafficViewHolder) holder;

        currentHolder.nameView.setText(current.name);
        currentHolder.infoView.setText(current.lastTransmission);

        int flag = context.getResources().getIdentifier("@drawable/" + current.flag,
                "png", context.getPackageName());
        try
            {
                currentHolder.flagView.setImageDrawable
                    (
                            ResourcesCompat.getDrawable(context.getResources(), flag, null)
                    );
            }
        catch (Exception ignored) {}
    }

    @Override
    public int getItemCount() {
        return docked.size();
    }
}
