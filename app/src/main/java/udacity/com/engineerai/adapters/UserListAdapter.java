package udacity.com.engineerai.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.com.engineerai.ui.ItemDetailActivity;
import udacity.com.engineerai.ui.MainActivity;
import udacity.com.engineerai.R;
import udacity.com.engineerai.model.User;

/**
 * Created by amit on 15/3/18.
 */


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private MainActivity mainActivity;
    private List<User> records;

    public UserListAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.records = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            final User record = records.get(position);
            Glide.with(mainActivity).load(record.getImage())
                    .apply(new RequestOptions().circleCrop())
                    .into(holder.userImage);

            holder.userName.setText(record.getName());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mainActivity, ItemDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("USER_DETAILS", record);
                    intent.putExtra("DATA",bundle);
                    mainActivity.startActivity(intent);
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
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.main_layout)
        RelativeLayout layout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void addUsers(int offset, List<User> users) {
        this.records.addAll(users);
        notifyDataSetChanged();
//        notifyItemRangeInserted(offset, users.size());
    }


    private void notifyItemChange(final int positionStart, final int itemCount) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyItemRangeChanged(positionStart, itemCount);
            }
        });
    }
}
