package com.example.gbts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Display_Nearby extends BaseDrawerActivity {

    List<ExampleModel> list;
    RecyclerView recyclerView;
    RecyclerAdapter radapter;
    User user;
    String uid, flag;
    Button btn;
    List<Map<String, Object>> rData = new ArrayList<>();
    String cid , resulte;



    public List<String>selectedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_display_nearby, frameLayout);
        setTitle("View Near By");
        user = SharedPrefManager.getInstance(this).getUser();
        uid = String.valueOf(user.getLOGIN_ID());



        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        radapter = new RecyclerAdapter(getAllData(),this);
        recyclerView.setAdapter(radapter);




    }


    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(6).setChecked(true);
    }

    private List<ExampleModel> getAllData()
    {

        final ProgressDialog pd = new ProgressDialog(Display_Nearby.this);
        pd.setMessage("loading");
        pd.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        pd.dismiss();
                    }
                }, 2000);

        final List<ExampleModel> data=new ArrayList<>();

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        // JSONObject userJson = obj.getJSONObject("vehicle");


                        JSONArray jsonArray=obj.getJSONArray("data");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String l_id = jsonObject1.getString("NEARBY_ID").trim();
                                String LOCATION_NAME = jsonObject1.getString("LOCATION_NAME");
                                String LATITUDE = jsonObject1.getString("LATITUDE");
                                String LONGITUDE = jsonObject1.getString("LONGITUDE");
                                String POLICE_STATION = jsonObject1.getString("POLICE_STATION");
                                String FIRE_STATION = jsonObject1.getString("FIRE_STATION");
                                String HOSPITAL = jsonObject1.getString("HOSPITAL");




                                ExampleModel current = new ExampleModel();
                                current.LOCATION_NAME = LOCATION_NAME;
                                current.LATITUDE = LATITUDE;
                                current.LONGITUDE = LONGITUDE;
                                current.POLICE_STATION = POLICE_STATION;
                                current.FIRE_STATION = FIRE_STATION;
                                current.HOSPITAL = HOSPITAL;

                                data.add(current);
                            }



                        radapter.notifyDataSetChanged();



                    } else {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();



                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("uid",uid);


                    return requestHandler.sendPostRequest(URLs.URL_MNEARBY, params);



                //returing the response


            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




        return data;
    }



    private  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {


        List<ExampleModel> list;
        Context context;
        public List<String>selectedValues;



        public RecyclerAdapter(List<ExampleModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_viewnb,parent,false);

            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerAdapter.MyviewHolder holder, final int position) {

            final ExampleModel current = list.get(position);

            holder.lname.setText("location: "+current.getLOCATION_NAME().toUpperCase());
            holder.lat.setText("latitude: "+current.getLATITUDE().toUpperCase());
            holder.longi.setText("longitude: "+current.getLONGITUDE().toUpperCase());
            holder.hs.setText("hospital: "+current.getHOSPITAL().toUpperCase());
            holder.ps.setText("police station: "+current.getPOLICE_STATION().toUpperCase());
            holder.fs.setText("fire station: "+current.getFIRE_STATION().toUpperCase());

















//



        }



        @Override
        public int getItemCount() {
            return list.size();
        }


         public class MyviewHolder extends RecyclerView.ViewHolder {

            private TextView lname , lat , longi , hs , ps , fs;




            public MyviewHolder(@NonNull View itemView) {
                super(itemView);


                lname = itemView.findViewById(R.id.tv_lname);
                lat = itemView.findViewById(R.id.tv_lat);
                longi = itemView.findViewById(R.id.tv_long);
                hs = itemView.findViewById(R.id.tv_hs);
                ps = itemView.findViewById(R.id.tv_ps);
                fs = itemView.findViewById(R.id.tv_fs);




            }


        }
    }











}
