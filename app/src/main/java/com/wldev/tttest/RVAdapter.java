package com.wldev.tttest;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wldev.tttest.databinding.RvItemBinding;

/**
 * Created by wldev on 12/29/16.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{

    public interface onClick{
        public void onClick(RvItemBinding binding);
    }

    private onClick onClickListener;
    private static final String TAG = RVAdapter.class.getSimpleName();
    int height = 0;
    boolean isEnd = false;


    public RVAdapter(onClick onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(RvItemBinding.inflate(inflater,parent,false).getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.descr.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        switch (holder.getAdapterPosition())
        {
            case 0:holder.binding.title.setText("About us"); holder.binding.image.setImageResource(R.drawable.img1);holder.binding.image.setTag(R.drawable.img1);break;
            case 1:holder.binding.title.setText("Menu"); holder.binding.image.setImageResource(R.drawable.img2); holder.binding.image.setTag(R.drawable.img2);break;
            case 2:holder.binding.title.setText("Credits"); holder.binding.image.setImageResource(R.drawable.img3);holder.binding.image.setTag(R.drawable.img3); break;
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.binding.image.getLayoutParams();
        params.height = this.height;
        holder.binding.image.requestLayout();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEnd)
                    onClickListener.onClick(holder.binding);
            }
        });
//        Log.d(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void changeImageWidth(int i, boolean isEnd) {
        height = i;
        this.isEnd = isEnd;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RvItemBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
