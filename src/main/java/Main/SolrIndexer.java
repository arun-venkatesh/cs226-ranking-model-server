//This Program indexes documents into collections in Apache Solr
package Main;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import SolrTemplates.SolrTweet;
import SolrTemplates.SolrUser;
import SolrTemplates.TweetMeta;
import api.APIHandler;


@SpringBootApplication
@ComponentScan(basePackageClasses = APIHandler.class)
public class SolrIndexer {
	

	private static List<TweetMeta> tweetMeta = new ArrayList<TweetMeta>();
	
    public static void main(String[] args) throws Exception {

        //Connecting to solr user client
    	SpringApplication.run(SolrIndexer.class, args);
    	
    	try
    	{
    		init();
    		APIHandler.set = Boolean.TRUE;
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    
    private static void init() throws Exception {
    	//Commented for now
    	//String urlUserString = "http://localhost:8983/solr/users";
        //SolrClient solrUsersClient = new HttpSolrClient.Builder(urlUserString).build();

        //Map<String, SolrUser> userMap = new HashMap<>();
        //Map<String, SolrTweet> tweetMap = new HashMap<>();

        try {
            DataFormatter formatter = new DataFormatter();
            File dataset = new File("Sample Dataset.xlsx");
            FileInputStream fis = new FileInputStream(dataset);

            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            TweetMeta tm = null;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                
                tm = new TweetMeta();
                //User Information
                System.out.println("Row Number: " + row.getRowNum());
                String userName = formatter.formatCellValue(row.getCell(12));
                String userScreenName = formatter.formatCellValue(row.getCell(13));
                BigInteger userFollowersCount = new BigInteger(formatter.formatCellValue(row.getCell(16)));
                BigInteger userFriendsCount = new BigInteger(formatter.formatCellValue(row.getCell(18)));
                Boolean userVerified = row.getCell(14).getBooleanCellValue();
                String userProfileImageURL = formatter.formatCellValue(row.getCell(15));
                String userProfileBannerURL = formatter.formatCellValue(row.getCell(17));

                SolrUser solrUser = new SolrUser(userName, userScreenName, userFollowersCount, userFriendsCount, userVerified, userProfileImageURL, userProfileBannerURL);
                System.out.println(solrUser);
                tm.setSolrUser(solrUser);

                //Tweet Information
                String tweetID = formatter.formatCellValue(row.getCell(1));
                String tweetDateTime = formatter.formatCellValue(row.getCell(3));
                String tweetText = formatter.formatCellValue(row.getCell(8));
                BigInteger tweetQuoteCount = new BigInteger(formatter.formatCellValue(row.getCell(19)));
                BigInteger tweetReplyCount = new BigInteger(formatter.formatCellValue(row.getCell(20)));
                BigInteger tweetRetweetCount = new BigInteger(formatter.formatCellValue(row.getCell(21)));
                String tweetHashtags = formatter.formatCellValue(row.getCell(9));
                String tweetUserMentions = formatter.formatCellValue(row.getCell(10));
                String tweetMediaURL = formatter.formatCellValue(row.getCell(6));
                String tweetAttachedLinks = formatter.formatCellValue(row.getCell(7));

                SolrTweet solrTweet = new SolrTweet(tweetID, userScreenName, tweetDateTime, tweetText, tweetQuoteCount, tweetReplyCount, tweetRetweetCount, tweetHashtags, tweetUserMentions, tweetMediaURL, tweetAttachedLinks );
                System.out.println(solrTweet);
                tm.setSolrTweet(solrTweet);
                
                addTweetMeta(tm);
                
                //break;
            }
        }
        catch (Exception E) {
            E.printStackTrace();
        }
    }
    
    private static void addTweetMeta(TweetMeta tm)
    {
    	tweetMeta.add(tm);
    }
    
    public static List<TweetMeta> getTweetMeta()
    {
    	return tweetMeta;
    }
}
