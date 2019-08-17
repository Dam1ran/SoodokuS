package creation.frames;


import creation.filehandlers.Sounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AboutDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel textLbl;
    private JTextPane textPane;
    private Parent parent;

    AboutDialog(Parent aParent) {

        parent=aParent;

        setContentPane(contentPane);

        getRootPane().setDefaultButton(buttonOK);

        setModal(true);

        buttonOK.addActionListener(e -> onClose());

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

        buttonOK.addAncestorListener(new RequestFocusListener());

        textPane.setText("\tMade By Damiran - 2019\n \n" +
                "SoodokuS(tar) was made as an inspiration to for solving a Sudoku puzzle.\n \n" +
                "    The main idea was to be able to implement Enchanted Recursive Backtrack solving algorithm.\n" +
                "    When Generating new Game, Generator creates initial number patterns, then feed it to SoodokuS Generator™.\n" +
                "If a solution is found then certain numbers are randomly cleared from the board. Number of remaining clues is set as Difficulty.\n" +
                "    This board has assistance which makes Game experience better.\n" +
                "Harder the difficulty, less assistance level, and less time spent leads to more earned Hint Tokens." +
                "Striking row, column or square will give bonus Tokens.\n" +
                "Diagonals constraint is not set, however it can give bonus Tokens.\n" +
                "User can Set his puzzle also, SoodokuS(tar) will count initial clues and it will assign a difficulty to it.\n" +
                "Adding Hint concept makes this Game little more attractive, User can use Hint Tokens at will, to help solve Sudoku puzzle.\n" +
                "Before Hint is requested, SoodokuS Generator™ will try to solve The board, if there is a solution then Hint is applied at choice\n" +
                "\n" +
                "In dependency which Assistance level is selected SoodokuS(star) will apply natural restrictions." +
                " Also if target cell stood still for 1 minute time span the board will help finding best possible cell to fill in.\n" +
                "\n" +
                "Another thing that I have to mention that I have some programming experience in C++.\n" +
                "This is my first project in Java. It was intended to get used to Java Programming language. As a by product I was forced to learn JFrames also :). Besides back end logic and algorithms, multiple Java features was used.\n" +
                "\n" +
                "If you wish to Donate\n" +
                "you will receive a Named copy as a gift!\n" +
                "\n" +
                "Any comments are welcome on:\n" +
                "kilowak.mime.@gmail.com\n" +
                "\n" +
                "Thanks! :)");

        textPane.setCaretPosition(0);


    }

    private void onClose() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        dispose();
    }

}
