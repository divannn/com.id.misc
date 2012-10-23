package com.id.misc.xml_csv.ie;

import com.id.misc.xml_csv.model.SMData;
import com.id.misc.xml_csv.model.SMRow;
import com.id.misc.xml_csv.model.SkillLevel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author idanilov
 */
public class CSVExporter extends AbstractExporter {

    public CSVExporter(final File target, final SMData d) {
        super(target, d);
    }

    public void export() throws ExportException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(targetFile));
            writer.write(exportIdsLine());
            writer.newLine();

            List<SMRow> allRows = data.getRowData();
            for (int i = 0; i < allRows.size(); i++) {
                SMRow nextRow = allRows.get(i);
                writer.write(exportRow(nextRow));
                writer.newLine();
            }
        } catch (Exception e) {
            ExportException smee = new ExportException("Export error. See cause.", e);
            throw smee;
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

    private String exportIdsLine() {
        List<String> skillsIds = data.getSkillIds();
        StringBuffer result = new StringBuffer();
        result.append(ImportExportConstant.DELIMETER);
        Iterator<String> it = skillsIds.iterator();
        while (it.hasNext()) {
            result.append(it.next());
            if (it.hasNext()) {
                result.append(ImportExportConstant.DELIMETER);
            }
        }
        return result.toString();
    }

    private String exportRow(final SMRow row) {
        StringBuffer result = new StringBuffer();
        result.append(row.getAgentId());
        result.append(ImportExportConstant.DELIMETER);
        List<SkillLevel> allColumns = row.getSkillData();
        Iterator<SkillLevel> it = allColumns.iterator();
        while (it.hasNext()) {
            SkillLevel nextSkillLevel = it.next();
            result.append(nextSkillLevel.getValue());
            if (it.hasNext()) {
                result.append(ImportExportConstant.DELIMETER);
            }
        }
        return result.toString();
    }

}
