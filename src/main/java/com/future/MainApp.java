package com.future;

import com.future.message.LocalizeMessage;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

@UtilityClass
@Slf4j
public class MainApp
{
    private Options setOptions() {
        Options options = new Options();

        Option optionConfig = Option.builder("c")
                .required(false)
                .hasArg()
                .desc(LocalizeMessage.getString("CommandLine.Arg.c"))
                .longOpt("config")
                .build();

        Option optionType = Option.builder("t")
                .required(true)
                .hasArg()
                .desc("CommandLine.Arg.t")
                .longOpt("type")
                .build();

        Option optionHelp = Option.builder("h")
                .required(false)
                .desc("CommandLine.Arg.h")
                .longOpt("help")
                .build();

        options.addOption(optionConfig);
        options.addOption(optionType);
        options.addOption(optionHelp);

        return  options;
    }

    public static void main( final String... args ) {

        CommandLine commandLine;
        CommandLineParser parser = new DefaultParser();
        try
        {
            Options options = setOptions();
            commandLine = parser.parse(options, args);

            if(args.length == 0 || commandLine.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "inFuture", options );

                System.exit(1);
            }

            String messageType = "";
            if (commandLine.hasOption("t"))
            {
                messageType = commandLine.getOptionValue("t");
            }
            else {
                log.error(LocalizeMessage.getString("CommandLine.Arg.NoType"));
                System.exit(1);
            }

            log.info("Message Type:", messageType);

        }
        catch (ParseException e)
        {
            log.error("参数解析失败: {} ", e.getMessage());
        }

    }

}

