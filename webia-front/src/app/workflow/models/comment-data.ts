export class CommentData {
    date: Date;
    stepName: string;
    user: string;
    html: string

    constructor(date: Date, stepName: string, user: string, html: string){
        this.date = date;
        this.stepName = stepName;
        this.user = user;
        this.html = html;
    }
}