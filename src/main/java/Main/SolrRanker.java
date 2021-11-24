//This Program ranks documents using collection indexes in Apache Solr
package Main;

import SolrTemplates.SolrTweet;
import SolrTemplates.SolrUser;
import Utils.SolrDataParser;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SolrRanker {

    SolrDataParser dataParser;

    public SolrRanker() {
        dataParser = new SolrDataParser();
    }

    public SolrUser getSolrUser(SolrDocument solrDocument) {
        String userID = solrDocument.getOrDefault("id", "").toString();
        String userDateTime = solrDocument.getOrDefault("userDateTime", "").toString();
        String userName = solrDocument.getOrDefault("userName", "").toString();
        String userScreenName = solrDocument.getOrDefault("userScreenName", "").toString();
        Long userFollowersCount = Long.valueOf(solrDocument.getOrDefault("userFollowersCount", "").toString());
        Long userFriendsCount = Long.valueOf(solrDocument.getOrDefault("userFriendsCount", "").toString());
        Boolean userVerified = Boolean.valueOf(solrDocument.getOrDefault("userVerified", "").toString());
        String userProfileImageURL = solrDocument.getOrDefault("userProfileImageURL", "").toString();
        String userProfileBannerURL = solrDocument.getOrDefault("userProfileBannerURL", "").toString();
        return new SolrUser(userID, userDateTime, userName, userScreenName, userFollowersCount, userFriendsCount, userVerified, userProfileImageURL, userProfileBannerURL);
    }

    public SolrTweet getSolrTweet(SolrDocument solrDocument) {
        String tweetID = solrDocument.getOrDefault("id", "").toString();
        String userID = solrDocument.getOrDefault("userID", "").toString();
        String tweetDateTime = solrDocument.getOrDefault("tweetDateTime", "").toString();
        String tweetText = solrDocument.getOrDefault("tweetText", "").toString();
        Long tweetFavoriteCount = Long.valueOf(solrDocument.getOrDefault("tweetFavoriteCount", 0).toString());
        Long tweetQuoteCount = Long.valueOf(solrDocument.getOrDefault("tweetQuoteCount", 0).toString());
        Long tweetReplyCount = Long.valueOf(solrDocument.getOrDefault("tweetReplyCount", 0).toString());
        Long tweetRetweetCount = Long.valueOf(solrDocument.getOrDefault("tweetRetweetCount", 0).toString());
        List<String> tweetHashtags = dataParser.getArrayList(solrDocument.getOrDefault("tweetHashtags", "").toString());
        List<String> tweetUserMentions = dataParser.getArrayList(solrDocument.getOrDefault("tweetUserMentions", "").toString());
        List<String> tweetMediaURL = dataParser.getArrayList(solrDocument.getOrDefault("tweetMediaURL", "").toString());
        List<String> tweetAttachedLinks = dataParser.getArrayList(solrDocument.getOrDefault("tweetAttachedLinks", "").toString());
        return new SolrTweet(tweetID, userID, tweetDateTime, tweetText, tweetFavoriteCount, tweetQuoteCount, tweetReplyCount, tweetRetweetCount, tweetHashtags, tweetUserMentions, tweetMediaURL, tweetAttachedLinks);
    }

    public SolrUser getTweetUser(SolrClient solrUserClient, String userID) throws SolrServerException, IOException {
        SolrQuery userQuery = new SolrQuery() {{
           set("q", "id:" + userID);
        }};
        QueryResponse userResponse = solrUserClient.query(userQuery);
        return getSolrUser(userResponse.getResults().get(0));
    }

    public List<Map<SolrTweet, SolrUser>> getRawQueryResult(String query) {
        SolrDataParser dataParser = new SolrDataParser();
        List<Map<SolrTweet, SolrUser>> results = new ArrayList<>();

        //Connecting to solr clients
        SolrClient solrUserClient = dataParser.getSolrUserClient();
        SolrClient solrTweetClient = dataParser.getSolrTweetClient();

        try{
            SolrQuery tweetQuery = new SolrQuery();
            tweetQuery.set("q", "tweetText:" + query);
            tweetQuery.setRows(1000);
            QueryResponse tweetResponse = solrTweetClient.query(tweetQuery);
            SolrDocumentList tweetDocList = tweetResponse.getResults();
            for (SolrDocument solrDocument : tweetDocList) {
                SolrTweet solrTweet = getSolrTweet(solrDocument);
                SolrUser solrUser = getTweetUser(solrUserClient, solrTweet.getUserID());
                results.add(Map.of(solrTweet, solrUser));
            }
        }
        catch (Exception E) {
            System.out.println("Raw Query Error");
            E.printStackTrace();
        }
        return results;
    }

    public List<Map<SolrTweet, SolrUser>> getHashtagQueryResult(String query) {
        SolrDataParser dataParser = new SolrDataParser();
        List<Map<SolrTweet, SolrUser>> results = new ArrayList<>();

        //Connecting to solr clients
        SolrClient solrUserClient = dataParser.getSolrUserClient();
        SolrClient solrTweetClient = dataParser.getSolrTweetClient();

        try{
            SolrQuery tweetQuery = new SolrQuery();
            tweetQuery.set("q", "tweetHashtags:'" + query + "'");
            tweetQuery.setRows(1000);
            QueryResponse tweetResponse = solrTweetClient.query(tweetQuery);
            SolrDocumentList tweetDocList = tweetResponse.getResults();
            for (SolrDocument solrDocument : tweetDocList) {
                SolrTweet solrTweet = getSolrTweet(solrDocument);
                SolrUser solrUser = getTweetUser(solrUserClient, solrTweet.getUserID());
                results.add(Map.of(solrTweet, solrUser));
            }
        }
        catch (Exception E) {
            System.out.println("Hashtag Query Error");
            E.printStackTrace();
        }
        return results;
    }

    public Map<SolrUser, List<SolrTweet>> getUserQueryResult(String query) {
        SolrUser solrUser;
        SolrDataParser dataParser = new SolrDataParser();
        List<SolrTweet> tweetResults = new ArrayList<>();

        //Connecting to solr clients
        SolrClient solrUserClient = dataParser.getSolrUserClient();
        SolrClient solrTweetClient = dataParser.getSolrTweetClient();

        try {
            SolrQuery userQuery = new SolrQuery();
            userQuery.set("q", "id:" + query);
            QueryResponse userResponse = solrUserClient.query(userQuery);
            solrUser = getSolrUser(userResponse.getResults().get(0));
        }
        catch (Exception E) {
            System.out.println("No User Found!");
            E.printStackTrace();
            return null;
        }

        try {
            SolrQuery tweetQuery = new SolrQuery();
            tweetQuery.set("q", "userID:" + solrUser.getUserID());
            tweetQuery.addSort("tweetDateTime", SolrQuery.ORDER.desc);
            tweetQuery.setRows(1000);

            QueryResponse tweetResponse = solrTweetClient.query(tweetQuery);
            SolrDocumentList tweetDocList = tweetResponse.getResults();
            for (SolrDocument solrDocument : tweetDocList) {
                SolrTweet solrTweet = getSolrTweet(solrDocument);
                tweetResults.add(solrTweet);
            }
        }
        catch (Exception E) {
            System.out.println("User Query Error");
            E.printStackTrace();
            return null;
        }
        return Map.of(solrUser, tweetResults);
    }

//    public static void main(String[] args) {
//
//        SolrRanker ranker = new SolrRanker();
//        Scanner inputReader = new Scanner(System.in);
//
//        System.out.println("**************************************************");
//        System.out.println("*******************SOLRJ RANKING******************");
//        System.out.println("**************************************************");
//        System.out.println("1. Raw Query\n2. Hashtag Query\n3. User Query\n4. Exit");
//        System.out.println("Enter Search Option: ");
//        int userOption = inputReader.nextInt();
//        while(userOption != 4) {
//            System.out.println("Enter Search Query: ");
//            String query = inputReader.next();
//            switch (userOption) {
//                case 1:
//                    System.out.println(ranker.getRawQueryResult(query));
//                    break;
//                case 2:
//                    System.out.println(ranker.getHashtagQueryResult(query));
//                    break;
//                case 3:
//                    System.out.println(ranker.getUserQueryResult(query));
//                    break;
//                default: System.out.println("Invalid Option!");
//            }
//            System.out.println("\n\n1. Raw Query\n2. Hashtag Query\n3. User Query\n4. Exit");
//            System.out.println("Enter Search Option: ");
//            userOption = inputReader.nextInt();
//        }
//        System.out.println("**************************************************");
//        System.out.println("****************END OF SOLRJ RANKING**************");
//        System.out.println("**************************************************");
//    }
}
