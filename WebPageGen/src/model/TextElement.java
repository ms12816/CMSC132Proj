package model;

public class TextElement implements Element{
	private String text;
	
	public TextElement(String text) {
		this.text = text;
	}
	
	public String genHTML(int indentation) {
		//Create text element with indentation
		String indent = "";
		for(int i = 0; i < indentation; i++) {
			indent += " ";
		}
		String html = indent + text;
		return html;
	}
	
}
