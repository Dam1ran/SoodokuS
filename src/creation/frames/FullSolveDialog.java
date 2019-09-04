package creation.frames;


import creation.dataworks.SudokuGenerator;
import creation.filehandlers.Sounds;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;


public class FullSolveDialog extends JDialog{

    private JPanel contentPane;
    private JLabel textLbl;
    private JButton buttonBack;


    private JLabel operationLbl;

    private SudokuGenerator SG;
    private String operation;

    private Parent parent;
    private int element;

    FullSolveDialog(Parent aParent,String aOperation,int aElement){

        setAlwaysOnTop(true);
        element=aElement;
        operation=aOperation;

        parent=aParent;

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


        if(operation.equals("fullSolve")) onFullSolve();
        if(operation.equals("check")) onCheck();
        if(operation.equals("random")) onRandom();
        if(operation.equals("target")) onTarget();
        if(operation.equals("row")) onRandomColRowSquare();
        if(operation.equals("col")) onRandomColRowSquare();
        if(operation.equals("square")) onRandomColRowSquare();
        if(operation.equals("targetRow"))    onTargetColRowSquare();
        if(operation.equals("targetCol"))    onTargetColRowSquare();
        if(operation.equals("targetSquare")) onTargetColRowSquare();
        if(operation.equals("firstDiagonal")) onDiagonals();
        if(operation.equals("secondDiagonal")) onDiagonals();
        if(operation.equals("number")) onNumber();


    }


    private void onNumber(){

        onFullSolve();

    }


    private void onDiagonals(){

        onFullSolve();

    }

    private void onTargetColRowSquare(){

        onFullSolve();

    }

    private void onRandomColRowSquare(){

        onFullSolve();

    }

    private void onInit() {

        operationLbl.setText("SoodokuS Generator "+'\u2122');
        AncestorListener ancestorListener =new RequestFocusListener();
        buttonBack.addAncestorListener(ancestorListener);

    }

    private void onTarget(){

        onFullSolve();
    }

    private void onRandom(){

        onFullSolve();
    }

    private void onCheck(){

        onFullSolve();
    }

    private void onFullSolve() {

        SG = new SudokuGenerator();

        SG.setCells(parent.getSudokuData().getCells());
        SG.start();


        AtomicInteger dot = new AtomicInteger(-75);
        Timer timer = new Timer(10, e -> {

            if(SG.isAlive()) {

                if(dot.get() >5) dot.set(0);

                if( dot.get()  < 0 ) { operationLbl.setText("Acquiring initial pattern...");   }
                else{
                    operationLbl.setHorizontalAlignment(SwingConstants.LEFT);
                    if( dot.get() == 0 ) operationLbl.setText(" Solving initial pattern.     ");
                    if( dot.get() == 1 ) operationLbl.setText(" Solving initial pattern..    ");
                    if( dot.get() == 2 ) operationLbl.setText(" Solving initial pattern...   ");
                    if( dot.get() == 3 ) operationLbl.setText(" Solving initial pattern....  ");
                    if( dot.get() == 4 ) operationLbl.setText(" Solving initial pattern..... ");
                    if( dot.get() == 5 ) operationLbl.setText(" Solving initial pattern......");

                }

                dot.getAndIncrement();

                buttonBack.setText("Abort");

            }
            if(!SG.isAlive()) {

                if(SG.isFull()) {
                    operationLbl.setHorizontalAlignment(SwingConstants.CENTER);

                    if(!Parent.fullCheck(SG.getCells())){
                        operationLbl.setText("Board Has Solution(s)!");
                        buttonBack.setText("OK");
                    }
                    else {
                        operationLbl.setForeground(new Color(100, 0, 0));
                        operationLbl.setText("Wrong Puzzle.");
                        buttonBack.setText("Close");
                    }

                    ((Timer)e.getSource()).stop();

                }else {

                    operationLbl.setForeground(new Color(100, 0, 0));
                    operationLbl.setText("Board has no solution!");
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

        if(operation.equals("fullSolve")) if(buttonBack.getText().equals("OK")) {

            for(int index=0;index<81;index++) {
                if (index==40) {parent.writeField(index,""); continue;}
                parent.writeField(index,String.valueOf(SG.getCells()[index/9][index%9]));
            }

            parent.getCentralField().setEditable(true);

            parent.progressBarText("Solved Board",3000,false);

        }

        if(operation.equals("random")) if(buttonBack.getText().equals("OK")) {

            int pos;

            do {

                pos = SG.getRandomNumberInRange(0, 80);

            } while (parent.getSudokuData().getCell(pos / 9, pos % 9) != 0);

            parent.writeField(pos, String.valueOf(SG.getCells()[pos / 9][pos % 9]));

            parent.progressBarText("Wrote random Cell", 3000, false);

        }

        if(operation.equals("target")) if(buttonBack.getText().equals("OK")) {

            parent.writeField(parent.getSudokuData().getFocusPos(), String.valueOf(SG.getCells()[parent.getSudokuData().getFocusPos()/9][parent.getSudokuData().getFocusPos()%9]));

            parent.progressBarText("Wrote target Cell",3000,false);
        }

        if(operation.equals("row")) if(buttonBack.getText().equals("OK")) {

            int step;
            boolean empty = false;


            do {

                step = SG.getRandomNumberInRange(0, 8);

                for (int index = 0; index < 9; index++) {

                    if (parent.getSudokuData().getCell(step, index) == 0) empty = true;

                }

            } while (!empty);


            for (int index = 0; index < 9; index++) {


                parent.writeField(step * 9 + index, String.valueOf(SG.getCells()[step][index]));

            }

            parent.progressBarText("Wrote random Edge", 3000, false);

        }

        if(operation.equals("col")) if(buttonBack.getText().equals("OK")) {

            int step;
            boolean empty = false;



            do {

                step = SG.getRandomNumberInRange(0, 8);

                for (int index = 0; index < 9; index++) {

                    if (parent.getSudokuData().getCell(index, step) == 0) empty = true;

                }

            } while (!empty);

            for (int index = 0; index < 9; index++) {

                parent.writeField(index * 9 + step, String.valueOf(SG.getCells()[index][step]));

            }

            parent.progressBarText("Wrote random Pillar", 3000, false);

        }

        if(operation.equals("square")) if(buttonBack.getText().equals("OK")) {

            int square;
            boolean empty = false;

            do {

                square = SG.getRandomNumberInRange(0, 8);

                int row = (square / 3) * 3;
                int col = (square % 3) * 3;

                int smallRow = row - row % 3;
                int smallCol = col - col % 3;

                for (int sRow = smallRow; sRow < smallRow + 3; sRow++)
                    for (int sCol = smallCol; sCol < smallCol + 3; sCol++) {

                        if (parent.getSudokuData().getCell(sRow, sCol) == 0) {
                            square = (smallRow * 3 + smallCol) / 3;
                            empty = true;
                        }
                }


            } while (!empty);



            int row = (square / 3) * 3;
            int col = (square % 3) * 3;

            int smallRow = row - row % 3;
            int smallCol = col - col % 3;

            for (int sRow = smallRow; sRow < smallRow + 3; sRow++)
                for (int sCol = smallCol; sCol < smallCol + 3; sCol++) {
                    parent.writeField(sRow * 9 + sCol, String.valueOf(SG.getCells()[sRow][sCol]));
                }

            parent.progressBarText("Wrote random Square", 3000, false);

        }

        if(operation.equals("targetRow")) if(buttonBack.getText().equals("OK")) {

            int step = element/9;

            for(int index=0;index<9;index++){

                parent.writeField(step*9+index, String.valueOf(SG.getCells()[step][index]));

            }

            parent.progressBarText("Wrote target Edge",3000,false);

        }

        if(operation.equals("targetCol")) if(buttonBack.getText().equals("OK")) {

            int step = element%9;

            for(int index=0;index<9;index++){

                parent.writeField(index*9+step, String.valueOf(SG.getCells()[index][step]));

            }

            parent.progressBarText("Wrote target Pillar",3000,false);

        }

        if(operation.equals("targetSquare")) if(buttonBack.getText().equals("OK")) {

            int row = element/9;
            int col = element%9;

            int smallRow = row-row%3;
            int smallCol = col-col%3;

            for (int sRow = smallRow; sRow < smallRow + 3; sRow++)
                for (int sCol = smallCol; sCol < smallCol + 3; sCol++){

                    parent.writeField(sRow*9+sCol, String.valueOf(SG.getCells()[sRow][sCol]));
                }

            parent.progressBarText("Wrote target Square",3000,false);

        }

        if(operation.equals("firstDiagonal")) if(buttonBack.getText().equals("OK")) {

            for(int index=0;index<9;index++){

                parent.writeField(index*9+index, String.valueOf(SG.getCells()[index][index]));

            }

            parent.progressBarText("Wrote first Diagonal",3000,false);

        }

        if(operation.equals("secondDiagonal")) if(buttonBack.getText().equals("OK")) {

            for(int index=0;index<9;index++){

                parent.writeField(index*9+(8-index), String.valueOf(SG.getCells()[index][8-index]));

            }

            parent.progressBarText("Wrote second Diagonal",3000,false);

        }

        if(operation.equals("number")) if(buttonBack.getText().equals("OK")) {

            for(int index=0;index<81;index++) {

                if (SG.getCells()[index/9][index%9]==element)
                    parent.writeField(index,String.valueOf(SG.getCells()[index/9][index%9]));

            }

            String number="";

            switch (element) {

                case 1: {number="One's"; break;}
                case 2: {number="Two's"; break;}
                case 3: {number="Three's"; break;}
                case 4: {number="Four's"; break;}
                case 5: {number="Five's"; break;}
                case 6: {number="Six's"; break;}
                case 7: {number="Seven's"; break;}
                case 8: {number="Eight's"; break;}
                case 9: {number="Nine's"; break;}
                default:break;
            }

            parent.progressBarText("Wrote all "+number,3000,false);

        }


        if(SG!=null) {

            SG.abort();

        }

        dispose();

    }



}
