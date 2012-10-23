package com.id.misc.xml_csv.ie;

import com.id.misc.xml_csv.model.SMData;
import com.id.misc.xml_csv.model.SMRow;
import com.id.misc.xml_csv.model.SkillLevel;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author idanilov
 */
public class XMLExporter extends AbstractExporter {

    private final static String XML_ENCODING = "UTF-8";

    public XMLExporter(final File target, final SMData d) {
        super(target, d);
    }

    public void export() throws ExportException {
        try {
            Document doc = createDOM();
            write(doc);
        } catch (Exception e) {
            ExportException smee = new ExportException(e);
            throw smee;
        }
    }

    private Document createDOM() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFactory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element alarmsE = document.createElement(ImportExportConstant.AGENTS);
        document.appendChild(alarmsE);

        List<SMRow> allRows = data.getRowData();
        for (int i = 0; i < allRows.size(); i++) {
            SMRow nextRow = allRows.get(i);
            alarmsE.appendChild(exportRow(nextRow, document));
        }
        return document;
    }

    private Element exportRow(final SMRow row, final Document document) {
        Element result = document.createElement(ImportExportConstant.AGENT);
        result.setAttribute(ImportExportConstant.AGENT_ID_A, row.getAgentId());

        Element skillsE = document.createElement(ImportExportConstant.SKILLS);

        List<String> skillsIds = data.getSkillIds();
        for (int i = 0; i < skillsIds.size(); i++) {
            String nextSkillId = skillsIds.get(i);

            Element nextSkillE = document.createElement(ImportExportConstant.SKILL);
            nextSkillE.setAttribute(ImportExportConstant.SKILL_ID_A, nextSkillId);

            SkillLevel nextSkillLevel = row.getSkillData().get(i);
            nextSkillE.setAttribute(ImportExportConstant.SKILL_VALUE_A, nextSkillLevel.getValue());
            skillsE.appendChild(nextSkillE);
        }
        result.appendChild(skillsE);
        return result;
    }

    /**
     * Writes DOM to the file.
     *
     * @param doc
     * @throws IOException
     */
    private void write(final Document doc) throws IOException {
        BufferedWriter writer = null;
        XMLSerializer serializer = null;
        try {
            writer = new BufferedWriter(new FileWriter(targetFile));
            serializer = new XMLSerializer(writer, new OutputFormat("xml", XML_ENCODING, true));
            serializer.serialize(doc);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ioe) {
                    // no need to handle.
                }
            }
        }
    }

}
