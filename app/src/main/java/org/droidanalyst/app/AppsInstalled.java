package org.droidanalyst.app;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class AppsInstalled extends Fragment {


    OnAppSelectListener oal;
    public interface OnAppSelectListener{
        public void OnAppSelected(String path);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            oal = (OnAppSelectListener)activity;
        }
        catch (ClassCastException e) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_apps_installed, null);
        ((MainActivity)getActivity()).setActionBarTitle("Installed Apps");

        final PackageManager pm = getActivity().getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages =  pm.getInstalledApplications(PackageManager.GET_META_DATA);
        final ArrayList<String> installed_apps=new ArrayList<>();
        final ArrayList<String> installed_apps_url=new ArrayList<>();
        for (ApplicationInfo packageInfo : packages) {
            Log.d("IP", "Installed package :" + packageInfo.packageName);
            installed_apps.add(packageInfo.packageName);
            Log.d("FP", "Apk file path:" + packageInfo.sourceDir);
            installed_apps_url.add(packageInfo.sourceDir);
        }
        ListView lv = (ListView)root.findViewById(R.id.apps);

        ArrayAdapter ad = new ArrayAdapter(getActivity(), R.layout.list_item, installed_apps);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = installed_apps_url.get(position);
                Toast.makeText(getActivity(),"URL"+path,Toast.LENGTH_SHORT).show();
                oal.OnAppSelected(path);

            }
        });
        return root;

    }




}
