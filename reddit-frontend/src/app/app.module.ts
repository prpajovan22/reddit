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

@NgModule({
  declarations: [
    AppComponent,
    AllUsersComponent,
    RegisterComponent,
    LoginComponent,
    UpdateUserComponent,
    AllPostsComponent,
    ProfileUserComponent,
    ChangePasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [Ng2SearchPipeModule, Ng2OrderModule,
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
        JwtHelperService],
  bootstrap: [AppComponent]
})
export class AppModule { }
