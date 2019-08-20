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

        setAlwaysOnTop(true);

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
                "    The main idea was to be able to implement Enchanted Hybrid Recursive Backtrack solving algorithm." +
                " Which can solve quickly any Sudoku puzzle, even the most difficult(solvable) Sudoku puzzle found was solved in around one second on my machine." +
                "Those puzzles that has no solutions are solved even quicker.\n" +
                "    When Generating new Game, Generator creates initial number pattern, then feed it to SoodokuS Generator™ " +
                "(although SoodokuS Generator™ return different puzzles even from blank board).\n" +
                "    If a solution is found then certain numbers are cleared from the board so all small squares has approximately same number of clues.\n" +
                "Number of remaining clues is set as Difficulty.\n\n" +
                "    This board has assistance which makes Game experience better.\n\n" +
                "    Harder the difficulty, less assistance level, and less time spent leads to more earned Hint Tokens." +
                " Striking row, column or square will give bonus Tokens.\n" +
                "Diagonals constraint is not set, however it can give bonus Tokens.\n\n" +
                "    User can Set his puzzle also, SoodokuS(tar) will count initial clues and it will assign a difficulty to it.\n" +
                "    Adding Hint concept makes this Game little more attractive, User can use Hint Tokens at will, to help solve Sudoku puzzle.\n\n" +
                "   Before Hint is requested, SoodokuS Generator™ will try to solve The board, if there is a solution then Hint is applied at choice\n" +
                "\n" +
                "    In dependency which Assistance level is selected SoodokuS(star) will apply natural restrictions." +
                " Also if mouse wheel is pressed board will help find best possible cell" +
                " to fill in.\n" +
                "    User can use keyboard, mouse wheel or number palette(if enabled) - double click on cell. Up to 8+1 slots available for Save/Load your Sudoku."+
                "\n \n" +
                "    Another thing that I have to mention that I have some programming experience in C++.\n" +
                "    This is my first project in Java. It was intended to get used to Java Programming language. As a by product I was forced to learn JFrames also :). Besides back end logic and algorithms, multiple Java features was used(~6k LOC).\n" +
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
