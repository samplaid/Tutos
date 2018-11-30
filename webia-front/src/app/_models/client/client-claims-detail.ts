export class ClientClaimsDetail {
    ccmId: number;
	cliId: number;
	dateOfPtd?: Date;
	dateDeathNotified?: Date;
	dateOfDeath?: Date;
	causeOfDeath?: string;
	deathCertificate?: Date;
	ptdCertification?: Date;
	datePtdNotified?: Date;
	causeOfPtd?: string;
	status?: number;	
	dateCiNotified?: Date;
	dateOfCi?: Date;
	ciCertification?: Date;
	causeOfCi?: string;
	createdProcess?: string;
	modifyProcess?: string;
    
    /// Allow to add other field  dynamically
	[other: string]: any;
	
	constructor(cliId:number){
		this.cliId = cliId;
		this.createdProcess = "WEBIA";
	}
}