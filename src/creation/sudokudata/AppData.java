package creation.sudokudata;

import java.awt.*;
import java.io.Serializable;

public class AppData implements Serializable {

    private static final long serialVersionUID = 14863L;


    public int getStartedGames() { return startedGames; }
    public void addStartedGames() { this.startedGames++; }
    private int startedGames;


    public boolean isMuteSounds() { return muteSounds;  }
    public void setMuteSounds(boolean muteSounds) { this.muteSounds = muteSounds;  }
    private boolean muteSounds;

    public boolean isSoundIsDisabled() { return soundIsDisabled; }
    public void setSoundIsDisabled(boolean soundIsDisabled) { this.soundIsDisabled = soundIsDisabled; }
    private boolean soundIsDisabled;


    public int getWonGames() { return wonGames; }
    public void addWonGame() { this.wonGames++;   }
    private int wonGames;

    private int gameTokens;
    private int selectedSlot;
    private int selectedRadioBtn;

    private int selectedRadioGenDifficultyBtn;


    private byte selectedMode;

    private int mouseWheelSens;
    private int grain;


    private boolean showPalette;
    private boolean showWelcomeMsg;
    private boolean saveOnExit;

    private boolean showTimer;
    public boolean isShowTimer() { return showTimer;  }
    public void setShowTimer(boolean showTimer) { this.showTimer = showTimer;  }



    private Color defaultNumberColor;
    private Color crossColor;
    private Color stonedNumberBackgroundColor;
    private Color stonedNumberColor;
    private Color defaultBackgroundColor;
    private Color deletedBackgroundColor;
    private Color markNumberColor;
    private Color highlightNumberColor;


    private Color stonedBorderColor;

    private Color PBForegroundColor;


    private Font  stonedNumberFont;
    private Font  defaultNumberFont;
    private Font  markedNumberFont;


    private int[] spiralFromCenterClockwise = new int[]{
             40, 41, 50, 49, 48, 39, 30, 31, 32,
             42, 51, 60, 59, 58, 57, 56, 47, 38, 29, 20, 21, 22, 23, 24, 33,
             43, 52, 61, 70, 69, 68, 67, 66, 65, 64, 55, 46, 37, 28, 19, 10, 11, 12, 13, 14, 15, 16, 25, 34,
             44, 53, 62, 71, 80, 79, 78, 77, 76, 75, 74, 73, 72, 63, 54, 45, 36, 27, 18, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 35
    };

    private int[] drainSpiral = new int[]{
            40,49,48,39,30,31,32,41,50,
            59,58,57,56,47,38,29,20,21,22,23,24,33,42,51,60,
            69,68,67,66,65,64,55,46,37,28,19,10,11,12,13,14,15,16,25,34,43,52,61,70,
            79,78,77,76,75,74,73,72,63,54,45,36,27,18,9,0,1,2,3,4,5,6,7,8,17,26,35,44,53,62,71,80
    };


    public Color getDefaultNumberColor() { return defaultNumberColor; }
    public Color getCrossColor() { return crossColor;  }
    public Color getStonedNumberBackgroundColor() { return stonedNumberBackgroundColor; }
    public Color getStonedNumberColor() { return stonedNumberColor; }
    public Color getDefaultBackgroundColor() { return defaultBackgroundColor; }
    public Color getDeletedBackgroundColor() { return deletedBackgroundColor; }
    public Color getStonedBorderColor() { return stonedBorderColor; }
    public Color getMarkNumberColor() { return markNumberColor;  }
    public Color getHighlightNumberColor() { return highlightNumberColor;   }

    public Color getPBForegroundColor() { return PBForegroundColor;    }
    public Font  getStonedNumberFont() {return stonedNumberFont; }
    public Font  getMarkedNumberFont() { return markedNumberFont;  }
    public Font  getDefaultNumberFont() { return defaultNumberFont; }
    public int getSpiralFromCenterClockwise(int aIndex) { return spiralFromCenterClockwise[aIndex];  }
    public int getDrainSpiral(int aIndex) { return drainSpiral[aIndex];  }


    public void setSelectedSlot(int selectedSlot) { this.selectedSlot = selectedSlot; }
    public void setMouseWheelSens(int mWheelSens) { this.mouseWheelSens = mWheelSens; }
    public void setGrain(int grain) { this.grain = grain;  }
    public void getAndIncrementGrain() { this.grain++;  }
    public void setSelectedRadioBtn(int selectedRadioBtn) {  this.selectedRadioBtn = selectedRadioBtn; }
    public void setSelectedRadioGenDifficultyBtn(int selectedRadioGenDifficultyBtn) { this.selectedRadioGenDifficultyBtn = selectedRadioGenDifficultyBtn; }
    public void setShowPalette(boolean showPalette) {  this.showPalette = showPalette; }
    public void setShowWelcomeMsg(boolean showWelcomeMsg) {  this.showWelcomeMsg = showWelcomeMsg;  }
    public void setSaveOnExit(boolean saveOnExit) { this.saveOnExit = saveOnExit; }


    public int getGameTokens() { return gameTokens; }
    public int getSelectedSlot() { return selectedSlot; }
    public int getSelectedMode() { return selectedMode; }
    public int getMouseWheelSens() { return mouseWheelSens; }
    public int getGrain() { return grain;  }
    public int getSelectedRadioBtn() { return selectedRadioBtn; }
    public int getSelectedRadioGenDifficultyBtn() { return selectedRadioGenDifficultyBtn;   }
    public boolean isShowPalette() { return showPalette;  }
    public boolean isShowWelcomeMsg() { return showWelcomeMsg;  }
    public boolean isSaveOnExit() { return saveOnExit;  }



   public AppData(){
        //when writing default AppData file
        this.gameTokens        = 200;
        this.selectedSlot      = 0;
        this.selectedMode      = 0;
        this.mouseWheelSens    = 3;
        this.grain             = 0;
        this.selectedRadioBtn  = 0;
        this.selectedRadioGenDifficultyBtn  = 2;
        this.muteSounds=false;
        this.wonGames=0;
        this.startedGames=0;


        this.defaultNumberColor            =  new Color(9,   124, 0);
        this.crossColor                    =  new Color(230, 230, 200);
        this.stonedNumberColor             =  new Color(70, 70, 80);
        this.stonedNumberBackgroundColor   =  new Color(210, 210, 210);
        this.defaultBackgroundColor        =  new Color(245, 245, 245);
        this.deletedBackgroundColor        =  new Color(255, 220, 220);
        this.highlightNumberColor          =  new Color(200, 50, 50);
        this.stonedBorderColor             =  new Color(70,  70,  70);
        this.markNumberColor               =  new Color(255, 130, 90);
        this.PBForegroundColor             =  new Color(120, 10, 10);

        this.stonedNumberFont              =  new Font("Bahnschrift", Font.BOLD, 34);
        this.defaultNumberFont             =  new Font("Cambria", Font.BOLD, 34);
        this.markedNumberFont              =  new Font("Cambria", Font.ITALIC, 26);

        this.showPalette = false;
        this.showWelcomeMsg=true;
        this.saveOnExit=false;
        this.showTimer=true;

   }

   public void addHintToken(int aGameTokens){

       this.gameTokens+=aGameTokens;
       if(this.gameTokens>9999) this.gameTokens=9999;

   }

   public void decreaseTokens(int aAmount) {

        this.gameTokens-=aAmount;
        if(this.gameTokens<0) this.gameTokens=0;

   }


}
