package creation;

import creation.frames.Parent;
import javax.swing.*;

public class Starter {



    public static void main(String ...varargs) {

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

        JFrame soodokusMainFrame = new Parent();

    }

}

