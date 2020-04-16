import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateCommentComponent } from './create-comment/create-comment.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-item-info',
  templateUrl: './item-info.component.html',
  styleUrls: ['./item-info.component.scss']
})
export class ItemInfoComponent implements OnInit {

  constructor(private router: Router, private modalService: NgbModal) { }
  currentRate = 3.5;
  numberItem: number = 1;
  data: any;
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
  purchaseItem(): void {
    this.router.navigateByUrl('/component/confirm-purchase');
  }
  createComment(): void {
    const modalRef = this.modalService.open(CreateCommentComponent, { size: 'lg', windowClass: 'create-comment-dialog', centered: true });
    modalRef.componentInstance.data = { data: this.data };
    modalRef.componentInstance.output.subscribe((res) => {
      if (res === "success") {
      }
    });
  }
}
