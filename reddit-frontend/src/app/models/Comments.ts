import { Reaction } from "./Reaction";
import { Reports } from "./Reports";

export class Comments{
    comment_id:number;
    text:string;
    timestamp:Date;
    isDeleted:boolean;
    comment:Comments;
    reactions:Reaction;
    reports:Reports;
}