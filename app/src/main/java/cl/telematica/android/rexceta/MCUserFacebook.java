package cl.telematica.android.rexceta;

/**
 * Created by Jesi on 06-12-16.
 */
import com.facebook.Profile;

import org.json.JSONObject;

public class MCUserFacebook {

    public String id;
    public String token;

    public String firstname;
    public String lastname;
    public String fullname;
    public String email;
    public String picture;

    public void fill(JSONObject data) {
        try {
            this.fullname = data.getString("name");

            if(data.has("first_name")){
                this.firstname = data.getString("first_name");
                System.out.println(firstname);
            }

            if(data.has("last_name")){
                this.lastname = data.getString("last_name");
                System.out.println(lastname);
            }

            /*String birthday = "";
            if (jsonObject.has("birthday")) {
                birthday = jsonObject.getString("birthday");
            }
            String gender = "";
            if (jsonObject.has("gender")) {
                gender = jsonObject.getString("gender");
            }
            String city = "";
            if (jsonObject.has("hometown")) {
                city = jsonObject.getJSONObject("hometown").getString("name");
            }*/

            if (data.has("email")) {
                this.email = data.getString("email");
            }

            this.picture = "http://graph.facebook.com/" + this.id + "/picture?type=large";
            //String fbId = jsonObject.getString("id");

        } catch (Exception e) {

        }
    }

    /**
     * Get profile picture
     * @param width int
     * @param height int
     * @return String
     */
    public String getProfilePictureUri(int width, int height) {
        Profile profile = Profile.getCurrentProfile();
        if(profile != null){
            profile.getProfilePictureUri(width, height);
        }

        return null;
    }
}