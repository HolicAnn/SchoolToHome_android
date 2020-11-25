package com.example.easyreader;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link h_HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class h_HomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private WebView webView;
    private ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7,ib8;

    public h_HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment h_HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static h_HomePageFragment newInstance(String param1, String param2) {
        h_HomePageFragment fragment = new h_HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initHomePage();

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能1", Toast.LENGTH_SHORT).show();
            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能2", Toast.LENGTH_SHORT).show();
            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能3", Toast.LENGTH_SHORT).show();
            }
        });
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能4", Toast.LENGTH_SHORT).show();
            }
        });
        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能5", Toast.LENGTH_SHORT).show();
            }
        });
        ib6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能6", Toast.LENGTH_SHORT).show();
            }
        });
        ib7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能7", Toast.LENGTH_SHORT).show();
            }
        });
        ib8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能8", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void initHomePage() {

        ib1=getView().findViewById(R.id.imageButton);
        ib2=getView().findViewById(R.id.imageButton2);
        ib3=getView().findViewById(R.id.imageButton3);
        ib4=getView().findViewById(R.id.imageButton4);
        ib5=getView().findViewById(R.id.imageButton5);
        ib6=getView().findViewById(R.id.imageButton6);
        ib7=getView().findViewById(R.id.imageButton7);
        ib8=getView().findViewById(R.id.imageButton8);

        webView = getView().findViewById(R.id.webview);
        webView.loadUrl("http://news.cyol.com/node_67071.htm");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);


    }
}