package creation;

import creation.frames.Parent;

import javax.swing.*;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Starter {


    public static void main(String args[]) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

        try{

            ServerSocket socket =
                    new ServerSocket(9999, 10, InetAddress.getLocalHost());
            JFrame soodokusMainFrame = new Parent();

        }catch(java.net.BindException b){
            System.out.println("Already Running...");
        }catch(Exception e){
            System.out.println(e.toString());
        }

    }

}

