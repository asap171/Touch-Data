/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package touchauthentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Sarbjit
 */
public class TouchAuthentication {
 private static final Pattern IPPattern = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    // ipv4 regex
 /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        TouchUI app = new TouchUI();
        app.setVisible(true);
        String ip= getip();
        Connect(ip);
        
    }
       private static String getip(){
            JFrame frame = new JFrame();
            String Ip  = JOptionPane.showInputDialog(frame, "Enter device's IP address:"); 
            if(validate(Ip)==true){
            return Ip;}
            else JOptionPane.showMessageDialog(new JFrame(),"Wrong IP Format.","Inane warning",JOptionPane.WARNING_MESSAGE);
            getip();
            return "";
       }
    private static void Connect(String Ip) {
        final String port= ":5555";
        String workDir = System.getProperty("user.dir");
        Path currentRelativePath = Paths.get("");
        String s;// = currentRelativePath.toAbsolutePath().toString();
        String adb=workDir+"\\adb.exe ";
        String commandConnect="connect "+ Ip+port;
//        String command2="shell getevent -lt /dev/input/event1";
//        System.out.println(adb+command2);
    try {
        Process process = Runtime.getRuntime().exec(adb+commandConnect);
        System.out.println("Output stream"+process.getOutputStream());
        BufferedReader reader=new BufferedReader( new InputStreamReader(process.getInputStream())); 
        while ((s = reader.readLine()) != null){
             System.out.println(s);
//             System.out.println("unable to connect to "+ Ip+port+port);
//             System.out.println(s=="unable to connect to "+port);
           if(s.equals("unable to connect to "+ Ip+port+port)||s.equals("unable to connect to "+port)){ 
            JOptionPane.showMessageDialog(new JFrame(),"Unabale to connect.","Inane warning",JOptionPane.WARNING_MESSAGE);
           Ip= getip();
           Connect(Ip);}
           else if(s.equals("connected to "+ Ip+port+"\n")||s.equals("already connected to"+Ip+port+"\n")){
               JOptionPane.showMessageDialog(new JFrame(),"connected to "+ Ip+port);}
               break;
           }       
    } catch (IOException e) {
    }
//    try {
//        Process process = Runtime.getRuntime().exec(adb+command2);
//        System.out.println("the output stream is "+process.getOutputStream());
//        BufferedReader reader=new BufferedReader( new InputStreamReader(process.getInputStream())); 
//        while ((s = reader.readLine()) != null){
//            System.out.println(s+"inTA");// send to 
//        }                   
//    } catch (IOException e) {
//    }
    }
    public static boolean validate(final String ip) {
        return IPPattern.matcher(ip).matches();
}
    
}
