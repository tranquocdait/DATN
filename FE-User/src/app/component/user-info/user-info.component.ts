import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EditUserComponent } from './edit-user/edit-user.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.scss']
})
export class UserInfoComponent implements OnInit {

  constructor(private modalService: NgbModal) { }
  data: any;
  ngOnInit() {
  }
  changeInfor() {
    const modalRef = this.modalService.open(EditUserComponent, { size: 'lg', windowClass: 'edit-modal', centered: true });
    modalRef.componentInstance.data = { data: this.data, type: 'edit' };
    modalRef.componentInstance.output.subscribe((res) => {
      if (res === 'success') {

      }
    });
  }
  changePassword() {
    const modalRef = this.modalService.open(ChangePasswordComponent, { size: 'lg', windowClass: 'edit-modal', centered: true });
    modalRef.componentInstance.data = { data: this.data};
    modalRef.componentInstance.output.subscribe((res) => {
      if (res === 'success') {

      }
    });
  }
}
