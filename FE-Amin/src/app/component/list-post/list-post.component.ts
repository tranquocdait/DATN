
import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EndpointFactory } from '../../services/endpoint-factory.service';
import { PostElement } from '../model/post.model';
import { DeletePostComponent } from './delete-post/delete-post.component';
import { EditPostComponent } from './edit-post/edit-post.component';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.scss']
})
export class ListPostComponent implements OnInit {
  dataSource: MatTableDataSource<PostElement>;
  dataList: PostElement[] = null;
  displayedColumns: string[] = ['imageURL','postId', 'postName', 'userName', 'description', 'unitPrice', 'address', 'dateOfPost', 'province', 'category', 'edit', 'delete'];
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
    this.endpointFactory.getEndPoint("posts").subscribe(data => {
      if (data.status === "success") {
        const temp = [];
        data.data.forEach((element, index) => {
          let post = new PostElement();
          post.postId = element.id;
          post.postName = element.postName;
          post.userName = element.user.userName;
          post.userElement = element.user;
          post.unitPrice = element.unitPrice;
          post.address=element.address;
          post.dateOfPost=new Date(element.dateOfPost[0],element.dateOfPost[1],element.dateOfPost[2]);
          post.province = element.province.nameProvince;
          post.imageURL = element.imagePost.url;
          post.category = element.category.nameCategory;
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
  deleteUser(element: any) {
    const modalRef = this.modalService.open(DeletePostComponent, { size: 'lg', windowClass: 'delete-modal', centered: true });
    modalRef.componentInstance.data = { data: element }
    modalRef.componentInstance.output.subscribe((res) => {
      if (res === "success") {
        this.setData();
      }
    });
  }

  editUser(element: any) {
    const modalRef = this.modalService.open(EditPostComponent, { size: 'lg', windowClass: 'edit-modal', centered: true });
    modalRef.componentInstance.data = { data: element, type: 'edit' };
    modalRef.componentInstance.output.subscribe((res) => {
      if (res === "success") {
        this.setData();
      }
    });
  }

  searchUser(search: string) {
    this.endpointFactory.getEndPoint("users/search?keySearch="+search).subscribe(data => {
      if (data.status === "success") {
        const temp = [];
        data.data.forEach((element, index) => {
          let post = new PostElement();
          post.postId = element.postId;
          post.userName = element.userName;
          post.postName = element.postName;
          post.unitPrice = element.unitPrice;
          post.address=element.address;
          post.dateOfPost=element.dateOfPost;
          post.province = element.province;
          post.imageURL = element.imagePost.url;
          post.category = element.nameCategory;
          temp.push(post);

        });
        this.dataList = temp;
        this.setDataSource();
      };
    }
    );
  }
}
