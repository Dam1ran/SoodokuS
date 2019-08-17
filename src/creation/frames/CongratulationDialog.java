package creation.frames;


import creation.filehandlers.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class CongratulationDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel textLbl;
    private JLabel earnedLbl;

    private Parent parent;

    CongratulationDialog(Parent aParent, int aWonTokens) {

        parent=aParent;

        setContentPane(contentPane);

        getRootPane().setDefaultButton(buttonOK);

        setModal(true);

        buttonOK.addActionListener(e -> onClose());

        textLbl.setForeground(new Color(220,220,255));

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onClose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonOK.addAncestorListener(new RequestFocusListener());


        earnedLbl.setText("Earned "+aWonTokens+" Hints!");

        if(!parent.getSudokuData().getName().equals("Default")) {

            for(int index=0;index<81;index++) {

                parent.getSudokuData().setNumberBackgroundColor(index/9,index%9,parent.getAppData().getStonedNumberBackgroundColor());

            }

            parent.getSudokuData().setWon(true);
            parent.getFio().saveFile(parent.getSudokuData().getName(),parent.getFio().saveFilenameExtension,parent.getSudokuData());

        }


    }


    private void onClose() {

        if(!parent.getAppData().isMuteSounds()){ Sounds.notifySound(); }


        parent.setPauseFlag();
        parent.clearBoard();

        dispose();
    }

}
