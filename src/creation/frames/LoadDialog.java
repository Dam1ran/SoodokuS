package creation.frames;


import creation.filehandlers.FIO;
import creation.filehandlers.Sounds;
import creation.sudokudata.SudokuData;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class LoadDialog extends JDialog {

    private JPanel contentPane;
    private JComboBox<String> comboBox;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton deleteBtn;
    private JLabel textLbl;

    private Parent parent;
    private FIO fio;

    private String operationMsg;


    LoadDialog(Parent aParent, FIO aFio) {

        this.fio = aFio;
        this.parent=aParent;

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setModal(true);

        operationMsg="";

        loadList();

        init();

        buttonOK.addActionListener(e -> onLoad());

        buttonCancel.addActionListener(e -> onCancel());

        textLbl.setForeground(new Color(220,220,255));

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        comboBox.addActionListener(e -> {

           String slotString = (String) comboBox.getSelectedItem();

            if (slotString!=null) {

                if (!slotString.split(" ")[3].equals("<Empty>")) {
                    buttonOK.setText("Load");
                    deleteBtn.setText("Delete");
                    deleteBtn.setEnabled(true);
                    buttonOK.setEnabled(true);
                } else {
                    buttonOK.setText("Empty");
                    deleteBtn.setEnabled(false);
                    buttonOK.setEnabled(false);
                }

                parent.getAppData().setSelectedSlot(comboBox.getSelectedIndex());

            } else operationMsg = "Failed to init saves";

        });

        deleteBtn.addActionListener(e -> {

            if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

            int index = comboBox.getSelectedIndex();

            String slotString = comboBox.getItemAt(index);

            if (fio.deleteBinFile(slotString.split(" ")[2])) {

                comboBox.removeItemAt(index);
                comboBox.insertItemAt(slotString.split(" ")[0] + " " + slotString.split(" ")[1] + " " + slotString.split(" ")[2] + " <Empty>", index);
                comboBox.setSelectedIndex(index);

                parent.getAppData().setSelectedSlot(comboBox.getSelectedIndex());

                deleteBtn.setText("Emptied");
                operationMsg = "Slot Freed";

            } else {

                parent.getAppData().setSelectedSlot(comboBox.getSelectedIndex());

                deleteBtn.setText("Fail!");
                operationMsg = "Fail to Empty slot!";
            }

        });

        comboBox.addMouseWheelListener(e -> {

            parent.getAppData().setSelectedSlot(comboBox.getSelectedIndex());


            //mWheelUp
            if (e.getWheelRotation() < 0) {

                int slotIndex = parent.getAppData().getSelectedSlot()==0 ? 9 : parent.getAppData().getSelectedSlot();
                comboBox.setSelectedIndex(--slotIndex);
                parent.getAppData().setSelectedSlot(slotIndex);

            //mWheelDown
            }else{

                int slotIndex = parent.getAppData().getSelectedSlot()==8 ? -1 : parent.getAppData().getSelectedSlot() ;
                comboBox.setSelectedIndex(++slotIndex);
                parent.getAppData().setSelectedSlot(slotIndex);

            }
        });


    }


    private void loadList(){

        for(int index = 0; index < comboBox.getItemCount(); index++){

            if (fio.checkFile(comboBox.getItemAt(index).split(" ")[2]).equals("<Empty>")) {

                String slotString = comboBox.getItemAt(index);
                comboBox.removeItemAt(index);

                comboBox.insertItemAt(slotString.split(" ")[0]+" "+ slotString.split(" ")[1]+" "+ slotString.split(" ")[2]+" <Empty>",index);

            }
            else{

                String slotString = comboBox.getItemAt(index);
                comboBox.removeItemAt(index);
                comboBox.insertItemAt(slotString.split(" ")[0]+" "+ slotString.split(" ")[1]+" "+ slotString.split(" ")[2]+" (Busy)",index);

            }

        }

         comboBox.setSelectedIndex(parent.getAppData().getSelectedSlot());

    }

    private void init(){

        String slotString = (String) comboBox.getSelectedItem();

        if (slotString!=null) {

            if (!slotString.split(" ")[3].equals("<Empty>")) {
                buttonOK.setText("Load");
                deleteBtn.setEnabled(true);
                buttonOK.setEnabled(true);
            }else{
                deleteBtn.setEnabled(false);
                buttonOK.setEnabled(false);
                buttonOK.setText("Empty");

            }

        } else operationMsg = "Failed to init saves";

    }

    private void onLoad() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        String fileName = (String) comboBox.getSelectedItem();

        if (fileName!=null) {

            fileName=fileName.split(" ")[2];

            SudokuData sudokuData = (SudokuData) fio.loadFile(fileName,fio.saveFilenameExtension);

            if (sudokuData!=null) {

                parent.setSudokuData(sudokuData);
                operationMsg = "Loaded " + fileName;


            } else {

                operationMsg = "Failed to load "+ fileName;
            }


        }
        else operationMsg = "Failed to init loads";

        dispose();

    }

    private void onCancel() {

        if(!parent.getAppData().isMuteSounds()) { Sounds.notifySound(); }

        dispose();
    }

    String getResponse(){

        return operationMsg;

    }


}
