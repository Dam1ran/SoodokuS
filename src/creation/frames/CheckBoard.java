package creation.frames;


import creation.dataworks.StopWatch;
import creation.dataworks.SudokuGenerator;
import creation.filehandlers.Sounds;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;


public class CheckBoard extends JDialog{

    private JPanel contentPane;
    private JLabel textLbl;
    private JButton buttonBack;


    private JLabel operationLbl;

    private SudokuGenerator SG;


    private Parent parent;

    CheckBoard(Parent aParent){

        this.parent=aParent;

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonBack);
        setModal(true);

        onInit();

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        textLbl.setForeground(new Color(220,220,255));

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonBack.addActionListener(e -> onCancel());


        onFire();


    }


    private void onInit() {

        operationLbl.setText("SoodokuS Generator "+'\u2122');
        AncestorListener ancestorListener =new RequestFocusListener();
        buttonBack.addAncestorListener(ancestorListener);

    }

    private void onFire() {

        SG = new SudokuGenerator();
        StopWatch stopWatch = new StopWatch();
        SG.setCells(parent.getSudokuData().getCells());
        SG.start();
        stopWatch.start();

        AtomicInteger dot = new AtomicInteger(-75);
        Timer timer = new Timer(10, e -> {

            if(SG.isAlive()) {

                if(dot.get() >5) dot.set(0);

                if( dot.get()  < 0 ) { operationLbl.setText("Acquiring initial pattern...");   }
                else{
                    operationLbl.setHorizontalAlignment(SwingConstants.LEFT);
                    if( dot.get() == 0 ) operationLbl.setText("Solving initial pattern.     ");
                    if( dot.get() == 1 ) operationLbl.setText("Solving initial pattern..    ");
                    if( dot.get() == 2 ) operationLbl.setText("Solving initial pattern...   ");
                    if( dot.get() == 3 ) operationLbl.setText("Solving initial pattern....  ");
                    if( dot.get() == 4 ) operationLbl.setText("Solving initial pattern..... ");
                    if( dot.get() == 5 ) operationLbl.setText("Solving initial pattern......");

                }

                dot.getAndIncrement();

                buttonBack.setText("Abort");

            }
            if(!SG.isAlive()) {

                if(SG.isFull()) {
                    operationLbl.setHorizontalAlignment(SwingConstants.CENTER);
                    operationLbl.setText("Board Has Solution(s)!");

                    buttonBack.setText("OK");
                    stopWatch.stop();
                    System.out.println("right: "+!parent.fullCheck(SG.getCells()));

                    for (int i=0;i<81;i++)
                    parent.writeField(i,String.valueOf(SG.getCells()[i/9][i%9]));

                    System.out.println("time: "+stopWatch.getElapsedTime().toMillis()/1000f);
                    ((Timer)e.getSource()).stop();

                }else {

                    operationLbl.setText("Board has no solutions!");
                    buttonBack.setText("Close");

                    ((Timer)e.getSource()).stop();

                }

            }

        });
        timer.setRepeats(true);
        timer.start();



    }

    private void onCancel() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        if(SG!=null) {

            SG.abort();
            System.out.println("wtf?: ");
        }

        dispose();

    }



}
