import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DialogConfirmComponent } from '../dialog-confirm/dialog-confirm.component';

@Component({
  selector: 'app-confirm-purchase',
  templateUrl: './confirm-purchase.component.html',
  styleUrls: ['./confirm-purchase.component.scss']
})
export class ConfirmPurchaseComponent implements OnInit {
  name:string;
  address:string;
  phoneNumber:string;
  constructor(private modalService: NgbModal) { 
  }

  ngOnInit() {
  }
  confirmPurchase(){
    const modalRef = this.modalService.open(DialogConfirmComponent, { size: 'lg', windowClass: 'delete-modal', centered: true });
    modalRef.componentInstance.data = { title: "Xác nhận đơn hàng" ,content: "Bạn muốn xác nhận đơn hàng?" };
    modalRef.componentInstance.output.subscribe((res) => {
      if (res === "success") {
        alert("ok");
      }
    });
  }

}
