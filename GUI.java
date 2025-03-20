import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Stack;
import javax.swing.*;

public class GUI implements ActionListener {

    /* JFrame works like the main Window where components like labels, buttons, textfields are added to create a GUI */
    JFrame window;
    JTextArea textArea;
    JScrollPane scrollPane;
    boolean wordWrapOn = false;

    /* Top Menu Bar */
    JMenuBar menuBar;
    JMenu menuFile, menuEdit, menuFormat, menuColour, menuDraw;

    /* File Menu */
    JMenuItem iNew, iOpen, iSave, iSaveAs, iExit;

    /* Format Menu */
    JMenuItem iWrap, iFontArial, iFontCSMS, iFontTNR, iFontSize8, iFontSize12, iFontSize16, iFontSize20, iFontSize24, iFontSize28;
    JMenu menuFont, menuFontSize;

    /* Edit Menu */
    JMenuItem iCopy, iCopy1, iCut1, iCut, iPaste1, iPaste, iTD;
    Stack<String> stack = new Stack<>();
    String str = "";
    int i = 0, pos = 0;
    GregorianCalendar gcalendar;

    /* Colour Menu */
    JMenuItem iCol1, iCol2, iCol3;

    /* Draw Menu */
    JMenuItem iDraw;  // New menu item for Draw feature

    File fun = new File(this);
    Format format = new Format(this);
    Edit edit = new Edit(this);
    Colour col = new Colour(this);

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        createWindow();
        createTextArea();
        createMenuBar();
        createFileMenu();
        createEdit();
        createFormatMenu();
        createColourMenu();
        createDrawMenu();  // New Draw Menu

        format.selectedFont = "Arial";
        format.createFont(12);
        format.wordWrap();
        col.changeColour("White");
        window.setVisible(true);
    }

    public void createWindow() {
        window = new JFrame("Notepad");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createTextArea() {
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        window.add(scrollPane);
    }

    public void createMenuBar() {
        menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);

        menuFormat = new JMenu("Format");
        menuBar.add(menuFormat);

        menuColour = new JMenu("Background Colour");
        menuBar.add(menuColour);

        menuDraw = new JMenu("Draw"); // New Draw Menu
        menuBar.add(menuDraw);
    }

    public void createFileMenu() {
        iNew = new JMenuItem("New");
        iNew.addActionListener(this);
        iNew.setActionCommand("New");
        menuFile.add(iNew);

        iOpen = new JMenuItem("Open");
        iOpen.addActionListener(this);
        iOpen.setActionCommand("Open");
        menuFile.add(iOpen);

        iSave = new JMenuItem("Save");
        iSave.addActionListener(this);
        iSave.setActionCommand("Save");
        menuFile.add(iSave);

        iSaveAs = new JMenuItem("Save As");
        iSaveAs.addActionListener(this);
        iSaveAs.setActionCommand("SaveAs");
        menuFile.add(iSaveAs);

        iExit = new JMenuItem("Exit");
        iExit.addActionListener(this);
        iExit.setActionCommand("Exit");
        menuFile.add(iExit);
    }

    public void createEdit() {
        iCopy = new JMenuItem("Copy to Stack");
        iCopy.addActionListener(this);
        iCopy.setActionCommand("Copy to Stack");
        menuEdit.add(iCopy);

        iCut = new JMenuItem("Cut to Stack");
        iCut.addActionListener(this);
        iCut.setActionCommand("Cut to Stack");
        menuEdit.add(iCut);

        iPaste = new JMenuItem("Paste from Stack");
        iPaste.addActionListener(this);
        iPaste.setActionCommand("Paste from Stack");
        menuEdit.add(iPaste);

        iCopy1 = new JMenuItem("Copy (Ctrl + C)");
        iCopy1.addActionListener(this);
        iCopy1.setActionCommand("Copy (Ctrl + C)");
        menuEdit.add(iCopy1);

        iCut1 = new JMenuItem("Cut (Ctrl + X)");
        iCut1.addActionListener(this);
        iCut1.setActionCommand("Cut (Ctrl + X)");
        menuEdit.add(iCut1);

        iPaste1 = new JMenuItem("Paste (Ctrl + V)");
        iPaste1.addActionListener(this);
        iPaste1.setActionCommand("Paste (Ctrl + V)");
        menuEdit.add(iPaste1);

        iTD = new JMenuItem("Time & Date");
        iTD.addActionListener(this);
        iTD.setActionCommand("Time & Date");
        menuEdit.add(iTD);
    }

    public void createFormatMenu() {
        iWrap = new JMenuItem("Word Wrap : OFF");
        iWrap.addActionListener(this);
        iWrap.setActionCommand("Word Wrap");
        menuFormat.add(iWrap);
    }

    public void createColourMenu() {
        iCol1 = new JMenuItem("White");
        iCol1.addActionListener(this);
        iCol1.setActionCommand("White");
        menuColour.add(iCol1);

        iCol2 = new JMenuItem("Black");
        iCol2.addActionListener(this);
        iCol2.setActionCommand("Black");
        menuColour.add(iCol2);

        iCol3 = new JMenuItem("Blue");
        iCol3.addActionListener(this);
        iCol3.setActionCommand("Blue");
        menuColour.add(iCol3);
    }

    public void createDrawMenu() {
        iDraw = new JMenuItem("Open Drawing Pad"); // Menu item for Drawing feature
        iDraw.addActionListener(this);
        iDraw.setActionCommand("Draw");
        menuDraw.add(iDraw);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                fun.newFile();
                break;
            case "Open":
                fun.open();
                break;
            case "SaveAs":
                fun.saveAs();
                break;
            case "Save":
                fun.save();
                break;
            case "Exit":
                fun.exit();
                break;
            case "Word Wrap":
                format.wordWrap();
                break;
            case "White":
            case "Black":
            case "Blue":
                col.changeColour(command);
                break;
            case "Copy to Stack":
            case "Copy (Ctrl + C)":
            case "Cut to Stack":
            case "Cut (Ctrl + X)":
            case "Paste from Stack":
            case "Paste (Ctrl + V)":
            case "Time & Date":
                edit.editText(command);
                break;
            case "Draw":
                new Draw(); // Opens the drawing window when "Draw" is clicked
                break;
        }
    }
}
