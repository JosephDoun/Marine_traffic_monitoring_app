package diplaras.marine.diplarasvesselalert.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import diplaras.marine.diplarasvesselalert.Adapters.FleetAdapter;
import diplaras.marine.diplarasvesselalert.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FleetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FleetFragment extends Fragment {

    FleetAdapter adapter;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1 = "Στόλος";
    private String mParam2;

    public FleetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FleetFragment newInstance(String param1, String param2) {
        FleetFragment fragment = new FleetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        TextView searchResultsView = getActivity().findViewById(R.id.search_results);
        searchResultsView.setText("Αναζήτηση");

        FleetFragment fleetAdapter = new FleetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fleet_recycler, container, false);
        adapter = new FleetAdapter(getContext());
        adapter.loadFleetData();
        recyclerView = view.findViewById(R.id.fleet_recycler);
        recyclerView.setAdapter(adapter);

        return view;
    }
}