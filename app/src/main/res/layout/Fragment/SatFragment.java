package com.example.akteruzzaman.tejgaoncollege.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akteruzzaman.tejgaoncollege.R;
import com.example.akteruzzaman.tejgaoncollege.model.Routine;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by AKTERUZZAMAN on 10/25/2016.
 */
public class SatFragment extends Fragment {

    View v;
    RecyclerView recyclerView;
    DatabaseReference mdatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.sat,container,false);

        recyclerView= (RecyclerView) v.findViewById(R.id.recyclerView_sat);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        mdatabase= FirebaseDatabase.getInstance().getReference().child("cse1st").child("saturday");



        return v;
    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Routine,ViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Routine,ViewHolder>(
                Routine.class,
                R.layout.routine_list,
                ViewHolder.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, final Routine routine, int position) {

                final String post_key=getRef(position).getKey();

//                viewHolder.setTitle(model.getTitle());
//                viewHolder.setUser(model.getUser_name());
//                viewHolder.setImage(getActivity(),model.getImage());
                final ImageView bell= (ImageView) viewHolder.mView.findViewById(R.id.bell_icon);
                viewHolder.setSir(routine.getSir());
                viewHolder.setSub(routine.getSub());
                viewHolder.setTime(routine.getTime());


            }



        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        View mView;



        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }




        public void setSub(String sub)
        {
            TextView Sir_name= (TextView) mView.findViewById(R.id.Sub111);
            Sir_name.setText(sub);
        }


        public void setSir(String sir)
        {
            TextView sub_name= (TextView) mView.findViewById(R.id.sir111);
            sub_name.setText(sir);
        }

        public void setTime(String time)
        {
            TextView Time= (TextView) mView.findViewById(R.id.time111);
            Time.setText(time);
        }




    }



}
