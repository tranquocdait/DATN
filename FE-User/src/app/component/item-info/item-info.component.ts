import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-item-info',
  templateUrl: './item-info.component.html',
  styleUrls: ['./item-info.component.scss']
})
export class ItemInfoComponent implements OnInit {

  constructor() { }
  currentRate = 3.5;
  numberItem: number = 1;
  ngOnInit() {
  }
  changeNumber(change: String): void {
    if (change === "add") {
      this.numberItem += 1;
    } else {
      if (this.numberItem > 0)
        this.numberItem -= 1
    }
  }
  purchaseItem():void{
    //todo
  }
}
