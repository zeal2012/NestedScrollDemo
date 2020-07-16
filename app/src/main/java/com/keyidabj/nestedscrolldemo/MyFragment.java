package com.keyidabj.nestedscrolldemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyFragment extends Fragment {

    private RecyclerView mRecyclerview;

    int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt("index");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_fragment, null);

        mRecyclerview = rootView.findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> objects = new ArrayList<>();
        for (int i = 0; i<100; i++){
            objects.add("pager" + index + "--" + i);
        }
        MyAdapter myAdapter = new MyAdapter(getContext(), objects);
        mRecyclerview.setAdapter(myAdapter);

        return rootView;
    }

}
