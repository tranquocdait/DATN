import { Component, OnInit, ChangeDetectorRef, AfterContentChecked } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EndpointFactory } from '../../services/endpoint-factory.service';
import { PostElement } from '../model/post.model';
import { LocalStoreManager } from '../../services/local-store-manager.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-list-user-post',
    templateUrl: './list-user-post.component.html',
    styleUrls: ['./list-user-post.component.scss']
})
export class ListUserPostComponent implements OnInit, AfterContentChecked {
    dataList: PostElement[] = null;
    getUrl = '';
    categoryId = 0;
    change = false;
    constructor(private router: Router, private modalService: NgbModal, private changeDetectorRefs: ChangeDetectorRef,
        private endpointFactory: EndpointFactory, private localStoreManager: LocalStoreManager) {
        this.loadFisrtData();
    }
    ngOnInit() {
    }

    loadFisrtData(): void {
        this.setUrl();
        this.loadData();
    }
    loadData(): void {
        this.endpointFactory.getEndPointByHeader(this.getUrl).subscribe(data => {
            if (data.status === 'success') {
                const temp = [];
                data.data.forEach((elementInfo) => {
                    const post = new PostElement();
                    const element = elementInfo.post;
                    post.postId = element.id;
                    post.postName = element.postName;
                    post.userName = element.user.userName;
                    post.userElement = element.user;
                    post.unitPrice = element.unitPrice;
                    post.address = element.address;
                    post.dateOfPost = new Date(element.dateOfPost[0], element.dateOfPost[1], element.dateOfPost[2]);
                    post.province = element.province;
                    post.imageURL = element.imagePost.url;
                    post.category = element.category;
                    if (element.description.length < 100) {
                        post.description = element.description;
                    } else {
                        post.description = element.description.substr(0, 100) + '...';
                    }
                    post.calculationUnit = element.calculationUnit;
                    post.averageRate = Number.parseFloat(elementInfo.averageRate);
                    temp.unshift(post);

                });
                this.dataList = temp;
            }
        });
    }

    setUrl(): void {
        this.getUrl = 'users/posts';
    }

    ngAfterContentChecked(): void {
        if (this.localStoreManager.getCategoryId() !== this.categoryId) {
            this.loadFisrtData();
        }
    }

    searchPost(search: string): void {
        //this.getUrl = 'posts/search?keySearch=' + search;
        //this.loadData();
    }

    showInfo(post: PostElement): void {
        this.localStoreManager.setPostSelected(post.postId);
        this.router.navigateByUrl('/component/list-item');
    }

}

