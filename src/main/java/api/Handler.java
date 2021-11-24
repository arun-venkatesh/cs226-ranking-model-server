package api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
					JSONObject solrUser = new JSONObject(user.toJSON());
					JSONObject solrTweet = new JSONObject(tweet.toJSON());
					
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
					JSONObject solrUser = new JSONObject(user.toJSON());
					JSONObject solrTweet = new JSONObject(tweet.toJSON());
					
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
				
				
				JSONObject solrUser = new JSONObject(user.toJSON());
				obj.put(APIConstants.RESPONSE_USER, solrUser);
				
				List<SolrTweet> tweets = entry.getValue();
				int count = tweets.size();
				obj.put(APIConstants.RESPONSE_COUNT, count);
				
				JSONArray arr = new JSONArray();
				for(SolrTweet tweet : tweets) {
					JSONObject solrTweet = new JSONObject(tweet.toJSON());
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
	
	
	
	
}
