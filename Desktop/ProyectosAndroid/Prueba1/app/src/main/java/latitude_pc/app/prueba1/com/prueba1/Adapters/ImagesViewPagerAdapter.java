package latitude_pc.app.prueba1.com.prueba1.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import latitude_pc.app.prueba1.com.prueba1.Models.ImagesModel;
import latitude_pc.app.prueba1.com.prueba1.Models.ServicesModel;
import latitude_pc.app.prueba1.com.prueba1.ProjectDetail;
import latitude_pc.app.prueba1.com.prueba1.R;

/**
 * Created by Luis Garcia on 30/10/2017.
 */

public class ImagesViewPagerAdapter extends PagerAdapter {
    private ArrayList<ImagesModel> images;
    private Context context;

    public ImagesViewPagerAdapter (ArrayList<ImagesModel> images, Context context) {
        this.images= images;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setImageResource(images.get(position).getIdImage());
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        return new String(images.get(position).Date);
    }

}
