//This Program sets up collection's schema in Apache Solr
package Main;

import SolrTemplates.SolrField;
import SolrTemplates.SolrFieldType;
import Utils.SolrDataParser;
import Utils.SolrSchema;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SolrSetup {

    public void schemaLookup(SolrSchema solrUserSchema, SolrSchema solrTweetSchema) {
        Scanner schemaInput = new Scanner(System.in);
        System.out.println("**************************************************");
        System.out.println("*******************SCHEMA LOOKUPS*****************");
        System.out.println("**************************************************");
        System.out.println("Enter Schema Name: ");
        String input = schemaInput.nextLine();
        List<String> userFields = solrUserSchema.getFields();
        List<String> tweetFields = solrTweetSchema.getFields();
        while(!input.equals("exit")) {
            if (userFields.contains(input)) {
                System.out.println(solrUserSchema.getField(input));
            }
            else if (tweetFields.contains(input)) {
                System.out.println(solrTweetSchema.getField(input));
            }
            else {
                System.out.println("Missing Schema Field");
            }
            System.out.println("Enter Schema Name: ");
            input = schemaInput.nextLine();
        }
    }

    public static void main(String[] args) throws SolrServerException, IOException {

        /*
          Solr run command: ./bin/solr.cmd start
          Solr create collection command: ./bin/solr.cmd create -c tweets -s 2 -rf 2
          Solr create collection command: ./bin/solr.cmd create -c users -s 2 -rf 2
         */

        SolrDataParser dataParser = new SolrDataParser();

        //Connecting to solr user client
        SolrClient solrUserClient = dataParser.getSolrUserClient();
        SolrSchema solrUserSchema = new SolrSchema(solrUserClient);

        System.out.println("**************************************************");
        System.out.println("********************SCHEMA SETUP******************");
        System.out.println("**************************************************");
        System.out.println("Initiating Schema Setup");

        //Users - Schema Field Types Setup
        SolrFieldType dateField = new SolrFieldType("date_time", "BM25");
        SolrFieldType countField = new SolrFieldType("long_count", "BM25");
        SolrFieldType urlField = new SolrFieldType("hyper_link", "Email", "Email", "BM25");

        try {
            solrUserSchema.addFieldType(dateField);
            solrUserSchema.addFieldType(countField);
            solrUserSchema.addFieldType(urlField);
        }
        catch (Exception E) {
            System.out.println("Users Field Types Exists!");
            E.printStackTrace();
        }

        //Users - Schema Fields Setup
        SolrField userDateTime = new SolrField("userDateTime", "date_time", true, true, false, false);
        SolrField userName = new SolrField("userName", "string", true, true, false, false);
        SolrField userScreenName = new SolrField("userScreenName", "string", true, true, false, false);
        SolrField userFollowersCount = new SolrField("userFollowersCount", "long_count", true, true, false, false);
        SolrField userFriendsCount = new SolrField("userFriendsCount", "long_count", true, true, false, false);
        SolrField userVerified = new SolrField("userVerified", "boolean", true, true, false, false);
        SolrField userProfileImageURL = new SolrField("userProfileImageURL", "hyper_link", true, true, false, false);
        SolrField userProfileBannerURL = new SolrField("userProfileBannerURL", "hyper_link", true, true, false, false);

        try {
            solrUserSchema.addField(userDateTime);
            solrUserSchema.addField(userName);
            solrUserSchema.addField(userScreenName);
            solrUserSchema.addField(userFollowersCount);
            solrUserSchema.addField(userFriendsCount);
            solrUserSchema.addField(userVerified);
            solrUserSchema.addField(userProfileImageURL);
            solrUserSchema.addField(userProfileBannerURL);
        }
        catch (Exception E) {
            System.out.println("Users Fields Exists!");
            E.printStackTrace();
        }

        //Connecting to solr tweet client
        SolrClient solrTweetClient = dataParser.getSolrTweetClient();
        SolrSchema solrTweetSchema = new SolrSchema(solrTweetClient);

        //Tweets - Schema Field Types Setup
        SolrFieldType textField = new SolrFieldType("text_desc", "Classic", "Classic", "BM25");

        try {
            solrTweetSchema.addFieldType(dateField);
            solrTweetSchema.addFieldType(countField);
            solrTweetSchema.addFieldType(urlField);
            solrTweetSchema.addFieldType(textField);
        }
        catch (Exception E) {
            System.out.println("Tweet Field Types Exists!");
            E.printStackTrace();
        }

        //Tweets - Schema Fields Setup
        SolrField userID = new SolrField("userID", "string", true, true, false, false);
        SolrField tweetDateTime = new SolrField("tweetDateTime", "date_time", true, true, false, false);
        SolrField tweetText = new SolrField("tweetText", "text_desc", true, true, false, false);
        SolrField tweetFavoriteCount = new SolrField("tweetFavoriteCount", "long_count", true, true, false, false);
        SolrField tweetQuoteCount = new SolrField("tweetQuoteCount", "long_count", true, true, false, false);
        SolrField tweetReplyCount = new SolrField("tweetReplyCount", "long_count", true, true, false, false);
        SolrField tweetRetweetCount = new SolrField("tweetRetweetCount", "long_count", true, true, false, false);
        SolrField tweetHashtags = new SolrField("tweetHashtags", "string", true, true, true, false);
        SolrField tweetUserMentions = new SolrField("tweetUserMentions", "string", true, true, true, false);
        SolrField tweetMediaURL = new SolrField("tweetMediaURL", "hyper_link", true, true, true, false);
        SolrField tweetAttachedLinks = new SolrField("tweetAttachedLinks", "hyper_link", true, true, true,false);

        try {
            solrTweetSchema.addField(userID);
            solrTweetSchema.addField(tweetDateTime);
            solrTweetSchema.addField(tweetText);
            solrTweetSchema.addField(tweetFavoriteCount);
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
            E.printStackTrace();
        }

        System.out.println("Schema Setup Successful");
        System.out.println("**************************************************");
        System.out.println("********************END OF SETUP******************");
        System.out.println("**************************************************");

        //Lookup Method
        SolrSetup solrSetup = new SolrSetup();
        solrSetup.schemaLookup(solrUserSchema, solrTweetSchema);

    }
}
