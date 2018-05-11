package android.cse.diu.mehedi.serverimageupload;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dustu on 5/11/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("imageUpload/upload_image.php")
    Call<ImageClass> uploadsImage(@Field("title") String imageTitle, @Field("image") String image);
}
