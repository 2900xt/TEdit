import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main
{
    public static String currentFileName, currentFilePath;
    public static JFrame mainFrame;
    public static TEditMenuBar menuBar;
    public static TEditStatusBar statusBar;
    public static JTextPane textArea;
    public static Theme currentTheme;
    public static Style textStyle, keywordStyle, preprocessorStyle;
    public static OutputConsole outputConsole;

    public static void main(String[] args) 
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            mainFrame = new JFrame("TEdit v0.1");
            mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            mainFrame.setSize(1280, 1020);
            mainFrame.setIconImage(new ImageIcon("res/logo.png").getImage());

            menuBar = new TEditMenuBar();

            textArea = new JTextPane();
            textArea.setEditable(false);

            outputConsole = new OutputConsole();

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setBounds(10, 10, mainFrame.getWidth() - 40, mainFrame.getHeight() - 300);

            statusBar = new TEditStatusBar();

            SetTheme(Theme.DarkTheme);

            textArea.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {

                    if((e.getKeyChar() >= 32 && e.getKeyChar() <= 126) || e.getKeyChar() == '\n' || e.getKeyChar() == '\t')
                    {
                        try {
                            Append(e.getKeyChar() + "");
                        } catch (BadLocationException ex) {
                            throw new RuntimeException(ex);
                        }
                    }


                    if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        try {
                            DeleteChar();
                        } catch (BadLocationException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            textArea.setFocusable(true);

            mainFrame.setJMenuBar(menuBar);
            mainFrame.add(scrollPane);
            outputConsole.AddToFrame(mainFrame);
            statusBar.AddToFrame(mainFrame);

            mainFrame.setVisible(true);
        } catch (Throwable t)
        {
            t.printStackTrace();
            JOptionPane.showMessageDialog(null, t.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public static void UpdateFilePath(String path)
    {
        if(path == null)
        {
            currentFilePath = null;
            currentFileName = "Untitled";
            return;
        }
        Main.currentFileName = path.substring(path.lastIndexOf('\\') + 1);
        Main.currentFilePath = path;
        statusBar.currentFile.setText(currentFileName);
    }

    public static void SetTheme(Theme theme)
    {
        currentTheme = theme;
        mainFrame.getContentPane().setBackground(currentTheme.bg);
        textArea.setBackground(currentTheme.fg);
        textArea.setCaretColor(currentTheme.cursor);
        statusBar.currentFile.setForeground(currentTheme.status);

        textStyle = textArea.addStyle("TextStyle", null);
        StyleConstants.setForeground(textStyle, theme.textColor);

        preprocessorStyle = textArea.addStyle("PreprocessorStyle", null);
        StyleConstants.setForeground(textStyle, theme.preprocessorColor);

        keywordStyle = textArea.addStyle("KeywordStyle", null);
        StyleConstants.setForeground(keywordStyle, theme.keywordColor);

        outputConsole.textStyle = outputConsole.consoleOutput.addStyle("TextStyle", null);
        StyleConstants.setForeground(outputConsole.textStyle, theme.consoleText);
        outputConsole.consoleOutput.setBackground(theme.consoleBg);

        mainFrame.repaint();
    }

    public static void Append(String s) throws BadLocationException
    {
        textArea.getStyledDocument().insertString(textArea.getCaretPosition(), s, textStyle);
        if(s.equals(" "))
        {
            UpdateFormatting();
        }
    }


    public static void UpdateFormatting() throws BadLocationException
    {
        int wordEndIndex = textArea.getCaretPosition() - 1;
        int wordStartIndex = textArea.getText().lastIndexOf(' ', wordEndIndex - 1) + 1;


        String word = textArea.getText().substring(wordStartIndex, wordEndIndex);

        for(String k : Theme.keywords)
        {
            if(word.equals(k))
            {
                textArea.getStyledDocument().remove(wordStartIndex, wordEndIndex - wordStartIndex);
                textArea.getStyledDocument().insertString(wordStartIndex, k, keywordStyle);
            }
        }
    }

    public static void FormatFile() throws BadLocationException
    {
        for(int currentIndex = 0; currentIndex < textArea.getText().length(); currentIndex = textArea.getText().indexOf(' ', currentIndex + 1))
        {
            int wordStartIndex = currentIndex + 1, wordEndIndex = -1;

            int spaceInd = textArea.getText().indexOf(' ', wordStartIndex);
            int enterInd = textArea.getText().indexOf('\n', wordStartIndex);
            int bracketInd = textArea.getText().indexOf('(', wordStartIndex);

            if(spaceInd != -1)
            {
                wordEndIndex = spaceInd;
            }

            if(enterInd != -1 && enterInd < wordEndIndex)
            {
                wordEndIndex = enterInd;
            }

            if(bracketInd != -1 && bracketInd < wordEndIndex)
            {
                wordEndIndex = bracketInd;
            }

            if(wordEndIndex == -1) break;

            String word = textArea.getText().substring(wordStartIndex, wordEndIndex);

            for(String k : Theme.keywords)
            {
                if(word.equals(k))
                {
                    textArea.getStyledDocument().remove(wordStartIndex, wordEndIndex - wordStartIndex);
                    textArea.getStyledDocument().insertString(wordStartIndex, k, keywordStyle);
                }
            }
        }
    }

    public static void DeleteChar() throws BadLocationException
    {
        if(textArea.getCaretPosition() == 0) return;
        textArea.getStyledDocument().remove(textArea.getCaretPosition() - 1, 1);
    }

}