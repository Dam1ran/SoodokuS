package creation.frames;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ByeDialog extends JDialog{

    private JLabel byeLabel;
    private JPanel byePanel;

    ByeDialog(String aName, Parent aParent) {

        setContentPane(byePanel);

        setAlwaysOnTop(true);

        setModal(true);

        byeLabel.setForeground(new Color(220,220,255));

        byeLabel.setText(aName);

        byePanel.registerKeyboardAction(e -> System.exit(-1), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        aParent.setCloseFlag();

        aParent.onClose();

        aParent.getFio().timedExit(2000);

    }

}
