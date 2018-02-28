package team11.mobileiot.myooz;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by flora on 2/27/18.
 */

public class Interval extends RecyclerView.ItemDecoration{
    private int space;
    public Interval(int space){
        this.space=space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        outRect.bottom=space;
        outRect.right=space;
        outRect.left=space;
        outRect.top=space;
        if(parent.getChildPosition(view)!=0){
            outRect.top=space;
        }
    }
}
