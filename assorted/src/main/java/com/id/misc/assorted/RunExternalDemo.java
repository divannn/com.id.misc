package com.id.misc.assorted;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Show how to run some common programs in Windows.
 * @author swinghacks
 * @author idanilov
 *
 */
public class RunExternalDemo {

	public static void main(String[] args) throws Exception {
		String cmd = "cmd.exe /c start ";
		Runtime.getRuntime().exec(cmd + getExplorerCommand());//explorer.
		Runtime.getRuntime().exec(cmd + getWebCommand());//browser.
		Runtime.getRuntime().exec(cmd + getMailerCommand());//mailer.
		Runtime.getRuntime().exec(cmd + getFileCommand());//notepad.
	}

	private static String getExplorerCommand() {
		return "c:\\";
	}

	private static String getWebCommand() {
		return "http://www.google.com";
	}

	private static String getMailerCommand() {
		return "mailto:";
		//return "mailto:author@mybook.com";
	}

	private static String getFileCommand() throws URISyntaxException {
		URL url = RunExternalDemo.class.getResource("version.txt");
		File f = new File(url.toURI());
		return f.getAbsolutePath();
	}
}
