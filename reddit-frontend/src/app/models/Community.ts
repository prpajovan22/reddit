import { Banned } from "./Banned";
import { Post } from "./Post";
import { User } from "./User";

export class Community{
    community_id:number;
    name:string;
    description:string;
    creationDate:Date;
    isSuspended:boolean;
    suspendedReason:string;
    communityPDF:string;
    communityPDFName:string;
    banned:Banned;
    moderators:User;
    posts:Post;
    postCount: number;
    totalReaction:number;
}