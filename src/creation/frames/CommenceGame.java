package creation.frames;


import creation.filehandlers.Sounds;
import creation.sudokudata.AppData;
import creation.sudokudata.AssistanceLevel;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.event.*;


public class CommenceGame extends JDialog{

    private JPanel contentPane;
    private JRadioButton radioHigh;
    private JRadioButton radioLow;
    private JRadioButton radioMed;
    private JLabel assistLbl;
    private JLabel difficultyLbl;
    private JButton buttonBack;
    private JButton buttonGO;
    private JLabel tokensLbl;

    private String operationMsg;

    private Parent parent;

    CommenceGame(Parent aParent){

        this.parent=aParent;

        setUndecorated(true);

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonGO);
        setModal(true);

        onInit();

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

        buttonGO.addActionListener(e -> onFire());

        buttonBack.addActionListener(e -> onCancel());

        ActionListener listener = e -> {

            if (radioHigh.isSelected()) {
                assistLbl.setText("    Choose Assistance Level: Full");
                parent.getAppData().setSelectedRadioBtn(-1);
            }

            if (radioMed.isSelected()) {
                assistLbl.setText("    Choose Assistance Level: Medium");
                parent.getAppData().setSelectedRadioBtn(0);
            }

            if (radioLow.isSelected()) {
                assistLbl.setText("    Choose Assistance Level: Low");
                parent.getAppData().setSelectedRadioBtn(1);
            }

        };
        radioMed.addActionListener(listener);
        radioLow.addActionListener(listener);
        radioHigh.addActionListener(listener);



        AppData appData = parent.getAppData();
        int selectedRadioBtn = appData.getSelectedRadioBtn();
        if(selectedRadioBtn==-1)      {radioHigh.setSelected(true);   radioHigh.doClick(); }
        else if (selectedRadioBtn==0) {radioMed.setSelected(true);    radioMed.doClick();  }
        else if (selectedRadioBtn==1) {radioLow.setSelected(true);    radioLow.doClick();  }

        tokensLbl.setText("Available Hint Tokens: "+parent.getAppData().getGameTokens());

    }

    private void onInit() {

        operationMsg="";

        AncestorListener ancestorListener =new RequestFocusListener();
        buttonGO.addAncestorListener(ancestorListener);


        difficultyLbl.setText("This SoodokuS is marked as "+parent.getSudokuData().getDifficulty());

    }

    private void onFire() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        if(parent.getAppData().getGameTokens()>=25){

            AssistanceLevel assistanceLevel=null;
            if ( radioHigh.isSelected() )  {  assistanceLevel=AssistanceLevel.Full;   parent.getAppData().setSelectedRadioBtn(-1);  }
            if ( radioMed.isSelected()  )  {  assistanceLevel=AssistanceLevel.Medium; parent.getAppData().setSelectedRadioBtn(0);   }
            if ( radioLow.isSelected()  )  {  assistanceLevel=AssistanceLevel.Low;    parent.getAppData().setSelectedRadioBtn(1);   }

            parent.getSudokuData().setAssistanceLevel(assistanceLevel);

            operationMsg="Set assistance level to "+assistanceLevel;

            dispose();
        }
        else {

            if(parent.getSudokuData().isGenerated()) parent.getSudokuData().clearCells();
            parent.progressBarText("Could Not Start Game: Not Enough Hint Tokens",4000,false);
            dispose();

        }




    }

    private void onCancel() {

        if(!parent.getAppData().isMuteSounds()){ Sounds.notifySound(); }

        if(parent.getSudokuData().isGenerated()) parent.getSudokuData().clearCells();
        dispose();
    }

    String getResponse(){

        return operationMsg;

    }


}
