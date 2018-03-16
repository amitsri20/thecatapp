package udacity.com.engineerai.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.com.engineerai.R;
import udacity.com.engineerai.adapters.UserItemAdapter;
import udacity.com.engineerai.model.User;

public class ItemDetailActivity extends AppCompatActivity {

    @BindView(R.id.userItemList)
    RecyclerView userItemList;
    @BindView(R.id.toolbar)
    Toolbar toolbarView;
    @BindView(R.id.user_image)
    AppCompatImageView userImage;
    @BindView(R.id.user_name)
    TextView userName;
    private User user;
    private UserItemAdapter userItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        user = getIntent().getBundleExtra("DATA").getParcelable("USER_DETAILS");

        userItemAdapter = new UserItemAdapter(ItemDetailActivity.this);

        initToolbar();
        initUserItemList();
    }

    private void initToolbar() {
        if (null != toolbarView) {
            Glide.with(this)
                    .load(user.getImage())
                    .apply(new RequestOptions().circleCrop().placeholder(R.drawable.ic_turkish_angora))
                    .into(userImage);
            userName.setText(user.getName());
        }
    }

    private void initUserItemList() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return userItemAdapter.getItemViewType(position);
            }
        });
        userItemList.setLayoutManager(mLayoutManager);
        userItemList.setAdapter(userItemAdapter);

        userItemAdapter.addItems(user.getItems());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        this.finish();
    }
}
