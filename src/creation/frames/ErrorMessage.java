package creation.frames;

import creation.filehandlers.FIO;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class ErrorMessage extends JDialog{

    private JButton closeButton;
    private JLabel errorLabel;
    private JPanel errorPanel;

    ErrorMessage(String aErrorMessage, FIO aFio) {

        setContentPane(errorPanel);

        setAlwaysOnTop(true);

        setModal(true);
        getRootPane().setDefaultButton(closeButton);

        errorLabel.setText(aErrorMessage);

        errorPanel.registerKeyboardAction(e -> System.exit(-1), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        closeButton.addActionListener(e -> System.exit(-1));

        aFio.timedExit(3000);

    }

}
