package URL_Processor;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author alkyo
 */
public class URL_Processor {
    public String line;
    public PrintWriter outFile;
    public Scanner inFile;
    
   
    public URL_Processor (Scanner inInFile, PrintWriter inOutFile) {
        inFile = inInFile;
        outFile = inOutFile;
    }
    public void processStart () throws MalformedURLException, IOException{ 
        String urlType;
        while (inFile.hasNextLine()){   
            line = inFile.nextLine();
            URL url = getURL(line);
            URLConnection urlConnection = getURLConnection(url);
            urlType = getURLType(line);
            writeToFile(getURLInfo(urlConnection));
            // if the url points to a webpage, do following
            if (urlType.equals("pdf") || urlType.equals("PDF") ){
                getPDF(url);   
            }
            // if the url points to an image, do following
            else if (urlType.equals("jpg") || urlType.equals("gif") || urlType.equals("png")){
                getImage(url);
            }
            //if the url points to a pdf, do following
            else {         
                getWebPage(url, line);
            }
        }
        
    }
    
    public void getWebPage (URL inURL, String inStrURL) throws IOException{
        System.out.println("getWebPage()");
        String path = inURL.getPath();
        String fileName;
        //if home page is url is not in the format of .htm then give a fixed name.
        if (path.lastIndexOf("/")==-1) {
            fileName = inStrURL.substring(inStrURL.lastIndexOf("/")+1) + ".index.htm";
        }
        else {
            fileName = path.substring(path.lastIndexOf("/")+1);
        }
        BufferedReader br = new BufferedReader (new InputStreamReader(inURL.openStream()));
        File outputFile = new File (fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter (outputFile));
        String inLine;
        while ((inLine = br.readLine())!=null){
            bw.write(inLine);
        }
        bw.close();
    }
    
    //inspired by the tutorial from baeldung.com/convert-input-stream-to-a-file
    //ongoing read method (instead of read whole file to buffer first), in case of large pdf file.
    public void getPDF (URL inURL) throws IOException{
        System.out.println("getPDF()");
        //get filename from URL
        String path = inURL.getPath();
        String fileName = path.substring(path.lastIndexOf("/")+1);
        //create inputstream and outputstream with file with same filename.
        InputStream inStream = inURL.openStream();
        OutputStream outStream = new FileOutputStream (new File (fileName));
        byte [] buffer = new byte [1024];
        int byteCount;
        while ((byteCount = inStream.read(buffer))!=-1){
            outStream.write(buffer, 0, byteCount); //(byte array, read starting offset, read how many)
        }
        inStream.close();
        outStream.close();
    }
    
    public static void getImage (URL inURL) throws IOException{ //changed to static method to accommodate phase 3
        String workingDir=System.getProperty("user.dir");
        System.out.println("getImage():Getting image from"+inURL.toString());
        String path = inURL.getPath();
        String fileName = path.substring(path.lastIndexOf("/")+1);
        String extension = path.substring(path.lastIndexOf(".")+1);
        BufferedImage image = ImageIO.read(inURL);
        File outImageFile = new File (workingDir+"/Images/"+fileName);
        ImageIO.write(image, extension, outImageFile);
    }
    
    public String getURLType (String inStrUrl){
        String url = inStrUrl;
        String extension = url.substring(url.lastIndexOf(".")+1);
        return extension;
    }
    
    public String getURLInfo (URLConnection inURLConnection){
        String URLInfo;
        URLInfo = inURLConnection.getURL().toExternalForm()+":\n";
        URLInfo += "Content Type:"+inURLConnection.getContentType()+"\n";
        URLInfo += "Content Length:"+inURLConnection.getContentLength()+"\n";
        URLInfo += "Last Modified:"+ new Date(inURLConnection.getLastModified())+"\n";
        URLInfo += "Expiration:"+inURLConnection.getExpiration()+"\n";
        URLInfo+= "Content Encoding:"+inURLConnection.getContentEncoding()+"\n";
        return URLInfo;
    }
    public URL getURL (String inURL) throws MalformedURLException{
        return new URL (inURL);
    }
    public URLConnection getURLConnection(URL inURL) throws IOException{
        return inURL.openConnection();
    }
    
    public void writeToFile (String inText){
        outFile.println(inText);
        outFile.flush();
    }
    
    
  
}
