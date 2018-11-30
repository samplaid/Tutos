/**
 * This class holds the question scores informations. It represents the table SCORE_BCFTS.
 * The score is determined by the question code (field checkCode) and the response to the question (fieldresponse.)
 */
export class ScoreBCFT {
    checkCode: string ;
	response: string;
	score?: number;
}