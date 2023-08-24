import { Banned } from "./Banned";
import { Comments } from "./Comments";
import { Flair } from "./Flair";
import { Post } from "./Post";
import { Reports } from "./Reports";

export class User{
    user_id:number;
    username:string;
    password:string;
    email:string;
    avatar:File;
    registrationDate:Date;
    description:String;
    displayName:string;
    userRole:String;
    post:Post;
    banned:Banned;
    flairs:Flair;
    comments:Comments;
    reports:Reports;
}