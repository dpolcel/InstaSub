package br.com.polcel.instasub.adapters;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.com.polcel.instasub.R;
import br.com.polcel.instasub.models.Subtitle;

/**
 * Created by polcel on 23/03/17.
 */

public class InstaSubsRecyclerViewCursorAdapter extends RecyclerView.Adapter<InstaSubsRecyclerViewCursorAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Subtitle> mSubtitles;

    private Context getContext() {
        return mContext;
    }

    public InstaSubsRecyclerViewCursorAdapter(Context context, ArrayList<Subtitle> subtitles) {
        mContext = context;
        mSubtitles = subtitles;
    }


    @Override
    public InstaSubsRecyclerViewCursorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View subtitleView = inflater.inflate(R.layout.sub_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(subtitleView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InstaSubsRecyclerViewCursorAdapter.ViewHolder holder, int position) {

        Subtitle subtitle = mSubtitles.get(position);

        TextView titleTextView = holder.titleTextView;
        titleTextView.setText(subtitle.getTitle());

        TextView descriptionTextView = holder.descriptionTextView;
        descriptionTextView.setText(subtitle.getDescription());
    }

    @Override
    public int getItemCount() {
        return mSubtitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView descriptionTextView;

        public ViewHolder(View view) {
            super(view);

            titleTextView = (TextView) view.findViewById(R.id.subtitle_title);
            descriptionTextView = (TextView) view.findViewById(R.id.subtitle_description);
        }
    }

}
