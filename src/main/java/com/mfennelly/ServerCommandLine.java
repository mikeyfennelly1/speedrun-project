package com.mfennelly;

import org.apache.commons.cli.*;

/**
 * Class to handle CLI implementation
 */
public class ServerCommandLine {
    public static void main(String[] args) {
        Options options = new Options();

        Option serverStartOption = new Option("s","start", true, "Start the server on a port");
        serverStartOption.setArgs(1);
        options.addOption(serverStartOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("s")) {
                String portNumberKeyValue = cmd.getOptionValue("s");

                String[] portNumKVPParts = portNumberKeyValue.split("=");

                if (portNumKVPParts.length == 2) {
                    String portNumKey = portNumKVPParts[0];
                    String portNumberValue = portNumKVPParts[1];

                    System.out.println("Key: " + portNumKey);
                    System.out.println("Value: " + portNumberValue);
                } else {
                    System.out.println("Invalid key-value format. Use key=value.");
                }
            }

        } catch (ParseException e) {
            System.out.println("Invalid Command: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ServerCommandLine", options);
        }
    }
}
