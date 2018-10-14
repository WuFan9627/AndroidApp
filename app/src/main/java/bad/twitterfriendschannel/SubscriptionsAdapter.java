package bad.twitterfriendschannel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;


import java.util.List;


public class SubscriptionsAdapter extends RecyclerView.Adapter<SubscriptionsAdapter.ViewHolder> {
    private List<String> subscriptions;
    private Context context;
    private String dataverseName = "channels";
    private String userId;
    private String accessToken;
    private String brokerURL;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView_subscription);
        }
    }
    SubscriptionsAdapter(List<String> list, Context context, String brokerURL, String accessToken, String userId) {
        this.context = context;
        this.subscriptions = list;
        this.brokerURL = brokerURL;
        this.accessToken = accessToken;
        this.userId = userId;
    }
    @Override
    public SubscriptionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription, parent, false);
        SubscriptionsAdapter.ViewHolder holder = new SubscriptionsAdapter.ViewHolder(view);

        context = parent.getContext();
        Log.v("Create View Holder", "True");
        return holder;
    }

    @Override
    public void onBindViewHolder(SubscriptionsAdapter.ViewHolder holder, int position) {
        String name = subscriptions.get(position);
        Log.v("subscriptionname", name);
        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return subscriptions.size();
    }
}
