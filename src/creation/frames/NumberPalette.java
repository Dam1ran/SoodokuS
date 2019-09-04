package creation.frames;

import creation.filehandlers.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TimerTask;


public class NumberPalette extends JDialog {

    private java.util.Timer initTimer = new java.util.Timer();
    private java.util.Timer closeTimer = new java.util.Timer();


    private JPanel contentPane;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;


    NumberPalette(Parent aParent, ArrayList<Integer> aAvailableNumbers, boolean aSkipNumbers, boolean aHighlightSkippedNumbers, int aIndex) {


        setContentPane(contentPane);

        setAlwaysOnTop(true);

        TimerTask initTask = new TimerTask() {
            @Override
            public void run() {

                setVisible(true);

                initTimer.cancel();
                initTimer.purge();
            }
        };
        initTimer.schedule(initTask, 150);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        btn1.setBorder(null);
        btn2.setBorder(null);
        btn3.setBorder(null);
        btn4.setBorder(null);
        btn5.setBorder(null);
        btn6.setBorder(null);
        btn7.setBorder(null);
        btn8.setBorder(null);
        btn9.setBorder(null);


        for (Integer number : aAvailableNumbers) {

            if (aSkipNumbers) {

                switch (number) {

                    case 1: { btn1.setEnabled(true);  btn1.setForeground(Color.BLACK);  break; }
                    case 2: { btn2.setEnabled(true);  btn2.setForeground(Color.BLACK);  break; }
                    case 3: { btn3.setEnabled(true);  btn3.setForeground(Color.BLACK);  break; }
                    case 4: { btn4.setEnabled(true);  btn4.setForeground(Color.BLACK);  break; }
                    case 5: { btn5.setEnabled(true);  btn5.setForeground(Color.BLACK);  break; }
                    case 6: { btn6.setEnabled(true);  btn6.setForeground(Color.BLACK);  break; }
                    case 7: { btn7.setEnabled(true);  btn7.setForeground(Color.BLACK);  break; }
                    case 8: { btn8.setEnabled(true);  btn8.setForeground(Color.BLACK);  break; }
                    case 9: { btn9.setEnabled(true);  btn9.setForeground(Color.BLACK);  break; }
                    default:  break;

                }

            } else {

                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
                btn4.setEnabled(true);
                btn5.setEnabled(true);
                btn6.setEnabled(true);
                btn7.setEnabled(true);
                btn8.setEnabled(true);
                btn9.setEnabled(true);


                if (aHighlightSkippedNumbers) {

                    switch (number) {
                        case 1: { btn1.setForeground(Color.BLACK);  break;  }
                        case 2: { btn2.setForeground(Color.BLACK);  break;  }
                        case 3: { btn3.setForeground(Color.BLACK);  break;  }
                        case 4: { btn4.setForeground(Color.BLACK);  break;  }
                        case 5: { btn5.setForeground(Color.BLACK);  break;  }
                        case 6: { btn6.setForeground(Color.BLACK);  break;  }
                        case 7: { btn7.setForeground(Color.BLACK);  break;  }
                        case 8: { btn8.setForeground(Color.BLACK);  break;  }
                        case 9: { btn9.setForeground(Color.BLACK);  break;  }
                        default:  break;
                    }

                } else {

                    btn1.setEnabled(true);  btn1.setForeground(Color.BLACK);
                    btn2.setEnabled(true);  btn2.setForeground(Color.BLACK);
                    btn3.setEnabled(true);  btn3.setForeground(Color.BLACK);
                    btn4.setEnabled(true);  btn4.setForeground(Color.BLACK);
                    btn5.setEnabled(true);  btn5.setForeground(Color.BLACK);
                    btn6.setEnabled(true);  btn6.setForeground(Color.BLACK);
                    btn7.setEnabled(true);  btn7.setForeground(Color.BLACK);
                    btn8.setEnabled(true);  btn8.setForeground(Color.BLACK);
                    btn9.setEnabled(true);  btn9.setForeground(Color.BLACK);

                }


            }


        }


        ActionListener clickListener = e -> {

            aParent.writeField(aIndex, e.getActionCommand());

            if(!aParent.getAppData().isMuteSounds()) { Sounds.wheelRotate(); }

            onCancel();

        };
        btn1.addActionListener(clickListener);
        btn2.addActionListener(clickListener);
        btn3.addActionListener(clickListener);
        btn4.addActionListener(clickListener);
        btn5.addActionListener(clickListener);
        btn6.addActionListener(clickListener);
        btn7.addActionListener(clickListener);
        btn8.addActionListener(clickListener);
        btn9.addActionListener(clickListener);


        MouseAdapter hoverListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                if (((JButton) e.getSource()).isEnabled()) {

                    ((JButton) e.getSource()).requestFocusInWindow();

                    getRootPane().setDefaultButton((JButton) e.getSource());

                }

                super.mouseEntered(e);
            }
        };
        btn1.addMouseListener(hoverListener);
        btn2.addMouseListener(hoverListener);
        btn3.addMouseListener(hoverListener);
        btn4.addMouseListener(hoverListener);
        btn5.addMouseListener(hoverListener);
        btn6.addMouseListener(hoverListener);
        btn7.addMouseListener(hoverListener);
        btn8.addMouseListener(hoverListener);
        btn9.addMouseListener(hoverListener);


        contentPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                onCancel();
            }
        });
    }

    private void onCancel() {


        TimerTask initTask = new TimerTask() {

            private float opaqueFactor=1.0f;

            @Override
            public void run() {

                opaqueFactor-=0.1;



                if (opaqueFactor<=0) {

                    dispose();
                    initTimer.cancel();
                    initTimer.purge();
                } else


                setOpacity(opaqueFactor);


            }
        };

        closeTimer.schedule(initTask, 50,15);

    }

}


