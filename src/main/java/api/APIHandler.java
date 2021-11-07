package api;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Main.SolrIndexer;
import SolrTemplates.TweetMeta;
import constants.APIConstants;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class APIHandler {
	
	public static Boolean set = Boolean.FALSE;
	
	@GetMapping("/test")
	public String testAPI(String searchKey) throws Exception
	{
		JSONObject obj = null;
		try
		{
			obj = new JSONObject();
			List<TweetMeta> tweetMeta = SolrIndexer.getTweetMeta();
			int count = tweetMeta.size();
			obj.put(APIConstants.RESPONSE_COUNT, count);
			JSONArray arr = new JSONArray();
			
			for(TweetMeta tm : tweetMeta)
			{
				JSONObject tempObj = new JSONObject();
				JSONObject solrUser = new JSONObject(tm.getSolrUser().toJSON());
				JSONObject solrTweet = new JSONObject(tm.getSolrTweet().toJSON());
				
				tempObj.put(APIConstants.RESPONSE_USER, solrUser);
				tempObj.put(APIConstants.RESPONSE_TWEET, solrTweet);
				arr.put(tempObj);
			}
			
			obj.put(APIConstants.RESPONSE_DATA, arr);
			obj.put(APIConstants.RESPONSE_STATUS_CODE, APIConstants.HTTP_SUCCESS);
			obj.put(APIConstants.RESPONSE_MESSAGE, APIConstants.HTTP_SUCCESS_MESSAGE);
			return obj.toString();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			JSONObject errorObj = new JSONObject();
			errorObj.put(APIConstants.RESPONSE_STATUS_CODE, APIConstants.HTTP_INTERNAL_SERVER_ERROR);
			errorObj.put(APIConstants.RESPONSE_MESSAGE, APIConstants.HTTP_INTERNAL_SERVER_ERROR_MESSAGE);
			return errorObj.toString();
		}
	}

}
