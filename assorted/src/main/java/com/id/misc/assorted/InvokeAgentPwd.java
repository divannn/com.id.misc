import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class InvokeAgentPwd {

    private static String java_ver = System.getProperty("java.version");
    private static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private static boolean isAix = System.getProperty("os.name").toLowerCase().contains("aix");
    private static String add_LibPath = System.getProperty("addLIBPATH");
    private static String add_LdLibraryPath = System.getProperty("addLD_LIBPATH");

    private static final String PWD = System.getProperty("agentpwd");


    public static void main(String[] args) {
        System.err.printf("Java ver: %s AIX: %b WIN: %b %n", java_ver, isAix, isWindows);

        System.err.printf("add-LibPath: %b  add-LdLibraryPath: %b %n", add_LibPath != null, add_LdLibraryPath != null);
        if (PWD != null) {
            changePassword(PWD.trim());
        } else {
            System.err.println("ERROR : agent PWD is not specified");
        }
    }


    private static void changePassword(String password) {
        String dpaCommand = "/opt/emc/dpa/agent/bin/dpaagent";

        if (isWindows) {
            dpaCommand = "C:/EMC/DPA/agent/bin/dpaagent.exe";
        }
        String failMessage = "Executing '" + dpaCommand + "' was failed. The password was not set.";

        ProcessBuilder pb = new ProcessBuilder(dpaCommand, "--set-credentials", "noninteractive");

        if (!isWindows) {
            String libPath = "/opt/emc/dpa/agent/lib";

            if (isAix) {
                if (add_LdLibraryPath != null) {
                    pb.environment().put("LD_LIBRARY_PATH", libPath);
                    System.err.println("Setting LD_LIBRARY_PATH with: " + libPath);
                }
                if (add_LibPath != null) {
                    pb.environment().put("LIBPATH", libPath);
                    System.err.println("Setting LIBPATH with: " + libPath);
                }
            } else {
                pb.environment().put("LD_LIBRARY_PATH", libPath);
            }
        }

        System.err.println("Executing '" + dpaCommand + " --set-credentials noninteractive'");
        try {
            Process process = pb.start();
            Thread.sleep(1000);//give some time.

            //read error stream from process
            InputStream err = process.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(err));
            if (br.ready()) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.err.println(line);
                }
            }

            //feed pwd to process
            OutputStream outputStream = process.getOutputStream();
            outputStream.write(password.getBytes("UTF-8"));
            outputStream.write(System.lineSeparator().getBytes("UTF-8"));
            outputStream.flush();

            int resultCode = process.waitFor();
            if (resultCode == 0) {
                System.err.println("The agent registration user password was changed successfully.");
            } else {
                System.err.println(failMessage + "    Returned result code: " + resultCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println(failMessage);
        }
    }


}

