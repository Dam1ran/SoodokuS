package creation.filehandlers;

import creation.frames.Parent;
import creation.sudokudata.AppData;
import creation.sudokudata.SudokuData;


import java.io.*;
import java.util.TimerTask;

public class FIO {


    private String operationMsg;
    private Parent parent;


    public FIO(Parent aParent){
        this.parent = aParent;
    }

    public String getOperationMsg() { return operationMsg; }

    private final String dirName  = "soodokusfiles";
    private final String fileName = "appData";
    private final String filenameExtension = ".dat";
    public  final String saveFilenameExtension = ".bin";
    private final String fileSep = System.getProperty("file.separator");
    private final String currDir = System.getProperty("user.dir");
    private final String fileFullPath = currDir + fileSep + dirName + fileSep + fileName+ filenameExtension;
    private final String folderFullPath = currDir + fileSep + dirName;


    public AppData onInit(){

        boolean folderExists;
        boolean fileExists=false;

        AppData appData = new AppData();

        File dataFile   = new File(fileFullPath);
        File dataFolder = new File(folderFullPath);


        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                folderExists = true;
            } else {
                operationMsg = "Error creating directory '" + dirName + "'";
                this.timedExit(3000);
                folderExists = false;
            }
        } else folderExists = true;


        if (dataFile.isFile() && dataFile.canRead()) {

            fileExists = true;

        }

        if(!fileExists) fileExists=saveFile(fileName, filenameExtension,new AppData());


        if (fileExists&&folderExists) {

           return (AppData) loadFile(fileName, filenameExtension);

        }

        return appData;

    }

    public boolean onExit(){

        return !saveFile(fileName, filenameExtension, parent.getAppData());

    }

    public String checkFile(String aFileName){

        String fullPath = currDir + fileSep + dirName + fileSep + aFileName+ saveFilenameExtension;
        File dataFile = new File(fullPath);
        if(dataFile.isFile() && dataFile.canRead()) return aFileName.split("\\.")[0];
        else return "<Empty>";

    }

    public boolean deleteBinFile(String aFileName) {

        String fullPath = currDir + fileSep + dirName + fileSep + aFileName + saveFilenameExtension;
        File dataFile = new File(fullPath);
        return dataFile.delete();

    }

    public void deleteAllGameFiles() {

        deleteFile("appData.dat");
        deleteFile("Unique.bin");
        deleteFile("Duo.bin");
        deleteFile("Trinity.bin");
        deleteFile("Quadro.bin");
        deleteFile("Pentagram.bin");
        deleteFile("Hexagon.bin");
        deleteFile("Seas.bin");
        deleteFile("Octet.bin");
        deleteFile("Months.bin");

        //delete folder also
        String fullPath = currDir + fileSep + dirName;
        File dataFile = new File(fullPath);
        if(!dataFile.delete()) System.out.println("Fail to delete "+dirName);

    }

    private void deleteFile(String aFileName) {

        String fullPath = currDir + fileSep + dirName + fileSep + aFileName;
        File dataFile = new File(fullPath);
        if(!dataFile.delete()) System.out.println("Fail to delete "+aFileName);

    }

    public boolean saveFile(String aFileName,String aExtension, Object obj){

        boolean result;

        final String fileFullPath = currDir + fileSep + dirName + fileSep + aFileName+aExtension;

            try {

                File dataFile = new File(fileFullPath);
                FileOutputStream outputStream = new FileOutputStream(dataFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                if(obj instanceof SudokuData) {((SudokuData) obj).setName(aFileName);}

                objectOutputStream.writeObject(obj);

                operationMsg="Wrote " + aFileName + aExtension;
                System.out.println(operationMsg);

                objectOutputStream.close();
                outputStream.close();
                result = true;

            } catch (IOException ex) {

                operationMsg = "Error writing file '" + aFileName + "'";
                System.out.println(operationMsg);
                result = false;

            }

        return result;

    }

    public Object loadFile(String aFileName,String aExtension){

        Object object = null;

        final String fileFullPath = currDir + fileSep + dirName + fileSep + aFileName+aExtension;

        try {

            File              dataFile          = new File(fileFullPath);
            FileInputStream   inputStream       = new FileInputStream(dataFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            object = objectInputStream.readObject();

            operationMsg="Loaded " + aFileName;
            System.out.println(operationMsg);

            inputStream.close();
            objectInputStream.close();
            return object;

        } catch (IOException | ClassNotFoundException ex) {

            operationMsg = "Error loading file '" + aFileName + "'";
            System.out.println(operationMsg);

        }

        return object;

    }

    public void timedExit(int aDelay){

        java.util.Timer worker = new java.util.Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {


                System.exit(0);
                worker.cancel();
                worker.purge();

            }};

        worker.schedule(task, aDelay);


    }

}
