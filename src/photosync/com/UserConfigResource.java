package photosync.com;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class UserConfigResource extends File {

	private static final long serialVersionUID = 4978340828067778099L;

	private static Logger logger = Logger.getLogger(UserConfigResource.class);

	private static final String XML_DIRECTORIES = "Directories";
	private static final String XML_INPUT_DIRECTORY = "Input";
	private static final String XML_OUTPUT_DIRECTORY = "Output";

	private File inputDirectory;
	private File outputDirectory;

	public UserConfigResource(final String pathname) throws JDOMException, IOException {
		super(pathname);

		SAXBuilder builder = new SAXBuilder();
		Document document = (Document) builder.build(this);
		Element xmlConfig = document.getRootElement();
		inputDirectory = new File(xmlConfig.getChildText(XML_INPUT_DIRECTORY));
		logger.debug("Input directory : " + inputDirectory.getAbsolutePath());
		outputDirectory = new File(xmlConfig.getChildText(XML_OUTPUT_DIRECTORY));
		logger.debug("Output directory : " + outputDirectory.getAbsolutePath());
	}

	public UserConfigResource(final String pathname, final File iInputDirectory, final File iOutputDirectory) {
		super("config" + File.separator + pathname);
		inputDirectory = iInputDirectory;
		logger.debug("Input directory : " + iInputDirectory.getAbsolutePath());
		outputDirectory = iOutputDirectory;
		logger.debug("Output directory : " + iOutputDirectory.getAbsolutePath());
	}

	public final void saveConfig() throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		org.w3c.dom.Document doc = docBuilder.newDocument();
		org.w3c.dom.Element rootElement = doc.createElement(XML_DIRECTORIES);
		doc.appendChild(rootElement);

		org.w3c.dom.Element input = doc.createElement(XML_INPUT_DIRECTORY);
		input.appendChild(doc.createTextNode(inputDirectory.getAbsolutePath()));
		rootElement.appendChild(input);

		org.w3c.dom.Element output = doc.createElement(XML_OUTPUT_DIRECTORY);
		output.appendChild(doc.createTextNode(outputDirectory.getAbsolutePath()));
		rootElement.appendChild(output);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(this);
		transformer.transform(source, result);

		logger.info("User config saved");
	}

	public final void deleteConfig() {
		this.delete();
	}

	public final File getInputDirectory() {
		return inputDirectory;
	}

	public final File getOutputDirectory() {
		return outputDirectory;
	}
}
