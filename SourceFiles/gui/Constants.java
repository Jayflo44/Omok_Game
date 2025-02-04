package gui;

import java.awt.Color;

import java.awt.Font;


public class Constants {
    //Constants for GUI dimensions and layout
    public static final int Window_Width = 600;
    public static final int Window_Height = 800;
    public static final int Board_Panel_Size = 300;
    public static final int Stone_Size = 30;
    // Constants for colors used in the GUI
    static Color babyBlue = new Color(137,207,240);
    public static final Color Board_Color = babyBlue;
    public static final Color Player1_Color = Color.RED;
    public static final Color Player2_Color = Color.BLACK;
    static Color clearGreen = new Color(0,255,0,128);
    public static final Color Winning_Row_Color = clearGreen;
    public static final Font MAIN_FONT = new Font("SansSerif", Font.BOLD, 14);
//Strings for labels, buttons , tool tips. and error messages
    public static final String Game_TITLE ="Omok Game";
    public static final String NEW_GAME_BUTTON_TEXT = "New Game";
    public static final String NEW_GAME_TOOLTIP = "Start a new game of Omok";
    public static final String EXIT_BUTTON_TEXT = "Exit";
    public static final String EXIT_TOOL_TIP = "Exit the Application";

    public static final String PlayButton_IMAGE_PATH = "/BluePlayButton.jpg"; // assuming the image is in the resource folder

    //private constructor to prevent instantiation
    private Constants(){
    	//private class will not be instantiated.
    }
}




