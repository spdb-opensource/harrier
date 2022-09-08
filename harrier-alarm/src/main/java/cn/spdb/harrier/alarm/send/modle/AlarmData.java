
package cn.spdb.harrier.alarm.send.modle;

/**
 * AlertData
 */
public class AlarmData {

 
    /**
     * title
     */
    private String title;
    /**
     * content
     */
    private String content;


    public String getTitle() {
        return title;
    }

    public AlarmData setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AlarmData setContent(String content) {
        this.content = content;
        return this;
    }

}
