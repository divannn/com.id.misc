package com.id.misc.xml_csv.ie;

import com.id.misc.xml_csv.model.SMData;

import java.io.File;

/**
 * @author idanilov
 */
public abstract class AbstractExporter {

    protected File targetFile;
    protected SMData data;

    public AbstractExporter(final File target, final SMData d) {
        targetFile = target;
        data = d;
    }

    public abstract void export() throws ExportException;

}
