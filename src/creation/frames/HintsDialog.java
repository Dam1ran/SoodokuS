package creation.frames;


import creation.filehandlers.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class HintsDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonBack;
    private JLabel textLbl;
    private JButton fullSolveBtn;
    private JButton checkSolutionBtn;
    private JButton randomBtn;
    private JButton targetBtn;
    private JButton singlesBtn;
    private JButton randomRowBtn;
    private JButton randomColBtn;
    private JButton randomSquareBtn;
    private JButton targetRowBtn;
    private JButton targetColBtn;
    private JButton targetSquareBtn;
    private JButton firstDiagonalBtn;
    private JButton secondDiagonalBtn;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;
    private JButton writeSetBtn;
    private JPanel numberPanel;

    private Parent parent;


    HintsDialog(Parent aParent) {

        setAlwaysOnTop(true);

        parent = aParent;

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonBack);

        setModal(true);

        buttonBack.addActionListener(e -> onCancel());

        textLbl.setForeground(new Color(220,220,255));

        textLbl.setText("Hints Menu - Available Tokens: "+parent.getAppData().getGameTokens());

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

        btn1.setBorder(null);
        btn2.setBorder(null);
        btn3.setBorder(null);
        btn4.setBorder(null);
        btn5.setBorder(null);
        btn6.setBorder(null);
        btn7.setBorder(null);
        btn8.setBorder(null);
        btn9.setBorder(null);


        if(parent.getAppData().getGameTokens()>449) fullSolveBtn.setEnabled(true);
        else fullSolveBtn.setEnabled(false);
        fullSolveBtn.addActionListener(e -> onFullSolve());

        if(parent.getAppData().getGameTokens()>4) checkSolutionBtn.setEnabled(true);
        else checkSolutionBtn.setEnabled(false);
        checkSolutionBtn.addActionListener(e -> onCheckSolution());

        if(parent.getAppData().getGameTokens()>9 && parent.isFull()) randomBtn.setEnabled(true);
        else randomBtn.setEnabled(false);
        randomBtn.addActionListener(e -> onRandomCell());

        if(parent.getAppData().getGameTokens()>14) targetBtn.setEnabled(true);
        else targetBtn.setEnabled(false);
        targetBtn.addActionListener(e -> onTargetCell());

        if(parent.getAppData().getGameTokens()>10) singlesBtn.setEnabled(true);
        else singlesBtn.setEnabled(false);
        singlesBtn.addActionListener(e -> onSingles());


        if(parent.getAppData().getGameTokens()>44 && parent.isFull()) {
            randomRowBtn.setEnabled(true);
            randomColBtn.setEnabled(true);
            randomSquareBtn.setEnabled(true);
        }
        else {
            randomRowBtn.setEnabled(false);
            randomColBtn.setEnabled(false);
            randomSquareBtn.setEnabled(false);
        }

        randomRowBtn.addActionListener(e -> onRandomRowColSquare("row"));
        randomColBtn.addActionListener(e -> onRandomRowColSquare("col"));
        randomSquareBtn.addActionListener(e -> onRandomRowColSquare("square"));

        if(parent.getAppData().getGameTokens()>89) {
            targetColBtn.setEnabled(true);
            targetRowBtn.setEnabled(true);
            targetSquareBtn.setEnabled(true);
        }
        else {
            targetRowBtn.setEnabled(false);
            targetColBtn.setEnabled(false);
            targetSquareBtn.setEnabled(false);
        }

        targetRowBtn.addActionListener   (e -> onTargetRowColSquare("targetRow"));
        targetColBtn.addActionListener   (e -> onTargetRowColSquare("targetCol"));
        targetSquareBtn.addActionListener(e -> onTargetRowColSquare("targetSquare"));

        if(parent.getAppData().getGameTokens()>109) {
            firstDiagonalBtn.setEnabled(true);
            secondDiagonalBtn.setEnabled(true);
        }
        else {
            firstDiagonalBtn.setEnabled(false);
            secondDiagonalBtn.setEnabled(false);
        }

        firstDiagonalBtn.addActionListener    (e -> onDiagonals("firstDiagonal"));
        secondDiagonalBtn.addActionListener   (e -> onDiagonals("secondDiagonal"));

        if(parent.getAppData().getGameTokens()>59) {
            writeSetBtn.setEnabled(true);
                    }
        else {
            writeSetBtn.setEnabled(false);
        }


        writeSetBtn.addActionListener(e -> numberPanel.setVisible(true));

        ActionListener listener = e -> onSetNumber( Integer.parseInt(e.getActionCommand()));
        btn1.addActionListener(listener);
        btn2.addActionListener(listener);
        btn3.addActionListener(listener);
        btn4.addActionListener(listener);
        btn5.addActionListener(listener);
        btn6.addActionListener(listener);
        btn7.addActionListener(listener);
        btn8.addActionListener(listener);
        btn9.addActionListener(listener);
    }

    private void onSetNumber(int aNumber){

        parent.getAppData().decreaseTokens(60);
        parent.getFio().onExit();
        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        FullSolveDialog fullSolveDialog = new FullSolveDialog(parent,"number",aNumber);

        setVisible(false);

        fullSolveDialog.setUndecorated(true);
        fullSolveDialog.pack();
        fullSolveDialog.setLocation(parent.getLocation().x + 95, parent.getLocation().y + fullSolveDialog.getHeight()*2 + 11);
        fullSolveDialog.setVisible(true);

        dispose();

    }

    private void onDiagonals(String aElement){

        parent.getAppData().decreaseTokens(110);
        parent.getFio().onExit();
        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        FullSolveDialog fullSolveDialog = new FullSolveDialog(parent,aElement,-1);

        setVisible(false);

        fullSolveDialog.setUndecorated(true);
        fullSolveDialog.pack();
        fullSolveDialog.setLocation(parent.getLocation().x + 95, parent.getLocation().y + fullSolveDialog.getHeight()*2 + 11);
        fullSolveDialog.setVisible(true);


        dispose();


    }

    private void onTargetRowColSquare(String aElement){

        parent.getAppData().decreaseTokens(90);
        parent.getFio().onExit();
        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        FullSolveDialog fullSolveDialog = new FullSolveDialog(parent,aElement,parent.getSudokuData().getFocusPos());


        setVisible(false);

        fullSolveDialog.setUndecorated(true);
        fullSolveDialog.pack();
        fullSolveDialog.setLocation(parent.getLocation().x + 95, parent.getLocation().y + fullSolveDialog.getHeight()*2 + 11);
        fullSolveDialog.setVisible(true);


        dispose();


    }

    private void onRandomRowColSquare(String aElement){

        parent.getAppData().decreaseTokens(45);
        parent.getFio().onExit();
        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        FullSolveDialog fullSolveDialog = new FullSolveDialog(parent,aElement,-1);


        setVisible(false);

        fullSolveDialog.setUndecorated(true);
        fullSolveDialog.pack();
        fullSolveDialog.setLocation(parent.getLocation().x + 95, parent.getLocation().y + fullSolveDialog.getHeight()*2 + 11);
        fullSolveDialog.setVisible(true);


        dispose();


    }

    private void onSingles(){

        parent.getAppData().decreaseTokens(10);
        parent.getFio().onExit();
        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        parent.getCellFreeWeight(true);

        dispose();

    }

    private void onTargetCell(){

        parent.getAppData().decreaseTokens(15);
        parent.getFio().onExit();
        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        FullSolveDialog fullSolveDialog = new FullSolveDialog(parent,"target",-1);

        setVisible(false);

        fullSolveDialog.setUndecorated(true);
        fullSolveDialog.pack();
        fullSolveDialog.setLocation(parent.getLocation().x + 95, parent.getLocation().y + fullSolveDialog.getHeight()*2 + 11);
        fullSolveDialog.setVisible(true);


        dispose();


    }

    private void onRandomCell(){

        parent.getAppData().decreaseTokens(10);
        parent.getFio().onExit();
        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        FullSolveDialog fullSolveDialog = new FullSolveDialog(parent,"random",-1);

        setVisible(false);

        fullSolveDialog.setUndecorated(true);
        fullSolveDialog.pack();
        fullSolveDialog.setLocation(parent.getLocation().x + 95, parent.getLocation().y + fullSolveDialog.getHeight()*2 + 11);
        fullSolveDialog.setVisible(true);


        dispose();


    }

    private void onCheckSolution(){

        parent.getAppData().decreaseTokens(5);
        parent.getFio().onExit();
        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        setVisible(false);
        FullSolveDialog fullSolveDialog = new FullSolveDialog(parent,"check",-1);

        fullSolveDialog.setUndecorated(true);
        fullSolveDialog.pack();
        fullSolveDialog.setLocation(parent.getLocation().x + 95, parent.getLocation().y + fullSolveDialog.getHeight()*2 + 11);
        fullSolveDialog.setVisible(true);

        dispose();
    }

    private void onFullSolve(){


        parent.getAppData().decreaseTokens(450);
        parent.getFio().onExit();
        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        FullSolveDialog fullSolveDialog = new FullSolveDialog(parent,"fullSolve",-1);

        setVisible(false);

        fullSolveDialog.setUndecorated(true);
        fullSolveDialog.pack();
        fullSolveDialog.setLocation(parent.getLocation().x + 95, parent.getLocation().y + fullSolveDialog.getHeight()*2 + 11);
        fullSolveDialog.setVisible(true);


        dispose();
    }

    private void onCancel() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        dispose();
    }


}
