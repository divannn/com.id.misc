package com.id.misc.xml_csv.ie;

import com.id.misc.xml_csv.model.SMData;
import com.id.misc.xml_csv.model.SMRow;
import com.id.misc.xml_csv.model.SkillLevel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data flow: xml file->DOM->SMData. <br>
 * <strong>Note:</strong> None intergrity checks is perfromed (as in
 * CSVImporter) becuase xml file is not intenteded for manual editing.
 *
 * @author idanilov
 */
public class XMLImporter extends AbstractImporter {

    public XMLImporter(final File source) {
        super(source);
    }

    public SMData imprt() throws ImportException {
        SMData result = null;
        try {
            Document doc = read();
            result = parse(doc);
        } catch (Exception e) {
            ImportException sme = new ImportException(e);
            throw sme;
        }
        return result;
    }

    /**
     * Reads file.
     *
     * @return DOM document
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private Document read() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document result = docBuilder.parse(sourceFile);
        return result;
    }

    private SMData parse(final Document doc) {
        Element rootNode = doc.getDocumentElement();
        NodeList agentsElementsList = rootNode.getElementsByTagName(ImportExportConstant.AGENT);

        List<String> skillIdsList = getSkillIds((Element) agentsElementsList.item(0));
        SMData result = new SMData(skillIdsList);

        for (int i = 0; i < agentsElementsList.getLength(); i++) {
            Element nextAgentE = (Element) agentsElementsList.item(i);
            String nextAgentId = nextAgentE.getAttribute(ImportExportConstant.AGENT_ID_A);
            List<SkillLevel> nextSkillsList = new ArrayList<SkillLevel>();
            NodeList nextSkillsElementsList = nextAgentE
                    .getElementsByTagName(ImportExportConstant.SKILL);
            for (int j = 0; j < nextSkillsElementsList.getLength(); j++) {
                Element nextSkillE = (Element) nextSkillsElementsList.item(j);
                String nextSkillValue = nextSkillE.getAttribute(ImportExportConstant.SKILL_VALUE_A);
                SkillLevel nextSkillLevel = new SkillLevel(nextSkillValue);
                nextSkillsList.add(nextSkillLevel);
            }
            SMRow nextRow = new SMRow(nextAgentId, nextSkillsList);
            result.addRow(nextRow);
        }
        return result;
    }

    /**
     * Set of skill ids must be the same for all agents - so ask first agent to
     * collect them.
     *
     * @param firstAgentE
     * @return list of skill ids
     */
    private List<String> getSkillIds(final Element firstAgentE) {
        List<String> result = new ArrayList<String>();
        NodeList nextSkillsElementsList = firstAgentE
                .getElementsByTagName(ImportExportConstant.SKILL);
        for (int j = 0; j < nextSkillsElementsList.getLength(); j++) {
            Element nextSkillE = (Element) nextSkillsElementsList.item(j);
            String nextSkillId = nextSkillE.getAttribute(ImportExportConstant.SKILL_ID_A);
            result.add(nextSkillId);
        }
        return result;
    }

}
