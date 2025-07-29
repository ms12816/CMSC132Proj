package model;

public class TableElement extends TagElement implements Element{
	//Instance variables
	private Element[][] items;
	private int cols;
	private int rows;
	private int cellCount;
	
	public TableElement(int rows, int cols, String attributes) {
		//Call to super
		super("table", true, null, attributes);
		items = new Element[rows][cols];		
		this.cols = cols;
		this.rows = rows;
		//start count of cells at 0
		this.cellCount = 0;
	}
	
	public void addItem(int rowIndex, int colIndex, Element item) {
		//Check for valid arguments
        if (rowIndex < 0 || rowIndex >= rows || colIndex < 0 || colIndex >= cols) {
            throw new IndexOutOfBoundsException("Invalid table indices");
        }
        //Check for null (no currently existing element at desired space
        if(items[rowIndex][colIndex] == null) {
        	cellCount++;
        }
        //Add desired item
		items[rowIndex][colIndex] = item;		
	}
	
    public double getTableUtilization() {
    	//Return percentage of spaces used on table as a double (percentage)
    	return (double) cellCount / (rows * cols) * 100.0;
    }
	
	@Override 
	public String genHTML(int indentation) {
		//Create indent
		String indent = "";
		for(int i = 0; i < indentation; i++) {
			indent += " ";
		}
		//Required table HTML code
		String html = indent + getStartTag() + "\n";
        for (Element[] row : items) {
            html += indent + "  <tr>\n";
            for (Element cell : row) {
                html += indent + "    <td>";
                if(cell != null) {
                	html += cell.genHTML(0);
                } 
                html += "</td>\n";
            }
            html += indent + "  </tr>\n";
        }
        //End tag
        html += indent + getEndTag() + "\n";

        return html;
	}
	
}
