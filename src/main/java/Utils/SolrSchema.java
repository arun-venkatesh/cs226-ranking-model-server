//Provides wrappers for Schema API
package Utils;

import SolrTemplates.SolrField;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolrSchema {

    SolrClient solrClient;

    public SolrSchema(SolrClient solrClient) {
        this.solrClient = solrClient;
    }

    //Returns the list of fields in the Schema
    public List<String> getFields() {

        List<String> fields = new ArrayList<>();
        SchemaRequest.Fields request = new SchemaRequest.Fields();
        try {
            SchemaResponse.FieldsResponse response = request.process(solrClient);
            List<Map<String, Object>> responseFields = response.getFields();
            for (Map<String, Object> responseField : responseFields) {
                fields.add((String)responseField.get("name"));
            }
        }
        catch (Exception E) {
            E.printStackTrace();
        }
        return fields;
    }

    //Returns the specific field from the schema
    public SolrField getField(String fieldName) {

        SolrField field = null;
        SchemaRequest.Field request = new SchemaRequest.Field(fieldName);
        try {
            SchemaResponse.FieldResponse response = request.process(solrClient);
            Map<String, Object> responseField = response.getField();
            Object name = responseField.getOrDefault("name", null);
            Object type = responseField.getOrDefault("type", null);
            Object indexed = responseField.getOrDefault("indexed", null);
            Object stored = responseField.getOrDefault("stored", null);
            Object multiValued = responseField.getOrDefault("multiValued", null);

            field = new SolrField(name, type, indexed, stored, multiValued);
        }
        catch (Exception E) {
            System.out.println("Field does not exist");
        }
        return field;
    }

    //Returns the list of fields that are indexed
    public List<String> getIndexedFields() {

        List<String> indexedFields = new ArrayList<>();
        SchemaRequest.Fields request = new SchemaRequest.Fields();
        try {
            SchemaResponse.FieldsResponse response = request.process(solrClient);
            List<Map<String, Object>> responseFields = response.getFields();

            for (Map<String, Object> field : responseFields) {
                if (field.containsKey("indexed")) {
                    if ((Boolean)field.get("indexed")) {
                        indexedFields.add((String)field.get("name"));
                    }
                }
            }
        }
        catch (Exception E) {
            E.printStackTrace();
        }
        return indexedFields;
    }

    //Returns the list of fields that are stored
    public List<String> getStoredFields() {

        List<String> storedFields = new ArrayList<>();
        SchemaRequest.Fields request = new SchemaRequest.Fields();
        try {
            SchemaResponse.FieldsResponse response = request.process(solrClient);
            List<Map<String, Object>> responseFields = response.getFields();

            for (Map<String, Object> field : responseFields) {
                if (field.containsKey("stored")) {
                    if ((Boolean)field.get("stored")) {
                        storedFields.add((String)field.get("name"));
                    }
                }
            }
        }
        catch (Exception E) {
            E.printStackTrace();
        }
        return storedFields;
    }

    //Creates a field on the Collection Schema
    public void addField(SolrField field) throws IOException, SolrServerException {

        Map<String, Object> fieldAttributes = new HashMap<>();
        fieldAttributes.put("name", field.getName());
        fieldAttributes.put("type", field.getType());
        fieldAttributes.put("indexed", field.checkIndexed());
        fieldAttributes.put("stored", field.checkStored());
        fieldAttributes.put("multiValued", field.checkMultiValued());

        SchemaRequest.AddField schemaRequest = new SchemaRequest.AddField(fieldAttributes);
        schemaRequest.process(solrClient);
    }

    public void deleteField(String name) throws IOException, SolrServerException {

        SchemaRequest.DeleteField schemaRequest = new SchemaRequest.DeleteField(name);
        schemaRequest.process(solrClient);
    }
}
