//Provides Document Class (User Class)
package SolrTemplates;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigInteger;

import org.apache.solr.client.solrj.beans.Field;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Document Class
 *
 * User: user_name, user_screen_name,
 *       user_followers_count, user_friends_count, user_verified,
 *       user_profile_image_url, user_profile_background_image_url
 *
 * Tweet: org_id, org_datetime, org_text,
 *        org_quote_count, org_reply_count, org_retweet_count,
 *        org_hashtags, org_user_metions, org_media_url, org_attached_links
 *
 */

public class SolrUser {

    String userName;
    String userScreenName;
    BigInteger userFollowersCount;
    BigInteger userFriendsCount;
    Boolean userVerified;
    String userProfileImageURL;
    String userProfileBannerURL;

    public SolrUser(String userName, String userScreenName, BigInteger userFollowersCount, BigInteger userFriendsCount, Boolean userVerified, String userProfileImageURL, String userProfileBannerURL) {
        this.userName = userName;
        this.userScreenName = userScreenName;
        this.userFollowersCount = userFollowersCount;
        this.userFriendsCount = userFriendsCount;
        this.userVerified = userVerified;
        this.userProfileImageURL = userProfileImageURL;
        this.userProfileBannerURL = userProfileBannerURL;
    }

    public String getUserScreenName() {
        return this.userScreenName;
    }

    @Field("userScreenName")
    protected void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public String getUserName() {
        return this.userName;
    }

    @Field("userName")
    protected void setUserName(String userName) {
        this.userName = userName;
    }

    public BigInteger getFollowersCount() {
        return this.userFollowersCount;
    }

    @Field("userFollowersCount")
    protected void setUserFollowersCount(BigInteger userFollowersCount) {
        this.userFollowersCount = userFollowersCount;
    }

    public BigInteger getFriendsCount() {
        return this.userFriendsCount;
    }

    @Field("userFriendsCount")
    protected void setUserFriendsCount(BigInteger userFriendsCount) {
        this.userFriendsCount = userFriendsCount;
    }

    public Boolean checkVerified() {
        return this.userVerified;
    }

    @Field("userVerified")
    protected void setUserVerified(Boolean userVerified) {
        this.userVerified = userVerified;
    }

    public String getUserProfileImageURL() {
        return this.userProfileImageURL;
    }

    @Field("userProfileImageURL")
    protected void setUserProfileImageURL(String userProfileImageURL) {
        this.userProfileImageURL = userProfileImageURL;
    }

    public String getUserProfileBannerURL() {
        return this.userProfileBannerURL;
    }

    @Field("userProfileBannerURL")
    protected void setUserProfileBannerURL(String userProfileBannerURL) {
        this.userProfileBannerURL = userProfileBannerURL;
    }

    public String toString() {
        return "userScreenName : " + this.userScreenName +
               "\n|--userName : " + this.userName +
               "\n|--userFollowersCount : " + this.userFollowersCount +
               "\n|--userFriendsCount : " + this.userFriendsCount +
               "\n|--userVerified : " + this.userVerified +
               "\n|--userProfileImageURL : " + this.userProfileImageURL +
               "\n|--userProfileBannerURL : " + this.userProfileBannerURL;
    }
    
    public String toJSON() throws Exception {
    	return new Gson().toJson(this);
    }


}
