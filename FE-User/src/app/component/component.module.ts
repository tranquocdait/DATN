import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatSortModule} from '@angular/material/sort';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {MatTableModule} from '@angular/material/table';
import { ComponentsRoutes } from './component.routing';
import { ListItemComponent } from './list-item/list-item.component';
import { ListPostComponent } from './list-post/list-post.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import { EditPostComponent } from './list-post/edit-post/edit-post.component';
import { DeletePostComponent } from './list-post/delete-post/delete-post.component';
import { DeleteItemComponent } from './list-item/delete-item/delete-item.component';
import { ItemInfoComponent } from './item-info/item-info.component';
import { PurchaseInfoComponent } from './list-item/purchase-info/purchase-info.component';
import { ConfirmPurchaseComponent } from './confirm-purchase/confirm-purchase.component';
import { UserInfoComponent } from './user-info/user-info.component';
import { EditUserComponent } from './user-info/edit-user/edit-user.component';
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(ComponentsRoutes),
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    MatTableModule,
    MatSortModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule
  ],exports: [
    PurchaseInfoComponent,
    EditUserComponent,
    EditPostComponent,
    DeleteItemComponent,
    DeletePostComponent,
   ],
  entryComponents: [
    PurchaseInfoComponent,
    EditPostComponent,
    EditUserComponent,
    DeleteItemComponent,
    DeletePostComponent,
  ],
  declarations: [
    ListItemComponent,
    ListPostComponent,
    EditPostComponent,
    DeletePostComponent,
    PurchaseInfoComponent,
    EditUserComponent,
    DeleteItemComponent,
    ItemInfoComponent,
    ConfirmPurchaseComponent,
    UserInfoComponent,
    EditUserComponent
  ]
})
export class ComponentsModule {}
