package com.id.misc.xml_csv.ie;


import com.id.misc.xml_csv.model.SMData;
import com.id.misc.xml_csv.model.SMRow;
import com.id.misc.xml_csv.model.SkillLevel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data flow: csv file->CSDData->SMData.
 *
 * @author idanilov
 */
public class CSVImporter extends AbstractImporter {

    public CSVImporter(final File source) {
        super(source);
    }

    public SMData imprt() throws ImportException {
        SMData result = null;
        try {
            CSVData rawData = read();
            if (rawData == null) {
                throw new ImportException("Import error. Unable to read model");
            }
            // System.err.println(">>> raw CSV file:");
            // System.err.println(rawData);
            result = parse(rawData);
        } catch (Exception e) {
            ImportException sme = new ImportException("Import error. See cause.", e);
            throw sme;
        }
        return result;
    }

    /**
     * Reads file.
     *
     * @return raw model as they are in file.
     * @throws IOException
     */
    private CSVData read() throws IOException {
        CSVData result = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(sourceFile));
            String idsLine = reader.readLine();
            if (idsLine != null) {
                result = new CSVData(idsLine);
                String nextLine;
                while ((nextLine = reader.readLine()) != null) {
                    result.addLine(nextLine);
                }
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    // no need to handle.
                }
            }
        }
        return result;
    }

    /**
     * Parses strings and perform integrity checks. Parse process stops (by
     * throwing exception) on first found problem.
     *
     * @param rawData
     * @throws CSVParseException
     */
    private SMData parse(final CSVData rawData) throws CSVParseException {
        List<String> skillIds = parseSkillIdsLine(rawData.getIdsLine());
        int skillIdsCount = skillIds.size();
        SMData result = new SMData(skillIds);
        for (int i = 0; i < rawData.getLines().size(); i++) {
            String nextLine = rawData.getLines().get(i);
            int lineNum = (i + 1) + 1;// 1 is ids line.
            SMRow nextRow = parseLine(nextLine, lineNum);

            // agent ids must be unique.
            String nextAgentId = nextRow.getAgentId();
            if (result.isAgentIdExists(nextAgentId)) {
                throw new CSVParseException("Agent id already exists \"" + nextAgentId + "\".",
                        lineNum);
            }

            // number of skill ids in header must be equal to number of skills
            // in line.
            int nextSkillCount = nextRow.getColumnCount();
            if (skillIdsCount != nextSkillCount) {
                throw new CSVParseException(
                        "Number of skills in line must be equal to number of skill ids ("
                                + skillIdsCount + "!=" + nextSkillCount + ").", lineNum);
            }
            result.addRow(nextRow);
        }
        return result;
    }

    /**
     * @param idsLine
     * @return list of skill ids
     * @throws CSVParseException
     */
    private List<String> parseSkillIdsLine(final String idsLine) throws CSVParseException {
        if (idsLine == null || idsLine.trim().length() == 0) {
            throw new CSVParseException("Header is empty.");
        }
        String[] data = idsLine.split(ImportExportConstant.DELIMETER, -1);
        if (data == null || data.length == 0) {
            throw new CSVParseException("Header doesn't contain delimeter.");
        }
        int skillIdsCount = data.length - 1;
        // System.err.println(">>> skill ids count: " + skillIdsCount);
        if (skillIdsCount == 0) {
            throw new CSVParseException("There are no skill ids in header.");
        }
        // first header cell must be empty because first column contain agent
        // ids.
        if (data[0].trim().length() != 0) {
            throw new CSVParseException("First cell in header must be empty.");
        }
        // collect skill ids.
        List<String> result = new ArrayList<String>(skillIdsCount);
        for (int j = 1; j < data.length; j++) {
            String nextSkillId = data[j].trim();
            if (nextSkillId.length() == 0) {
                String previousSkillId = data[j - 1];
                int offset = idsLine.indexOf(previousSkillId) + previousSkillId.length() + 1 /* delimeter */;
                throw new CSVParseException("Skill id in header should not be empty.", 1,
                        1 + offset);
            }
            if (result.contains(nextSkillId)) {
                throw new CSVParseException("Skill id already exists: \"" + nextSkillId + "\".", 1,
                        1 + idsLine.lastIndexOf(data[j]));
            }
            result.add(nextSkillId);
        }
        return result;
        // System.err.println(">>>skill ids:");
        // System.err.println(skillIds);
    }

    private SMRow parseLine(final String line, final int lineNum) throws CSVParseException {
        if (line == null || line.trim().length() == 0) {
            throw new CSVParseException("Line is empty.", lineNum);
        }
        String[] data = line.split(ImportExportConstant.DELIMETER, -1);
        if (data == null || data.length == 0) {
            throw new CSVParseException("Line doesn't contain delimeter.", lineNum);
        }
        int skillsCount = data.length - 1;
        // System.err.println(">>> skill count in line " + lineNum +" :" +
        // skillCount);
        if (skillsCount == 0) {
            throw new CSVParseException("There are no skills in line.", lineNum);
        }
        // first cell must contain agent id.
        String agentId = data[0].trim();
        if (agentId.length() == 0) {
            throw new CSVParseException("First cell line must not be empty (agent id missed).",
                    lineNum);
        }
        // collect skills.
        List<SkillLevel> skillsList = new ArrayList<SkillLevel>(skillsCount);
        for (int j = 1; j < data.length; j++) {
            String nextSkillValue = data[j].trim();
            SkillLevel nextSkillLevel = new SkillLevel(nextSkillValue);
            if (!nextSkillLevel.isValid()) {
                throw new CSVParseException("Skill value is invalid (" + nextSkillValue + ").",
                        lineNum, 1 + line.indexOf(data[j]));
            }
            skillsList.add(nextSkillLevel);
        }
        SMRow result = new SMRow(agentId, skillsList);
        return result;
    }

    /**
     * Holds raw model - as strings in file. <br>
     * 1st line in file - skill ids,2nd - skill names, then model.
     */
    private static class CSVData {

        private String skillIdsLine;
        private List<String> lines;

        public CSVData(final String idsLine) {
            this.skillIdsLine = idsLine;
            lines = new ArrayList<String>();
        }

        public void addLine(final String line) {
            lines.add(line);
        }

        public String getIdsLine() {
            return skillIdsLine;
        }

        public List<String> getLines() {
            return Collections.unmodifiableList(lines);
        }

        public String toString() {
            StringBuffer result = new StringBuffer();
            if (skillIdsLine != null) {
                result.append(skillIdsLine);
            } else {
                result.append("");
            }
            result.append("\n");
            for (String nextLine : lines) {
                if (nextLine != null) {
                    result.append(nextLine);
                } else {
                    result.append("");
                }
                result.append("\n");
            }
            return result.toString();
        }
    }

    private static class CSVParseException extends Exception {

        private int errorLine;
        private int errorOffset;

        public CSVParseException(String s) {
            this(s, 1);
        }

        public CSVParseException(String s, int line) {
            this(s, line, 1);
        }

        public CSVParseException(String s, int line, int offset) {
            super(s);
            errorLine = line;
            errorOffset = offset;
        }

        public int getErrorLine() {
            return errorLine;
        }

        public int getErrorOffset() {
            return errorOffset;
        }

        public String toString() {
            return super.toString()
                    + (" Parse error at [" + getErrorLine() + "," + getErrorOffset() + "].");
        }
    }

}
