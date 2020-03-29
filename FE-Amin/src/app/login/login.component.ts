import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocalStoreManager } from '../services/local-store-manager.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,private router: Router,private localStoreManager:LocalStoreManager) {
    this.createForm();
   }
  loginForm: FormGroup;
  ngOnInit() {
  }
  createForm(){
    this.loginForm = this.formBuilder.group({
      user: ['', Validators.required],
      password: ['',Validators.required],
      memmory: [''],
    });
  }
  onSubmit(){
    this.localStoreManager.setToken("ok");
    this.router.navigateByUrl('');
  }
}
