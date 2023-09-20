import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { Ng2OrderModule } from 'ng2-order-pipe';
import { AllUsersComponent } from './components/all-users/all-users.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { UpdateUserComponent } from './components/update-user/update-user.component';
import { AllPostsComponent } from './components/all-posts/all-posts.component';
import { ProfileUserComponent } from './components/profile-user/profile-user.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { CommunityPostsComponent } from './components/community-posts/community-posts.component';
import { AllCommunitysComponent } from './components/all-communitys/all-communitys.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { CreateCommunityComponent } from './components/create-community/create-community.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { AllCommentsComponent } from './components/all-comments/all-comments.component';
import { UpdatePostComponent } from './components/update-post/update-post.component';
import { RepliesComponent } from './components/replies/replies.component';
import { OneCommunityComponent } from './components/one-community/one-community.component';
import { UpdateCommunityComponent } from './components/update-community/update-community.component';
import { UpdateCommentComponent } from './components/update-comment/update-comment.component';
import { CreateCommentComponent } from './components/create-comment/create-comment.component';
import { ReportPostComponent } from './components/report-post/report-post.component';
import { ReportCommentComponent } from './components/report-comment/report-comment.component';
import { AllReportsComponent } from './components/all-reports/all-reports.component';
import { CreateReplyComponent } from './components/create-reply/create-reply.component';
import { SuspendCommnuityComponent } from './components/suspend-commnuity/suspend-commnuity.component';
import { ChangeUserComponent } from './components/change-user/change-user.component';

@NgModule({
  declarations: [
    AppComponent,
    AllUsersComponent,
    RegisterComponent,
    LoginComponent,
    UpdateUserComponent,
    AllPostsComponent,
    ProfileUserComponent,
    ChangePasswordComponent,
    CommunityPostsComponent,
    AllCommunitysComponent,
    NavBarComponent,
    CreateCommunityComponent,
    CreatePostComponent,
    AllCommentsComponent,
    UpdatePostComponent,
    RepliesComponent,
    OneCommunityComponent,
    UpdateCommunityComponent,
    UpdateCommentComponent,
    CreateCommentComponent,
    ReportPostComponent,
    ReportCommentComponent,
    AllReportsComponent,
    CreateReplyComponent,
    SuspendCommnuityComponent,
    ChangeUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [Ng2SearchPipeModule, Ng2OrderModule,
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
        JwtHelperService],
  bootstrap: [AppComponent]
})
export class AppModule { }
