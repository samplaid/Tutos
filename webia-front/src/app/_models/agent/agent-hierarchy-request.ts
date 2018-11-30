import { pageableCriteria } from "../pageable-criteria";

export class AgentHierarchyRequest extends pageableCriteria {
    public aghId?: number;
	public masterBrokerId?: string;
	public subBrokerId?: string;
	public type?: number;
	public status?: number;
}