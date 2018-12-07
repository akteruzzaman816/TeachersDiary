package me.assaduzzaman.teachersdiary.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Routine;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<Routine> routineList;


    public CustomAdapter(Context context, ArrayList<Routine> routineList) {
        this.context = context;
        this.routineList = routineList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.routine_row, viewGroup, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.subjectName.setText(routineList.get(i).getCourseName());
        myViewHolder.roomNumber.setText(routineList.get(i).getRoutineRoom());
        myViewHolder.subjectCode.setText(routineList.get(i).getCourseCode());
        myViewHolder.time.setText(routineList.get(i).getRoutineTime());
        myViewHolder.semester.setText(routineList.get(i).getRoutineSemester());
        myViewHolder.sectionName.setText(routineList.get(i).getRoutineSection());


    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView subjectCode,semester,time,subjectName,roomNumber,sectionName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectCode=itemView.findViewById(R.id.subjectId);
            semester=itemView.findViewById(R.id.semesterID);
            time=itemView.findViewById(R.id.subjectTime);
            subjectName=itemView.findViewById(R.id.subjectName);
            roomNumber=itemView.findViewById(R.id.roomNumber);
            sectionName=itemView.findViewById(R.id.sectionName);



        }


    }
}
