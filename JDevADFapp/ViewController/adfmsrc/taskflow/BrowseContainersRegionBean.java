package taskflow;

import oracle.adf.controller.TaskFlowId;

public class BrowseContainersRegionBean {
    
    private String taskFlowId = "/WEB-INF/task-flows/browse-containers-task-flow-definition.xml#browse-containers-task-flow-definition";

    public BrowseContainersRegionBean() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }
}
