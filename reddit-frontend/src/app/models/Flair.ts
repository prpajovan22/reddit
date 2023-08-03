import { Community } from "./Community";
import { Post } from "./Post";

export class Flair{
    flair_id:number;
    name:string;
    post:Post;
    communities:Community;
}