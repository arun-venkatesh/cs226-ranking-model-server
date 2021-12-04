package api;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Main.SolrRanker;
import SolrTemplates.SolrTweet;
import SolrTemplates.SolrUser;
import constants.APIConstants;

@RestController
@RequestMapping("/api/search")
@CrossOrigin("*")
public class Handler {

	private static SolrRanker ranker = null;
	
	private void initRanker() {
		if(ranker == null)
		{
			ranker = new SolrRanker();
		}
	}
	
	@GetMapping("/query")
	public String getResultsBasedOnSearchQuery(String query) throws Exception {
		initRanker();
		
		JSONObject obj = null;
		try
		{
			List<Map<SolrTweet, SolrUser>> data = ranker.getRawQueryResult(query);
			int count = data.size();
			obj = new JSONObject();
			obj.put(APIConstants.RESPONSE_COUNT, count);
			
			JSONArray arr = new JSONArray();
			for(Map<SolrTweet, SolrUser> subObj : data) {
				for (Map.Entry<SolrTweet, SolrUser> entry : subObj.entrySet()) {
					SolrTweet tweet = entry.getKey();
					SolrUser user = entry.getValue();
					
					JSONObject tempObj = new JSONObject();
					JSONObject solrUser = handleUserObject(user.toJSON());
					JSONObject solrTweet = handleTweetObject(tweet.toJSON());
					
					tempObj.put(APIConstants.RESPONSE_USER, solrUser);
					tempObj.put(APIConstants.RESPONSE_TWEET, solrTweet);
					arr.put(tempObj);
				}
			}
			
			obj.put(APIConstants.RESPONSE_DATA, arr);
			obj.put(APIConstants.RESPONSE_STATUS_CODE, APIConstants.HTTP_SUCCESS);
			obj.put(APIConstants.RESPONSE_MESSAGE, APIConstants.HTTP_SUCCESS_MESSAGE);
			return obj.toString();
			
		}catch(Exception e)
		{
			JSONObject errorObj = new JSONObject();
			errorObj.put(APIConstants.RESPONSE_STATUS_CODE, APIConstants.HTTP_INTERNAL_SERVER_ERROR);
			errorObj.put(APIConstants.RESPONSE_MESSAGE, APIConstants.HTTP_INTERNAL_SERVER_ERROR_MESSAGE);
			return errorObj.toString();
		}
		
	}
	
	@GetMapping("/hashtag")
	public String getResultsBasedOnHashtag(String tag) throws Exception {
		initRanker();
		
		JSONObject obj = null;
		try
		{
			List<Map<SolrTweet, SolrUser>> data = ranker.getHashtagQueryResult(tag);
			int count = data.size();
			obj = new JSONObject();
			obj.put(APIConstants.RESPONSE_COUNT, count);
			
			JSONArray arr = new JSONArray();
			for(Map<SolrTweet, SolrUser> subObj : data) {
				for (Map.Entry<SolrTweet, SolrUser> entry : subObj.entrySet()) {
					SolrTweet tweet = entry.getKey();
					SolrUser user = entry.getValue();
					
					JSONObject tempObj = new JSONObject();
					JSONObject solrUser = handleUserObject(user.toJSON());
					JSONObject solrTweet = handleTweetObject(tweet.toJSON());
					
					tempObj.put(APIConstants.RESPONSE_USER, solrUser);
					tempObj.put(APIConstants.RESPONSE_TWEET, solrTweet);
					arr.put(tempObj);
				}
			}
			
			obj.put(APIConstants.RESPONSE_DATA, arr);
			obj.put(APIConstants.RESPONSE_STATUS_CODE, APIConstants.HTTP_SUCCESS);
			obj.put(APIConstants.RESPONSE_MESSAGE, APIConstants.HTTP_SUCCESS_MESSAGE);
			return obj.toString();
			
		}catch(Exception e)
		{
			JSONObject errorObj = new JSONObject();
			errorObj.put(APIConstants.RESPONSE_STATUS_CODE, APIConstants.HTTP_INTERNAL_SERVER_ERROR);
			errorObj.put(APIConstants.RESPONSE_MESSAGE, APIConstants.HTTP_INTERNAL_SERVER_ERROR_MESSAGE);
			return errorObj.toString();
		}
		
	}
	
	@GetMapping("/user")
	public String getResultsBasedOnUserId(String id) throws Exception {
		initRanker();
		
		JSONObject obj = null;
		try
		{
			Map<SolrUser, List<SolrTweet>> data = ranker.getUserQueryResult(id);
			obj = new JSONObject();
			
			
			for (Map.Entry<SolrUser, List<SolrTweet>> entry : data.entrySet()) {
				
				SolrUser user = entry.getKey();
				
				JSONObject solrUser = handleUserObject(user.toJSON());
				obj.put(APIConstants.RESPONSE_USER, solrUser);
				
				List<SolrTweet> tweets = entry.getValue();
				int count = tweets.size();
				obj.put(APIConstants.RESPONSE_COUNT, count);
				
				JSONArray arr = new JSONArray();
				for(SolrTweet tweet : tweets) {
					JSONObject solrTweet = handleTweetObject(tweet.toJSON());
					arr.put(solrTweet);
				}
				obj.put(APIConstants.RESPONSE_TWEET_PLURAL, arr);
				break;
			}
			
			obj.put(APIConstants.RESPONSE_STATUS_CODE, APIConstants.HTTP_SUCCESS);
			obj.put(APIConstants.RESPONSE_MESSAGE, APIConstants.HTTP_SUCCESS_MESSAGE);
			return obj.toString();
			
		}catch(Exception e)
		{
			JSONObject errorObj = new JSONObject();
			errorObj.put(APIConstants.RESPONSE_STATUS_CODE, APIConstants.HTTP_INTERNAL_SERVER_ERROR);
			errorObj.put(APIConstants.RESPONSE_MESSAGE, APIConstants.HTTP_INTERNAL_SERVER_ERROR_MESSAGE);
			return errorObj.toString();
		}
	}
	
	private JSONObject handleUserObject(String user) {
		try {
			return new JSONObject(user);
		} catch (JSONException e) {
			return null;
		}
	}
	
	private JSONObject handleTweetObject(String tweet) {
		try {
			JSONObject obj = new JSONObject(tweet);
			
			JSONArray tweetAttachedLinks = obj.optJSONArray("tweetAttachedLinks");
			if(tweetAttachedLinks.length() > 0) {
				JSONArray arr = new JSONArray();
				for(int i=0;i<tweetAttachedLinks.length();i++) {
					String currentStr = tweetAttachedLinks.getString(i);
					if(currentStr.length() != 0) {
						currentStr = currentStr.trim();
						currentStr = currentStr.substring(1, currentStr.length()-1);
						arr.put(currentStr);
					}
				}
				obj.put("tweetAttachedLinks", arr);
			}
			
			JSONArray tweetUserMentions = obj.optJSONArray("tweetUserMentions");
			if(tweetUserMentions.length() > 0) {
				JSONArray arr = new JSONArray();
				for(int i=0;i<tweetUserMentions.length();i++) {
					String currentStr = tweetUserMentions.getString(i);
					if(currentStr.length() != 0) {
						currentStr = currentStr.trim();
						currentStr = currentStr.substring(1, currentStr.length()-1);
						arr.put(currentStr);
					}
				}
				obj.put("tweetUserMentions", arr);
			}
			
			JSONArray tweetMediaURL = obj.optJSONArray("tweetMediaURL");
			if(tweetMediaURL.length() > 0) {
				JSONArray arr = new JSONArray();
				for(int i=0;i<tweetMediaURL.length();i++) {
					String currentStr = tweetMediaURL.getString(i);
					if(currentStr.length() != 0) {
						currentStr = currentStr.trim();
						currentStr = currentStr.substring(1, currentStr.length()-1);
						arr.put(currentStr);
					}
				}
				obj.put("tweetMediaURL", arr);
			}
			
			JSONArray tweetHashtags = obj.optJSONArray("tweetHashtags");
			if(tweetHashtags.length() > 0) {
				JSONArray arr = new JSONArray();
				for(int i=0;i<tweetHashtags.length();i++) {
					String currentStr = tweetHashtags.getString(i);
					if(currentStr.length() != 0) {
						currentStr = currentStr.trim();
						currentStr = currentStr.substring(1, currentStr.length()-1);
						arr.put(currentStr);
					}
				}
				obj.put("tweetHashtags", arr);
			}
			
			return obj;
			
		}catch(Exception e)
		{
			return null;
		}
	}
	
	
	
	
}
