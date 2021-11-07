package SolrTemplates;

public class TweetMeta {
	SolrUser user;
	SolrTweet tweet;
	
	public void setSolrUser(SolrUser user) {
		this.user = user;
	}
	
	public SolrUser getSolrUser() {
		return user;
	}
	
	public SolrTweet getSolrTweet() {
		return tweet;
	}
	
	public void setSolrTweet(SolrTweet tweet) {
		this.tweet = tweet;
	}
}