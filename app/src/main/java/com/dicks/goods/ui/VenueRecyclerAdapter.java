package com.dicks.goods.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dicks.goods.R;
import com.dicks.goods.model.VenueListModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.dicks.goods.model.VenueListModel.Venues} and makes a call to the
 * specified {@link VenuesFragment.OnListFragmentInteractionListener}.
 */
public class VenueRecyclerAdapter extends RecyclerView.Adapter<VenueRecyclerAdapter.ViewHolder> {

    private List<VenueListModel.Venues> mValues;
    private final VenuesFragment.OnListFragmentInteractionListener mListener;

    public VenueRecyclerAdapter(List<VenueListModel.Venues> items, VenuesFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_venue, parent, false);
        return new ViewHolder(view);
    }

    public void setData(List<VenueListModel.Venues> mValues){
        this.mValues = mValues;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mRatingView.setText(String.valueOf(mValues.get(position).getRating()));
        holder.mDistanceView.setText(String.valueOf(mValues.get(position).getDistance()));
        holder.mFavoriteView.setImageResource(holder.mItem.isFavourite() ? android.R.drawable.star_big_on : android.R.drawable.star_big_off);
        holder.mVerifiedView.setImageResource(holder.mItem.getVerified() ? android.R.drawable.ic_lock_lock : android.R.drawable.ic_partial_secure);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mRatingView;
        public final TextView mDistanceView;
        public final ImageView mFavoriteView;
        public final ImageView mVerifiedView;

        public VenueListModel.Venues mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.name);
            mRatingView = view.findViewById(R.id.rating);
            mDistanceView = view.findViewById(R.id.distance);
            mFavoriteView = view.findViewById(R.id.favourite);
            mVerifiedView = view.findViewById(R.id.verified);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mRatingView.getText() + "'";
        }
    }
}
