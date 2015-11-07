package archit.scannerapp;

/**
 * Created by archit on 5/11/15.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import archit.model.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.CustomViewHolder> {
    private List<History> feedItemList;
    private Context mContext;

    public HistoryAdapter(Context context, List<History> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_row, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        History feedItem = feedItemList.get(i);

        //Setting text view title
        customViewHolder.date.setText(feedItem.getDate());
        customViewHolder.data.setText(feedItem.getData());
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView date;
        protected TextView data;

        public CustomViewHolder(View view) {
            super(view);
            this.date = (TextView) view.findViewById(R.id.date);
            this.data = (TextView) view.findViewById(R.id.data);
        }
    }
}

