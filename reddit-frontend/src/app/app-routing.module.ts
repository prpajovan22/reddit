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
import { UpdatePostComponent } from './components/update-post/update-post.component';
import { RepliesComponent } from './components/replies/replies.component';
import { OneCommunityComponent } from './components/one-community/one-community.component';
import { UpdateCommunityComponent } from './components/update-community/update-community.component';
import { ProfileUserComponent } from './components/profile-user/profile-user.component';
import { UpdateCommentComponent } from './components/update-comment/update-comment.component';
import { CreateCommentComponent } from './components/create-comment/create-comment.component';
import { ReportPostComponent } from './components/report-post/report-post.component';
import { AllReportsComponent } from './components/all-reports/all-reports.component';
import { ReportCommentComponent } from './components/report-comment/report-comment.component';

const routes: Routes = [
  {path: '',pathMatch:'full', redirectTo:'home'},
  {path:'home',component:AllPostsComponent},
  {path:'login',component:LoginComponent},
  {path:'registration',component:RegisterComponent},
  {path:'users',component:AllUsersComponent},
  {path:'profile',component:ProfileUserComponent},
  {path:'communitys',component:AllCommunitysComponent},
  {path:'createCommunity',component:CreateCommunityComponent},
  {path:'createPost/:community_id',component:CreatePostComponent},
  {path:'communityPosts/:id',component:CommunityPostsComponent},
  {path:'updateUser',component:ChangePasswordComponent},
  {path:'showComments/:post_id',component:AllCommentsComponent},
  {path:'updatePost/:post_id',component:UpdatePostComponent},
  {path:'replies/:comment_id',component:RepliesComponent},
  {path:'one-community/:community_id',component:OneCommunityComponent},
  {path:'updateCommunity/:community_id',component:UpdateCommunityComponent},
  {path:'updateComment/:comment_id',component:UpdateCommentComponent},
  {path:'createComment/:post_id',component:CreateCommentComponent},
  {path:'replies/:comment_id',component:RepliesComponent},
  {path:'reportPost/:post_id',component:ReportPostComponent},
  {path:'reportComment/:comment_id',component:ReportCommentComponent},
  {path:'allReports',component:AllReportsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
