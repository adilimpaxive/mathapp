package com.akhil.akhildixit.picmatrix.solutions_rajesh.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akhil.akhildixit.picmatrix.Database.Setter;
import com.akhil.akhildixit.picmatrix.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by raajesharunachalam on 3/25/17.
 */

public class WolframAdapter extends RecyclerView.Adapter<WolframAdapter.SolutionViewHolder>{
    Context context;
    Pod[] pods;
    ArrayList<Subpod> results;
    int[][] targets;
    ProgressBar progressBar;
    RecyclerView recyclerView;


    public static final double NEXUS_DP_WIDTH = 360.0;
    public static final int NEXUS_IMAGE_WIDTH = 320;

    public static final double KINDLE_DP_WIDTH = 600.0;
    public static final int KINDLE_IMAGE_WIDTH = 400;


    public WolframAdapter(Context context, ArrayList<Subpod> subpods, ProgressBar progressBar,RecyclerView recyclerView){
        this.context = context;
        this.results = subpods;
        targets = new int[0][0];
        pods = new Pod[0];
        this.progressBar=progressBar;
        this.recyclerView=recyclerView;
    }

    public ArrayList<Subpod> getResults(){
        return results;
    }

    public void setPods(Pod[] newPods){
        pods = newPods;
    }

    public void setResults(ArrayList<Subpod> newResults){
        this.results = newResults;
        PicassoAsyncTask imageTask = new PicassoAsyncTask(context, this,progressBar,recyclerView);
        imageTask.execute(results);
    }

    public void setTargets(int[][] newTargets){
        targets = newTargets;
    }

    @Override
    public SolutionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        int movieItemLayout = R.layout.solution_list_item;
        boolean shouldAttachToParent = false;
        View view = inflater.inflate(movieItemLayout, parent, shouldAttachToParent);
        return new SolutionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SolutionViewHolder holder, int position) {
        Subpod currentSubpod = results.get(position);
        if(pods.length > 0){
            for(Pod pod : pods){
                for(Subpod subpod : pod.getSubpods()){
                    if(subpod.equals(currentSubpod)){
                        holder.itemLabel.setText(pod.getTitle());
                        holder.solutionText.setText(results.get(position).getPlainText());
                        if (pod.getTitle().equals("Input"))
                        {
                            Setter.input=subpod.getPlainText();

                        }else
                        if (pod.getTitle().equals("Alternate form"))
                        {
                            Setter.alternativeform=subpod.getPlainText();
                        }else
                        if (pod.getTitle().equals("Solution"))
                        {
                            Setter.solution=subpod.getPlainText();
                        }

                    }
                }
            }
        }

        if(targets.length > 0){
            String currentURL = results.get(position).getImage().getSourceLink();
            Picasso.with(context).load(currentURL).resize(targets[position][0], targets[position][1]).into(holder.solutionImage);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class SolutionViewHolder extends RecyclerView.ViewHolder{
        public ImageView solutionImage;
        public TextView itemLabel;
        public TextView solutionText;

        public SolutionViewHolder(View itemView) {
            super(itemView);
            solutionImage = (ImageView) itemView.findViewById(R.id.solution_image);
            itemLabel = (TextView) itemView.findViewById(R.id.item_label);
            solutionText=itemView.findViewById(R.id.solution_text);
        }
    }

}
