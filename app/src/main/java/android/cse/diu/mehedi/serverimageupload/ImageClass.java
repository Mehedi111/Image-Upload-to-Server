package android.cse.diu.mehedi.serverimageupload;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dustu on 5/11/2018.
 */

public class ImageClass {

    @SerializedName("title")
    private String imageTitle;


    @SerializedName("image")
    private String image;

    @SerializedName("response")
    private String responseMsg;

    public String getResponseMsg() {
        return responseMsg;
    }
}
