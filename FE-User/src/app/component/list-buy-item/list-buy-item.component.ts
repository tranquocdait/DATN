import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EndpointFactory } from '../../services/endpoint-factory.service';
import { PostElement } from '../model/post.model';
import { PurchaseElement } from '../model/PurchaseElement.model';
import { DialogConfirmComponent } from '../dialog-confirm/dialog-confirm.component';
import { LocalStoreManager } from '../../services/local-store-manager.service';
import { PurchaseBuyInfoComponent } from './purchase-buy-info/purchase-buy-info.component';
import { MatPaginator } from '@angular/material/paginator';

@Component({
    selector: 'app-list-buy-item',
    templateUrl: './list-buy-item.component.html',
    styleUrls: ['./list-buy-item.component.css']
})
export class ListBuyItemComponent implements OnInit {
    dataSource: MatTableDataSource<PostElement>;
    dataList: PostElement[] = null;
    getUrl = 'purchases/buyer';
    displayedColumns: string[] = ['imageURL', 'purchaseId', 'postId', 'buyerName', 'unitPrice', 'purchaseNumber', 'dateOfOrder',
        'statusPurchaseName', 'view', 'delete'];
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    constructor(private modalService: NgbModal, private changeDetectorRefs: ChangeDetectorRef, private endpointFactory: EndpointFactory,
        private localStoreManager: LocalStoreManager) {
        //this.setUrl();
    }
    ngOnInit() {
        this.setData();
    }
    setData() {
        this.loadData();
        this.setDataSource();
    }

    // setUrl(): void {
    //     if (this.localStoreManager.getPostSelected() !== null && this.localStoreManager.getPostSelected() !== '0') {
    //         this.getUrl = 'purchases/user/' + this.localStoreManager.getPostSelected();
    //     } else {
    //         this.getUrl = 'purchases/user';
    //     }
    // }

    loadData() {
        this.endpointFactory.getEndPointByHeader(this.getUrl).subscribe(data => {
            if (data.status === 'success') {
                const temp = [];
                data.data.forEach((element, index) => {
                    const post = new PurchaseElement();
                    post.purchaseId = element.id;
                    post.postId = element.post.id;
                    post.buyerName = element.fullName;
                    post.unitPrice = element.post.unitPrice;
                    post.purchaseNumber = element.purchaseNumber;
                    post.statusPurchaseName = element.statusPurchase.status;
                    post.statusPurchase = element.statusPurchase;
                    post.dateOfOrder = new Date(element.dateOfOrder[0], element.dateOfOrder[1] - 1, element.dateOfOrder[2]);
                    post.imageURL = element.post.imagePosts[0].url;
                    temp.unshift(post);
                });
                this.dataList = temp;
            }
        }
        );
    }

    setDataSource(): void {
        setTimeout(() => {
            this.dataSource = new MatTableDataSource(this.dataList);
            this.dataSource.sort = this.sort;
            this.dataSource.paginator = this.paginator;
        }, 1000);
    }

    deleteItem(element: any): void {
        const modalRef = this.modalService.open(DialogConfirmComponent, { size: 'lg', windowClass: 'delete-modal', centered: true });
        modalRef.componentInstance.data = { title: 'Xác nhận xóa đơn hàng', content: 'Bạn muốn xóa đơn hàng đơn hàng?' };
        modalRef.componentInstance.output.subscribe((res) => {
            if (res === 'success') {
                const params = element.purchaseId;
                this.endpointFactory.deleteEndPoint(params, 'purchases/' + params).subscribe(data => {
                    if (data.status === 'success') {
                        this.setData();
                    }
                }, error => {
                });
            }
        });
    }

    viewItem(element: any) {
        const modalRef = this.modalService.open(PurchaseBuyInfoComponent, { size: 'lg', windowClass: 'edit-modal', centered: true });
        // modalRef.componentInstance.data = { data: element, type: 'edit' };
        // modalRef.componentInstance.output.subscribe((res) => {
        //     if (res === 'success') {
        //         this.setData();
        //     }
        // }, error => {
        // });
    }
}
