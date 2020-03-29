import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EndpointFactory } from '../../../services/endpoint-factory.service';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {
  roleList: any;
  imageBase64: string;
  constructor(public activeModal: NgbActiveModal, private formBuilder: FormBuilder, private endpointFactory: EndpointFactory) {
  }
  @Input() data;
  @Output() output = new EventEmitter();
  editForm: FormGroup;
  ngOnInit(): void {
    this.getRole();
    this.createForm();
  }
  getRole() {
    this.endpointFactory.getEndPoint("roles").subscribe(data => {
      if (data.status === "success") {
        this.roleList = data.data;
      };
    }
    );
  }
  createForm() {
    if (this.data.type === 'edit') { this.modeEdit(); }
    else { this.modeAdd(); }
  }
  modeAdd() {
    console.log(this.roleList);
    this.editForm = this.formBuilder.group({
      userId: ['', Validators.required],
      userName: ['', Validators.required],
      role: ['', Validators.required],
      fullName: ['', Validators.required],
      // address: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      email: ['', Validators.required],
      avatarURL: ['', Validators.required]
    });
  }
  modeEdit() {
    this.editForm = this.formBuilder.group({
      userId: [this.data.data.userId, Validators.required],
      userName: [this.data.data.userName, Validators.required],
      role: [this.data.data.role, Validators.required],
      fullName: [this.data.data.fullName, Validators.required],
      // address: [this.data.data.address, Validators.required],
      phoneNumber: [this.data.data.phoneNumber, Validators.required],
      email: [this.data.data.email, Validators.required],
      avatarURL: ['', Validators.required]
    });
  }
  clickClose() {
    this.activeModal.close();
  }
  changeToBase64(event) {
    let files = event.target.files;
    var reader = new FileReader();
    reader.onload = this._handleReaderLoaded.bind(this);
    reader.readAsDataURL(files[0]);
  }

  _handleReaderLoaded(readerEvt) {
    this.imageBase64 = readerEvt.target.result;
  }
  onSubmit() {
    let params: any = {
      userName: this.editForm.value["userName"],
      fullName: this.editForm.value["fullName"],
      imageBase64: this.imageBase64,
      password: "abcdefght",
      phoneNumber: this.editForm.value["phoneNumber"],
      roleID: parseInt(this.editForm.value["role"]),
      email: this.editForm.value["email"],
    };
    if (this.data.type !== 'edit') {
      this.endpointFactory.postEndPoint(params, "users").subscribe(data => {
        if (data.status === "success") {
          this.output.emit("success");
          this.activeModal.close();
        };
      }
      );
    }else{
      this.endpointFactory.putEndPoint(params, "users/"+this.data.data.userId).subscribe(data => {
        if (data.status === "success") {
          this.output.emit("success");
          this.activeModal.close();
        };
      }
      );
    }
  }
}
