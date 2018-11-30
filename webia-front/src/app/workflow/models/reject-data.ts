export class RejectData {
    
    rejectDataId: number;
	WorkflowItemId: number;
	step: StepLightDTO;
	rejectComment: string;
	creationUser: string;
    creationDt: Date;

    constructor(rejectDataId: number, WorkflowItemId: number, step: StepLightDTO, rejectComment: string, creationUser: string, creationDt: Date) {
        this.rejectDataId = rejectDataId;
        this.WorkflowItemId = WorkflowItemId;
        this.step = step;
        this.rejectComment = rejectComment;
        this.creationUser = creationUser;
        this.creationDt = creationDt;
    }
}


export class StepLightDTO {
	stepId: number;
    stepWorkflow: string;
    
    constructor(stepId: number, stepWorkflow: string) {
        this.stepId = stepId;
        this.stepWorkflow = stepWorkflow;
    }
}