package com.example.miguelbcr.autofittextviewtrucated;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.fragment_list_lv);

        List<Object> list = new ArrayList<>();
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        listView.setAdapter(new ListAdapter(getActivity(), list));

        return view;
    }
}
