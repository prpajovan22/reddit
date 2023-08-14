import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AllUsersComponent } from './components/all-users/all-users.component';
import { RegisterComponent } from './components/register/register.component';
import { AllPostsComponent } from './components/all-posts/all-posts.component';
import { AllCommunitysComponent } from './components/all-communitys/all-communitys.component';
import { CreateCommunityComponent } from './components/create-community/create-community.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { CommunityPostsComponent } from './components/community-posts/community-posts.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { AllCommentsComponent } from './components/all-comments/all-comments.component';

const routes: Routes = [
  {path: '',pathMatch:'full', redirectTo:'home'},
  {path:'home',component:AllPostsComponent},
  {path:'login',component:LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'users',component:AllUsersComponent},
  {path:'communitys',component:AllCommunitysComponent},
  {path:'createCommunity',component:CreateCommunityComponent},
  {path:'createPost/:id',component:CreatePostComponent},
  {path:'communityPosts/:id',component:CommunityPostsComponent},
  {path:'updateUser',component:ChangePasswordComponent},
  {path:'showComments/:post_id',component:AllCommentsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
