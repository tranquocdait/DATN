import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-confirm-purchase',
  templateUrl: './confirm-purchase.component.html',
  styleUrls: ['./confirm-purchase.component.scss']
})
export class ConfirmPurchaseComponent implements OnInit {
  name:string;
  address:string;
  phoneNumber:string;
  constructor() { }

  ngOnInit() {
  }
  confirmPurchase(){
    //todo
  }

}
