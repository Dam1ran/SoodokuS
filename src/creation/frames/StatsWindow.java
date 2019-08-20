package creation.frames;


import creation.filehandlers.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class StatsWindow extends JDialog {

    private JPanel contentPane;
    private JButton closeBtn;
    private JLabel textLbl;
    private JLabel difficultyLbl;
    private JLabel assistanceLbl;
    private JLabel singlesLbl;
    private JLabel doublesLbl;
    private JLabel triplesLbl;
    private JLabel correctLbl;
    private JLabel potentialLbl;
    private JLabel wonSudokuLbl;
    private JLabel nameLbl;
    private JLabel hintsLbl;
    private JLabel currentProgressLbl;
    private JLabel startedLbl;

    private Parent parent;


    StatsWindow(Parent aParent) {

        setAlwaysOnTop(true);

        parent = aParent;
        setContentPane(contentPane);

        getRootPane().setDefaultButton(closeBtn);

        setModal(true);


        closeBtn.addActionListener(e -> onClose());


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


        nameLbl.setText("  Name: "+parent.getSudokuData().getName());

        currentProgressLbl.setText("  Current Progress: "+String.format("%.1f",parent.getSudokuData().getProgress()/81.0f*100.0f)+" %");

        difficultyLbl.setText("  Difficulty: "+parent.getSudokuData().getDifficulty());

        assistanceLbl.setText("  Assistance Level: "+parent.getSudokuData().getAssistanceLevel());

        int[] frees = parent.getCellFreeWeight(false);

        singlesLbl.setText("  Current Singles: "+frees[0]);

        doublesLbl.setText("  Current Doubles: "+frees[1]);

        triplesLbl.setText("  Current Triples: "+frees[2]);

        correctLbl.setText("  Current Sudoku correct: "+!Parent.fullCheck(parent.getSudokuData().getCells()));

        if(parent.isCreateMode()) potentialLbl.setText("  Potential Hint Tokens: "+(parent.calcTokens()+27));
        else potentialLbl.setText("  Potential Hint Tokens: __");

        startedLbl.setText("  Started Games: "+parent.getAppData().getStartedGames());

        wonSudokuLbl.setText("  Won SoodokuS: "+parent.getAppData().getWonGames());

        hintsLbl.setText("  Available Hint Tokens: "+parent.getAppData().getGameTokens());



    }



    private void onClose() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        dispose();

    }


}
