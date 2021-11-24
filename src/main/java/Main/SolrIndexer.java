//This Program indexes documents into collections in Apache Solr
package Main;

import SolrTemplates.SolrTweet;
import SolrTemplates.SolrUser;
import Utils.SolrDataParser;
import com.opencsv.CSVReader;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SolrIndexer {

    Map<String, SolrUser> userMap = new HashMap<>();
    Map<String, SolrTweet> tweetMap = new HashMap<>();

    public void addOrUpdateUser(String userID, SolrUser solrUser) {
        if (userMap.containsKey(userID)) {
            if (solrUser.getUserParsedDateTime().isAfter(userMap.get(userID).getUserParsedDateTime())) {
                userMap.put(solrUser.getUserID(), solrUser);
            }
        }
        else {
            userMap.put(userID, solrUser);
        }
    }

    public void addOrUpdateTweet(String tweetID, SolrTweet solrTweet) {
        if (tweetMap.containsKey(tweetID)) {
            if (solrTweet.getTweetParsedDateTime().isAfter(tweetMap.get(tweetID).getTweetParsedDateTime())) {
                tweetMap.put(tweetID, solrTweet);
            }
        }
        else {
            tweetMap.put(tweetID, solrTweet);
        }
    }

    public void commitUserMap(SolrClient solrClient) throws IOException, SolrServerException {
        for (Map.Entry<String, SolrUser> user : userMap.entrySet()) {
            solrClient.addBean(user.getValue());
        }
        solrClient.commit();
    }

    public void commitTweetMap(SolrClient solrClient) throws IOException, SolrServerException {
        for (Map.Entry<String, SolrTweet> tweet : tweetMap.entrySet()) {
            solrClient.addBean(tweet.getValue());
        }
        solrClient.commit();
    }

    public static void main(String[] args) {

        long time = -System.currentTimeMillis();
        SolrIndexer indexWriter = new SolrIndexer();
        SolrDataParser dataParser = new SolrDataParser();

        System.out.println("**************************************************");
        System.out.println("********************INDEX SETUP*******************");
        System.out.println("**************************************************");
        System.out.println("Initiating Index Setup");

        //Reading the dataset
        try {
            File dataset = new File("Dataset");
            for (File file : Objects.requireNonNull(dataset.listFiles())) {
                CSVReader reader = new CSVReader(new FileReader(file));
                String[] nextLine;
                reader.readNext(); //Skip the first line
                while((nextLine = reader.readNext()) != null) {
                    try{
                        //User Information
                        String userID = nextLine[10];
                        String userDateTime = nextLine[2];
                        String userName = nextLine[11];
                        String userScreenName = nextLine[12];
                        Long userFollowersCount = Long.valueOf(nextLine[17]);
                        Long userFriendsCount = Long.valueOf(nextLine[18]);
                        Boolean userVerified = Boolean.valueOf(nextLine[13]);
                        String userProfileImageURL = nextLine[14];
                        String userProfileBannerURL = nextLine[15];

                        SolrUser solrUser = new SolrUser(userID, userDateTime, userName, userScreenName, userFollowersCount, userFriendsCount, userVerified, userProfileImageURL, userProfileBannerURL);
                        indexWriter.addOrUpdateUser(userID, solrUser);

                        //Tweet Information
                        String tweetID = nextLine[0];
                        String tweetText = nextLine[7];
                        Long tweetFavoriteCount = Long.valueOf(nextLine[19]);
                        Long tweetQuoteCount = Long.valueOf(nextLine[20]);
                        Long tweetReplyCount = Long.valueOf(nextLine[21]);
                        Long tweetRetweetCount = Long.valueOf(nextLine[22]);
                        List<String> tweetHashtags = dataParser.getArrayList(nextLine[8]);
                        List<String> tweetUserMentions = dataParser.getArrayList(nextLine[9]);
                        List<String> tweetMediaURL = dataParser.getArrayList(nextLine[4]);
                        List<String> tweetAttachedLinks = dataParser.getArrayList(nextLine[6]);

                        SolrTweet solrTweet = new SolrTweet(tweetID, userID, userDateTime, tweetText, tweetFavoriteCount, tweetQuoteCount, tweetReplyCount, tweetRetweetCount, tweetHashtags, tweetUserMentions, tweetMediaURL, tweetAttachedLinks );
                        indexWriter.addOrUpdateTweet(tweetID, solrTweet);
                    }
                    catch (Exception E) {
                        System.out.println("Exception at File: " + file.getName() + " on TweetID: " + nextLine[0]);
                    }
                }
            }

            time += System.currentTimeMillis();
            System.out.println("Dataset Read Time: " + time + "ms");
            System.out.println("Dataset has been read in memory...");

            System.out.println("Initiating Indexing...");
            time = -System.currentTimeMillis();

            //Connecting to solr user client
            SolrClient solrUserClient = dataParser.getSolrUserClient();
            indexWriter.commitUserMap(solrUserClient);

            //Connecting to solr tweet client
            SolrClient solrTweetClient = dataParser.getSolrTweetClient();
            indexWriter.commitTweetMap(solrTweetClient);

            time += System.currentTimeMillis();
            System.out.println("Indexing time: " + time + "ms");
            System.out.println("Index Setup Successful");
            System.out.println("**************************************************");
            System.out.println("********************END OF SETUP******************");
            System.out.println("**************************************************");
        }
        catch (Exception E) {
            E.printStackTrace();
        }

    }
}
