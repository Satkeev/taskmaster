package com.satkeev.github.taskmaster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "body";
    private static final String ARG_PARAM3 = "state";
    private static final String ARG_PARAM4 = "key";
    private static final String ARG_PARAM5 = "name";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private String mBody;
    private String mState;
    private String mKey;
    private String mName;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Title 1.
     * @param body Body 2.
     *   @param state State 3.
     *   @param key Key 4.
     *    @param name Name 5.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String title, String body, String state, String key, String name) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, body);
        args.putString(ARG_PARAM3, state);
        args.putString(ARG_PARAM4, key);
        args.putString(ARG_PARAM5, name);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);
            mBody = getArguments().getString(ARG_PARAM2);
            mState = getArguments().getString(ARG_PARAM3);
            mKey = getArguments().getString(ARG_PARAM4);
            mName = getArguments().getString(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }
}