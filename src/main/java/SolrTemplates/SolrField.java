//Provides Field Class
package SolrTemplates;

/**
 * Solr Schema Field API
 *
 * name        - Field Name
 * type        - Field Type
 * sorted      - True/False
 * indexed     - True/False
 * stored      - True/False
 * multiValued - True/False
 *
 */

public class SolrField {

    Object name;
    Object type;
    Object indexed;
    Object stored;
    Object multiValued;

    public SolrField(Object name, Object type, Object indexed, Object stored, Object multiValued) {
        this.name = name;
        this.type = type;
        this.indexed = indexed;
        this.stored = stored;
        this.multiValued = multiValued;
    }

    public Object getName() {
        return this.name;
    }

    public Object getType() {
        return this.type;
    }

    public Boolean checkIndexed() {
        return (Boolean)this.indexed;
    }

    public Boolean checkStored() {
        return (Boolean)this.stored;
    }

    public Boolean checkMultiValued() {
        return (Boolean)this.multiValued;
    }

    public String toString() {
        return "name : " + this.name +
               "\n|--type : " + this.type +
               "\n|--indexed : " + this.indexed +
               "\n|--stored : " + this.stored +
               "\n|--multiValued : " + this.multiValued;
    }
}
