package model;

public class TagElement implements Element {
	//Static variables 
    private static int idCounter = 1;
    public static boolean idEnabled = true;
    //Instance variables
    private int id;
    private String tagName;
    private boolean endTag;
    private Element content;
    protected String attributes;

    public TagElement(String tagName, boolean endTag, Element content, String attributes) {
        if (tagName == null || tagName.isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty");
        }
        this.tagName = tagName;
        this.endTag = endTag;
        this.content = content;
        this.attributes = attributes;
        //Add when new id is created
        this.id = idCounter++;
    }

    public static void enableId(boolean choice) {
        idEnabled = choice;
    }

    public static void resetIds() {
        idCounter = 1;
    }

    @Override
    public String genHTML(int indentation) {
    	//HTML code 
        String indent = " ".repeat(indentation);
        String html = indent + getStartTag() + "\n";
        if (content != null) {
            html += (content.genHTML(indentation + 2) + "\n");
        }
        if (endTag) {
            html += indent + getEndTag() + "\n";
        }
        return html;
    }
    //Creates start tag based on name and attributes
    public String getStartTag() {
        String tag = "<" + tagName;
        if (idEnabled) {
            tag += " id=\"" + getStringId() + "\"";
        }
        if (attributes != null && !attributes.isEmpty()) {
            tag += " " + attributes;
        }
        tag += ">";
        return tag;
    }

    public String getEndTag() {
    	//Return endtag (used for sub classes)
        return endTag ? "</" + tagName + ">" : "";
    }

    public int getId() {
        return this.id;
    }

    public String getStringId() {
        return tagName + id;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
}
