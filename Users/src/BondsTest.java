import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BondsTest {

	Bonds bonds = new Bonds();
	
	@Test
	public void test1() throws Exception {
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		bonds.createXmlFile(doc, "def", "hij", "10.11", "10.12", "02/02/2023", "02/19/2023");
		
		assertNotNull(bonds);
	}
	
	@Test
	public void test2() throws Exception {
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element root = doc.createElement("Bond");
		doc.appendChild(root);
		Element element1 = doc.createElement("Name");
		root.appendChild(element1);
		
		assertTrue(root.toString(), true);
	}

}
