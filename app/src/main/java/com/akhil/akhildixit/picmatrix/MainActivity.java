package com.akhil.akhildixit.picmatrix;


import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.akhil.akhildixit.picmatrix.Fragments.camera.CameraActivity;

import com.akhil.akhildixit.picmatrix.Fragments.NoteBookActivity;
import com.akhil.akhildixit.picmatrix.Fragments.SolutionActivity;
import com.akhil.akhildixit.picmatrix.chenupt.SpringIndicator;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;

import github.chenupt.springindicator.viewpager.ScrollerViewPager;

public class MainActivity extends AppCompatActivity {
    ScrollerViewPager scrollerViewPager;

    Button[] buttons;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollerViewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        final SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);

        /*Edits*/

        /*ImageView camera=new ImageView(this);
        ImageView solution=new ImageView(this);
        ImageView notebook=new ImageView(this);*/
        Button camera=new Button(this);
        Button solution=new Button(this);
        Button notebook=new Button(this);
        camera.setBackgroundResource(R.color.fbutton_color_transparent);
        solution.setBackgroundResource(R.color.fbutton_color_transparent);
        notebook.setBackgroundResource(R.color.fbutton_color_transparent);

        camera.setText(R.string.camera);
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        solution.setText(R.string.solution);
        notebook.setText(R.string.history);
        camera.setTypeface(fontAwesomeFont);
        solution.setTypeface(fontAwesomeFont);
        notebook.setTypeface(fontAwesomeFont);



        /*camera.setImageResource(R.drawable.camera);
        solution.setImageResource(R.drawable.bookopenpagevariant);
        notebook.setImageResource(R.drawable.factory);
*/
        /*int c= Color.parseColor("#3390FF");
        edit.setColorFilter(c);*/

        buttons=new Button[]{camera,solution,notebook};

        /**/




        PagerModelManager pagerModelManager=new PagerModelManager();


        pagerModelManager.addFragment(new CameraActivity(),"&#xf030;");
        pagerModelManager.addFragment(new SolutionActivity(),"&#xf24a;");
        pagerModelManager.addFragment(new NoteBookActivity(),"&#xf1da;");



        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), pagerModelManager);


        scrollerViewPager.setAdapter(adapter);
        scrollerViewPager.fixScrollSpeed();
        springIndicator.setViewPager(scrollerViewPager);

       /*Edits*/


       /**/



    }

    private List<String> getTitles(){
        ArrayList<String > arrayList=new ArrayList<>();
        arrayList.add("1");arrayList.add("2");arrayList.add("3");
        return (List)arrayList;

    }
}
