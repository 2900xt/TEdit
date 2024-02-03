import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TEditMenuBar extends JMenuBar
{
    public JMenu fileMenu, buildMenu;
    public void Open()
    {
        String path = FileIO.SelectFileToOpen();
        try {
            String buffer = FileIO.ReadBufferFromFile(path);
            Main.textArea.setText("");
            Main.Append(buffer);
            Main.UpdateFilePath(path);
            Main.FormatFile();
        } catch (IOException | BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void SaveAs()
    {
        String buffer = Main.textArea.getText();
        String path = FileIO.SelectSaveLocation();

        if(path == null)
        {
            return;
        }

        try {
            FileIO.WriteBufferToFile(path, buffer);
            Main.UpdateFilePath(path);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void Save()
    {
        if(Main.currentFilePath == null)
        {
            SaveAs();
            return;
        }

        String buffer = Main.textArea.getText();

        try {
            FileIO.WriteBufferToFile(Main.currentFilePath, buffer);
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }
    public void CreateNewFile()
    {
        Main.currentFileName = "Untitled";
        Main.currentFilePath = null;
        Main.textArea.setText("");
    }

    public int Build()
    {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:\\MinGW\\bin\\g++", Main.currentFilePath, "-o", "C:\\Users\\1038493\\Documents\\a.exe");
            processBuilder.redirectErrorStream(true);
            Process program = processBuilder.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(program.getInputStream()));

            String line;
            while((line = reader.readLine()) != null)
            {
                Main.outputConsole.WriteString(line + '\n');
            }

            reader.close();
            program.waitFor();

            Main.outputConsole.WriteString("\nBuild exited with code " + program.exitValue() + '\n');
            return program.exitValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void Run()
    {
        new Thread( () ->
        {
            Main.outputConsole.clear();
            Build();

            try
            {
                Process program = Runtime.getRuntime().exec( new String[]{"C:\\Users\\1038493\\Documents\\a.exe"});
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(program.getInputStream()));

                String line;
                while((line = reader.readLine()) != null)
                {
                    Main.outputConsole.WriteString(line + '\n');
                }

                reader.close();
                program.waitFor();
                Main.outputConsole.WriteString(String.format("\nProcess exited with code 0x%X\n", program.exitValue()));
            } catch (IOException | BadLocationException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public JMenuItem[] fileOptions = new JMenuItem[]
            {
                    new JMenuItem(new AbstractAction("New") {
                        @Override
                        public void actionPerformed(ActionEvent e) { CreateNewFile(); }
                    }),
                    new JMenuItem(new AbstractAction("Open") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Open();
                        }
                    }),
                    new JMenuItem(new AbstractAction("Save") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Save();
                        }
                    }),
                    new JMenuItem(new AbstractAction("Save As") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            SaveAs();
                        }
                    }),
                    new JMenuItem(new AbstractAction("Close") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    })
            };

    public JMenuItem[] toolsOptions = new JMenuItem[]
            {
                    new JMenuItem(new AbstractAction("Build") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Build();
                        }
                    }),

                    new JMenuItem(new AbstractAction("Run") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Run();
                        }
                    })
            };
    public TEditMenuBar()
    {
        fileMenu = new JMenu("File");

        for(JMenuItem m : fileOptions)
        {
            fileMenu.add(m);
        }

        buildMenu = new JMenu("Tools");

        for(JMenuItem m : toolsOptions)
        {
            buildMenu.add(m);
        }

        add(fileMenu);
        add(buildMenu);
    }
}
