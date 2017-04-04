package info.androidhive.firebase;

/**
 * Created by ehab- on 3/31/2017.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Config extends  Fragment{
    public Fragment_Config() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Child Tracker");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_config, container, false);



        return view;
    }

}
