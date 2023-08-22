import javax.swing.text.Style;
import java.awt.*;

public class Theme
{
    public Color bg, fg, status, cursor;
    public Color textColor, keywordColor, preprocessorColor;
    public static final String[] keywords = {
            "break",
            "case",
            "catch",
            "class",
            "const",
            "constexpr",
            "continue",
            "default",
            "delete",
            "do",
            "dynamic_cast",
            "else",
            "enum",
            "extern",
            "for",
            "goto",
            "if",
            "inline",
            "namespace",
            "new",
            "operator",
            "private:",
            "protected:",
            "public:",
            "return",
            "static",
            "struct",
            "switch",
            "template",
            "this",
            "throw",
            "try",
            "typedef",
            "union",
            "virtual",
            "volatile",
            "while"
    };
    public Theme(Color bg, Color fg, Color status, Color cursor, Color textColor, Color keywordColor, Color preprocessorColor)
    {
        this.bg = bg;
        this.fg = fg;
        this.status = status;
        this.cursor = cursor;

        this.textColor = textColor;
        this.keywordColor = keywordColor;
        this.preprocessorColor = preprocessorColor;
    }

    public static Theme DarkTheme = new Theme(
            new Color(0x331D1D),
            new Color(0x250202),
            new Color(0xBDA8DE),
            new Color(0xFF4600),
            new Color(0xD6C5E3),
            new Color(0x0087E8),
            new Color(0x77079D)
    );
}
