package creation.frames;

import creation.filehandlers.FIO;
import creation.filehandlers.Sounds;
import creation.sudokudata.AppData;
import creation.sudokudata.AssistanceLevel;
import creation.sudokudata.Difficulty;
import creation.sudokudata.SudokuData;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


import static java.awt.event.KeyEvent.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Parent extends JFrame {

    private JDialog initWindow = new InitWindow();

    private JPanel rootPanel;

    private JProgressBar progressBar;
    private JButton saveBtn;
    private JButton loadBtn;
    private JButton createBtn;
    private JButton startStopBtn;
            JLabel  timerLabel;
    private JButton clearBtn;
    private JButton hintBtn;
    private JButton optionsBtn;

/*    private Clip deleteSound;
    private Clip wrongNumberSound;
    private Clip earnedSound;
    private Clip helperSound;
            Clip wheelRotateSound;
            Clip notify;
    private Clip startSound;
    private Clip clearBoardSound;
    private Clip wonGameSound;
    private AudioInputStream audioIn;*/

    private boolean pauseFlag;
    private boolean numberHelperFlag=true;
    private boolean paletteFlag;
    private boolean createMode = true;
    private boolean unsaved = false;
    private boolean timerIsStarted = false;
    private boolean closeFlag=false;
    private boolean strikeRow=false;
    private boolean strikeCol=false;
    private boolean strikeSquare=false;
    private boolean strikeSecondDiagonal=false;
    private boolean strikeFirstDiagonal=false;

    private int yRect =0;
    private NumberPalette numberPalette;
    private int loadIndex=0;

    private FIO fio;
    private SaveDialog saveDialog;
    private LoadDialog loadDialog;
    private GenerateSudoku generateSudoku;
    private OptionsDialog optionsDialog;
    private ClearDialog clearDialog;
    private int animRowStep=0;
    private int animColStep=0;
    private int animFirstDiagonalStep=0;
    private int animSecondDiagonalStep=0;
    private int animSquareStep=0;
    private int[] smallSpiral = new int[] {0,1,2,5,8,7,6,3,4};
    private int[] intermediateSpiral = new int[9];
    private int   drainIndex=0;
    private int[] drainedNumbers = new int[81];

    FIO getFio() { return fio; }
    void setCloseFlag() { this.closeFlag = true; }
    void setPauseFlag() { this.pauseFlag = true; }
    boolean isCreateMode() {  return createMode;  }


    //<editor-fold desc=" TEXTFIELDS DECLARATION">

    //1 row                             //2 row                             //3 row
    private JTextField textField00;     private JTextField textField09;     private JTextField textField18;
    private JTextField textField01;     private JTextField textField10;     private JTextField textField19;
    private JTextField textField02;     private JTextField textField11;     private JTextField textField20;
    private JTextField textField03;     private JTextField textField12;     private JTextField textField21;
    private JTextField textField04;     private JTextField textField13;     private JTextField textField22;
    private JTextField textField05;     private JTextField textField14;     private JTextField textField23;
    private JTextField textField06;     private JTextField textField15;     private JTextField textField24;
    private JTextField textField07;     private JTextField textField16;     private JTextField textField25;
    private JTextField textField08;     private JTextField textField17;     private JTextField textField26;

    //4 row                             //5 row                             //6 row
    private JTextField textField27;     private JTextField textField36;     private JTextField textField45;
    private JTextField textField28;     private JTextField textField37;     private JTextField textField46;
    private JTextField textField29;     private JTextField textField38;     private JTextField textField47;
    private JTextField textField30;     private JTextField textField39;     private JTextField textField48;
    private JTextField textField31;     private JTextField textField40;     private JTextField textField49;
    private JTextField textField32;     private JTextField textField41;     private JTextField textField50;
    private JTextField textField33;     private JTextField textField42;     private JTextField textField51;
    private JTextField textField34;     private JTextField textField43;     private JTextField textField52;
    private JTextField textField35;     private JTextField textField44;     private JTextField textField53;

    //7 row                             //8 row                             //9 row
    private JTextField textField54;     private JTextField textField63;     private JTextField textField72;
    private JTextField textField55;     private JTextField textField64;     private JTextField textField73;
    private JTextField textField56;     private JTextField textField65;     private JTextField textField74;
    private JTextField textField57;     private JTextField textField66;     private JTextField textField75;
    private JTextField textField58;     private JTextField textField67;     private JTextField textField76;
    private JTextField textField59;     private JTextField textField68;     private JTextField textField77;
    private JTextField textField60;     private JTextField textField69;     private JTextField textField78;
    private JTextField textField61;     private JTextField textField70;     private JTextField textField79;
    private JTextField textField62;     private JTextField textField71;     private JTextField textField80;

    //</editor-fold>

    private final Hashtable<Integer, JTextField> fldNames = new Hashtable<>();

    private AppData appData;
    public AppData getAppData() {return appData;}
    private void setAppData(AppData appData) { this.appData = appData;  }

    private SudokuData sudokuData;
    SudokuData getSudokuData() { return sudokuData; }
    void setSudokuData(SudokuData sudokuData) { this.sudokuData = sudokuData; }


    private LocalTime zeroTime = LocalTime.MIN;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Timer timer = new Timer(1000, new ActionListener() {

        public void actionPerformed(ActionEvent evt) {

            //update timer label
            timerLabel.setText(dateFormat.format(zeroTime.plusSeconds(sudokuData.incrementSecondCounter())));

            //update progress bar
            if(!sudokuData.isMarkingNumbers() && !createMode) progressBar.setValue(sudokuData.getProgress());

        }

    });


    public Parent() {


        //<editor-fold desc="FRAME RELATED">

        try {
            URL resource = getClass().getResource("/Icon.png");
            BufferedImage image = ImageIO.read(resource);
            setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }


        setTitle("SoodokuS");

        setContentPane(rootPanel);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 380, 450, 5, 5));
        pack();
        setLocationRelativeTo(null);
        setVisible(false);

        initWindow.setUndecorated(true);
        initWindow.pack();
        initWindow.setLocationRelativeTo(rootPanel);
        initWindow.setVisible(true);


        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) { onClose(); }

        });

        class FrameDragListener extends MouseAdapter {

            private final JFrame frame;
            private Point mouseDownCompCoords = null;

            private FrameDragListener(JFrame frame) {
                this.frame = frame;
            }

            public void mouseReleased(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {
                    mouseDownCompCoords = null;

                }

            }

            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {

                    mouseDownCompCoords = e.getPoint();

                }

            }

            public void mouseDragged(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {
                    Point currCoords = e.getLocationOnScreen();

                    if (mouseDownCompCoords != null) {
                        frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
                    }

                }

            }

        }

        FrameDragListener frameDragListener = new FrameDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);

        //</editor-fold>


        //===================================================================


        init();


        //options btn
        optionsBtn.addActionListener(e -> {

            if(numberPalette!=null) numberPalette.dispose();

            if(!appData.isMuteSounds()) { Sounds.notifySound(); }

            numberHelperFlag=false;

            optionsDialog = new OptionsDialog(this);
            optionsDialog.setUndecorated(true);
            optionsDialog.pack();
            optionsDialog.setLocation(this.getLocation().x+optionsDialog.getWidth()/4, this.getLocation().y + optionsDialog.getHeight()/4-4);
            optionsDialog.setVisible(true);

            if (optionsDialog.getResponse().equals("Marking")) onMarkFire();
            if (optionsDialog.getResponse().equals("Approved")) onApproveFire();
            if (optionsDialog.getResponse().equals("Discarded")) onDiscardFire();
            if (optionsDialog.getResponse().equals("")) progressBar.setString("");


            fldNames.get(sudokuData.getFocusPos()).grabFocus();


        });


        startStopBtn.addActionListener(e -> {

            if(numberPalette!=null) numberPalette.dispose();

            if(!appData.isMuteSounds()) { Sounds.notifySound(); }


            if (!timer.isRunning()) {

                if (!createMode) {

                    startTimer();

                    fldNames.get(sudokuData.getFocusPos()).grabFocus();

                    progressBarText("Started", 1000, !progressBar.getString().equals("Paused"));

                    clearBtn.setEnabled(false);

                    pauseAnim();

                } else {
                    onNewGame();
                }


            } else {

                timer.stop();
                startStopBtn.setText("Play");
                timerLabel.setForeground(Color.GRAY);
                timerIsStarted = false;
                progressBarText("Paused", 1000, false);
                if (unsaved) clearBtn.setEnabled(true);

                pauseAnim();

            }


        });


        //<editor-fold desc="ToDos">


        //todo implement solve/hint


        //</editor-fold>


        //save
        saveBtn.addActionListener(e -> {

            if(numberPalette!=null) numberPalette.dispose();

            if (timerIsStarted) startStopBtn.doClick();

            saveDialog = new SaveDialog(this,fio);

            saveDialog.setUndecorated(true);
            saveDialog.pack();
            saveDialog.setLocation(this.getLocation().x+saveDialog.getWidth()/4, this.getLocation().y + saveDialog.getHeight()+71);
            saveDialog.setVisible(true);



            progressBarText(saveDialog.getResponse(), 4000, false);
            if (!saveDialog.getResponse().equals("")) saveBtn.setEnabled(false);

            startStopBtn.doClick();

        });
        saveBtn.setMnemonic(VK_S);


        //LOAD
        loadBtn.setMnemonic(VK_L);
        loadBtn.addActionListener(e -> loadBtnFire());


        //clear board
        clearBtn.setMnemonic(VK_C);
        clearBtn.addActionListener(e -> {

            if(numberPalette!=null) numberPalette.dispose();

            if(!appData.isMuteSounds()) {Sounds.notifySound();}

            if (unsaved || appData.getSelectedMode() != 0) {

                clearDialog = new ClearDialog(this);
                clearDialog.setUndecorated(true);
                clearDialog.pack();
                clearDialog.setLocation(this.getLocation().x+clearDialog.getWidth()/4, this.getLocation().y + clearDialog.getHeight()*2+71);

                clearDialog.setVisible(true);

            }

        });


        hintBtn.setMnemonic(VK_H);
        hintBtn.addActionListener(e -> {

            if(numberPalette!=null) numberPalette.dispose();

            if(!appData.isMuteSounds()) {Sounds.notifySound();}



            onHintFire();





            System.out.println("==================not done================");

        });


        createBtn.addActionListener(e -> {

            if(numberPalette!=null) numberPalette.dispose();

            if(!appData.isMuteSounds()) {Sounds.notifySound();}

            for(int index=0;index<81;index++) fldNames.get(index).setText("");

            do {

                generateSudoku = new GenerateSudoku(this,false);
                generateSudoku.setUndecorated(true);
                generateSudoku.pack();
                generateSudoku.setLocation(this.getLocation().x+generateSudoku.getWidth()/4, this.getLocation().y + generateSudoku.getHeight()+31);
                generateSudoku.setVisible(true);

            } while(generateSudoku.isRetry());


            if(generateSudoku.getResponse().split(" ")[0].equals("Generated")) {

                progressBarText(generateSudoku.getResponse(),3000,false);

                onNewGame();

            }


        });


        for (int index = 0; index < 81; index++) {

            int tIndex = index;

            //listen actions for fields(input and checks)
            fldNames.get(index).addKeyListener(new KeyAdapter() {

                @Override
                public void keyTyped(KeyEvent e) {

                    if ((e.getKeyChar() > 48 && e.getKeyChar() < 58)
                            && (!checkLegit(tIndex, e.getKeyChar(), sudokuData.isHighlightSkippedNumbers() ) || !sudokuData.isSkipNumbers() )
                            && fldNames.get(sudokuData.getFocusPos()).isEditable() ) {

                        super.keyTyped(e);

                        fldNames.get(tIndex).setText(String.valueOf(e.getKeyChar()));

                        e.consume();

                        updateCell(tIndex);

                    }
                    else {
                        e.consume();
                        updateCell(tIndex);
                    }

                }

                //navigate and key bindings
                @Override
                public void keyPressed(KeyEvent e) {

                         if (e.getKeyCode() == VK_DOWN) {

                        int locY = tIndex / 9 == 8 ? 0 : (tIndex / 9) + 1;
                        int locX = tIndex % 9;

                        fldNames.get(locY * 9 + locX).grabFocus();

                        super.keyPressed(e);

                    }
                    else if (e.getKeyCode() == VK_UP) {

                        int locY = tIndex / 9 == 0 ? 8 : (tIndex / 9) - 1;
                        int locX = tIndex % 9;

                        fldNames.get(locY * 9 + locX).grabFocus();

                        super.keyPressed(e);

                    }
                    else if (e.getKeyCode() == VK_LEFT) {

                        int locY = tIndex / 9;
                        int locX = tIndex % 9 == 0 ? 8 : tIndex % 9 - 1;

                        fldNames.get(locY * 9 + locX).grabFocus();

                        super.keyPressed(e);
                    }
                    else if (e.getKeyCode() == VK_RIGHT) {

                        int locY = tIndex / 9;
                        int locX = tIndex % 9 == 8 ? 0 : tIndex % 9 + 1;

                        fldNames.get(locY * 9 + locX).grabFocus();
                    }
                    else if (e.getKeyCode() == VK_SPACE) {
                        startStopBtn.doClick();

                    }
                    else if (e.getKeyCode() == VK_ESCAPE) {
                        optionsBtn.doClick();

                    }
                    else if (e.getKeyCode() == VK_C) {
                        clearBtn.doClick();

                    }
                    else if (e.getKeyCode() == VK_L) {
                        loadBtn.doClick();

                    }
                    else if (e.getKeyCode() == VK_S) {
                        saveBtn.doClick();

                    }
                    else if (e.getKeyCode() == VK_H) {
                        hintBtn.doClick();

                    }

                }

            });

            //mouse wheel number changer
            fldNames.get(index).addMouseWheelListener(e -> {

                if (!appData.isShowPalette() && fldNames.get(sudokuData.getFocusPos()).hasFocus() && fldNames.get(sudokuData.getFocusPos()).isEditable() ) {

                    if (appData.getGrain() < appData.getMouseWheelSens()) {appData.getAndIncrementGrain(); }

                    else {appData.setGrain(0);

                        if(!appData.isMuteSounds()) { Sounds.wheelRotate(); }

                        ArrayList<Integer> availableNumbers = getAvailableNumbers(sudokuData.getFocusPos(), true);

                        int focusPosRow = sudokuData.getFocusPos() / 9;
                        int focusPosCol = sudokuData.getFocusPos() % 9;


                        //mWheelUp
                        if (e.getWheelRotation() < 0) {

                            //get current number
                            int keyVal = sudokuData.getCells()[focusPosRow][focusPosCol] == 9 ? 0 : sudokuData.getCells()[focusPosRow][focusPosCol];
                            int nextKey;

                            if(sudokuData.isSkipNumbers()) {

                                //get next available number to scroll to
                                nextKey = availableNumbers.get(getNextFreeNumberIndex(availableNumbers, keyVal));

                                if(nextKey!=0) {
                                    fldNames.get(sudokuData.getFocusPos()).setText(String.valueOf(nextKey));
                                    updateCell(sudokuData.getFocusPos());
                                }

                                if (sudokuData.isHighlightSkippedNumbers()) {

                                    if (nextKey > keyVal) {
                                        for (int number = keyVal; number < nextKey; number++) {

                                            checkLegit(sudokuData.getFocusPos(), (char)(number+0x30), true);
                                        }
                                    }

                                    if (nextKey <= keyVal) {
                                        for (int number = keyVal; number <=9; number++) {

                                            checkLegit(sudokuData.getFocusPos(), (char)(number+0x30), true);
                                        }
                                        for (int number = 1; number < nextKey; number++) {

                                            checkLegit(sudokuData.getFocusPos(), (char)(number+0x30), true);

                                        }

                                    }

                                }

                            } else{
                                //get next number
                                nextKey = (sudokuData.getCells()[focusPosRow][focusPosCol] == 9 ? 1 : sudokuData.getCells()[focusPosRow][focusPosCol]+1);

                                fldNames.get(sudokuData.getFocusPos()).setText(String.valueOf(nextKey));
                                updateCell(sudokuData.getFocusPos());

                                if(sudokuData.isHighlightSkippedNumbers()) checkLegit(sudokuData.getFocusPos(), (char)(nextKey+0x30), true);

                            }

                        }
                        //mWheelDown
                        else {

                            int keyVal = sudokuData.getCells()[focusPosRow][focusPosCol] == 0
                                         || sudokuData.getCells()[focusPosRow][focusPosCol] == 1
                                         ? 10 : sudokuData.getCells()[focusPosRow][focusPosCol];

                            int previousKey;

                            if(sudokuData.isSkipNumbers()) {

                                previousKey= availableNumbers.get(getPreviousFreeNumberIndex(availableNumbers, keyVal));

                                if(previousKey!=0) {
                                    fldNames.get(sudokuData.getFocusPos()).setText(String.valueOf(previousKey));
                                    updateCell(sudokuData.getFocusPos());
                                }

                                if (sudokuData.isHighlightSkippedNumbers()) {

                                    if (previousKey < keyVal) {

                                        for (int number = keyVal; number > previousKey; number--) {

                                            checkLegit(sudokuData.getFocusPos(), (char) (number+0x30), true);

                                            }
                                    }

                                    if (previousKey >= keyVal) {

                                        for (int number = keyVal; number >= 1; number--) {

                                            checkLegit(sudokuData.getFocusPos(), (char) (number+0x30), true);

                                        }

                                        for (int number = 9; number > previousKey; number--) {

                                            checkLegit(sudokuData.getFocusPos(), (char) (number+0x30), true);

                                        }

                                    }

                                }

                            }
                            else {
                                previousKey = sudokuData.getCells()[focusPosRow][focusPosCol] == 0
                                              || sudokuData.getCells()[focusPosRow][focusPosCol] == 1
                                              ? 9 : (sudokuData.getCells()[focusPosRow][focusPosCol]-1);

                                fldNames.get(sudokuData.getFocusPos()).setText(String.valueOf(previousKey));
                                updateCell(sudokuData.getFocusPos());

                                if(sudokuData.isHighlightSkippedNumbers()) checkLegit(sudokuData.getFocusPos(), (char)(previousKey+0x30), true);

                            }

                        }

                    }

                }

            });

            //focus and cross highlight
            fldNames.get(index).addFocusListener(new FocusAdapter() {

                @Override
                public void focusGained(FocusEvent e) {

                    super.focusGained(e);

                    paletteFlag=false;
                    numberHelperFlag=true;

                    sudokuData.setFocusPos(tIndex);
                    fldNames.get(tIndex).setCaretPosition(fldNames.get(tIndex).getDocument().getLength());

                    for (int index = 0; index < 81; index++) {
                        fldNames.get(index).setBackground(sudokuData.getNumberBackgroundColors()[index / 9][index % 9]);
                    }

                    int aY = tIndex / 9;
                    int aX = tIndex % 9;


                    for (int step = 0; step < 9; step++) {

                        if (!fldNames.get(aX + step * 9).getBackground().equals(appData.getStonedNumberBackgroundColor())) {

                            fldNames.get(aX + step * 9).setBackground(appData.getCrossColor());

                        }


                        if (!fldNames.get(aY * 9 + step).getBackground().equals(appData.getStonedNumberBackgroundColor())) {

                            fldNames.get(aY * 9 + step).setBackground(appData.getCrossColor());

                        }

                    }

                    if (!sudokuData.getNumberBackgroundColors()[tIndex / 9][tIndex % 9].equals(appData.getStonedNumberBackgroundColor())) {

                        fldNames.get(tIndex).setBackground(Color.WHITE);
                    }
                }

            });

            //delete number by right click on focused field, double click for number palette
            fldNames.get(index).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {

                    if (me.getButton() == MouseEvent.BUTTON3) {

                        if (fldNames.get(tIndex).hasFocus() && fldNames.get(tIndex).isEditable() || appData.getSelectedMode() != 0) {

                            fldNames.get(tIndex).setText("");

                            updateCell(tIndex);

                            alterFieldColor(tIndex, appData.getDeletedBackgroundColor(), 300, "background");

                            if(!appData.isMuteSounds()) {Sounds.delete();}

                        }
                    }

                    if (appData.isShowPalette() && fldNames.get(tIndex).isEditable() && fldNames.get(tIndex).hasFocus() && me.getButton() == MouseEvent.BUTTON1 ) {

                        if(numberPalette!=null) numberPalette.dispose();

                        if(paletteFlag) {showPalette(fldNames.get(tIndex),tIndex); paletteFlag=false;}

                        else{

                            paletteFlag=true;

                        }


                    }

                    super.mouseClicked(me);

                }


            });

        }


        //=========================================================================


    }


    private int getRandomNumberInRange(int aMin, int aMax) {

        if (aMin >= aMax) {
            throw new IllegalArgumentException("Max must be greater than Min");
        }

        return (int)(Math.random() * ((aMax - aMin) + 1)) + aMin;
    }

    void clearBoard() {

        sudokuData=new SudokuData();

        progressBar.setValue(sudokuData.clearProgress());

        for (int index = 0; index < 81; index++) {

            sudokuData.setNumberBackgroundColor(index / 9, index % 9, appData.getDefaultBackgroundColor());
            sudokuData.setNumberColor(index / 9, index % 9, appData.getStonedNumberColor());

            fldNames.get(index).setEditable(true);
            fldNames.get(index).setForeground(appData.getStonedNumberColor());

            fldNames.get(index).setText("");

            fldNames.get(index).setBorder(BorderFactory.
                    createLineBorder(new Color(130, 150, 150), 2, true));

            fldNames.get(index).setFont(appData.getDefaultNumberFont());

        }

        createMode = true;

        //center cursor
        fldNames.get(40).grabFocus();

        loadBtn.setEnabled(true);
        clearBtn.setEnabled(false);
        startStopBtn.setEnabled(false);
        saveBtn.setEnabled(false);
        createBtn.setEnabled(true);


        progressBar.setString("Load/Create or Set your Sudoku");

        //reset timer
        timerLabel.setText("--:--:--");
        timerLabel.setForeground(Color.GRAY);
        timerIsStarted = false;
        timer.stop();

        startStopBtn.setText("Start");

        pauseAnim();

        if(!appData.isMuteSounds()) {Sounds.clear();}

    }

    private void showWelcomeMessage() {

        WelcomeDialog welcomeDialog = new WelcomeDialog(this);

        welcomeDialog.setUndecorated(true);
        welcomeDialog.pack();
        welcomeDialog.setVisible(true);

    }

    private void showPalette(Component component, int aIndex){

        numberPalette = new NumberPalette(this,getAvailableNumbers(aIndex,true), sudokuData.isSkipNumbers(),sudokuData.isHighlightSkippedNumbers(), aIndex);

        numberPalette.setUndecorated(true);
        numberPalette.pack();
        numberPalette.setLocationRelativeTo(component);

    }

    private void pauseAnim() {

        pauseFlag=!pauseFlag;

        class TimedTask extends TimerTask{

            public void run() {

                if(pauseFlag) {if(yRect <372) yRect +=12; else cancel();}
                    else {if(yRect >0) yRect -=12; else cancel();}

                setShape(new RoundRectangle2D.Double(0, yRect, 380, 450- yRect, 5, 5));

            }
        }

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimedTask(), 200, 10);


    }

    private void onHintFire() {



//       /*
       CheckBoard checkBoard = new CheckBoard(this);

        checkBoard.setUndecorated(true);
        checkBoard.pack();
        checkBoard.setLocation(this.getLocation().x+ checkBoard.getWidth()/4, this.getLocation().y + checkBoard.getHeight()+11);
        checkBoard.setVisible(true);
//*/
/*

      for(int i=1;i<=9;i++)
        System.out.println("is absent: "+i+" "+isAbsent(sudokuData.getFocusPos()/9,sudokuData.getFocusPos()%9,i));

*/

//        sortWeights();


/*

        ArrayList<Integer> numberList = new ArrayList<>();

       */
/* for(int myIndex=80;myIndex<81;myIndex++)*//*
 {

           int myIndex=sudokuData.getFocusPos();

            int aXcross = myIndex / 9;
            int aYcross = myIndex % 9;

            if(sudokuData.getCell(aXcross,aYcross)==0) {

                int smallRow = aXcross - aXcross % 3;
                int smallCol = aYcross - aYcross % 3;


                for (int number = 1; number <= 9; number++) {

                    searcher:
                    {

                        //square check
                        for (int row = smallRow; row < smallRow + 3; row++)
                            for (int col = smallCol; col < smallCol + 3; col++)

                                if (sudokuData.getCell(row,col) == number) break searcher;

                        //cross check
                        for (int step = 0; step < 9; step++) {

                            if (sudokuData.getCell(aXcross,step) == number) break searcher;
                            if (sudokuData.getCell(step,aYcross) == number) break searcher;

                        }

                        numberList.add(number);

                    }

                }

            }

        }


        System.out.println("numbers: "+numberList);
*/



        //==========================================================


/*

        int[][] temp = new int[][] {

                { 0, 2, 3,   4, 0, 6,   7, 8, 0 },
                { 4, 0, 6,   7, 8, 9,   1, 0, 3 },
                { 7, 8, 0,   1, 2, 3,   0, 5, 6 },

                { 2, 1, 4,   0, 6, 0,   8, 9, 7 },
                { 0, 6, 5,   8, 0, 7,   2, 1, 0 },
                { 8, 9, 7,   0, 1, 0,   3, 6, 5 },

                { 5, 3, 0,   6, 4, 2,   0, 7, 8 },
                { 6, 0, 2,   9, 7, 8,   5, 0, 1 },
                { 0, 7, 8,   5, 0, 1,   6, 4, 0 }
        };



        for(int i =0;i<81;i++) {
            if(temp[i/9][i%9]!=0) {
            fldNames.get(i).setText(String.valueOf(temp[i/9][i%9]));
            updateCell(i);
            }
        }


*/

        //startStopBtn.setEnabled(true);


/*

        System.out.println(Arrays.toString(getMisses(false)));
        System.out.println("duplicates: "+ fullCheck(sudokuData.getCells()));
*/


        //===========================================================

    }


    private void sortWeights(){

//        int[] array = new int[81];
/*
        class byValue implements Comparator<Map.Entry<Integer,Integer>> {
            public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        }*/

//        byValue cmp = new byValue();


 /*       int[] indexes = new int[81];

        for(int index=0;index<81;index++) {int row = index/9; int col = index%9;

            int counter=0;


            for(int i=1;i<=9;i++) if(isAbsent(row,col,i)) counter++;

            if(sudokuData.getCell(row,col)==0) indexes[index]=counter;

            else indexes[index]=0;
        }


        for(int i=0;i<81;i++)
            System.out.println("index "+i+" "+indexes[i]+" ");

        ArrayList al = new ArrayList();
*/







/*

        class Pair implements Comparable<Pair> {
            private final int index;
            public final int value;

            private Pair(int index, int value) {
                this.index = index;
                this.value = value;
            }

            @Override
            public int compareTo(Pair other) {
                return Integer.compare(this.value, other.value);
            }
        }

*/





/*

        Pair[] pairArray = new Pair[81];

        for(int index=0;index<81;index++) {int row = index/9; int col = index%9;

            int counter=0;

            for(int i=1;i<=9;i++) if(isAbsent(row,col,i)) counter++;

            if(sudokuData.getCell(row,col)==0)
            pairArray[index]= new Pair(index, counter);
            else pairArray[index]=new Pair(index,0);

        }

        Arrays.sort(pairArray);

        for(int i=0;i<81;i++)
        System.out.println("PA index: "+pairArray[i].index+" PA value: "+pairArray[i].value);

        int[] fakingSortedIndex = new int[81];


        for(int i=0;i<81;i++) fakingSortedIndex[i]=pairArray[i].index;


        System.out.println("faking sorted: "+Arrays.toString(fakingSortedIndex));


*/







      /*  class ValueComparator implements Comparator<Integer> {
            Map<Integer, Integer> base;

            public ValueComparator(Map<Integer, Integer> base) {
                this.base = base;
            }


            public int compare(Integer a,Integer b) {
                if (base.get(a) >= base.get(b)) {
                    return 1;
                } else {
                    return -1;
                } // returning 0 would merge keys
            }
        }



        ValueComparator bvc = new ValueComparator(tm);

        System.out.println("is tm: "+tm);

        TreeMap<Integer, Integer> sorted_map = new TreeMap<>(bvc);

        sorted_map.putAll(tm);
        System.out.println("is tm: "+sorted_map);
        */




    }


    private boolean isAbsent(int aRow, int aCol, int aNumber) {

        int smallRow = aRow - aRow%3;
        int smallCol = aCol - aCol%3;

        for(int sRow = smallRow;sRow<smallRow+3;sRow++)
            for(int sCol = smallCol;sCol<smallCol+3;sCol++)

                if (sudokuData.getCell(sRow,sCol) == aNumber) return false;



        for(int step = 0; step<9;step++) {

            if (sudokuData.getCell(step,aCol) == aNumber) return false;
            if (sudokuData.getCell(aRow,step) == aNumber) return false;

        }

        return true;

    }

    boolean fullCheck(int[][] aCells){

        boolean duplicate = false;

        for(int step=0;step<9;step++) {
            for (int first = 0; first < 9; first++)
                for (int second = 0; second < 9; second++) {

                    //row
                    if (first != second && aCells[first][step] == aCells[second][step] && aCells[first][step]!=0) {
                        duplicate = true;
                        break;
                    }

                    //col
                    if (first != second && aCells[step][first] == aCells[step][second]&& aCells[step][second]!=0) {
                        duplicate = true;
                        break;
                    }


                }
        }


        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) {

            int smallRow = row - row % 3;
            int smallCol = col - col % 3;
            outer:
            for (int sRow = smallRow; sRow < smallRow + 3; sRow++)
                for (int sCol = smallCol; sCol < smallCol + 3; sCol++)
                    for (int sRow2 = smallRow; sRow2 < smallRow + 3; sRow2++)
                        for (int sCol2 = smallCol; sCol2 < smallCol + 3; sCol2++)

                            if ( (sRow!=sRow2 || sCol!=sCol2) && aCells[sRow][sCol]==aCells[sRow2][sCol2] && aCells[sRow][sCol]!=0) {
                            duplicate = true;
                            break outer;
                    }


        }

        return duplicate;

    }

    private void onApproveFire() {

        sudokuData.setMarkingNumbers(false);

        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) {int index = row * 9 + col;

            if (sudokuData.getNumberColor(row,col).equals(appData.getMarkNumberColor())) {

                sudokuData.setNumberColor(row,col,appData.getDefaultNumberColor());
                fldNames.get(index).setForeground(appData.getDefaultNumberColor());
                fldNames.get(index).setFont(appData.getDefaultNumberFont());
                updateCell(index);

            }

        }


        progressBarText("Approved Marked numbers",4000,false);

    }

    private void onDiscardFire() {

        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) {int index = row * 9 + col;

            if (sudokuData.getNumberColor(row,col).equals(appData.getMarkNumberColor())) {

                sudokuData.setNumberColor(row,col,appData.getDefaultNumberColor());
                fldNames.get(index).setForeground(appData.getDefaultNumberColor());
                fldNames.get(index).setFont(appData.getDefaultNumberFont());
                fldNames.get(index).setText("");
                updateCell(index);

            }

        }

        sudokuData.setMarkingNumbers(false);

        progressBarText("Discarded Marked numbers",4000,false);

    }

    private void onMarkFire() {

        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) {int index = row * 9 + col;

            if (sudokuData.getCells()[row][col]==0) {

                sudokuData.setNumberColor(row,col,appData.getMarkNumberColor());
                fldNames.get(index).setForeground(appData.getMarkNumberColor());
                fldNames.get(index).setFont(appData.getMarkedNumberFont());

            }

        }

        sudokuData.setMarkingNumbers(true);

        progressBarText("Using Marked numbers",4000,false);

    }

    int[] getCellFreeWeight(boolean aWriteSingles){

        ArrayList<Integer> listOfAvailableNumbers;

        int singles = 0;
        int doubles = 0;
        int triples = 0;

        ArrayList<Integer> listOfSinglesIndexes = new ArrayList<>();
        ArrayList<Integer> listOfDoublesIndexes = new ArrayList<>();
        ArrayList<Integer> listOfTriplesIndexes = new ArrayList<>();

        for (int row = 0; row < 9; row++) for (int col = 0; col < 9; col++) { int index = row * 9 + col;

            if (sudokuData.getCell(row, col) == 0) { listOfAvailableNumbers = getAvailableNumbers(index, true);

                if (listOfAvailableNumbers.size() == 1) {
                    if(aWriteSingles) {
                        fldNames.get(index).setText(listOfAvailableNumbers.get(0).toString());
                        updateCell(index);
                    }

                    listOfSinglesIndexes.add(index);
                    singles++;
                }

                if (listOfAvailableNumbers.size() == 2) {

                    listOfDoublesIndexes.add(index);
                    doubles++;

                }

                if (listOfAvailableNumbers.size() == 3) {

                    listOfTriplesIndexes.add(index);
                    triples++;
                }

            }

        }


        int focusTo=-1;

        if(singles!=0) {

            if(singles!=1) focusTo = listOfSinglesIndexes.get(getRandomNumberInRange(0,listOfSinglesIndexes.size()-1));
                    else focusTo = listOfSinglesIndexes.get(0);

        } else if(doubles!=0) {

            if(doubles!=1) focusTo = listOfDoublesIndexes.get(getRandomNumberInRange(0,listOfDoublesIndexes.size()-1));
                    else focusTo = listOfDoublesIndexes.get(0);

        } else if(triples!=0) {

            if(triples!=1)  focusTo = listOfTriplesIndexes.get(getRandomNumberInRange(0,listOfTriplesIndexes.size()-1));
                    else focusTo = listOfTriplesIndexes.get(0);
        }


        return new int[]{singles,doubles,triples,focusTo};

    }

    private void startFocusPosHelper() {

        class FocusHelper extends TimerTask{

           private int focusTo=-1;

            public void run() {

                if(!createMode && timerIsStarted && numberHelperFlag && sudokuData.getAssistanceLevel()!= AssistanceLevel.Low) {

                    if(focusTo==sudokuData.getFocusPos()) {

                        if(getCellFreeWeight(false)[3]!=-1) {

                            int pos = getCellFreeWeight(false)[3];

                            if(pos!=sudokuData.getFocusPos()) {

                                fldNames.get(pos).grabFocus();

                                sudokuData.setFocusPos(pos);

                                appData.decreaseTokens(1);

                                if (fio.onExit()) progressBarText("Error", 2500, false);

                                hintBtn.setToolTipText("Available Hint Tokens: " + appData.getGameTokens());

                                progressBarText("Target Cell Moved, Hint Token Deducted", 4000, false);

                            }

                            if (!appData.isMuteSounds()) {Sounds.helper(); }

                        }

                    } else{focusTo=sudokuData.getFocusPos();}

                }

            }
        }

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new FocusHelper(), 1000, 30000);

    }

    private void onNewGame(){

        CommenceGame commenceGame = new CommenceGame(this);

        commenceGame.setUndecorated(true);
        commenceGame.pack();
        commenceGame.setLocation(this.getLocation().x+ commenceGame.getWidth()/4, this.getLocation().y + commenceGame.getHeight()+11);
        commenceGame.setVisible(true);


        if (!commenceGame.getResponse().equals("")) { createMode=false;

            prepareGame();

            progressBarText(commenceGame.getResponse(),4000,false);

            startTimer();
            fldNames.get(sudokuData.getFocusPos()).grabFocus();
            saveBtn.setEnabled(true);
        }else {
            createMode=true;
        }

    }

    private void prepareGame(){

        for(int index=0;index<81;index++) {fldNames.get(index).setText("");}

        loadIndex=0;
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new LoadNumbers(), 200, 10);


        for(int row=0;row<9;row++)
            for(int col=0;col<9;col++) {  int index = row * 9 + col;

                if (sudokuData.getCells()[row][col] != 0) {

                    sudokuData.setNumberColor(row,col,appData.getStonedNumberColor());
                    sudokuData.setNumberBackgroundColor(row,col,appData.getStonedNumberBackgroundColor());


                    fldNames.get(index).setForeground(appData.getStonedNumberColor());
                    fldNames.get(index).setBackground(appData.getStonedNumberBackgroundColor());


                    //alter border
                    fldNames.get(index).setBorder(BorderFactory.
                            createLineBorder(appData.getStonedBorderColor(), 2, true));

                    //alter font for stoned
                    fldNames.get(index).setFont(appData.getStonedNumberFont());

                    fldNames.get(index).setEditable(false);

                } else {

                    sudokuData.setNumberBackgroundColor(row,col,appData.getDefaultBackgroundColor());
                    sudokuData.setNumberColor(row,col,appData.getDefaultNumberColor());

                    fldNames.get(index).setForeground(appData.getDefaultNumberColor());
                    fldNames.get(index).setBackground(appData.getDefaultBackgroundColor());

                }

            }


        clearBtn.setEnabled(false);
        createBtn.setEnabled(false);
        loadBtn.setEnabled(false);
        appData.addStartedGames();
        appData.decreaseTokens(25);

        fio.saveFile("Unique",fio.saveFilenameExtension,this.getSudokuData());

        if(fio.onExit()) progressBarText("Error!",3000,false);

        if(!appData.isMuteSounds()) {Sounds.commence();}

    }

    private void loadBtnFire(){

        if(numberPalette!=null) numberPalette.dispose();

        if(!appData.isMuteSounds()) {{Sounds.notifySound();}}

        loadDialog = new LoadDialog(this, fio);
        loadDialog.setUndecorated(true);
        loadDialog.pack();
        loadDialog.setLocation(this.getLocation().x+ loadDialog.getWidth()/4, this.getLocation().y + loadDialog.getHeight()+71);
        loadDialog.setVisible(true);


        if (loadDialog.getResponse().split(" ")[0].equals("Loaded")) {

            onLoadGame();

        }else if (!loadDialog.getResponse().equals(""))

        progressBarText(loadDialog.getResponse(), 4000, false);

    }

    private void onLoadGame(){

        loadIndex=0;
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new LoadNumbers(), 200, 10);

        createMode = false;

        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) { int index = row * 9 + col;

            fldNames.get(index).setForeground(sudokuData.getNumberColors()[row][col]);
            fldNames.get(index).setBackground(sudokuData.getNumberBackgroundColors()[row][col]);

            //stoned
            if (sudokuData.getNumberBackgroundColor(row, col).equals(appData.getStonedNumberBackgroundColor())) {

                fldNames.get(index).setEditable(false);
                //alter border
                fldNames.get(index).setBorder(BorderFactory.
                        createLineBorder(appData.getStonedBorderColor(), 2, true));

                //alter font for stoned
                fldNames.get(index).setFont(appData.getStonedNumberFont());

            }

            //marked
            if (sudokuData.getNumberColor(row, col).equals(appData.getMarkNumberColor())) {

                fldNames.get(index).setFont(appData.getMarkedNumberFont());

            }

        }

        if (!timerIsStarted) startTimer();

        timerLabel.setText(dateFormat.format(zeroTime.plusSeconds(sudokuData.incrementSecondCounter())));

        loadBtn.setEnabled(false);
        startStopBtn.setEnabled(true);

        createBtn.setEnabled(false);

        fldNames.get(sudokuData.getFocusPos()).grabFocus();

        if(loadDialog!=null) progressBarText(loadDialog.getResponse(), 4000, false);

        if(!appData.isMuteSounds()) {Sounds.commence();}

    }

    private boolean checkLegit(int aIndex, char aKey, boolean aAlterColor){

        boolean result = false;

        //square check
        int aY = Integer.parseInt(fldNames.get(aIndex).getName())/9;
        int aX = Integer.parseInt(fldNames.get(aIndex).getName())%9;

        int yStart = max(0,aY-1); int yEnd = min(8,aY+1);
        int xStart = max(0,aX-1); int xEnd = min(8,aX+1);

        for(int row=yStart;row<=yEnd;row++)
            for(int col=xStart;col<=xEnd;col++){

                //check square except self
                if ((row*9+col)!=aIndex && Integer.toString(sudokuData.getCells()[row][col]).equals(Character.toString(aKey)) ) {

                    if(aAlterColor) alterFieldColor(row*9+col,appData.getHighlightNumberColor(),200,"foreground");

                    if(!appData.isMuteSounds() && aAlterColor && sudokuData.getCells()[row][col]!=0) {Sounds.wrongNumber();}

                    result = true;

                }

            }


        //cross check

        int aYcross = aIndex/9;
        int aXcross = aIndex%9;

        for(int step=0;step<9;step++){


            if ((aYcross*9+step)!=aIndex && Integer.toString(sudokuData.getCells()[aYcross][step]).equals(Character.toString(aKey)) ) {

                if(aAlterColor) alterFieldColor(aYcross*9+step,appData.getHighlightNumberColor(),200,"foreground");

                if(!appData.isMuteSounds() && aAlterColor && sudokuData.getCell(aYcross,step)!=0) {Sounds.wrongNumber();}

                result = true;

            }

            if ((step*9+aXcross)!=aIndex && Integer.toString(sudokuData.getCells()[step][aXcross]).equals(Character.toString(aKey)) ) {

                if(aAlterColor) alterFieldColor(step*9+aXcross,appData.getHighlightNumberColor(),200,"foreground");

                if(!appData.isMuteSounds() && aAlterColor && sudokuData.getCell(step,aXcross)!=0) {Sounds.wrongNumber();}

                result = true;

            }


        }


        return result;
    }

    private void alterFieldColor(int aIndex, Color aColor, int aMs, String aProperty) {

        switch (aProperty) {

            case "foreground" : {

                fldNames.get(aIndex).setForeground(aColor);

                java.util.Timer worker = new java.util.Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                        fldNames.get(aIndex).setForeground(sudokuData.getNumberColors()[aIndex/9][aIndex%9]);

                        worker.cancel();
                        worker.purge();

                    }};

                worker.schedule(task, aMs);

                break;
            }

            case "background" : {

                fldNames.get(aIndex).setBackground(aColor);

                java.util.Timer worker = new java.util.Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                        fldNames.get(aIndex).setBackground(sudokuData.getNumberBackgroundColors()[aIndex/9][aIndex%9]);

                        worker.cancel();
                        worker.purge();

                    }};

                worker.schedule(task, aMs);

                break;
            }

            default:{
                      progressBar.setString("process: Wrong argument for property switch"); break;
            }

        }

    }

    void progressBarText(String aText,int aMs,boolean aKeepPreviousText){

        java.util.Timer worker = new java.util.Timer();
        String previousText = progressBar.getString();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (aKeepPreviousText) {
                    if(progressBar.getString().equals(aText))
                        progressBar.setString(previousText);

                }else if(progressBar.getString().equals(aText))
                    progressBar.setString("");

                worker.cancel();
                worker.purge();

            }};

        progressBar.setString(aText);
        worker.schedule(task, aMs);

    }

    private ArrayList<Integer> getAvailableNumbers(int aIndex,boolean aCheckSelf) {

        ArrayList<Integer> numberList = new ArrayList<>();

        //get center of square
        int aY = Integer.parseInt(fldNames.get(aIndex).getName()) / 9;
        int aX = Integer.parseInt(fldNames.get(aIndex).getName()) % 9;

        //clamp boundaries
        int yStart = max(0, aY - 1); int yEnd = min(8, aY + 1);
        int xStart = max(0, aX - 1); int xEnd = min(8, aX + 1);

        //get row and column to check
        int aYcross = aIndex / 9;
        int aXcross = aIndex % 9;

        //add to array only available numbers from square and cross
        for (int number = 1; number <= 9; number++) {
            searcher:
            {

                //square check
                for (int row = yStart; row <= yEnd; row++)
                    for (int col = xStart; col <= xEnd; col++)

                        if (sudokuData.getCells()[row][col] == number &&  ((row*9+col)!=aIndex||!aCheckSelf)) break searcher;

                //cross check
                for (int step = 0; step < 9; step++) {

                    if ( sudokuData.getCells()[aYcross][step] == number && ((aYcross*9+step)!=aIndex||!aCheckSelf))  break searcher;
                    if ( sudokuData.getCells()[step][aXcross] == number && ((step*9+aXcross)!=aIndex||!aCheckSelf))  break searcher;

                }

                numberList.add(number);

            }


        }

        if(numberList.isEmpty()) numberList.add(0);

        return numberList;
    }

    private byte getNextFreeNumberIndex(ArrayList<Integer> aIntList, int aNumber) {

        if (aIntList==null) return 0;

        for(byte index=0;index<aIntList.size();index++){

            if (aIntList.get(index)>aNumber) return index;

        }

        return 0;
    }

    private Integer getPreviousFreeNumberIndex(ArrayList<Integer> aIntList, int aNumber) {

        if (aIntList==null) return 0;

        for(int index=aIntList.size()-1;index>=0;index--){

            if (aIntList.get(index)<aNumber) return index;

        }

        return (aIntList.size()-1);
    }

    private void updateCell(int aIndex){

        int value = fldNames.get(aIndex).getText().length()!=0  ?
                Byte.valueOf(fldNames.get(aIndex).getText())    :   0;

        sudokuData.setCell(aIndex/9,aIndex%9, value);

        sudokuData.calcProgress();

        if(!sudokuData.isMarkingNumbers() && !createMode) progressBar.setValue(sudokuData.getProgress());


        if(sudokuData.getProgress()>13 && sudokuData.getProgress()<55) {

            startStopBtn.setEnabled(true);

            if(!createMode) {

                saveBtn.setEnabled(true);

            }
            else{

                for (int index = 0; index < 81; index++) {
                    fldNames.get(index).setEnabled(true);

                }
            }

        }
        else {

            saveBtn.setEnabled(false);

            if(createMode) {

                startStopBtn.setEnabled(false);

                if (sudokuData.getProgress()>54){

                    for (int index = 0; index < 81; index++) {
                    if (fldNames.get(index).getText().equals("")) fldNames.get(index).setEnabled(false);

                    }
                }
            }

        }

        if(sudokuData.getProgress()!=0) {

            loadBtn.setEnabled(false);
            createBtn.setEnabled(false);
            unsaved=true;

        }
        else {

            clearBtn.setEnabled(false);
            createBtn.setEnabled(true);
            loadBtn.setEnabled(true);

        }


        if (createMode) {

            int progress = sudokuData.getProgress();

            if      (progress < 19 && progress > 13 ) {//14-18

                    sudokuData.setDifficulty(Difficulty.Evil);
            }

            else if (progress < 27 && progress > 18 ) {//19-26

                    sudokuData.setDifficulty(Difficulty.Hard);
            }

            else if (progress < 37 && progress > 26) {//27-36

                    sudokuData.setDifficulty(Difficulty.Fair);
            }

            else if (progress < 55 && progress > 36) {//37-54

                    sudokuData.setDifficulty(Difficulty.Easy);
            }
            else if(progress>54) {

                     sudokuData.setDifficulty(Difficulty.Baby);
            }
            else sudokuData.setDifficulty(Difficulty.Poor);

            progressBar.setString("Set Mode");

        }


        if(!createMode && !sudokuData.isMarkingNumbers()) checkForStrike(aIndex);

        hintBtn.setToolTipText("Available Hint Tokens: "+appData.getGameTokens());

        //Win State
        if(!sudokuData.isMarkingNumbers() && !createMode && sudokuData.getProgress()==81) {

            if(!fullCheck(sudokuData.getCells())) {

                if( !sudokuData.isWon() ) gameWon();

            }

        }


    }

    private void gameWon(){

        for (int row = 0; row < 9; row++)  for (int col = 0; col < 9; col++) {int index = row * 9 + col;

            fldNames.get(index).setEditable(false);

            fldNames.get(index).setBorder(BorderFactory.
                    createLineBorder(new Color(130, 150, 150), 2, true));

            fldNames.get(index).setFont(appData.getDefaultNumberFont());

            fldNames.get(index).setBackground(appData.getDefaultBackgroundColor());

            fldNames.get(index).setForeground(new Color(219, 167, 47));

        }

        for(int innerIndex=0;innerIndex<81;innerIndex++){

           int number = !fldNames.get(appData.getDrainSpiral(innerIndex)).getText().equals("") ?
                   Integer.parseInt(fldNames.get(appData.getDrainSpiral(innerIndex)).getText()) : 0;

            drainedNumbers[innerIndex]=number;

        }

        class DrainNumbers extends TimerTask{

            public void run() {

                System.arraycopy(drainedNumbers, 0, drainedNumbers, 1, 80);
                drainedNumbers[0]=0;

                for(int innerIndex=0;innerIndex<81;innerIndex++){

                if(drainedNumbers[innerIndex]!=0)
                fldNames.get(appData.getDrainSpiral(innerIndex)).setText(String.valueOf(drainedNumbers[innerIndex]));

                }

                fldNames.get(appData.getDrainSpiral(drainIndex)).setBackground(appData.getDefaultBackgroundColor());
                fldNames.get(appData.getDrainSpiral(drainIndex)).setText("");


                if(drainIndex<80) drainIndex++;
                else {
                    drainIndex=0;
                    cancel();
                }


            }

        }

        if(!appData.isMuteSounds()) {Sounds.wonGame();}

        progressBarText("Congratulations", 5000, false);

        java.util.Timer timerDrain = new java.util.Timer();
        timerDrain.schedule(new DrainNumbers(), 200, 40);

        int intermediateCalc=calcTokens();

        appData.addHintToken(intermediateCalc);
        appData.addWonGame();

        CongratulationDialog congratulationDialog = new CongratulationDialog(this,intermediateCalc);

        congratulationDialog.setUndecorated(true);
        congratulationDialog.pack();
        congratulationDialog.setLocation(this.getLocation().x+ congratulationDialog.getWidth()/4, this.getLocation().y+congratulationDialog.getWidth()/2-4);
        congratulationDialog.setVisible(true);


    }

    int calcTokens() {

        int intermediateCalc=0;

        if(sudokuData.getDifficulty()==Difficulty.Evil) {
            intermediateCalc=20;
                 if(sudokuData.getSecondCounter()< 601)intermediateCalc+=25;  //45
            else if(sudokuData.getSecondCounter()<1201)intermediateCalc+=20;  //40
            else if(sudokuData.getSecondCounter()<1801)intermediateCalc+=15;  //35
            else intermediateCalc+=10;                                        //30
        }

        if(sudokuData.getDifficulty()==Difficulty.Hard) {
            intermediateCalc=15;
                 if(sudokuData.getSecondCounter()< 601)intermediateCalc+=20;  //35
            else if(sudokuData.getSecondCounter()<1201)intermediateCalc+=15;  //30
            else if(sudokuData.getSecondCounter()<1801)intermediateCalc+=10;  //25
            else intermediateCalc+=5;                                         //20
        }

        if(sudokuData.getDifficulty()==Difficulty.Fair) {
            intermediateCalc=10;
                 if(sudokuData.getSecondCounter()< 601)intermediateCalc+=15;  //25
            else if(sudokuData.getSecondCounter()<1201)intermediateCalc+=10;  //20
            else if(sudokuData.getSecondCounter()<1801)intermediateCalc+= 5;  //15
        }

        if(sudokuData.getDifficulty()==Difficulty.Easy) {
            intermediateCalc=5;
                 if(sudokuData.getSecondCounter()<601)intermediateCalc+= 5;}  //10



        if(sudokuData.getAssistanceLevel()==AssistanceLevel.Low)    intermediateCalc+= 18;
        if(sudokuData.getAssistanceLevel()==AssistanceLevel.Medium) intermediateCalc+= 13;
        if(sudokuData.getAssistanceLevel()==AssistanceLevel.Full)   intermediateCalc+=  3;

        return intermediateCalc;

    }

    private void checkForStrike(int aIndex){

        int row = aIndex/9;
        int col = aIndex%9;

        boolean rowGood=true;
        boolean colGood=true;
        boolean squareGood=true;

        int smallRow = row - row % 3;
        int smallCol = col - col % 3;

        for(int check=0;check<9;check++) for(int step=0;step<9;step++) {

            if(step!=check && (sudokuData.getCell(row, step) == sudokuData.getCell(row, check) || sudokuData.getCell(row, step) == 0 )) rowGood = false;

            if(step!=check && (sudokuData.getCell(step, col) == sudokuData.getCell(check, col) || sudokuData.getCell(step, col) == 0 )) colGood = false;

        }

        for (int sRow = smallRow; sRow < smallRow + 3; sRow++)
            for (int sCol = smallCol; sCol < smallCol + 3; sCol++)
                for (int sRow2 = smallRow; sRow2 < smallRow + 3; sRow2++)
                    for (int sCol2 = smallCol; sCol2 < smallCol + 3; sCol2++){

                        if (sudokuData.getCell(sRow2,sCol2)==0 ||
                                (sRow!=sRow2 || sCol!=sCol2) &&
                                            sudokuData.getCell(sRow,sCol)==sudokuData.getCell(sRow2,sCol2) ) squareGood = false;

                        }


        boolean firstDiagonalGood  = true;
        boolean secondDiagonalGood = true;

        if (sudokuData.isStruckFirstDiagonal() || sudokuData.isStruckSecondDiagonal()) {

            for (int shift = 0; shift < 9; shift++)
                for (int checkShift = 0; checkShift < 9; checkShift++) {

                    if (shift != checkShift && (sudokuData.getCell(shift, shift) == 0
                            || sudokuData.getCell(shift, shift) == sudokuData.getCell(checkShift, checkShift)))
                        firstDiagonalGood = false;

                    if (shift != checkShift && (sudokuData.getCell(shift, 8 - shift) == 0
                            || sudokuData.getCell(shift, 8 - shift) == sudokuData.getCell(checkShift, 8 - checkShift)))
                        secondDiagonalGood = false;


                }

            if (firstDiagonalGood) {
                strikeFirstDiagonal = true;
                strikeFirstDiagonal();
            }
            if (secondDiagonalGood) {
                strikeSecondDiagonal = true;
                strikeSecondDiagonal();
            }

        }

        if (rowGood) {
            strikeRow = true;
            strikeRow(aIndex);
        }
        if (colGood) {
            strikeCol = true;
            strikeCol(aIndex);
        }
        if (squareGood) {
            strikeSquare = true;
            strikeSquare(aIndex);
        }

    }

    private void strikeFirstDiagonal() {


        class TimedFirstDiagonalTask extends TimerTask{

            public void run() {

                if(strikeFirstDiagonal)   {

                    alterFieldColor(animFirstDiagonalStep*9+animFirstDiagonalStep,  new Color(255, 181, 0),300,"foreground");

                }


                if(animFirstDiagonalStep<8) animFirstDiagonalStep++;
                else {

                    strikeFirstDiagonal=false;

                    animFirstDiagonalStep=0;

                    cancel();
                }

            }

        }

        java.util.Timer timerFirstDiagonal = new java.util.Timer();

        if(sudokuData.isStruckFirstDiagonal() && !createMode) {

            progressBarText("Bonus: Diagonal Struck - Earned 2 Hint Tokens",4000,false);
            appData.addHintToken(2);
            timerFirstDiagonal.schedule(new TimedFirstDiagonalTask(), 50, 150);

            if(!appData.isMuteSounds()) {Sounds.earned();}

        }

        if(strikeFirstDiagonal)sudokuData.setStruckFirstDiagonal(true);

    }

    private void strikeSecondDiagonal() {

        class TimedSecondDiagonalTask extends TimerTask{

            public void run() {

                if(strikeSecondDiagonal)   {

                    alterFieldColor((animSecondDiagonalStep)*9+(8-animSecondDiagonalStep),  new Color(255, 181, 0),300,"foreground");

                }


                if(animSecondDiagonalStep<8) animSecondDiagonalStep++;
                else {

                    strikeSecondDiagonal=false;

                    animSecondDiagonalStep=0;

                    cancel();
                }

            }

        }

        java.util.Timer timerSecondDiagonal = new java.util.Timer();

        if(sudokuData.isStruckSecondDiagonal() && !createMode) {

            progressBarText("Bonus: Diagonal Struck - Earned 2 Hint Tokens",4000,false);
            appData.addHintToken(2);
            timerSecondDiagonal.schedule(new TimedSecondDiagonalTask(), 50, 150);

            if(!appData.isMuteSounds()) {Sounds.earned();}
        }

        if(strikeSecondDiagonal)sudokuData.setStruckSecondDiagonal(true);

    }

    private void strikeRow(int aIndex){

        int row = aIndex/9;

        class TimedRowTask extends TimerTask{

            public void run() {

                if(strikeRow)   {

                    alterFieldColor(row*9+animRowStep,  new Color(40, 190, 140),300,"foreground");

                }


                if(animRowStep<8) animRowStep++;
                else {

                    strikeRow=false;

                    animRowStep=0;
                    cancel();
                }

            }
        }

        java.util.Timer timerRow = new java.util.Timer();

        if(!sudokuData.getStruckRow(row)&&!createMode) {

            progressBarText("Edge Struck - Earned 1 Hint Token",4000,false);
            appData.addHintToken(1);
            timerRow.schedule(new TimedRowTask(), 50, 150);

            if(!appData.isMuteSounds()) {Sounds.earned();}
        }

        if(strikeRow)sudokuData.strikeRow(row);

    }

    private void strikeCol(int aIndex){

        int col = aIndex%9;

        class TimedColTask extends TimerTask{

            public void run() {

                if(strikeCol)   {

                    alterFieldColor(animColStep*9+col,new Color(255, 150, 0),300,"foreground");

                }


                if(animColStep<8) animColStep++;
                else {

                    strikeCol=false;

                    animColStep=0;
                    cancel();
                }

            }
        }


        java.util.Timer timerCol = new java.util.Timer();
        if(!sudokuData.getStruckCol(col)&&!createMode) {

            progressBarText("Pillar Struck - Earned 1 Hint Token",4000,false);
            appData.addHintToken(1);
            timerCol.schedule(new TimedColTask(), 50, 150);

            if(!appData.isMuteSounds()) {Sounds.earned();}

        }

        if(strikeCol)sudokuData.strikeCol(col);

    }

    private void strikeSquare(int aIndex) {

        int row = aIndex/9;
        int col = aIndex%9;

        int smallRow = row - row % 3;
        int smallCol = col - col % 3;

        int sIndex=0;
        for (int sRow = smallRow; sRow < smallRow + 3; sRow++)
            for (int sCol = smallCol; sCol < smallCol + 3; sCol++){

                intermediateSpiral[sIndex++]=sRow*9+sCol;

            }


        class TimedSquareTask extends TimerTask{

            public void run() {


                if(strikeSquare) {

                    alterFieldColor((intermediateSpiral[smallSpiral[animSquareStep]]),  new Color(85, 117, 255),300,"foreground");

                }

                if(animSquareStep<8) animSquareStep++;
                else {

                    strikeSquare=false;

                    animSquareStep=0;

                    cancel();
                }


            }

        }


        java.util.Timer timerSquare = new java.util.Timer();
        if(!sudokuData.getStruckSquare(intermediateSpiral[0])&&!createMode) {

            progressBarText("Square Struck - Earned 1 Hint Token",4000,false);
            appData.addHintToken(1);
            timerSquare.schedule(new TimedSquareTask(), 50, 150);

            if(!appData.isMuteSounds()) {Sounds.earned();}

        }

        if(strikeSquare)sudokuData.strikeSquare(intermediateSpiral[0]);


    }

    private void startTimer(){

        if(timerLabel.getText().equals("--:--:--"))
           timerLabel.setText("00:00:00");

        timerLabel.setForeground(Color.BLACK);
        timer.start();
        startStopBtn.setText("Pause");
        timerIsStarted=true;

    }

    private void init(){

        //<editor-fold desc="TEXTFIELDS BINDS TO HASHTABLE">
        //1 row                             //2 row                             //3 row
        fldNames.put(0,  textField00);      fldNames.put(9,  textField09);      fldNames.put(18, textField18);
        fldNames.put(1,  textField01);      fldNames.put(10, textField10);      fldNames.put(19, textField19);
        fldNames.put(2,  textField02);      fldNames.put(11, textField11);      fldNames.put(20, textField20);
        fldNames.put(3,  textField03);      fldNames.put(12, textField12);      fldNames.put(21, textField21);
        fldNames.put(4,  textField04);      fldNames.put(13, textField13);      fldNames.put(22, textField22);
        fldNames.put(5,  textField05);      fldNames.put(14, textField14);      fldNames.put(23, textField23);
        fldNames.put(6,  textField06);      fldNames.put(15, textField15);      fldNames.put(24, textField24);
        fldNames.put(7,  textField07);      fldNames.put(16, textField16);      fldNames.put(25, textField25);
        fldNames.put(8,  textField08);      fldNames.put(17, textField17);      fldNames.put(26, textField26);
        //4 row                             //5 row                             //6 row
        fldNames.put(27, textField27);      fldNames.put(36, textField36);      fldNames.put(45, textField45);
        fldNames.put(28, textField28);      fldNames.put(37, textField37);      fldNames.put(46, textField46);
        fldNames.put(29, textField29);      fldNames.put(38, textField38);      fldNames.put(47, textField47);
        fldNames.put(30, textField30);      fldNames.put(39, textField39);      fldNames.put(48, textField48);
        fldNames.put(31, textField31);      fldNames.put(40, textField40);      fldNames.put(49, textField49);
        fldNames.put(32, textField32);      fldNames.put(41, textField41);      fldNames.put(50, textField50);
        fldNames.put(33, textField33);      fldNames.put(42, textField42);      fldNames.put(51, textField51);
        fldNames.put(34, textField34);      fldNames.put(43, textField43);      fldNames.put(52, textField52);
        fldNames.put(35, textField35);      fldNames.put(44, textField44);      fldNames.put(53, textField53);
        //7 row                             //8 row                             //9 row
        fldNames.put(54, textField54);      fldNames.put(63, textField63);      fldNames.put(72, textField72);
        fldNames.put(55, textField55);      fldNames.put(64, textField64);      fldNames.put(73, textField73);
        fldNames.put(56, textField56);      fldNames.put(65, textField65);      fldNames.put(74, textField74);
        fldNames.put(57, textField57);      fldNames.put(66, textField66);      fldNames.put(75, textField75);
        fldNames.put(58, textField58);      fldNames.put(67, textField67);      fldNames.put(76, textField76);
        fldNames.put(59, textField59);      fldNames.put(68, textField68);      fldNames.put(77, textField77);
        fldNames.put(60, textField60);      fldNames.put(69, textField69);      fldNames.put(78, textField78);
        fldNames.put(61, textField61);      fldNames.put(70, textField70);      fldNames.put(79, textField79);
        fldNames.put(62, textField62);      fldNames.put(71, textField71);      fldNames.put(80, textField80);
        //</editor-fold>


        fio = new FIO(this);

        setAppData(fio.onInit());

        sudokuData = new SudokuData();

        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++) {
                int index = row * 9 + col;

                //load defaults to new SudokuData at init
                sudokuData.setNumberBackgroundColor(row, col, appData.getDefaultBackgroundColor());
                sudokuData.setNumberColor(row, col, appData.getStonedNumberColor());

                //change default color for fields to black
                fldNames.get(index).setForeground(appData.getStonedNumberColor());


                fldNames.get(index).setBorder(BorderFactory.
                        createLineBorder(new Color(130, 150, 150), 2, true));

                fldNames.get(index).setSelectionColor(new Color(80, 50, 120));

                fldNames.get(index).getCaret().setBlinkRate(1000);
            }


        progressBar.setForeground(appData.getPBForegroundColor());

        hintBtn.setToolTipText("Available Hint Tokens: " + appData.getGameTokens());

        //<editor-fold desc="WELCOME MSG">
        java.util.Timer welcomeMsgTimer = new java.util.Timer();
        TimerTask welcomeMsgTask = new TimerTask() {
            @Override
            public void run() {

                if (progressBar.getString().equals("Enjoy your day!"))
                    progressBar.setString("Press Load/Create or Set your Sudoku");

                welcomeMsgTimer.cancel();
                welcomeMsgTimer.purge();

            }
        };

            welcomeMsgTimer.schedule(welcomeMsgTask, 5000);
            //</editor-fold>


        //get cursor in center
        fldNames.get(sudokuData.getFocusPos()).grabFocus();


        //<editor-fold desc="TIMED INITIALIZATIONS">
        java.util.Timer initTimer = new java.util.Timer();
        TimerTask initTask = new TimerTask() {
            @Override
            public void run() {

                setVisible(true);

                initWindow.setVisible(false);
                initWindow.dispose();

                if(appData.isSaveOnExit()) {

                    SudokuData loadedSudokuData=(SudokuData) fio.loadFile("Unique",fio.saveFilenameExtension);

                    if(loadedSudokuData!=null){

                        sudokuData=loadedSudokuData;
                        onLoadGame();
                        progressBarText("Loaded Unique Save File",2500,false);

                    }
                    else progressBarText("Missing Unique Save File",2500,true);

                }

                if (appData.isShowWelcomeMsg()) showWelcomeMessage();

                initTimer.cancel();
                initTimer.purge();
            }
        };

        initTimer.schedule(initTask, 1000);
        //</editor-fold>


        timerLabel.setVisible(appData.isShowTimer());

        startFocusPosHelper();


        try {

            Sounds.init();
            appData.setSoundIsDisabled(false);
            appData.setMuteSounds(false);

        } catch (Exception e) {
            e.printStackTrace();
            appData.setSoundIsDisabled(true);
            appData.setMuteSounds(true);
            progressBarText("Sounds Loading Error!",4000,false);

        }

    }

    void onClose(){

        try {
            Sounds.audioIn.close();
        } catch (IOException e) {

            errorMsgWindow("Error closing audios");

        }

        if (fio.onExit()) {

            errorMsgWindow(fio.getOperationMsg());

        }

        if(appData.isSaveOnExit() && !createMode && sudokuData.getProgress()<55) {

            fio.saveFile("Unique",fio.saveFilenameExtension,this.sudokuData);

        }

        if(!closeFlag) System.exit(0);

    }

    private void errorMsgWindow(String aMsg){

        JDialog errorMessage = new ErrorMessage(aMsg,fio);

        errorMessage.setUndecorated(true);
        errorMessage.pack();
        errorMessage.setLocationRelativeTo(rootPanel);
        setVisible(false);
        errorMessage.setVisible(true);

    }

    private class LoadNumbers extends TimerTask{

        public void run() {

            int index = appData.getSpiralFromCenterClockwise(loadIndex);

            int row = index/9; int col = index%9;

            //load numbers
            if (sudokuData.getCell(row, col) != 0) {
                    fldNames.get(index).setText(Integer.toString(sudokuData.getCell(row, col)));
                    progressBar.setValue(sudokuData.getProgress());
            }

            if(loadIndex<80) loadIndex++;
            else {
                updateCell(40);
                loadIndex=0;
                cancel();
            }

        }

    }

    private static class ProgressPainter implements Painter {

        private Color light, dark;

        ProgressPainter(Color light, Color dark) {
            this.light = light;
            this.dark = dark;
        }

        @Override
        public void paint(Graphics2D g, Object c, int w, int h) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradPaint = new GradientPaint((w / 2.0f), 0, light, (w / 2.0f), (h / 2.0f), dark, true);
            g.setPaint(gradPaint);
            g.fillRect(2, 2, (w - 5), (h - 5));

            Color outline = new Color(60, 0, 90);
            g.setColor(outline);
            g.drawRect(2, 2, (w - 5), (h - 5));
            Color trans = new Color(outline.getRed(), outline.getGreen(), outline.getBlue(), 100);
            g.setColor(trans);
            g.drawRect(1, 1, (w - 4), (h - 4));
        }
    }

    void writeField(int aIndex, String aNumber){
        fldNames.get(aIndex).setText(aNumber);
//        updateCell(aIndex);
    }

    private void createUIComponents() {
        progressBar = new JProgressBar();
        UIManager.put("nimbusOrange", new Color(36, 93, 127));
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].backgroundPainter", new ProgressPainter(new Color(100, 100, 100), new Color(220, 220, 250)));
        progressBar.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        progressBar.putClientProperty("Nimbus.Overrides", defaults);
    }


}
