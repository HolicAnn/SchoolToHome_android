package com.example.easyreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String[] Author = new String[5];
    private String[] Name = new String[5];
    private String[] Time = new String[5];
    private String[] Imageids = new String[5];
    private String[] Professional = new String[5];
    private int[] Seq = {0, 1, 2, 3, 4};
    private String[] Hot = new String[5];
    private String[] Memo = new String[5];
    private String[] Id = new String[5];
    private int[][] Images = {{R.mipmap.action_31, R.mipmap.action_32, R.mipmap.action_33}, {R.mipmap.action_11, R.mipmap.action_12, R.mipmap.action_13}, {R.mipmap.action_21, R.mipmap.action_22, R.mipmap.action_23}, {R.mipmap.action_01, R.mipmap.action_02, R.mipmap.action_03}, {R.mipmap.action_41, R.mipmap.action_42, R.mipmap.action_43}};
    private int[] Photo = {R.mipmap.action_photo0, R.mipmap.action_photo1, R.mipmap.action_photo2, R.mipmap.action_photo3, R.mipmap.action_photo4};
    private ListView action_list;
    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//

    public ActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityFragment newInstance(String param1, String param2) {
        ActivityFragment fragment = new ActivityFragment();
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
        return inflater.inflate(R.layout.h_action, container, false);
    }

    public void setMenuVisibility(boolean menuVisible){
        super.setMenuVisibility(menuVisible);
        if(this.getView()!=null){
            this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
        }
    }

    private void getJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //URL url = new URL("http://172.20.10.2:3000/user/student/action_list");
                    URL url = new URL(getString(R.string.Server_IP_Port) + "/user/student/action_list");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isw = new InputStreamReader(in);
                    BufferedReader br = new BufferedReader(isw);
                    StringBuilder sb = new StringBuilder();
                    String result = "";
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        result = result + line;
                    }
                    br.close();
                    //System.out.println("--------------------------------------------------------------------------");
                    try {
                        JSONObject jsonObjectALL = new JSONObject(result);
                        JSONArray jsonArray = jsonObjectALL.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String _id = jsonObject.optString("_id", null);
                            Id[i] = _id;
                            String name = "  " + jsonObject.optString("name", null);
                            Name[i] = name;
                            String author = "  " + jsonObject.optString("author", null);
                            Author[i] = author;
                            String professional = "  " + jsonObject.optString("professional", null);
                            Professional[i] = professional;
                            String hot = "热度：" + jsonObject.optString("hot", null);
                            Hot[i] = hot;
                            String memo = jsonObject.optString("memo", null);
                            Memo[i] = memo;
                            String created_time = jsonObject.optString("created_time", null);
                            Time[i] = created_time;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onStart(){
        super.onStart();
        getJson();
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //getActivity().setContentView(R.layout.h_action);
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Name.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("action_name", Author[i]);
            item.put("action_title", Name[i]);
            item.put("action_photo", Photo[i]);
            item.put("a_image_21", Images[i][0]);
            item.put("a_image_22", Images[i][1]);
            item.put("a_image_23", Images[i][2]);
            item.put("action_professional", Professional[i]);
            item.put("action_hot", Hot[i]);
            listitem.add(item);
        }
        SimpleAdapter simplead = new SimpleAdapter(getActivity(), listitem,
                R.layout.h_action_item, new String[]{"action_name", "action_title", "action_photo", "a_image_21", "a_image_22", "a_image_23", "action_professional", "action_hot"},
                new int[]{R.id.action_name, R.id.action_title, R.id.action_photo, R.id.a_image_21, R.id.a_image_22, R.id.a_image_23, R.id.action_professional, R.id.action_hot});
        action_list = getView().findViewById(R.id.action_l1);
        action_list.setAdapter(simplead);
        final Intent intent = new Intent(getActivity(), action_detail.class);

        action_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", Seq[position]);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", Seq[position]);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", Seq[position]);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", Seq[position]);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", Seq[position]);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}