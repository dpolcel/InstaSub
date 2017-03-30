package br.com.polcel.instasub.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import br.com.polcel.instasub.R;
import br.com.polcel.instasub.contracts.InstaSubContract;
import br.com.polcel.instasub.helpers.InstaSubDbHelper;
import br.com.polcel.instasub.models.SubtitleModel;
import br.com.polcel.instasub.utils.Tools;

/**
 * Created by polcel on 23/03/17.
 */

public class SubtitlesRecyclerViewAdapter extends RecyclerView.Adapter<SubtitlesRecyclerViewAdapter.ViewHolder> {
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec

    private Context mContext;
    private ArrayList<SubtitleModel> mSubtitleModels;
    List<SubtitleModel> itemsPendingRemoval;
    boolean undoOn = true;
    InstaSubDbHelper mInstaSubDbHelper;

    private Handler handler = new Handler();
    HashMap<SubtitleModel, Runnable> pendingRunnables = new HashMap<>();

    public interface OnItemClickListener {
        void onItemClick(SubtitleModel item);
    }

    private final OnItemClickListener listener;

    private Context getContext() {
        return mContext;
    }

    public SubtitlesRecyclerViewAdapter(Context context, ArrayList<SubtitleModel> subtitles, OnItemClickListener listener) {
        mContext = context;
        mSubtitleModels = subtitles;
        itemsPendingRemoval = new ArrayList<>();
        this.listener = listener;
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
        final SubtitleModel subtitleModel = mSubtitleModels.get(position);

        holder.bind(subtitleModel, listener);

        if (itemsPendingRemoval.contains(subtitleModel)) {
            holder.itemView.setBackgroundColor(Color.RED);
            holder.placeHolderCardView.setCardBackgroundColor(Color.RED);

            holder.titleTextView.setVisibility(View.GONE);
            holder.createdTextView.setVisibility(View.GONE);
            holder.titleDivider.setVisibility(View.GONE);
            holder.dateDivider.setVisibility(View.GONE);
            holder.descriptionTextView.setVisibility(View.GONE);
            holder.undoButton.setVisibility(View.VISIBLE);

            holder.undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Runnable pendingRemovalRunnable = pendingRunnables.get(subtitleModel);
                    pendingRunnables.remove(subtitleModel);
                    if (pendingRemovalRunnable != null)
                        handler.removeCallbacks(pendingRemovalRunnable);
                    itemsPendingRemoval.remove(subtitleModel);
                    notifyItemChanged(mSubtitleModels.indexOf(subtitleModel));
                }
            });
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.titleTextView.setVisibility(View.VISIBLE);
            holder.titleTextView.setText(subtitleModel.getTitle());
            holder.descriptionTextView.setVisibility(View.VISIBLE);
            holder.descriptionTextView.setText(subtitleModel.getDescription());
//            holder.descriptionTextView.setText(Tools.formatStringWithSeeMore(subtitleModel.getDescription()));
            holder.titleDivider.setVisibility(View.VISIBLE);
            holder.dateDivider.setVisibility(View.VISIBLE);
            holder.createdTextView.setText(Tools.formatDateFromMillis("dd-MM-yyyy - HH:mm", subtitleModel.getCreated()));

            holder.undoButton.setVisibility(View.GONE);
            holder.undoButton.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return mSubtitleModels.size();
    }

    public boolean isUndoOn() {
        return undoOn;
    }

    public void pendingRemoval(int position) {
        final SubtitleModel subtitle = mSubtitleModels.get(position);
        if (!itemsPendingRemoval.contains(subtitle)) {
            itemsPendingRemoval.add(subtitle);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the item
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(mSubtitleModels.indexOf(subtitle));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(subtitle, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        SubtitleModel subtitle = mSubtitleModels.get(position);
        if (itemsPendingRemoval.contains(subtitle)) {
            itemsPendingRemoval.remove(subtitle);
        }
        if (mSubtitleModels.contains(subtitle)) {
            mSubtitleModels.remove(position);

            mInstaSubDbHelper = new InstaSubDbHelper(mContext);
            SQLiteDatabase db = mInstaSubDbHelper.getWritableDatabase();

            //remove item from Db
            db.execSQL(String.format(Locale.getDefault(), InstaSubContract.InstaSub.SQL_DELETE_SUBTITLE, subtitle.getId()));

            notifyItemRemoved(position);
        }
    }

    public boolean isPendingRemoval(int position) {
        SubtitleModel subtitle = mSubtitleModels.get(position);
        return itemsPendingRemoval.contains(subtitle);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        TextView createdTextView;
        Button undoButton;
        View titleDivider;
        View dateDivider;
        CardView placeHolderCardView;

        ViewHolder(View view) {
            super(view);

            titleTextView = (TextView) view.findViewById(R.id.rv_subtitles_item_tv_subtitle_title);
            descriptionTextView = (TextView) view.findViewById(R.id.rv_subtitles_item_tv_subtitle_description);
            createdTextView = (TextView) view.findViewById(R.id.rv_subtitles_item_tv_subtitle_created);
            undoButton = (Button) view.findViewById(R.id.rv_subtitles_item_bt_undo);
            titleDivider = view.findViewById(R.id.rv_subtitles_item_vw_title_divider);
            dateDivider = view.findViewById(R.id.rv_subtitles_item_vw_date_divider);
            placeHolderCardView = (CardView) view.findViewById(R.id.rv_subtitles_item_cv_subtitle);
        }

        public void bind(final SubtitleModel subtitle, final OnItemClickListener listener) {
            //  name.setText(item.name);
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(subtitle);
                }
            });
        }
    }

}
