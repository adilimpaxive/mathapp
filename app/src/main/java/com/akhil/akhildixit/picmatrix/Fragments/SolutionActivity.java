package com.akhil.akhildixit.picmatrix.Fragments;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.akhil.akhildixit.picmatrix.R;
import com.akhil.akhildixit.picmatrix.solutions_rajesh.PackageManagerUtils;
import com.akhil.akhildixit.picmatrix.solutions_rajesh.data.APIKey;
import com.akhil.akhildixit.picmatrix.solutions_rajesh.data.Converter;
import com.akhil.akhildixit.picmatrix.solutions_rajesh.data.Subpod;
import com.akhil.akhildixit.picmatrix.solutions_rajesh.data.WolframAdapter;
import com.akhil.akhildixit.picmatrix.solutions_rajesh.data.WolframAlphaAsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Akhil Dixit on 12/19/2017.
 */

public class SolutionActivity extends Fragment {
    private static final String BASE_URL = "http://api.wolframalpha.com/v2/query?input=";
    private static final String FORMAT = "&format=image,plaintext";
    private static final String OUTPUT = "&output=JSON";
    private static final String APP_ID = "&appid=" + APIKey.ID;
    private static final String CLOUD_VISION_API_KEY = "AIzaSyBJZfFl4WOXximth-4cizevOqbvtVYSKEQ";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    public static final String TO_SOLUTIONS_LABEL = "Solutions";
    private static final String TO_SOLUTIONS = "Let's go to the solutions!!!!!!";
    public static final String INPUT_LABEL = "Input";
    String ocrResult;

    RecyclerView solutionList;
    ArrayList<Subpod> subpods;
    WolframAdapter adapter;
    FloatingActionButton fab;
    String result;
    ProgressBar progressBar;
    TextView textView;

    ShareActionProvider mShareActionProvider;
    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        subpods = new ArrayList<>();



}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.solution_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView=view.findViewById(R.id.solution_click);
        textView.setVisibility(View.VISIBLE);
        solutionList=view.findViewById(R.id.solution_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        fab=view.findViewById(R.id.solution_fab);
        progressBar=view.findViewById(R.id.solution_progressbar);
        solutionList.setLayoutManager(layoutManager);
        listeners(view);
        adapter = new WolframAdapter(getContext(), subpods,progressBar,solutionList);
        solutionList.setAdapter(adapter);

        fabAnimation();


    }
    public void listeners(View view)
    {
      fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Uri uri=Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"PicMatrix/pic.jpg"));
              try {
                  textView.setVisibility(View.GONE);
                  searchOcr(uri);
              } catch (IOException e) {
                  e.printStackTrace();
              }

          }
      });
    }

    /*Maths*/

    public void getResults(String result)
    {
        WolframAlphaAsyncTask wolframTask = new WolframAlphaAsyncTask(adapter, getContext(),progressBar,solutionList);
        String input = Converter.convertMathToWolfram(result);
        input = input.trim();
        String urlString = createFullURL(input);
        Log.e("URL:", urlString);
        wolframTask.execute(urlString);
    }
    public String createFullURL(String input) {
        String fullURL = BASE_URL + input + FORMAT + OUTPUT + APP_ID;
        return fullURL;
    }

    /*Maths end*/

    /*Animation*/
    public void fabAnimation()
    {
       final ScaleAnimation growAnim = new ScaleAnimation(1.0f, 1.18f, 1.0f, 1.18f);
        final  ScaleAnimation shrinkAnim = new ScaleAnimation(1.18f, 1.0f, 1.18f, 1.0f);
        growAnim.setDuration(1000);
        shrinkAnim.setDuration(1000);

        fab.setAnimation(growAnim);
        growAnim.start();

        growAnim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation){}

            @Override
            public void onAnimationRepeat(Animation animation){}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                fab.setAnimation(shrinkAnim);
                shrinkAnim.start();
            }
        });
        shrinkAnim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation){}

            @Override
            public void onAnimationRepeat(Animation animation){}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                fab.setAnimation(growAnim);
                growAnim.start();
            }
        });
    }
    /**/

    /*OCR*/
    public void searchOcr(Uri uri) throws IOException {
        Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
        callCloudVision(bitmap);
    }
    @SuppressLint("StaticFieldLeak")
    private void callCloudVision(final Bitmap bitmap) throws IOException {

        // Do the real work in an async task, because we need to use the network anyway
        new AsyncTask<Object, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected String doInBackground(Object... params) {
                try {
                    Log.e("Line 255:", "This line has been executed");
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();


                    VisionRequestInitializer requestInitializer =
                            new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                                /**
                                 * We override this so we can inject important identifying fields into the HTTP
                                 * headers. This enables use of a restricted cloud platform API key.
                                 */
                                @Override
                                protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                                        throws IOException {
                                    super.initializeVisionRequest(visionRequest);

                                    String packageName =getActivity().getPackageName();
                                    visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                                    String sig = PackageManagerUtils.getSignature(getActivity().getPackageManager(), packageName);

                                    visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                                }
                            };

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);

                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        // Add the image
                        Image base64EncodedImage = new Image();
                        // Convert the bitmap to a JPEG
                        // Just in case it's a format that Android understands but Cloud Vision
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

                        // Base64 encode the JPEG
                        base64EncodedImage.encodeContent(imageBytes);
                        annotateImageRequest.setImage(base64EncodedImage);

                        // add the features we want
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature textDetection = new Feature();
                            textDetection.setType("TEXT_DETECTION");
                            add(textDetection);
                        }});

                        // Add the list of one thing to the request
                        add(annotateImageRequest);
                    }});

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    // Due to a bug: requests to Vision API containing large images fail when GZipped.
                    annotateRequest.setDisableGZipContent(true);

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response);

                } catch (GoogleJsonResponseException e) {
                } catch (IOException e) {

                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
                ocrResult = result.trim();
                Log.e("Results:::", result);
                progressBar.setVisibility(View.GONE);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getContext());
                edittext.setText(ocrResult);
                alert.setCancelable(true);
                alert.setMessage("Looks like we got something!!!");
                alert.setTitle("EXPRESSION");
                alert.setView(edittext);

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getResults(edittext.getText().toString());

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        }.execute();
    }

    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        String message = "";

        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();
        if (labels != null && !labels.isEmpty()) {
            message += String.format(Locale.US, "%s", labels.get(0).getDescription());
        } else {
            message += "nothing";
        }

        return message;
    }
    /*OCr end*/


}