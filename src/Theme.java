import javax.swing.text.Style;
import java.awt.*;

public class Theme
{
    public Color bg, fg, status, cursor, consoleBg, consoleText;
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
    public Theme(Color bg, Color fg, Color status, Color cursor, Color textColor, Color keywordColor, Color preprocessorColor, Color consoleText, Color consoleBg)
    {
        this.bg = bg;
        this.fg = fg;
        this.status = status;
        this.cursor = cursor;

        this.textColor = textColor;
        this.keywordColor = keywordColor;
        this.preprocessorColor = preprocessorColor;
        this.consoleBg = consoleBg;
        this.consoleText = consoleText;
    }

    public static Theme DarkTheme = new Theme(
            new Color(0x310000),
            new Color(0x170101),
            new Color(0xDBF4FF),
            new Color(0xFF4600),
            new Color(0xFFFFFF),
            new Color(0x0087E8),
            new Color(0x77079D),
            new Color(0xFFFFFF),
            new Color(0x000000)
    );
}
