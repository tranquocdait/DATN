import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { LocalStoreManager } from '../../services/local-store-manager.service';
import { EndpointFactory } from '../../services/endpoint-factory.service';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
    roleAdmin = 'admin';
    loginStatus = false;
    @ViewChild('tt', { static: true }) ttUsername: NgbTooltip;

    messageErrorArray = {
        corfirmPassword: 'Nhập lại mật khẩu không đúng',
        success: 'Đăng kí thành công !'
    };
    isSuccess = false;
    messageError: any;
    constructor(private formBuilder: FormBuilder, private router: Router, private localStoreManager: LocalStoreManager,
        private endpointFactory: EndpointFactory) {
        this.createForm();
    }

    loginForm: FormGroup;

    ngOnInit() {

    }

    createForm() {
        this.loginForm = this.formBuilder.group({
            userName: ['', Validators.required],
            password: ['', Validators.required],
            confirmPassword: ['', Validators.required],
        });
    }

    onSubmit() {
        const params: any = {
            userName: this.loginForm.value['userName'],
            password: this.loginForm.value['password']
        };
        this.endpointFactory.postEndPoint(params, 'users/fist').subscribe(data => {
            if (data.status === 'success') {
                this.isSuccess = true;
                this.messageError = this.messageErrorArray.success;
            }
        }, error => {
        });
        this.loginFailed();
    }
    loginFailed() {
        this.localStoreManager.removeToken();
        this.loginStatus = true;
    }

    checkForm(): boolean {
        if (this.loginForm.value['confirmPassword'].length > 6) {
            if (this.loginForm.value['password'] !== this.loginForm.value['confirmPassword']) {
                this.messageError = this.messageErrorArray.corfirmPassword;
                return false;
            }
        }
        return true;
    }

    resetMessage(){
        this.isSuccess = false;
    }
}
