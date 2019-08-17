package creation.frames;


import creation.filehandlers.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class WelcomeDialog extends JDialog{

    private JPanel contentPane;
    private JLabel textLbl;
    private JButton closeBtn;
    private JLabel msgLbl;
    private JButton dismissBtn;

    private Parent parent;


    WelcomeDialog(Parent aParent){

        parent=aParent;

        setContentPane(contentPane);
        setModal(true);
        setLocation(parent.getLocation().x,parent.getLocation().y);

//        setLocationRelativeTo(null);

        textLbl.setForeground(new Color(220,220,255));

        getRootPane().setDefaultButton(closeBtn);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onClose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        closeBtn.addAncestorListener(new RequestFocusListener());
        closeBtn.addActionListener(e -> onClose());
        dismissBtn.addActionListener(e -> onDismiss());




        msgLbl.setText("<HTML>" +
                "<br>&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp "+
                "Fill in The Board with numbers!<br>"+
                "Do not repeat numbers in the same row column or small square.<br>" +

                "&nbsp &nbsp &nbsp &nbsp Earn Hint Tokens by solving SoodokuS.<br>" +
                "The Hardest SoodokuS and lesser Assistance Level yields<br>"+
                "potential more Hint Tokens, solving quicker (10 minutes ranges[3x10]) also adds bonuses. "+
                "Every start of a game costs 25 Tokens. If active cell not moved in 1 minute then helper will move(except Low Assistance) it for 1 Hint automatically.<br><br>"+
                "&nbsp &nbsp &nbsp &nbsp Using any Hints will tell SoodokuS to solve your board, "+
                "if your board has solutions then it will return requested Hint "+
                "according to first found solution.<br>"+
                "</HTML>");


    }

    private void onDismiss(){

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        parent.getAppData().setShowWelcomeMsg(false);
        onClose();
    }

    private void onClose() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        dispose();

    }


}
