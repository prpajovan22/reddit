import { Post } from "./Post";
import { Reaction } from "./Reaction";
import { Reports } from "./Reports";
import { User } from "./User";

export class Comments{
    comment_id:number;
    text:string;
    timestamp:Date;
    isDeleted:boolean;
    reactions:Reaction;
    reports:Reports;
}