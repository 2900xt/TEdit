import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OutputConsole
{
    public JTextPane consoleOutput;
    public Style textStyle;
    public OutputConsole()
    {
        consoleOutput = new JTextPane();
    }
    public void AddToFrame(JFrame frame)
    {
        JScrollPane consoleScroll = new JScrollPane(consoleOutput);
        consoleScroll.setBounds(10, frame.getHeight() - 280, frame.getWidth() - 40, 200);
        consoleOutput.setEditable(false);
        consoleOutput.setFocusable(true);
        consoleOutput.setCaretColor(Color.WHITE);
        frame.add(consoleScroll);
    }

    public void clear()
    {
        consoleOutput.setText("");
    }

    public void WriteString(String data) throws BadLocationException
    {
        consoleOutput.getStyledDocument().insertString(consoleOutput.getStyledDocument().getLength(), data, consoleOutput.getStyledDocument().getStyle("TextStyle"));
    }
}
