package diplaras.marine.diplarasvesselalert.Activities;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import diplaras.marine.diplarasvesselalert.R;
import diplaras.marine.diplarasvesselalert.Adapters.SearchAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    String results;
    SearchAdapter adapter;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Αναζήτηση";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1 = "Αναζήτηση";
    private String mParam2;

    public SearchFragment() {
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
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_recycler, container, false);

        final String query = getArguments().getString("query");

        adapter = new SearchAdapter(getContext());
        recyclerView = view.findViewById(R.id.search_recycler);
        recyclerView.setAdapter(adapter);
        TextView searchResultsView = getActivity().findViewById(R.id.search_results);

        final Toast searchToast = Toast.makeText(getActivity(),
                "Αναζήτηση...",
                Toast.LENGTH_LONG);

        Thread searchThread =
                new Thread(new Runnable() {

                    /**
                     * The html response will
                     *  ultimately have to be
                     *  requested through Volley.
                     */

                    @Override
                    public void run() {

                        searchToast.show();
                        results = adapter.searchVessels(query, getActivity());
                    }
                });

        searchThread.start();

        try {
            searchThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        searchToast.cancel();

        adapter.loadSearchData();
        searchResultsView.setText(results);

        return view;
    }

}