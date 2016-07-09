/*
* Program descriptions:
* This program is a component of Scrola Viewer software. This program functions
* as the browser launcher. The parameter used by this program is the directory
* which contains the images file intended to be displayed using Scrola Viewer.
*/
//package scrola;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.net.*;

public class Scrola {
  public static void main(String[] args) throws IOException {
    String programPath = URLDecoder.decode(Scrola.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");

    // Read the configation file.
    // TEMPORARY: If configuration reader not stable, use below lines to patch-run program.
    //String browserPath = "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"; // "/usr/bin/google-chrome-stable";
    //String template = programPath + "templates/default/default.html";
    Properties scrolaProperties = new Properties();
    InputStream input = null;
    String fileName = "config.properties";
    input = Scrola.class.getClassLoader().getResourceAsStream(fileName);
    scrolaProperties.load(input);

    // Get the property value
    String browserPath = scrolaProperties.getProperty("bropath");
    String templatePath = scrolaProperties.getProperty("tempath");

    // Read and parse the template chosen in the configuration file.
    String[] Parts = {"","","", "","","", "","","", ""}; // 'Parts' stands for 10 parts of a template.

    try (BufferedReader br = new BufferedReader(new FileReader(templatePath))) {
      String line;
      int partIndex = 0; // To determine which index of 'Parts' array String gets to be fulfilled.
      while ((line=br.readLine()) != null) {
        // Process the parsing
        if (line.contains("<!DOCTYPE html")) {
          partIndex = 0;
        }
        else if (line.contains("<!--start-item-body-->")) {
          partIndex = 1;
          line = "";
        }
        else if (line.contains("<!--start-item-title-->")) {
          partIndex = 2;
          line = "";
        }
        else if (line.contains("<!--end-item-title-->")) {
          partIndex = 5;
          line = "";
        }
        else if (line.contains("<!--start-item-img-->")) {
          partIndex = 6;
          line = "";
        }
        else if (line.contains("<!--end-item-img-->")) {
          partIndex = 8;
          line = "";
        }
        else if (line.contains("<!--end-item-body-->")) {
          partIndex = 9;
          line = "";
        }

        // Add the read line into 'Parts' array String, based on partIndex.
        if (partIndex == 2) {
          if (line != null) {
            String[] subParts = line.split("#");
            for (int i=0; i<subParts.length; i++) {
              Parts[i+2] += subParts[i];
            }
          }
        }
        else if (partIndex == 6) {
          if (line != null) {
            String[] subParts = line.split("#");
            for (int i=0; i<subParts.length; i++) {
              Parts[i+6] += subParts[i];
            }
          }
        }
        else {
          Parts[partIndex] += line + "\n";
        }
      }
    }

    // Parameter for this program.
    String directory = args[0];

    // Initialize the variables.
    File folder = new File(directory); // File variable representing the selected folder.
    String[] fileList = folder.list(); // Array to store list of image files.
    File htmlFile = new File(programPath + "templates/default/scrolaviewer.html"); // File variable for creating the new generated HTML file.
    FileWriter htmlWriter = new FileWriter(htmlFile); // FileWriter Object for the process of writing the HTML file.

    // Starts to write the HTML file.
    htmlWriter.write(Parts[0]);
    // Write the XML line
    htmlWriter.write("\t\t<script id=\"items-xml\" type=\"text/xml\">\n");
    htmlWriter.write("\t\t\t<items>\n");
    for (String files:fileList) {
      htmlWriter.write("\t\t\t\t<item>\n");
        htmlWriter.write("\t\t\t\t\t<title>");
          htmlWriter.write(files);
        htmlWriter.write("</title>\n");
        htmlWriter.write("\t\t\t\t\t<img>");
          htmlWriter.write(directory + "/" + files);
        htmlWriter.write("</img>\n");
      htmlWriter.write("\t\t\t\t</item>\n");
    }
    htmlWriter.write("\t\t\t</items>\n");
    htmlWriter.write("\t\t</script>\n");

    for (String files:fileList) {
      htmlWriter.write("\n" + Parts[1]);
        htmlWriter.write("\n" + Parts[2] + files + Parts[3] + files + Parts[4]);
        htmlWriter.write("\n" + Parts[5]);
        htmlWriter.write("\n" + Parts[6] + directory + "/" + files + Parts[7]);
        htmlWriter.write("\n" + Parts[8]);
    }
    htmlWriter.write(Parts[9]);

    htmlWriter.flush();
    htmlWriter.close();

    // Launch the browser.
    String[] process = {browserPath, "%U", System.getProperty("java.class.path") + "/templates/default/scrolaviewer.html"};
    ProcessBuilder browserLauncher = new ProcessBuilder(process);
    Process scrolaViewerBrowser = browserLauncher.start();
  }
}
