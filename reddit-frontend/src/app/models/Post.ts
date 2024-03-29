import { Comments } from "./Comments";
import { Flair } from "./Flair";
import { Reaction } from "./Reaction";
import { Reports } from "./Reports";

export class Post{
    post_id:number;
    title:string;
    text:string;
    creationDate:Date;
    postPDFPath:string;
    descriptionPDF:string;
    flair:Flair;
    reports:Reports;
    reaction:Reaction[];
    comments:Comments;
    community_id: number;
    netReactions: number;
    commentCount:number;
}