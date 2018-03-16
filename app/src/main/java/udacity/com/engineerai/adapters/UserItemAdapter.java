package udacity.com.engineerai.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.com.engineerai.R;
import udacity.com.engineerai.ui.ItemDetailActivity;

/**
 * Created by amit on 15/3/18.
 */

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ViewHolder> {
    private ItemDetailActivity detailActivity;
    private List<String> records;

    public UserItemAdapter(ItemDetailActivity detailActivity) {
        this.detailActivity = detailActivity;
        this.records = new ArrayList<>();
    }

    @Override
    public UserItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users_pictures, parent, false);
        return new UserItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserItemAdapter.ViewHolder holder, int position) {
        try {
            final String record = records.get(position);
            Glide.with(detailActivity).load(record)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_turkish_angora))
                    .into(holder.userImage);

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.user_image)
        ImageView userImage;
        @BindView(R.id.main_layout)
        RelativeLayout layout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void addItems(List<String> usersItems) {
        this.records.addAll(usersItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount()%2 != 0 && position == 0) {
            return 2;
        } else {
            return 1;
        }
    }
}
