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

public class Display_state extends BaseDrawerActivity {

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
        getLayoutInflater().inflate(R.layout.activity_display_state, frameLayout);
        setTitle("View State");
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
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    private List<ExampleModel> getAllData()
    {

        final ProgressDialog pd = new ProgressDialog(Display_state.this);
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


                        JSONArray jsonArray=obj.getJSONArray("state");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String l_id = jsonObject1.getString("STATE_ID").trim();
                                String lname = jsonObject1.getString("STATE_NAME");



                                ExampleModel current = new ExampleModel();
                                current.cname = l_id;
                                current.sname = lname;

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


                    return requestHandler.sendPostRequest(URLs.URL_VIEWSTATE, params);



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
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_viewstate,parent,false);

            return new MyviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerAdapter.MyviewHolder holder, final int position) {

            final ExampleModel current = list.get(position);

            holder.number.setText("State: "+current.getSname().toUpperCase());

















//



        }



        @Override
        public int getItemCount() {
            return list.size();
        }


         public class MyviewHolder extends RecyclerView.ViewHolder {

            private TextView name , number;




            public MyviewHolder(@NonNull View itemView) {
                super(itemView);


                number = itemView.findViewById(R.id.state_name);




            }


        }
    }











}
