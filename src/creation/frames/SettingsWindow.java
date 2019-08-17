package creation.frames;


import creation.filehandlers.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SettingsWindow extends JDialog {

    private JPanel contentPane;
    private JButton closeBtn;
    private JLabel textLbl;
    private JButton resetBtn;
    private JButton aboutBtn;
    private JRadioButton numbersPaletteRadioButton;
    private JRadioButton wheelRBtn;
    private JLabel sensitivityLbl;
    private JSpinner sensitivitySpinner;
    private JCheckBox saveOnExitCheckBox;
    private JCheckBox welcomeCheckBox;
    private JCheckBox muteLbl;
    private JCheckBox timerCheckBox;
    private SpinnerNumberModel model;
    private Parent parent;




    SettingsWindow(Parent aParent) {

        parent = aParent;
        setContentPane(contentPane);

        getRootPane().setDefaultButton(closeBtn);

        setModal(true);


        model = new SpinnerNumberModel(parent.getAppData().getMouseWheelSens(), 1, 9, 1);
        sensitivitySpinner.setModel(model);

        closeBtn.addActionListener(e -> onClose());

        resetBtn.addActionListener(e -> onResetPress());

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

        closeBtn.addAncestorListener(new RequestFocusListener());


        if(parent.getAppData().isShowPalette()) {
            numbersPaletteRadioButton.setSelected(true);
            sensitivityLbl.setEnabled(false);
            sensitivitySpinner.setEnabled(false);
        }
        else {
            wheelRBtn.setSelected(true);
            sensitivityLbl.setEnabled(true);
            sensitivitySpinner.setEnabled(true);
        }

        ActionListener listener = e -> {
            if(e.getActionCommand().equals("palette")) {
                parent.getAppData().setShowPalette(true);
                sensitivityLbl.setEnabled(false);
                sensitivitySpinner.setEnabled(false);

            }
            else {
                parent.getAppData().setShowPalette(false);
                sensitivityLbl.setEnabled(true);
                sensitivitySpinner.setEnabled(true);
            }
        };
        numbersPaletteRadioButton.addActionListener(listener);
        wheelRBtn.addActionListener(listener);
        welcomeCheckBox.setSelected(parent.getAppData().isShowWelcomeMsg());

        welcomeCheckBox.addActionListener(e -> parent.getAppData().setShowWelcomeMsg(((JCheckBox)e.getSource()).isSelected()));

        saveOnExitCheckBox.setSelected(parent.getAppData().isSaveOnExit());
        saveOnExitCheckBox.addActionListener(e -> parent.getAppData().setSaveOnExit(((JCheckBox)e.getSource()).isSelected()));
        saveOnExitCheckBox.setToolTipText("Will save Started SoodokuS only under 55 progress");

        muteLbl.setEnabled(!parent.getAppData().isSoundIsDisabled());
        muteLbl.setSelected(parent.getAppData().isMuteSounds());
        muteLbl.addActionListener(e -> parent.getAppData().setMuteSounds(((JCheckBox)e.getSource()).isSelected()));


        timerCheckBox.setSelected(parent.getAppData().isShowTimer());
        timerCheckBox.addActionListener(e -> {
            parent.getAppData().setShowTimer(((JCheckBox)e.getSource()).isSelected());
            parent.timerLabel.setVisible(((JCheckBox)e.getSource()).isSelected());
        });


        aboutBtn.addActionListener(e -> onAbout());



    }

    private void onAbout() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        AboutDialog aboutDialog = new AboutDialog(parent);

        aboutDialog.setUndecorated(true);
        aboutDialog.pack();
        aboutDialog.setLocation(parent.getLocation().x, parent.getLocation().y+1);
        aboutDialog.setVisible(true);

    }


    private void onResetPress(){

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        ResetDialog resetDialog = new ResetDialog(parent);
        resetDialog.setUndecorated(true);
        resetDialog.pack();
        resetDialog.setLocation(parent.getLocation().x+resetDialog.getWidth()/4, parent.getLocation().y + resetDialog.getHeight()*2+71);

        setVisible(false);

        resetDialog.setVisible(true);


        dispose();

    }



    private void onClose() {

        if(!parent.getAppData().isMuteSounds()){ Sounds.notifySound(); }

        parent.getAppData().setMouseWheelSens(model.getNumber().intValue());
        dispose();

    }



}
