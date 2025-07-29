package tests;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PublicTests {

	@Test
	public void pub01TextElementTest1() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3;

		TagElement.resetIds();
		TagElement.enableId(false);
		TextElement element = new TextElement("John");
		String answer = element.genHTML(indentation);

		answer += "\nSecond Element\n";
		element = new TextElement("University of MD");
		answer += element.genHTML(indentation);

		answer += "\nThird Element\n";
		indentation = 6;
		element = new TextElement("University of MD");
		answer += element.genHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub02HeadingElementTestA() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3, level = 1;
		String answer = "";
		String attributes = null;

		TagElement.resetIds();
		TagElement.enableId(false);
		HeadingElement element = new HeadingElement(new TextElement("Introduction"), level, attributes);
		answer = element.genHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub03HeadingElementTest1() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3, level = 1;
		String answer = "";
		String attributes = null;

		TagElement.resetIds();
		TagElement.enableId(false);
		HeadingElement element = new HeadingElement(new TextElement("Introduction"), level, attributes);
		answer = element.genHTML(indentation);

		answer += "\nSecond Element\n";
		element = new HeadingElement(new TextElement("Introduction"), level + 1, attributes);
		answer += element.genHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub04AnchorElementTest1() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3;
		String answer = "", attributes = null;

		TagElement.resetIds();
		TagElement.enableId(false);
		AnchorElement element = new AnchorElement("http://www.cs.umd.edu", "UMD", attributes);
		answer += element.genHTML(indentation);
		answer += "\nSecondElement\n";

		element = new AnchorElement("http://www.cs.umd.edu", "UMD", attributes);
		answer += element.genHTML(indentation);

		answer += "\nThirdElement\n";
		indentation = 6;
		element = new AnchorElement("http://www.cs.umd.edu", "UMD", attributes);
		;
		answer += element.genHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub05ImageElementTest1() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3;
		String answer = "", attributes = null, alt = "Testudo Image";
		int width = 84, height = 111;

		TagElement.resetIds();
		TagElement.enableId(true);
		ImageElement element = new ImageElement("testudo.jpg", width, height, alt, attributes);
		answer += element.genHTML(indentation);
		answer += "\nSecondElement\n";

		indentation = 6;
		element = new ImageElement("testudo.jpg", width, height, alt, attributes);
		answer += element.genHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub06ParagraphElementTest1() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3;
		String answer = "", attributes = null;

		TagElement.resetIds();
		TagElement.enableId(true);
		ParagraphElement element = new ParagraphElement(attributes);
		element.addItem(new TextElement("Fear the turtle"));
		element.addItem(new ImageElement("testudo.jpg", 84, 111, "Testudo Image", attributes));
		element.addItem(new AnchorElement("http://www.cs.umd.edu", "UMD", attributes));
		answer += element.genHTML(indentation);
		answer += "\nSecondElement\n";

		TagElement.enableId(false);
		indentation = 6;
		attributes = "style=\"color:red\"";
		ParagraphElement element2 = new ParagraphElement(attributes);
		element2.addItem(new TextElement("Fear the turtle"));
		element2.addItem(new ImageElement("testudo.jpg", 84, 111, "Testudo Image", ""));
		answer += element2.genHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub07ListElementTest1() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3;
		String answer = "", attributes = null;

		boolean orderedList = false;
		TagElement.resetIds();
		TagElement.enableId(true);
		ListElement element = new ListElement(orderedList, attributes);
		element.addItem(new TextElement("Superman"));
		element.addItem(new AnchorElement("http://www.cs.umd.edu", "UMD", attributes));
		answer += element.genHTML(indentation);
		answer += "\nSecondElement\n";

		indentation = 6;
		orderedList = true;
		attributes = "reversed";
		ListElement element2 = new ListElement(orderedList, attributes);
		element2.addItem(new TextElement("Superman"));
		element2.addItem(new TextElement("Batman"));
		answer += element2.genHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub08TableTest1() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3;
		String attributes = "border=\"1\"", answer = "";

		TagElement.resetIds();
		TagElement.enableId(true);		
		TableElement tableElement = new TableElement(2, 2, attributes);
		tableElement.addItem(0, 0, new TextElement("John"));
		tableElement.addItem(0, 1, new TextElement("Laura"));
		tableElement.addItem(1, 0, new TextElement("Rose"));

		answer += tableElement.genHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub09WebPageTest1() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3;
		String answer = "";

		TagElement.resetIds();
		TagElement.enableId(false);
		WebPage webPage = new WebPage("Example1");
		answer = webPage.getWebPageHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub10WebPageTest2() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		WebPage webPage = new WebPage("Example1");
		int indentation = 3;
		String headingAttributes = null, paragraphAttributes = null, answer = "";

		TagElement.resetIds();
		TagElement.enableId(false);
		webPage.addElement(new HeadingElement(new TextElement("Introduction"), 1, headingAttributes));
		ParagraphElement paragraph = new ParagraphElement(paragraphAttributes);
		paragraph.addItem(new TextElement("Fear the turtle"));
		paragraph.addItem(new ImageElement("testudo.jpg", 200, 300, "Testudo Image", ""));
		webPage.addElement(paragraph);

		answer += webPage.getWebPageHTML(indentation);

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}

	@Test
	public void pub11WebPageTest3() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		WebPage webPage = new WebPage("Example2");
		int indentation = 3;
		String answer = "";

		TagElement.resetIds();
		TagElement.enableId(false);
		TableElement tableElement = new TableElement(2, 2, null);
		tableElement.addItem(0, 0, new TextElement("Dog"));
		tableElement.addItem(1, 1, new TextElement("Cat"));
		webPage.addElement(tableElement);

		tableElement = new TableElement(2, 2, null);
		tableElement.addItem(0, 0, new TextElement("Red"));
		tableElement.addItem(0, 1, new TextElement("Blue"));
		tableElement.addItem(1, 0, new TextElement("Green"));
		tableElement.addItem(1, 1, new TextElement("Yellow"));
		webPage.addElement(tableElement);

		webPage.addElement(new ListElement(true, null));

		answer += webPage.getWebPageHTML(indentation);
		answer += "\n" + webPage.stats();

		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}
	@Test
	public void pub12TableTest() {
		String testName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		int indentation = 3;
		String attributes = "border=\"1\"", answer = "";

		TagElement.resetIds();
		TagElement.enableId(false);
		TableElement tableElement = new TableElement(4, 4, attributes);
		tableElement.addItem(0, 1, new AnchorElement("http://www.umd.edu", "UMD", attributes));
		
		answer += tableElement.genHTML(indentation);
		assertTrue(TestingSupport.isResultCorrect(testName, answer, true));
	}
}
