//Provides Schema Class (Field Type Class)
package SolrTemplates;

import org.apache.solr.client.solrj.request.schema.AnalyzerDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Solr Schema Field Type API
 *
 * attributes
 ** name        - Field Type Name
 ** class       - Field Type Class
 *
 * indexAnalyser
 ** type       - index
 ** class      - tokenizer class
 *
 * queryAnalyzer
 ** type       - query
 ** class      - tokenizer class
 *
 * similarity
 ** class      - similarity class
 */

public class SolrFieldType {

    //https://solr.apache.org/guide/6_6/field-types-included-with-solr.html
    static final HashMap<String, Object> typeClassMap = new HashMap<String, Object>() {{
        put("date_time", "solr.DatePointField");
        put("long_count", "solr.LongPointField");
        put("hyper_link", "solr.TextField");
        put("text_desc", "solr.TextField");
    }};

    //https://solr.apache.org/guide/6_6/tokenizers.html
    static final HashMap<String, Object> tokenizerClassMap = new HashMap<String, Object>() {{
        put("Standard", "solr.StandardTokenizerFactory");
        put("Classic", "solr.ClassicTokenizerFactory");
        put("LowerCase", "solr.LowerCaseTokenizerFactory");
        put("Email", "solr.UAX29URLEmailTokenizerFactory");
        put("WhiteSpace", "solr.WhitespaceTokenizerFactory");
    }};

    //https://solr.apache.org/docs/6_0_1/solr-core/org/apache/solr/schema/class-use/SimilarityFactory.html
    static final HashMap<String, Object> similarityClassMap = new HashMap<String, Object>() {{
        put("BM25", "solr.BM25SimilarityFactory");
        put("VSM", "solr.ClassicSimilarityFactory");
    }};

    Map<String, Object> attributes;
    AnalyzerDefinition indexAnalyzer;
    AnalyzerDefinition queryAnalyzer;
    Map<String, Object> similarity;

    public SolrFieldType(String name) {
        this.attributes = new HashMap<String, Object>() {{
            put("name", name);
            put("class", typeClassMap.get(name));
        }};
    }

    public SolrFieldType(String name, String similarity) {
        this.attributes = new HashMap<String, Object>() {{
            put("name", name);
            put("class", typeClassMap.get(name));
        }};
        this.similarity = Map.of("class", similarityClassMap.get(similarity));
    }

    public SolrFieldType(String name, String indexTokenizer, String queryTokenizer, String similarity) {
        this.attributes = new HashMap<String, Object>() {{
            put("name", name);
            put("class", typeClassMap.get(name));
        }};

        this.indexAnalyzer = new AnalyzerDefinition();
        this.indexAnalyzer.setAttributes(Map.of("type", "index"));
        this.indexAnalyzer.setTokenizer(Map.of("class", tokenizerClassMap.get(indexTokenizer)));

        this.queryAnalyzer = new AnalyzerDefinition();
        this.queryAnalyzer.setAttributes(Map.of("type", "query"));
        this.queryAnalyzer.setTokenizer(Map.of("class", tokenizerClassMap.get(queryTokenizer)));

        this.similarity = Map.of("class", similarityClassMap.get(similarity));
    }

    public Object getName() {
        return this.attributes.get("name");
    }

    public Object getClassType() {
        return this.attributes.get("class");
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public Object getIndexTokenizer() {
        if (indexAnalyzer != null)
            return this.indexAnalyzer.getTokenizer();
        return null;
    }

    public AnalyzerDefinition getIndexAnalyzer() {
        return this.indexAnalyzer;
    }

    public Object getQueryTokenizer() {
        if (queryAnalyzer != null)
            return this.queryAnalyzer.getTokenizer();
        return null;
    }

    public AnalyzerDefinition getQueryAnalyzer() {
        return this.queryAnalyzer;
    }

    public Map<String, Object> getSimilarity() {
        return this.similarity;
    }

    public String toString() {
        return "name : " + getName() +
                "\n|--class : " + getClassType() +
                "\n|--indexTokenizer : " + getIndexTokenizer() +
                "\n|--queryTokenizer : " + getQueryTokenizer() +
                "\n|--similarity : " + getSimilarity();
    }

}
