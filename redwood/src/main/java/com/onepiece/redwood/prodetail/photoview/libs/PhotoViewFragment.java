package com.onepiece.redwood.prodetail.photoview.libs;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.onepiece.redwood.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 *
 */
public class PhotoViewFragment extends Fragment {


    int width, height;
    private String url;
    private Context context;

   /* public PhotoViewFragment() {
        // Required empty public constructor
    }
*/

   /* public void PhotoViewFragment(Context context,String url) {
        this.context = context;
        this.url = url;

    }*/


    private PhotoView imageView;

    public PhotoViewFragment() {
        super();
    }
    @SuppressLint("ValidFragment")
    public PhotoViewFragment(Context context, String url) {

        this.context = context;
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_photo_view, container, false);
       /* width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        height = getActivity().getWindowManager().getDefaultDisplay().getHeight();*/
        imageView = (PhotoView) view.findViewById(R.id.photoIm);
       /* FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);*/
      /*  imageView.setMaxHeight(height);
        imageView.setMaxWidth(width);
        imageView.setAdjustViewBounds(true);*/
        //   progressBar= (CircularProgressBar) view.findViewById(R.id.progressBar);
       // Glide.with(context).load(url).into(imageView);
        ImageLoader.getInstance().displayImage(url, imageView);
   /*     ImageLoader loader = ImageLoader.getInstance();
        ImageLoaderUtils.initImageLoader(getActivity());
        DisplayImageOptions options = new DisplayImageOptions.Builder().
        		showImageOnLoading(R.drawable.background_720).cacheInMemory(true)
        		.bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();*/


        imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                getActivity().finish();
            }
        });

       /* loader.displayImage(url, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (imageUri.equals(url)) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        },new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i2) {
                progressBar.setProgressPecentage((float)i/i2);
            }
        });
*/

        return view;
    }


}
