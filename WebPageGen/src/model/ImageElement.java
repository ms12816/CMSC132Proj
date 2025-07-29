package model;

public class ImageElement extends TagElement implements Element{
	//Instance variables
	private String imageURL;
	private int width;
	private int height;
	private String alt;
	private String attributes;
	
	public ImageElement(String imageURL, int width, int height, String alt, String attributes) {
		//Call to super
		super("img", false, null, attributes);
		this.imageURL = imageURL;
		this.width = width;
		this.height = height;
		this.alt = alt;
		this.attributes = attributes;
		
	}
	
	public String getImageURL() {
		return imageURL;
	}
	
	@Override
	public String genHTML(int indentation) {
		//Create indent
	    String indent = Utilities.spaces(indentation);
	    //Start img tag
	    String tag = indent + "<img";
	    //Check for idEnabled, if true add id
	    if (idEnabled) tag += " id=\"img" + getId() + "\"";
	    //Check for null/empty attributes value
	    if (attributes != null && !attributes.isEmpty()) tag += " " + attributes;
	    //Add url, width, height, and alt in correct order for img 
	    tag += " src=\"" + imageURL + "\" width=\"" + width + 
	    		"\" height=\"" + height + "\" alt=\"" + alt + "\">";
	    return tag;
	}


		
}
