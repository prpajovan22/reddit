import { SafeResourceUrl } from "@angular/platform-browser";

export class UserFront{
    user_id:number;
    username:string;
    password:string;
    email:string;
    avatar:string;
    registrationDate:Date;
    description:string;
    displayName:string;
    userRole:string;
    suspended:boolean;
    sanitisedImage: SafeResourceUrl;
}