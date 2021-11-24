//Provide Commonly used functions
package Utils;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.util.Arrays;
import java.util.List;

public class SolrDataParser {

    public SolrClient getSolrUserClient() {
        String urlUserString = "http://localhost:8983/solr/users";
        return new HttpSolrClient.Builder(urlUserString).build();
    }

    public SolrClient getSolrTweetClient() {
        String urlTweetString = "http://localhost:8983/solr/tweets";
        return new HttpSolrClient.Builder(urlTweetString).build();
    }

    public List<String> getArrayList(String value) {
        String[] values = value.replace("[", "").replace("]", "").split(",");
        return Arrays.asList(values);
    }

}
