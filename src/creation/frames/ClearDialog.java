package creation.frames;


import creation.filehandlers.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ClearDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel textLbl;

    private Parent parent;

    ClearDialog(Parent aParent) {

        parent=aParent;

        setContentPane(contentPane);

        getRootPane().setDefaultButton(buttonCancel);

        setModal(true);

        buttonOK.addActionListener(e -> onClear());

        buttonCancel.addActionListener(e -> onCancel());

        textLbl.setForeground(new Color(220,220,255));

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonCancel.addAncestorListener(new RequestFocusListener());

    }

    private void onClear() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        parent.clearBoard();
        parent.progressBarText("Board Cleared!", 4000, true);

        dispose();

    }

    private void onCancel() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        dispose();
    }

}
