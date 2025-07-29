package model;

import java.util.ArrayList;
import java.util.List;

public class ListElement extends TagElement implements Element {
	//Instance variables
    private List<Element> items;
    //Not used but created to keep track of ordered status for given list
    private boolean ordered;

    public ListElement(boolean ordered, String attributes) {
    	//Call to super, check for ordered or unordered list
        super(ordered ? "ol" : "ul", true, null, attributes);
        this.ordered = ordered;
        this.items = new ArrayList<>();
    }

    public void addItem(Element item) {
    	//Add desired element to arraylist
        items.add(item);
    }

    @Override
    public String genHTML(int indentation) {
    	//Create indent
        String indent = " ".repeat(indentation);
        StringBuilder html = new StringBuilder();
        //Place start tag after indent
        html.append(indent).append(getStartTag()).append("\n");

        for (Element item : items) {
        	//Usual list documentation
            html.append(indent).append("   <li>\n");
            //Add the given item HTML 
            html.append(indent).append("      ").append(item.genHTML(0)).append("\n");
            html.append(indent).append("   </li>\n");
        }
        //Close with end tag
        html.append(indent).append(getEndTag());
        return html.toString();
    }
}
