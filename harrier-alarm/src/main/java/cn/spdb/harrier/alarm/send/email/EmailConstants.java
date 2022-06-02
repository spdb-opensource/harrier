package cn.spdb.harrier.alarm.send.email;

public class EmailConstants {

    private EmailConstants() {
        throw new IllegalStateException(EmailConstants.class.getName());
    }


    public static final String ATTACHMENT_FILE_PATH = "attachment.path";

    public static final String DEFAULT_PROTOCOL = "smtp";

    public static final String DEFAULT_SMTP_PORT = "25";

    public static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";

    public static final int NUMBER_1000 = 1000;

    public static final String TR = "<tr>";

    public static final String TD = "<td>";

    public static final String TD_END = "</td>";

    public static final String TR_END = "</tr>";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String TH = "<th>";

    public static final String TH_END = "</th>";

    public static final String MARKDOWN_QUOTE = ">";

    public static final String MARKDOWN_ENTER = "\n";

    public static final String HTML_HEADER_PREFIX = new StringBuilder("<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>")
            .append("<html>")
            .append("<head>")
            .append("<title>harrier</title>")
            .append("<meta name='Keywords' content=''>")
            .append("<meta name='Description' content=''>")
            .append("<style type=\"text/css\">")
            .append("table {margin-top:0px;padding-top:0px;border:1px solid;font-size: 14px;color: #333333;border-width: 1px;border-color: #666666;border-collapse: collapse;}")
            .append("table th {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #dedede;text-align: left;}")
            .append("table td {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #ffffff;text-align: left;}")
            .append("</style>")
            .append("</head>")
            .append("<body style=\"margin:0;padding:0\"><table border=\"1px\" cellpadding=\"5px\" cellspacing=\"-10px\"> ")
            .toString();

    public static final String TABLE_BODY_HTML_TAIL = "</table></body></html>";

    public static final String UTF_8 = "UTF-8";

    public static final String EXCEL_SUFFIX_XLSX = ".xlsx";

    public static final String SINGLE_SLASH = "/";
}
