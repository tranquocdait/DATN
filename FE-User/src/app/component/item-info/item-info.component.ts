import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateCommentComponent } from './create-comment/create-comment.component';
import { Router } from '@angular/router';
import { LocalStoreManager } from '../../services/local-store-manager.service';
import { EndpointFactory } from '../../services/endpoint-factory.service';
import { PostElement } from '../model/post.model';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { LoginComponent } from '../../login/login.component';

@Component({
    selector: 'app-item-info',
    templateUrl: './item-info.component.html',
    styleUrls: ['./item-info.component.scss']
})
export class ItemInfoComponent implements OnInit {
    @BlockUI() blockUI: NgBlockUI;
    constructor(private router: Router, private modalService: NgbModal, private localStoreManager: LocalStoreManager,
        private endpointFactory: EndpointFactory) {
        this.loadData();
    }
    currentRate = 0;
    numberItem = 1;
    dataContent: any;
    dataComment: any[];
    checkLoadData = false;
    checkUserRate: false;
    checkUserComment: false;
    descriptions: string[];
    dataList: PostElement[] = null;
    userId = 0;
    arrPostId: string[];
    arrPost1: any[];
    arrPost2: any[];
    ngOnInit() {
    }

    loadData() {
        this.blockUI.start();
        if (this.localStoreManager.getToken() !== null || this.localStoreManager.getToken() === '') {
            this.endpointFactory.postByHeader(null, 'users/information').subscribe(dataInfo => {
                if (dataInfo.status === 'success') {
                    this.userId = dataInfo.data.userID;
                }
            });
        }
        this.endpointFactory.getEndPoint('posts/' + this.localStoreManager.getPostSelected()).subscribe(data => {
            if (data.status === 'success') {
                const post = new PostElement();
                const element = data.data.post;
                post.postId = element.id;
                post.postName = element.postName;
                post.userId = element.user.userID;
                post.userName = element.user.userName;
                post.userElement = element.user;
                post.unitPrice = element.unitPrice;
                post.address = element.address;
                post.dateOfPost = new Date(element.dateOfPost[0], element.dateOfPost[1], element.dateOfPost[2]);
                post.province = element.province;
                post.imageURLs = element.imagePosts;
                post.category = element.category;
                post.description = element.description;
                this.convertDescription(element.description);
                post.calculationUnit = element.calculationUnit;
                post.averageRate = Number.parseFloat(data.data.averageRate);
                this.dataComment = data.data.userCommentDTOList;
                this.dataContent = post;
                this.suggetPurchase();
                this.blockUI.stop();
                this.checkLoadData = true;
            }
        });
    }

    suggetPurchase(): void {
        const url = 'posts/' + this.dataContent.category.id + '/0/category';
        this.endpointFactory.getEndPointWithResponeHeader(url).subscribe(data => {
            if (data.body.status === 'success') {
                const temp = [];
                data.body.data.forEach((elementInfo) => {
                    const post = new PostElement();
                    const element = elementInfo.post;
                    if (this.localStoreManager.getPostSelected().toString() !== element.id.toString()) {
                        post.postId = element.id;
                        post.postName = element.postName;
                        post.userName = element.user.userName;
                        post.userElement = element.user;
                        post.unitPrice = element.unitPrice;
                        post.address = element.address;
                        post.dateOfPost = new Date(element.dateOfPost[0], element.dateOfPost[1], element.dateOfPost[2]);
                        post.province = element.province;
                        post.imageURLs = element.imagePosts;
                        post.category = element.category;
                        if (element.description !== null) {
                            if (element.description.length < 100) {
                                post.description = element.description;
                            } else {
                                post.description = element.description.substr(0, 100) + '...';
                            }
                        } else {
                            post.description = '';
                        }
                        post.calculationUnit = element.calculationUnit;
                        post.averageRate = Number.parseFloat(elementInfo.averageRate);
                        temp.push(post);
                    }
                });
                this.dataList = temp;
            }
        });
        setTimeout(() => {
            this.arrPostId = JSON.parse(this.localStoreManager.getArrPostSelected());
            for (const postId of this.arrPostId) {
                let check = true;
                if (this.localStoreManager.getPostSelected().toString() === postId.toString()) {
                    continue;
                }
                for (const post of this.dataList) {
                    if (postId === post.postId) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    this.addPostToData(postId);
                }
            }
        }, 2000);
        setTimeout(() => this.setArrPost(), 3000);
    }

    setArrPost() {
        this.arrPost1 = [];
        this.arrPost2 = [];
        this.dataList.forEach((post, i) => {
            if (i < 5) {
                this.arrPost1.push(post);
            } else if (i >= 5 && i < 10) {
                this.arrPost2.push(post);
            }
        });
    }

    addPostToData(postId: string): void {
        this.endpointFactory.getEndPoint('posts/' + postId).subscribe(data => {
            if (data.status === 'success') {
                const post = new PostElement();
                const element = data.data.post;
                post.postId = element.id;
                post.postName = element.postName;
                post.userId = element.user.userID;
                post.userName = element.user.userName;
                post.userElement = element.user;
                post.unitPrice = element.unitPrice;
                post.address = element.address;
                post.dateOfPost = new Date(element.dateOfPost[0], element.dateOfPost[1], element.dateOfPost[2]);
                post.province = element.province;
                post.imageURLs = element.imagePosts;
                post.category = element.category;
                post.description = element.description;
                post.calculationUnit = element.calculationUnit;
                post.averageRate = Number.parseFloat(data.data.averageRate);
                this.dataList.push(post);
            }
        });
    }

    convertDescription(description: string): void {
        if (description === null) {
            this.descriptions = [];
            return;
        }
        this.descriptions = [];
        const arrString = description.split('-');
        if (arrString.length === 1) {
            this.descriptions.push(arrString[0]);
            return;
        }
        arrString.forEach(element => {
            if (element !== '') {
                this.descriptions.push(' - ' + element);
            }
        });
    }

    changeNumber(change: String): void {
        if (change === 'add') {
            this.numberItem += 1;
        } else {
            if (this.numberItem > 0) {
                this.numberItem -= 1;
            }
        }
    }

    createComment(): void {
        if (this.localStoreManager.getCheckLogin()) {
            const modalRef =
                this.modalService.open(CreateCommentComponent, { size: 'lg', windowClass: 'create-comment-dialog', centered: true });
            modalRef.componentInstance.data = { data: this.dataContent };
            modalRef.componentInstance.output.subscribe((res) => {
                if (res === 'success') {
                    this.loadData();
                }
            });
        } else {
            this.onLogin();
        }
    }

    purchaseItem(): void {
        const dataPurchase = [
            this.dataContent.postId,
            this.dataContent.postName,
            this.dataContent.unitPrice,
            this.numberItem,
            this.dataContent.imageURLs[0].url,
            this.dataContent.calculationUnit.unitName,
        ];
        this.localStoreManager.setDataPurchase(dataPurchase.toString());
        this.router.navigateByUrl('/component/confirm-purchase');

    }

    addStorageCart(): void {
        const dataPurchase = {
            postId: this.dataContent.postId,
            postName: this.dataContent.postName,
            unitPrice: this.dataContent.unitPrice,
            numberItem: this.numberItem,
            userName: this.dataContent.userName,
            province: this.dataContent.province.nameProvince,
            imageURL: this.dataContent.imageURLs[0].url,
            address: this.dataContent.address,
            unitName: this.dataContent.calculationUnit.unitName,
        };
        this.localStoreManager.setStorageCart(JSON.stringify(dataPurchase));
    }

    onLogin(): void {
        this.modalService.open(LoginComponent, { size: 'lg', windowClass: 'login-modal', centered: true });
    }

    viewProfileUser(): void {
        this.localStoreManager.setUserNameSelected(this.dataContent.userName);
        this.router.navigateByUrl('/component/profile-user');
    }

    showInfo(post: PostElement): void {
        this.localStoreManager.setPostSelected(post.postId);
        location.reload();
    }

}
