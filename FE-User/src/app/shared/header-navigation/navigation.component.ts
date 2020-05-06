import { Component, AfterViewInit, EventEmitter, Output, AfterContentChecked, OnInit } from '@angular/core';
import {
    NgbModal,
    ModalDismissReasons,
    NgbPanelChangeEvent,
    NgbCarouselConfig
} from '@ng-bootstrap/ng-bootstrap';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
import { LocalStoreManager } from '../../services/local-store-manager.service';
import { Router } from '@angular/router';
declare var $: any;
@Component({
    selector: 'app-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit, AfterViewInit, AfterContentChecked {
    @Output() data = new EventEmitter();
    number: any;
    urlAvatar: string;
    public config: PerfectScrollbarConfigInterface = {};
    constructor(private modalService: NgbModal, private router: Router, private localStoreManager: LocalStoreManager) {
    }
    ngOnInit(): void {
        this.number = this.localStoreManager.getNumberCart();
        this.urlAvatar = this.localStoreManager.getUrlAvatar();
    }

    logout() {
        this.localStoreManager.clearAll();
        this.router.navigateByUrl('/login');
    }

    ngAfterViewInit() {
    }

    ngAfterContentChecked() {
        this.number = this.localStoreManager.getNumberCart();
        this.localStoreManager.getUrlAvatar();
    }

    onAction(url: string): void {
        this.localStoreManager.setPageProfile('profile');
        this.router.navigateByUrl(url);
    }
    toStorageCart(): void {
        if (this.localStoreManager.getNumberCart() > 0) {
            this.router.navigateByUrl('/component/storage-cart');
        }
    }
}
