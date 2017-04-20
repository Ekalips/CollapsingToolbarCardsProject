package com.wldev.tttest;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    public static SecondFragment newInstance(Bitmap image) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Bundle b = new Bundle();
        b.putByteArray("image", byteArray);
        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(b);
        return fragment;
    }

    public static SecondFragment newInstance(int image, String text, String desc) {


        Bundle b = new Bundle();
        b.putInt("imageID", image);
        b.putString("title",text);
        b.putString("desc",desc);
        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(b);
        return fragment;
    }
    
    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        rootView.setPadding(0,getStatusBarHeight(),0,0);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
        TextView titleTv = (TextView) rootView.findViewById(R.id.title);
        TextView descTv = (TextView) rootView.findViewById(R.id.descr);
        if (getArguments().containsKey("image")) {
            byte[] byteArray = getArguments().getByteArray("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bmp);
        }
        else if(getArguments().containsKey("imageID"))
        {
            imageView.setImageResource(getArguments().getInt("imageID"));
        }

        if (getArguments().containsKey("title"))
        {
            titleTv.setText(getArguments().getString("title"));
            descTv.setText(getArguments().getString("desc"));
        }



        return rootView;
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
