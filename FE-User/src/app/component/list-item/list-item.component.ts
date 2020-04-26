import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EndpointFactory } from '../../services/endpoint-factory.service';
import { PostElement } from '../model/post.model';
import { PurchaseElement } from '../model/PurchaseElement.model';
import { PurchaseInfoComponent } from './purchase-info/purchase-info.component';
import { DialogConfirmComponent } from '../dialog-confirm/dialog-confirm.component';

@Component({
    selector: 'app-list-item',
    templateUrl: './list-item.component.html',
    styleUrls: ['./list-item.component.scss']
})
export class ListItemComponent implements OnInit {
    dataSource: MatTableDataSource<PostElement>;
    dataList: PostElement[] = null;
    displayedColumns: string[] = ['imageURL', 'purchaseId', 'postId', 'buyerName', 'unitPrice', 'purchaseNumber', 'dateOfOrder',
        'statusPurchaseName', 'view', 'delete'];
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
        this.endpointFactory.getEndPointByHeader('purchases/user').subscribe(data => {
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
                    post.dateOfOrder = new Date(element.dateOfOrder[0], element.dateOfOrder[1], element.dateOfOrder[2]);
                    post.imageURL = element.post.imagePost.url;
                    temp.unshift(post);
                });
                this.dataList = temp;
                data = [{ id: 1, name: 'name1' }, { id: 2, name: 'name2' }];
            }
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
        const modalRef = this.modalService.open(DialogConfirmComponent, { size: 'lg', windowClass: 'delete-modal', centered: true });
        modalRef.componentInstance.data = { title: 'Xác nhận xóa đơn hàng', content: 'Bạn muốn xóa đơn hàng đơn hàng?' };
        modalRef.componentInstance.output.subscribe((res) => {
            if (res === 'success') {
                alert('ok');
            }
        });
    }

    viewItem(element: any) {
        const modalRef = this.modalService.open(PurchaseInfoComponent, { size: 'lg', windowClass: 'edit-modal', centered: true });
        modalRef.componentInstance.data = { data: element, type: 'edit' };
        modalRef.componentInstance.output.subscribe((res) => {
            if (res === 'success') {
                this.setData();
            }
        });
    }
}
