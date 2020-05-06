import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ChartsModule } from 'ng2-charts';
import { LoginComponent } from './login.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SignupComponent } from './signup/signup.component';
import { LoginRoutes } from './login.routing';

@NgModule({
    imports: [FormsModule,
        CommonModule,
        RouterModule.forChild(LoginRoutes),
        ChartsModule,
        ReactiveFormsModule,
        NgbModule],
    declarations: [LoginComponent, SignupComponent]
})
export class LoginModule { }
