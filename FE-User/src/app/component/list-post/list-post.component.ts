
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EndpointFactory } from '../../services/endpoint-factory.service';
import { PostElement } from '../model/post.model';
import { LocalStoreManager } from '../../services/local-store-manager.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-list-post',
    templateUrl: './list-post.component.html',
    styleUrls: ['./list-post.component.scss']
})
export class ListPostComponent implements OnInit {
    currentRate = 3.5;
    isLoadData: false;
    dataList: PostElement[] = null;
    constructor(private router: Router, private modalService: NgbModal, private changeDetectorRefs: ChangeDetectorRef,
        private endpointFactory: EndpointFactory, private localStoreManager: LocalStoreManager) {
        this.loadData();
        this.checkLoadData();
    }
    ngOnInit() {
    }

    loadData() {
        this.endpointFactory.getEndPoint('posts').subscribe(data => {
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
                    temp.push(post);

                });
                this.dataList = temp;
            }
        });
    }

    checkLoadData(): boolean {
        return false;
    }

    searchPost(search: string) {
        this.endpointFactory.getEndPoint('posts/search?keySearch=' + search).subscribe(data => {
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
                    temp.push(post);

                });
                this.dataList = temp;
            }
        });
    }

    showInfo(post: PostElement): void {
        this.localStoreManager.setPostSelected(post.postId);
        if (this.localStoreManager.getPageProfile() === 'profile') {
            this.router.navigateByUrl('/component/list-item');
        } else {
            this.router.navigateByUrl('/component/item-info');
        }
    }

}

