package com.akhil.akhildixit.picmatrix.solutions_rajesh.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raajesharunachalam on 3/25/17.
 */

public class Image {
    @SerializedName("src")
    private String sourceLink;

    public String getSourceLink(){
        return sourceLink;
    }

}
