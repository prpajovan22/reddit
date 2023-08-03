import { User } from "./User";

export class Reaction{
    reaction_id:number;
    timestamp:Date;
    type:string;
    user:User;
}