package model;

import java.util.ArrayList;
import java.util.List;

public class ParagraphElement extends TagElement implements Element {
	//Declare list 
    private List<Element> items = new ArrayList<>();

    public ParagraphElement(String attributes) {
    	//Call to super
        super("p", true, null, attributes);
    }

    public void addItem(Element item) {
    	//Add element to paragraph
        items.add(item);
    }

    @Override
    public String genHTML(int indentation) {
    	//Creat indent
        String indent = " ".repeat(indentation);
        //Initialize html starting with indent and start tag
        StringBuilder result = new StringBuilder(indent + super.getStartTag() + "\n");
        for (Element item : items) {
        	//Add items with correct indentation
            result.append(item.genHTML(indentation + 2)).append("\n");
        }
        //End tag
        result.append(indent).append(super.getEndTag()).append("\n");
        return result.toString();
    }
}
