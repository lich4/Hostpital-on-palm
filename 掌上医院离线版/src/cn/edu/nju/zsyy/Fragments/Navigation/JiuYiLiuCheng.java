package cn.edu.nju.zsyy.Fragments.Navigation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;

public class JiuYiLiuCheng extends Fragment
{
	private static final String TAG="JiuYiLiuCheng";
	private LinearLayout lcontent=null;
	private int lcontentid=Constants.JIUYILIUCHENG*0xFFFFFF;
	
//	private static final int NONE = 0;
//	private static final int DRAG = 1;
//	private static final int ZOOM = 2;
//
//	private int mode = NONE;
//	private float oldDist;
//	private Matrix matrix = new Matrix();
//	private Matrix savedMatrix = new Matrix();
//	private PointF start = new PointF();
//	private PointF mid = new PointF();
//
//	view.setOnTouchListener(new OnTouchListener() 
//	{
//		@Override
//		public boolean onTouch(View v, MotionEvent event)
//		{
//			ImageView view = (ImageView) v;
//			switch (event.getAction() & MotionEvent.ACTION_MASK)
//			{
//				case MotionEvent.ACTION_DOWN:
//					savedMatrix.set(matrix);
//					start.set(event.getX(), event.getY());
//					mode = DRAG;
//					break;
//				case MotionEvent.ACTION_UP:
//				case MotionEvent.ACTION_POINTER_UP:
//					mode = NONE;
//					break;
//				case MotionEvent.ACTION_POINTER_DOWN:
//					oldDist = spacing(event);
//					if (oldDist > 10f) 
//					{
//						savedMatrix.set(matrix);
//						midPoint(mid, event);
//						mode = ZOOM;
//					}
//					break;
//				case MotionEvent.ACTION_MOVE:
//					if (mode == DRAG) 
//					{
//						matrix.set(savedMatrix);
//						matrix.postTranslate(event.getX() - start.x, event.getY()
//								- start.y);
//					} 
//					else if (mode == ZOOM) 
//					{
//						float newDist = spacing(event);
//						if (newDist > 10f)
//						{
//							matrix.set(savedMatrix);
//							float scale = newDist / oldDist;
//							matrix.postScale(scale, scale, mid.x, mid.y);
//						}
//					}
//					break;
//			}
//
//			view.setImageMatrix(matrix);
//			return true;
//		}
//		
//		private float spacing(MotionEvent event) 
//		{
//			float x = event.getX(0) - event.getX(1);
//			float y = event.getY(0) - event.getY(1);
//			return FloatMath.sqrt(x * x + y * y);
//		}
//
//		private void midPoint(PointF point, MotionEvent event) 
//		{
//			float x = event.getX(0) + event.getX(1);
//			float y = event.getY(0) + event.getY(1);
//			point.set(x / 2, y / 2);
//		}
//	});
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		lcontent=new LinearLayout(getActivity());
		lcontent.setOrientation(LinearLayout.VERTICAL);
		lcontent.setId(lcontentid);
		
		ScrollView sv=new ScrollView(getActivity());
		LinearLayout.LayoutParams lllp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		sv.setLayoutParams(lllp);

		ViewGroup.LayoutParams vglp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		
		ImageView iv=new ImageView(getActivity());
		Drawable drawable=getResources().getDrawable(R.drawable.jiuyiliuchengtu);
		iv.setImageDrawable(drawable);
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		iv.setLayoutParams(vglp);
		sv.setHorizontalScrollBarEnabled(false);
		sv.setVerticalScrollBarEnabled(true);
		sv.addView(iv);
		
		lcontent.removeAllViews();
		lcontent.addView(sv);
		return lcontent;
	}
}

