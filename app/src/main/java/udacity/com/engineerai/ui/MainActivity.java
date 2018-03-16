package udacity.com.engineerai.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udacity.com.engineerai.R;
import udacity.com.engineerai.adapters.UserListAdapter;
import udacity.com.engineerai.api.ApiService;
import udacity.com.engineerai.model.User;
import udacity.com.engineerai.model.Users;
import udacity.com.engineerai.utils.EndlessRecyclerViewScrollListener;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.userList)
    RecyclerView userListRecyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    private List<User> users;
    private Boolean nextPage = false;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int offset = 0;
    private UserListAdapter userListAdapter;
    private int limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        userListAdapter = new UserListAdapter(MainActivity.this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        userListRecyclerView.setLayoutManager(manager);
        userListRecyclerView.setAdapter(userListAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                offset += 10;
                if (nextPage)
                    initUserList(offset, limit);
            }
        };
        userListRecyclerView.addOnScrollListener(scrollListener);

        initUserList(offset, limit);
    }


    private void initUserList(final int offset, int limit) {
        progressbar.setVisibility(View.VISIBLE);
        ApiService.Application.getUsers(this, offset, limit)
                .enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        if (response.code() == 200) {
                            users = response.body().getData().getUsers();
                            nextPage = response.body().getData().getHasMore();
                            userListAdapter.addUsers(offset, users);
                        } else
                            Toast.makeText(MainActivity.this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
                        progressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
                    }
                });

        userListRecyclerView.setHasFixedSize(true);
    }

}
