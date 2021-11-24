//Provides Schema Class (Field Class)
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
 * docValues   - True/False
 *
 */

public class SolrField {

    Object name;
    Object type;
    Object indexed;
    Object stored;
    Object multiValued;
    Object docValues;

    public SolrField(Object name, Object type, Object indexed, Object stored, Object multiValued, Object docValues) {
        this.name = name;
        this.type = type;
        this.indexed = indexed;
        this.stored = stored;
        this.multiValued = multiValued;
        this.docValues = docValues;
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

    public Boolean checkDocValues() {
        return (Boolean) this.docValues;
    }

    public String toString() {
        return "name : " + this.name +
               "\n|--type : " + this.type +
               "\n|--indexed : " + this.indexed +
               "\n|--stored : " + this.stored +
               "\n|--multiValued : " + this.multiValued +
               "\n|--docValues : " + this.docValues ;
    }
}
