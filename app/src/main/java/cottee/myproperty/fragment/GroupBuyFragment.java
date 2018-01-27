package cottee.myproperty.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cottee.myproperty.R;

/**
 * Created by Administrator on 2018/1/16.
 */

public class GroupBuyFragment extends Fragment {
    private View inflate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_groupby, null);


        return inflate;
    }
}
