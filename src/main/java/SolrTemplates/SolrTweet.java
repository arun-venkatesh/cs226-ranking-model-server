//Provides Document Class (Tweet Class)
package SolrTemplates;

import org.apache.solr.client.solrj.beans.Field;

import com.google.gson.Gson;

import java.time.OffsetDateTime;
import java.util.List;

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
    String userID;
    String tweetDateTime;
    String tweetText;
    Long tweetFavoriteCount;
    Long tweetQuoteCount;
    Long tweetReplyCount;
    Long tweetRetweetCount;
    List<String> tweetHashtags;
    List<String> tweetUserMentions;
    List<String> tweetMediaURL;
    List<String> tweetAttachedLinks;

    public SolrTweet(String tweetID, String userID, String tweetDateTime, String tweetText, Long tweetFavoriteCount, Long tweetQuoteCount, Long tweetReplyCount, Long tweetRetweetCount, List<String> tweetHashtags, List<String> tweetUserMentions, List<String> tweetMediaURL, List<String> tweetAttachedLinks) {
        this.tweetID = tweetID;
        this.userID = userID;
        this.tweetDateTime = tweetDateTime;
        this.tweetText = tweetText;
        this.tweetFavoriteCount = tweetFavoriteCount;
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

    @Field("id")
    protected void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public String getUserID() {
        return this.userID;
    }

    @Field("userID")
    protected void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTweetDateTime() {
        return this.tweetDateTime;
    }

    public OffsetDateTime getTweetParsedDateTime() {
        return OffsetDateTime.parse(this.tweetDateTime);
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

    public Long getTweetFavoriteCount() {
        return this.tweetFavoriteCount;
    }

    @Field("tweetFavoriteCount")
    protected void setTweetFavoriteCount(Long tweetFavoriteCount) {
        this.tweetFavoriteCount = tweetFavoriteCount;
    }

    public Long getTweetQuoteCount() {
        return this.tweetQuoteCount;
    }

    @Field("tweetQuoteCount")
    protected void setTweetQuoteCount(Long tweetQuoteCount) {
        this.tweetQuoteCount = tweetQuoteCount;
    }

    public Long getTweetReplyCount() {
        return this.tweetReplyCount;
    }

    @Field("tweetReplyCount")
    protected void setTweetReplyCount(Long tweetReplyCount) {
        this.tweetReplyCount = tweetReplyCount;
    }

    public Long getTweetRetweetCount() {
        return this.tweetRetweetCount;
    }

    @Field("tweetRetweetCount")
    protected void setTweetRetweetCount(Long tweetRetweetCount) {
        this.tweetRetweetCount = tweetRetweetCount;
    }

    public List<String> getTweetHashtags() {
        return this.tweetHashtags;
    }

    @Field("tweetHashtags")
    protected void setTweetHashtags(List<String> tweetHashtags) {
        this.tweetHashtags = tweetHashtags;
    }

    public List<String> getTweetUserMentions() {
        return this.tweetUserMentions;
    }

    @Field("tweetUserMentions")
    protected void setTweetUserMentions(List<String> tweetUserMentions) {
        this.tweetUserMentions = tweetUserMentions;
    }

    public List<String> getTweetMediaURL() {
        return this.tweetMediaURL;
    }

    @Field("tweetMediaURL")
    protected void setTweetMediaURL(List<String> tweetMediaURL) {
        this.tweetMediaURL = tweetMediaURL;
    }

    public List<String> getTweetAttachedLinks() {
        return this.tweetAttachedLinks;
    }

    @Field("tweetAttachedLinks")
    protected void setTweetAttachedLinks(List<String> tweetAttachedLinks) {
        this.tweetAttachedLinks = tweetAttachedLinks;
    }

    public String toString() {
        return "tweetID : " + this.tweetID +
               "\n|--userID : " + this.userID+
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
