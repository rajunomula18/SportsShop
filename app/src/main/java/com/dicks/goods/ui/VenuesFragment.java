package com.dicks.goods.ui;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dicks.goods.DicksApplication;
import com.dicks.goods.R;
import com.dicks.goods.model.VenueListModel;
import com.dicks.goods.network.DicksVenueAPI;
import com.dicks.goods.utils.LocationUtils;
import com.dicks.goods.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class VenuesFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_info_text)
    TextView errorInfoText;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private VenueRecyclerAdapter venueRecyclerAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VenuesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VenuesFragment newInstance(int columnCount) {
        VenuesFragment fragment = new VenuesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (venueRecyclerAdapter != null) {
            venueRecyclerAdapter.setData(sortAlignVenues(((DicksApplication) getActivity().getApplication()).getVenueList()));
        }
    }

    private void initiateServiceCall() {
        Gson gson = new GsonBuilder()
                .create();
        OkHttpClient httpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DicksVenueAPI.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DicksVenueAPI dicksVenueAPI = retrofit.create(DicksVenueAPI.class);

        Call<VenueListModel> venueList = dicksVenueAPI.getDickVenues();
        venueList.enqueue(new Callback<VenueListModel>() {
            @Override
            public void onResponse(Response<VenueListModel> response, Retrofit retrofit) {
                progressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                errorInfoText.setVisibility(View.GONE);
                List<VenueListModel.Venues> venueList = sortAlignVenues(response.body().getVenues());
                ((DicksApplication) getActivity().getApplication()).setVenueList(venueList);
                mRecyclerView.setAdapter(venueRecyclerAdapter = new VenueRecyclerAdapter(venueList, mListener));
            }

            @Override
            public void onFailure(Throwable t) {
                displayErrorMessage(R.string.tech_issue);
            }
        });
    }

    private List<VenueListModel.Venues> sortAlignVenues(List<VenueListModel.Venues> listModel) {
        Location currentLocation = ((MainActivity) getActivity()).getCurrentLocation();
        if (currentLocation != null) {
            for (VenueListModel.Venues venue : listModel) {
                if (venue.isFavourite()) {
                    venue.setDistance(0);
                } else {
                    venue.setDistance(LocationUtils.getDistance(currentLocation, venue.getLocation()));
                }
            }
            Collections.sort(listModel, new Comparator<VenueListModel.Venues>() {
                @Override
                public int compare(VenueListModel.Venues o1, VenueListModel.Venues o2) {
                    return String.valueOf(o1.getDistance()).compareTo(String.valueOf(o2.getDistance()));
                }
            });
        }
        return listModel;
    }

    private void displayErrorMessage(int app_name) {
        errorInfoText.setVisibility(View.VISIBLE);
        errorInfoText.setText(app_name);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);
        ButterKnife.bind(this, view);
        // Set the adapter
        ((DicksApplication) getActivity().getApplicationContext()).getDicksComponent().inject(this);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            initiateServiceCall();
        } else {
            displayErrorMessage(R.string.no_internet_connection_available);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(VenueListModel.Venues item);
    }
}
