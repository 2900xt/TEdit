import javax.swing.*;

public class TEditStatusBar
{
    public JLabel currentFile = new JLabel();
    public void AddToFrame(JFrame frame)
    {
        currentFile.setVerticalAlignment(JLabel.BOTTOM);
        frame.add(currentFile);
        frame.repaint();
    }
}
