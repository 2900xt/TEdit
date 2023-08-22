import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIO
{
    public static void WriteBufferToFile(String path, String bufferData) throws IOException
    {
        FileOutputStream fOStream = new FileOutputStream(path);
        fOStream.write(bufferData.getBytes());
    }
    
    public static String ReadBufferFromFile(String path) throws IOException
    {
        FileInputStream fIStream = new FileInputStream(path);
        return new String(fIStream.readAllBytes());
    }

    public static String SelectSaveLocation()
    {
        JFileChooser fChooser = new JFileChooser();
        fChooser.setDialogTitle("Select Save Location");
        fChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fChooser.setFileFilter(new CPPFileFiler());
        if(fChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION)
        {
            return null;
        }

        return fChooser.getSelectedFile().getAbsolutePath();
    }

    public static String SelectFileToOpen()
    {
        JFileChooser fChooser = new JFileChooser();
        fChooser.setDialogTitle("Open File");
        fChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fChooser.setFileFilter(new CPPFileFiler());
        fChooser.showOpenDialog(null);

        return fChooser.getSelectedFile().getAbsolutePath();
    }

    private static class CPPFileFiler extends FileFilter
    {
        @Override
        public boolean accept(File f) {
            if(f.isDirectory())
            {
                return true;
            }

            int ind = f.getName().lastIndexOf('.');

            if(ind == -1)
            {
                return false;
            }

            String extension = f.getName().substring(ind);
            return extension.toLowerCase().charAt(1) == 'c';
        }

        @Override
        public String getDescription() {
            return "C / C++ Files";
        }
    }
}
