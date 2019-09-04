package creation.frames;


import creation.filehandlers.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class OptionsDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonExit;
    private JButton buttonBack;
    private JLabel textLbl;
    private JButton markBtn;
    private JButton discardBtn;
    private JButton approveBtn;
    private JButton settingsBtn;
    private JButton statsBtn;

    private Parent parent;

    private static String operationMsg;


    OptionsDialog(Parent aParent) {

        setAlwaysOnTop(true);

        parent = aParent;

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonBack);

        setModal(true);

        operationMsg="";

        buttonExit.addActionListener(e -> onExit());

        buttonBack.addActionListener(e -> onCancel());

        markBtn.addActionListener(e -> onMarkPress());

        discardBtn.addActionListener(e -> onDiscardPress());

        approveBtn.addActionListener(e -> onApprovePress());

        settingsBtn.addActionListener(e -> onSettingsPress());

        statsBtn.addActionListener(e -> onStatsPress());

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

        buttonBack.addAncestorListener(new RequestFocusListener());

        if (parent.getSudokuData().isMarkingNumbers()) {
            discardBtn.setEnabled(true);
            approveBtn.setEnabled(true);
            markBtn.setEnabled(false);

        } else {

            discardBtn.setEnabled(false);
            approveBtn.setEnabled(false);
            if (parent.isCreateMode()) markBtn.setEnabled(true);

        }

    }

    private void onStatsPress() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        setVisible(false);

        StatsWindow statsWindow = new StatsWindow(parent);
        statsWindow.setUndecorated(true);
        statsWindow.pack();
        statsWindow.setLocation(parent.getLocation().x+statsWindow.getWidth()/3-1, parent.getLocation().y+1);
        statsWindow.setVisible(true);


        dispose();

    }

    private void onSettingsPress(){

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        setVisible(false);

        SettingsWindow settingsWindow = new SettingsWindow(parent);
        settingsWindow.setUndecorated(true);
        settingsWindow.pack();
        settingsWindow.setLocation(parent.getLocation().x+settingsWindow.getWidth()/4, parent.getLocation().y+1);
        settingsWindow.setVisible(true);

        dispose();

    }

    private void onApprovePress() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        operationMsg="Approved";
        dispose();

    }

    private void onDiscardPress() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        operationMsg="Discarded";
        dispose();

    }

    private void onMarkPress() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        operationMsg="Marking";
        dispose();

    }

    private void onExit() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        operationMsg="Closed";

        parent.setVisible(false);
        setVisible(false);

        ByeDialog byeDialog = new ByeDialog("Mime, See you soon!",parent);
        byeDialog.setUndecorated(true);
        byeDialog.pack();
        byeDialog.setLocation(parent.getLocation().x, parent.getLocation().y+200);
        byeDialog.setVisible(true);

    }

    private void onCancel() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        operationMsg="";
        dispose();
    }

    String getResponse(){

        return operationMsg;

    }


}
