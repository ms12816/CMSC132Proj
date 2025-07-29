package model;

public class HeadingElement extends TagElement implements Element {
	//Instance variables
    private Element content;
    private int level;

    public HeadingElement(Element content, int level, String attributes) {
    	//Call to super
        super("h" + level, true, content, attributes);
        //Check for valid html level value
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("Heading level must be between 1 and 6");
        }
        this.content = content;
        this.level = level;
    }

    @Override
    public String genHTML(int indentation) {
    	//Create indent and declare html StringBuilder
        StringBuilder html = new StringBuilder();
        String indent = " ".repeat(indentation);
        //Add indent and start tag to heading
        html.append(indent).append(getStartTag());
        //Null check before adding
        if (content != null) {
            html.append(content.genHTML(0));
        }
        //End tag
        html.append(getEndTag());
        return html.toString();
    }

	public int getLevel() {
		return this.level;
	}
}
