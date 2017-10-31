package fr.hdb.artibip.presentation.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * {@link ViewPager} personnalise dont la direction du swipe est horizontale et
 * peut etre desactive
 * 
 * @author Solo
 * 
 */
public class HorizontalNonSwipeableViewPager extends ViewPager {

	private boolean isSwipeEnabled;

	public HorizontalNonSwipeableViewPager(Context context) {
		super(context);
		isSwipeEnabled = false;
	}

	public HorizontalNonSwipeableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		isSwipeEnabled = false;
	}

	/**
	 * {@link ViewPager#onTouchEvent(MotionEvent)}
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.isSwipeEnabled) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	/**
	 * {@link ViewPager#onInterceptTouchEvent(MotionEvent)}
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.isSwipeEnabled) {
			return super.onInterceptTouchEvent(event);
		}
		return false;
	}

	/**
	 * Activer/desactiver le swipe du {@link ViewPager}
	 * 
	 * @param isSwipeEnabled
	 *            true pour activer le swipe, false pour le desactiver
	 */
	public void setPagingEnabled(boolean isSwipeEnabled) {
		this.isSwipeEnabled = isSwipeEnabled;
	}

}
