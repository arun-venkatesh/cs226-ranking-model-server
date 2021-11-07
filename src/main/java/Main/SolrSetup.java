//This Program sets up collection's schema in Apache Solr
package Main;

import SolrTemplates.SolrField;
import Utils.SolrSchema;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SolrSetup {

    public void schemaLookup(SolrSchema solrUsersSchema, SolrSchema solrTweetsSchema) {
        Scanner schemaInput = new Scanner(System.in);
        System.out.println("**************************************************");
        System.out.println("*******************SCHEMA LOOKUPS*****************");
        System.out.println("**************************************************");
        System.out.println("Enter Schema Name: ");
        String input = schemaInput.nextLine();
        List<String> userFields = solrUsersSchema.getFields();
        List<String> tweetFields = solrTweetsSchema.getFields();
        while(!input.equals("exit")) {
            if (userFields.contains(input)) {
                System.out.println(solrUsersSchema.getField(input));
            }
            else if (tweetFields.contains(input)) {
                System.out.println(solrTweetsSchema.getField(input));
            }
            else {
                System.out.println("Missing Schema Field");
            }
            System.out.println("Enter Schema Name: ");
            input = schemaInput.nextLine();
        }
    }

    public void schemaCleanup(SolrSchema solrUserSchema, SolrSchema solrTweetSchema) {
        System.out.println("**************************************************");
        System.out.println("*******************SCHEMA CLEANUP*****************");
        System.out.println("**************************************************");
        System.out.println("Schema Cleanup Initiated");

        try {
            //Users - Schema Fields Cleanup
            solrUserSchema.deleteField("userName");
            solrUserSchema.deleteField("userScreenName");
            solrUserSchema.deleteField("userFollowersCount");
            solrUserSchema.deleteField("userFriendsCount");
            solrUserSchema.deleteField("userVerified");
            solrUserSchema.deleteField("userProfileImageURL");
            solrUserSchema.deleteField("userProfileBannerURL");

            //Tweets - Schema Fields Cleanup
            solrTweetSchema.deleteField("tweetID");
            solrTweetSchema.deleteField("userScreenName");
            solrTweetSchema.deleteField("tweetDateTime");
            solrTweetSchema.deleteField("tweetText");
            solrTweetSchema.deleteField("tweetQuoteCount");
            solrTweetSchema.deleteField("tweetReplyCount");
            solrTweetSchema.deleteField("tweetRetweetCount");
            solrTweetSchema.deleteField("tweetHashtags");
            solrTweetSchema.deleteField("tweetUserMentions");
            solrTweetSchema.deleteField("tweetMediaURL");
            solrTweetSchema.deleteField("tweetAttachedLinks");
        }
        catch (Exception E) {
            System.out.println("Schema does not exist!");
        }

        System.out.println("Schema Cleanup Successful");
        System.out.println("**************************************************");
        System.out.println("*******************END OF CLEANUP*****************");
        System.out.println("**************************************************");
    }

    public static void main(String[] args) throws SolrServerException, IOException {

        /*
          Solr run command: ./bin/solr.cmd start
          Solr create collection command: ./bin/solr.cmd create -c tweets -s 2 -rf 2
          Solr create collection command: ./bin/solr.cmd create -c users -s 2 -rf 2
         */

        //Connecting to solr user client
        String urlUserString = "http://localhost:8983/solr/users";
        SolrClient solrUsersClient = new HttpSolrClient.Builder(urlUserString).build();
        SolrSchema solrUsersSchema = new SolrSchema(solrUsersClient);

        System.out.println("**************************************************");
        System.out.println("********************SCHEMA SETUP******************");
        System.out.println("**************************************************");
        System.out.println("Initiating Schema Setup");

        //Users - Schema Fields Setup
        SolrField userName = new SolrField("userName", "text_general", true, true, false);
        SolrField userScreenName = new SolrField("userScreenName", "text_general", true, true, false);
        SolrField userFollowersCount = new SolrField("userFollowersCount", "text_general", true, true, false);
        SolrField userFriendsCount = new SolrField("userFriendsCount", "text_general", true, true, false);
        SolrField userVerified = new SolrField("userVerified", "text_general", true, true, false);
        SolrField userProfileImageURL = new SolrField("userProfileImageURL", "text_general", true, true, false);
        SolrField userProfileBannerURL = new SolrField("userProfileBannerURL", "text_general", true, true, false);

        try {
            solrUsersSchema.addField(userName);
            solrUsersSchema.addField(userScreenName);
            solrUsersSchema.addField(userFollowersCount);
            solrUsersSchema.addField(userFriendsCount);
            solrUsersSchema.addField(userVerified);
            solrUsersSchema.addField(userProfileImageURL);
            solrUsersSchema.addField(userProfileBannerURL);
        }
        catch (Exception E) {
            System.out.println("Users Fields Exists!");
        }

        //Connecting to solr tweet client
        String urlTweetString = "http://localhost:8983/solr/tweets";
        SolrClient solrTweetClient = new HttpSolrClient.Builder(urlTweetString).build();
        SolrSchema solrTweetSchema = new SolrSchema(solrTweetClient);

        //Tweets - Schema Fields Setup
        SolrField tweetID = new SolrField("tweetID", "text_general", true, true, false);
        SolrField tweetDateTime = new SolrField("tweetDateTime", "text_general", true, true, false);
        SolrField tweetText = new SolrField("tweetText", "text_general", true, true, false);
        SolrField tweetQuoteCount = new SolrField("tweetQuoteCount", "text_general", true, true, false);
        SolrField tweetReplyCount = new SolrField("tweetReplyCount", "text_general", true, true, false);
        SolrField tweetRetweetCount = new SolrField("tweetRetweetCount", "text_general", true, true, false);
        SolrField tweetHashtags = new SolrField("tweetHashtags", "text_general", true, true, false);
        SolrField tweetUserMentions = new SolrField("tweetUserMentions", "text_general", true, true, false);
        SolrField tweetMediaURL = new SolrField("tweetMediaURL", "text_general", true, true, false);
        SolrField tweetAttachedLinks = new SolrField("tweetAttachedLinks", "text_general", true, true, false);

        try {
            solrTweetSchema.addField(tweetID);
            solrTweetSchema.addField(userScreenName); //Same as User Schema
            solrTweetSchema.addField(tweetDateTime);
            solrTweetSchema.addField(tweetText);
            solrTweetSchema.addField(tweetQuoteCount);
            solrTweetSchema.addField(tweetReplyCount);
            solrTweetSchema.addField(tweetRetweetCount);
            solrTweetSchema.addField(tweetHashtags);
            solrTweetSchema.addField(tweetUserMentions);
            solrTweetSchema.addField(tweetMediaURL);
            solrTweetSchema.addField(tweetAttachedLinks);
        }
        catch (Exception E) {
            System.out.println("Tweet Fields Exists!");
        }

        System.out.println("Schema Setup Successful");
        System.out.println("**************************************************");
        System.out.println("********************END OF SETUP******************");
        System.out.println("**************************************************");

        //Lookup Method
        SolrSetup solrSetup = new SolrSetup();
        solrSetup.schemaLookup(solrUsersSchema, solrTweetSchema);

        //Cleanup Method
        solrSetup.schemaCleanup(solrUsersSchema, solrTweetSchema);

        /*
         Solr delete collection command: ./bin/solr.cmd delete -c tweets
         Solr delete collection command: ./bin/solr.cmd delete -c users
         Solr stop command: ./bin/solr.cmd stop -all
         */
    }
}
