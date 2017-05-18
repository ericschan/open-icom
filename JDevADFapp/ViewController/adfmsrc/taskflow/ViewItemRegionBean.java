package taskflow;

import oracle.adf.controller.TaskFlowId;

public class ViewItemRegionBean {
    
    private String taskFlowId = "/WEB-INF/task-flows/view-artifact-task-flow-definition.xml#view-artifact-task-flow-definition";

    public ViewItemRegionBean() {
        super();
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public String viewartifacttaskflowdefinition() {
        taskFlowId = "/WEB-INF/task-flows/view-artifact-task-flow-definition.xml#view-artifact-task-flow-definition";
        return null;
    }

    public String viewdocumenttaskflowdefinition() {
        taskFlowId = "/WEB-INF/task-flows/view-document-task-flow-definition.xml#view-document-task-flow-definition";
        return null;
    }

    public String viewunifiedmessagetaskflowdefinition() {
        taskFlowId = "/WEB-INF/task-flows/view-unified-message-task-flow-definition.xml#view-unified-message-task-flow-definition";
        return null;
    }
}
