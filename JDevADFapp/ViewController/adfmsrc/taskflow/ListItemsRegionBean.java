package taskflow;

import oracle.adf.controller.TaskFlowId;

public class ListItemsRegionBean {
    
    private String taskFlowId = "/WEB-INF/task-flows/list-artifacts-task-flow-definition.xml#list-artifacts-task-flow-definition";

    public ListItemsRegionBean() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public String listartifactstaskflowdefinition() {
        taskFlowId = "/WEB-INF/task-flows/list-artifacts-task-flow-definition.xml#list-artifacts-task-flow-definition";
        return null;
    }

    public String listdocumentstaskflowdefinition() {
        taskFlowId = "/WEB-INF/task-flows/list-documents-task-flow-definition.xml#list-documents-task-flow-definition";
        return null;
    }

    public String listunifiedmessagestaskflowdefinition() {
        taskFlowId = "/WEB-INF/task-flows/list-unified-messages-task-flow-definition.xml#list-unified-messages-task-flow-definition";
        return null;
    }
    
}
