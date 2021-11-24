//Provides Document Class (User Class)
package SolrTemplates;

import org.apache.solr.client.solrj.beans.Field;

import com.google.gson.Gson;

import java.time.OffsetDateTime;

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

    String userID;
    String userDateTime;
    String userName;
    String userScreenName;
    Long userFollowersCount;
    Long userFriendsCount;
    Boolean userVerified;
    String userProfileImageURL;
    String userProfileBannerURL;

    public SolrUser(String userID, String userDateTime, String userName, String userScreenName, Long userFollowersCount, Long userFriendsCount, Boolean userVerified, String userProfileImageURL, String userProfileBannerURL) {
        this.userID = userID;
        this.userDateTime = userDateTime;
        this.userName = userName;
        this.userScreenName = userScreenName;
        this.userFollowersCount = userFollowersCount;
        this.userFriendsCount = userFriendsCount;
        this.userVerified = userVerified;
        this.userProfileImageURL = userProfileImageURL;
        this.userProfileBannerURL = userProfileBannerURL;
    }

    public String getUserID() {
        return this.userID;
    }

    @Field("id")
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserDateTime() {
        return this.userDateTime;
    }

    public OffsetDateTime getUserParsedDateTime() {
        return OffsetDateTime.parse(this.userDateTime);
    }

    @Field("userDateTime")
    public void setUserDateTime(String userDateTime) {
        this.userDateTime = userDateTime;
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

    public Long getUserFollowersCount() {
        return this.userFollowersCount;
    }

    @Field("userFollowersCount")
    protected void setUserFollowersCount(Long userFollowersCount) {
        this.userFollowersCount = userFollowersCount;
    }

    public Long getUserFriendsCount() {
        return this.userFriendsCount;
    }

    @Field("userFriendsCount")
    protected void setUserFriendsCount(Long userFriendsCount) {
        this.userFriendsCount = userFriendsCount;
    }

    public Boolean getUserVerified() {
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
        return "userID : " + this.userID +
               "\n|--userDateTime : " + this.userDateTime +
               "\n|--userScreenName : " + this.userScreenName +
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
