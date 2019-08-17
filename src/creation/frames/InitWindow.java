package creation.frames;

import javax.swing.*;
import java.awt.*;

public class InitWindow extends JDialog{

    private JPanel contentPane;
    private JLabel textLbl;


    InitWindow(){

        setContentPane(contentPane);
        setModal(false);
        textLbl.setForeground(new Color(220,220,255));


    }

}
