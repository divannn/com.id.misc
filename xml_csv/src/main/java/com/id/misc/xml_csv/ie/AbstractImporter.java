package com.id.misc.xml_csv.ie;


import com.id.misc.xml_csv.model.SMData;

import java.io.File;

/**
 * @author idanilov
 */
public abstract class AbstractImporter {

    protected File sourceFile;

    public AbstractImporter(final File source) {
        sourceFile = source;
    }

    public abstract SMData imprt() throws ImportException;

}
