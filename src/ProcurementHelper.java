import java.io.IOException;

public class ProcurementHelper {
    public static void main(String[] args) throws IOException {
        int argsIndex=0;
        String infileName=null;
        String outfileName=null;
        if (args.length==0){
            // GUI Mode
            // call main GUI
        }
        else {
            // Command mode
            while (argsIndex < args.length){
                if (args[argsIndex].equals("-i")){
                    argsIndex++;
                    if (args[argsIndex].startsWith("-")) {
                        throw new IllegalArgumentException("argument after -i must be input file path.");
                    }
                    else {
                        infileName = args[argsIndex];
                        argsIndex++;
                    }
                }
                else if (args[argsIndex].equals("-o")){
                    argsIndex++;
                    if (args[argsIndex].startsWith("-")){
                        throw new IllegalArgumentException("argument after -o must be output file path.");
                    }
                    else {
                        outfileName=args[argsIndex];
                        argsIndex++;
                    }
                }
                else
                    throw new IllegalArgumentException("no flags recognizable.");
            }
            // instantiate the main class for the CommandLineMode
            CommandLineMode cmdMode = new CommandLineMode(infileName, outfileName);
            cmdMode.start();
        }
    }
}
