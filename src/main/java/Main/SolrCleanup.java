//This Program cleans up the collections in Apache Solr
package Main;

import Utils.SolrDataParser;
import Utils.SolrSchema;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public class SolrCleanup {

    public void schemaCleanup(SolrSchema solrUserSchema, SolrSchema solrTweetSchema) {
        System.out.println("**************************************************");
        System.out.println("*******************SCHEMA CLEANUP*****************");
        System.out.println("**************************************************");
        System.out.println("Schema Cleanup Initiated");

        try {
            //Users Fields - Schema Cleanup
            solrUserSchema.deleteField("userDateTime");
            solrUserSchema.deleteField("userName");
            solrUserSchema.deleteField("userScreenName");
            solrUserSchema.deleteField("userFollowersCount");
            solrUserSchema.deleteField("userFriendsCount");
            solrUserSchema.deleteField("userVerified");
            solrUserSchema.deleteField("userProfileImageURL");
            solrUserSchema.deleteField("userProfileBannerURL");
            System.out.println("User Field Schema Cleaned...");
        }
        catch (Exception E) {
            System.out.println("User Field Schema does not exist!");
        }

        try {
            //Users Field Types - Schema Cleanup
            solrUserSchema.deleteFieldType("date_time");
            solrUserSchema.deleteFieldType("long_count");
            solrUserSchema.deleteFieldType("hyper_link");
            System.out.println("User Field Type Schema Cleaned...");
        }
        catch (Exception E) {
            System.out.println("User Field Type Schema does not exist!");
        }

        try{
            //Tweets - Schema Fields Cleanup
            solrTweetSchema.deleteField("userID");
            solrTweetSchema.deleteField("tweetDateTime");
            solrTweetSchema.deleteField("tweetText");
            solrTweetSchema.deleteField("tweetFavoriteCount");
            solrTweetSchema.deleteField("tweetQuoteCount");
            solrTweetSchema.deleteField("tweetReplyCount");
            solrTweetSchema.deleteField("tweetRetweetCount");
            solrTweetSchema.deleteField("tweetHashtags");
            solrTweetSchema.deleteField("tweetUserMentions");
            solrTweetSchema.deleteField("tweetMediaURL");
            solrTweetSchema.deleteField("tweetAttachedLinks");
            System.out.println("Tweet Schema Cleaned...");
        }
        catch (Exception E) {
            System.out.println("Tweet Schema does not exist!");
        }

        try {
            //Tweet Field Types - Schema Cleanup
            solrTweetSchema.deleteFieldType("date_time");
            solrTweetSchema.deleteFieldType("long_count");
            solrTweetSchema.deleteFieldType("hyper_link");
            solrTweetSchema.deleteFieldType("text_desc");
            System.out.println("Tweet Field Type Schema Cleaned...");
        }
        catch (Exception E) {
            System.out.println("Tweet Field Type Schema does not exist!");
        }

        System.out.println("Ending Schema Cleanup");
        System.out.println("**************************************************");
        System.out.println("*******************END OF CLEANUP*****************");
        System.out.println("**************************************************");
    }

    public void indexCleanup(SolrClient solrUserClient, SolrClient solrTweetClient) {
        System.out.println("**************************************************");
        System.out.println("********************INDEX CLEANUP*****************");
        System.out.println("**************************************************");
        System.out.println("Initiating Index Cleanup");

        try{
            //Users - Indexes Cleanup
            solrUserClient.deleteByQuery("*:*");
            solrUserClient.commit();
            System.out.println("User Index Cleaned");
        }
        catch (Exception E) {
            System.out.println("User Index Cleanup Error");
            E.printStackTrace();
        }

        try{
            //Tweets - Indexes Cleanup
            solrTweetClient.deleteByQuery("*:*");
            solrTweetClient.commit();
            System.out.println("Tweet Index Cleaned");
        }
        catch (Exception E) {
            System.out.println("Tweet Index Cleanup Error");
            E.printStackTrace();
        }

        System.out.println("Ending Index Cleanup");
        System.out.println("**************************************************");
        System.out.println("*******************END OF CLEANUP*****************");
        System.out.println("**************************************************");
    }

    public static void main(String[] args) throws SolrServerException, IOException {

        SolrCleanup solrCleaner = new SolrCleanup();
        SolrDataParser dataParser = new SolrDataParser();

        SolrClient solrUserClient = dataParser.getSolrUserClient();
        SolrSchema solrUserSchema = new SolrSchema(solrUserClient);

        SolrClient solrTweetClient = dataParser.getSolrTweetClient();
        SolrSchema solrTweetSchema = new SolrSchema(solrTweetClient);

        //Index Cleanup
        solrCleaner.indexCleanup(solrUserClient, solrTweetClient);

        //Schema Cleanup
        solrCleaner.schemaCleanup(solrUserSchema, solrTweetSchema);

        /*
         Solr delete collection command: ./bin/solr.cmd delete -c tweets
         Solr delete collection command: ./bin/solr.cmd delete -c users
         Solr stop command: ./bin/solr.cmd stop -all
         */

    }
}
