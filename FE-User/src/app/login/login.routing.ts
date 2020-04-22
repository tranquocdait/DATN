import { Routes } from '@angular/router';
import { LoginComponent } from './login.component';
import { SignupComponent } from './signup/signup.component';


export const LoginRoutes: Routes = [
    {
        path: '',
        children: [
            {
                path: '',
                component: LoginComponent,
                pathMatch: 'full'
            },
            {
                path: 'sign-up',
                component: SignupComponent
            }
        ]
    }
];
