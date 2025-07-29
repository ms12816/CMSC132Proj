package model;

import java.util.ArrayList;

public class WebPage implements Comparable<WebPage> {
	//Declare instance variables 
    private ArrayList<Element> items;
    private String title;

    public WebPage(String title) {
    	//Null Check
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        //Initialize instance variables
        this.items = new ArrayList<>();
        this.title = title;
    }

    public int addElement(Element element) {
    	//Null and correct element type check
        if (element == null) return -1;
        if (!(element instanceof TagElement || element instanceof TextElement)) {
            throw new IllegalArgumentException("Unsupported element type");
        }
        //Add item to list
        items.add(element);
        if (element instanceof TagElement) {
            return ((TagElement) element).getId();
        }
        return -1;
    }

    public String getWebPageHTML(int indentation) {
    	//Check for valid indentation value
        if (indentation < 0) {
            throw new IllegalArgumentException("Indentation cannot be negative");
        }
        //Indent 
        String indent = " ".repeat(indentation);
        //Usual webpage documentation
        StringBuilder html = new StringBuilder(indent + "<!doctype html>\n");
        html.append(indent).append("<html>\n");
        html.append(indent).append("  <head>\n");
        html.append(indent).append("    <meta charset=\"utf-8\"/>\n");
        //Add desired title
        html.append(indent).append("    <title>").append(title).append("</title>\n");
        html.append(indent).append("  </head>\n");
        html.append(indent).append("  <body>\n");
        //Go through array list, adding all elements to webpage
        for (Element e : items) {
            html.append(e.genHTML(indentation + 4)).append("\n");
        }
        //Close webpage
        html.append(indent).append("  </body>\n").append(indent).append("</html>");
        return html.toString();
    }
    
    public void writeToFile(String filename, int indentation) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        Utilities.writeToFile(filename, getWebPageHTML(indentation));
    }

    public Element findElem(int id) {
        for (Element e : items) {
        	//Check for correct element and correct id
            if (e instanceof TagElement && ((TagElement) e).getId() == id) {
                return e;
            }
        }
        return null;
    }

    public String stats() {
        int p = 0, l = 0, t = 0;
        double tUtil = 0.0;
        for (Element e : items) {
        	//Count for number of paragraphs, lists, and tables
                if (e instanceof ParagraphElement) {
                	p++;
                }
                if (e instanceof ListElement) {
                	l++;
                }
                if (e instanceof TableElement) {
                    t++;
                    tUtil += ((TableElement) e).getTableUtilization();
                }
        	}
        //Check for avg table utilization
        double util = t > 0 ? tUtil / t : 0.0;
        //Return in required format
        return "List Count: " + l + "\n" +
               "Paragraph Count: " + p + "\n" +
               "Table Count: " + t + "\n" +
               "TableElement Utilization: " + util;
    }

    public int compareTo(WebPage webPage) {
        if (webPage == null) throw new NullPointerException("Cannot compare to null");
        return this.title.compareTo(webPage.title);
    }

    public static void enableId(boolean choice) {
        enableId(choice);
    }
}
