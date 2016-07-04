package com.phoenix.soft.agenda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.phoenix.soft.agenda.resource.BaseActivity;
import com.phoenix.soft.agenda.resource.MyAdapter;
import com.phoenix.soft.agenda.resource.ResAccount;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView list;
    private List<ResAccount> accList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private String myDataset[] = {"APPLE","BANANA","ORANGE","PEAR","STRAWBERRY"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.content_main);
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        list = (ListView) findViewById(R.id.list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        initAccList();
        initListView();
/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent("com.phoenix.soft.agenda.TOAST");
                sendBroadcast(intent);
            }
        });*/

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.content_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Activity2.class);
            this.startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    public void initListView() {
        AccAdapter adapter = new AccAdapter(MainActivity.this, R.layout.list_acc, accList);
        list.setAdapter(adapter);
    }

    public void initAccList() {
        ResAccount bank = new ResAccount("Bank",100);
        accList.add(bank);
        ResAccount cash = new ResAccount("CASH",200);
        accList.add(cash);

    }
}



class AccAdapter extends ArrayAdapter<ResAccount>{
    private int resourceId;

    public AccAdapter(Context context, int resource,List<ResAccount> object) {
        super(context, resource,object);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResAccount acc = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView tv_acc = (TextView) view.findViewById(R.id.tv_list_acc);
        TextView tv_bal = (TextView) view.findViewById(R.id.tv_list_bal);
        tv_acc.setText(acc.getName());
        tv_bal.setText(acc.getBal()+"");

        return view;
    }
}
