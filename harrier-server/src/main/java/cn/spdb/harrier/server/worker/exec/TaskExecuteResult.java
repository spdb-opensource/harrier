

package cn.spdb.harrier.server.worker.exec;

/**
 * command execute result
 */
public class TaskExecuteResult {

    /**
     * command exit code
     */
    private Integer exitStatusCode;

    /**
     *  appIds
     */
    private String appIds;

    /**
     * process id
     */
    private Integer processId;


    public TaskExecuteResult(){
        this.exitStatusCode = 0;
    }


    public Integer getExitStatusCode() {
        return exitStatusCode;
    }

    public void setExitStatusCode(Integer exitStatusCode) {
        this.exitStatusCode = exitStatusCode;
    }

    public String getAppIds() {
        return appIds;
    }

    public void setAppIds(String appIds) {
        this.appIds = appIds;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }
}
