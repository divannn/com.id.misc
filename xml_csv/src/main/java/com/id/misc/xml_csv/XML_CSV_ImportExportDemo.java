package com.id.misc.xml_csv;


import com.id.misc.xml_csv.ie.*;
import com.id.misc.xml_csv.model.SMData;
import com.id.misc.xml_csv.model.SMRow;
import com.id.misc.xml_csv.model.SkillLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author idanilov
 */
public class XML_CSV_ImportExportDemo {

    public static void main(String args[]) {
        File csvFile = new File("d:\\f.csv");
        SMData data = new SMData(Arrays.asList(new String[]{"sk1", "sk2",}));

        SkillLevel skill1 = new SkillLevel(1);
        SkillLevel skill2 = new SkillLevel(2);
        List<SkillLevel> skills1 = new ArrayList<SkillLevel>();
        skills1.add(skill1);
        skills1.add(skill2);
        SMRow row1 = new SMRow("ag1", skills1);

        SkillLevel skill3 = new SkillLevel(3);
        SkillLevel skill4 = new SkillLevel(4);
        List<SkillLevel> skills2 = new ArrayList<SkillLevel>();
        skills2.add(skill3);
        skills2.add(skill4);
        SMRow row2 = new SMRow("ag2", skills2);

        data.addRow(row1);
        data.addRow(row2);

        // export CSV.
        try {
            new CSVExporter(csvFile, data).export();
        } catch (ExportException ee) {
            ee.printStackTrace();
        }

        // import CSV.
        try {
            SMData d = new CSVImporter(csvFile).imprt();
            System.out.println("CSV file imported sucessfully!");
            // do somthing with result.
        } catch (ImportException ie) {
            ie.printStackTrace();
        }

        File xmlFile = new File("d:\\f.xml");
        // export XML.
        try {
            new XMLExporter(xmlFile, data).export();
        } catch (ExportException ee) {
            ee.printStackTrace();
        }

        // import XML.
        try {
            SMData d = new XMLImporter(xmlFile).imprt();
            System.out.println("XML file imported sucessfully!");
            // do something with result.
        } catch (ImportException ie) {
            ie.printStackTrace();
        }
    }

}
