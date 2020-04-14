import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EndpointFactory } from '../../services/endpoint-factory.service';
import { PostElement } from '../model/post.model';
import { DeleteItemComponent } from './delete-item/delete-item.component';
import { PurchaseElement } from '../model/PurchaseElement.model';
import { PurchaseInfoComponent } from './purchase-info/purchase-info.component';

@Component({
  selector: 'app-list-item',
  templateUrl: './list-item.component.html',
  styleUrls: ['./list-item.component.scss']
})
export class ListItemComponent implements OnInit {
  dataSource: MatTableDataSource<PostElement>;
  dataList: PostElement[] = null;
  displayedColumns: string[] = ['imageURL','purchaseId','postId','buyerName', 'unitPrice', 'purchaseNumber', 'dateOfOrder', 'status', 'edit', 'delete'];
  @ViewChild(MatSort, { static: true }) sort: MatSort;
   constructor(private modalService: NgbModal, private changeDetectorRefs: ChangeDetectorRef, private endpointFactory: EndpointFactory) {

   }
  ngOnInit() {
    this.setData();
  }
  setData() {
    this.loadData();
    this.setDataSource();
  }
  loadData() {
    this.endpointFactory.getEndPoint("purchases").subscribe(data => {
      if (data.status === "success") {
        const temp = [];
        data.data.forEach((element, index) => {
          let post = new PurchaseElement();
          post.purchaseId = element.id;
          post.postId = element.post.id;
          post.sellerName = element.post.user.userName;
          post.buyerName = element.buyer.userName;
          post.unitPrice = element.post.unitPrice;
          post.purchaseNumber = element.purchaseNumber;
          post.statusPurchase = element.statusPurchase;
          post.dateOfOrder=new Date(element.dateOfOrder[0],element.dateOfOrder[1],element.dateOfOrder[2]);
          post.imageURL = element.post.imagePost.url;
          temp.push(post);
        });
        this.dataList = temp;
      };
    }
    );
  }
  setDataSource() {
    setTimeout(() => {
      this.dataSource = new MatTableDataSource(this.dataList);
      this.dataSource.sort = this.sort;
    }, 1000);

  }
  deleteItem(element: any) {
    const modalRef = this.modalService.open(DeleteItemComponent, { size: 'lg', windowClass: 'delete-modal', centered: true });
    modalRef.componentInstance.data = { data: element }
    modalRef.componentInstance.output.subscribe((res) => {
      if (res === "success") {
        this.setData();
      }
    });
  }

  editItem(element: any) {
    const modalRef = this.modalService.open(PurchaseInfoComponent, { size: 'lg', windowClass: 'edit-modal', centered: true });
    modalRef.componentInstance.data = { data: element, type: 'edit' };
    modalRef.componentInstance.output.subscribe((res) => {
      if (res === "success") {
        this.setData();
      }
    });
  }

  searcPost(search: string) {
    // this.endpointFactory.getEndPoint("purchases/search?keySearch="+search).subscribe(data => {
    //   if (data.status === "success") {
    //     const temp = [];
    //     data.data.forEach((element, index) => {
    //       let post = new PurchaseElement();
    //       post.purchaseId = element.id;
    //       post.postId = element.post.id;
    //       post.sellerName = element.post.user.userName;
    //       post.buyerName = element.buyer.userName;
    //       post.unitPrice = element.post.unitPrice;
    //       post.purchaseNumber = element.purchaseNumber;
    //       post.statusPurchase = element.statusPurchase;
    //       post.dateOfOrder=new Date(element.dateOfOrder[0],element.dateOfOrder[1],element.dateOfOrder[2]);
    //       post.imageURL = element.post.imagePost.url;
    //       temp.push(post);
    //     });
    //     this.dataList = temp;
    //     this.setDataSource();
    //   };
    // }
    // );
  }
}
