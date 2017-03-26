package br.com.polcel.instasub.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.polcel.instasub.R;
import br.com.polcel.instasub.models.SubtitleModel;

/**
 * Created by polcel on 23/03/17.
 */

public class SubtitlesRecyclerViewAdapter extends RecyclerView.Adapter<SubtitlesRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SubtitleModel> mSubtitleModels;

    private Context getContext() {
        return mContext;
    }

    public SubtitlesRecyclerViewAdapter(Context context, ArrayList<SubtitleModel> subtitles) {
        mContext = context;
        mSubtitleModels = subtitles;
    }


    @Override
    public SubtitlesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View subtitleView = inflater.inflate(R.layout.activity_main_rv_subtitles_item, parent, false);

        return new ViewHolder(subtitleView);
    }

    @Override
    public void onBindViewHolder(SubtitlesRecyclerViewAdapter.ViewHolder holder, int position) {

        SubtitleModel subtitleModel = mSubtitleModels.get(position);

        TextView titleTextView = holder.titleTextView;
        titleTextView.setText(subtitleModel.getTitle());

        TextView descriptionTextView = holder.descriptionTextView;
        descriptionTextView.setText(subtitleModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return mSubtitleModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView descriptionTextView;

        ViewHolder(View view) {
            super(view);

            titleTextView = (TextView) view.findViewById(R.id.subtitle_title);
            descriptionTextView = (TextView) view.findViewById(R.id.subtitle_description);
        }
    }

}
