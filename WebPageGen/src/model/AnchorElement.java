package model;

public class AnchorElement extends TagElement implements Element{
	//Instance variables
	private String url;
	private String linkText;
	
	public AnchorElement(String url, String linkText, String attributes) {
		//Call to super 
		super("a", true, new TextElement(linkText), attributes);
		this.url = url;
		this.linkText = linkText;
		
	}
	public String getLinkText() {
		return linkText;
	}
	
	public String getUrl() {
		return url;
	}
	@Override
	public String genHTML(int indentation) {
		//Create indentation
		String indent = " ".repeat(indentation);
		
		String start = indent + "<a ";
		//State id of <a> if idEnabled is true
		if(idEnabled) {
			start += " id=\"" + getStringId() + "\"";
		}
		//Add url with usual documentation
		start += "href=\"" + url + "\"";
		//Check for null super value
        if (super.attributes != null && !super.attributes.isEmpty()) {
        	start += " ";
        	start += super.attributes;
        }
        //Close html
        start += ">" + linkText + "</a>";
        return start;
		
	}
}
