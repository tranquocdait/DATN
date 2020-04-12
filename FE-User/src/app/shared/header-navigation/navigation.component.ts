import { Component, AfterViewInit, EventEmitter, Output, AfterContentChecked } from '@angular/core';
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
export class NavigationComponent implements AfterViewInit, AfterContentChecked {
  @Output() toggleSidebar = new EventEmitter<void>();
  number: any;
  public config: PerfectScrollbarConfigInterface = {};
  constructor(private modalService: NgbModal, private router: Router, private localStoreManager: LocalStoreManager) {
  }

  public showSearch = false;

  // This is for Notifications

  logout() {
    this.localStoreManager.removeToken();
    this.localStoreManager.deleteNumberCart();
    //location.reload();
    this.router.navigateByUrl('/login');
  }

  ngAfterViewInit() {

  }
  ngAfterContentChecked() {
    this.number = this.localStoreManager.getNumberCart();
  }
}
