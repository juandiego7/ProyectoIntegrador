package co.edu.udea.notasudea.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 30/06/2013
 */
public class PagerAdapter extends FragmentPagerAdapter {
 
	private List<Fragment> fragments;
	
	public PagerAdapter(FragmentManager fm){
        super(fm);	
	}

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }
 
    @Override
    public int getCount() {
        return this.fragments.size();
    }
    
}