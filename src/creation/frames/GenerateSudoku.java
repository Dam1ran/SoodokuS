package creation.frames;


import creation.dataworks.StopWatch;
import creation.dataworks.SudokuGenerator;
import creation.filehandlers.Sounds;
import creation.sudokudata.AssistanceLevel;
import creation.sudokudata.Difficulty;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;


public class GenerateSudoku extends JDialog{

    private JPanel contentPane;
    private JLabel difficultyLbl;
    private JButton buttonBack;
    private JButton buttonGO;
    private JLabel textLbl;
    private JRadioButton evilRadioBtn;
    private JRadioButton hardRadioBtn;
    private JRadioButton fairRadioBtn;
    private JRadioButton easyRadioBtn;
    private JLabel operationLbl;
    private JLabel potentialLbl;

    private SudokuGenerator SG;
    private StopWatch stopWatch = new StopWatch();

    private boolean retry;
    private String operationMsg;
    private boolean grabBoard;

    boolean isRetry() { return retry; }

    private Parent parent;

    GenerateSudoku(Parent aParent, boolean aGrabBoard){

        setAlwaysOnTop(true);

        this.parent=aParent;

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonGO);
        setModal(true);

        grabBoard=aGrabBoard;

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

        buttonGO.addActionListener(e -> onFire());

        buttonBack.addActionListener(e -> onCancel());

        parent.getSudokuData().setAssistanceLevel(AssistanceLevel.Full);

        ActionListener listener = e -> {


            if (evilRadioBtn.isSelected()) {
                difficultyLbl.setText("Clues: [14-18] - Evil");
                parent.getAppData().setSelectedRadioGenDifficultyBtn(0);
                parent.getSudokuData().setDifficulty(Difficulty.Evil);
            }

            if (hardRadioBtn.isSelected()) {
                difficultyLbl.setText("Clues: [19-26] - Hard");
                parent.getAppData().setSelectedRadioGenDifficultyBtn(1);
                parent.getSudokuData().setDifficulty(Difficulty.Hard);
            }

            if (fairRadioBtn.isSelected()) {
                difficultyLbl.setText("Clues: [27-36] - Fair");
                parent.getAppData().setSelectedRadioGenDifficultyBtn(2);
                parent.getSudokuData().setDifficulty(Difficulty.Fair);
            }

            if (easyRadioBtn.isSelected()) {
                difficultyLbl.setText("Clues: [37-54] - Easy");
                parent.getAppData().setSelectedRadioGenDifficultyBtn(3);
                parent.getSudokuData().setDifficulty(Difficulty.Easy);
            }

            potentialLbl.setText("      Potential Win Hint Tokens: "+(parent.calcTokens()+27));

        };
        evilRadioBtn.addActionListener(listener);
        hardRadioBtn.addActionListener(listener);
        fairRadioBtn.addActionListener(listener);
        easyRadioBtn.addActionListener(listener);

        buttons();

        potentialLbl.setText("      Potential Win Hint Tokens: __");

    }

    private void buttons(){

        int selectedRadioBtn = parent.getAppData().getSelectedRadioGenDifficultyBtn();
        if(selectedRadioBtn==0)       {
            evilRadioBtn.setSelected(true);    evilRadioBtn.doClick(); difficultyLbl.setText("Clues: [14-18] - Evil"); }
        else if (selectedRadioBtn==1) {
            hardRadioBtn.setSelected(true);    hardRadioBtn.doClick(); difficultyLbl.setText("Clues: [19-26] - Hard"); }
        else if (selectedRadioBtn==2) {
            fairRadioBtn.setSelected(true);    fairRadioBtn.doClick(); difficultyLbl.setText("Clues: [27-36] - Fair"); }
        else if (selectedRadioBtn==3) {
            easyRadioBtn.setSelected(true);    easyRadioBtn.doClick(); difficultyLbl.setText("Clues: [37-54] - Easy"); }

    }

    private void onInit() {

        operationMsg="";
        retry=false;
        operationLbl.setText("SoodokuS Generator "+'\u2122');
        AncestorListener ancestorListener =new RequestFocusListener();
        buttonGO.addAncestorListener(ancestorListener);
        evilRadioBtn.setEnabled(false);
        hardRadioBtn.setEnabled(false);
        fairRadioBtn.setEnabled(false);
        easyRadioBtn.setEnabled(false);

    }

    private void onFire() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        //first press on button
        if(SG==null) {

            buttonGO.setEnabled(false);

            SG = new SudokuGenerator();

            if(grabBoard) SG.setCells(parent.getSudokuData().getCells());
            else          SG.generateInitialPattern();

            SG.start();

            stopWatch.start();

            AtomicInteger dot = new AtomicInteger(-75);
            javax.swing.Timer timer = new Timer(10, e -> {

                if(SG.isAlive()) {

                    if(dot.get() >5) dot.set(0);

                    if( dot.get()  < 0 ) { operationLbl.setText("Acquiring initial pattern...");   }
                    else{

                        operationLbl.setHorizontalAlignment(SwingConstants.LEFT);

                        if( dot.get() == 0 ) operationLbl.setText("              Solving initial pattern.     ");
                        if( dot.get() == 1 ) operationLbl.setText("              Solving initial pattern..    ");
                        if( dot.get() == 2 ) operationLbl.setText("              Solving initial pattern...   ");
                        if( dot.get() == 3 ) operationLbl.setText("              Solving initial pattern....  ");
                        if( dot.get() == 4 ) operationLbl.setText("              Solving initial pattern..... ");
                        if( dot.get() == 5 ) operationLbl.setText("              Solving initial pattern......");

                        }

                    dot.getAndIncrement();

                    buttonBack.setText("Restart");
                    retry=true;

                }
                if(!SG.isAlive()) {

                    if(SG.isFull()) {
                        operationLbl.setHorizontalAlignment(SwingConstants.CENTER);
                        stopWatch.stop();
                        operationLbl.setText("Board Ready - "+ (stopWatch.getElapsedTime().toMillis()/1000f)+" s");

                        evilRadioBtn.setEnabled(true);
                        hardRadioBtn.setEnabled(true);
                        fairRadioBtn.setEnabled(true);
                        easyRadioBtn.setEnabled(true);

                        buttonGO.setText("Proceed");
                        buttonGO.setEnabled(true);
                        potentialLbl.setText("      Potential Win Hint Tokens: "+(parent.calcTokens()+27));
                        buttons();
                        buttonBack.setText("Back");
                        retry=false;

                        ((Timer)e.getSource()).stop();

                    }else {

                        operationLbl.setHorizontalAlignment(SwingConstants.CENTER);
                        operationLbl.setText("Initial pattern has no solutions.");
                        buttonBack.setText("Retry");
                        retry=true;
                        ((Timer)e.getSource()).stop();

                    }

                }

            });
            timer.setRepeats(true);
            timer.start();

        //on second press on button
        }else {

            if ( evilRadioBtn.isSelected()  )  {  parent.getAppData().setSelectedRadioGenDifficultyBtn(0);  parent.getSudokuData().setDifficulty(Difficulty.Evil); }
            if ( hardRadioBtn.isSelected()  )  {  parent.getAppData().setSelectedRadioGenDifficultyBtn(1);  parent.getSudokuData().setDifficulty(Difficulty.Hard); }
            if ( fairRadioBtn.isSelected()  )  {  parent.getAppData().setSelectedRadioGenDifficultyBtn(2);  parent.getSudokuData().setDifficulty(Difficulty.Fair); }
            if ( easyRadioBtn.isSelected()  )  {  parent.getAppData().setSelectedRadioGenDifficultyBtn(3);  parent.getSudokuData().setDifficulty(Difficulty.Easy); }


            SG.prepareBoard(parent.getSudokuData().getDifficulty());

            operationMsg="Generated "+parent.getSudokuData().getDifficulty()+" Sudoku";

            if(Parent.fullCheck(SG.getCells())) {operationMsg = "Sudoku is wrong";}
            else {
                parent.getSudokuData().setCells(SG.getCells());
                parent.getSudokuData().setGenerated(true);
            }

            dispose();

        }


    }

    private void onCancel() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }


        if(SG!=null) {

            SG.abort();
            if(retry) dispose();

        }


        if ( evilRadioBtn.isSelected() )   { parent.getAppData().setSelectedRadioGenDifficultyBtn(0); }
        if ( hardRadioBtn.isSelected()  )  { parent.getAppData().setSelectedRadioGenDifficultyBtn(1); }
        if ( fairRadioBtn.isSelected()  )  { parent.getAppData().setSelectedRadioGenDifficultyBtn(2); }
        if ( easyRadioBtn.isSelected()  )  { parent.getAppData().setSelectedRadioGenDifficultyBtn(3); }

        parent.getSudokuData().setDifficulty(Difficulty.Poor);

        dispose();

    }

    String getResponse(){

        return operationMsg;

    }


}
