import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DialogConfirmComponent } from '../dialog-confirm/dialog-confirm.component';
import { LocalStoreManager } from '../../services/local-store-manager.service';
import { EndpointFactory } from '../../services/endpoint-factory.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-confirm-purchase',
    templateUrl: './confirm-purchase.component.html',
    styleUrls: ['./confirm-purchase.component.scss']
})
export class ConfirmPurchaseComponent implements OnInit {
    postId: number;
    unitPrice: number;
    numberItem: number;
    imageURL: string;
    unitName: string;
    name: string;
    address: string;
    phoneNumber: string;
    totalPrice: number;
    constructor(private modalService: NgbModal,private router: Router, private localStoreManager: LocalStoreManager,
         private endpointFactory: EndpointFactory) {
    }

    ngOnInit() {
        this.loadData();
    }

    loadData(): void {
        const data = this.localStoreManager.getDataPurchase().split(',');
        this.postId = Number.parseInt(data[0]);
        this.unitPrice = Number.parseFloat(data[2]);
        this.numberItem = Number.parseInt(data[3]);
        this.totalPrice = this.unitPrice * this.numberItem;
        this.imageURL = data[4];
        this.unitName = data[5];
    }

    confirmPurchase(): void {
        const modalRef = this.modalService.open(DialogConfirmComponent, { size: 'lg', windowClass: 'delete-modal', centered: true });
        modalRef.componentInstance.data = { title: 'Xác nhận đơn hàng', content: 'Bạn muốn xác nhận đơn hàng?' };
        modalRef.componentInstance.output.subscribe((res) => {
            if (res === 'success') {
                const params: any = {
                    address: this.address,
                    fullName: this.name,
                    phoneNumber: this.phoneNumber,
                    postId: this.postId,
                    purchaseNumber: this.numberItem,
                    statusPurchaseId: 1
                };
                this.endpointFactory.postByHeader(params, 'purchases/create').subscribe(data => {
                    if (data.status === 'success') {
                        this.router.navigateByUrl('component/list-post');
                    }
                }, error => {
                });
            }
        });
    }

}
