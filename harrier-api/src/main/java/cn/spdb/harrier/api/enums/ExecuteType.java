
package cn.spdb.harrier.api.enums;

/**
 * execute type
 */
public enum ExecuteType {


    /**
     * operation type
     * 1 repeat running
     * 2 resume pause
     * 3 resume failure
     * 4 stop
     * 5 pause
     */
    NONE, REPEAT_RUNNING, RECOVER_SUSPENDED_PROCESS, START_FAILURE_TASK_PROCESS, STOP, PAUSE;


    public static ExecuteType getEnum(int value){
        for (ExecuteType e: ExecuteType.values()) {
            if(e.ordinal() == value) {
                return e;
            }
        }
        return null;
    }
}
