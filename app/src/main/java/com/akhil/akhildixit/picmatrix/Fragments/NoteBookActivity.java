package com.akhil.akhildixit.picmatrix.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akhil.akhildixit.picmatrix.Database.Setter;
import com.akhil.akhildixit.picmatrix.Database.Sql;
import com.akhil.akhildixit.picmatrix.Pojo.Color;
import com.akhil.akhildixit.picmatrix.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

/**
 * Created by Akhil Dixit on 12/19/2017.
 */

public class NoteBookActivity extends Fragment {
    ArrayList<Setter> arrayList=new ArrayList<>();
    ViewGroup insert;
    ViewGroup scheduleViewGroup;

    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notebook_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Sql sql=new Sql(getContext());
        arrayList=sql.getDataFromTable();

        addViews(view);
    }
    public void addViews(View view)
    {
        Log.e("It runs","k");
        if (arrayList!=null) {


            Log.e("Inside","arra");
            LayoutInflater inflater= (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);/*Set height and width here of view*/
            //  lp.setMargins(0,10,5,0);
            //lp.setMargins(5,10,1,100);

            lp.setMargins(8,10,12,40);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.noteBook_linearLayout);
            linearLayout.removeAllViews();
            YoYo.with(Techniques.FadeIn).duration(1000).playOn(linearLayout);
            for (int i = 0; i < arrayList.size(); i++) {

                int position= Color.position;


                insert = (ViewGroup) view.findViewById(R.id.noteBook_linearLayout);
              final  View view1 = inflater.inflate(R.layout.notebook_customlists, null);
              final   Setter setter=arrayList.get(i);
                GradientDrawable gd = new GradientDrawable();

                if (position== Color.color.length)
                {
                  Color.position=0;
                    position=0;
                }
                gd.setColor(Color.color[position]);
                //  gd.setColor(Color.RED);
                gd.setCornerRadius(25);
                view1.setBackground(gd);
                Color.position++;
                TextView input=view1.findViewById(R.id.notebook_input);
                TextView solution=view1.findViewById(R.id.notebook_solution);
                TextView alternate=view1.findViewById(R.id.notebook_alternate_solution);
                final Button hideshow=view1.findViewById(R.id.notebook_hide_show);

                hideshow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      final ViewGroup scheduleViewGroup = (ViewGroup) view1.findViewById(R.id.notebook_image_add);
                        LinearLayout showSchedule=(LinearLayout)view1.findViewById(R.id.notebook_image);

                        LayoutInflater inflater= (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        if (showSchedule.getVisibility()==showSchedule.GONE)

                        {
                            YoYo.with(Techniques.SlideInLeft).duration(700).repeat(0).playOn(showSchedule);
                            TransitionManager.beginDelayedTransition(insert);
                            showSchedule.setVisibility(View.VISIBLE);
                            hideshow.setText(R.string.arrowup);
                            View view1 = inflater.inflate(R.layout.notebook_imageview, null);
                            ImageView imageView=view1.findViewById(R.id.notebook_dynamic_image);
                            imageView.setImageBitmap(setter.bitmapnon);

                            scheduleViewGroup.addView(view1,0,lp);

                    }
                        else {
                            YoYo.with(Techniques.SlideOutLeft).duration(700).repeat(0).playOn(showSchedule);
                            TransitionManager.beginDelayedTransition(insert);
                            showSchedule.setVisibility(View.GONE);
                            hideshow.setText(R.string.arrowdown);
                            scheduleViewGroup.removeAllViews();
                        }

                }});

                insert.addView(view1,lp);
                input.setText(setter.inputnon);
                solution.setText(setter.solutionnon);
                alternate.setText(setter.alternativeformnon);
                fontAwesome(view1);



            }}
    }
    public void fontAwesome(View view)
    {
        Typeface fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Button showhode=view.findViewById(R.id.notebook_hide_show);
        showhode.setTypeface(fontAwesomeFont);

    }
}
