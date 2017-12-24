package com.akhil.akhildixit.picmatrix.solutions_rajesh.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raajesharunachalam on 3/25/17.
 */

public class WolframResult {
    @SerializedName("queryresult")
    private QueryResult result;

    public QueryResult getResult(){
        return result;
    }
}
