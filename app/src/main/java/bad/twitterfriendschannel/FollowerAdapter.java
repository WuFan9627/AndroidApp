package bad.twitterfriendschannel;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import twitter4j.User;

/**
 * Created by Leo on 2/18/2017.
 */

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ViewHolder>{
    private List<User> followers;

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView followerImage;
        TextView followerName;
        public ViewHolder(View view) {
            super(view);
            followerImage = (ImageView) view.findViewById(R.id.follower_image);
            followerName = (TextView) view.findViewById(R.id.follower_name);
        }

    }

    public FollowerAdapter(List<User> followers) {
        this.followers = followers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follower, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = followers.get(position);
        holder.followerName.setText(user.getName());
        String url = user.getBiggerProfileImageURL();
        Uri uri = Uri.parse(url);
        Context context = holder.followerImage.getContext();
        Picasso.with(context).load(uri).into(holder.followerImage);
        int i = 0;
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }


}
