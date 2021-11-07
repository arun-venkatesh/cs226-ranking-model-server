//Provides Document Class (Tweet Class)
package SolrTemplates;

import org.apache.solr.client.solrj.beans.Field;
import org.json.JSONObject;

import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.math.BigInteger;

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

public class SolrTweet {

    String tweetID;
    String userScreenName;
    String tweetDateTime;
    String tweetText;
    BigInteger tweetQuoteCount;
    BigInteger tweetReplyCount;
    BigInteger tweetRetweetCount;
    String tweetHashtags;
    String tweetUserMentions;
    String tweetMediaURL;
    String tweetAttachedLinks;

    public SolrTweet(String tweetID, String userScreenName, String tweetDateTime, String tweetText, BigInteger tweetQuoteCount, BigInteger tweetReplyCount, BigInteger tweetRetweetCount, String tweetHashtags, String tweetUserMentions, String tweetMediaURL, String tweetAttachedLinks) {
        this.tweetID = tweetID;
        this.userScreenName = userScreenName;
        this.tweetDateTime = tweetDateTime;
        this.tweetText = tweetText;
        this.tweetQuoteCount = tweetQuoteCount;
        this.tweetReplyCount = tweetReplyCount;
        this.tweetRetweetCount = tweetRetweetCount;
        this.tweetHashtags = tweetHashtags;
        this.tweetUserMentions = tweetUserMentions;
        this.tweetMediaURL = tweetMediaURL;
        this.tweetAttachedLinks = tweetAttachedLinks;
    }

    public String getTweetID() {
        return this.tweetID;
    }

    @Field("tweetID")
    protected void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public String getUserScreenName() {
        return this.userScreenName;
    }

    @Field("userScreenName")
    protected void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public String getTweetDateTime() {
        return this.tweetDateTime;
    }

    @Field("tweetDateTime")
    protected void setTweetDateTime(String tweetDateTime) {
        this.tweetDateTime = tweetDateTime;
    }

    public String getTweetText() {
        return this.tweetText;
    }

    @Field("tweetText")
    protected void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public BigInteger getTweetQuoteCount() {
        return this.tweetQuoteCount;
    }

    @Field("tweetQuoteCount")
    protected void setTweetQuoteCount(BigInteger tweetQuoteCount) {
        this.tweetQuoteCount = tweetQuoteCount;
    }

    public BigInteger getTweetReplyCount() {
        return this.tweetReplyCount;
    }

    @Field("tweetReplyCount")
    protected void setTweetReplyCount(BigInteger tweetReplyCount) {
        this.tweetReplyCount = tweetReplyCount;
    }

    public BigInteger getTweetRetweetCount() {
        return this.tweetRetweetCount;
    }

    @Field("tweetRetweetCount")
    protected void setTweetRetweetCount(BigInteger tweetRetweetCount) {
        this.tweetRetweetCount = tweetRetweetCount;
    }

    public String getTweetHashtags() {
        return this.tweetHashtags;
    }

    @Field("tweetHashtags")
    protected void setTweetHashtags(String tweetHashtags) {
        this.tweetHashtags = tweetHashtags;
    }

    public String getTweetUserMentions() {
        return this.tweetUserMentions;
    }

    @Field("tweetUserMentions")
    protected void setTweetUserMentions(String tweetUserMentions) {
        this.tweetUserMentions = tweetUserMentions;
    }

    public String getTweetMediaURL() {
        return this.tweetMediaURL;
    }

    @Field("tweetMediaURL")
    protected void setTweetMediaURL(String tweetMediaURL) {
        this.tweetMediaURL = tweetMediaURL;
    }

    public String getTweetAttachedLinks() {
        return this.tweetAttachedLinks;
    }

    @Field("tweetAttachedLinks")
    protected void setTweetAttachedLinks(String tweetAttachedLinks) {
        this.tweetAttachedLinks = tweetAttachedLinks;
    }

    public String toString() {
        return "tweetID : " + this.tweetID +
               "\n|--userScreenName : " + this.userScreenName+
               "\n|--tweetDateTime : " + this.tweetDateTime +
               "\n|--tweetText : " + this.tweetText +
               "\n|--tweetQuoteCount : " + this.tweetQuoteCount +
               "\n|--tweetReplyCount : " + this.tweetReplyCount +
               "\n|--tweetRetweetCount : " + this.tweetRetweetCount +
               "\n|--tweetHashtags : " + this.tweetHashtags +
               "\n|--tweetUserMentions : " + this.tweetUserMentions +
               "\n|--tweetMediaURL : " + this.tweetMediaURL +
               "\n|--tweetAttachedLinks : " + this.tweetAttachedLinks;
    }
    
    public String toJSON() throws Exception {
    	return new Gson().toJson(this);
    }

}
