package diplaras.marine.diplarasvesselalert.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import diplaras.marine.diplarasvesselalert.Adapters.PortAdapter;
import diplaras.marine.diplarasvesselalert.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PortFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PortFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Λιμάνια";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1 = "Λιμάνια";
    private String mParam2;
    private PortAdapter adapter;
    private RecyclerView recyclerView;

    public PortFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PortFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PortFragment newInstance(String param1, String param2) {
        PortFragment fragment = new PortFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.port_recycler, container, false);
        adapter = new PortAdapter(getContext());
        recyclerView = view.findViewById(R.id.port_recycler);
        recyclerView.setAdapter(adapter);
        adapter.searchPortTraffic();

        return view;
    }
}