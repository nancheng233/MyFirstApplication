package com.jnu.student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jnu.student.view.ReadBookView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button myButton;
    private TextView myTextView;
    private ReadBookView readBookView;
    private int my_books = 0;
    private Handler handler;

    public ReadBookFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReadBookFragment newInstance() {
        ReadBookFragment fragment = new ReadBookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_read_book, container, false);

        myButton = rootView.findViewById(R.id.my_read_book_button);
        myTextView = rootView.findViewById(R.id.my_read_book_text_view);
        readBookView = rootView.findViewById(R.id.my_read_book_view);

        myButton.setOnClickListener(v -> readBookView.setCount(0));

        handler = new Handler(Looper.getMainLooper());

        new Thread(() -> {
            while (true) {
                int parameter = readBookView.getCount();
                handler.post(() -> myTextView.setText(String.format(getString(R.string.my_books), parameter)));

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return rootView;
    }
}