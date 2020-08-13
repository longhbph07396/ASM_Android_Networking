package com.hblong.assigment.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hblong.assigment.R;

public class BaseFragment extends Fragment {
    protected String linkEmpty(String[] strings) {
        for (int i = strings.length - 1; i >= 0; i--) {
            if (strings[i] != null) {
                return strings[i];
            }
        }
        return null;
    }

    public void back(int aniIn, int aniOut, Fragment fragment, int idFrame) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(aniIn, aniOut);

        ft.replace(idFrame, fragment, "detailFragment");

        ft.commit();
    }
}
