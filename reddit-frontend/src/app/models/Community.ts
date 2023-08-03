import { Banned } from "./Banned";
import { Post } from "./Post";
import { User } from "./User";

export class Community{
    community_id:number;
    name:string;
    description:string;
    creationDate:Date;
    isSuspended:boolean;
    suspendedReason:String;
    communityPDFPath:string;
    banned:Banned;
    moderators:User;
    posts:Post;
}